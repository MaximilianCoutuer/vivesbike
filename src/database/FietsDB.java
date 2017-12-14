package database;

import databag.Fiets;
import database.connect.ConnectionManager;
import datatype.Status;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FietsDB implements InterfaceFietsDB {

    @Override
    public Integer toevoegenFiets(Fiets fiets) throws DBException {

        if(fiets == null) {
            //throw new ApplicationException();
        }
        
        try(Connection conn = ConnectionManager.getConnection();)
        {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "insert into fiets (registratienummer"
                            + ", status"
                            + ", standplaats"
                            + ", opmerkingen"
                            + ") values(?,?,?,?)",
                    Statement.NO_GENERATED_KEYS);)
            {
                stmt.setString(1, fiets.getRegistratienummer().toString());
                stmt.setString(2, fiets.getStatus().name());
                stmt.setString(3, fiets.getStandplaats().name());
                stmt.setString(4, fiets.getOpmerking());
                stmt.execute();
            }
            catch(SQLException e) {
                throw new DBException("SQL-exception in toevoegenFiets - statement" + e);
            }
        }
        catch(SQLException e) {
            throw new DBException("SQL-exception in toevoegenFiets - connection" + e);
        }
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
