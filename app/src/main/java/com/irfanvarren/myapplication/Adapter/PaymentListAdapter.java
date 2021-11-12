package com.irfanvarren.myapplication.Adapter;

import android.app.Application;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;





import com.google.gson.Gson;

import com.irfanvarren.myapplication.Database.DistributorRepository;
import com.irfanvarren.myapplication.Model.Distributor;
import com.irfanvarren.myapplication.Model.Payment;
import com.irfanvarren.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;  
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentListAdapter extends ListAdapter<Payment,PaymentListAdapter.ViewHolder> {
    private Context mContext;
    private OnPaymentListener mPaymentListener;
    private Application mApplication;


    public PaymentListAdapter(DiffUtil.ItemCallback<Payment> diffCallback, OnPaymentListener paymentListener, Application application) {
        super(diffCallback);
        this.mPaymentListener = paymentListener;
        this.mApplication = application;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context mContext;
        private View rootView;
        private Payment mPayment;
        private TextView txtAmount,txtPaymentDate;
        private OnPaymentListener mPaymentListener;
        private Application mApplication;
        public ViewHolder(View view,OnPaymentListener paymentListener, Application application){
            super(view);
            mPaymentListener = paymentListener;
            mContext = view.getContext();
            mApplication = application;
            rootView = view;
            txtAmount = view.findViewById(R.id.amount);
            txtPaymentDate = view.findViewById(R.id.paymentDate);
        }
        public void bind(Payment payment){
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));

            Double amount = payment.getAmount();
            if(amount == null){
                amount = 0.0;
            }

            txtAmount.setText("Rp. "+nf.format(amount));

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
            txtPaymentDate.setText("JT: "+dateFormat.format(payment.getPaymentDate()));

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
        
                    mPaymentListener.PaymentClick(getAdapterPosition(),mPayment);
                }
            });
        }

        @Override
        public void onClick(View view) {
        mPaymentListener.PaymentClick(getAdapterPosition(),mPayment);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payments_list,parent,false);
        return new ViewHolder(view,mPaymentListener, mApplication);
    }

    @Override
    public void onBindViewHolder(PaymentListAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class PaymentDiff extends DiffUtil.ItemCallback<Payment> {

        @Override
        public boolean areItemsTheSame(@NonNull Payment oldItem, @NonNull Payment newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Payment oldItem, @NonNull Payment newItem) {
            return oldItem.getId() == newItem.getId();
        }
    }


    public interface OnPaymentListener{
        public void PaymentClick(Integer position,Payment payment);
    }

}
