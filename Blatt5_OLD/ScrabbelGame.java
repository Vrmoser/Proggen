import java.util.LinkedList;
import java.util.List;

public class ScrabbelGame {
    private int[][] playerID = new int[10][10];
    private int playerIDOne = 1;
    private int playerIDTwo = 2;

    private List<String> words = new LinkedList<>();
    private List<String> wordsCoordinate = new LinkedList<>();
    Regex rg = new Regex();
    Extractor ex = new Extractor();

    public void showScore() {
        // String test = "342*+";
        // System.out.println(postfixFunction(test));
        System.out.println(calculateScoreOne());
    }

    public int getPlayerIDOne() {
        return playerIDOne;
    }

    public int getPlayerIDTwo() {
        return playerIDTwo;
    }

    public void createPlayerIDField() {
        for (int i = 0; i < playerID.length; i++) {
            for (int j = 0; j < playerID[0].length; j++) {
                this.playerID[i][j] = 0;
            }
        }
    }

    public void setFieldToPlayer(int i, int j, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne())) {
            playerID[i][j] = getPlayerIDOne();
        } else if (currentPlayer.matches(rg.getRegexPlayerTwo())) {
            playerID[i][j] = getPlayerIDTwo();
        }
    }


    private List<String> postfixFunction(String expression) {
        List<String> stack = new LinkedList<String>();
        for (int i = 0; i < expression.length(); i++) {
            if (isNumber(expression.substring(i, i + 1))) {
                stack.add(expression.substring(i, i + 1));
            } else if (isOperator(expression.substring(i, i + 1))) {
                int cacheOne = Integer.parseInt(stack.get(0));
                stack.remove(0);
                int cacheTwo = Integer.parseInt(stack.get(0));
                stack.remove(0);
                int result = calculate(cacheTwo, cacheOne, expression.substring(i, i + 1));
                stack.add(Integer.toString(result));
            }
        }
        return stack;
    }

    private boolean isNumber(String c) {
        return c.matches(rg.getRegexNumber());
    }

    private boolean isOperator(String c) {
        return c.matches(rg.getRegexOperator());
    }

    private int calculate(int cacheOne, int cacheTwo, String operator) {
        if (operator.matches("[+]")) {
            return cacheOne + cacheTwo;
        } else if (operator.matches("[-]")) {
            return cacheOne - cacheTwo;
        } else {
            return cacheOne * cacheTwo;
        }
    }

    public void fillWords(String userInput) {
        List<String> wordsCoordinate = getWordsCoordinate();
        List<String> words = getWords();
        wordsCoordinate.add(Integer.toString(ex.getRow(userInput) + ex.getColumn(userInput)));
        words.add(ex.getToken(userInput));
        setWordsCoordinate(wordsCoordinate);
        setWords(words);
    }

    public List<String> getWords() {
        return words;
    }


    public List<String> getWordsCoordinate() {
        return wordsCoordinate;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }

    public void setWordsCoordinate(List<String> wordsCoordinate) {
        this.wordsCoordinate = wordsCoordinate;
    }

    public int calculateScoreOne() {
        int scorePlayerOne = 0;
        for (int i = 0; i < getWords().size(); i++) {
            scorePlayerOne = scorePlayerOne + Integer.parseInt(postfixFunction(getWords().get(i)).get(0));
        }
        return scorePlayerOne;
    }

    private int calculateScoreTwo() {
        int scorePlayerTwo = 0;
        for (int i = 0; i < words.size(); i++) {
            scorePlayerTwo = Integer.parseInt(postfixFunction(words.get(i)).get(0));
        }
        return scorePlayerTwo;
    }
}
