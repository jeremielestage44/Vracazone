package ca.qc.vracazone.Paniers;

import ca.qc.vracazone.db.Database;

import java.util.List;

public class Robot {
    private List<Contenant> contenantPanier;

    /** créé un robot avec comme attribut
     * @param contenantPanier
     */
    public Robot(List<Contenant> contenantPanier) {
        this.contenantPanier = contenantPanier;
    }

    /** methode affichant les contenants sur la console
     * @param database
     * @param compteurSachet
     * @param compteurPots
     */
    public void afficherContenant(Database database,int compteurSachet,int compteurPots){
        for(int i=0; i<contenantPanier.size(); i++){
            if(contenantPanier.get(i).isSachet()){
            contenantPanier.get(i).visser(database,compteurSachet,compteurPots);
            compteurSachet++;
            }
            else{
                contenantPanier.get(i).visser(database,compteurSachet,compteurPots);
                compteurPots++;
            }
        }
    }

}
