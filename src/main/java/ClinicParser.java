import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ClinicParser extends BaseParser{

    @Override
    void getAllInfo() throws IOException {
        String baseUrl = "https://ctoma.ru";
        Document page = getPage(baseUrl+"/clinics/");
        Elements clinics = page.select(".klinikiView__row");
        for (Element clinic: clinics){
            String curUrl = clinic.select(".views-field").get(1).select("a").attr("href");
            parseOnePage(baseUrl, curUrl);
        }
    }

    private static void parseOnePage(String baseUrl, String curUrl) throws IOException {
        Document page = getPage(baseUrl + curUrl);
        String address = page.select(".clinic__adres").text();
        String workTime = page.select(".clinic__rezim").text();
        String metroStation = page.select(".clinic__metro").text();
        String phoneNumber = page.select(".clinic__phone").text();
        String description = page.select(".clinic__informacia").text();
        writeToCsv(new String[] {address, workTime, metroStation, phoneNumber, description},
                "clinic.csv");
    }

    public static void main( String[] args ) throws IOException {
        new ClinicParser().getAllInfo();
    }

}
