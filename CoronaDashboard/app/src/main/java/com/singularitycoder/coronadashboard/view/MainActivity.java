package com.singularitycoder.coronadashboard.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.test.espresso.IdlingResource;

import com.singularitycoder.coronadashboard.R;
import com.singularitycoder.coronadashboard.adapter.CoronaStatisticsAdapter;
import com.singularitycoder.coronadashboard.databinding.ActivityMainBinding;
import com.singularitycoder.coronadashboard.helper.ApiIdlingResource;
import com.singularitycoder.coronadashboard.helper.AppUtils;
import com.singularitycoder.coronadashboard.helper.StateMediator;
import com.singularitycoder.coronadashboard.helper.UiState;
import com.singularitycoder.coronadashboard.model.CoronaResponse;
import com.singularitycoder.coronadashboard.model.CoronaStatisticItem;
import com.singularitycoder.coronadashboard.viewmodel.CoronaStatisticsViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

import static java.lang.String.valueOf;

public final class MainActivity extends AppCompatActivity {

    @NonNull
    private final String TAG = "MainActivity";

    @NonNull
    private final List<CoronaStatisticItem> coronaStatisticList = new ArrayList<>();

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    private CoronaStatisticsViewModel coronaStatisticsViewModel;

    @Nullable
    private ApiIdlingResource idlingResource;

    @Nullable
    private ProgressDialog progressDialog;

    @Nullable
    private CoronaStatisticsAdapter coronaStatisticsAdapter;

    @NonNull
    private AppUtils appUtils = AppUtils.getInstance();

    @Nullable
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appUtils.setStatusBarColor(this, R.color.colorPrimary);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialise();
        setUpToolBar();
        setUpRecyclerView();
        getCoronaData();
        binding.swipeRefreshLayout.setOnRefreshListener(this::getCoronaData);
        Log.d(TAG, "onCreate: " + Math.ceil(7760279314L));
        Log.d(TAG, "onCreate: " + Math.round(7760279314L));
        Log.d(TAG, "onCreate: " + (double) Math.round(7760279314L));
        Log.d(TAG, "onCreate: " + Math.round(Math.floor(7760279314L)));
        Log.d(TAG, "onCreate: " + Integer.MIN_VALUE);
        Log.d(TAG, "onCreate: " + Integer.MAX_VALUE);
        Log.d(TAG, "onCreate: " + "HelloWorld".length());
    }

    private void initialise() {
        progressDialog = new ProgressDialog(this);
        coronaStatisticsViewModel = new ViewModelProvider(this).get(CoronaStatisticsViewModel.class);
    }

    private void setUpToolBar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
    }

    private void setUpRecyclerView() {
        final RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerNews.setLayoutManager(layoutManager);
        coronaStatisticsAdapter = new CoronaStatisticsAdapter(coronaStatisticList, this);
        binding.recyclerNews.setAdapter(coronaStatisticsAdapter);
        binding.recyclerNews.setItemAnimator(new DefaultItemAnimator());
    }

    private void getCoronaData() {
        if (appUtils.hasInternet(this)) showOnlineState();
        else showOfflineState();
    }

    private void showOnlineState() {
        coronaStatisticsViewModel.getCoronaStatisticsFromRepository(
                "v3",
                "covid-19",
                "all",
                idlingResource
        ).observe(MainActivity.this, liveDataObserver());
    }

    private void showOfflineState() {
        binding.tvNoInternet.setVisibility(View.VISIBLE);
        binding.swipeRefreshLayout.setRefreshing(false);
        coronaStatisticList.clear();
    }

    private void showLoading() {
        if (null != progressDialog && !progressDialog.isShowing()) progressDialog.show();
    }

    private void hideLoading() {
        if (null != progressDialog && progressDialog.isShowing()) progressDialog.dismiss();
    }

    private Observer<StateMediator<Object, UiState, String, String>> liveDataObserver() {
        Observer<StateMediator<Object, UiState, String, String>> observer = null;
        observer = stateMediator -> {

            if (UiState.LOADING == stateMediator.getStatus()) showLoadingState(stateMediator);

            if (UiState.SUCCESS == stateMediator.getStatus()) showSuccessState(stateMediator);

            if (UiState.EMPTY == stateMediator.getStatus()) showEmptyState(stateMediator);

            if (UiState.ERROR == stateMediator.getStatus()) showErrorState(stateMediator);
        };
        return observer;
    }

    private void showLoadingState(StateMediator<Object, UiState, String, String> stateMediator) {
        runOnUiThread(() -> {
            progressDialog.setMessage(valueOf(stateMediator.getMessage()));
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            showLoading();
        });
    }

    private void showSuccessState(StateMediator<Object, UiState, String, String> stateMediator) {
        runOnUiThread(() -> {
            if (("STATISTICS").equals(stateMediator.getKey())) {
                coronaStatisticList.clear();
                final CoronaResponse coronaResponse = (CoronaResponse) stateMediator.getData();

                // Add to Room DB
                coronaStatisticsViewModel.insertIntoRoomDbFromRepository(coronaResponse);

                coronaStatisticList.add(new CoronaStatisticItem("Updated On", valueOf(appUtils.getDateTime(new Date((long) coronaResponse.getUpdated())))));
                coronaStatisticList.add(new CoronaStatisticItem("Cases", roundOffNumber(valueOf((int) coronaResponse.getCases()))));
                coronaStatisticList.add(new CoronaStatisticItem("Cases Today", roundOffNumber(valueOf((int) coronaResponse.getTodayCases()))));
                coronaStatisticList.add(new CoronaStatisticItem("Deaths", roundOffNumber(valueOf((int) coronaResponse.getDeaths()))));
                coronaStatisticList.add(new CoronaStatisticItem("Deaths Today", roundOffNumber(valueOf((int) coronaResponse.getTodayDeaths()))));
                coronaStatisticList.add(new CoronaStatisticItem("Recovered", roundOffNumber(valueOf((int) coronaResponse.getRecovered()))));
                coronaStatisticList.add(new CoronaStatisticItem("Recovered Today", roundOffNumber(valueOf((int) coronaResponse.getTodayRecovered()))));
                coronaStatisticList.add(new CoronaStatisticItem("Active", roundOffNumber(valueOf((int) coronaResponse.getActive()))));
                coronaStatisticList.add(new CoronaStatisticItem("Critical", roundOffNumber(valueOf((int) coronaResponse.getCritical()))));
                coronaStatisticList.add(new CoronaStatisticItem("Cases Per One Million", roundOffNumber(valueOf((int) coronaResponse.getCasesPerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Deaths Per One Million", roundOffNumber(valueOf((int) coronaResponse.getDeathsPerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Total Tests", roundOffNumber(valueOf((int) coronaResponse.getTests()))));
                coronaStatisticList.add(new CoronaStatisticItem("Tests Per One Million", roundOffNumber(valueOf((int) coronaResponse.getTestsPerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Total Population", roundOffNumber(valueOf(Math.round(coronaResponse.getPopulation())))));
                coronaStatisticList.add(new CoronaStatisticItem("One Case Per People", roundOffNumber(valueOf((int) coronaResponse.getOneCasePerPeople()))));
                coronaStatisticList.add(new CoronaStatisticItem("One Death Per People", roundOffNumber(valueOf((int) coronaResponse.getOneDeathPerPeople()))));
                coronaStatisticList.add(new CoronaStatisticItem("One Test Per People", roundOffNumber(valueOf((int) coronaResponse.getOneTestPerPeople()))));
                coronaStatisticList.add(new CoronaStatisticItem("Active Per One Million", roundOffNumber(valueOf((int) coronaResponse.getActivePerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Recovered Per One Million", roundOffNumber(valueOf((int) coronaResponse.getRecoveredPerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Critical Per One Million", roundOffNumber(valueOf((int) coronaResponse.getCriticalPerOneMillion()))));
                coronaStatisticList.add(new CoronaStatisticItem("Affected Countries", roundOffNumber(valueOf((int) coronaResponse.getAffectedCountries()))));

                coronaStatisticsAdapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(MainActivity.this, valueOf(stateMediator.getData()), Toast.LENGTH_SHORT).show();
                hideLoading();
                binding.tvNoInternet.setVisibility(View.GONE);
            }
        });
    }

    private void showEmptyState(StateMediator<Object, UiState, String, String> stateMediator) {
        runOnUiThread(() -> {
            Toast.makeText(MainActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
            binding.swipeRefreshLayout.setRefreshing(false);
            binding.progressCircular.setVisibility(View.GONE);
            binding.tvNothing.setVisibility(View.VISIBLE);
            binding.tvNothing.setText("Nothing to show :(");
            hideLoading();
            binding.tvNoInternet.setVisibility(View.GONE);
            Toast.makeText(this, valueOf(stateMediator.getMessage()), Toast.LENGTH_LONG).show();
        });
    }

    private void showErrorState(StateMediator<Object, UiState, String, String> stateMediator) {
        runOnUiThread(() -> {
            binding.progressCircular.setVisibility(View.GONE);
            binding.tvNothing.setVisibility(View.GONE);
            binding.swipeRefreshLayout.setRefreshing(false);
            hideLoading();
            binding.tvNoInternet.setVisibility(View.GONE);
            Toast.makeText(this, valueOf(stateMediator.getMessage()), Toast.LENGTH_LONG).show();
            Log.d(TAG, "liveDataObserver: error: " + stateMediator.getMessage());
        });
    }

    private String roundOffNumber(String resultString) {
//        double result = (double) Math.round(number * 100)/100;
//        double result = (double) Math.round(Double.parseDouble(number));
//        int resultInt = (int) result;
//        String resultString = valueOf(resultInt);

        Log.d(TAG, "roundOffNumber: " + resultString);

        // Thousand
        if (resultString.length() == 4) {
            return resultString.charAt(0) + "." + resultString.charAt(1) + "K";
        } else if (resultString.length() == 5) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "." + resultString.charAt(2) + "K";
        } else if (resultString.length() == 6) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "" + resultString.charAt(2) + "K";
        }

        // Million
        else if (resultString.length() == 7) {
            return resultString.charAt(0) + "." + resultString.charAt(1) + "M";
        } else if (resultString.length() == 8) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "." + resultString.charAt(2) + "M";
        } else if (resultString.length() == 9) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "" + resultString.charAt(2) + "M";
        }

        // Billion
        else if (resultString.length() == 10) {
//            String billionResult = valueOf(Math.round(Math.floor(Double.parseDouble(resultString))));
            return resultString.charAt(0) + "." + resultString.charAt(1) + "B";
        } else if (resultString.length() == 11) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "." + resultString.charAt(2) + "B";
        } else if (resultString.length() == 12) {
            return resultString.charAt(0) + "" + resultString.charAt(1) + "" + resultString.charAt(2) + "B";
        }

        // Trillion
        else if (resultString.length() == 13) {
            return resultString.charAt(0) + "T";
        } else {
            return resultString;
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_statistics_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setQueryHint("Search Statistics");
        searchView.setQueryHint(HtmlCompat.fromHtml("<font color = #000000>" + "Search Statistics" + "</font>", Html.FROM_HTML_MODE_LEGACY));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_statistics_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void searchUsers(String text) {
        final List<CoronaStatisticItem> filteredList = new ArrayList<>();
        for (CoronaStatisticItem item : coronaStatisticList) {
            if (item.getStatisticName().toLowerCase().trim().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        coronaStatisticsAdapter.filterList(filteredList);
        coronaStatisticsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        binding = null;
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getWaitingState() {
        if (null == idlingResource) idlingResource = new ApiIdlingResource();
        return idlingResource;
    }

}