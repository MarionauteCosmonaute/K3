package Model.Info;

public class GameInfo {
    public int winner;
    public int[] played;

    public GameInfo(int winner, int[] played){
        this.winner = winner;
        this.played = played;
    }

    public int getWinner(){
        return winner;
    }

    public String toString(){
        String string = "";
        string+= "the winner is " + winner + " after " + played[0] + " play"; 
        return string;
    }
}
