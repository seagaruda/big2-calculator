package com.big2.calculator.models;

public class Player {
    private String name;
    private double balance;

    public Player(String name) {
        this.name = name;
        this.balance = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addToBalance(double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        String sign = balance >= 0 ? "+" : "";
        return name + ": " + sign + "$" + String.format("%.2f", balance);
    }
}
