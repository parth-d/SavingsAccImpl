package com.parth.bank;

/**
 * A runnable class which executes the withdrawal operation a certain number of times, defined in the Transact.java class in a thread
 */
public class Withdraw implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < Transact.count; i++){
            try {Transact.defAccount.withdraw(Transact.defaultAmount);}
            catch (InterruptedException e) {e.printStackTrace();}
        }
    }
}
