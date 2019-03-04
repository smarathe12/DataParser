import java.util.ArrayList;

/***
 * Main class for data parsers
 * @author siddharthmarathe
 */
public class Main {

    public static void main(String[] args) {
        // Test of Utils
        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");
        //System.out.print(data);
        ArrayList<ElectionResult> results = Utils.parse2016PresidentialResults(data);
        for (int i = 0; i < results.size(); i++) {
            results.get(i).toString();
        }
    }
}
