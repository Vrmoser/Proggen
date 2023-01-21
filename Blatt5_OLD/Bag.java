import java.util.LinkedList;
import java.util.List;

public class Bag {
    Regex rg = new Regex();
    Error er = new Error();
    Extractor ex = new Extractor();
    private List<String> bagOne = new LinkedList<String>();
    private List<String> bagTwo = new LinkedList<String>();
    private String bagOneOutput;
    private String bagTwoOutput;
    private String bagTokens;

    public void fillBagTokens(String startTokens) {
        bagTokens = startTokens;
        fillBags(bagTokens);
    }

    private void fillBags(String bagTokens) {
        bagOne = ex.bagTokenExtractor(ex.bagStartTokensPlayerOne(bagTokens));
        bagTwo = ex.bagTokenExtractor(ex.bagStartTokensPlayerTwo(bagTokens));
    }

    private void bagOutput() {
        bagOneOutput = ex.bagListToString(bagOne);
        bagTwoOutput = ex.bagListToString(bagTwo);
    }

    public void printBag(String userInput) {
        bagOutput();
        if (ex.getBagPlayer(userInput).matches(rg.getRegexPlayerOne())) {
            System.out.println(bagOneOutput);
        } else if (ex.getBagPlayer(userInput).matches(rg.getRegexPlayerTwo())) {
            System.out.println(bagTwoOutput);
        } else {
            er.noValidPlayer();
        }
    }

    private boolean lookForTokenOne(String userInput) {
        int correctCount = 0;
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            if (isThisTokenInBagOne(ex.getToken(userInput).substring(i, i + 1))) {
                correctCount++;
            }
        }
        return correctCount == ex.getToken(userInput).length();
    }

    private boolean lookForTokenTwo(String userInput) {
        int correctCount = 0;
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            if (isThisTokenInBagTwo(ex.getToken(userInput).substring(i, i + 1))) {
                correctCount++;
            }
        }
        return correctCount == ex.getToken(userInput).length();
    }

    public boolean areTokensInBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne())) {
            return lookForTokenOne(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo())) {
            return lookForTokenTwo(userInput);
        }
        return false;
    }

    private boolean isThisTokenInBagOne(String token) {
        for (int i = 0; i < bagOne.size(); i++) {
            if (token.equals(bagOne.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean isThisTokenInBagTwo(String token) {
        for (int i = 0; i < bagTwo.size(); i++) {
            if (token.equals(bagTwo.get(i))) {
                return true;
            }
        }
        return false;
    }


    public void takeTokensOutOfBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne())) {
            removeUsedTokensBagOne(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo())) {
            removeUsedTokensBagTwo(userInput);
        }
    }

    public void removeUsedTokensBagOne(String userInput) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            this.bagOne.remove(ex.getToken(userInput).substring(i, i + 1));
        }
    }

    public void removeUsedTokensBagTwo(String userInput) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            this.bagTwo.remove(ex.getToken(userInput).substring(i, i + 1));
        }
    }

    private boolean tokensMinusBagOne(String bagTokens) {
        return (bagOne.size() - ex.getToken(bagTokens).length()) >= 0;
    }

    private boolean tokensMinusBagTwo(String bagTokens) {
        return (bagTwo.size() - ex.getToken(bagTokens).length()) >= 0;
    }

    public boolean checkForTokenInBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne()) && tokensMinusBagOne(userInput)) {
            return areTokensInBag(userInput, currentPlayer);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo()) && tokensMinusBagTwo(userInput)) {
            return areTokensInBag(userInput, currentPlayer);
        }
        return false;
    }
}

