import java.util.*;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {
    private final Link link;
    private volatile Map<String, Link> usedLink = new HashMap<>();
    private StringBuffer linksTree = new StringBuffer();
    private volatile int maxLevel = 0;

    public Task(Link link) {
        this.link = link;
    }


    @Override
    protected void compute()
    {
    if (maxLevel < 3)
    {

        if (!usedLink.containsKey(link.getAddress()))
        {
            usedLink.put(link.getAddress(), link);
            maxLevel = link.getLevel();

            System.out.println(maxLevel);
            System.out.println(link.getAddress() + " " + link.getLevel() + link.getParentName());

            HtmlParser parser = new HtmlParser(link);

            if (!parser.getList().isEmpty())
            {
                for (Link childLink : parser.getList())
                {
                    childLink.setLevel(link.getLevel() + 1);
                    childLink.setParentName(link.getAddress());

                    Task task = new Task(childLink);
                    task.invoke();
                }
            }
        }
    }
}


public Map<String, Link> getVisitedUrls(){
        return usedLink;
        }

public StringBuffer getLinksTree(){
        return linksTree;
        }
        }
