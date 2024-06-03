package IA;

public class Couple {
    int x,y;

    Couple(){}
    Couple(int a, int b){
        this.x=a;
        this.y=b;
    }
    public void setX(int a){
        x=a;
    }
    public void setY(int b){
        y=b;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if (!(o instanceof Couple)){
            return false;
        }
        Couple c= (Couple) o;

        return x==c.x && y==c.y;
    }
}
