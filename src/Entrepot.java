import java.util.LinkedList;

public class Entrepot {

    private int capaciteLimite;
    private Hypermarche hypermarche;
    
    Entrepot(int capaciteLimite, Hypermarche hypermarche){
        this.capaciteLimite = capaciteLimite;
        this.hypermarche = hypermarche;
    }

    public Hypermarche getHypermarche() {
        return hypermarche;
    }

    //utilisation d'une liste liée pour faciliter le mechanisme FIFO
    private LinkedList<Livraison> Livraisons = new LinkedList<Livraison>();

    public LinkedList<Livraison> getLivraisons() {
        return Livraisons;
    }

    public int getCapatiteLimite(){
        return this.capaciteLimite;
    }

    public int getCapatiteDisponible(){
        return this.capaciteLimite - Livraisons.size();
    }

    public void addLivraison(Livraison livraison){
        if( Livraisons.size() < capaciteLimite){
            //System.out.println("+1 livraison dans le stock de l'entrepot");
            this.Livraisons.add(livraison);
            
        } else{
            System.out.println("L'entrepot est deja à capacité maximale");
        }
    }

    public void addLivraisons(LinkedList<Livraison> livraisons){
        if( Livraisons.size() < capaciteLimite){
            //System.out.println(livraisons.size() + " livraisons ajoutées a l'entrepot");

            this.Livraisons.addAll(livraisons);            
        } else{
            System.out.println("L'entrepot est deja à capacité maximale");
        }
    }

    public void removeLivraison(){
        this.Livraisons.removeFirst();
     }
}
