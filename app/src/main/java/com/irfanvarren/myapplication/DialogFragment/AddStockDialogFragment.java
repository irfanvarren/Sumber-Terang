package com.irfanvarren.myapplication.DialogFragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.Model.DateConverter;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddStockDialogFragment extends DialogFragment {
    private Product mProduct;
    private Inventory mInventory;
    private MaterialDatePicker mDatePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").build();
    private Long selectedDate;

    private int qty = 0;
    private double price = 0;
    private String note = null;
    private String cmd = "add";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_stock, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getChildFragmentManager();

        Bundle bundle = getArguments();
        mProduct = (Product) bundle.getSerializable("product");
        if (bundle.getSerializable("inventory") != null) {
            mInventory = (Inventory) bundle.getSerializable("inventory");
        }

        TextInputLayout dateInputLayout = (TextInputLayout) view.findViewById(R.id.date);
        EditText dateInput = dateInputLayout.getEditText();
        TextInputLayout txtQty = (TextInputLayout) view.findViewById(R.id.qty);
        TextInputLayout txtPrice = (TextInputLayout) view.findViewById(R.id.price);
        TextInputLayout txtNote = (TextInputLayout) view.findViewById(R.id.note);
        TextView txtMessage = (TextView) view.findViewById(R.id.message);
        Button addBtn = (Button) view.findViewById(R.id.addBtn);
        Button cancelBtn = (Button) view.findViewById(R.id.cancelBtn);


        if (mInventory != null) {
            qty = mInventory.getQty();
            price = mInventory.getPrice();
            note = mInventory.getNote();

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
            dateInput.setText(df.format(mInventory.getCreatedAt()));
            txtQty.getEditText().setText(String.valueOf(mInventory.getQty()));
            txtPrice.getEditText().setText(String.valueOf(mInventory.getPrice()));
            txtNote.getEditText().setText(mInventory.getNote());

            addBtn.setText("Ubah");
            cmd = "update";
        }


        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        mDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                selectedDate = Long.parseLong(selection.toString());
                DateConverter dateConverter = new DateConverter(selectedDate);
                Date date = dateConverter.getDate();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
                dateInput.setText(df.format(date));
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {;
                if (txtQty.getEditText().getText().toString().trim().length() > 0) {
                    qty = Integer.parseInt(txtQty.getEditText().getText().toString());
                }

                if (txtPrice.getEditText().getText().toString().trim().length() > 0) {
                    price = Double.parseDouble(txtPrice.getEditText().getText().toString());
                }


                if (txtNote.getEditText().getText().toString().trim().length() > 0) {
                    note = txtNote.getEditText().getText().toString();
                }

                if (cmd == "add") {
                    Inventory insertInventory = new Inventory(mProduct.id, 0, "", "in", "add-stock", mProduct.name, qty, price, note);

                    if (selectedDate != null) {
                        DateConverter dateConverter = new DateConverter(selectedDate);
                        insertInventory.setCreatedAt(dateConverter.getDate());
                    }
                    InventoryRepository iRepository = new InventoryRepository(getActivity().getApplication());
                    iRepository.insert(insertInventory);
                    dismiss();
                    Toast.makeText(getContext(), "Stok berhasil ditambah", Toast.LENGTH_LONG).show();
                } else if (cmd == "update") {
                    mInventory.setQty(qty);
                    mInventory.setPrice(price);
                    mInventory.setNote(note);
                    if (selectedDate != null) {
                        DateConverter dateConverter = new DateConverter(selectedDate);
                        mInventory.setCreatedAt(dateConverter.getDate());
                    }
                    InventoryRepository iRepository = new InventoryRepository(getActivity().getApplication());
                    iRepository.update(mInventory);
                    dismiss();
                    Toast.makeText(getContext(), "Stok berhasil diupdate", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public static String TAG = "AddStockDialog";
}
