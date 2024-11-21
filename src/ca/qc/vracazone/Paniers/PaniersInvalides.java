package ca.qc.vracazone.Paniers;

public class PaniersInvalides {

    private String identifiantTransaction;

    private String raisonDuRejet;

    /** créé panier invalide avec ses arguments
     * @param identifiantTransaction
     * @param raisonDuRejet
     */
    public PaniersInvalides(String identifiantTransaction, String raisonDuRejet) {
        this.identifiantTransaction = identifiantTransaction;
        this.raisonDuRejet = raisonDuRejet;
    }

    /**
     * @return affiche panier invalide
     */
    @Override
    public String toString() {
        return identifiantTransaction+":   "+raisonDuRejet+"\n";
    }
}
