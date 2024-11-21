package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

import java.util.ArrayList;
import java.util.List;

public class PanierProduits {


    private int code;

    public int getCode() {
        return code;
    }

    private double quantite;

    public double getQuantite() {
        return quantite;
    }

    private String unite;

    private String decription;

    /** crée un produit dans un panier avec ses arguments
     * @param code
     * @param quantite
     * @param unite
     * @param description
     */
    public PanierProduits(int code, double quantite, String unite,String description) {
        this.code = code;
        this.quantite = quantite;
        this.unite=unite;
        this.decription=description;
    }

    /**
     * @return affiche produit
     */
    @Override
    public String toString() {
        return quantite+" "+unite+" de "+decription;
    }

    /**
     * @param database
     * @return prix du produit acheté dependant de sa quantité
     */
    public double prixProduitAchete(Database database){
        double prix=0;
        for(int i=0; i<database.getProduits().size(); i++){
            if(database.getProduits().get(i).getCodeProduit()==code){
                prix=quantite*database.getProduits().get(i).getPrixUntiaire();

            }
        }
        return prix;
    }

    /**
     * @param database
     * @return taxes sur le produit acheté
     */
    public double taxeProduitAchete(Database database){
        double taxe=0;
        double prix;
        for(int i=0; i<database.getProduits().size(); i++){
            if(database.getProduits().get(i).getCodeProduit()==code&&!database.getProduits().get(i).isProduitAlimentaire()){
                prix=quantite*database.getProduits().get(i).getPrixUntiaire();
                taxe=prix*0.15;

            }
        }
        return taxe;
    }

    /**
     * @return transfère des produits dans des contenants
     */
    public Contenant remplirContenant() {
        Contenant contenant=new Sachets(quantite,code,TypeSachets.values()[0]);
        if (unite.equals("kg")) {
                for (int i = 0; i < TypeSachets.values().length; i++) {
                    if(i==TypeSachets.values().length-1&&quantite<TypeSachets.values()[i].getQuantiteMax()/2&&quantite!=0){
                        contenant =new Sachets(quantite,code,TypeSachets.values()[i]);
                        quantite=0;
                    }
                    else if(quantite>TypeSachets.values()[i].getQuantiteMax()){
                        contenant=new Sachets(TypeSachets.values()[i].getQuantiteMax(),code,TypeSachets.values()[i]);
                        quantite=quantite-TypeSachets.values()[i].getQuantiteMax();
                        i=TypeSachets.values().length;

                    }
                    else if(quantite>=TypeSachets.values()[i].getQuantiteMax()/2){
                        contenant=new Sachets(quantite,code,TypeSachets.values()[i]);
                        quantite=0;

                    }
                  else  if (quantite==TypeSachets.values()[i].getQuantiteMax()){
                        contenant=new Sachets(quantite,code,TypeSachets.values()[i]);
                        quantite=0;

                    }
                }
            }
            else {
                for(int k = 0; k < TypePots.values().length; k++){
                    if(k==TypePots.values().length-1&&quantite<TypePots.values()[k].getQuantiteMax()/2&&quantite!=0){
                        contenant =new Pots(quantite,code,TypePots.values()[k]);
                        quantite=0;
                    }
                    else if(quantite>TypePots.values()[k].getQuantiteMax()){
                        contenant=new Pots(TypePots.values()[k].getQuantiteMax(),code,TypePots.values()[k]);
                        quantite=quantite-TypePots.values()[k].getQuantiteMax();
                        k=TypePots.values().length;
                    }
                    else if(quantite>=TypePots.values()[k].getQuantiteMax()/2){
                        contenant =new Pots(quantite,code,TypePots.values()[k]);
                        quantite=0;

                    }
                   else if (quantite==TypePots.values()[k].getQuantiteMax()){
                        contenant=new Pots(quantite,code,TypePots.values()[k]);
                        quantite=0;

                    }
                }
            }
            return contenant;
    }


}
