import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
    public static String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(filepath))) {

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static ArrayList<ElectionResult> parse2016PresidentialResults(String data) {
        ArrayList<ElectionResult> output = new ArrayList<>();
        String[] row = data.split("\n");
        for (int i = 1; i < row.length; i++) {
            if (row[i].indexOf("\"") != -1) {
                row[i] = correctedString(row[i]);
            }
            String[] elements = row[i].split(",");
            double votes_dem = Double.parseDouble(elements[1]);
            double votes_gop = Double.parseDouble(elements[2]);
            double total_votes = Double.parseDouble(elements[3]);
            double per_dem = Double.parseDouble(elements[4]);
            double per_gop = Double.parseDouble(elements[5]);
            int diff = Integer.parseInt(elements[6]);
            double per_point_diff = Double.parseDouble(elements[7].substring(0, elements[7].length() - 1));
            String state_abbr = elements[8];
            String county_name = elements[9];
            int combined_fips = Integer.parseInt(elements[10]);
            ElectionResult electionResult = new ElectionResult(votes_dem, votes_gop, total_votes, per_dem, per_gop, diff, per_point_diff, state_abbr, county_name, combined_fips);
            output.add(electionResult);
        }
        return output;
    }

    private static String correctedString(String s) {
        int replaceCommaIndex = s.indexOf("\"", s.indexOf("\"") + 1);
        String wordWithoutComma = s.substring(s.indexOf("\"") + 1, replaceCommaIndex);
        wordWithoutComma = wordWithoutComma.replace(",", "");
        return s.replace(s.substring(s.indexOf("\""), replaceCommaIndex + 1), wordWithoutComma);
    }


}
