package Model;


import Reseau.Client;
import Structure.Fifo;

import java.awt.Point;
import java.util.ArrayList;


import Model.Runnables.Action;


public class JeuOnline extends Jeu {
    Client connection;
    Fifo send, receive;
    Thread playThread;
    boolean owner;
    int ID;

    public JeuOnline(String Connection, boolean owner){
        super(2);
        this.connection = new Client(Connection);
        connection.readLine();
        System.out.println("Connected");
        if(owner){
            ID = 0;
            initTest();
            connection.writeLine(stringPrincipale());
            connection.writeLine(""+current_player);

            for(int i = 1; i < nbJoueur ; i++){
                connection.writeLine(getPlayer(i).stringPersonalBag());
            }

        }
        else{
            ID=1;
            initPrincipale(centre());
            current_player = Integer.parseInt(connection.readLine());
            getPlayer(ID).emptyBag();
            initPlayerBag(ID);
        }
        send = new Fifo();
        receive = new Fifo();
        
    }

    public void doneConstruction(){
        String string;
        string = getPlayer(ID).getPyramid().convertLine();
        connection.writeLine(string);
        //System.out.println("Pyramide perso envoyee: " + string);
        for(int i = 0; i < nbJoueur; i++){
            if(i != ID){
                //System.out.println("attente de pyramide");
                string = connection.readLine();
                getPlayer(i).build(string);
                //System.out.println("Pyramide n " + i + " recu");
            }
        }
    }


    @Override
    public void gameStart() {
        super.gameStart();
        connection.begin(send,receive);
        playThread = new Thread(new Action(this, receive));
        playThread.start();
    }
    
    private ArrayList<Cube> centre(){
        ArrayList<Cube> list = new ArrayList<>();
        String[] stringList = connection.readLine().split(" ");
        for(int i = 0; i < stringList.length; i++){
            list.add(Cube.conversion(stringList[i]));
        }
        return list;
    }

    private void initPlayerBag(int player){
        String[] stringList = connection.readLine().split(" ");
        for(int i = 0; i < stringList.length; i++){
            getPlayer(player).addBag(stringList[i]);
        }
    }
    
    @Override
    public int jouer_coup(int x_central, int y_central, int x_player, int y_player){
        int value = super.jouer_coup(x_central, y_central, x_player, y_player);
        send.add(new Coup(1,new Point(x_player,y_player),new Point(x_central,y_central)));
        return value;
    }

    @Override
    public void takePenaltyCube(int x, int y){
        super.takePenaltyCube(x,y);
        send.add(new Coup(3, new Point(x,y), new Point(-1,-1)));
    }
    
    
    public void doAction(Coup c){
        switch(c.type){
            case 3:
            case 4:
                super.takePenaltyCube(c.source.x, c.source.y);
            break;
            case 7:
                
            break;
            default:
                super.jouer_coup(c.dest.x,c.dest.y,c.source.x,c.source.y);
            break;
        }
    }

}
