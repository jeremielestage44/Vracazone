package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;
import ca.qc.vracazone.db.Poids;
import ca.qc.vracazone.db.Volume;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class ChargerPaniers {

    private final List<Panier> paniers = new ArrayList<>();

    /**
     * @return la liste de paniers valide
     */
    public List<Panier> getPaniers() {
        return paniers;
    }

    private final Database database;

    /**
     * liste de paniers invalides
     */
    private final List<PaniersInvalides> paniersInvalides=new ArrayList<>();

    private final String dateCreation="2019-10-21 00:00 UTC"; // date de mise en place de la plateforme Vracazone

    /**
     * @param database initialise une base de donnée contenant la liste de clients et de produits en référence
     *  crée la liste de paniers
     */
    public ChargerPaniers(Database database){
        this.database=database;
        boolean produitsCorrects;
        try(RandomAccessFile lecteur= new RandomAccessFile("fichiers/paniers1.bin","r")){
            while(lecteur.getFilePointer()< lecteur.length()){
                produitsCorrects=true;
                String transaction= lecteur.readUTF();
                    String identifiantClient = lecteur.readUTF();
                    Long tempsEcoules = lecteur.readLong();
                    int nbProduits = lecteur.readInt();
                    List<PanierProduits> produits = new ArrayList<>();// créé les attributs de panier
                        for (int i = 0; i < nbProduits; i++) {
                            int code = lecteur.readInt();
                            double quantite = lecteur.readDouble();
                            String unite = lecteur.readUTF();
                            if(uniteValide(unite,database,code)){// verifie si l'unité est valide
                            if(isSolide(unite)){// verifie si c'est solide
                                quantite=conversionPoidsMax(unite,quantite);
                                unite="kg";
                            }
                            else{
                                quantite=conversionVolumeMax(unite,quantite);
                                unite="L";
                            }
                            }
                            quantite=additionnerMemeProduitcommande(code,produits,quantite);//additionne si même produits commande
                            if (produitValide(code, database)&&produitsCorrects&&quantiteValide(quantite,code,database)&&uniteValide(unite,database,code)) {// si les conditions sont remplies ont crée le produit et nous l'ajoutons à la liste
                                PanierProduits produit = new PanierProduits(code, quantite, unite, nomProduit(database, code));
                                produits.add(produit);
                            } else if (!produitValide(code, database)&&produitsCorrects) {// créé un panier invalide et nous l'ajoutons à la liste de paniers invalide
                                PaniersInvalides panierInvalide = new PaniersInvalides(transaction, "Produit invalide (" + code + ")");
                                paniersInvalides.add(panierInvalide);
                                produitsCorrects=false;
                                break;
                            } else if (!uniteValide(unite, database, code)&&produitsCorrects) {
                                PaniersInvalides panierInvalide = new PaniersInvalides(transaction, "Unite invalide (" + unite+ ")");
                                paniersInvalides.add(panierInvalide);
                                produitsCorrects=false;
                                break;
                            } else if(!quantiteValide(quantite,code,database)) {
                                PaniersInvalides panierInvalide = new PaniersInvalides(transaction, "Quantite Invalide (" + quantite+ ")");
                                paniersInvalides.add(panierInvalide);
                                produitsCorrects=false;
                                break;
                            }
                        }
                            if (nbProduits >= 1 && identifiantTransactionValide(transaction) && clientExistant(database,identifiantClient) && identifiantClientValide(identifiantClient)&& produitsCorrects &&dateValide(tempsEcoules)) {
                                // si toute les condition du panier sont remplies ont l'ajoute à la liste de panier
                                Panier panier = new Panier(transaction, identifiantClient, tempsEcoules, nbProduits, produits);
                                if(paDejaTraite(panier)){
                                    paniers.add(panier);
                                }
                            }
                            if(nbProduits<1) {// crée un produit invalide si les conditions du panier ne sont pas remplies
                                    PaniersInvalides panierInvalide = new PaniersInvalides(transaction, "Aucun produit commandé");
                                    paniersInvalides.add(panierInvalide);
                                }
                                else if(!identifiantTransactionValide(transaction)){
                                    PaniersInvalides panierInvalide=new PaniersInvalides(transaction,"identifiant de transaction invalide" +transaction+")");
                                    paniersInvalides.add(panierInvalide);
                                }
                                else if(!clientExistant(database,identifiantClient)){
                                    PaniersInvalides panierInvalide=new PaniersInvalides(transaction,"Client inexistant ("+identifiantClient+")");
                                    paniersInvalides.add(panierInvalide);
                                }
                                else if(!identifiantClientValide(identifiantClient)){
                                    PaniersInvalides panierInavlide=new PaniersInvalides(transaction,"Identifiant de client invalide("+identifiantClient+")");
                                    paniersInvalides.add(panierInavlide);
                                }
                                else if(!dateValide(tempsEcoules)){
                                    PaniersInvalides panierInvalide= new PaniersInvalides(transaction,"date invalide:"+formatterDateString(tempsEcoules));
                                    paniersInvalides.add(panierInvalide);
                            }
                            }




        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("PANIERS REJETÉS =================================================");
        for(int i=0; i< paniersInvalides.size(); i++){// affiche les panier invalides
            System.out.println(paniersInvalides.get(i));
        }
        System.out.println("=================================================================");
    }

    /**
     * méthode robot faiasant l'action des robots, le bilan financier, et la livraiso
     */
        public void actionRobots(){
        List<Colis> colisTotal=new ArrayList<>();
        List<Contenant> contenantPanier;
        List<Contenant> contenantToutPanierFichier=new ArrayList<>();
        List<ProduitsVendus> produitsVendus=new ArrayList<>();
        for(int i=0; i<paniers.size(); i++){
            contenantPanier=paniers.get(i).listeContenantPanier();
            contenantToutPanierFichier.addAll(contenantPanier);
        }
        Robot robot=new Robot(contenantToutPanierFichier);
        int compteurSachet=1;
        int compteurPot=1;
        robot.afficherContenant(database,compteurSachet,compteurPot);
            for(int a=0;a<database.getProduits().size(); a++){
                ProduitsVendus vendus=vendus(contenantToutPanierFichier,database.getProduits().get(a).getDescription(),database);
                if(vendus!=null){
                    produitsVendus.add(vendus);
                }
            }
            BilanFinancier bilan =new BilanFinancier(produitsVendus);
            bilan.afficherBilan();
        for( int b=0; b<paniers.size(); b++){
            Colis colis =paniers.get(b).emballer(contenantToutPanierFichier,database);
            colis.EcrireFichier();
            colisTotal.add(colis);
        }
        Livraison livraison=new Livraison(colisTotal);
        livraison.expedition(database);
        
    }

    /**
     * @param contenants
     * @param nomProduit
     * @param database
     * @return un produit vendu
     */
    public ProduitsVendus vendus(List<Contenant> contenants,String nomProduit,Database database){
        String nomProduitVendus="";
        double ventes=0;
        double coutant=0;
        double emballage=0;
        double profits;
        for(int i=0; i<contenants.size(); i++){
            if(contenants.get(i).nomProduit(database).equals(nomProduit)){
                nomProduitVendus=nomProduit;
                if(contenants.get(i).isSachet()){
                    emballage=emballage+contenants.get(i).retournerTypeSachet().getPrixSachets();
                }
                else{
                    emballage=emballage+contenants.get(i).retournerTypePots().getPrixPot();
                }
                for(int k=0; k<database.getProduits().size();k++){
                  if(database.getProduits().get(k).getDescription().equals(nomProduit)){
                      ventes=ventes+contenants.get(i).getQuantiteVersee()*database.getProduits().get(k).getPrixUntiaire();
                      coutant=coutant+contenants.get(i).getQuantiteVersee()*database.getProduits().get(k).getPrixCoutant();
                  }
                }
            }
        }
        profits=ventes-(coutant+emballage);
        if(ventes!=0&&coutant!=0){
            ProduitsVendus produitvendu=new ProduitsVendus(nomProduitVendus,ventes,coutant,emballage,profits);
            return produitvendu;
        }
    else{
        return null;
        }

    }

    /**
     * @param database
     * méthode ecrivant les factures des paniers
     */
        public void fichierFacture(Database database){
            List<Facture> factures=new ArrayList<>();
        for(int i=0; i<paniers.size();i++){
            factures.add(paniers.get(i).calculerFacture(database));
            }
            EcrireFactures ecrireFacture=new EcrireFactures(factures);
            ecrireFacture.ecrireFichier();
        }

    /**
     * methode additionnant les memes produits dans une commande
     * @param code
     * @param produits
     * @param quantite
     * @return
     */
    public double additionnerMemeProduitcommande(int code, List<PanierProduits> produits,double quantite ){
        for(int i=0; i<produits.size(); i++){
            if(produits.get(i).getCode()==code){
                quantite=quantite+produits.get(i).getQuantite();
            }
        }
        return quantite;
    }

    /**
     * @return la date actuelle
     */
    public String dateActuelle(){
        String dateTexte;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date maintenant = Date.from(Instant.now());
        dateTexte = formatDate.format(maintenant);
        return dateTexte;
    }

    /**
     * @param tempsEcoules formatte un long en String
     * @return
     */
    public String formatterDateString(Long tempsEcoules){
        String dateTexte="";
        SimpleDateFormat formatDate= new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateTexte=formatDate.format(tempsEcoules);
        return dateTexte;
    }

    /**
     * @param dateTexte
     * converti un long en String
     * @return
     */
    public Long convertirDateLong(String dateTexte){
        long dateLong=0;
        Date date;
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm z");
        formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            date = formatDate.parse(dateTexte);
            dateLong = date.getTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLong;
    }

    /**
     * @param tempsEcoules verifie si la date du panier est valide par rapport à la date actuelle et la date de creation du site
     * @return
     */
    public boolean dateValide(long tempsEcoules){
        boolean valide=false;
        if(tempsEcoules>=convertirDateLong(dateCreation)&&tempsEcoules<=convertirDateLong(dateActuelle())){
            valide=true;
        }
        return valide;
    }

    /**
     * @param unite converti poids des produits solides dans les paniers en kg
     * @param quantite
     * @return
     */
    public double conversionPoidsMax(String unite,double quantite){
        if(unite.equals("mg")){
            quantite=quantite/1000000;
        }
        else if(unite.equals("g")){
            quantite=quantite/1000;
        }
        return quantite;
    }

    /**
     * @param unite converti poids des produits liquides dans les paniers en L
     * @param quantite
     * @return
     */
    public double conversionVolumeMax(String unite,double quantite){
        if(unite.equals("ml")){
            quantite=quantite/1000;
        }
        else if(unite.equals("cl")){
            quantite=quantite/100;
        }
        return quantite;
    }

    /**
     * @param unite
     * @return boolean si le produit est solide
     */
    public boolean isSolide(String unite){
        boolean solide=false;
        for(int k=0; k< Poids.values().length; k++){
            if(Poids.values()[k].getUnitePoids().equalsIgnoreCase(unite)){
                solide=true;
            }
        }
        return solide;
    }

    /**
     * @param panier
     * @return boolean si le panier à été traité
     */
    public boolean paDejaTraite(Panier panier){
        boolean transactionUnique=true;
        for(int i=0; i<paniers.size(); i++){
            if(paniers.get(i).getIdentifiantTransaction().equalsIgnoreCase(panier.getIdentifiantTransaction())){
                transactionUnique=false;
                i=paniers.size();
            }
        }
        return transactionUnique;
    }

    /**
     * @param database
     * @param code
     * @returnv le nom du produit
     */
    public String nomProduit(Database database, int code){
        String nom="";
        for(int i=0; i<database.getProduits().size();i++){
            if(database.getProduits().get(i).getCodeProduit()==code){
                nom=database.getProduits().get(i).getDescription();
            }
        }
        return nom;
    }

    /**
     * @param transaction
     * @return boolean si idTransaction est valide
     */
    public boolean identifiantTransactionValide(String transaction){
         boolean identifiantValide=false;
         if(transaction.substring(0,3).equalsIgnoreCase("VAZ")&&transaction.length()==11){
            identifiantValide=true;
         }
            return identifiantValide;
    }

    /**
     * @param database
     * @param identifianClient
     * @return boolean si le client existe
     */
        public boolean clientExistant(Database database, String identifianClient){
            boolean existant=false;
            for(int i=0; i<database.getClients().size(); i++){
                if(database.getClients().get(i).getIdentifiantClients().equalsIgnoreCase(identifianClient)){
                    existant=true;
                }
            }
       return existant; }

    /**
     * @param identifiantClient
     * @return boolean si id client valide
     */
    public boolean identifiantClientValide(String identifiantClient){
        boolean identifiantValide=false;
        if(identifiantClient.substring(0,2).equalsIgnoreCase("CL")&& identifiantClient.length()==10){
            identifiantValide=true;
        }
        return identifiantValide;
    }

    /**
     * @param codeProduit
     * @param database
     * @return boolean si produit existe dans la liste de produit
     */
    public boolean produitValide(int codeProduit, Database database){
        boolean existe=false;
        for(int i=0; i<database.getProduits().size();i++){
            if(database.getProduits().get(i).getCodeProduit()==codeProduit) {
                existe=true;
            }
            }
        return existe;
    }

    /**
     * @param unite
     * @param database
     * @param codeProduit
     * @return boolean si l'unité est valide
     */
    public boolean uniteValide(String unite,Database database,int codeProduit){
        boolean valide=false;
        for(int i=0; i<database.getProduits().size(); i++){
            if(database.getProduits().get(i).getCodeProduit()==codeProduit) {
                if(database.getProduits().get(i).isProduitSolide()){
                for(int k=0; k< Poids.values().length; k++)
                    if(Poids.values()[k].getUnitePoids().equalsIgnoreCase(unite)){
                    valide = true;}
                }
                else{
                    for(int j=0; j<Volume.values().length; j++){
                        if(Volume.values()[j].getUniteVolume().equalsIgnoreCase(unite)){
                            valide = true;}
                    }
                }
            }
        }
        return valide;
    }

    /**
     * @param quantite
     * @param code
     * @param database
     * @return boolean si la quantité est valide
     */
    public boolean quantiteValide(double quantite, int code, Database database){
        boolean valide=false;
        for(int i=0; i<database.getProduits().size(); i++){
            if(database.getProduits().get(i).getCodeProduit()==code) {
                if (quantite<=database.getProduits().get(i).getQuantiteMax() && quantite > 0) {
                    valide = true;
                }
            }
        }
    return valide;}

}
