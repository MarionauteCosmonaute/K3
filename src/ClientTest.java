import Reseau.*;

public class ClientTest {
    public static void main(String[] args) {
        Client client = new Client(args[0]);
        client.begin();
        client.listen();
    }
}
