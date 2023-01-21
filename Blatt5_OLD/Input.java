public class Input {
    ScrabbleField sf = new ScrabbleField();
    Regex reg = new Regex();
    Bag b = new Bag();
    private String startInput;
    private int gameCounter;

    public String getStartInput() {
        return startInput;
    }

    public void setStartInput(String startInput) {
        this.startInput = startInput;
    }

    public void startProgram() {
        runProgram();
    }

    private void runProgram() {
        Scan sc = new Scan();
        gameCounter = 0;
        sf.setField();
        b.fillBagTokens(startInput);
        String userInput = sc.einlesen();
        while (!(userInput.matches(reg.getRegexQuit()))) {
            selectAction(userInput);
            userInput = sc.einlesen();
        }
    }

    private void selectAction(String userInput) {
        Error e = new Error();
        if (userInput.matches(reg.getRegexUserInput())) {
            if (b.checkForTokenInBag(userInput, gameCount())) {
                if (sf.validMoveCheck(userInput)) {
                    sf.placeToken(userInput, gameCount());
                    b.takeTokensOutOfBag(userInput, gameCount());
                    gameCounter++;
                } else {
                    e.isolatedMove();
                }
            } else {
                e.wrongTokens();
            }
        } else if (userInput.matches(reg.getRegexScore())) {
            sf.scoreOut();
        } else if (userInput.matches(reg.getRegexBag())) {
            b.printBag(userInput);
        } else if (userInput.matches(reg.getRegexPrint())) {
            sf.printField();
        } else {
            e.wrongInput();
        }
    }

    private String gameCount() {
        String currentPlayer;
        if ((gameCounter % 2) == 0) {
            currentPlayer = reg.getRegexPlayerOne();
        } else {
            currentPlayer = reg.getRegexPlayerTwo();
        }
        return currentPlayer;
    }

}



// 1037
