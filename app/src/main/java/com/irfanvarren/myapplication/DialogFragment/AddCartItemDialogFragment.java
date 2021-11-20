package com.irfanvarren.myapplication.DialogFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.irfanvarren.myapplication.Database.ProductRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.R;

public class AddCartItemDialogFragment extends DialogFragment {
    private Product mProduct;
    private Context mContext;
    private int mPosition = 0;
    private int mQty = 0;
    private Double mPrice = new Double(0);
    private OnFinishListener onFinishListener;
    private ProductRepository mProductRepository;
    private PurchaseRepository mPurchaseRepository;
    private int type = 1;
    private Integer mProductId = new Integer(0);

    public void setOnFinishListener(OnFinishListener listener) {
        onFinishListener = listener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_cart_item, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        mProductId = bundle.getInt("productId");
        mProduct = (Product) bundle.getSerializable("product");
        mPosition = bundle.getInt("position");
        Integer qty = bundle.getInt("qty");
        Double price = bundle.getDouble("price");
        type = bundle.getInt("type");
        Double buyPrice = new Double(0);
        Double sellPrice = new Double(0);

        ImageButton addBtn = (ImageButton) view.findViewById(R.id.addBtn);
        ImageButton substractBtn = (ImageButton) view.findViewById(R.id.substractBtn);
        EditText txtQty = (EditText) view.findViewById(R.id.txtQty);
        TextInputLayout txtPrice = (TextInputLayout) view.findViewById(R.id.price);
        Button submitBtn = (Button) view.findViewById(R.id.submitBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);
TextView dialogTitle = (TextView) view.findViewById(R.id.dialogTitle);

        if (type == 1) {
            dialogTitle.setText("Pembelian");
            txtPrice.setHint("Harga Beli");
            if(mProductId != 0) {
                mPurchaseRepository = new PurchaseRepository(getActivity().getApplication());
                buyPrice = mPurchaseRepository.getLastBoughtPrice(mProductId);
            }
        } else if (type == 2) {
            dialogTitle.setText("Penjualan");
            txtPrice.setHint("Harga Jual");
            if(mProductId != 0) {
                mProductRepository = new ProductRepository(getActivity().getApplication());
                sellPrice = Double.valueOf(mProductRepository.getProductPrice(mProductId));
            }
        }


        if (price != null) {
            mPrice = price;
            if(price > 0) {
                txtPrice.getEditText().setText(String.valueOf(price.intValue()));
            }else if (sellPrice > 0) {
                txtPrice.getEditText().setText(String.valueOf(sellPrice.intValue()));
            } else if (buyPrice > 0) {
                txtPrice.getEditText().setText(String.valueOf(buyPrice.intValue()));
            }
        }



        if (qty != null) {
            mQty = qty;
            txtQty.setText(String.valueOf(qty));
        }




        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQty++;
                txtQty.setText(String.valueOf(mQty));
            }
        });

        substractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mQty > 0) {
                    mQty--;
                }
                txtQty.setText(String.valueOf(mQty));
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strQty = txtQty.getText().toString().trim();
                if (strQty.length() > 0) {
                    mQty = Integer.parseInt(strQty);
                }

                if(mQty > mProduct.getQty()){
                    Toast.makeText(mContext, "Stok tidak mencukupi !", Toast.LENGTH_SHORT).show();
                    return;
                }

                String strPrice = txtPrice.getEditText().getText().toString().trim();
                if (strPrice.length() > 0) {
                    mPrice = Double.parseDouble(strPrice);
                }

                if (mQty <= 0) {
                    Toast.makeText(mContext, "Jumlah barang tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                    return;
                } else if (mPrice <= 0) {
                    Toast.makeText(mContext, "Harga barang tidak boleh kosong !", Toast.LENGTH_SHORT).show();
                    return;
                }

                onFinishListener.onFinish(mPosition, mProduct, mQty, mPrice);
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface OnFinishListener {
        void onFinish(Integer position, Product product, Integer qty, Double price);
    }


    public static String TAG = "AddCartItemDialog";
}
