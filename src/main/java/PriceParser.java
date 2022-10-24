import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class PriceParser extends BaseParser{

    @Override
    void getAllInfo() throws IOException {
        String url = "https://ctoma.ru/price";
        Document mainPage = getPage(url);
        Element content = mainPage.select(".normPage__content").first();
        Elements priceRows = content.select(".praiseTable__row");
        for (Element row: priceRows){
            String name = row.select(".praiseTable__name").text();
            String price = row.select(".praiseTable__cena").text();
            writeToCsv(new String[] {name, price}, "prices.csv");
        }
    }

    public static void main( String[] args ) throws IOException {
        new PriceParser().getAllInfo();
    }


}
