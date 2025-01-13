import java.util.LinkedList;

public class Hypermarche {


    private LinkedList<Livraison> stockHypermarche = new LinkedList<Livraison>();


    public LinkedList<Livraison> getStock(){
        return this.stockHypermarche;
    }

    public void addStock(Livraison livraison){
        this.stockHypermarche.add(livraison);
    }

    public void addStocks(LinkedList<Livraison> livraison){
        this.stockHypermarche.addAll(livraison);
    }


}
