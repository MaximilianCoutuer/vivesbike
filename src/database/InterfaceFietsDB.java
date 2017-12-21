package database;

import databag.Fiets;
import datatype.Status;
import exception.DBException;
import java.util.ArrayList;

/**
 *
 * @author Katrien.Deleu
 */
public interface InterfaceFietsDB {

    Integer toevoegenFiets(Fiets fiets) throws DBException;

    void wijzigenToestandFiets(Integer regnr, Status status) throws Exception;

    void wijzigenOpmerkingFiets(Integer regnr, String opmerking) throws Exception;

    Fiets zoekFiets(Integer regnr) throws Exception;

    ArrayList<Fiets> zoekAlleFietsen() throws Exception;

}
