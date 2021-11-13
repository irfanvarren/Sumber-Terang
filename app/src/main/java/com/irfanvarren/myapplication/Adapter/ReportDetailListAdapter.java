package com.irfanvarren.myapplication.Adapter;

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
import com.irfanvarren.myapplication.Model.ReportDetail;
import com.irfanvarren.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;  
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class ReportDetailListAdapter extends ListAdapter<ReportDetail,ReportDetailListAdapter.ViewHolder> {
    private Context mContext;
    private OnReportDetailListener mReportDetailListener;
    
    public ReportDetailListAdapter(DiffUtil.ItemCallback<ReportDetail> diffCallback, OnReportDetailListener reportDetailListener) {
        super(diffCallback);
        this.mReportDetailListener = reportDetailListener;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context mContext;
        private TextView txtTotal,txtTransactionDate,txtType;
        private View rootView;
        private ReportDetail mReportDetail;
        private OnReportDetailListener mReportDetailListener;
        public ViewHolder(View view,OnReportDetailListener reportDetailListener){
            super(view);
            mReportDetailListener = reportDetailListener;
            mContext = view.getContext();
            txtTotal = view.findViewById(R.id.total);
            txtTransactionDate = view.findViewById(R.id.transactionDate);
            txtType = view.findViewById(R.id.type);
            rootView = view;
        }
        public void bind(ReportDetail reportDetail){
            mReportDetail = reportDetail;
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            Log.d("REPORT_DETAIL", new Gson().toJson(reportDetail));

            if(reportDetail.getPurchaseId() != 0){
                txtType.setText("Pembelian");
            }else if(reportDetail.getOrderId() != 0){
                txtType.setText("Penjualan");
            }

            Double total = reportDetail.getTotal();
            if(total != null){
                txtTotal.setText(nf.format(total));
            }

            Date transaction_date = reportDetail.getTransactionDate();
            if(transaction_date != null){
                txtTransactionDate.setText(dateFormat.format(transaction_date));
            }
            
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mReportDetailListener.ReportDetailClick(getAdapterPosition(),mReportDetail);
                }
            });
        }
        
        @Override
        public void onClick(View view) {
            mReportDetailListener.ReportDetailClick(getAdapterPosition(),mReportDetail);
        }
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_details_list,parent,false);
        return new ViewHolder(view,mReportDetailListener);
    }
    
    @Override
    public void onBindViewHolder(ReportDetailListAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
    
    public static class ReportDetailDiff extends DiffUtil.ItemCallback<ReportDetail> {
        
        @Override
        public boolean areItemsTheSame(@NonNull ReportDetail oldItem, @NonNull ReportDetail newItem) {
            return oldItem == newItem;
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull ReportDetail oldItem, @NonNull ReportDetail newItem) {
            return oldItem.getPurchaseId() == newItem.getPurchaseId() && oldItem.getOrderId() == newItem.getOrderId() && oldItem.getTransactionDate() == newItem.getTransactionDate();
        }
    }
    
    
    public interface OnReportDetailListener{
        public void ReportDetailClick(Integer position,ReportDetail reportDetail);
    }
    
}
