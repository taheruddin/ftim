package ericsson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App
{
    private Map<String, Folder> folders;
    private Map<String, Tag> tags;
    private List<Item> items;

    public static void main( String[] args )
    {
        App app = new App();
        app.match();
        app.printInFormat();
        app.save();
    }

    public void match() {
        DiskParser dP = new DiskParser("src/fixtures/disk.xml");
        folders = dP.parserFolders();
        tags = dP.parseTags();
        System.out.println("--- Folders ---");
        folders.keySet().forEach(key -> System.out.println(folders.get(key)));
        System.out.println("--- Tags ---");
        tags.keySet().forEach(key -> System.out.println(tags.get(key)));
        System.out.println("--- Items ---");
        ItemMatcher iM = new ItemMatcher("src/fixtures/items.txt");
        items = iM.match(folders, tags);
    }

    public void printInFormat() {
        System.out.println();
        System.out.println("--- Items in The Expected Format ---");
        items.forEach(item -> {
            String iStr = item.getFolder().getName().toUpperCase();
            iStr += "[" + item.getFolder().getId() + "]: ";
            iStr += item.getName() + " ";
            int len = item.getTags().size();
            for (int i = 0; i < len; i++) {
                Tag tag = item.getTags().get(i);
                iStr += (i==0 ? "@" : ", @") + tag.getName().toUpperCase() + "[" + tag.getId() + "]";
            }
            System.out.println(iStr);
        });
    }

    public void save() {
        System.out.println();
        System.out.println("--- Database ---");
        String dbPath = "src/derby/db";
        Database db = new Database(dbPath);
        db.dropAllTables();
        if (db.createTables()) {
            System.out.println("Tables are created.");
        }
        if (db.clearTables()) {
            System.out.println("Tables are empty.");
        }
        if (db.insertFolders(new ArrayList<Folder>(folders.values()))) {
            System.out.println("Folders are inserted.");
        }
        if (db.insertTags(new ArrayList<Tag>(tags.values()))) {
            System.out.println("Tags are inserted.");
        }
        if (db.insertItems(items)) {
            System.out.println("Items are inserted.");
        }
        if (db.insertItemTags(items)) {
            System.out.println("Item_Tags are inserted.");
        }
    }
}
