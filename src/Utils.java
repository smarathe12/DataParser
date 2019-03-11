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

    public static DataManager makeDataManager(String electionResults, String education, String employment) {

        ArrayList<States> states = new ArrayList<>();
        String electionResultsData = normalizeLineBreaks(electionResults);
        String educuationData = normalizeLineBreaks(education);
        String employmentData = normalizeLineBreaks(employment);

        String[] electionElements = electionResultsData.split("\n");
        String[] educationElements = educuationData.split("\n");
        String[] employmentElements = employmentData.split("\n");
        String[] data;
        for (int i = 1; i < electionElements.length; i++) {
            data = electionElements[i].split(",");
            String state = getState(data);
            boolean doesStateExist = false;
            for (int j = 0; j < states.size(); j++) {
                if (states.get(i).getName().equals(state)) {
                    doesStateExist = true;
                }

            }
            if (doesStateExist == false) {
                States s = new States(state, new ArrayList<County>());
                states.add(s);
            }

            String[] electionData;
            for (int a = 1; a < electionElements.length; a++) {
                electionData = electionElements[a].split(",");

                for (int r = 0; r < states.size(); r++) {
                    if (getState(electionData).equals(states.get(i).getName())) {

                        ArrayList<County> counties = states.get(i).getCounties();
                        int differenceInCommas = data.length - 11;
                        String countyName = data[9 + differenceInCommas];
                        int fips = Integer.parseInt(data[10 + differenceInCommas].trim());
                        boolean doesCountyExist = false;
                        for (County c : counties) {
                            if (c.getName().equals(countyName) && c.getFips() == fips) {
                                doesCountyExist = true;
                            }
                        }

                    }

                }
            }


        }
        return new DataManager(states);
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

    public static ArrayList<Education2016> parseEducation(String data) {
        ArrayList<Education2016> output = new ArrayList<>();
        String[] row = data.split("\n");
        for (int i = 1; i < row.length; i++) {
            if (row[i].indexOf("\"") != -1) {
                row[i] = correctedString(row[i]);
            }
            String[] elements = row[i].split(",");
            System.out.println(elements.length);
            double noHighSchool = Double.parseDouble(elements[42]);
            double onlyHighSchool = Double.parseDouble(elements[39]);
            double someCollege = Double.parseDouble(elements[40]);
            double bachelorsOrMore = Double.parseDouble(elements[41]);
            Education2016 education2016 = new Education2016(noHighSchool, onlyHighSchool, someCollege, bachelorsOrMore);
            output.add(education2016);
        }
        return output;
    }


    public static ArrayList<ElectionResult> parseUnemployment(String data) {


    }

    private static String correctedString(String s) {
        int replaceCommaIndex = s.indexOf("\"", s.indexOf("\"") + 1);
        String wordWithoutComma = s.substring(s.indexOf("\"") + 1, replaceCommaIndex);
        wordWithoutComma = wordWithoutComma.replace(",", "");
        return s.replace(s.substring(s.indexOf("\""), replaceCommaIndex + 1), wordWithoutComma);
    }

    private static String normalizeLineBreaks(String str) {
        str = str.replace('\u00A0', ' ');
        str = str.replace('\u2007', ' ');
        str = str.replace('\u202F', ' ');
        str = str.replace('\uFEFF', ' ');

        return str.replace("\r\n", "\n").replace('\r', '\n');
    }

    private static String getState(String[] lineData) {
        int differenceInCommas = lineData.length - 11;
        return lineData[8 + differenceInCommas];

    }





}
