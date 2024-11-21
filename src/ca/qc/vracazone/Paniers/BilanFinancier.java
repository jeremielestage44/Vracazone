package ca.qc.vracazone.Paniers;
import java.util.List;
public class BilanFinancier {
    List<ProduitsVendus> bilan;

    /**
     * @param bilan crée le bilan avec comme attribut une liste de produits vendus
     */
    public BilanFinancier(List<ProduitsVendus> bilan) {
        this.bilan = bilan;
    }

    /**
     * affiche le bilan sur la console
     */
    public void afficherBilan(){
        double ventesTotales=0;
        double coutantTotal=0;
        double emballageTotal=0;
        double profitsTotal=0;
        String total="TOTAL";
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.print("PRODUITS                 ");
        System.out.print("VENTES     ");
        System.out.print("COUTANT     ");
        System.out.print("EMBALLAGE     ");
        System.out.print("PROFITS");
        System.out.println();
        for(int i=0; i<bilan.size(); i++) {
            double ventes=Math.round(bilan.get(i).getVentes()*100)/100.0;
            double coutant=Math.round(bilan.get(i).getCoutant()*100)/100.0;
            double emballe=Math.round(bilan.get(i).getEmballage()*100)/100.0;
            double profits=Math.round(bilan.get(i).getProfits()*100)/100.0;
            System.out.printf("%-25s ",bilan.get(i).getNomProduit());
            System.out.printf("%5.2f",ventes);
            ventesTotales = ventesTotales + ventes;// additionner les prix de ventes , coutant, l'emballage et les profits pour fair le total
            System.out.printf("%11.2f",coutant);
            coutantTotal = coutantTotal + coutant;
            System.out.printf("%14.2f",emballe);
            emballageTotal = emballageTotal + emballe;
            System.out.printf("%13.2f",profits);
            profitsTotal = profitsTotal + profits;
            System.out.println();
        }
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-25s ",total);
        System.out.printf("%5.2f",ventesTotales);
        System.out.printf("%11.2f",coutantTotal);
        System.out.printf("%14.2f",emballageTotal);
        System.out.printf("%13.2f",profitsTotal);



    }
}
