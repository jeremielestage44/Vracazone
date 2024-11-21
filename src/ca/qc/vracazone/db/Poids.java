package ca.qc.vracazone.db;

public enum Poids {
    MILLIGRAMMES("mg"),
    GRAMMES("g"),
    KILOGRAMMES("kg");

    /**
     * @return l'unite de poids soit un String
     */
    public String getUnitePoids() {
        return unitePoids;
    }

    private final String unitePoids;

    /**
     * @param unitePoids enum des differentes unites de poids
     */
    Poids(String unitePoids) {
        this.unitePoids=unitePoids;
    }
}
