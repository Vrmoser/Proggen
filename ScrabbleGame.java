import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ScrabbleGame {
    ScrabbleField sf = new ScrabbleField();
    Regex rg = new Regex();
    Extractor ex = new Extractor();
    Bag b1 = new Bag();
    Bag b2 = new Bag();
    Error er = new Error();


    private List<String> words = new LinkedList<>();
    private List<String> wordsCoordinate = new LinkedList<>();
    private int gameCounter;

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
        wordsCoordinate.add(
                Integer.toString(ex.getRow(userInput))
                        + Integer.toString(ex.getColumn(userInput))
                        + ex.getDirection(userInput));
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
            if (whichWordWhichPlayer(i) == 1) {
                scorePlayerOne = scorePlayerOne + Integer.parseInt(postfixFunction(getWords().get(i)).get(0));
            }
        }
        return scorePlayerOne;
    }

    private int calculateScoreTwo() {
        int scorePlayerTwo = 0;
        for (int i = 0; i < getWords().size(); i++) {
            if (whichWordWhichPlayer(i) == 2) {
                scorePlayerTwo = scorePlayerTwo + Integer.parseInt(postfixFunction(getWords().get(i)).get(0));
            }
        }
        return scorePlayerTwo;
    }

    public void scoreOutOne() {
        System.out.println(calculateScoreOne());
    }

    public void scoreOutTwo() {
        System.out.println(calculateScoreTwo());
    }

    public void score(String userInput) {
        if (ex.getBagScorePlayer(userInput).matches(rg.getRegexPlayerOne())) {
            scoreOutOne();
        } else if (ex.getBagScorePlayer(userInput).matches(rg.getRegexPlayerTwo())) {
            scoreOutTwo();
        } else {
            er.noValidPlayer();
        }
    }

    public void printField() {
        sf.printField();
    }

    public void printBag(String userInput) {
        if (ex.getBagScorePlayer(userInput).matches(rg.getRegexPlayerOne())) {
            b1.printBag();
        } else if (ex.getBagScorePlayer(userInput).matches(rg.getRegexPlayerTwo())) {
            b2.printBag();
        } else {
            er.noValidPlayer();
        }
    }

    public boolean checkForTokenInBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne()) && b1.tokensMinusBag(userInput)) {
            return b1.lookForToken(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo()) && b2.tokensMinusBag(userInput)) {
            return b2.lookForToken(userInput);
        }
        return false;
    }

    public void takeTokensOutOfBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne())) {
            b1.removeUsedTokensBag(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo())) {
            b2.removeUsedTokensBag(userInput);
        }
    }

    public void placeToken(String userInput, String currentPlayer) {
        fillWords(userInput);
        if (ex.getDirection(userInput).matches("H")) {
            sf.tokenPlacerH(userInput, currentPlayer);
            takeTokensOutOfBag(userInput, currentPlayer);
        } else if (ex.getDirection(userInput).matches("V")) {
            sf.tokenPlacerV(userInput, currentPlayer);
            takeTokensOutOfBag(userInput, currentPlayer);
        }
    }

    public void place(String userInput, String currentPlayer) {
        if (checkForTokenInBag(userInput, currentPlayer)) {
            if (sf.validMoveCheck(userInput)) {
                if (sf.directionCheck(userInput)) {
                    placeToken(userInput, currentPlayer);
                    if (isPostFixCurrent()) {
                        setPlayerCounter();
                    }
                } else {
                    er.outOfBounds();
                }
            } else {
                er.isolatedMove();
            }
        } else {
            er.wrongTokens();
        }
    }

    private void setPlayerCounter() {
        this.gameCounter = gameCounter + 1;
    }

    public int getGameCounter() {
        return gameCounter;
    }

    public void initialize(String startInputOne, String startInputTwo) {
        sf.setField();
        b1.fillBagTokens(startInputOne);
        b2.fillBagTokens(startInputTwo);
    }

    private int whichWordWhichPlayer(int j) {
        int playerOneCount = 0;
        int playerTwoCount = 0;
        for (int i = 0; i < getWords().get(j).length(); i++) {
            if (Objects.equals(ex.getWordCoordinateDirection(getWordsCoordinate().get(j)), "H")) {
                if (sf.getPlayerIDtoCoordinate(
                        Integer.parseInt(ex.getWordCoordinateRow(getWordsCoordinate().get(j))),
                        Integer.parseInt(ex.getWordCoordinateColumn(getWordsCoordinate().get(j))) + i) == 1) {
                    playerOneCount++;
                } else if (sf.getPlayerIDtoCoordinate(
                        Integer.parseInt(ex.getWordCoordinateRow(getWordsCoordinate().get(j))),
                        Integer.parseInt(ex.getWordCoordinateColumn(getWordsCoordinate().get(j))) + i) == 2) {
                    playerTwoCount++;
                }
            } else {
                if (sf.getPlayerIDtoCoordinate(
                        Integer.parseInt(ex.getWordCoordinateRow(getWordsCoordinate().get(j))) + i,
                        Integer.parseInt(ex.getWordCoordinateColumn(getWordsCoordinate().get(j)))) == 1) {
                    playerOneCount++;
                } else if (sf.getPlayerIDtoCoordinate(
                        Integer.parseInt(ex.getWordCoordinateRow(getWordsCoordinate().get(j))) + i,
                        Integer.parseInt(ex.getWordCoordinateColumn(getWordsCoordinate().get(j))) + 1) == 2) {
                    playerTwoCount++;
                }
            }
        }
        if (playerTwoCount < playerOneCount) {
            return 1;
        } else if (playerOneCount < playerTwoCount) {
            return 2;
        }
        return 0;
    }

    private boolean isPostFixCurrent() {
        String word = words.get(0);
        String cooDirection = wordsCoordinate.get(0);
        for (int i = 0; i < word.length(); i++) {
            if (cooDirection.contains("H")) {
                int xPlus = Integer.parseInt(cooDirection.substring(0, 1)) + i;
                int xMinus = Integer.parseInt(cooDirection.substring(0, 1)) - i;
                int y = Integer.parseInt(cooDirection.substring(1, 2));
                if (!(sf.getField()[xPlus][y].matches(" "))) {
                    if (!(controlCurrentWordH(xPlus, y, word.length()))) {
                        return false;
                    }
                }
                if (!(sf.getField()[xPlus][y].matches(" "))) {
                    if (!(controlCurrentWordH(xMinus, y, word.length()))) {
                        return false;
                    }
                }
            }
            if (cooDirection.contains("V")) {
                int x = Integer.parseInt(cooDirection.substring(0, 1));
                int yPlus = Integer.parseInt(cooDirection.substring(1, 2)) + i;
                int yMinus = Integer.parseInt(cooDirection.substring(1, 2)) - i;
                if (!(sf.getField()[x][yPlus].matches(" "))) {
                    if (!(controlCurrentWordV(x, yPlus, word.length()))) {
                        return false;
                    }
                }
                if (!(sf.getField()[x][yMinus].matches(" "))) {
                    if (!(controlCurrentWordV(x, yMinus, word.length()))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean controlCurrentWordH(int x, int y, int length) {
        List<String> currentWord = new LinkedList<>();
        int startCoordinate = 0;
        int count = 0;
        StringBuilder tempWord = new StringBuilder();
        for (int i = length; i > 0; i--) {
            if (sf.getField()[x + i][y].matches(" ")) {
                startCoordinate = x + i - 1;
            }
        }
        while (!(sf.getField()[startCoordinate + count][y]).matches(" ")) {
            tempWord.append(sf.getField()[startCoordinate + count][y]);
            count++;
        }
        if (isPostFix(tempWord.toString())) {
            int wordCheckIndex = wordsCoordinate.indexOf(startCoordinate + y + "H");
            words.set(wordCheckIndex, tempWord.toString());
            return true;
        }
        return false;
    }

    private boolean controlCurrentWordV(int x, int y, int length) {
        int startCoordinate = 0;
        int count = 0;
        StringBuilder tempWord = new StringBuilder();
        for (int i = length; i > 0; i--) {
            if (sf.getField()[x][y + i].matches(" ")) {
                startCoordinate = y + i - 1;
            }
        }
        while (!(sf.getField()[startCoordinate][y + count]).matches(" ")) {
            tempWord.append(sf.getField()[startCoordinate][y + count]);
            count++;
        }
        if (isPostFix(tempWord.toString())) {
            int wordCheckIndex = wordsCoordinate.indexOf(startCoordinate + y + "H");
            words.set(wordCheckIndex, tempWord.toString());
            return true;
        }
        return false;
    }


/**
 private List<String> allWordsVertical() {
 List<String> allWords = new LinkedList<String>();
 StringBuilder tempWord = new StringBuilder();
 int emptyCount = 0;
 int numberCount = 0;
 for (int i = 0; i < sf.getField().length; i++) {
 while (!(sf.getField()[i][numberCount].equals(" "))) {
 tempWord.append(sf.getField()[i][numberCount]);
 numberCount++;
 if (sf.getField()[i][numberCount].equals(" ")) {
 emptyCount++;
 }
 if (emptyCount != 0) {
 allWords.add(String.valueOf(tempWord));
 tempWord.delete(0, tempWord.length());
 emptyCount = 0;
 numberCount = 0;
 }
 }
 }
 return allWords;
 }
 private List<String> allWordsHorizontal() {
 List<String> allWords = new LinkedList<String>();
 StringBuilder tempWord = new StringBuilder();
 int emptyCount = 0;
 int numberCount = 0;
 for (int i = 0; i < sf.getField().length; i++) {
 for (int j = 0; j < sf.getField()[0].length; j++) {
 while (!(sf.getField()[i + numberCount][j].equals(" "))) {
 tempWord.append(sf.getField()[i + numberCount][j]);
 numberCount++;
 if (sf.getField()[i + numberCount][j].equals(" ")) {
 emptyCount++;
 }
 if (emptyCount != 0) {
 allWords.add(String.valueOf(tempWord));
 tempWord.delete(0, tempWord.length());
 emptyCount = 0;
 numberCount = 0;
 }
 }
 }
 }
 return allWords;
 }
 private List<String> allWordsHorizontaly() {
 List<String> allWords = new LinkedList<String>();
 StringBuilder tempWord = new StringBuilder();
 int emptyCount = 0;
 for (int i = 0; i < sf.getField().length; i++) {
 for (int j = 0; j < sf.getField()[0].length; j++) {
 if (emptyCount == 0) {
 while (!(sf.getField()[j][i].equals(" "))) {
 tempWord.append(sf.getField()[i][j]);
 if (sf.getField()[j + 1][i].equals(" ")) {
 emptyCount++;
 } else {
 allWords.add(String.valueOf(tempWord));
 tempWord.delete(0, tempWord.length());
 emptyCount = 0;
 }
 }
 }
 }
 }
 return allWords;
 }

 private List<String> allWordCombined() {
 List<String> allWords = new LinkedList<String>();
 allWords.addAll(allWordsVertical());
 allWords.addAll(allWordsHorizontal());
 return allWords;
 }
 */
    /**
     * private boolean isPostFix() {
     * int numberCount = 0;
     * int operatorCount = 0;
     * for (int i = 0; i < allWordCombined().size(); i++) {
     * for (int j = 0; j < allWordCombined().get(i).length(); j++) {
     * while(allWordCombined().get(i).substring(i, i+1).matches("[0-9]")){
     * numberCount++;
     * }
     * if(allWordCombined().get(i).substring(i, i+1).matches("[*+-]")){
     * operatorCount++;
     * }
     * if(operatorCount==numberCount-1){
     * <p>
     * }
     * }
     * }
     * }
     */
    private boolean isPostFix(String word) {
        List<String> wordList = new LinkedList<String>();
        StringBuilder tempWord = new StringBuilder();
        StringBuilder resultWord = new StringBuilder();
        int numberCount = 0;
        int operatorCount = 0;
        int whileCountBefore = 0;
        int whileCountAfter = 0;

        for (int i = 0; i < ex.eachCharAsStringList(word).size(); i++) {
            while (ex.eachCharAsStringList(word).get(i).matches("[0-9]") && operatorCount == 0) {
                tempWord.append(ex.eachCharAsStringList(word).get(i));
                wordList.add(ex.eachCharAsStringList(word).get(i));
                numberCount++;
            }
            if (ex.eachCharAsStringList(word).get(i).matches("[+*-]") && operatorCount < numberCount
                    && ex.eachCharAsStringList(word).get(i + 1).matches("[+*-]")) {
                tempWord.append(ex.eachCharAsStringList(word).get(i));
                wordList.add(ex.eachCharAsStringList(word).get(i));
                operatorCount++;
            }
            if (operatorCount == numberCount - 1) {
                String result = ex.setWordToResult(wordList, operatorCount + numberCount,
                        Integer.parseInt(postfixFunction(tempWord.toString()).get(0))).get(0);
                word = word.replaceFirst(String.valueOf(tempWord), result);
                resultWord.delete(0, resultWord.length());
                resultWord.append(result);
                tempWord.delete(0, tempWord.length());
                wordList.clear();
            }
        }
        return resultWord.length() == 1 && resultWord.substring(0, 1).matches("[0-9]+");
    }


/**
 private boolean isPostFixAllWords() {
 for (int i = 0; i < allWordCombined().size(); i++) {
 if (!(isPostFix(allWordCombined().get(i)))) {
 return false;
 }
 }
 return true;
 }
 */
}
