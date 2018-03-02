package ElgamalSystem;

/**
 * Created by sfaxi19 on 14.04.17.
 */
public class PublicKeys {

    private String p;
    private String g;
    private String y;

    public PublicKeys(String p, String g, String y) {
        this.p = p;
        this.g = g;
        this.y = y;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
