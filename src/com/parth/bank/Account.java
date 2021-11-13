package com.parth.bank;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Account {
    /*
    Private variables
     */
    private double balance;
    private final Lock transactionLock;
    private final Condition sufficientAmount;
    private final Logger logger = Logger.getLogger(Account.class.getName());

    /*
    Default constructor
     */
    public Account(){
        balance = 0;
        transactionLock = new ReentrantLock();
        sufficientAmount = transactionLock.newCondition();
    }

    /**
     * For depositing money, the lock is held, and the balance amount is changed
     * Also, the condition is signalled which helps the threads waiting on the condition to check if they can start executing
     * Finally, the lock is released
     * @param amount: Amount to be deposited
     */
    public void deposit(double amount){
        try{
            transactionLock.lock();
            this.balance = this.balance + amount;
            sufficientAmount.signalAll();
            logger.info("Deposited " + amount + ". New Balance\t: " + balance);
        }
        finally {transactionLock.unlock();}
    }

    /**
     * The lock is held and the thread waits on the sufficientAmount condition if the current balance is less than the amount to be withdrawn
     * When the condition is met, the amount is subtracted from the balance
     * Finally the lock is released
     * @param amount Amount to be withdrawn
     */
    public void withdraw(double amount) throws InterruptedException {
        try{
            transactionLock.lock();
            while (balance < amount){
                logger.warning("Current balance not sufficient. Waiting.\nBalance\t: " + balance + "\tAmount\t: " + amount);
                sufficientAmount.await();
            }
            balance = balance - amount;
            logger.info("Withdrew " + amount + ". New Balance\t: " + this.balance);
        }
        finally {transactionLock.unlock();}
    }

    /**
     * The lock is held. The withdraw function is called in sender account instance and the deposit function is called in this instance
     * @param amount Amount to be transferred
     * @param sender Sending account instance
     */
    public void transfer(double amount, Account sender){
        transactionLock.lock();
        try {
            sender.withdraw(amount);
            this.deposit(amount);
            logger.info("Transferred " + amount + ".");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {transactionLock.unlock();}
    }

    public double getBalance() {return balance;}
}
