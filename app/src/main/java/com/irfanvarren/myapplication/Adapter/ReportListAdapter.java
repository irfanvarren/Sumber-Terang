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
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;  
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReportListAdapter extends ListAdapter<Report,ReportListAdapter.ViewHolder> {
    private Context mContext;
    private OnReportListener mReportListener;
    
    public ReportListAdapter(DiffUtil.ItemCallback<Report> diffCallback, OnReportListener reportListener) {
        super(diffCallback);
        this.mReportListener = reportListener;
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context mContext;
        private TextView txtTotalSell,txtTotalPurchase,txtTransactionDate,txtTransactionMonth,txtTransactionYear;
        private View rootView;
        private Report mReport;
        private OnReportListener mReportListener;
        public ViewHolder(View view,OnReportListener reportListener){
            super(view);
            mReportListener = reportListener;
            mContext = view.getContext();
            txtTotalSell = view.findViewById(R.id.totalSell);
            txtTotalPurchase = view.findViewById(R.id.totalPurchase);
            txtTransactionDate = view.findViewById(R.id.transactionDate);
            txtTransactionMonth = view.findViewById(R.id.transactionMonth);
            txtTransactionYear = view.findViewById(R.id.transactionYear);
            rootView = view;
        }
        public void bind(Report report){
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("in", "ID"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM"); 
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy"); 
            
            txtTransactionDate.setText(dateFormat.format(report.getTransactionDate()));
            txtTransactionMonth.setText(monthFormat.format(report.getTransactionDate()));
            txtTransactionYear.setText(yearFormat.format(report.getTransactionDate()));
            
            mReport = report;
            Double totalSell = (Double) report.getTotalSell();
            if(totalSell != null){
                txtTotalSell.setText(nf.format(totalSell));
            }
            
            Double totalPurchase = (Double) report.getTotalPurchase();
            if(totalPurchase != null){
                txtTotalPurchase.setText(nf.format(totalPurchase));
            }
            
            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mReportListener.ReportClick(getAdapterPosition(),mReport);
                }
            });
        }
        
        @Override
        public void onClick(View view) {
            mReportListener.ReportClick(getAdapterPosition(),mReport);
        }
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reports_list,parent,false);
        return new ViewHolder(view,mReportListener);
    }
    
    @Override
    public void onBindViewHolder(ReportListAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }
    
    public static class ReportDiff extends DiffUtil.ItemCallback<Report> {
        
        @Override
        public boolean areItemsTheSame(@NonNull Report oldItem, @NonNull Report newItem) {
            return oldItem == newItem;
        }
        
        @Override
        public boolean areContentsTheSame(@NonNull Report oldItem, @NonNull Report newItem) {
            return oldItem.getTotalSell() == newItem.getTotalSell() && oldItem.getTotalPurchase() == newItem.getTotalPurchase() && oldItem.getTransactionDate() == newItem.getTransactionDate();
        }
    }
    
    
    public interface OnReportListener{
        public void ReportClick(Integer position,Report report);
    }
    
}
