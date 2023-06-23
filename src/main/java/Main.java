import org.fusesource.jansi.AnsiConsole;

import java.io.File;
import java.util.concurrent.ForkJoinPool;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {

    public static void main(String[] args) throws Exception {

        try {
            ParametersBag parametersBag = new ParametersBag(args);

            String folderPath = parametersBag.getPath();
            long sizeLimit = parametersBag.getLimit();

            File file = new File(folderPath);
            Node root = new Node(file, sizeLimit);

            long start = System.currentTimeMillis();

            FolderSizeCalculator calculator = new FolderSizeCalculator(root);
            ForkJoinPool pool = new ForkJoinPool();
            pool.invoke(calculator);

            System.out.println(root);

            long duration = System.currentTimeMillis() - start;
            System.out.println(duration + " ms");
        } catch (MyException ex) {
           logColorUsingJANSI(ex.getMessage());
        }
    }
    private static void logColorUsingJANSI(String massage) {
        AnsiConsole.systemInstall();
        System.out.println(ansi()
                .fgRed()
                .a(massage)
                .reset());
        AnsiConsole.systemUninstall();
    }
}
