package ericsson;

import java.util.List;

/**
 * presents a single item in items.txt file
 */
public class Item {
    private int id;
    private String name;
    private Folder folder;
    private List<Tag> tags;

    /**
     *
     * @param id
     * @param name
     * @param folder
     * @param tags
     */
    public Item(int id, String name, Folder folder, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.folder = folder;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", folder=" + folder +
                ", tags=" + tags +
                '}';
    }
}
