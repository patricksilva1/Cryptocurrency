package cryptocurrency;

import java.security.PublicKey;

public class TransactionOutput {

    // Identifier of the transaction output (SHA-256)
    private String id;

    // Transaction id of the parent (so the transaction it was created in)
    private String parentTrasactionId;

    // The new owner of the coin
    private PublicKey receiver;

    // Amount of coins
    private double amount;

    public TransactionOutput(PublicKey receiver, double amount, String parentTransactionId) {
        this.receiver = receiver;
        this.amount = amount;
        this.parentTrasactionId = parentTransactionId;
        generateId();
    }

    private void generateId() {
        this.id = CryptographyHelper.generateHash(receiver.toString() + Double.toString(amount) + parentTrasactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey == receiver;
    }

    // #region Getters and Setters
    public String getId() {
        return id;
    }
 
    public String getParentTrasactionId() {
        return parentTrasactionId;
    }

    public void setParentTrasactionId(String parentTrasactionId) {
        this.parentTrasactionId = parentTrasactionId;
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

    // #endregion
}
