package ericsson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    Connection conn;

    @Before
    public void setUp() {
        App app = new App();
        app.match();
        app.printInFormat();
        app.save();
        conn = getConn("src/derby/db");
    }

    @Test
    public void foldersSavedInDatabase()
    {
        ResultSet rs;
        List<String> fNames = new ArrayList();
        String sql = "SELECT * FROM folders";
        try {
            PreparedStatement fS = conn.prepareStatement(sql);
            rs = fS.executeQuery();
            while (rs.next()) {
                fNames.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(4, fNames.size());
        String[] nL = {"Second Folder", "First", "My Second Sub", "Root Folder"};
        assertTrue(fNames.containsAll(Arrays.asList(nL)));
    }

    @Test
    public void tagsSavedInDatabase()
    {
        ResultSet rs;
        List<String> tNames = new ArrayList();
        String sql = "SELECT * FROM tags";
        try {
            PreparedStatement tS = conn.prepareStatement(sql);
            rs = tS.executeQuery();
            while (rs.next()) {
                tNames.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(3, tNames.size());
        String[] nL = {"mytag1", "mytagN", "mytag2"};
        assertTrue(tNames.containsAll(Arrays.asList(nL)));
    }

    @Test
    public void itemsSavedInDatabase()
    {
        ResultSet rs;
        List<String> iNames = new ArrayList();
        String sql = "SELECT * FROM items";
        try {
            PreparedStatement iS = conn.prepareStatement(sql);
            rs = iS.executeQuery();
            while (rs.next()) {
                iNames.add(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(3, iNames.size());
        String[] nL = {"MYFIRSTITEM", "MYITEM1", "MYITEM2"};
        assertTrue(iNames.containsAll(Arrays.asList(nL)));
    }

    @Test
    public void itemTagsSavedInDatabase()
    {
        ResultSet rs;
        List<Integer> iIds = new ArrayList();
        String sql = "SELECT * FROM item_tags";
        try {
            PreparedStatement iTS = conn.prepareStatement(sql);
            rs = iTS.executeQuery();
            while (rs.next()) {
                iIds.add(rs.getInt("itemId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(5, iIds.size());
    }

    private Connection getConn(String dbPath) {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:derby:" + dbPath +";create=true");
        } catch (Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }
}
