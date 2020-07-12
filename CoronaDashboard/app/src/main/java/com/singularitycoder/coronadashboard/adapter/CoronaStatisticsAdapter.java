package com.singularitycoder.coronadashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.singularitycoder.coronadashboard.R;
import com.singularitycoder.coronadashboard.databinding.ItemCoronaStatisticBinding;
import com.singularitycoder.coronadashboard.model.CoronaStatisticItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CoronaStatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final String TAG = "CoronaStatisticsAdapter";

    @NonNull
    private List<CoronaStatisticItem> statisticList = Collections.emptyList();

    @Nullable
    private Context context;

    public CoronaStatisticsAdapter(List<CoronaStatisticItem> statisticList, Context context) {
        this.statisticList = statisticList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_corona_statistic, parent, false);
        return new CoronaStatisticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CoronaStatisticItem coronaStatisticItem = statisticList.get(position);
        if (null != holder) {
            CoronaStatisticViewHolder coronaStatisticViewHolder = (CoronaStatisticViewHolder) holder;
            coronaStatisticViewHolder.binding.tvStatisticName.setText(coronaStatisticItem.getStatisticName());
            coronaStatisticViewHolder.binding.tvStatisticValue.setText(coronaStatisticItem.getStatisticValue());

            if (position == 1) {
                coronaStatisticViewHolder.binding.barChart.setVisibility(View.VISIBLE);
                showChart(coronaStatisticViewHolder);
            }

            if (position == 2) {
                coronaStatisticViewHolder.binding.barChart.setVisibility(View.VISIBLE);
                showChart(coronaStatisticViewHolder);
            }
        }
    }

    private void showChart(CoronaStatisticViewHolder coronaStatisticViewHolder) {
        // Y axis
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44f, 0));
        barEntries.add(new BarEntry(88f, 1));
        barEntries.add(new BarEntry(66f, 2));
        barEntries.add(new BarEntry(12f, 3));
        barEntries.add(new BarEntry(44f, 4));

        // X axis
        List<String> dates = new ArrayList<>();
        dates.add("April");
        dates.add("May");
        dates.add("June");
        dates.add("July");
        dates.add("Aug");

        BarDataSet barDataSet = new BarDataSet(barEntries, "Values");
        BarData barData = new BarData(barDataSet);
        coronaStatisticViewHolder.binding.barChart.setData(barData);
        coronaStatisticViewHolder.binding.barChart.setTouchEnabled(false);
        coronaStatisticViewHolder.binding.barChart.setDragEnabled(false);
        coronaStatisticViewHolder.binding.barChart.setScaleEnabled(false);
        coronaStatisticViewHolder.binding.barChart.setDrawGridBackground(false);
        coronaStatisticViewHolder.binding.barChart.setGridBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public int getItemCount() {
        return statisticList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void filterList(List<CoronaStatisticItem> list) {
        this.statisticList = list;
        notifyDataSetChanged();
    }

    class CoronaStatisticViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private ItemCoronaStatisticBinding binding;

        CoronaStatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCoronaStatisticBinding.bind(itemView);
        }
    }
}