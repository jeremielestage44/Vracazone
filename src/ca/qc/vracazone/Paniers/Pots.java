package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

public class Pots extends Contenant{
    private TypePots type;


    /** créé pot
     * @param quantiteVersee
     * @param code
     * @param type
     */
    public Pots(double quantiteVersee, int code, TypePots type) {
        super(quantiteVersee,"L",code,1);
        this.type=type;
    }

    /** Verser et visser Pot
     * @param database
     * @param compteurSachets
     * @param compteurPots
     */
    @Override
    public void visser(Database database,int compteurSachets, int compteurPots) {
        System.out.println("Verser "+Math.round(quantiteVersee*100)/100.0+" L de " +nomProduit(database)+" dans un "+type.getNomPot()+" de "+type.getQuantiteMax()+" L de capacité (pot " +compteurPots+") ");
        System.out.println("Visser couvercle pot "+compteurPots);
        compteur=compteurPots;

    }

    /**
     * @param database
     * @return nom du produit
     */
    @Override
    public String nomProduit(Database database) {
        String nom="";
        for(int i=0; i<database.getProduits().size();i++){
            if(database.getProduits().get(i).getCodeProduit()==code){
                nom=database.getProduits().get(i).getDescription();
            }
        }
        return nom;
    }

    /**
     * @return false car se n'est pas un sachet
     */
    @Override
    public boolean isSachet() {
        return false;
    }

    @Override
    public void afficherCompteur() {
        System.out.println(compteur);
    }

    /**
     * @return rien car pas un sachet
     */
    @Override
    public TypeSachets retournerTypeSachet() {
        return null;
    }

    /**
     * @return type de pot
     */
    @Override
    public TypePots retournerTypePots() {
        return type;
    }
}

