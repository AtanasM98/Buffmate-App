package com.YAStudio.buffmate;

public class Food {
    public String name;  //name
    public int quantity;
    public String measurement;

    public double cal; //calories
    public double protein;  //protein
    public double fat;  //fat
    public double carbs; //carbs


    public Food(String name, int quantity, String measurement, double cal, double protein, double fat, double carbs) {
        this.name = name;
        this.quantity = quantity;
        this.measurement = measurement;
        this.cal = cal;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;

    }

    public Food(){

    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                ", measurement='" + measurement + '\'' +
                ", cal=" + cal +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbs=" + carbs +
                '}';
    }
}
