import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Task extends RecursiveAction {
    private final ForkJoinPool pool;
    private final Link link;
    private volatile static Map<String, Link> usedLink = new LinkedHashMap<>();
    private StringBuffer linksTree = new StringBuffer();
    private volatile static int maxLevel = 0;

    public Task(Link link, ForkJoinPool pool) {
        this.link = link;
        this.pool = pool;
    }


    @Override
    protected void compute() {
        synchronized (this) {
            System.out.println(this.link.getAddress() + " - " + Thread.currentThread().getName());
        }

        if (!usedLink.containsKey(link.getAddress())) {
            usedLink.put(link.getAddress(), link);
            maxLevel = link.getLevel();

            if (maxLevel < 3) {
                HtmlParser parser = new HtmlParser(link);
                if (!parser.getList().isEmpty()) {
                    List<Task> tasks = new ArrayList<>();
                    for (Link childLink : parser.getList()) {
                        if (!usedLink.containsKey(childLink.getAddress()))
                        {
                            childLink.setLevel(link.getLevel() + 1);
                            childLink.setParentName(link.getAddress());

                            Task task = new Task(childLink, pool);
                            tasks.add(task);
                            pool.invoke(task);
                            task.fork();
                        }
                    }
                    for (Task task : tasks) {
                        task.join();
                    }
                }
            }
        }
    }


    public Map<String, Link> getVisitedUrls() {
        return usedLink;
    }

    public StringBuffer getLinksTree() {
        return linksTree;
    }
}
