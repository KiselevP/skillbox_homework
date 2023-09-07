import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    static String rootUrl = "https://mirtekgroup.com/";
    public static void main(String[] args) {
        File file = new File("src/main/resources/urls.txt");
        // String rootUrl = "https://skillbox.com/";

        Link rootLink = new Link(rootUrl);
        rootLink.setLevel(0);

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        Task task = new Task(rootLink, pool);
        pool.invoke(task);
        task.fork();
        task.join();
        pool.close();

        /* for (Link link : task.getVisitedUrls().values())
        {
            System.out.println("\t".repeat(link.getLevel()) + " " + link.getAddress() + " " + link.getLevel() + " " + link.getParentName());
        } */

        try (FileWriter writer = new FileWriter(file)) {
            // writer.write("");
            for (Link link : task.getVisitedUrls().values())
            {
                writer.write("\t".repeat(link.getLevel()) + " " + link.getAddress() + " " + link.getLevel() + " " + link.getParentName() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
