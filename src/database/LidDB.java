package database;

import databag.Lid;
import database.connect.ConnectionManager;
import exception.DBException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class LidDB implements InterfaceLidDB {

    @Override
    public void toevoegenLid(Lid lid) throws DBException
    {
        if(lid == null)
        {
            // exceptie?
        }
        
        try(Connection conn = ConnectionManager.getConnection();)
        {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "insert into lid (rijksregisternummer" +
                    ", naam" +
                    ", voornaam" +
                    ", geslacht" +
                    ", telnr" +
                    ", emailadres" +
                    ", start_lidmaatschap" +
                    ", einde_lidmaatschap" +
                    ", opmerkingen" +
                    ") values(?,?,?,?,?,?,?,?,?)",
                    Statement.NO_GENERATED_KEYS);)
            {
                stmt.setString(1, lid.getRijksregisternummer());
                stmt.setString(2, lid.getNaam());
                stmt.setString(3, lid.getVoornaam());
                stmt.setString(4, lid.getGeslacht().name());
                stmt.setString(5, lid.getTelnr());
                stmt.setString(6, lid.getEmailadres());
                stmt.setDate(7, Date.valueOf(lid.getStart_lidmaatschap()));
                stmt.setDate(8, Date.valueOf(lid.getEinde_lidmaatschap()));
                stmt.setString(9, lid.getOpmerkingen());
                stmt.execute();
            }
            catch(SQLException e)
            {
                throw new DBException("SQL-exception in toevoegenKlant - statement" + e);
            }
        }
        catch(SQLException e)
        {
            throw new DBException("SQL-exception in toevoegenLid - connection" + e);
        }
    }

    @Override
    public void wijzigenLid(Lid lid)  {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void uitschrijvenLid(String rr)  {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Lid zoekLid(String rijksregisternummer)  {
        return null;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Lid> zoekAlleLeden()  {
        return null;//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
  
}
