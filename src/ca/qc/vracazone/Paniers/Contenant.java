package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

public abstract class Contenant {

    protected double quantiteVersee;

    /**
     * @return quantité versée
     */
    public double getQuantiteVersee() {
        return quantiteVersee;
    }
    protected int code;

    protected int compteur;


    protected String unite;


    /** classe abstraite contenant avec ses unités
     * @param quantiteVersee
     * @param unite
     * @param code
     * @param compteur
     */
    public Contenant(double quantiteVersee, String unite, int code, int compteur) {
        this.quantiteVersee=quantiteVersee;
        this.unite=unite;
        this.code=code;
        this.compteur=compteur;
    }
    public abstract void visser(Database database, int compteurSachets, int compteurPots);


    public abstract String nomProduit(Database database);

    public abstract boolean isSachet();

    public abstract void afficherCompteur();

    public abstract TypeSachets retournerTypeSachet();

    public abstract TypePots retournerTypePots();
}

