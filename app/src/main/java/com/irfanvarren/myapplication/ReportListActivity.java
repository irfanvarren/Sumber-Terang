package com.irfanvarren.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.irfanvarren.myapplication.Adapter.ReportDetailListAdapter;
import com.irfanvarren.myapplication.Model.Report;
import com.irfanvarren.myapplication.Model.ReportDetail;
import com.irfanvarren.myapplication.ViewModel.ReportViewModel;

import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class ReportListActivity extends AppCompatActivity implements ReportDetailListAdapter.OnReportDetailListener {
    
    private ReportViewModel mReportViewModel;
    private LiveData<List<ReportDetail>> mReportDetailList;
    private final ReportDetailListAdapter adapter = new ReportDetailListAdapter(new ReportDetailListAdapter.ReportDetailDiff(), this);
    private Date transactionDate;
    private Report report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        
        if(getIntent().getExtras() != null){
            report = (Report) getIntent().getSerializableExtra("report");
            transactionDate = report.getTransactionDate();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setItemAnimator(null);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        mReportViewModel = new ViewModelProvider(this).get(ReportViewModel.class);
        if(transactionDate != null){
        mReportDetailList = mReportViewModel.getReportDetail(transactionDate);
        mReportDetailList.observe(this, report -> {
            adapter.submitList(report);
        });
    }

    }

    @Override
    public void ReportDetailClick(Integer position,ReportDetail reportDetail){
        Intent intent = new Intent(ReportListActivity.this, ReportDetailActivity.class);
        Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
    
}