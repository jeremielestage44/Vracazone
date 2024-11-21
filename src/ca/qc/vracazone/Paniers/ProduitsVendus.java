package ca.qc.vracazone.Paniers;

public class ProduitsVendus {

    private String nomProduit;

    public String getNomProduit() {
        return nomProduit;
    }

    private double ventes;

    public double getVentes() {
        return ventes;
    }

    private double coutant;

    public double getCoutant() {
        return coutant;
    }

    private double emballage;

    public double getEmballage() {
        return emballage;
    }

    private double profits;

    public double getProfits() {
        return profits;
    }

    /** créé produit vendu
     * @param nomProduit
     * @param ventes
     * @param coutant
     * @param emballage
     * @param profits
     */
    public ProduitsVendus(String nomProduit, double ventes, double coutant, double emballage, double profits) {
        this.nomProduit = nomProduit;
        this.ventes = ventes;
        this.coutant = coutant;
        this.emballage = emballage;
        this.profits = profits;
    }
}
