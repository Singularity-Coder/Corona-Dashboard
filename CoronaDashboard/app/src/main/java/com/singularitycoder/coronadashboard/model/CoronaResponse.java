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
