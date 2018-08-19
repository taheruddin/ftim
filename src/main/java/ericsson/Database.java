package ericsson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

public class Database {
    private String dbPath;
    private Connection conn;

    public Database(String dbPath) {
        this.dbPath = dbPath;
        try {
            conn = DriverManager.getConnection("jdbc:derby:" + dbPath +";create=true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insertFolders(List<Folder> folders) {
        boolean rs = false;
        String sql = "INSERT INTO folders VALUES ";
        for (int i = 0; i < folders.size(); i++) {
            if (i != 0) {
                sql += ", ";
            }
            sql += "(" +
                folders.get(i).getId() + ", '" +
                folders.get(i).getName() + "'," +
                folders.get(i).getParentId() + ")";
        }
        //System.out.println(sql);
        try {
            PreparedStatement insert = conn.prepareStatement(sql);
            insert.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean insertTags(List<Tag> tags) {
        boolean rs = false;
        String sql = "INSERT INTO tags VALUES ";
        for (int i = 0; i < tags.size(); i++) {
            if (i != 0) {
                sql += ", ";
            }
            sql += "(" +
                    tags.get(i).getId() + ", '" +
                    tags.get(i).getName() + "')";
        }
        //System.out.println(sql);
        try {
            PreparedStatement insert = conn.prepareStatement(sql);
            insert.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean insertItems(List<Item> items) {
        boolean rs = false;
        String sql = "INSERT INTO items VALUES ";
        for (int i = 0; i < items.size(); i++) {
            if (i != 0) {
                sql += ", ";
            }
            sql += "(" +
                    items.get(i).getId() + ", '" +
                    items.get(i).getName() + "'," +
                    items.get(i).getFolder().getId() + ")";
        }
        //System.out.println(sql);
        try {
            PreparedStatement insert = conn.prepareStatement(sql);
            insert.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean insertItemTags(List<Item> items) {
        boolean rs = false;
        String sql = "INSERT INTO item_tags (itemId, tagId) VALUES ";
        for (int i = 0; i < items.size(); i++) {
            if (i != 0) {
                sql += ", ";
            }
            for (int j = 0; j < items.get(i).getTags().size(); j++) {
                if (j != 0) {
                    sql += ", ";
                }
                sql += "(" + items.get(i).getId() + ", "
                        + items.get(i).getTags().get(j).getId() + ")";
            }
        }
        //System.out.println(sql);
        try {
            PreparedStatement insert = conn.prepareStatement(sql);
            insert.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean createTables() {
        boolean rs = false;
        try {
            PreparedStatement foldersTable = conn.prepareStatement(
            "CREATE TABLE folders ( " +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "parentId INT)"
            );
            foldersTable.execute();
            PreparedStatement tagsTable = conn.prepareStatement(
            "CREATE TABLE tags ( " +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100) )"
            );
            tagsTable.execute();
            PreparedStatement itemsTable = conn.prepareStatement(
            "CREATE TABLE items ( " +
                    "id INT PRIMARY KEY," +
                    "name VARCHAR(100)," +
                    "folderId INT )"
            );
            itemsTable.execute();
            // One item many tags
            PreparedStatement itemTags = conn.prepareStatement(
            "CREATE TABLE item_tags ( " +
                    "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "itemId INT," +
                    "tagId INT )"
            );
            itemTags.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean clearTables() {
        boolean rs = false;
        try {
            PreparedStatement foldersTable = conn.prepareStatement(
            "DELETE FROM folders "
            );
            foldersTable.execute();
            PreparedStatement tagsTable = conn.prepareStatement(
            "DELETE FROM tags "
            );
            tagsTable.execute();
            PreparedStatement itemsTable = conn.prepareStatement(
                    "DELETE FROM items "
            );
            itemsTable.execute();
            PreparedStatement itemTags = conn.prepareStatement(
                    "DELETE FROM item_tags "
            );
            itemTags.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs = false;
        }
        return rs;
    }

    public boolean dropAllTables() {
        boolean rs = false;
        // Unfortunately, Derby does not support removing multiple table in one statement
        // neither IF EXISTS
        try {
            PreparedStatement foldersTable = conn.prepareStatement(
                    "DROP TABLE folders"
            );
            PreparedStatement tagsTable = conn.prepareStatement(
                    "DROP TABLE tags"
            );
            PreparedStatement itemsTable = conn.prepareStatement(
                    "DROP TABLE items"
            );
            PreparedStatement itemTagsTable = conn.prepareStatement(
                    "DROP TABLE item_tags"
            );
            foldersTable.execute();
            tagsTable.execute();
            itemsTable.execute();
            itemTagsTable.execute();
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
            rs =false;
        }
        return rs;
    }
}
