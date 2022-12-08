package blockchain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import constants.Constants;
import cryptocurrency.CryptographyHelper;
import cryptocurrency.Transaction;

public class Block {

    private int id;
    private int nonce;
    private long timeStamp;
    private String hash;
    private String previousHash;

    public List<Transaction> transactions;

    public Block(String previousHash) {
        this.transactions = new ArrayList<>();
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        generateHash();
    }

    public void generateHash() {
        String dataToHash = Integer.toString(id) + previousHash + Long.toString(timeStamp) + transactions.toString()
                + Integer.toString(nonce);

        this.hash = CryptographyHelper.generateHash(dataToHash);
    }

    // Check if the given transaction is valid or not
    public boolean addTransaction(Transaction transaction) {

        if (transaction == null) {
            return false;
        }

        // If the block is the genesis block we do not process
        if ((!previousHash.equals(Constants.GENESIS_PREV_HASH))) {
            if ((!transaction.verifyTransaction())) {
                System.out.println("Transaction is not valid...");

                return false;
            }
        }
        transaction.add(transaction);

        System.out.println("Transaction is valid ant it's added to the block " + this);

        return true;
    }

    // The nonce is the only parameter the miners can tune (change)
    public void incrementNonce() {
        this.nonce++;
    }

    // This SHA-256 hash identifies the block
    public String getHash() {
        return this.hash;
    }

}
