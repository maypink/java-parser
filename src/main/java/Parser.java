import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;


public class Parser {

    private static Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    private static void getAllDoctorsInfo() throws IOException {
        String baseUrl = "https://ctoma.ru";
        String additionalUrl = "/personal/index.php?PAGEN_1=";
        Document firstPage = getPage(baseUrl + "/personal/");
        int pagesNum = Integer.parseInt(firstPage.select(".pager__item").select(".pager__item--last")
                .select("a").first().attr("href").split("=")[1]);
        for (int i=1; i <= pagesNum; i++){
            System.out.println(i);
            String currentUrl = baseUrl + additionalUrl + i;
            parseOnePage(baseUrl, currentUrl);
        }
    }

    private static void parseOnePage(String baseUrl, String curUrl) throws IOException {
        Document page = getPage(curUrl);
        Elements doctorsElems = page.select(".doctorsFilter__title").select("a");
        for (Element doctorElem: doctorsElems) {
            getOneDoctorInfo(baseUrl + doctorElem.attr("href"));
        }
    }

    private static void getOneDoctorInfo(String url) throws IOException {
        Document page = getPage(url);
        Element doctorInfo = page.select(".doctor").first();
        if (doctorInfo == null) return;
        String name = doctorInfo
                .select(".doctor__title").text();
        ArrayList<Element> doctorFields = doctorInfo.select(".doctor__field");
        String[] doctorFieldsStr = new String[doctorFields.size()+1];
        doctorFieldsStr[0] = name;
        for (int i = 1; i<doctorFieldsStr.length; i++) {
            doctorFieldsStr[i] = doctorFields.get(i-1).select("div").get(1).text();
        }
        writeToCsv(doctorFieldsStr);

    }

    private static void writeToCsv(String [] record) throws IOException {
        String csv = "data.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        writer.writeNext(record);
        writer.close();
    }

    public static void main( String[] args ) throws IOException {
        getAllDoctorsInfo();
    }

}
