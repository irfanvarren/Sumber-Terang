package com.irfanvarren.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.irfanvarren.myapplication.Adapter.ProductListAdapter;
import com.irfanvarren.myapplication.Config.FragmentConfig;
import com.irfanvarren.myapplication.ViewModel.ProductViewModel;
import com.irfanvarren.myapplication.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;
    private ProductViewModel mProductViewModel;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        //final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff());
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        //mProductViewModel.getAll().observe(getViewLifecycleOwner(), products -> {
        //    adapter.submitList(products);
        //});
        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       /* binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}