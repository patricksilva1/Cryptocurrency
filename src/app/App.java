package app;

import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        Security.addProvider(new BouncyCastleProvider());

    }
}
