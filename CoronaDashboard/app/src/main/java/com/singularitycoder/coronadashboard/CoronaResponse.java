package com.singularitycoder.coronadashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class CoronaResponse {

    @SerializedName("updated")
    @Expose(serialize = true, deserialize = true)
    private long updated;

    @SerializedName("cases")
    @Expose(serialize = true, deserialize = true)
    private long cases;

    @SerializedName("todayCases")
    @Expose(serialize = true, deserialize = true)
    private long todayCases;

    @SerializedName("deaths")
    @Expose(serialize = true, deserialize = true)
    private long deaths;

    @SerializedName("todayDeaths")
    @Expose(serialize = true, deserialize = true)
    private long todayDeaths;

    @SerializedName("recovered")
    @Expose(serialize = true, deserialize = true)
    private long recovered;

    @SerializedName("todayRecovered")
    @Expose(serialize = true, deserialize = true)
    private long todayRecovered;

    @SerializedName("active")
    @Expose(serialize = true, deserialize = true)
    private long active;

    @SerializedName("critical")
    @Expose(serialize = true, deserialize = true)
    private long critical;

    @SerializedName("casesPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long casesPerOneMillion;

    @SerializedName("deathsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long deathsPerOneMillion;

    @SerializedName("tests")
    @Expose(serialize = true, deserialize = true)
    private long tests;

    @SerializedName("testsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long testsPerOneMillion;

    @SerializedName("population")
    @Expose(serialize = true, deserialize = true)
    private long population;

    @SerializedName("oneCasePerPeople")
    @Expose(serialize = true, deserialize = true)
    private long oneCasePerPeople;

    @SerializedName("oneDeathPerPeople")
    @Expose(serialize = true, deserialize = true)
    private long oneDeathPerPeople;

    @SerializedName("oneTestPerPeople")
    @Expose(serialize = true, deserialize = true)
    private long oneTestPerPeople;

    @SerializedName("activePerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long activePerOneMillion;

    @SerializedName("recoveredPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long recoveredPerOneMillion;

    @SerializedName("criticalPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private long criticalPerOneMillion;

    @SerializedName("affectedCountries")
    @Expose(serialize = true, deserialize = true)
    private long affectedCountries;

    public long getUpdated() {
        return updated;
    }

    public long getCases() {
        return cases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public long getDeaths() {
        return deaths;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public long getRecovered() {
        return recovered;
    }

    public long getTodayRecovered() {
        return todayRecovered;
    }

    public long getActive() {
        return active;
    }

    public long getCritical() {
        return critical;
    }

    public long getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public long getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public long getTests() {
        return tests;
    }

    public long getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public long getPopulation() {
        return population;
    }

    public long getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public long getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public long getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public long getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public long getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public long getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public long getAffectedCountries() {
        return affectedCountries;
    }
}
