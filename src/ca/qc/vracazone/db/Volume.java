package ca.qc.vracazone.db;

public enum Volume {
    MILLILITRES("ml"),
    CENTILITRES("cl"),
    LITRES("L");


    private final String uniteVolume;

    public String getUniteVolume() {
        return uniteVolume;
    }

    Volume(String uniteVolume) {
        this.uniteVolume = uniteVolume;
    }


}
