package com.irfanvarren.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.Database.PurchaseRepository;
import com.irfanvarren.myapplication.DialogFragment.AddCartItemDialogFragment;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.Model.PurchaseDetail;
import com.irfanvarren.myapplication.ViewHolder.ProductViewHolder;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyActivity extends AppCompatActivity{
    private CartItemViewModel mCartItemViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            CartItemRepository mRepository = new CartItemRepository(getApplication());
            mRepository.deleteAll();

            mCartItemViewModel = new ViewModelProvider(this).get(CartItemViewModel.class);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, BuyFragment.class, null)
                    .commit();
        }
        setContentView(R.layout.activity_buy);
    }

    @Override
    protected void onDestroy() {
        CartItemRepository mRepository = new CartItemRepository(getApplication());
        mRepository.deleteAll();
        super.onDestroy();

    }
}