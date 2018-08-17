package ericsson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class DiskParserTest {
    DiskParser dP;

    @Before
    public void setUp() {
        dP = new DiskParser("src/fixtures/disk.xml");
    }

    @Test
    public void testParseFolders() {
        Map folders = dP.parserFolders();
        Set keys = folders.keySet();
        // Number of folders is right
        assertEquals(4, keys.size());
        // Keys of the folders are as expected
        assertTrue(keys.contains("ROOT FOLDER"));
        assertTrue(keys.contains("FIRST"));
        assertTrue(keys.contains("SECOND FOLDER"));
        assertTrue(keys.contains("MY SECOND SUB"));
        // IDs are unique
        List ids = new ArrayList();
        keys.forEach(key -> {
            ids.add(((Folder)folders.get(key)).getId());
        });
        Set uniqIds = new HashSet(ids);
        assertEquals(uniqIds.size(), ids.size());
    }

    @Test
    public void testParseTags() {
        Map tags = dP.parseTags();
        Set keys = tags.keySet();
        // Number of tags is right
        assertEquals(3, keys.size());
        // Keys of the tags are as expected
        assertTrue(keys.contains("MYTAG1"));
        assertTrue(keys.contains("MYTAG2"));
        assertTrue(keys.contains("MYTAGN"));
        // IDs are unique
        List ids = new ArrayList();
        keys.forEach(key -> {
            ids.add(((Tag)tags.get(key)).getId());
        });
        Set uniqIds = new HashSet(ids);
        assertEquals(uniqIds.size(), ids.size());
    }
}
