package Model.Info;

public class Stat {
    int toatalGames, winsPlayer1, winsPlayer2;

    public Stat(){
        toatalGames = winsPlayer1 = winsPlayer2 = 0;
    }

    public float winratePlayer1(){
        return ((float)winsPlayer1/((float)toatalGames))*100;
    }
    
    public float winratePlayer2(){
        return ((float)winsPlayer2/((float)toatalGames))*100;
    }

    public synchronized void addGame(boolean winner){
        toatalGames++;
        if (winner) winsPlayer1++;
        else winsPlayer2++;
    }
}
