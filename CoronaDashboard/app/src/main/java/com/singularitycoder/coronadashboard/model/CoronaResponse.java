package com.singularitycoder.coronadashboard.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "table_statistics")
public final class CoronaResponse {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "RoomId")
    @Expose(serialize = false, deserialize = false)
    private int roomId;

    @ColumnInfo(name = "updated")
    @SerializedName("updated")
    @Expose(serialize = true, deserialize = true)
    private double updated;

    @ColumnInfo(name = "cases")
    @SerializedName("cases")
    @Expose(serialize = true, deserialize = true)
    private double cases;

    @ColumnInfo(name = "todayCases")
    @SerializedName("todayCases")
    @Expose(serialize = true, deserialize = true)
    private double todayCases;

    @ColumnInfo(name = "deaths")
    @SerializedName("deaths")
    @Expose(serialize = true, deserialize = true)
    private double deaths;

    @ColumnInfo(name = "todayDeaths")
    @SerializedName("todayDeaths")
    @Expose(serialize = true, deserialize = true)
    private double todayDeaths;

    @ColumnInfo(name = "recovered")
    @SerializedName("recovered")
    @Expose(serialize = true, deserialize = true)
    private double recovered;

    @ColumnInfo(name = "todayRecovered")
    @SerializedName("todayRecovered")
    @Expose(serialize = true, deserialize = true)
    private double todayRecovered;

    @ColumnInfo(name = "active")
    @SerializedName("active")
    @Expose(serialize = true, deserialize = true)
    private double active;

    @ColumnInfo(name = "critical")
    @SerializedName("critical")
    @Expose(serialize = true, deserialize = true)
    private double critical;

    @ColumnInfo(name = "casesPerOneMillion")
    @SerializedName("casesPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double casesPerOneMillion;

    @ColumnInfo(name = "deathsPerOneMillion")
    @SerializedName("deathsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double deathsPerOneMillion;

    @ColumnInfo(name = "tests")
    @SerializedName("tests")
    @Expose(serialize = true, deserialize = true)
    private double tests;

    @ColumnInfo(name = "testsPerOneMillion")
    @SerializedName("testsPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double testsPerOneMillion;

    @ColumnInfo(name = "population")
    @SerializedName("population")
    @Expose(serialize = true, deserialize = true)
    private double population;

    @ColumnInfo(name = "oneCasePerPeople")
    @SerializedName("oneCasePerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneCasePerPeople;

    @ColumnInfo(name = "oneDeathPerPeople")
    @SerializedName("oneDeathPerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneDeathPerPeople;

    @ColumnInfo(name = "oneTestPerPeople")
    @SerializedName("oneTestPerPeople")
    @Expose(serialize = true, deserialize = true)
    private double oneTestPerPeople;

    @ColumnInfo(name = "activePerOneMillion")
    @SerializedName("activePerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double activePerOneMillion;

    @ColumnInfo(name = "recoveredPerOneMillion")
    @SerializedName("recoveredPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double recoveredPerOneMillion;

    @ColumnInfo(name = "criticalPerOneMillion")
    @SerializedName("criticalPerOneMillion")
    @Expose(serialize = true, deserialize = true)
    private double criticalPerOneMillion;

    @ColumnInfo(name = "affectedCountries")
    @SerializedName("affectedCountries")
    @Expose(serialize = true, deserialize = true)
    private double affectedCountries;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getUpdated() {
        return updated;
    }

    public void setUpdated(double updated) {
        this.updated = updated;
    }

    public double getCases() {
        return cases;
    }

    public void setCases(double cases) {
        this.cases = cases;
    }

    public double getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(double todayCases) {
        this.todayCases = todayCases;
    }

    public double getDeaths() {
        return deaths;
    }

    public void setDeaths(double deaths) {
        this.deaths = deaths;
    }

    public double getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(double todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public double getRecovered() {
        return recovered;
    }

    public void setRecovered(double recovered) {
        this.recovered = recovered;
    }

    public double getTodayRecovered() {
        return todayRecovered;
    }

    public void setTodayRecovered(double todayRecovered) {
        this.todayRecovered = todayRecovered;
    }

    public double getActive() {
        return active;
    }

    public void setActive(double active) {
        this.active = active;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getCasesPerOneMillion() {
        return casesPerOneMillion;
    }

    public void setCasesPerOneMillion(double casesPerOneMillion) {
        this.casesPerOneMillion = casesPerOneMillion;
    }

    public double getDeathsPerOneMillion() {
        return deathsPerOneMillion;
    }

    public void setDeathsPerOneMillion(double deathsPerOneMillion) {
        this.deathsPerOneMillion = deathsPerOneMillion;
    }

    public double getTests() {
        return tests;
    }

    public void setTests(double tests) {
        this.tests = tests;
    }

    public double getTestsPerOneMillion() {
        return testsPerOneMillion;
    }

    public void setTestsPerOneMillion(double testsPerOneMillion) {
        this.testsPerOneMillion = testsPerOneMillion;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    public double getOneCasePerPeople() {
        return oneCasePerPeople;
    }

    public void setOneCasePerPeople(double oneCasePerPeople) {
        this.oneCasePerPeople = oneCasePerPeople;
    }

    public double getOneDeathPerPeople() {
        return oneDeathPerPeople;
    }

    public void setOneDeathPerPeople(double oneDeathPerPeople) {
        this.oneDeathPerPeople = oneDeathPerPeople;
    }

    public double getOneTestPerPeople() {
        return oneTestPerPeople;
    }

    public void setOneTestPerPeople(double oneTestPerPeople) {
        this.oneTestPerPeople = oneTestPerPeople;
    }

    public double getActivePerOneMillion() {
        return activePerOneMillion;
    }

    public void setActivePerOneMillion(double activePerOneMillion) {
        this.activePerOneMillion = activePerOneMillion;
    }

    public double getRecoveredPerOneMillion() {
        return recoveredPerOneMillion;
    }

    public void setRecoveredPerOneMillion(double recoveredPerOneMillion) {
        this.recoveredPerOneMillion = recoveredPerOneMillion;
    }

    public double getCriticalPerOneMillion() {
        return criticalPerOneMillion;
    }

    public void setCriticalPerOneMillion(double criticalPerOneMillion) {
        this.criticalPerOneMillion = criticalPerOneMillion;
    }

    public double getAffectedCountries() {
        return affectedCountries;
    }

    public void setAffectedCountries(double affectedCountries) {
        this.affectedCountries = affectedCountries;
    }
}
