import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Scanner;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class App {

    private static final Logger log = Logger.getLogger("global");
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("Log.log", false);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    //return record.getLevel() + ": " + record.getMessage() + "\n";
                    return record.getMessage() + "\n";
                }
            });
            log.addHandler(fileHandler);
        } catch (IOException e) {
            System.out.println("Error initializing FileHandler: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        
        //Temps de la simulation en semaines.
        int tempsDeSim = 2;

        //liste de producteurs ==> hashset pour eviter des doublons
        HashSet<Producteur> producteurs = new HashSet<Producteur>();

        //liste de camions ==> hashset pour eviter des doublons
        HashSet<Camion> camions = new HashSet<Camion>();

        try (Scanner userInput = new Scanner(System.in)) {

            boolean entreValide = false;

            System.out.println("Veuillez entrer le nombre de Camions à Disposition:");

            int nbCam = 0;

            while (!entreValide) {
    
                String entre = userInput.nextLine(); 

                try {
                    nbCam = Integer.parseInt(entre); 
                    if (nbCam < 0) {
                        System.out.println("Veuillez entrer un nombre supérieur à 0 SVP:");
                    } else {
                        entreValide = true; 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre valide SVP:");
                }
            }


            entreValide = false;
            System.out.println("Veuillez entrer le nombre de producteurs:");

            int nbProducteur = 0;

            while (!entreValide) {
    
                String entre = userInput.nextLine(); 

                try {
                    nbProducteur = Integer.parseInt(entre); 
                    if (nbProducteur < 0) {
                        System.out.println("Veuillez entrer un nombre supérieur à 0 SVP:");
                    } else {
                        entreValide = true; 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre valide SVP:");
                }
            }

            entreValide = false;
            System.out.println("Veuillez entrer la capacité de l'entrepot:");

            int capaciteEntrepot = 0;

            while (!entreValide) {
    
                String entre = userInput.nextLine(); 

                try {
                    capaciteEntrepot = Integer.parseInt(entre); 
                    if (capaciteEntrepot < 0) {
                        System.out.println("Veuillez entrer un nombre supérieur à 0 SVP:");
                    } else {
                        entreValide = true; 
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Veuillez entrer un nombre valide SVP:");
                }
            }

            Hypermarche hypermarche = new Hypermarche();

            //liste des noms d'agriculteurs possibles
            String[] nomsProducteurs = {"Aggregate Agro",
            "Agricultural Gain",
            "Agventure",
            "Fresh Fields",
            "Succulent Seeds",
            "AgroAcres",
            "Blueswan Poultry",
            "Blackburrow Ranch",
            "Whitecreek Farms",
            "Bignest Farms",
            "Oakwood Farms",
            "Dewberry Farm",
            "Nature Hills",
            "Farmington",
            "Vertigo Farms",
            "Vertical Farming",
            "Farm Fund",
            "Living Livestock",
            "AgroPro",
            "Lifelong Livestock"};

            //liste des noms deja utilisé
            HashSet<String> nomsUtilise = new HashSet<String>();

            //on commence par creer les instances des producteurs
            for(int i = 0; i < nbProducteur; i++){
                int index = (int)(Math.random() * nomsProducteurs.length); 
                if(nomsUtilise.contains(nomsProducteurs[index])){
                    i -= 1;
                } else {
                    nomsUtilise.add(nomsProducteurs[index]);
                    producteurs.add(new Producteur(nomsProducteurs[index]));
                }    

            }

            //System.out.println(producteurs);

            //on cree l'entrpot en fonction de la taille choisie:
            Entrepot entrepot = new Entrepot(capaciteEntrepot, hypermarche);


            //on commence par creer les instances des camions
            for(int i = 0; i < nbCam; i++){
                //capacité du camion
                //int capacité = (int)((Math.random() * 5)+1);
                camions.add(new Camion(i+1));
            }

            //Date et heure du jour
            Calendar calendar = new GregorianCalendar(2025, 01, 11);


            //Loop pour la simulation
            for(int semaine = 0; semaine < tempsDeSim; semaine++){
                //log.info("\n");
                //log.info("Semaine du " + calendar.get(Calendar.DATE)+ "/" + calendar.get(Calendar.MONTH)+ "/" + calendar.get(Calendar.YEAR)+ "\n");
                //System.out.println("\n");
                System.out.println("Semaine du " + calendar.get(Calendar.DATE)+ "/" + calendar.get(Calendar.MONTH)+ "/" + calendar.get(Calendar.YEAR)+ "\n");
                
                //livraisons des producteurs vers l'entrepot chaque semaine 
                for(Producteur produteur : producteurs){
                    //fonction qui calcule le nombre de production et cree les livraisons de la semaine.
                    produteur.productuctionHebdo();
                    while(produteur.getStock().size() > 0){
                        for(Camion camion: camions){
                            if(produteur.getStock().size() > 0){
                                System.out.println("Nouvelle livraison disponible1 chez le producteur : " + produteur.getNomProducteur());
                                camion.livraisonEntrepot(produteur, entrepot, log);
                            }else{
                                System.out.println("Toute la marchandise est à l'entrepot");
                                break;
                            }
                        }
                    }
                }

                /*
                 * Une fois que toutes les livraisons sont effectuées des fournisseurs vers l'entrepot, on vide l'entrepot vers le supermarché
                 */
                while(entrepot.getLivraisons().size()>0){
                    System.out.println("Le stock de l'entrepot s'élève à  " + entrepot.getLivraisons().size() + " unitées");

                    for(Camion camion : camions){

                        if(entrepot.getLivraisons().size()>0){
                            camion.LivraisonHypemarché(entrepot, hypermarche, log);
                        } else {
                            System.out.println("Tout le stock est livré au hypermarche");
                            break;
                        }
                    }
                }

                System.out.println("Le stock dans le hypermarché s'éleve à " + hypermarche.getStock().size() + "\n");
                calendar.add(Calendar.DATE, 7);
            }
        } catch (Exception e) {
            System.out.println("Error handeling user input: " + e.getMessage());
        }

        //on ferme le filehandler
        if (fileHandler != null) {
            try {
                fileHandler.close();
            } catch (Exception e) {
                System.out.println("Error closing FileHandler: " + e.getMessage());
            }
        }
   
    }
}
