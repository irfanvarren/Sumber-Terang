package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.PurchaseItemListAdapter;
import com.irfanvarren.myapplication.DialogFragment.AddCartItemDialogFragment;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BuyCheckoutActivity extends AppCompatActivity implements PurchaseItemListAdapter.CartItemListener, AddCartItemDialogFragment.OnFinishListener {
    private HashMap<Integer, CartItem> cartItems;
    private RecyclerView recyclerView;
    private PurchaseItemListAdapter recyclerViewAdapter;
    private MutableLiveData<List<CartItem>> cartItemsLive;
    private List<CartItem> cartItemsList;
    private CartItemViewModel cartItemViewModel;
    private TextView txtTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_checkout);

        RelativeLayout checkoutBtn = (RelativeLayout) findViewById(R.id.checkoutBtn);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);


        txtTotalPrice = (TextView) findViewById(R.id.txtTotal);
        if (getIntent().getExtras() != null) {
            cartItems = (HashMap<Integer, CartItem>) getIntent().getSerializableExtra("cartItems");
            if (!cartItems.isEmpty()) {
                cartItemsLive = new MutableLiveData<List<CartItem>>();
                cartItemsList = new ArrayList<CartItem>(cartItems.values());
                cartItemsLive.setValue(cartItemsList);

                cartItemViewModel = new ViewModelProvider(this).get(CartItemViewModel.class);
                cartItemViewModel.setData(cartItemsLive);
                cartItemViewModel.getData().observe(this, purchaseItemListObserver);
                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in","ID"));
                txtTotalPrice.setText("Rp. "+nf.format(sumPriceItem(cartItemsList)));
            }
        }

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyCheckoutActivity.this, PaymentActivity.class);
                intent.putExtra("cartItems", (Serializable) cartItemsLive.getValue());
                intent.putExtra("transactionType",1);
                startActivity(intent);
            }
        });
    }

    Observer<List<CartItem>> purchaseItemListObserver = new Observer<List<CartItem>>() {
        @Override
        public void onChanged(List<CartItem> cartItems) {
//            recyclerViewAdapter = new PurchaseItemListAdapter(BuyCheckoutActivity.this);
//            recyclerViewAdapter.setContext(getApplicationContext());
//            recyclerViewAdapter.setDataSet(cartItems);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setAdapter(recyclerViewAdapter);
        }
    };

    @Override
    public void CartItemClick(Integer position, CartItem cartItem) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("addCartItemDialog");
        if (prev != null) {
            ft.remove(prev);
            ft.addToBackStack(null);
        } else {
            AddCartItemDialogFragment addCartItemDialogFragment = new AddCartItemDialogFragment();
            Bundle bundle = new Bundle();

            bundle.putInt("qty",cartItem.getQty());
            bundle.putDouble("price",cartItem.getPrice());

            addCartItemDialogFragment.setArguments(bundle);
            addCartItemDialogFragment.show(ft, "addCartItemDialog");
        }
    }

    public Double sumPriceItem(List<CartItem> cartItems){
        Double price = new Double(0);
        if(cartItems.size() > 0){
           for(int i = 0; i < cartItems.size(); i++){
               price += (cartItems.get(i).getQty() * cartItems.get(i).getPrice());
           }
        }
        return price;
    }

    @Override
    public void onFinish(Integer position, Product product, Integer qty, Double price) {
        CartItem updateItem = cartItemsList.get(position);
        updateItem.setQty(qty);
        updateItem.setPrice(price);
        cartItemsList.set(position,updateItem);
        cartItemsLive.postValue(cartItemsList);
        cartItemViewModel.setData(cartItemsLive);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
        txtTotalPrice.setText(nf.format(sumPriceItem(cartItemsList)));
    }
}