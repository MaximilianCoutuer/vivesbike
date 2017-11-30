package database;

import databag.Fiets;
import datatype.Status;
import exception.DBException;
import java.util.ArrayList;

public class FietsDB implements InterfaceFietsDB {

    @Override
    public Integer toevoegenFiets(Fiets fiets)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void wijzigenToestandFiets(Integer regnr, Status status)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void wijzigenOpmerkingFiets(Integer regnr, String opmerking)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Fiets zoekFiets(Integer regnr)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Fiets> zoekAlleFietsen()  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
