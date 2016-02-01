/*Written by Alex Brosda
* Assignment 1 cmpt 301
* 1428272
* Fuel Log*/

package abrosda.abrosda_fueltrack;


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
    protected float total; //for total cost calculating

    public Entry(){
        this.cost = 50;
        this.amount = 50;
    }
    public Entry(String date, String station, float odo, String grade, float amount, float cost) {
        this.date = date;
        this.station = station;
        this.odo = odo;
        this.grade = grade;
        this.amount = amount;
        this.cost = cost;
        this.total = (cost * amount)/100;  //gets the cost for this entry

    }

    public float getTotal() {
        return total;
    }

    public void setTotal() { //gets the cost for this entry
        this.total = (getAmount() * getCost())/100;
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

    public void setOdo(String odo) {

        this.odo = Float.valueOf(odo);
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

    public void setAmount(String amount)
    {
        this.amount = Float.valueOf(amount);
    }

    public float getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = Float.valueOf(cost);
    }

    @Override
    public String toString(){
        return date + " | " + station + " | " +String.format("%.1f", odo) + " km" +
                " | " + grade + " | " + String.format("%.3f", amount) + " L" + " | " +
                String.format("%.2f", cost) + " cents/L";
    }
}
