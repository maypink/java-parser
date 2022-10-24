import java.io.IOException;

/**
 * Class to parse the whole <a href="https://ctoma.ru">site</a>
 */
public class StomatologyParser {

    public static void parse() throws IOException {
        new DoctorParser().getAllInfo();
        new PriceParser().getAllInfo();
    }

    public static void main( String[] args ) throws IOException {
        StomatologyParser.parse();
    }
}
