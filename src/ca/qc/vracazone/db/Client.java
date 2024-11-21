package ca.qc.vracazone.db;

public class Client {

    /**
     * @return identifiant client;
     */
    public String getIdentifiantClients() {
        return identifiantClients;
    }

    private String identifiantClients;

    private String nomClients;

    /**
     * @return nom complet du client;
     */
    public String getNomClients() {
        return nomClients;
    }

    /**
     * @return affiche le client avec son identifiant suivi de son nom complet;
     */
    @Override
    public String toString() {
        return identifiantClients+"|"+nomClients;
    }

    /** créé un client avec
     * @param identifiantClients son identifiant
     * @param nomClients son prnom et son nom de famille
     */
    public Client(String identifiantClients, String nomClients) {
        this.identifiantClients = identifiantClients;
        this.nomClients = nomClients;
    }
}
