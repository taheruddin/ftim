package ericsson;

import java.util.List;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World!" );
        DiskParser dP = new DiskParser("src/disk.xml");
        Map folders = dP.parserFolders();
        //System.out.println(folders);
        Map tags = dP.parseTags();
        //System.out.println(tags);

        folders.keySet().forEach(key -> System.out.println(folders.get(key)));
        tags.keySet().forEach(key -> System.out.println(tags.get(key)));

        ItemMatcher iF = new ItemMatcher("src/items.txt");
        List<Item> items = iF.match(folders, tags);
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
        //System.out.println(iF.formatLines());
        //System.out.println(iF.match(folders, tags));
    }
}
