package database;

import databag.Fiets;
import database.connect.ConnectionManager;
import datatype.Standplaats;
import datatype.Status;
import exception.ApplicationException;
import exception.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FietsDB implements InterfaceFietsDB {

    // TODO: uitzoeken waarom return value vereist
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
        
        /*try() {
                String SQL = "SELECT USER_ID, USERNAME FROM DBUSER WHERE USER_ID = ?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(SQL);
			preparedStatement.setInt(1, 1001);

			// execute select SQL stetement
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				String userid = rs.getString("USER_ID");
				String username = rs.getString("USERNAME");

				System.out.println("userid : " + userid);
				System.out.println("username : " + username);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		} 
        }*/
        
        return 0; //TODO
    }

    @Override
    public void wijzigenToestandFiets(Integer regnr, Status status)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Gegeven een registratienummer en opmerking, pas de opmerking van de fiets aan die bij het registratienummer hoort.
     * @param regnr
     * @param opmerking
     * @throws DBException
     * @throws ApplicationException 
     * @author Maximilian Coutuer
     */
    @Override
    public void wijzigenOpmerkingFiets(Integer regnr, String opmerking) throws DBException, ApplicationException {
        
        validateRegnr(regnr);
        try(Connection connection = ConnectionManager.getConnection();)
        {
            String SQL = "UPDATE Messages SET opmerkingen = ? WHERE registratienummer = ?";
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS)) {
                statement.setString(1, opmerking);
                statement.setInt(2, regnr);
                statement.execute();
            }
            catch(SQLException e) {
                throw new DBException("SQL-exception in toevoegenFiets - statement\n" + e + "\n");
            }
        }
        catch(SQLException e) {
            throw new DBException("SQL-exception in toevoegenFiets - connection\n" + e + "\n");
        }
    }

    /**
     * Returns een fiets die overeenkomt met het gegeven registratienummer.
     * @param regnr
     * @return
     * @throws DBException
     * @throws ApplicationException 
     * @author Maximilian Coutuer
     */
    @Override
    public Fiets zoekFiets(Integer regnr) throws DBException, ApplicationException {
        
        validateRegnr(regnr);
        try(Connection connection = ConnectionManager.getConnection();) {
            String SQL = "SELECT * WHERE registratienummer = ?";
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS)) {
                statement.setInt(1, regnr);
                ResultSet results = statement.executeQuery();
                Fiets fiets = new Fiets();
                fiets.setRegistratienummer(results.getInt("REGISTRATIENUMMER"));
                fiets.setStatus(Status.valueOf(results.getString("STATUS")));
                fiets.setStandplaats(Standplaats.valueOf(results.getString("STANDPLAATS")));
                fiets.setOpmerking(results.getString("OPMERKINGEN"));
                return fiets;
            } catch(SQLException e) {
                throw new DBException("SQL-exception in zoekFiets - statement\n" + e + "\n");
            }
        }
        catch(SQLException e) {
            throw new DBException("SQL-exception in zoekFiets - connection\n" + e + "\n");
        }
    }

    /**
     * Returns een lijst van alle fietsen.
     * @return
     * @throws DBException
     * @throws ApplicationException 
     * @author Maximilian Coutuer
     */
    @Override
    public ArrayList<Fiets> zoekAlleFietsen() throws DBException, ApplicationException {
        
        try(Connection connection = ConnectionManager.getConnection();) {
            String SQL = "SELECT *";
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.NO_GENERATED_KEYS)) {
                ResultSet results = statement.executeQuery();
                ArrayList<Fiets> fietsen = new ArrayList<>();
                while (results.next()) {
                    Fiets fiets = new Fiets();
                    fiets.setRegistratienummer(results.getInt("REGISTRATIENUMMER"));
                    fiets.setStatus(Status.valueOf(results.getString("STATUS")));
                    fiets.setStandplaats(Standplaats.valueOf(results.getString("STANDPLAATS")));
                    fiets.setOpmerking(results.getString("OPMERKINGEN"));
                    fietsen.add(fiets);
                }
                return fietsen;
            } catch(SQLException e) {
                throw new DBException("SQL-exception in zoekAlleFietsen - statement\n" + e + "\n");
            }
        } catch(SQLException e) {
            throw new DBException("SQL-exception in zoekAlleFietsen - connection\n" + e + "\n");
        }
    }

    
    private void validateRegnr(Integer regnr) throws ApplicationException {
        if(regnr == null) {
            throw new ApplicationException("Registratienummer fiets is verplicht");
        }
    }
   
}
