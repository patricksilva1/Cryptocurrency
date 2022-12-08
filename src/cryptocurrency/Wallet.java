package cryptocurrency;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blockchain.Blockchain;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() {
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public Transaction transferMoney(PublicKey receiver, double amount) {
        if (calculateBalance() < amount) {
            System.out.println("Invalid Transaction because of not enough money...");

            return null;
        }

        List<TransactionInput> inputs = new ArrayList<TransactionInput>();

        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {

            TransactionOutput UTXO = item.getValue();

            if (UTXO.isMine(this.publicKey)) {
                inputs.add(new TransactionInput(UTXO.getId()));
            }
        }

        Transaction newTransaction = new Transaction(publicKey, receiver, amount, inputs);

        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

    private double calculateBalance() {

        double balance = 0;

        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {
            TransactionOutput transactionOutput = item.getValue();

            if (transactionOutput.isMine(publicKey)) {
                balance += transactionOutput.getAmount();
            }
        }

        return balance;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
