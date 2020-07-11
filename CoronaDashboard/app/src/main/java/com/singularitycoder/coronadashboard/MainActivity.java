package com.singularitycoder.coronadashboard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.IdlingResource;

import com.singularitycoder.coronadashboard.databinding.ActivityMainBinding;

import java.util.ArrayList;
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

    @Nullable
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor(this, R.color.colorPrimary);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialise();
        setUpToolBar();
        setUpRecyclerView();
        getCoronaData();
        binding.swipeRefreshLayout.setOnRefreshListener(this::getCoronaData);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    public final void setStatusBarColor(Activity activity, int statusBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
            window.requestFeature(window.FEATURE_NO_TITLE);
            window.requestFeature(Window.FEATURE_PROGRESS);
            window.setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
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
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
//        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(this);
        binding.recyclerNews.setLayoutManager(layoutManager);
        coronaStatisticsAdapter = new CoronaStatisticsAdapter(coronaStatisticList, this);
        binding.recyclerNews.setAdapter(coronaStatisticsAdapter);
        binding.recyclerNews.setItemAnimator(new DefaultItemAnimator());
    }

    private void getCoronaData() {
        if (hasInternet(this)) {
            coronaStatisticsViewModel.getCoronaStatisticsFromRepository(
                    "v3",
                    "covid-19",
                    "all",
                    idlingResource
            ).observe(MainActivity.this, liveDataObserver());
        } else {
            binding.tvNoInternet.setVisibility(View.VISIBLE);
            binding.swipeRefreshLayout.setRefreshing(false);
            coronaStatisticList.clear();
        }
    }

    private void showProgress() {
        if (null != progressDialog && !progressDialog.isShowing()) progressDialog.show();
    }

    private void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing()) progressDialog.dismiss();
    }

    private Observer<RequestStateMediator<Object, UiState, String, String>> liveDataObserver() {
        Observer<RequestStateMediator<Object, UiState, String, String>> observer = null;
        if (hasInternet(this)) {
            observer = requestStateMediator -> {

                if (UiState.LOADING == requestStateMediator.getStatus()) {
                    runOnUiThread(() -> {
                        progressDialog.setMessage(valueOf(requestStateMediator.getMessage()));
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        showProgress();
                    });
                }

                if (UiState.SUCCESS == requestStateMediator.getStatus()) {
                    runOnUiThread(() -> {
                        if (("STATISTICS").equals(requestStateMediator.getKey())) {
                            coronaStatisticList.clear();
                            CoronaResponse coronaResponse = (CoronaResponse) requestStateMediator.getData();

                            coronaStatisticList.add(new CoronaStatisticItem("Updated", valueOf(coronaResponse.getUpdated())));
                            coronaStatisticList.add(new CoronaStatisticItem("Cases", valueOf(coronaResponse.getCases())));
                            coronaStatisticList.add(new CoronaStatisticItem("Cases Today", valueOf(coronaResponse.getTodayCases())));
                            coronaStatisticList.add(new CoronaStatisticItem("Deaths", valueOf(coronaResponse.getDeaths())));
                            coronaStatisticList.add(new CoronaStatisticItem("Deaths Today", valueOf(coronaResponse.getTodayDeaths())));
                            coronaStatisticList.add(new CoronaStatisticItem("Recovered", valueOf(coronaResponse.getRecovered())));
                            coronaStatisticList.add(new CoronaStatisticItem("Recovered Today", valueOf(coronaResponse.getTodayRecovered())));
                            coronaStatisticList.add(new CoronaStatisticItem("Active", valueOf(coronaResponse.getActive())));
                            coronaStatisticList.add(new CoronaStatisticItem("Critical", valueOf(coronaResponse.getCritical())));
                            coronaStatisticList.add(new CoronaStatisticItem("Cases Per One Million", valueOf(coronaResponse.getCasesPerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Deaths Per One Million", valueOf(coronaResponse.getDeathsPerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Tests", valueOf(coronaResponse.getTests())));
                            coronaStatisticList.add(new CoronaStatisticItem("Tests Per One Million", valueOf(coronaResponse.getTestsPerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Population", valueOf(coronaResponse.getPopulation())));
                            coronaStatisticList.add(new CoronaStatisticItem("One Case Per People", valueOf(coronaResponse.getOneCasePerPeople())));
                            coronaStatisticList.add(new CoronaStatisticItem("One Death Per People", valueOf(coronaResponse.getOneDeathPerPeople())));
                            coronaStatisticList.add(new CoronaStatisticItem("One Test Per People", valueOf(coronaResponse.getOneTestPerPeople())));
                            coronaStatisticList.add(new CoronaStatisticItem("Active Per One Million", valueOf(coronaResponse.getActivePerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Recovered Per One Million", valueOf(coronaResponse.getRecoveredPerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Critical Per One Million", valueOf(coronaResponse.getCriticalPerOneMillion())));
                            coronaStatisticList.add(new CoronaStatisticItem("Affected Countries", valueOf(coronaResponse.getAffectedCountries())));

                            coronaStatisticsAdapter.notifyDataSetChanged();
                            binding.swipeRefreshLayout.setRefreshing(false);

                            Toast.makeText(MainActivity.this, valueOf(requestStateMediator.getData()), Toast.LENGTH_SHORT).show();
                            hideProgress();
                            binding.tvNoInternet.setVisibility(View.GONE);
                        }
                    });
                }

                if (UiState.EMPTY == requestStateMediator.getStatus()) {
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Something is wrong!", Toast.LENGTH_SHORT).show();
                        binding.swipeRefreshLayout.setRefreshing(false);
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.tvNothing.setVisibility(View.VISIBLE);
                        binding.tvNothing.setText("Nothing to show :(");
                        hideProgress();
                        binding.tvNoInternet.setVisibility(View.GONE);
                        Toast.makeText(this, valueOf(requestStateMediator.getMessage()), Toast.LENGTH_LONG).show();
                    });
                }

                if (UiState.ERROR == requestStateMediator.getStatus()) {
                    runOnUiThread(() -> {
                        binding.progressCircular.setVisibility(View.GONE);
                        binding.tvNothing.setVisibility(View.GONE);
                        binding.swipeRefreshLayout.setRefreshing(false);
                        hideProgress();
                        binding.tvNoInternet.setVisibility(View.GONE);
                        Toast.makeText(this, valueOf(requestStateMediator.getMessage()), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "liveDataObserver: error: " + requestStateMediator.getMessage());
                    });
                }
            };
        }
        return observer;
    }

    public final boolean hasInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        return cm.getActiveNetworkInfo() != null;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        MenuItem searchItem = menu.findItem(R.id.action_statistics_search);
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
        List<CoronaStatisticItem> filteredList = new ArrayList<>();
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