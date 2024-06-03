//import java.net.*;
import Reseau.*;

public class ServTest {

    public static void main(String[] args) {
        Server serv = new Server(2); /* Integer.parseInt(args[0]) */
        System.out.println(serv.initSockets());
        serv.initConnection();
        serv.WaitAll();
        serv.begin();
        while(true){}

    }
}
