package io.mainContent.coronavirustracker.models;

public class LocationStats {
    private String state;
    private String country;
    private int LatestTotalCases;
    private int diffFromPrevDay;


    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatestTotalCases(int latestTotalCases) {
        LatestTotalCases = latestTotalCases;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getLatestTotalCases() {
        return LatestTotalCases;
    }

    public String toString() {
        return "State is "+ this.state + " country is " + this.country + " latestCases " + this.LatestTotalCases;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }
}
