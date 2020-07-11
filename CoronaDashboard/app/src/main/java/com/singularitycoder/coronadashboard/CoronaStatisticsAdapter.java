package com.singularitycoder.coronadashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding3.view.RxView;
import com.singularitycoder.coronadashboard.databinding.ItemCoronaStatisticBinding;

import java.util.Collections;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public final class CoronaStatisticsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final String TAG = "CoronaStatisticsAdapter";

    @NonNull
    private List<CoronaStatisticItem> statisticList = Collections.emptyList();

    @Nullable
    private Context context;

    @Nullable
    private StatisticViewListener statisticViewListener;

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
        }
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

    public interface StatisticViewListener {
        void onStatisticItemClicked(int position);
    }

    public final void setStatisticViewListener(StatisticViewListener statisticViewListener) {
        this.statisticViewListener = statisticViewListener;
    }

    class CoronaStatisticViewHolder extends RecyclerView.ViewHolder {

        @Nullable
        private ItemCoronaStatisticBinding binding;

        @NonNull
        private final CompositeDisposable compositeDisposable = new CompositeDisposable();

        CoronaStatisticViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCoronaStatisticBinding.bind(itemView);

            compositeDisposable.add(
                    RxView.clicks(itemView)
                            .map(o -> itemView)
                            .subscribe(
                                    button -> statisticViewListener.onStatisticItemClicked(getAdapterPosition()),
                                    throwable -> Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show()
                            )
            );
        }
    }
}