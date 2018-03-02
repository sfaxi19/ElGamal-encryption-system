package ElgamalSystem;

/**
 * Created by sfaxi19 on 14.04.17.
 */
public class CryptData {

    private String a;
    private String b;

    public CryptData(String a, String b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a + "\n" + b;
    }
}
