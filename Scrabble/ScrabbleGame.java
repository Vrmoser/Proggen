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
            return cacheTwo - cacheOne;
        } else {
            return cacheOne * cacheTwo;
        }
    }

    private void fillWords(String checkedWord, int startPoint, String userInput) {
        List<String> wordsCoordinate = getWordsCoordinate();
        List<String> words = getWords();
        words = this.words;
        wordsCoordinate = this.wordsCoordinate;
        int checkCount = 0;
        if (ex.getDirection(userInput).matches("H")) {
            for (int i = startPoint; i < checkedWord.length(); i++) {
                for (int j = 0; j < wordsCoordinate.size(); j++) {
                    if (wordsCoordinate.get(j).matches(ex.getRow(userInput) + i + "H")) {
                        wordsCoordinate.set(j, Integer.toString(ex.getRow(userInput))
                                + Integer.toString(startPoint) + "H");
                        words.set(j, checkedWord);
                        checkCount++;
                    }
                }
            }
            if (checkCount == 0) {
                wordsCoordinate.add(Integer.toString(ex.getRow(userInput)) + Integer.toString(startPoint) + "H");
                words.add(checkedWord);
            }
        }

        if (ex.getDirection(userInput).matches("V")) {
            for (int i = startPoint; i < checkedWord.length(); i++) {
                for (int j = 0; j < wordsCoordinate.size(); j++) {
                    if (wordsCoordinate.get(j).matches(i + ex.getColumn(userInput) + "V")) {
                        wordsCoordinate.set(j, Integer.toString(startPoint)
                                + Integer.toString(ex.getColumn(userInput)) + "V");
                        words.set(j, checkedWord);
                        checkCount++;
                    }
                }
            }
            if (checkCount == 0) {
                wordsCoordinate.add(Integer.toString(startPoint) + Integer.toString(ex.getColumn(userInput)) + "V");
                words.add(checkedWord);
            }
        }

        setWords(words);
        setWordsCoordinate(wordsCoordinate);
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

    private int calculateScoreOne() {
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
                // if (getWords().get(i).length() < 1) {
                //     return 0;
                // }
                scorePlayerTwo = scorePlayerTwo + Integer.parseInt(postfixFunction(getWords().get(i)).get(0));
            }
        }
        return scorePlayerTwo;
    }

    private void scoreOutOne() {
        System.out.println(calculateScoreOne());
    }

    private void scoreOutTwo() {
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

    private boolean checkForTokenInBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne()) && b1.tokensMinusBag(userInput)) {
            return b1.lookForToken(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo()) && b2.tokensMinusBag(userInput)) {
            return b2.lookForToken(userInput);
        }
        return false;
    }

    private void takeTokensOutOfBag(String userInput, String currentPlayer) {
        if (currentPlayer.matches(rg.getRegexPlayerOne())) {
            b1.removeUsedTokensBag(userInput);
        }
        if (currentPlayer.matches(rg.getRegexPlayerTwo())) {
            b2.removeUsedTokensBag(userInput);
        }
    }

    private void placeToken(String userInput, String currentPlayer) {
        //fillWords(userInput);
        if (ex.getDirection(userInput).matches("H")) {
            sf.tokenPlacerH(userInput, currentPlayer);
            //takeTokensOutOfBag(userInput, currentPlayer);
        } else if (ex.getDirection(userInput).matches("V")) {
            sf.tokenPlacerV(userInput, currentPlayer);
            //takeTokensOutOfBag(userInput, currentPlayer);
        }
    }

    private void removeWrongTokenFromField(String userInput, String currentPlayer) {
        // fillWords(userInput);
        if (ex.getDirection(userInput).matches("H")) {
            sf.tokenRemoverH(userInput);
            //takeTokensOutOfBag(userInput, currentPlayer);
        } else if (ex.getDirection(userInput).matches("V")) {
            sf.tokenRemoverV(userInput);
            //takeTokensOutOfBag(userInput, currentPlayer);
        }
    }

    public void place(String userInput, String currentPlayer) {
        if (checkForTokenInBag(userInput, currentPlayer)) {
            if (sf.validMoveCheck(userInput)) {
                if (sf.directionCheck(userInput)) {
                    placeToken(userInput, currentPlayer);
                    if (areSurroundingWordsAlsoValidPostFixExpressions(userInput)) {
                        setPlayerCounter();
                        takeTokensOutOfBag(userInput, currentPlayer);
                        System.out.println("OK");
                    } else {
                        er.noPostFix();
                        //removeWrongWord();
                        removeWrongTokenFromField(userInput, currentPlayer);
                    }
                }
            } else {
                er.isolatedMove();
            }
        } else {
            er.wrongTokens();
        }
    }

    private void removeWrongWord() {
        words.remove(words.size() - 1);
        wordsCoordinate.remove(wordsCoordinate.size() - 1);
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

    private boolean areSurroundingWordsAlsoValidPostFixExpressionsH(String userInput) {
        int startPoint = ex.getColumn(userInput);
        int x = ex.getRow(userInput);
        int fieldCounter = 0;
        int minus = 1;
        while (!(sf.getField()[x][startPoint - fieldCounter].matches(" "))) {
            if (startPoint - (fieldCounter + 1) >= 0) {
                fieldCounter++;
            } else break;
        }
        if (fieldCounter == 0) {
            minus = 0;
        }
        if (!(produceNewWordV(startPoint - fieldCounter + minus, userInput))) {
            return false;
        }
        minus = 1;
        for (int i = ex.getColumn(userInput); i < ex.getToken(userInput).length(); i++) {
            fieldCounter = 0;
            while (!(sf.getField()[startPoint - fieldCounter][i].matches(" "))) {
                if (startPoint - (fieldCounter + 1) >= 0) {
                    fieldCounter++;
                } else break;
            }
            if (fieldCounter == 0) {
                minus = 0;
            }
            if (!(produceNewWordH(startPoint - fieldCounter + minus, userInput))) {
                return false;
            }
        }
        return true;
    }

    private boolean areSurroundingWordsAlsoValidPostFixExpressionsV(String userInput) {
        int y = ex.getColumn(userInput);
        int startPoint = ex.getRow(userInput);
        int fieldCounter = 0;
        int minus = 1;
        while (!(sf.getField()[startPoint - fieldCounter][y].matches(" "))) {
            if (startPoint - (fieldCounter + 1) >= 0) {
                fieldCounter++;
            } else break;
        }
        if (fieldCounter == 0) {
            minus = 0;
        }
        if (!(produceNewWordH(startPoint - fieldCounter + minus, userInput))) {
            return false;
        }
        minus = 1;
        for (int i = ex.getRow(userInput); i < ex.getToken(userInput).length(); i++) {
            fieldCounter = 0;
            while (!(sf.getField()[i][startPoint - fieldCounter].matches(" "))) {
                if (startPoint - (fieldCounter + 1) >= 0) {
                    fieldCounter++;
                } else break;
            }
            if (fieldCounter == 0) {
                minus = 0;
            }
            if (!(produceNewWordV(startPoint - fieldCounter + minus, userInput))) {
                return false;
            }
        }
        return true;
    }

    private boolean produceNewWordH(int startX, String userInput) {
        int fieldCount = 0;
        StringBuilder chars = new StringBuilder();
        while (!(sf.getField()[startX + fieldCount][ex.getColumn(userInput)].matches(" "))) {
            chars.append(sf.getField()[startX + fieldCount][ex.getColumn(userInput)]);
            if (startX + fieldCount + 1 < sf.getField().length) {
                fieldCount++;
            } else break;
        }
        if (chars.isEmpty() || chars.length() == 1) {
            return true;
        }
        if (postfixFunctionBoolean(chars.toString())) {
            fillWords(chars.toString(), startX, userInput);
            return true;
        }
        return false;
    }

    private boolean produceNewWordV(int startY, String userInput) {
        int fieldCount = 0;
        StringBuilder chars = new StringBuilder();
        while (!(sf.getField()[ex.getRow(userInput)][startY + fieldCount].matches(" "))) {
            chars.append(sf.getField()[ex.getRow(userInput)][startY + fieldCount]);
            if (startY + fieldCount + 1 < sf.getField().length) {
                fieldCount++;
            } else break;
        }
        if (chars.isEmpty() || chars.length() == 1) {
            return true;
        }
        if (postfixFunctionBoolean(chars.toString())) {
            fillWords(chars.toString(), startY, userInput);
            return true;
        }
        return false;
    }

    private boolean areSurroundingWordsAlsoValidPostFixExpressions(String userInput) {
        if (ex.getDirection(userInput).matches("H")) {
            return areSurroundingWordsAlsoValidPostFixExpressionsH(userInput);
        }
        if (ex.getDirection(userInput).matches("V")) {
            return areSurroundingWordsAlsoValidPostFixExpressionsV(userInput);
        }
        return false;
    }

    private boolean postfixFunctionBoolean(String expression) {
        List<String> stack = new LinkedList<String>();
        if (expression.length() < 3) {
            return false;
        }
        for (int i = 0; i < expression.length(); i++) {
            if (isNumber(expression.substring(i, i + 1))) {
                stack.add(expression.substring(i, i + 1));
            } else if (isOperator(expression.substring(i, i + 1))) {
                int cacheOne = Integer.parseInt(stack.get(0));
                stack.remove(0);
                if (stack.size() == 0) {
                    return false;
                }
                int cacheTwo = Integer.parseInt(stack.get(0));
                stack.remove(0);
                int result = calculate(cacheTwo, cacheOne, expression.substring(i, i + 1));
                stack.add(Integer.toString(result));
            }
        }
        return stack.size() == 1;
    }

    public void endGame() {
        System.out.println(calculateScoreOne());
        System.out.println(calculateScoreTwo());
        if (calculateScoreOne() < calculateScoreTwo()) {
            System.out.println("P2 wins");
        } else if (calculateScoreTwo() < calculateScoreOne()) {
            System.out.println("P1 wins");
        } else {
            System.out.println("draw");
        }
    }
}
