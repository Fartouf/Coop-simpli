import java.util.LinkedList;

public class Producteur {

    private String nomProducteur;

    private LinkedList<Livraison> stock = new LinkedList<Livraison>();

    Producteur(String nomProducteur){
        this.nomProducteur = nomProducteur;
    }


    public LinkedList<Livraison> getStock() {
        return stock;
    }


    public void addStock(Livraison livraison) {
        this.stock.add(livraison);
    }

    public void removeStock() {
        this.stock.removeFirst();
    }
   
    public String getNomProducteur() {
        return nomProducteur;
    }


    public void setNomProducteur(String nomProducteur) {
        this.nomProducteur = nomProducteur;
    }

    public void productuctionHebdo(){

        //Nombre de livraisons par semaine
        int productuctionHebdo = ((int)(Math.random() * 5) +1); 

        for(int c = 0; c < productuctionHebdo ; c++){
            //on cree une nouvelle livraison en focntion de la frequence de production
            this.addStock(new Livraison(this));
        }
    }
    
}
