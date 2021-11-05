package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.irfanvarren.myapplication.Adapter.InventoryListAdapter;
import com.irfanvarren.myapplication.Database.InventoryRepository;
import com.irfanvarren.myapplication.DialogFragment.AddStockDialogFragment;
import com.irfanvarren.myapplication.Model.Category;
import com.irfanvarren.myapplication.Model.Inventory;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.InventoryViewModel;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class StockManagementViewActivity extends AppCompatActivity implements InventoryListAdapter.OnInventoryListener{
    private InventoryViewModel mInventoryViewModel;
    private LiveData<List<Inventory>> mInventorysList;
    private final InventoryListAdapter adapter = new InventoryListAdapter(new InventoryListAdapter.InventoryDiff(),this);
    private Product mProduct;
    private Category mCategory;
    private LiveData<Double> mTotalAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management_view);

        LinearLayout addBtn = (LinearLayout) findViewById(R.id.addBtn);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        TextView txtProductName = (TextView) findViewById(R.id.productName);
        TextView txtProductCode = (TextView) findViewById(R.id.productCode);
        TextView txtRemainingStock = (TextView) findViewById(R.id.remainingStock);
        TextView txtTotal = (TextView) findViewById(R.id.totalAsset);



        if(getIntent().getExtras() != null) {
            mProduct = (Product) getIntent().getSerializableExtra("product");
            mCategory = (Category) getIntent().getSerializableExtra("category");
            if (mProduct != null) {
                String strProductName = mProduct.getName();
                if(!TextUtils.isEmpty(strProductName)){
                    txtProductName.setText(strProductName);
                }

                String strProductCode = mProduct.getCode();
                if(!TextUtils.isEmpty(strProductCode)){
                    txtProductCode.setText(strProductCode);
                }

                String strRemainingStock = String.valueOf(mProduct.getQty());
                if(!TextUtils.isEmpty(strRemainingStock)){
                    txtRemainingStock.setText(strRemainingStock);
                }
               mInventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
                mInventorysList = mInventoryViewModel.getByProductId(mProduct.getId());
                mInventorysList.observe(this, inventory -> {
                    adapter.submitList(inventory);
                });

                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));

                mTotalAsset = mInventoryViewModel.getTotalAsset(mProduct.getId());
                mTotalAsset.observe(this,totalAsset -> {

                    txtTotal.setText("Rp. "+nf.format(totalAsset));
                });


            }
        }


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment prev = getSupportFragmentManager().findFragmentByTag("AddStockDialog");
                if(prev != null){
                    ft.remove(prev);
                    ft.addToBackStack(null);
                }else{
                    AddStockDialogFragment addStockDialogFragment = new AddStockDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product",mProduct);
                    addStockDialogFragment.setArguments(bundle);
                    addStockDialogFragment.show(ft,"AddStockDialog");
                }

            }
        });
    }


    @Override
    public void OnEditClick(int position, Inventory current) {
        FragmentManager fm = getSupportFragmentManager();
        AddStockDialogFragment addStockDialogFragment = new AddStockDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product",mProduct);
        bundle.putSerializable("inventory",current);
        addStockDialogFragment.setArguments(bundle);
        addStockDialogFragment.show(fm,"AddStockDialog");
    }

    @Override
    public void OnDeleteClick(int position, Inventory current) {
        InventoryRepository iRepository = new InventoryRepository(getApplication());
        iRepository.delete(current);
        Toast.makeText(getApplicationContext(),"Stok berhasil dihapus",Toast.LENGTH_LONG).show();
    }
}