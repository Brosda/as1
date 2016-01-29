package abrosda.abrosda_fueltrack;

import java.util.Date;

/**
 * Created by abrosda on 1/28/16.
 */
public class Entry {

    protected String date;
    protected String station;
    protected float odo;
    protected String grade;
    protected float amount;
    protected float cost;

    public Entry(String date, String station, float odo, String grade, float amount, float cost) {
        this.date = date;
        this.station = station;
        this.odo = odo;
        this.grade = grade;
        this.amount = amount;
        this.cost = cost;

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public float getOdo() {
        return odo;
    }

    public void setOdo(float odo) {
        this.odo = odo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
