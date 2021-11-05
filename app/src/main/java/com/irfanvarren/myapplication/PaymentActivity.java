package com.irfanvarren.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.DistributorArrayAdapter;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Purchase;
import com.irfanvarren.myapplication.Model.PurchaseDetail;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;
import com.irfanvarren.myapplication.ViewModel.DistributorViewModel;
import com.irfanvarren.myapplication.ViewModel.PurchaseViewModel;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentActivity extends AppCompatActivity {
    private Bitmap invoiceBitmap;
    private Uri invoiceUri;
    private List<CartItem> mCartItems;
    private DistributorViewModel mDistributorViewModel;
    private MaterialDatePicker mTransactionDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private MaterialDatePicker mDueDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Integer mDistributorId;
    private Integer transactionType;
    private PurchaseViewModel purchaseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        TextView txtTotalPrice = (TextView) findViewById(R.id.txtTotal);
        RelativeLayout inputInvoiceImg = (RelativeLayout) findViewById(R.id.inputInvoiceImg);
        AutoCompleteTextView txtDistributor = (AutoCompleteTextView) findViewById(R.id.distributorId);
        ImageButton addDistributorBtn = (ImageButton) findViewById(R.id.addDistributorBtn);
        EditText txtInvoiceNo = (EditText) findViewById(R.id.invoiceNo);
        EditText txtNote = (EditText) findViewById(R.id.note);
        EditText txtPaymentAmount = (EditText) findViewById(R.id.paymentAmount);
        EditText transactionDate = (EditText) findViewById(R.id.transactionDate);
        EditText dueDate = (EditText) findViewById(R.id.dueDate);
        RelativeLayout payBtn = (RelativeLayout) findViewById(R.id.payBtn);


        if (getIntent().getExtras() != null) {
            transactionType = getIntent().getIntExtra("transcationType",0);
            purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
            Integer no = purchaseViewModel.getNo();
            if(no != null){
                no++;
            }else{
                no = 1;
            }
            String invoiceNo = String.format("%04d",no);
            if(transactionType == 1){
                invoiceNo = "PJ/"+ Year.now().getValue()+"/"+invoiceNo;
            }
            txtInvoiceNo.setText(invoiceNo);
        }


        mDistributorViewModel = new ViewModelProvider(this).get(DistributorViewModel.class);
        mDistributorViewModel.getAll().observe(this, new Observer<List<Distributor>>() {
            @Override
            public void onChanged(List<Distributor> categories) {
                final DistributorArrayAdapter adapter = new DistributorArrayAdapter(PaymentActivity.this, R.layout.auto_complete_item, categories);
                txtDistributor.setAdapter(adapter);
            }
        });

        txtDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Distributor selectedCat = (Distributor) adapterView.getItemAtPosition(i);
                mDistributorId = selectedCat.getId();
            }
        });

        addDistributorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PaymentActivity.this, DistributorInputActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDueDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });


        mTransactionDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.parseLong(selection.toString());
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                transactionDate.setText(df.format(date));

            }
        });


        mDueDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.parseLong(selection.toString());
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                dueDate.setText(df.format(date));

            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Purchase purchase = new Purchase();
                PurchaseRepository mRepository = new PurchaseRepository(getApplication());
                int purchaseId = (int) mRepository.insert(purchase);

                List<PurchaseDetail> purchaseDetails = new ArrayList<PurchaseDetail>();

                if(purchaseId != 0) {
                    for (CartItem cartItem : mCartItems) {
                        PurchaseDetail purchaseDetail = new PurchaseDetail(purchaseId, cartItem.getProductId(), cartItem.getQty(), cartItem.getPrice());
                        purchaseDetails.add(purchaseDetail);
                    }
                }

                PurchaseDetail[] array = new PurchaseDetail[purchaseDetails.size()];
                purchaseDetails.toArray(array);
                mRepository.insertAllDetails(array);
                Toast.makeText(getApplicationContext(),"Pembelian berhasil dilakukan",Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        inputInvoiceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });

        if (getIntent().getExtras() != null) {
            mCartItems = (List<CartItem>) getIntent().getSerializableExtra("cartItems");
            if (!mCartItems.isEmpty()) {
                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
                txtTotalPrice.setText(nf.format(sumPriceItem(mCartItems)));
            }
        }
    }

    public Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList();
        PackageManager pm = getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = pm.queryIntentActivities(captureIntent, 0);

        for (ResolveInfo res : listCam) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = pm.queryIntentActivities(galleryIntent, 0);

        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent = allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

        Intent chooserIntent = Intent.createChooser(mainIntent, "Select source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));
        return chooserIntent;
    }

    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "invoice.png"));
        }
        return outputFileUri;
    }

    public Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ? getCaptureImageOutputUri() : data.getData();
    }

    public Double sumPriceItem(List<CartItem> cartItems) {
        Double price = new Double(0);
        if (cartItems.size() > 0) {
            for (int i = 0; i < cartItems.size(); i++) {
                price += (cartItems.get(i).getQty() * cartItems.get(i).getPrice());
            }
        }
        return price;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            ImageView imageView = (ImageView) findViewById(R.id.invoiceImg);
            LinearLayout imageDesc = (LinearLayout) findViewById(R.id.invoiceImgDesc);
            if (getPickImageResultUri(data) != null) {
                invoiceUri = getPickImageResultUri(data);
                try {
                    invoiceBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), invoiceUri);
                    imageDesc.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(invoiceBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                bitmap = (Bitmap) data.getExtras().get("data");
                invoiceBitmap = bitmap;
                imageDesc.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(invoiceBitmap);
            }
        }
    }
}