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

    public static ArrayList<ElectionResult> parse2016PresidentialResults(String data){
        ArrayList<ElectionResult> output = new ArrayList<>();
        String [] row = data.split("\n");
        for (int i = 1; i < row.length; i++) {
            int replaceCommaIndex = row[i].indexOf("\"", row[i].indexOf("\"") + 1);
//            System.out.println(row[i].indexOf("\""));
//            System.out.println(replaceCommaIndex);
            String wordWithoutComma = row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1);
//
//            wordWithoutComma = removeCharAt(wordWithoutComma, wordWithoutComma.indexOf(","));
//            System.out.println(wordWithoutComma);
//            System.out.println(row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1));
           // System.out.println(row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1));
          System.out.println(row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).substring(0, row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).indexOf(",")) + row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).substring(row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).indexOf(",") + 1));

            String a = row[i].replaceFirst(row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1), row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).substring(0, row[i].indexOf(",")) + row[i].substring(row[i].indexOf("\""), replaceCommaIndex + 1).substring(row[i].indexOf(",") + 1));
            row[i] = a;

            System.out.println(row[i].substring(row[i].indexOf("\"") - 1, replaceCommaIndex + 1));


        //System.out.println(row.length);

            String [] elements = row[i].split(",");
            System.out.println(elements.length);
            double votes_dem = Double.parseDouble(elements[1]);
            double votes_gop = Double.parseDouble(elements[2]);
            double total_votes = Double.parseDouble(elements[3]);
            double per_dem = Double.parseDouble(elements[4]);
            double per_gop = Double.parseDouble(elements[5]);
            String diff = elements[6];
            String per_point_diff = elements[7];
            String state_abbr = elements[8];
            String county_name = elements[9];
            int combined_fips = Integer.parseInt(elements[10]);
            ElectionResult electionResult = new ElectionResult(votes_dem, votes_gop, total_votes, per_dem, per_gop, diff, per_point_diff, state_abbr, county_name, combined_fips);
            output.add(electionResult);
        }
        return output;
    }

    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }
}
