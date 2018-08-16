package ericsson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemMatcher {
    private String filePath;
    private List<String> lines;
    private List<String> formatedLines;
    private List<Item> items;

    public ItemMatcher(String filePath) {
        this.filePath = filePath;
        lines = new ArrayList();
        items = new ArrayList();
        formatedLines = new ArrayList();
    }

    public List fileToList() {
        lines.clear();
        try {
            File file = new File(filePath);
            FileReader  fR = new FileReader(file);
            BufferedReader bR = new BufferedReader(fR);
            String line;
            lines.clear();
            while ((line = bR.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public List formatLines() {
        fileToList();
        formatedLines.clear();
        lines.forEach(line -> {
            String fLine = "";
            String[] parts = line.split(":");
            fLine = parts[0].trim().toUpperCase();
            fLine += ":";
            String[] p2 = parts[1].split(" ");
            String itemName = "";
            String tags = "";
            for (String word: p2) {
                if (word.matches("^@.*$")) {
                    tags += word.replace("@", "").toUpperCase() + "|";
                } else {
                    itemName += word.toUpperCase();
                }
            }
            fLine += itemName + ":" + tags;
            formatedLines.add(fLine);
        });
        return formatedLines;
    }

    public List match(Map<String, Folder> folders, Map<String, Tag> tags) {
        formatLines();
        items.clear();
        formatedLines.forEach(line -> {
            int rand = (int)(Math.random() * 1000000000);
            String[] lineParts = line.split(":");
            String[] lineTags = lineParts[2].split("\\|");
            List<Tag> itemTags = new ArrayList();
            for (String lineTag: lineTags) {
                itemTags.add(tags.get(lineTag));
            }
            Item item = new Item(rand, lineParts[1], folders.get(lineParts[0]), itemTags);
            items.add(item);
        });
        return items;
    }
}
