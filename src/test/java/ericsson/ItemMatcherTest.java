package ericsson;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ItemMatcherTest {
    ItemMatcher iM;

    @Before
    public void setUp(){
        iM = new ItemMatcher("src/fixtures/items.txt");
    }

    @Test
    public void testFileToList() {
        List lines = iM.fileToList();
        // 3 lines converted to 3 list items
        assertEquals(3, lines.size());
    }

    @Test
    public void testFormatLines() {
        List formatedLines = iM.formatLines();
        // format is correct
        assertEquals("MY SECOND SUB:MYFIRSTITEM:MYTAG2|MYTAG1|", formatedLines.get(0));
        assertEquals("SECOND FOLDER:MYITEM1:MYTAG1|", formatedLines.get(1));
        assertEquals("MY SECOND SUB:MYITEM2:MYTAG2|MYTAG1|", formatedLines.get(2));
    }

    @Test
    public void testMatch() {
        Map folders = new HashMap<String, Folder>();
        folders.put("ROOT FOLDER", new Folder(0, "Root Folder", -1));
        folders.put("FIRST", new Folder(1, "First", 0));
        folders.put("SECOND FOLDER", new Folder(2, "Second Folder", 0));
        folders.put("MY SECOND SUB", new Folder(3, "My Second Sub", 2));
        Map tags = new HashMap<String, Tag>();
        tags.put("MYTAG1", new Tag(1, "mytag1"));
        tags.put("MYTAG2", new Tag(1, "mytag2"));
        tags.put("MYTAGN", new Tag(1, "mytagN"));
        List items = iM.match(folders, tags);
        // output list has 3 items
        assertEquals(3, items.size());
        // items poperties are correct
        assertEquals("MYFIRSTITEM", ((Item)items.get(0)).getName());
        assertEquals("My Second Sub", ((Item)items.get(0)).getFolder().getName());
        assertEquals(2, ((Item)items.get(0)).getTags().size());
        assertEquals("mytag2", ((Item)items.get(0)).getTags().get(0).getName());
        assertEquals("mytag1", ((Item)items.get(0)).getTags().get(1).getName());

        assertEquals("MYITEM1", ((Item)items.get(1)).getName());
        assertEquals("Second Folder", ((Item)items.get(1)).getFolder().getName());
        assertEquals(1, ((Item)items.get(1)).getTags().size());
        assertEquals("mytag1", ((Item)items.get(1)).getTags().get(0).getName());

        assertEquals("MYITEM2", ((Item)items.get(2)).getName());
        assertEquals("My Second Sub", ((Item)items.get(2)).getFolder().getName());
        assertEquals(2, ((Item)items.get(2)).getTags().size());
        assertEquals("mytag2", ((Item)items.get(2)).getTags().get(0).getName());
        assertEquals("mytag1", ((Item)items.get(2)).getTags().get(1).getName());
    }
}
