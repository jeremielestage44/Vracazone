package ca.qc.vracazone.Paniers;
import ca.qc.vracazone.db.Database;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
public class Livraison {
    private List<Colis> colis;

    /**
     * @param colis créé une livraison avec un argument une liste de colis
     */
    public Livraison(List<Colis> colis) {
        this.colis = colis;
    }

    /**
     * @param database
     * ecrire les colis dans fichier txt livraison.txt
     */
    public void expedition(Database database){
        String nomClient="";
        try (PrintWriter fichier = new PrintWriter(new FileOutputStream("livraisons.txt"))){
                for(int i=0; i<colis.size(); i++){
                    for(int k=0; k<database.getClients().size(); k++){
                        if(database.getClients().get(k).getIdentifiantClients().equals(colis.get(i).getIdClient())){
                            nomClient=database.getClients().get(k).getNomClients();
                        }
                    }
                    fichier.print(colis.get(i).getIdTransaction()+"|");
                    fichier.print(colis.get(i).getIdClient()+"|");
                    fichier.println(nomClient);
                }
        } catch (
                FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
