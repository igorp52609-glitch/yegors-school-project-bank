package com.example.myapplication;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class acountData {
    private double balance;
    private String name;
    private int num;
    private ArrayList<Transaction> transactionList = new ArrayList<>();
     public acountData(int num,String name,double balance)
     {
         this.balance = balance;
         this.name = name;
         this.num = num;
     }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getNum() {
        return num;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("the sum have to be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException("you dont have money");
        }

        balance -= amount;
    }
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("the sum have to be positive");
        }

        balance += amount;
    }
    public void transfer(acountData targetAccount, double amount) throws InsufficientFundsException, InvalidAmountException {
        if (targetAccount == null) {
            throw new IllegalArgumentException("not exsit");
        }
        if (amount <= 0) {
            throw new InvalidAmountException("have to be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("you dont have enougth money");
        }
        this.balance -= amount;
        targetAccount.balance += amount;
    }
    public void addTransaction(String type, double amount, String status)
    {
        transactionList.add(new Transaction(type,amount,status));
    }
    public ArrayList<Transaction> getTransaction()
    {
         return transactionList;
    }
}


