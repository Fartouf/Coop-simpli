import java.util.LinkedList;
import java.util.logging.Logger;

public class Camion {

    private int capaciteCamion;
    private int numeroCamion;

    Camion(int capaciteCamion, int numeroCamion) {
        this.capaciteCamion = capaciteCamion;
        this.numeroCamion = numeroCamion;
    }

    public int getCapaciteCamion() {
        return capaciteCamion;
    }


    private LinkedList<Livraison> chargeVersEntrepot(Producteur producteur, Entrepot entrepot) {
        int stockProducteur = producteur.getStock().size();
        int capaciteEntrepot = entrepot.getCapatiteDisponible();

        LinkedList<Livraison> chargeCamion = new LinkedList<Livraison>();

        // Cas 1 : la capacité disponible de l'entrerepot est superieure a la capacité
        // de transport d'un camion
        if (capaciteEntrepot > this.capaciteCamion) {
            // Cas 1.1 : le camion peut transorter plus de livraisons que sont disponibles
            // chez le producteur
            if (stockProducteur < this.capaciteCamion) {
                for (int s = 0; s < stockProducteur; s++) {
                    // on ajoute tout le stock du fournisseur dans le camion
                    chargeCamion.add(producteur.getStock().getFirst());
                    //on eleve la livraison du stock du producteur
                    producteur.removeStock();
                }
            }
            // Cas 1.2 : on peut remplir le camion entierement
            else if (stockProducteur >= this.capaciteCamion) {
                for (int c = 0; c < this.capaciteCamion; c++) {
                    // on ajoute une livraison dans le camion
                    chargeCamion.add(producteur.getStock().getFirst());
                    //on eleve la livraison du stock du producteur
                    producteur.removeStock();
                }
            }
        }
        // Cas 2 la capactité disponible dans l'entrepot est inferieure ou égale à la capacité de
        // transport d'un camion
        else if (capaciteEntrepot <= this.capaciteCamion) {
            // Cas 2.1 le stock du fournisseur est supperieur a la capacité de l'entrepot
            if (stockProducteur > capaciteEntrepot) {
                for (int c = 0; c < capaciteEntrepot; c++) {
                    // on ajoute une livraison au camion
                    chargeCamion.add(producteur.getStock().getFirst());
                    //on eleve la livraison du stock du producteur
                    producteur.removeStock();
                }
            }
            // Cas 2.2 le stock du fournisseur est inferieur ou égal à la capactié de l'entrepot
            else if (stockProducteur <= capaciteEntrepot) {
                for (int s = 0; s < stockProducteur; s++) {
                    // on ajoute une livraison au camion
                    chargeCamion.add(producteur.getStock().getFirst());
                    //on eleve la livraison du stock du producteur
                    producteur.removeStock();
                }
            }
        }
        return chargeCamion;
    }

    // livraison vers un entrepot depuis le stock d'un producteur
    public void livraisonEntrepot(Producteur producteur, Entrepot entrepot, Logger log) {

        int stockProducteur = producteur.getStock().size();
        int capaciteDisponible = entrepot.getCapatiteDisponible();

        // Cas 1 le stock du producteur n'est pas vide et l'entrepot n'est pas rempli au maximum
        if (stockProducteur > 0 && capaciteDisponible > 0) {

            LinkedList<Livraison> charge = this.chargeVersEntrepot(producteur, entrepot);
            entrepot.addLivraisons(charge);
            if (charge.size() > 1){
                System.out.println( charge.size() + " Nouvelles livraisons du producteur " + producteur.getNomProducteur() + " arrivées à l'entrepot par le camion n°" + this.numeroCamion);
            }else{
                //log.info(("Nouvelle livraison du producteur " + producteur.getNomProducteur() + " arrivées à l'entrepot par le camion n°" + this.numeroCamion));
                System.out.println("Nouvelle livraison du producteur " + producteur.getNomProducteur() + " arrivées à l'entrepot par le camion n°" + this.numeroCamion);
            }
        }
        // Cas 2 le stock du producteur est vide et l'entrepot n'est pas rempli au maximum
        else if (stockProducteur <= 0 && capaciteDisponible > 0) {
            System.out.println("Plus de livraisons à effectuer vers l'entrepot");
        }
        // Cas 3 l'entrepot est a capacité maximale
        else {
            // on doit effectuer une livraison vers l'hypermarché pour faire de la place
            this.LivraisonHypemarché(entrepot, entrepot.getHypermarche(), log);
            //log.info("Livraison interemediaire vers l'hypermarché par le camion n°" + this.numeroCamion);
            System.out.println("Livraison interemediaire vers l'hypermarché par le camion n°" + this.numeroCamion);
        }

    }

    // livraison vers un hypermarché
    public void LivraisonHypemarché(Entrepot entrepot, Hypermarche hypermarche, Logger log) {

        int capaciteCamion = this.getCapaciteCamion();

        LinkedList<Livraison> charge = new LinkedList<Livraison>();

        // on peut juste enlever un a un les elements de l'entreport sans soucis de
        // conflit vers le hypermarche (taille illimitée)
        for (int c = 0; c < capaciteCamion; c++) {
            if (entrepot.getLivraisons().size() > 0) {
                // System.out.println("livraison vers le hypermarche");
                Livraison livraisonActuelle = entrepot.getLivraisons().getFirst();
                entrepot.removeLivraison();
                charge.add(livraisonActuelle);  
            } else {
                break;
            }
        }

        if(charge.size() > 1){
            //log.info(charge.size() + " Nouvelles livraisons arrivées au hypermarché par le camion n°" + this.numeroCamion);
            System.out.println(charge.size() + " Nouvelles livraisons arrivées au hypermarché par le camion n°" + this.numeroCamion);
        }else{
            //log.info("Nouvelle livraison arrivée au hypermarché par le camion n°" + this.numeroCamion);
            System.out.println("Nouvelle livraison arrivée au hypermarché par le camion n°" + this.numeroCamion);
        }
        hypermarche.addStocks(charge);
    }

}
