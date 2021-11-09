package com.irfanvarren.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.irfanvarren.myapplication.Adapter.CustomerArrayAdapter;
import com.irfanvarren.myapplication.Adapter.DistributorArrayAdapter;
import com.irfanvarren.myapplication.Database.DebtRepository;
import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Database.OrderRepository;
import com.irfanvarren.myapplication.Database.PaymentRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.Database.ReceivableRepository;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Customer;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.Model.Debt;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Order;
import com.irfanvarren.myapplication.Model.OrderDetail;
import com.irfanvarren.myapplication.Model.Payment;
import com.irfanvarren.myapplication.Model.Purchase;
import com.irfanvarren.myapplication.Model.PurchaseDetail;
import com.irfanvarren.myapplication.Model.Receivable;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;
import com.irfanvarren.myapplication.ViewModel.CustomerViewModel;
import com.irfanvarren.myapplication.ViewModel.DistributorViewModel;
import com.irfanvarren.myapplication.ViewModel.OrderViewModel;
import com.irfanvarren.myapplication.ViewModel.PurchaseViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 */
public class PaymentFragment extends Fragment {

    private Bitmap invoiceBitmap;
    private Uri invoiceUri;
    private List<CartItem> mCartItems;
    private DistributorViewModel mDistributorViewModel;
    private CustomerViewModel mCustomerViewModel;
    private MaterialDatePicker mTransactionDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private MaterialDatePicker mDueDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Integer mDistributorId,mCustomerId, mPurchaseId, mOrderId;
    private Integer mTransactionType;
    private PurchaseViewModel purchaseViewModel;
    private OrderViewModel orderViewModel;
    private String mInvoiceNo, mNote, mInvoicePath, appTitle;
    private Date mTransactionDate = new Date(), mDueDate = new Date();
    private Boolean mStatus = false;
    private CartItemViewModel cartItemViewModel;
    private Double mTotalPrice = new Double(0), mSubtotal = new Double(0), mOtherCost = new Double(0);

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mTransactionType = getArguments().getInt("transactionType", 0);
            Integer no = new Integer(0);
            if (mTransactionType == 1) {
                purchaseViewModel = new ViewModelProvider(this).get(PurchaseViewModel.class);
                no = purchaseViewModel.getNo();
                appTitle = "Pembelian";
            } else {
                orderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
                no = orderViewModel.getNo();
                appTitle = "Penjualan";
            }

            if (no != null) {
                no++;
            } else {
                no = 1;
            }
            mInvoiceNo = String.format("%04d", no);
            if (mTransactionType == 1) {
                mInvoiceNo = "PB/" + Year.now().getValue() + "/" + mInvoiceNo;
            } else if (mTransactionType == 2) {
                mInvoiceNo = "PJ/" + Year.now().getValue() + "/" + mInvoiceNo;
            }


            mCartItems = (List<CartItem>) getArguments().getSerializable("cartItems");
            cartItemViewModel = new ViewModelProvider(requireActivity()).get(CartItemViewModel.class);
            mSubtotal = cartItemViewModel.getTotalPrice();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        TextView txtTotalPrice = (TextView) view.findViewById(R.id.txtTotal);
        RelativeLayout inputInvoiceImg = (RelativeLayout) view.findViewById(R.id.inputInvoiceImg);
        AutoCompleteTextView txtDistributor = (AutoCompleteTextView) view.findViewById(R.id.distributorId);
        AutoCompleteTextView txtCustomer = (AutoCompleteTextView) view.findViewById(R.id.customerId);
        LinearLayout distributorWrapper = (LinearLayout) view.findViewById(R.id.distributorWrapper);
        LinearLayout customerWrapper = (LinearLayout) view.findViewById(R.id.customerWrapper);
        ImageButton addDistributorBtn = (ImageButton) view.findViewById(R.id.addDistributorBtn);
        ImageButton addCustomerBtn = (ImageButton) view.findViewById(R.id.addCustomerBtn);
        EditText txtInvoiceNo = (EditText) view.findViewById(R.id.invoiceNo);
        EditText txtNote = (EditText) view.findViewById(R.id.note);
        EditText txtPaymentAmount = (EditText) view.findViewById(R.id.paymentAmount);
        EditText transactionDate = (EditText) view.findViewById(R.id.transactionDate);
        EditText dueDate = (EditText) view.findViewById(R.id.dueDate);
        TextView txtTitle = (TextView) view.findViewById(R.id.paymentTitle);
        RelativeLayout payBtn = (RelativeLayout) view.findViewById(R.id.payBtn);
        CheckBox ckFullPayment = (CheckBox) view.findViewById(R.id.ckFullPayment);

        ckFullPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtPaymentAmount.setText(String.valueOf(mSubtotal.intValue()));
               }else{
                    txtPaymentAmount.setText("0");
                }
            }
        });

        if(mTransactionType == 1){
            distributorWrapper.setVisibility(View.VISIBLE);
            mDistributorViewModel = new ViewModelProvider(this).get(DistributorViewModel.class);
            mDistributorViewModel.getAll().observe(getActivity(), new Observer<List<Distributor>>() {
                @Override
                public void onChanged(List<Distributor> categories) {
                    categories.add(0, new Distributor("Tanpa Distributor"));
                    final DistributorArrayAdapter adapter = new DistributorArrayAdapter(getActivity(), R.layout.auto_complete_item, categories);
                    txtDistributor.setAdapter(adapter);
                    txtDistributor.setThreshold(1);
                }
            });
        }else if(mTransactionType == 2){
            customerWrapper.setVisibility(View.VISIBLE);
            mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
            mCustomerViewModel.getAll().observe(getActivity(), new Observer<List<Customer>>() {
                @Override
                public void onChanged(List<Customer> categories) {
                    categories.add(0, new Customer("Tanpa Customer"));
                    final CustomerArrayAdapter adapter = new CustomerArrayAdapter(getActivity(), R.layout.auto_complete_item, categories);
                    txtCustomer.setAdapter(adapter);
                    txtCustomer.setThreshold(1);
                }
            });
        }

        txtTitle.setText(appTitle);
        txtInvoiceNo.setText(mInvoiceNo);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        txtTotalPrice.setText("Rp. " + nf.format(mSubtotal));
        txtPaymentAmount.setText(String.valueOf(mSubtotal.intValue()));

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
                Intent intent = new Intent(getActivity(), DistributorInputActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        txtCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Customer selectedCat = (Customer) adapterView.getItemAtPosition(i);
                mCustomerId = selectedCat.getId();
            }
        });

        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerInputActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        transactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransactionDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDueDatePicker.show(getChildFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });


        mTransactionDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mTransactionDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                transactionDate.setText(df.format(date));

            }
        });


        mDueDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Long selectedDate = Long.valueOf(selection.toString());
                mDueDate = new DateConverter(selectedDate).getDate();
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                dueDate.setText(df.format(date));

            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double amountPaid = new Double(0);
                if (txtPaymentAmount.getText().toString().trim().length() > 0) {
                    amountPaid = Double.valueOf(txtPaymentAmount.getText().toString());
                }

                if(amountPaid > mSubtotal){
                    Toast.makeText(getActivity().getApplicationContext(),"Jumlah yang dibayarkan melebihi subtotal !",Toast.LENGTH_SHORT).show();
                    return;
                }else if(amountPaid < 0){
                    Toast.makeText(getActivity().getApplicationContext(),"Jumlah yang diinputan salah !",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mTransactionType == 1) {
                    Purchase purchase = new Purchase();
                    String invoiceNo = mInvoiceNo;


                    if (mDistributorId != null && mDistributorId != 0) {
                        purchase.setDistributorId(mDistributorId);
                    }

                    if (txtInvoiceNo.getText().toString().trim().length() > 0) {
                        invoiceNo = txtInvoiceNo.getText().toString().trim();
                        purchase.setInvoiceNo(txtInvoiceNo.getText().toString().trim());
                    }

                    if (txtNote.getText().toString().trim().length() > 0) {
                        purchase.setNote(txtNote.getText().toString());
                    }

                    if (mSubtotal > 0) {
                        purchase.setSubtotal(mSubtotal);
                        purchase.setTotal(mSubtotal);
                    }

                    if (amountPaid < mSubtotal) {
                        mStatus = false;
                    } else {
                        mStatus = true;
                    }

                    if (invoiceBitmap != null) {
                        mInvoicePath = saveToInternalStorage(invoiceBitmap, "invoices", invoiceNo);
                    }

                    purchase.setAmountPaid(amountPaid);
                    purchase.setInvoicePath(mInvoicePath);
                    purchase.setStatus(mStatus);
                    purchase.setTransactionDate(mTransactionDate);
                    purchase.setDueDate(mDueDate);

                    PurchaseRepository mRepository = new PurchaseRepository(getActivity().getApplication());
                    mPurchaseId = (int) mRepository.insert(purchase);

                    if (amountPaid > 0) {
                        Payment payment = new Payment();
                        payment.setPurchaseId(mPurchaseId);
                        payment.setInvoiceNo(invoiceNo);
                        //payment.setPaymentNo(paymentNO);
                        payment.setAmount(amountPaid);
                        PaymentRepository paymentRepository = new PaymentRepository(getActivity().getApplication());
                        paymentRepository.insert(payment);
                    }

                    if(amountPaid < mSubtotal){
                        Log.d("DISTRIBUTOR_ID",String.valueOf(mDistributorId));
                        if (mDistributorId == null || mDistributorId == 0) {
                            Toast.makeText(getActivity().getApplicationContext(),"Hutang wajib memilih distributor !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Debt debt = new Debt();
                        debt.setDistributorId(mDistributorId);
                        debt.setPurchaseId(mPurchaseId);
                        debt.setAmount(mSubtotal - amountPaid);
                        debt.setDueDate(mDueDate);
                        debt.setStatus(false);
                        DebtRepository debtRepository = new DebtRepository(getActivity().getApplication());
                        debtRepository.insert(debt);
                    }

                    List<PurchaseDetail> purchaseDetails = new ArrayList<PurchaseDetail>();
                    List<Inventory> inventories = new ArrayList<Inventory>();
                    if (mPurchaseId != 0) {
                        for (CartItem cartItem : mCartItems) {
                            PurchaseDetail purchaseDetail = new PurchaseDetail(mPurchaseId, cartItem.getProductId(), cartItem.getQty(), cartItem.getPrice());
                            purchaseDetails.add(purchaseDetail);

                            Inventory inventory = new Inventory();
                            inventory.setProductId(cartItem.getProductId());
                            inventory.setQty(cartItem.getQty());
                            inventory.setPrice(cartItem.getPrice());
                            inventory.setInvoiceNo(mInvoiceNo);
                            inventory.setPurchaseId(mPurchaseId);
                            inventory.setType("in");
                            inventory.setTransactionType("buy");
                            inventory.setProductName(cartItem.getName());
                            if (mDistributorId != null && mDistributorId != 0) {
                                inventory.setDistributorId(mDistributorId);
                            }
                            inventory.setCreatedAt(new Date());
                            inventory.setUpdatedAt(new Date());
                            inventories.add(inventory);
                        }
                    }

                    PurchaseDetail[] purchaseArray = new PurchaseDetail[purchaseDetails.size()];
                    purchaseDetails.toArray(purchaseArray);
                    mRepository.insertAllDetails(purchaseArray);

                    Inventory[] inventoryArray = new Inventory[inventories.size()];
                    inventories.toArray(inventoryArray);
                    InventoryRepository inventoryRepository = new InventoryRepository(getActivity().getApplication());
                    inventoryRepository.insertAll(inventoryArray);


                    Toast.makeText(getActivity().getApplicationContext(), "Pembelian berhasil dilakukan", Toast.LENGTH_SHORT).show();
                }else{
                    Order order = new Order();
                    String invoiceNo = mInvoiceNo;

                    if (mCustomerId != null && mCustomerId != 0) {
                        order.setCustomerId(mCustomerId);
                    }

                    if (txtInvoiceNo.getText().toString().trim().length() > 0) {
                        invoiceNo = txtInvoiceNo.getText().toString().trim();
                        order.setInvoiceNo(txtInvoiceNo.getText().toString().trim());
                    }

                    if (txtNote.getText().toString().trim().length() > 0) {
                        order.setNote(txtNote.getText().toString());
                    }

                    if (amountPaid > 0) {
                        order.setAmountPaid(amountPaid);
                    }

                    if (mSubtotal > 0) {
                        order.setSubtotal(mSubtotal);
                        order.setTotal(mSubtotal);
                    }

                    if (amountPaid < mSubtotal) {
                        mStatus = false;
                    } else {
                        mStatus = true;
                    }

                    if (invoiceBitmap != null) {
                        mInvoicePath = saveToInternalStorage(invoiceBitmap, "invoices", invoiceNo);
                    }
                    order.setInvoicePath(mInvoicePath);
                    order.setStatus(mStatus);
                    order.setTransactionDate(mTransactionDate);
                    order.setDueDate(mDueDate);

                    OrderRepository mRepository = new OrderRepository(getActivity().getApplication());
                    mOrderId = (int) mRepository.insert(order);

                    if (amountPaid > 0) {
                        Payment payment = new Payment();
                        payment.setOrderId(mOrderId);
                        payment.setInvoiceNo(invoiceNo);
                        //payment.setPaymentNo(paymentNO);
                        payment.setAmount(amountPaid);
                        PaymentRepository paymentRepository = new PaymentRepository(getActivity().getApplication());
                        paymentRepository.insert(payment);
                    }

                    if(amountPaid < mSubtotal){

                        if (mCustomerId == null || mCustomerId == 0) {
                            Toast.makeText(getActivity().getApplicationContext(),"Piutang wajib memilih pelanggan !",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Receivable receivable = new Receivable();
                        receivable.setOrderId(mOrderId);
                        receivable.setAmount(mSubtotal - amountPaid);
                        receivable.setDueDate(mDueDate);
                        receivable.setStatus(false);
                        ReceivableRepository receivableRepository = new ReceivableRepository(getActivity().getApplication());
                        receivableRepository.insert(receivable);
                    }


                    List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
                    List<Inventory> inventories = new ArrayList<Inventory>();
                    if (mOrderId != 0) {
                        for (CartItem cartItem : mCartItems) {
                            OrderDetail orderDetail = new OrderDetail(mOrderId, cartItem.getProductId(), cartItem.getQty(), cartItem.getPrice());
                            orderDetails.add(orderDetail);

                            Inventory inventory = new Inventory();
                            inventory.setProductId(cartItem.getProductId());
                            inventory.setQty(-cartItem.getQty());
                            inventory.setPrice(cartItem.getPrice());
                            inventory.setInvoiceNo(mInvoiceNo);
                            inventory.setOrderId(mOrderId);
                            inventory.setType("out");
                            inventory.setTransactionType("sell");
                            inventory.setProductName(cartItem.getName());
                            if (mCustomerId != null && mCustomerId != 0) {
                                inventory.setCustomerId(mCustomerId);
                            }
                            inventory.setCreatedAt(new Date());
                            inventory.setUpdatedAt(new Date());
                            inventories.add(inventory);
                        }
                    }

                    OrderDetail[] orderArray = new OrderDetail[orderDetails.size()];
                    orderDetails.toArray(orderArray);
                    mRepository.insertAllDetails(orderArray);

                    Inventory[] inventoryArray = new Inventory[inventories.size()];
                    inventories.toArray(inventoryArray);
                    InventoryRepository inventoryRepository = new InventoryRepository(getActivity().getApplication());
                    inventoryRepository.insertAll(inventoryArray);
                    Toast.makeText(getActivity().getApplicationContext(), "Penjualan berhasil dilakukan", Toast.LENGTH_SHORT).show();
                }

                getActivity().finish();
            }
        });


        inputInvoiceImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(getPickImageChooserIntent(), 200);
            }
        });


        return view;
    }

    public Intent getPickImageChooserIntent() {
        Uri outputFileUri = getCaptureImageOutputUri();
        List<Intent> allIntents = new ArrayList();
        PackageManager pm = getActivity().getPackageManager();

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
        File getImage = getActivity().getExternalCacheDir();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap;
        if (resultCode == Activity.RESULT_OK) {
            ImageView imageView = (ImageView) getView().findViewById(R.id.invoiceImg);
            LinearLayout imageDesc = (LinearLayout) getView().findViewById(R.id.invoiceImgDesc);
            if (getPickImageResultUri(data) != null) {
                invoiceUri = getPickImageResultUri(data);
                try {
                    invoiceBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), invoiceUri);
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

    private String saveToInternalStorage(Bitmap bitmapImage, String dir) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        Long ts = System.currentTimeMillis() / 1000;
        String filename = ts.toString() + ".png";
        File mypath = new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            Log.d("SAVE FILE", "Error Cannot Save FIle");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String dir, String filename) {
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        File directory = cw.getDir(dir, Context.MODE_PRIVATE);
        filename = filename + ".png";
        File mypath = new File(directory, filename);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            Log.d("SAVE FILE", "Error Cannot Save FIle");
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filename;
    }
}