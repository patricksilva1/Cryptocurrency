package cryptocurrency;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import blockchain.Blockchain;

public class Wallet {

    // Users of the network
    // Used for signature
    private PrivateKey privateKey;

    // Verification
    // Address: RIPMD public key (160 bits)
    private PublicKey publicKey;

    public Wallet() {
        KeyPair keyPair = CryptographyHelper.ellipticCurveCrypto();
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    // We are able to transfer money
    // Miners of the Blockchain will put this transaction into the blockchain
    public Transaction transferMoney(PublicKey receiver, double amount) {
        if (calculateBalance() < amount) {
            System.out.println("Invalid Transaction because of not enough money...");

            return null;
        }

        // We store the inputs for the transaction in this array
        List<TransactionInput> inputs = new ArrayList<TransactionInput>();

        // Let's find our unspent transactions (The blockchain stores all the UTXOs)
        for (Map.Entry<String, TransactionOutput> item : Blockchain.UTXOs.entrySet()) {

            TransactionOutput UTXO = item.getValue();

            if (UTXO.isMine(this.publicKey)) {
                inputs.add(new TransactionInput(UTXO.getId()));
            }
        }

        // Let's create the new transaction
        Transaction newTransaction = new Transaction(publicKey, receiver, amount, inputs);

        // The sender signs the transaction
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

    // There is no balance associated with the users
    // UTXOs and consider all the transactions in the past
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
