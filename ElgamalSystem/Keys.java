package ElgamalSystem;

/**
 * Created by sfaxi19 on 14.04.17.
 */
public class Keys {
    public PrivateKeys privateKeys;
    public PublicKeys publicKeys;

    public Keys(PrivateKeys privateKeys, PublicKeys publicKeys) {
        this.privateKeys = privateKeys;
        this.publicKeys = publicKeys;
    }

    @Override
    public String toString() {
        return  "Public keys:" +
                "\np: " + publicKeys.getP() +
                "\ng: " + publicKeys.getG() +
                "\ny: " + publicKeys.getY() +
                "\n\nPrivate key:" +
                "\nx: " + privateKeys.getX();
    }
}
