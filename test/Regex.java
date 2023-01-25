public class Regex {
    private final String regexRow = getRegexColumn();
    private final String regexBag = "bag " + getRegexPlayer();
    private final String regexUserInput = getRegexPlace() + " "
            + getRegexToken() + ";" + getRegexRow() + ";"
            + getRegexColumn() + ";" + getRegexDirection();

    public String getRegexNumber() {
        return "[0-9]";
    }

    public String getRegexOperator() {
        return "[*+-]+";
    }

    public String getRegexScore() {
        return "score " + getRegexPlayer();
    }

    public String getRegexPlayer() {
        return "P[12]";
    }

    public String getRegexPlayerOne() {
        return "P1";
    }

    public String getRegexPlayerTwo() {
        return "P2";
    }

    public String getRegexBag() {
        return regexBag;
    }

    public String getRegexPrint() {
        return "print";
    }

    public String getRegexQuit() {
        return "quit";
    }

    public String getRegexDirection() {
        return "[HV]";
    }

    public String getRegexPlace() {
        return "place";
    }

    public String getRegexUserInput() {
        return regexUserInput;
    }

    public String getRegexStart() {
        //private String regexStart = getRegexToken() + " " + getRegexToken();
        return "[0-9*+-]+ [0-9*+-]+";
    }

    public String getRegexToken() {
        return "[0-9*+-]+";
    }

    public String getRegexColumn() {
        return "[0-9]+";
    }

    public String getRegexRow() {
        return regexRow;
    }
}
