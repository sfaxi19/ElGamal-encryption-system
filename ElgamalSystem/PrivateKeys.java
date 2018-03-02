package ElgamalSystem;

/**
 * Created by sfaxi19 on 14.04.17.
 */
public class PrivateKeys {
    private String x;
    private String p;

    public PrivateKeys(String x, String p) {
        this.x = x;
        this.p = p;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
