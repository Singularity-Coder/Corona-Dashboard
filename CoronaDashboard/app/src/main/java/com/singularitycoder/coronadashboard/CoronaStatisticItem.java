package com.singularitycoder.coronadashboard;

public final class CoronaStatisticItem {

    private String statisticName;
    private String statisticValue;

    public CoronaStatisticItem(String statisticName, String statisticValue) {
        this.statisticName = statisticName;
        this.statisticValue = statisticValue;
    }

    public String getStatisticName() {
        return statisticName;
    }

    public String getStatisticValue() {
        return statisticValue;
    }
}
