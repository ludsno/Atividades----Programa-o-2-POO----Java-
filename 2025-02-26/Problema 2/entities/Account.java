package entities;

import exceptions.AccountException;

public class Account {
    private int number;
    private String holder;
    private double balance;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws AccountException {
        if (amount > withdrawLimit) {
            throw new AccountException("The amount exceeds withdraw limit");
        }
        if (amount > balance) {
            throw new AccountException("Not enough balance");
        }
        balance -= amount;
    }

    @Override
    public String toString() {
        return "Account [balance=" + balance + ", holder=" + holder + ", number=" + number + ", withdrawLimit="
                + withdrawLimit + "]";
    }
}
