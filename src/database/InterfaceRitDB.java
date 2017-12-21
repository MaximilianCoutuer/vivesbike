package database;

import databag.Rit;
import java.util.ArrayList;

/**
 *
 * @author Katrien.Deleu
 */
public interface InterfaceRitDB {

    Integer toevoegenRit(Rit rit) throws Exception;

    void afsluitenRit(Rit rit) throws Exception;

    ArrayList zoekAlleRitten() throws Exception;

    Rit zoekRit(Integer ritID) throws Exception;

    int zoekEersteRitVanLid(String rr) throws Exception;

    ArrayList zoekActieveRittenVanLid(String rr) throws Exception;

    ArrayList zoekActieveRittenVanFiets(Integer regnr) throws Exception;

}
