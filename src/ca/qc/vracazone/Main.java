package ca.qc.vracazone;

import ca.qc.vracazone.Paniers.ChargerPaniers;
import ca.qc.vracazone.db.Client;
import ca.qc.vracazone.db.Database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Database database=new Database();
        ChargerPaniers paniers=new ChargerPaniers(database);
        paniers.fichierFacture(database);
        paniers.actionRobots();

    }


    }
