package database;

import databag.Lid;
import database.connect.ConnectionManager;
import datatype.Geslacht;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import exception.DBException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class LidDB implements InterfaceLidDB 
{

    /**
     * Voegt een lid toe.
     * 
     * @param lid het lid dat toegevoegd moet worden
     * @throws exception.DBException bij een verkeerde installatie van de 
     * database of een fout in de query
     */
    @Override
    public void toevoegenLid(Lid lid) throws DBException
    {
        if(lid == null)
        {
            return;
            // exceptie gooien?
        }
        
        try(Connection connection = ConnectionManager.getConnection();)
        {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "insert into lid (rijksregisternummer"
                    + " , naam"
                    + " , voornaam"
                    + " , geslacht"
                    + " , telnr"
                    + " , emailadres"
                    + " , start_lidmaatschap"
                    + " , einde_lidmaatschap"
                    + " , opmerkingen"
                    + " ) values(?,?,?,?,?,?,?,?,?)"
            );)
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
            catch(SQLException ex)
            {
                throw new DBException("SQL-exception in toevoegenKlant - statement\n" + ex);
            }
        }
        catch(SQLException ex)
        {
            throw new DBException("SQL-exception in toevoegenLid - connection\n" + ex);
        }
    }

    /**
     * Wijzigt een lid adhv zijn rijksregisternummer.
     * 
     * @param lid het lid dat gewijzigd moet worden
     * @throws exception.DBException bij een verkeerde installatie van de
     * database of een fout in de query
     */
    @Override
    public void wijzigenLid(Lid lid) throws DBException 
    {
        if(lid == null)
        {
            return;
            // exceptie gooien?
        }
        
        try(Connection connection = ConnectionManager.getConnection();)
        {
            try(PreparedStatement stmt = connection.prepareStatement(
                    "update lid "
                    + " set naam = ?"
                    + " , voornaam = ?"
                    + " , geslacht = ?"
                    + " , telnr = ?"
                    + " , emailadres = ?"
                    + " , start_lidmaatschap = ?"
                    + " , einde_lidmaatschap = ?"
                    + " , opmerkingen = ?"
                    + " where rijksregisternummer = ?"
            );)
            {
                stmt.setString(1, lid.getNaam());
                stmt.setString(2, lid.getVoornaam());
                stmt.setString(3, lid.getGeslacht().name());
                stmt.setString(4, lid.getTelnr());
                stmt.setString(5, lid.getEmailadres());
                stmt.setDate(6, Date.valueOf(lid.getStart_lidmaatschap()));
                stmt.setDate(7, Date.valueOf(lid.getEinde_lidmaatschap()));
                stmt.setString(8, lid.getOpmerkingen());
                stmt.setString(9, lid.getRijksregisternummer());
                
                stmt.execute();
            }
            catch(SQLException ex)
            {
                throw new DBException("SQL-exception in wijzigenLid - statement\n" + ex);
            }
        }
        catch(SQLException ex)
        {
            throw new DBException("SQL-exception in wijzigenLid - connection\n" + ex);
        }
    }

    /**
     * Schrijft de klant met meegegeven rijksregisternummer uit.
     * 
     * @param rr rijksregisternummer van de klant die uitgeschreven moet worden
     * @throws exception.DBException  bij een verkeerde installatie van de 
     * database of een fout in de query
     */
    @Override
    public void uitschrijvenLid(String rr) throws DBException 
    {
        if(rr == null)
        {
            return;
            // exceptie gooien?
        }
        
        try(Connection connection = ConnectionManager.getConnection();)
        {
            try(PreparedStatement stmt = connection.prepareStatement(
                    "update lid"
                    + " set einde_lidmaatschap = ?"
                    + " where rijksregisternummer = ?"
            );)
            {
                stmt.setDate(1, Date.valueOf(LocalDate.now()));
                stmt.setString(2, rr);
                
                stmt.execute();
            }
            catch(SQLException ex)
            {
                throw new DBException("SQL-exception in uitschrijvenLid - statement\n" + ex);
            } 
        }
        catch(SQLException ex)
        {
            throw new DBException("SQL-exception in uitschrijvenLid - connection\n" + ex);
        }
    }

    /**
     * Zoek adhv het meegegeven rijksregisternummer een lid op.
     * Wanneer geen lid werd gevonden, wordt null teruggegeven.
     * 
     * @param rr rijksregisternummer van het lid die gezocht 
     * moet worden 
     * @return lid die gezocht wordt, null indien het lid niet werd gevonden 
     * @throws exception.DBException bij een verkeerde installatie van de 
     * database of een fout in de query
     * @throws exception.ApplicationException wanneer het opgehaalde lid een
     * ongeldig rijksregisternummer heeft
     */
    @Override
    public Lid zoekLid(String rr) throws DBException, ApplicationException
    {
        Lid resultaatLid = null;
        
        if(rr != null)
        {
            try(Connection connection = ConnectionManager.getConnection();)
            {
                try(PreparedStatement stmt = connection.prepareStatement(
                        "select rijksregisternummer"
                        + " , naam"
                        + " , voornaam"
                        + " , geslacht"
                        + " , telnr"
                        + " , emailadres"
                        + " , start_lidmaatschap"
                        + " , einde_lidmaatschap"
                        + " , opmerkingen"
                        + " from lid"
                        + " where rijksregisternummer = ?"
                );)
                {
                    stmt.setString(1, rr);

                    stmt.execute();

                    try(ResultSet resultSet = stmt.getResultSet();)
                    {
                        if(resultSet.next())
                        {
                            Lid tempResultaatLid = new Lid();
                            tempResultaatLid.setRijksregisternummer(new Rijksregisternummer(resultSet.getString("rijksregisternummer")));
                            tempResultaatLid.setNaam(resultSet.getString("naam"));
                            tempResultaatLid.setVoornaam(resultSet.getString("voornaam"));
                            tempResultaatLid.setGeslacht(Geslacht.valueOf(resultSet.getString("geslacht")));
                            tempResultaatLid.setTelnr(resultSet.getString("telnr"));
                            tempResultaatLid.setEmailadres(resultSet.getString("emailadres"));
                            tempResultaatLid.setStart_lidmaatschap(resultSet.getDate("start_lidmaatschap").toLocalDate());
                            tempResultaatLid.setEinde_lidmaatschap(resultSet.getDate("einde_lidmaatschap").toLocalDate());
                            tempResultaatLid.setNaam(resultSet.getString("opmerkingen"));

                            resultaatLid = tempResultaatLid;
                        }
                    }
                    catch(SQLException ex)
                    {
                        throw new DBException("SQL-exception in zoekLid - resultset\n" + ex);
                    }
                }
                catch(SQLException ex)
                {
                    throw new DBException("SQL-exception in zoekLid - statement\n" + ex);
                }
            }
            catch(SQLException ex)
            {
                throw new DBException("SQL-exception in zoekLid - connection\n" + ex);
            }
        }
        
        return resultaatLid;
    }

    /**
     * Geeft alle leden terug in een lijst, gesorteerd op naam, voornaam
     * 
     * @return lijst van leden
     * @throws exception.DBException bij een verkeerde installatie van de
     * database of een fout in de query
     * @throws exception.ApplicationException wanneer één van de opgehaalde
     * leden een ongeldig rijksregisternummer heeft
     */
    @Override
    public ArrayList<Lid> zoekAlleLeden() throws DBException, ApplicationException
    {
        ArrayList<Lid> resultaatLeden = new ArrayList<>();
        
        try(Connection connection = ConnectionManager.getConnection();)
        {
            try(PreparedStatement stmt = connection.prepareStatement(
                    "select rijksregisternummer"
                    + " , naam"
                    + " , voornaam"
                    + " , geslacht"
                    + " , telnr"
                    + " , emailadres"
                    + " , start_lidmaatschap"
                    + " , einde_lidmaatschap"
                    + " , opmerkingen"
                    + " from lid"
                    + " order by naam"
                    + " , voornaam"
            );)
            {
                stmt.execute();
                        
                try(ResultSet resultSet = stmt.getResultSet())
                {
                    while(resultSet.next())
                    {
                        Lid resultLid = new Lid();
                        resultLid.setRijksregisternummer(new Rijksregisternummer(resultSet.getString("rijksregisternummer")));
                        resultLid.setNaam(resultSet.getString("naam"));
                        resultLid.setVoornaam(resultSet.getString("voornaam"));
                        resultLid.setGeslacht(Geslacht.valueOf(resultSet.getString("geslacht")));
                        resultLid.setEmailadres(resultSet.getString("emailadres"));
                        resultLid.setStart_lidmaatschap(resultSet.getDate("start_lidmaatschap").toLocalDate());
                        resultLid.setEinde_lidmaatschap(resultSet.getDate("einde_lidmaatschap").toLocalDate());
                        resultLid.setOpmerkingen(resultSet.getString("opmerkingen"));
                        
                        resultaatLeden.add(resultLid);
                    }
                }
                catch(SQLException ex)
                {
                    throw new DBException("SQL-exception in zoekAlleLeden - resultset");
                }
            }
            catch(SQLException ex)
            {
                throw new DBException("SQL-exception in zoekAlleLeden - statement\n" + ex);
            }
        }
        catch(SQLException ex)
        {
            throw new DBException("SQL-exception in zoekAlleLeden - connection\n" + ex);
        }
        
        return resultaatLeden;
    }
}
