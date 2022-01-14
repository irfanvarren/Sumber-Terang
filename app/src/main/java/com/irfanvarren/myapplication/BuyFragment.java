package com.irfanvarren.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.google.gson.Gson;
import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Database.CartItemRepository;
import com.irfanvarren.myapplication.DialogFragment.AddCartItemDialogFragment;
import com.irfanvarren.myapplication.Model.CartItem;
import com.irfanvarren.myapplication.Model.Product;
import com.irfanvarren.myapplication.Model.ProductAndCategory;
import com.irfanvarren.myapplication.ViewHolder.ProductViewHolder;
import com.irfanvarren.myapplication.ViewModel.CartItemViewModel;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuyFragment extends Fragment implements ProductListAdapter.OnProductListener, AddCartItemDialogFragment.OnFinishListener {
    private ProductViewModel mProductViewModel;
    private LiveData<List<ProductAndCategory>> mProductsList;
    private Integer totalQty;
    private TextView txtTotal;
    private HashMap<Integer, CartItem> cartItems = new HashMap<Integer, CartItem>();
    private AddCartItemDialogFragment.OnFinishListener onFinishListener = this;
    private CartItemViewModel cartItemViewModel;
    private CartItemRepository cartRepository;

    private RecyclerView recyclerView;

    private final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(), this, 1);
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuyFragment newInstance(String param1, String param2) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mProductViewModel != null) {
            mProductsList = mProductViewModel.getAllWithCart();
            mProductsList.observe(getActivity(), products -> {
                if (adapter != null) {
                    adapter.submitList(products);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable("cartItems",cartItems);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        cartRepository = new CartItemRepository(getActivity().getApplication());
        
        if (savedInstanceState != null) {

        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        txtTotal = view.findViewById(R.id.totalQty);
        cartItemViewModel = new ViewModelProvider(requireActivity()).get(CartItemViewModel.class);
        totalQty = cartItemViewModel.getTotalQty();
        txtTotal.setText(String.valueOf(totalQty));

        RelativeLayout checkoutBtn = (RelativeLayout) view.findViewById(R.id.checkoutBtn);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if (s != null) {
                    searchDatabase(s);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s != null) {
                    searchDatabase(s);
                }
                return true;
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalQty > 0) {
                   
                    CartItem[] array = new CartItem[cartItems.size()];
                    cartItems.values().toArray(array);
                    cartRepository.deleteAll();
                    cartRepository.insertAll(array);

                    List<CartItem> cartItemsList = new ArrayList<CartItem>(cartItems.values());
                    MutableLiveData<List<CartItem>> cartItemsLive = new MutableLiveData<List<CartItem>>(cartItemsList);
                    cartItemViewModel.setTotalQty(totalQty);
                    cartItemViewModel.setTotalPrice(sumPriceItem(cartItemsList));
                    cartItemViewModel.setData(cartItemsLive);


                    BuyCheckoutFragment buyCheckoutFragment = new BuyCheckoutFragment();
                    final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.setReorderingAllowed(true);
                    ft.replace(R.id.fragment_container_view, buyCheckoutFragment);
                    ft.addToBackStack("checkout");
                    ft.commit();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Anda belum menambah produk apapun !", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }


    private void searchDatabase(String query) {
        String searchQuery = "%" + query + "%";
        mProductsList = mProductViewModel.searchDatabaseWithCart(searchQuery);
        mProductsList.observe(this, products -> {
            adapter.submitList(products);
        });
    }


    @Override
    public void OnProductClick(int position, ProductAndCategory currentProduct) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Fragment prev = getChildFragmentManager().findFragmentByTag("addCartItemDialog");
        if (prev != null) {
            ft.remove(prev);
            ft.addToBackStack(null);
        } else {
            AddCartItemDialogFragment addCartItemDialogFragment = new AddCartItemDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("productId", currentProduct.product.id);
            bundle.putInt("position", position);
            bundle.putInt("type",1);

            CartItem checkItem = cartItems.get(currentProduct.product.id);
            if (checkItem != null) {
                bundle.putInt("qty", checkItem.getQty());
                bundle.putDouble("price", checkItem.getPrice());
            }

            addCartItemDialogFragment.setArguments(bundle);
            addCartItemDialogFragment.setOnFinishListener(onFinishListener);
            addCartItemDialogFragment.show(ft, "addCartItemDialog");
        }
    }

    @Override
    public void OnAddClick(int position, ProductAndCategory current, View itemView) {
        TextView txtQty = itemView.findViewById(R.id.tvQty);
        int intQty = Integer.parseInt(txtQty.getText().toString());
        intQty++;
        totalQty++;

        if (cartItems.containsKey(current.product.id)) {
            CartItem updateItem = cartItems.get(current.product.id);
            updateItem.setQty(intQty);
            cartItems.put(current.product.id, updateItem);
            cartRepository.update(current.product.id,intQty,updateItem.getPrice());
          
        } else {
            CartItem cartItem = new CartItem(1, current.product);
            cartItem.setQty(intQty);
            cartItems.put(current.product.id, cartItem);
            cartRepository.insert(cartItem);
        }

        txtQty.setText(String.valueOf(intQty));
        txtTotal.setText(String.valueOf(totalQty));
        
    }

    @Override
    public void OnSubstractClick(int position, ProductAndCategory current, View itemView) {
        TextView txtQty = itemView.findViewById(R.id.tvQty);
        int intQty = Integer.parseInt(txtQty.getText().toString());
        if (intQty > 0) {
            intQty--;
            totalQty--;

            if (cartItems.containsKey(current.product.id)) {
                if (intQty == 0) {
                    cartRepository.deleteByProductId(current.product.id);
                    cartItems.remove(current.product.id);
                    
                } else {
                    CartItem updateItem = cartItems.get(current.product.id);
                    updateItem.setQty(intQty);
                    cartItems.put(current.product.id, updateItem);
                    cartRepository.update(current.product.id,intQty,updateItem.getPrice());
                }
            }
            Log.d("CART_ITEMS", new Gson().toJson(cartItems));
            txtQty.setText(String.valueOf(intQty));
            txtTotal.setText(String.valueOf(totalQty));
        }
    }

    @Override
    public void onFinish(Integer position, Product product, Integer qty, Double price) {
        Integer productId = product.id;
        if (cartItems.containsKey(productId)) {
            CartItem updateItem = cartItems.get(productId);
            updateItem.setQty(qty);
            updateItem.setPrice(price);
            cartItems.put(productId, updateItem);
            cartRepository.update(productId,qty,price);
        } else {
            CartItem cartItem = new CartItem(1, product);
            cartItem.setQty(qty);
            cartItem.setPrice(price);
            cartItems.put(productId, cartItem);
            cartRepository.insert(cartItem);
        }
        ProductViewHolder viewHolder = (ProductViewHolder) recyclerView.findViewHolderForAdapterPosition(position);
        View itemView = viewHolder.itemView;
        TextView txtQty = (TextView) itemView.findViewById(R.id.tvQty);
        TextView txtPrice = (TextView) itemView.findViewById(R.id.tvPrice);
        ImageButton addQty = (ImageButton) itemView.findViewById(R.id.addBtn);
        ImageButton substractQty = (ImageButton) itemView.findViewById(R.id.substractBtn);
        txtQty.setTextColor(getResources().getColor(R.color.black));
        txtQty.setBackgroundResource(0);
        txtQty.setText(String.valueOf(qty));

        totalQty = sumTotalItem(cartItems);
        txtTotal.setText(String.valueOf(totalQty));
        txtPrice.setText("Rp. " + String.valueOf(price.intValue()));

        txtPrice.setVisibility(View.VISIBLE);
        addQty.setVisibility(View.VISIBLE);
        substractQty.setVisibility(View.VISIBLE);

    }

    public Integer sumTotalItem(HashMap<Integer, CartItem> cartItems) {
        Integer total = 0;
        if (cartItems.size() > 0) {
            for (Map.Entry<Integer, CartItem> e : cartItems.entrySet()) {
                total += e.getValue().getQty();
            }
        }
        return total;
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
}
