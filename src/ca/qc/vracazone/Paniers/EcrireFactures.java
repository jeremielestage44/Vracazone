package ca.qc.vracazone.Paniers;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class EcrireFactures {
    List<Facture> factures;

    /**
     * @param factures Créé classe avec comme attribut une liste de facture
     */
    public EcrireFactures(List<Facture> factures){
        this.factures = factures;

    }

    /**
     * ecrire les factures dans le fichier txt factures.txt
     */
    public void ecrireFichier(){
        try (PrintWriter fichier=new PrintWriter(new FileOutputStream("factures.txt"))){
            for(int i=0; i<factures.size();i++){
                fichier.println(factures.get(i).getIdentifiantTransaction()+"|"+factures.get(i).getDate()+"|"+factures.get(i).getSousTotal()+"$|"+factures.get(i).getTaxes()+"$|"+factures.get(i).getTotal()+"$");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
