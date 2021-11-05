package com.irfanvarren.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;


public class SellActivity extends AppCompatActivity {
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
                    .add(R.id.fragment_container_view, SellFragment.class, null)
                    .commit();
        }
        setContentView(R.layout.activity_sell);
    }

    @Override
    protected void onDestroy() {
        CartItemRepository mRepository = new CartItemRepository(getApplication());
        mRepository.deleteAll();
        super.onDestroy();

    }
}