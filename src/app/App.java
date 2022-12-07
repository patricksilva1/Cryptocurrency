package app;

import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import cryptocurrency.CryptographyHelper;

public class App {
    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        KeyPair keys = CryptographyHelper.ellipticCurveCrypto();

        System.out.println();

        System.out.println("Private Key: " + Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()));

        System.out.println();

        System.out.println("Public Key: " + Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()));

        System.out.println();

        System.out.println("Public Key Coordinates on Elliptic Curve - " + keys.getPublic().toString());

    }
}
