package com.parth.bank;

/**
 * Runs the transfer operation in a thread
 */
public class Transfer implements Runnable{
    @Override
    public void run() { Transact.altAccount.transfer(Transact.defaultAmount, Transact.defAccount); }
}
