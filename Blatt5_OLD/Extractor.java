import java.util.LinkedList;
import java.util.List;

public class Extractor {
    public String getToken(String regex) {
        return extractToken(regex);
    }

    public int getRow(String regex) {
        return extractRow(regex);
    }

    public int getColumn(String regex) {
        return extractColumn(regex);
    }

    public String getDirection(String regex) {
        return extractDirection(regex);
    }

    private String extractToken(String regex) {
        return regex.substring(spaceBarIndex(regex) + 1, firstSemiIndex(regex));
    }


    private int extractRow(String regex) {
        return Integer.parseInt(regex.substring(firstSemiIndex(regex) + 1, getSecondSemiIndex(regex)));
    }

    private int extractColumn(String regex) {
        return Integer.parseInt(regex.substring(getSecondSemiIndex(regex) + 1, getThirdSemiIndex(regex)));
    }

    private String extractDirection(String regex) {
        return regex.substring(getThirdSemiIndex(regex) + 1);
    }

    private int spaceBarIndex(String regex) {
        return regex.indexOf(" ");
    }

    private int firstSemiIndex(String regex) {
        return regex.indexOf(";");
    }

    private int getSecondSemiIndex(String regex) {
        return regex.indexOf(";", firstSemiIndex(regex) + 1);
    }

    private int getThirdSemiIndex(String regex) {
        return regex.indexOf(";", getSecondSemiIndex(regex) + 1);
    }

    private String bagPlayer(String regex) {
        int spaceBar = regex.indexOf(" ");
        return regex.substring(spaceBar + 1);
    }

    public String getBagPlayer(String regex) {
        return bagPlayer(regex);
    }

    public List<String> bagTokenExtractor(String regex) {
        List<String> bagToken = new LinkedList<>();
        for (int i = 0; i < regex.length(); i++) {
            bagToken.add(regex.substring(i, i + 1));
        }
        return bagToken;
    }

    public String bagStartTokensPlayerOne(String regex) {
        int spacebarOne = regex.indexOf(" ");
        int spacebarTwo = regex.indexOf(" ", spacebarOne + 1);
        return regex.substring(spacebarOne + 1, spacebarTwo);
    }

    public String bagStartTokensPlayerTwo(String regex) {
        int spacebarOne = regex.indexOf(" ");
        int spacebarTwo = regex.indexOf(" ", spacebarOne + 1);
        return regex.substring(spacebarTwo + 1);
    }

    public String bagListToString(List<String> bag) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bag.size(); i++) {
            sb.append(bag.get(i));
        }
        return sb.toString();
    }
}
