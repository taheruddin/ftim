package ericsson;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * convert disk.xml to list
 */
public class DiskParser {
    private String filePath;
    private Map<String, Folder> folders;
    private Map<String, Tag> tags;

    /**
     *
     * @param filePath
     */
    public DiskParser(String filePath) {
        this.filePath = filePath;
        folders = new HashMap<String, Folder>();
        tags = new HashMap<String, Tag>();
    }

    public Map parserFolders() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath);
            Node foldersNode = (doc.getElementsByTagName("folders")).item(0);
            Node root = foldersNode.getChildNodes().item(1);
            folders.clear();
            addFolders(root, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.folders;
    }

    private void addFolders(Node node, int parentId) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            int folderId = 0;
            if (parentId != -1) {
                folderId = (int)(Math.random() * 1000000000);
            }
            String name = ((Element)node).getAttribute("name");
            Folder folder = new Folder(folderId, name, parentId);
            folders.put(name.toUpperCase(), folder);
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                addFolders(children.item(i), folderId);
            }
        }
    }

    public Map parseTags() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filePath);
            Node tagsRoot = (doc.getElementsByTagName("tags")).item(0);
            NodeList children = tagsRoot.getChildNodes();
            tags.clear();
            for (int i = 0; i < children.getLength(); i++) {
                addTag(children.item(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.tags;
    }

    private void addTag(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            int tagId = (int)(Math.random() * 1000000000);
            String name = ((Element)node).getAttribute("name");
            Tag tag = new Tag(tagId, name);
            tags.put(name.toUpperCase(), tag);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}