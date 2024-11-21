package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Colis {

    private String idTransaction;

    /**
     * @return id transaction
     */
    public String getIdTransaction() {
        return idTransaction;
    }

    private String idClient;

    /**
     * @return id client
     */
    public String getIdClient() {
        return idClient;
    }

    private Facture facture;

    private Stack<Contenant> pile;

    private List<Contenant> liste;

    /** créé colis avec ses arguments
     * @param idTransaction
     * @param idClient
     * @param facture
     * @param pile
     * @param liste
     */
    public Colis(String idTransaction,String idClient,Facture facture, Stack<Contenant> pile, List<Contenant> liste) {
        this.idTransaction=idTransaction;
        this.idClient=idClient;
        this.facture=facture;
        this.pile = pile;
        this.liste = liste;
    }

    /**
     * met les differents colis dans le fichier registre.txt
     */
    public void EcrireFichier(){
        try (PrintWriter fichier = new PrintWriter(new FileOutputStream("registre.txt"))){
            fichier.println(idTransaction);
            fichier.print("      Sachets:[");
            for(int i=0; i< pile.size(); i++) {
                fichier.print(pile.get(i).compteur);
                if (i < pile.size() - 1) {
                    fichier.print(", ");
                }
            }
                fichier.print("]");
                fichier.println();

            fichier.print("      Pots:[");
            for(int k=0; k<liste.size(); k++){
                fichier.print(liste.get(k).compteur);
                if(k<liste.size()-1){
                    fichier.print(", ");
                }
            }
            fichier.print("]");
    } catch (
    FileNotFoundException e) {
        System.out.println(e.getMessage());
    }
    }

}
