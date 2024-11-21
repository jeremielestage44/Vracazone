package ca.qc.vracazone.Paniers;

public enum TypeSachets {

    GARGANTUESQUE(500,"sachet gargantuesque",15.75),
    TRESGRAND(100,"Très grand sachet",5.50),
    GRAND(20,"grand sachet",1.25),
    MOYEN(2,"moyen sachet",0.75),
    PETIT(0.5,"petit sachet",0.25),
    MINUSCULE(0.1,"sachet minuscule",0.15),
    LILlIPUTIEN(0.03,"sachet liliputien",0.02);

    private final double quantiteMax;

    private final String nomSachet;

    private final double prixSachets;

    /**
     * @return son prix
     */
    public double getPrixSachets() {
        return prixSachets;
    }

    /**
     * @return son nom
     */
    public String getNomSachet() {
        return nomSachet;
    }

    /**
     * @return sa quantite maximale
     */
    public double getQuantiteMax() {
        return quantiteMax;
    }

    /**
     * @param quantiteMax quantite maximale pour ce type de sachet
     * @param nomSachet nom du sachet pour ce type de sachet
     * @param prixSachets prix du sachet pour ce type de sachet
     */
    TypeSachets(double quantiteMax, String nomSachet, double prixSachets) {
        this.quantiteMax = quantiteMax;
        this.nomSachet=nomSachet;
        this.prixSachets=prixSachets;
    }
}
