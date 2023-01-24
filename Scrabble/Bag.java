import java.util.LinkedList;
import java.util.List;

public class Bag {
    Extractor ex = new Extractor();
    private List<String> bag = new LinkedList<String>();
    private String bagOutPutString;

    private String bagTokens;

    public void fillBagTokens(String startTokens) {
        bagTokens = startTokens;
        fillBag(bagTokens);
    }


    private void fillBag(String bagTokens) {
        bag = ex.bagTokenExtractor(bagTokens);
    }


    private void bagOutput() {
        bagOutPutString = ex.bagListToString(bag);
    }

    public void printBag() {
        bagOutput();
        System.out.println(bagOutPutString);
    }


    public boolean lookForToken(String userInput) {
        int correctCount = 0;
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            if (isThisTokenInBag(ex.getToken(userInput).substring(i, i + 1))) {
                correctCount++;
            }
        }
        return correctCount == ex.getToken(userInput).length();
    }

    private boolean isThisTokenInBag(String token) {
        for (int i = 0; i < bag.size(); i++) {
            if (token.equals(bag.get(i))) {
                return true;
            }
        }
        return false;
    }



    public void removeUsedTokensBag(String userInput) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            this.bag.remove(ex.getToken(userInput).substring(i, i + 1));
        }
    }

    public boolean tokensMinusBag(String bagTokens) {
        return (bag.size() - ex.getToken(bagTokens).length()) >= 0;
    }

}

