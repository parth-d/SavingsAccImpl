package com.parth.bank;

/**
 * A runnable class which executes the deposit operation a certain number of times, defined in the Transact.java class in a thread
 */
public class Deposit implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < Transact.count; i++) Transact.defAccount.deposit(Transact.defaultAmount);
    }
}
