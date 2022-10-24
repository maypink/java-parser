import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;


public abstract class BaseParser {

    static Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 3000);
    }

    /**
     * Method to get all necessary info from the main page
     */
    abstract void getAllInfo() throws IOException;

    static void writeToCsv(String[] record, String fileName) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter(fileName, true));
        writer.writeNext(record);
        writer.close();
    }
}
