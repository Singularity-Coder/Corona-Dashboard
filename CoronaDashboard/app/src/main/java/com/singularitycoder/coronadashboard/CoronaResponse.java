package com.singularitycoder.coronadashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class CoronaResponse {

    @SerializedName("updated")
    @Expose(serialize = true, deserialize = true)
    private double updated;

    @SerializedName("cases")
    @Expose(serialize = true, deserialize = true)
    private double cases;

    @SerializedName("todayCases")
    @Expose(serialize = true, deserialize = true)
    private double todayCases;

    @SerializedName("deaths")
    @Expose(serialize = true, deserialize = true)
    private double deaths;

    @SerializedName("todayDeaths")
    @Expose(serialize = true, deserialize = true)
    private double todayDeaths;

    @SerializedName("recovered")
    @Expose(serialize = true, deserialize = true)
    private double recovered;

    @SerializedName("todayRecovered")
    @Expose(serialize = true, deserialize = true)
    private double todayRecovered;

    @SerializedName("active")
    @Expose(serialize = true, deserialize = true)
    private double active;

    @SerializedName("critical")
    @Expose(serialize = true, deserialize = true)
    private double critical;

    @SerializedName("casesPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double casesPerOneMillion;

    @SerializedName("deathsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double deathsPerOneMillion;

    @SerializedName("tests")
    @Expose(serialize = true, deserialize = true)
    private double tests;

    @SerializedName("testsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double testsPerOneMillion;

    @SerializedName("population")
    @Expose(serialize = true, deserialize = true)
    private double population;

    @SerializedName("oneCasePerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneCasePerPeople;

    @SerializedName("oneDeathPerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneDeathPerPeople;

    @SerializedName("oneTestPerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneTestPerPeople;

    @SerializedName("activePerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double activePerOneMillion;

    @SerializedName("recoveredPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double recoveredPerOneMillion;

    @SerializedName("criticalPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double criticalPerOneMillion;

    @SerializedName("affectedCountries")
    @Expose(serialize = true, deserialize = true)
    private double affectedCountries;

    public double getUpdated() {
        return updated;
    }

    public double getCases() {
        return cases;
    }

    public double getTodayCases() {
        return todayCases;
    }

    public double getDeaths() {
        return deaths;
    }

    public double getTodayDeaths() {
        return todayDeaths;
    }

    public double getRecovered() {
        return recovered;
    }

    public double getTodayRecovered() {
        return todayRecovered;
    }

    public double getActive() {
        return active;
    }

    public double getCritical() {
        return critical;
    }

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public double getTests() {
        return tests;
    }

    public double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public double getPopulation() {
        return population;
    }

    public double getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public double getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public double getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public double getAffectedCountries() {
        return affectedCountries;
    }
}
