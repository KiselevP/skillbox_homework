import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class HtmlParser {
    private List<Link> list;

    private boolean isLink(Link link) {
        String regex = "http[s]?://[^#, \\s]*\\.?[a-z]*\\.[a-z]{2,4}[^#,\\s]*";
        return !list.contains(link) && link.getAddress().matches(regex);
    }

    public HtmlParser(Link link) {
        synchronized (link) {
            try {
                sleep(150);
                list = new ArrayList<>();
                Document doc = Jsoup.connect(link.getAddress())
                        .ignoreHttpErrors(true)
                        .ignoreContentType(true).timeout(1500)
                        .followRedirects(false).get();
                Elements elements = doc.select("a");
                for (Element element : elements) {
                    String url = element.absUrl("href");
                    Link linkChild = new Link(url);
                    if (isLink(linkChild) && linkChild.getAddress().startsWith(Main.rootUrl))
                    {
                        list.add(linkChild);
                    }
                }
                link.setChildList(list);
            } catch (SocketTimeoutException e) {
                System.out.println("Таймаут при чтении данных");
            } catch (IOException | InterruptedException e) {
                System.err.println("Ошибка со стороны сайта");
                ;
            }
        }
    }

    public List<Link> getList() {
        return list;
    }
}

