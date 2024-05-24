
import Model.*;


public class pyramideListtest {
    public static void main(String[] args) {
        Pyramid Bleu,Rouge,Vert;
        Bleu = new Pyramid(6);
        Rouge = new Pyramid(6);
        Vert = new Pyramid(6);
        Bleu.fill(Cube.Bleu);
        Vert.fill(Cube.Vert);
        Rouge.fill(Cube.Rouge);
        PyramideList list = new PyramideList(3, 5);
        list.add(Bleu, 5);
        list.add(Rouge, 3);
        list.add(Vert, 6);
        list.add(Bleu, 10);
        list.add(Rouge,7 );
        System.out.println(list.getPyramid(0));
        System.out.println(list.getPyramid(1));
        System.out.println(list.getPyramid(2));

    }
}
