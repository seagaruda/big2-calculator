package com.big2.calculator.models;

public class Player {
    private String name;
    private int balance;

    public Player(String name) {
        this.name = name;
        this.balance = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void addToBalance(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return name + ": " + (balance >= 0 ? "+" : "") + balance + " 分";
    }
}