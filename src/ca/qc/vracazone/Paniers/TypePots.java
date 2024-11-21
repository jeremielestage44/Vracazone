package ca.qc.vracazone.Paniers;

public enum TypePots {
    EXAGERE(20,"pot exagéré",5.75),
    GRAND(2,"grand pot", 0.70),
    PETIT(0.5,"petit pot", 0.50),
    MINUSCULE(0.1,"minuscule pot", 0.30)
    ;

private final double quantiteMax;
    private final String nomPot;

    private final double prixPot;

    /**
     * @return son prix
     */
    public double getPrixPot() {
        return prixPot;
    }

    /**
     * @return son nom
     */
    public String getNomPot() {
        return nomPot;
    }

    /**
     * @return sa quantité maximum
     */
    public double getQuantiteMax() {
        return quantiteMax;
    }


    /**
     * @param quantiteMax quantité maximum dans un pot de ce type
     * @param nomPot String du nom du pot de ce type
     * @param prixPot double du prix du nom de ce type
     */
    TypePots(double quantiteMax, String nomPot, double prixPot) {
        this.quantiteMax = quantiteMax;
        this.nomPot=nomPot;
        this.prixPot = prixPot;
    }
}
