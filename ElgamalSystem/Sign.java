package ElgamalSystem;

import java.io.*;

/**
 * Created by sfaxi19 on 14.04.17.
 */
public class Sign {

    private String r;
    private String s;

    public Sign(String r, String s) {
        this.r = r;
        this.s = s;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void loadSignFromFile(String filename) throws IOException {
        File file = new File(filename);
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        r = dis.readLine();
        s = dis.readLine();
    }

    public void saveSignToFile(String filename) throws IOException {
        File file = new File(filename);
        DataOutputStream dis = new DataOutputStream(new FileOutputStream(file));
        dis.writeUTF(r + "\n");
        dis.writeUTF(s + "\n");
    }

    @Override
    public String toString() {
        return r + "\n" + s;
    }
}
