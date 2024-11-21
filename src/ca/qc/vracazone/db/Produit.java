package ca.qc.vracazone.db;

public class Produit {

    private int codeProduit;

    /**
     * @return son attribut codeProduit
     */
    public int getCodeProduit() {
        return codeProduit;
    }

    private String description;

    /**
     * @return retourne sa decription
     */
    public String getDescription() {
        return description;
    }

    private boolean produitAlimentaire;

    /**
     * @return si c'est un produit alimentaire ou non
     */
    public boolean isProduitAlimentaire() {
        return produitAlimentaire;
    }

    private boolean produitSolide;

    /**
     * @return si c'est un produit solide ou non
     */
    public boolean isProduitSolide() {
        return produitSolide;
    }

    private double prixCoutant;

    /**
     * @return son prix coutant
     */
    public double getPrixCoutant() {
        return prixCoutant;
    }


    private double prixUntiaire;

    /**
     * @return son prix unitaire
     */
    public double getPrixUntiaire() {
        return prixUntiaire;
    }

    private String unitePoidsVolume;

    private double quantiteMax;

    /**
     * @return sa quantite max dans une commande
     */
    public double getQuantiteMax() {
        return quantiteMax;
    }

    /**Crée un objet produit avec comme attributs
     * @param codeProduit son code de produit
     * @param description sa description
     * @param produitAlimentaire si c'est un produit alimentaire
     * @param produitSolide si c'est un produit solide
     * @param prixCoutant son prix coutant
     * @param prixUntiaire son prix unitaire
     * @param unitePoidsVolume son unite de poids et volume
     * @param quantiteMax
     */
    public Produit(int codeProduit, String description, boolean produitAlimentaire, boolean produitSolide, double prixCoutant, double prixUntiaire, String unitePoidsVolume, double quantiteMax) {
        this.codeProduit = codeProduit;
        this.description = description;
        this.produitAlimentaire = produitAlimentaire;
        this.produitSolide = produitSolide;
        this.prixCoutant = prixCoutant;
        this.prixUntiaire = prixUntiaire;
        this.unitePoidsVolume = unitePoidsVolume;
        this.quantiteMax = quantiteMax;
    }

    /**
     * @return affiche le produit avec ses attributs
     */
    @Override
    public String toString() {
        return codeProduit+"|"+description+"|"+produitAlimentaire+"|"+produitSolide
                +"|"+prixCoutant+"|"+prixUntiaire+"|"+unitePoidsVolume+"|"+quantiteMax;
    }
}
