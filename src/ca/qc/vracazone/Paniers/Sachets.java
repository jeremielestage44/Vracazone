package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

public  class Sachets extends Contenant {
    private TypeSachets type;


    /** créé sachet avec ses attributs
     * @param quantiteVersee
     * @param code
     * @param type
     */
    public Sachets(double quantiteVersee, int code, TypeSachets type) {
        super(quantiteVersee,"Kg",code,1);
        this.type=type;
    }

    /**
     * @param database
     * @return nom du produit dans le sachet
     */
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
     * @return true car est un sachet
     */
    @Override
    public boolean isSachet() {
        return true;
    }

    @Override
    public void afficherCompteur() {
        System.out.println(compteur);
    }

    /**
     * @return type de sachet
     */
    @Override
    public TypeSachets retournerTypeSachet() {
        return type;
    }

    /**
     * @return rien car pas un pot
     */
    @Override
    public TypePots retournerTypePots() {
        return null;
    }

    /** verse et scelle le sachet
     * @param database
     * @param compteurSachets
     * @param compteurPots
     */
    @Override
    public void visser(Database database,int compteurSachets, int compteurPots) {
        System.out.println("Verser "+Math.round(quantiteVersee*100)/100.0+" Kg de "+nomProduit(database)+" dans "+type.getNomSachet()+" de "+type.getQuantiteMax()+" kg de capacité (sachet "+compteurSachets+") ");
        System.out.println("sceller sachet "+compteurSachets);
        compteur=compteurSachets;
    }

}
