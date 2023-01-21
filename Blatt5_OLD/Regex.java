public class Regex {
    Extractor ex = new Extractor();
    //private String regexStart = getRegexToken() + " " + getRegexToken();
    private String regexStart = "[0-9*+-]+ [0-9*+-]+";
    private String regexNumber = "[0-9]";
    private String regexOperator = "[*+-]+";
    private String regexPlayerOne = "P1";
    private String regexPlayerTwo = "P2";
    private String regexPlayer = "P[12]";
    private String regexToken = "[0-9*+-]+";
    private String regexColumn = "[0-9]+";
    private String regexRow = getRegexColumn();
    private String regexScore = "score";
    private String regexPlace = "place";
    private String regexDirection = "[HV]";
    private String regexBag = "bag " + getRegexPlayer();
    private String regexPrint = "print";
    private String regexQuit = "quit";
    private String regexUserInput = getRegexPlace() + " "
            + getRegexToken() + ";" + getRegexRow() + ";"
            + getRegexColumn() + ";" + getRegexDirection();

    public String getRegexNumber() {
        return regexNumber;
    }

    public String getRegexOperator() {
        return regexOperator;
    }

    public String getRegexScore() {
        return regexScore;
    }

    public String getRegexPlayer() {
        return regexPlayer;
    }

    public String getRegexPlayerOne() {
        return regexPlayerOne;
    }

    public String getRegexPlayerTwo() {
        return regexPlayerTwo;
    }

    public String getRegexBag() {
        return regexBag;
    }

    public String getRegexPrint() {
        return regexPrint;
    }

    public String getRegexQuit() {
        return regexQuit;
    }

    public String getRegexDirection() {
        return regexDirection;
    }

    public String getRegexPlace() {
        return regexPlace;
    }

    public String getRegexUserInput() {
        return regexUserInput;
    }

    public String getRegexStart() {
        return regexStart;
    }

    public String getRegexToken() {
        return regexToken;
    }

    public String getRegexColumn() {
        return regexColumn;
    }

    public String getRegexRow() {
        return regexRow;
    }
}
