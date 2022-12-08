package cryptocurrency;

public class TransactionInput {

    // Every input has an output. This id is the transactionId of the TransactionOutput.
    private String transactionOutputId;

    // This is the unspent transaction output
    private TransactionOutput UTXO;

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public String getTransactionOutputId() {
        return this.transactionOutputId;
    }

    public void setTransactionOutputId(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }

    public TransactionOutput getUTXO() {
        return this.UTXO;
    }

    public void setUTXO(TransactionOutput uTXO) {
        this.UTXO = uTXO;
    }

}
