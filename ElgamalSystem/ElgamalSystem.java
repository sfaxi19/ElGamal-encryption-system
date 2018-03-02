package ElgamalSystem;
import SecureMethods.SecureMethods;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sfaxi19 on 13.04.17.
 */
public class ElgamalSystem {


    public boolean checkSign(byte[] bytes, Sign sign, PublicKeys keys) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] mdhash = md.digest(bytes);
        BigInteger r = new BigInteger(sign.getR(), 16);
        BigInteger s = new BigInteger(sign.getS(), 16);
        BigInteger p = new BigInteger(keys.getP(), 16);
        BigInteger g = new BigInteger(keys.getG(), 16);
        BigInteger y = new BigInteger(keys.getY(), 16);
        BigInteger m = new BigInteger(1, mdhash);
        BigInteger tmp = SecureMethods.binMod(y, r, p).multiply(SecureMethods.binMod(r, s, p));
        tmp = tmp.mod(p);
        BigInteger tmp2 = SecureMethods.binMod(g, m, p);
        boolean check = (tmp).compareTo(tmp2) == 0;
        return check;
    }

    public Sign sign(byte[] bytes, Keys keys) throws NoSuchAlgorithmException {
        Random rand = new Random();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] mdhash = md.digest(bytes);
        BigInteger p = new BigInteger(keys.privateKeys.getP(), 16);
        BigInteger x = new BigInteger(keys.privateKeys.getX(), 16);
        BigInteger g = new BigInteger(keys.publicKeys.getG(), 16);
        BigInteger p_1 = p.subtract(BigInteger.ONE);
        BigInteger m = new BigInteger(1, mdhash);
        System.out.println("hash: " + m.toString(16));
        BigInteger k = BigInteger.valueOf(1 + Math.abs(rand.nextLong()));
        while (SecureMethods.gcd(k, p_1).compareTo(BigInteger.ONE) != 0) {
            k = k.add(BigInteger.ONE);
        }
        System.out.println("k: "+ k.toString(16));
        BigInteger r = SecureMethods.binMod(g, k, p);
        BigInteger k_ = SecureMethods.ggcd(k, p_1);
        BigInteger s = (m.subtract(x.multiply(r))).multiply(k_);
        s = s.mod(p_1);
        Sign sign = new Sign(r.toString(16), s.toString(16));
        return sign;
    }


    public Keys generateKeys() {
        Random rand = new Random();
        BigInteger min = BigInteger.valueOf(2).pow(128);
        BigInteger max = BigInteger.valueOf(2).pow(256);

        BigInteger q = BigInteger.valueOf(Math.abs(rand.nextLong()));
        while ((q.compareTo(min) < 0)) {
            q = q.multiply(BigInteger.valueOf(Math.abs(rand.nextInt(Math.abs(rand.nextInt())))));
            q = q.mod(max);
        }
        boolean check = false;
        BigInteger p;
        System.out.println("gen: " + q.bitLength());
        do {
            q = SecureMethods.getSimplesity(q);
            //q = BigInteger.valueOf(83);
            //System.out.println("q: " + q.toString(16));
            p = q.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
            check = SecureMethods.isSimplesity(p);
        } while (!check);

        BigInteger g = SecureMethods.primitiveRoot(p);
        BigInteger x = BigInteger.valueOf(1+Math.abs(rand.nextLong()));
        BigInteger y = SecureMethods.binMod(g, x, p);
        Keys keys = new Keys(
                new PrivateKeys(x.toString(16), p.toString(16)),
                new PublicKeys(p.toString(16), g.toString(16), y.toString(16)));
        return keys;
    }

    public static void saveBytesToFile(byte[] data, String filename) throws IOException {
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
        dos.write(data);
        dos.flush();
        dos.close();
    }

    public static byte[] loadBytesFromFile(String filename) throws IOException {
        File file = new File(filename);
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        byte[] buffer = new byte[(int) file.length()];
        dis.read(buffer);
        return buffer;
    }


    public CryptData encryption(byte[] data, PublicKeys keys) {
        Random rand = new Random();
        BigInteger p = new BigInteger(keys.getP(), 16);
        BigInteger g = new BigInteger(keys.getG(), 16);
        BigInteger y = new BigInteger(keys.getY(), 16);
        BigInteger m = new BigInteger(1, data);
        BigInteger k = BigInteger.valueOf(Math.abs(rand.nextLong()));
        BigInteger a = SecureMethods.binMod(g, k, p);
        BigInteger b = SecureMethods.binMod(y, k, p).multiply(m);
        b = b.mod(p);
        return new CryptData(a.toString(16), b.toString(16));
    }

    public byte[] decryption(CryptData cData, PrivateKeys keys) {
        BigInteger x = new BigInteger(keys.getX(), 16);
        BigInteger p = new BigInteger(keys.getP(), 16);
        BigInteger a = new BigInteger(cData.getA(), 16);
        BigInteger b = new BigInteger(cData.getB(), 16);
        BigInteger m = b.multiply(SecureMethods.binMod(a, p.subtract(BigInteger.ONE).subtract(x), p));
        m = m.mod(p);
        return m.toByteArray();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        ElgamalSystem elgamalSystem = new ElgamalSystem();
        String textData1 = "check encryption";
        String textData2 = "check encryption";
        byte[] data1 = {0};//loadBytesFromFile("12.jpg");
        byte[] data2 = {0};//loadBytesFromFile("12.jpg");

        // Key generation
        Keys keys = elgamalSystem.generateKeys();
        System.out.println(keys);
        // Encryption / Decryption
        System.out.println("Data: " + textData1);
        CryptData cData = elgamalSystem.encryption(textData1.getBytes(), keys.publicKeys);
        System.out.println("\nEnc data:\n" + cData);
        byte[] decData = elgamalSystem.decryption(cData, keys.privateKeys);
        System.out.println("\nDec data:\n" + new String(decData));

        // Sign
        Sign sign = elgamalSystem.sign(data1, keys);
        System.out.println("\nSign:");
        System.out.println(sign);
        sign.saveSignToFile("sign.sin");
        //BigInteger r = new BigInteger(sign.getR(),16);
        //System.out.println(sign.getR());
        //sign.setR(r.xor(BigInteger.valueOf(256)).toString(16));
        //System.out.println(sign.getR());
        // Check sign
        boolean check = elgamalSystem.checkSign(data2, sign, keys.publicKeys);
        System.out.println("\nSign check: " + check);

    }

}
