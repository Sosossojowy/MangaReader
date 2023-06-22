import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChapterScraper {
    public List<String> scrapeAvailableChapters(String url) throws IOException {
        List<String> availableChapters = new ArrayList<>();

        Document document = Jsoup.connect(url).get();
        Elements chapterElements = document.select(".lst a");
        for (Element chapterElement : chapterElements) {
            String chapterNumber = chapterElement.text();
            availableChapters.add(chapterNumber);
        }

        return availableChapters;
    }
}
