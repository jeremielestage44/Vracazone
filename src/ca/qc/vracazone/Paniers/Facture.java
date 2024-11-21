package ca.qc.vracazone.Paniers;

import java.io.PrintWriter;

public class Facture {

    private String identifiantTransaction;

    /**
     * @return identifiant de transaction
     */
    public String getIdentifiantTransaction() {
        return identifiantTransaction;
    }

    private String date;

    /**
     * @return date
     */
    public String getDate() {
        return date;
    }

    private double sousTotal;

    /**
     * @return soustotal
     */
    public double getSousTotal() {
        return sousTotal;
    }

    private double taxes;

    /**
     * @return taxes
     */
    public double getTaxes() {
        return taxes;
    }

    private double total;

    /**
     * @return total
     */
    public double getTotal() {
        return total;
    }

    /** créé un objet facture avec ses arguments
     * @param identifiantTransaction
     * @param date
     * @param sousTotal
     * @param taxes
     * @param total
     */
    public Facture(String identifiantTransaction, String date, double sousTotal, double taxes, double total) {
        this.identifiantTransaction = identifiantTransaction;
        this.date=date;
        this.sousTotal = sousTotal;
        this.taxes = taxes;
        this.total = total;
    }

}
