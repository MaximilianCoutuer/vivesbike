package database;

import databag.Lid;
import java.util.ArrayList;

/**
 *
 * @author Katrien.Deleu
 */
public interface InterfaceLidDB {

    void toevoegenLid(Lid lid) throws Exception;

    void wijzigenLid(Lid lid) throws Exception;

    void uitschrijvenLid(String rr) throws Exception;

    Lid zoekLid(String rijksregisternummer) throws Exception;

    public ArrayList<Lid> zoekAlleLeden() throws Exception;

}
