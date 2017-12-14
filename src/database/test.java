/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import databag.Lid;
import datatype.Rijksregisternummer;
import exception.ApplicationException;
import exception.DBException;

/**
 *
 * @author gwij
 */
public class test
{
    public static void main(String[] args) throws DBException, ApplicationException
    {
        Lid testLid = new Lid();
        testLid.setVoornaam("Gwij");
        testLid.setRijksregisternummer(new Rijksregisternummer("97111526739"));
        
        LidDB lidDB = new LidDB();
        lidDB.toevoegenLid(testLid);
    }
}
