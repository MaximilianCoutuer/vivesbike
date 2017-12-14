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
     * Gegeven een registratienummer en opmerking, past de bijbehorende fiets aan
     * @param regnr
     * @param opmerking
     * @throws DBException 
     */
    @Override
    public void wijzigenOpmerkingFiets(Integer regnr, String opmerking) throws DBException  {
        
        if(regnr == null) {
            //throw new ApplicationException();
        }
        
        try(Connection conn = ConnectionManager.getConnection();)
        {
            String SQL = "UPDATE Messages SET opmerkingen = ? WHERE registratienummer = ?";
            try (PreparedStatement stmt = conn.prepareStatement(SQL, Statement.NO_GENERATED_KEYS)) {
                stmt.setString(1, opmerking);
                stmt.setInt(2, regnr);
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
    public Fiets zoekFiets(Integer regnr)  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Fiets> zoekAlleFietsen()  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
