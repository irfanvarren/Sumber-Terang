package com.irfanvarren.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.gson.Gson;

import com.irfanvarren.myapplication.Adapter.PurchaseItemListAdapter;
import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.DialogFragment.AddCartItemDialogFragment;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyCheckoutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyCheckoutFragment extends Fragment implements PurchaseItemListAdapter.CartItemListener, AddCartItemDialogFragment.OnFinishListener {
    private HashMap<Integer, CartItem> cartItems;
    private RecyclerView recyclerView;
    private final PurchaseItemListAdapter recyclerViewAdapter = new PurchaseItemListAdapter(new PurchaseItemListAdapter.ItemDiff(),this);;
    private MutableLiveData<List<CartItem>> cartItemsLive;
    private List<CartItem> cartItemsList;
    private CartItemViewModel cartItemViewModel;
    private TextView txtTotalPrice;
    private AddCartItemDialogFragment.OnFinishListener onFinishListener = this;
    private Double totalPrice;
    private Integer totalQty;


    private static final String cartItemsArg = "cartItems";

    public BuyCheckoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuyCheckoutFragment.
     */
    public static BuyCheckoutFragment newInstance() {
        BuyCheckoutFragment fragment = new BuyCheckoutFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartItemViewModel = new ViewModelProvider(requireActivity()).get(CartItemViewModel.class);
        cartItemViewModel.getData().observe(this, items -> {
            Log.d("CHECKOUT_ITEM",new Gson().toJson(items));
            recyclerViewAdapter.submitList(items);
        });
        cartItemsLive = cartItemViewModel.getData();
        cartItemsList = cartItemsLive.getValue();
        totalPrice = cartItemViewModel.getTotalPrice();
        totalQty = cartItemViewModel.getTotalQty();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_checkout, container, false);
        RelativeLayout checkoutBtn = (RelativeLayout) view.findViewById(R.id.checkoutBtn);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(recyclerViewAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        txtTotalPrice = (TextView) view.findViewById(R.id.txtTotal);

        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
        txtTotalPrice.setText("Rp. " + nf.format(totalPrice));

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PaymentFragment buyFragment = new PaymentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cartItems", (Serializable) cartItemsLive.getValue());
                bundle.putInt("transactionType", 1);
                buyFragment.setArguments(bundle);

                final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setReorderingAllowed(true);
                ft.replace(R.id.fragment_container_view, buyFragment);
                ft.addToBackStack("payment");
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void CartItemClick(Integer position,CartItem cartItem) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("addCartItemDialog");
        if (prev != null) {
            ft.remove(prev);
            ft.addToBackStack(null);
        } else {
            AddCartItemDialogFragment addCartItemDialogFragment = new AddCartItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("productId",cartItem.getProductId());
            bundle.putInt("position",position);
            bundle.putInt("qty", cartItem.getQty());
            bundle.putDouble("price", cartItem.getPrice());
            bundle.putInt("type",1);

            addCartItemDialogFragment.setArguments(bundle);
            addCartItemDialogFragment.setOnFinishListener(onFinishListener);
            addCartItemDialogFragment.show(ft, "addCartItemDialog");
        }
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
    public void onFinish(Integer position, Product product, Integer qty, Double price) {
        CartItem updateItem = cartItemsList.get(position);

        int prevQty = updateItem.getQty();
        Double prevPrice = updateItem.getPrice();

        if (prevQty != qty || prevPrice != price) {
            Double prevSubTotal = prevQty * prevPrice;
            Double subTotal = qty * price;

            if (qty > prevQty) {
                totalQty = totalQty + (qty - prevQty);
            } else if (qty < prevQty) {
                totalQty = totalQty - (qty - prevQty);
            }

            totalPrice = (totalPrice - prevSubTotal) + subTotal;

            updateItem.setQty(qty);
            updateItem.setPrice(price);

            cartItemsList.set(position, updateItem);
            cartItemsLive.postValue(cartItemsList);
            cartItemViewModel.setData(cartItemsLive);
            cartItemViewModel.setTotalPrice(totalPrice);
            cartItemViewModel.setTotalQty(totalQty);
            recyclerViewAdapter.notifyDataSetChanged();

            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
            txtTotalPrice.setText("Rp. " + nf.format(totalPrice));

            CartItemRepository cartItemRepository = new CartItemRepository(getActivity().getApplication());
            cartItemRepository.update(updateItem.getProductId(), updateItem.getQty(), updateItem.getPrice());
        }

    }
}