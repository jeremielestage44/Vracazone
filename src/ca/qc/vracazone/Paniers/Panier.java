package ca.qc.vracazone.Paniers;


import ca.qc.vracazone.db.Database;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class Panier {
    private String identifiantTransaction;

    public String getIdentifiantTransaction() {
        return identifiantTransaction;
    }

    private String identifiantClient;

    private Long millisecondes;

    private int nbProduitsDifferents;

    private List<PanierProduits> produitsAchetes;

    /** crée un panier avec comeme argument
     * @param identifiantTransaction identifiant de Transaction
     * @param identifiantClient du client
     * @param millisecondes nb de millisecondes écoulées depuis le lancement du site
     * @param nbProduitsDifferents nb de produits différents
     * @param produitsAchetes liste de produits achetés
     */
    public Panier(String identifiantTransaction, String identifiantClient, Long millisecondes, int nbProduitsDifferents, List<PanierProduits> produitsAchetes) {
        this.identifiantTransaction = identifiantTransaction;
        this.identifiantClient = identifiantClient;
        this.millisecondes = millisecondes;
        this.nbProduitsDifferents = nbProduitsDifferents;
        this.produitsAchetes = produitsAchetes;
    }

    /**
     * @return la date de l'achat mais en String
     */
    public String formatterDate(){
        String dateTexte="";
        SimpleDateFormat formatDate= new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTexte=formatDate.format(millisecondes);
        return dateTexte;
    }

    /**
     * @param database
     * @return une facture
     */
    public Facture calculerFacture(Database database){
        double sousTotal=0;
        double taxes=0;
        double total;
        for(int i=0; i<produitsAchetes.size(); i++){
            sousTotal=sousTotal+produitsAchetes.get(i).prixProduitAchete(database);
            taxes=taxes+produitsAchetes.get(i).taxeProduitAchete(database);
        }
        sousTotal=Math.round(sousTotal*100)/100.0;
        taxes=Math.round(taxes*100)/100.0;
        total=taxes+sousTotal;
        Facture facture=new Facture(identifiantTransaction,formatterDate(),sousTotal,taxes,total);
        return facture;
    }

    /**
     * @return la liste de Contenant dans un panier
     */
    public List<Contenant> listeContenantPanier(){
        List<Contenant> contenantPanier=new ArrayList<>();
        for(int i=0; i<produitsAchetes.size(); i++){
            while(produitsAchetes.get(i).getQuantite()!=0){
                contenantPanier.add(produitsAchetes.get(i).remplirContenant());}
                }
        return contenantPanier;
    }

    /**
     * @param contenants liste de contenants dans un panier
     * @param database
     * @return un colis
     */
        public Colis emballer(List<Contenant> contenants,Database database){
            Stack<Contenant> sachets=new Stack<>();
            List<Contenant> pots=new ArrayList<>();
            while(!contenants.isEmpty()) {
                double maximum = contenants.get(0).getQuantiteVersee();
                for (int i = 1; i < contenants.size(); i++) {
                    if(contenants.get(i).quantiteVersee>maximum){
                        maximum=contenants.get(i).getQuantiteVersee();// trouve le contenant le plus lourd
                    }
                }
                for(int k=0; k<contenants.size();k++){
                    if(contenants.get(k).getQuantiteVersee()==maximum){
                        if (contenants.get(k).isSachet()) {
                            sachets.push(contenants.get(k));
                        }
                        else{
                            pots.add(contenants.get(k));
                        }
                        contenants.remove(contenants.get(k));
                    }
                }
            }
        Colis colis=new Colis(identifiantTransaction,identifiantClient,calculerFacture(database),sachets,pots);// renvoie un colis avec ses attributs
            return colis;

    }


}
