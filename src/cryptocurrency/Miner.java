package cryptocurrency;

import blockchain.Block;
import blockchain.Blockchain;
import constants.Constants;

public class Miner {

    // Every miner gets 6.25 BTC after the mining
    private double reward;

    public void mine(Block block, Blockchain blockchain) {

        // This is the Proof of Work (PoW)
        while (!isGoldenHash(block)) {
            block.incrementNonce();
            block.generateHash();
        }

        System.out.println(block + " has just mined...");
        System.out.println("Hash is: " + block.getHash());

        blockchain.addBlock(block);
        reward += Constants.MINE_REWARD;
    }

    public boolean isGoldenHash(Block block) {
        String leadingZeros = new String(new char[Constants.DIFFICULTY]).replace('\0', '0');

        return block.getHash().substring(0, Constants.DIFFICULTY).equals(leadingZeros);
    }

    public double getReward() {
        return this.reward;
    }

}
