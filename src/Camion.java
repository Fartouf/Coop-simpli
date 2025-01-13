import java.util.logging.Logger;

public class Camion {

    private int numeroCamion;

    Camion( int numeroCamion) {
        this.numeroCamion = numeroCamion;
    }

    // livraison vers un entrepot depuis le stock d'un producteur
    public void livraisonEntrepot(Producteur producteur, Entrepot entrepot, Logger log) {

        int stockProducteur = producteur.getStock().size();
        int capaciteDisponible = entrepot.getCapatiteDisponible();

        // Cas 1 le stock du producteur n'est pas vide et l'entrepot n'est pas rempli au maximum
        if (stockProducteur > 0 && capaciteDisponible > 0) {
            
            entrepot.addLivraison(producteur.getStock().getFirst());
            producteur.removeStock();
           
            //log.info(("Nouvelle livraison du producteur " + producteur.getNomProducteur() + " arrivées à l'entrepot par le camion n°" + this.numeroCamion));
            System.out.println("Nouvelle livraison du producteur " + producteur.getNomProducteur() + " arrivées à l'entrepot par le camion n°" + this.numeroCamion);
            
        }
        // Cas 2 le stock du producteur est vide et l'entrepot n'est pas rempli au maximum
        else if (stockProducteur <= 0 && capaciteDisponible > 0) {
            System.out.println("Plus de livraisons à effectuer vers l'entrepot");
        }
        // Cas 3 l'entrepot est a capacité maximale
        else {
            // on doit effectuer une livraison vers l'hypermarché pour faire de la place
            this.LivraisonHypemarché(entrepot, entrepot.getHypermarche(), log);
            System.out.println("Livraison interemediaire vers l'hypermarché par le camion n°" + this.numeroCamion);
        }

    }

    // livraison vers un hypermarché
    public void LivraisonHypemarché(Entrepot entrepot, Hypermarche hypermarche, Logger log) {

        // On livre de l'entrepot vers l'hypermarché
            if (entrepot.getLivraisons().size() > 0) {
                // System.out.println("livraison vers le hypermarche");
                Livraison livraisonActuelle = entrepot.getLivraisons().getFirst();
                hypermarche.addStock(livraisonActuelle);
                entrepot.removeLivraison();
            }else {
                System.out.println("Le stock de l'entrpot est vide");
            }
            
        //log.info("Nouvelle livraison arrivée au hypermarché par le camion n°" + this.numeroCamion);
        System.out.println("Nouvelle livraison arrivée au hypermarché par le camion n°" + this.numeroCamion);
        
    }

}
