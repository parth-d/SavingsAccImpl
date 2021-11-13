package com.parth.bank;

import java.util.logging.Logger;

/**
 * The driver class
 */

public class Transact {

    /**
     * Default parameters shared between classes
     */
    public static int count = 2;
    public static double defaultAmount = 100;

    /**
     * Instantiate 2 accounts
     * defAccount: The account on which most of the deposits and withdrawals take place. This account is also used to send money to altAccount
     * altAccount: This account is the receiver of the money from altAccount in the Transfer class
     */
    public static Account defAccount = new Account();
    public static Account altAccount = new Account();


    public static void main(String[] args) throws InterruptedException {

        /*
          Create threads
          (Currently I have commented withdrawThread. The withdrawal operation is as is performed in the transfer operation)
         */
        Thread depositThread = new Thread(new Deposit());
        Thread withdrawThread = new Thread(new Withdraw());
        Thread transferThread = new Thread(new Transfer());

        /*
          Start threads
         */

//       withdrawThread.start();
       depositThread.start();
       transferThread.start();

       /*
        Wait for the threads to finish
        */

//       withdrawThread.join();
       depositThread.join();
       transferThread.join();

       Logger.getLogger(Transact.class.getName()).info("Final balances:\n" +
               "Default Account\t\t: " + defAccount.getBalance() + "\nAlternate Account\t: " + altAccount.getBalance());
    }
}
