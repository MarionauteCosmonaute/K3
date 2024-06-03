package Model;


import Reseau.Client;
import Structure.Fifo;

import java.awt.Point;


public class JeuOnline extends Jeu {
    Client connection;
    Fifo send, receive;

    public JeuOnline(String Connection){
        super(2);
        this.connection = new Client(Connection);
        reset(connection.getNB());
        send = new Fifo();
        receive = new Fifo();
        connection.begin(send,receive);

    }
    
    @Override
    public int jouer_coup(int x_central, int y_central, int x_player, int y_player){
        int value = super.jouer_coup(x_central, y_central, x_player, y_player);
        send.add(new Coup(0,new Point(x_central,y_central),new Point(x_player,y_player)));
        return value;
    }

    @Override
    public void takePenaltyCube(int x, int y){
        super.takePenaltyCube(x,y);
        send.add(new Coup(0,new Point(x,y),new Point(-1,-1)));
    }

}
