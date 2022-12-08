package cryptocurrency;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import blockchain.Blockchain;

public class Transaction {

    // Id of the transaction is a hash
    private String transactionId;

    // Here we use PublicKeys to reference the sender or receiver
    private PublicKey sender;
    private PublicKey receiver;

    // Amount of coins the transaction sends to the receiver from the sender
    private double amount;

    // Make sure the transaction is signed to prevent anyone else from spending the
    // coins
    private byte[] signature;

    // Every transaction has inputs and outputs
    public List<TransactionInput> inputs;
    public List<TransactionOutput> outputs;

    public Transaction(PublicKey sender, PublicKey receiver, double amount, List<TransactionInput> inputs) {
        this.inputs = new ArrayList<TransactionInput>();
        this.outputs = new ArrayList<TransactionOutput>();
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.inputs = inputs;
        calculateHash();
    }

    public boolean verifyTransaction() {

        if (!verifySignature()) {
            System.out.println("Invalid transaction because of invalide Signature...");

            return false;
        }

        // Let's gather unspent transactions (We have to consider the inputs)
        for (TransactionInput transactionInput : inputs) {
            transactionInput.setUTXO(Blockchain.UTXOs.get(transactionInput.getTransactionOutputId()));
        }

        /*
         * Transactions have 2 part: send an amount to the receiver + send the
         * (balance-amount).
         * Back to the sender.
         * Send value to recipient.
         */
        outputs.add(new TransactionOutput(this.receiver, amount, transactionId));

        // Send the left over 'change' back to sender
        outputs.add(new TransactionOutput(this.sender, getInputsSum() - amount, transactionId));

        // We have to update the UTXOs.
        // The outputs will be inputs for other transactions (So put them in the
        // blockchain's UTXOs).
        for (TransactionOutput transactionOutput : outputs) {
            Blockchain.UTXOs.put(transactionOutput.getId(), transactionOutput);
        }

        // And here, remove transaction inputs from Blockchain's UTXOs list because
        // they've been spent.
        for (TransactionInput transactionInput : inputs) {
            if (transactionInput.getUTXO() != null) {
                Blockchain.UTXOs.remove(transactionInput.getUTXO().getId());
            }
        }

        return true;
    }

    // This is how we calculate that how much money the sender has
    // We have to consider transactions in the past
    private double getInputsSum() {
        double sum = 0;

        for (TransactionInput transactionInput : inputs) {
            if (transactionInput.getUTXO() != null) {
                sum += transactionInput.getUTXO().getAmount();
            }
        }
        return sum;
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = sender.toString() + receiver.toString() + Double.toString(amount);
        signature = CryptographyHelper.sign(privateKey, data);
    }

    public boolean verifySignature() {
        String data = sender.toString() + receiver.toString() + Double.toString(amount);
        return CryptographyHelper.verify(sender, data, signature);
    }

    private void calculateHash() {
        String hashData = sender.toString() + receiver.toString() + Double.toString(amount);
        this.transactionId = CryptographyHelper.generateHash(hashData);
    }

    // #region Getters and Setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public void setSender(PublicKey sender) {
        this.sender = sender;
    }

    public PublicKey getReceiver() {
        return receiver;
    }

    public void setReceiver(PublicKey receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public void setInputs(List<TransactionInput> inputs) {
        this.inputs = inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<TransactionOutput> outputs) {
        this.outputs = outputs;
    }

    // #endregion
}
