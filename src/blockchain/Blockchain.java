package blockchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cryptocurrency.TransactionOutput;

public class Blockchain {

    public static List<Block> blockchain;

    public static Map<String, TransactionOutput> UTXOs;

    public Blockchain() {
        Blockchain.UTXOs = new HashMap<String, TransactionOutput>();
        Blockchain.blockchain = new ArrayList<>();
    }

    public void addBlock(Block block) {
        Blockchain.blockchain.add(block);
    }

    public int size() {
        return Blockchain.blockchain.size();
    }

    @Override
    public String toString() {

        String blockChain = "";

        for (Block block : Blockchain.blockchain)
            blockChain += block.toString() + "\n";

        return blockChain;
    }

}
