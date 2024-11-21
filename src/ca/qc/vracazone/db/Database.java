package ca.qc.vracazone.db;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String CLIENTS_DAT="fichiers/clients.dat";//nom du fichier binaire client

    private static final String PRODUITS_DAT="fichiers/produits.dat";// nom du fichier binaire produits

    private final List<Client> clients= new ArrayList<>();// la liste de client

    /**
     * @return la liste de client
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * @return la liste de produit
     */
    public List<Produit> getProduits() {
        return produits;
    }

    private List<Produit> produits= new ArrayList<>();// attribut liste de produits

    /**
     * initialise les listes de produits et de clients
     */
    public Database (){
        try(RandomAccessFile lecteur= new RandomAccessFile(CLIENTS_DAT,"r")){
            while(lecteur.getFilePointer()< lecteur.length()){
            String identifiant =lecteur.readUTF();
            String nom= lecteur.readUTF();// lit les attributs clients du fichier CLIENT_DAT
            if(nom.length()>=3){// si le nom à au moins 3 caractères
            Client client= new Client(identifiant,nom);// crée le client
            if(identifiantClientUnique(client)){// si son identifiant est unique, l'ajouter à la file
            clients.add(client);}}
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try(RandomAccessFile fichier= new RandomAccessFile(PRODUITS_DAT,"r")){
            while(fichier.getFilePointer()< fichier.length()) {
                int code =fichier.readInt();
                String descripion=fichier.readUTF();
                boolean alimentaire= fichier.readBoolean();
                boolean solide=fichier.readBoolean();
                double cout=fichier.readDouble();
                double unitaire= fichier.readDouble();
                String unite= fichier.readUTF();
                double max=fichier.readDouble();// lit les attributs de produits dans le fichier binaire PRODUIT_DAT
                if(solide){
                  max=conversionPoidsMax(unite,max);// converti son unité et sa quantite max en kg
                  unite="kg";
                }
                else{
                    max=conversionVolumeMax(unite,max);// converti son unité et sa quantité en L
                    unite="L";
                }
                if(cout*1.2<unitaire&&uniteProduit(solide,unite)) {//si cout est 1.2 fois petit que son prix unitaire et unité valide
                    Produit produit = new Produit(code, descripion, alimentaire, solide, cout, unitaire, unite, max);
                    if (codeProduitUnique(produit)) {// si son codeProduit unique et si oui on l'ajoute à la liste de produits
                        produits.add(produit);
                    }
                }
            }

        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * @param client parcour la liste de clients et si un pareil à celui qu'on lit deient false
     * @return boolean si le client est unique
     */
    public boolean identifiantClientUnique(Client client){
        boolean clientUnique=true;
        for(int i=0; i<clients.size(); i++){
            if(clients.get(i).getIdentifiantClients().equalsIgnoreCase(client.getIdentifiantClients())){
                clientUnique=false;
                i= clients.size();
            }
        }
        return clientUnique;
    }

    /**
     * @param produit prend un produit et parcours la liste pour voir si un avec le même code et si oui, devient false
     * @return boolean produit unique
     */
    public boolean codeProduitUnique(Produit produit){
        boolean produitUnique=true;
        for(int i=0; i<produits.size(); i++){
            if(produits.get(i).getCodeProduit()==produit.getCodeProduit()){
                produitUnique=false;
                i= produits.size();
            }
        }
    return produitUnique;}

    /**
     * @param solide regarde si le produit est solide ou non
     * @param unite regarde si l'unite existe dans les enums
     * @return true si l'unite existe
     */
    public boolean uniteProduit(boolean solide, String unite){
        boolean  uniteAdequate=false;
        if(solide){
            for(int i=0; i<Poids.values().length; i++){// parcours l'enum de poids
                if(Poids.values()[i].getUnitePoids().equalsIgnoreCase(unite)){
                    uniteAdequate=true;
                    i=Poids.values().length;
                }
            }
        }
        else {
            for (int i = 0; i < Volume.values().length; i++) {// parcours l'enum de volume
                if (Volume.values()[i].getUniteVolume().equalsIgnoreCase(unite)) {
                    uniteAdequate = true;
                    i = Volume.values().length;
                }
            }
        }

    return uniteAdequate;}

    /**
     * @param unite convertit la quantite de poids en une autre unité soit kg
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
     * @param unite convertit la quantité de volume en une autre unité soit L
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

}
