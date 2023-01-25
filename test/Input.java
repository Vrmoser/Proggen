public class Input {
    ScrabbleGame sg = new ScrabbleGame();
    Error er = new Error();
    Regex rg = new Regex();
    Scan sc = new Scan();

    private String startInputOne;
    private String startInputTwo;
    private int gameCounter;

    public String getStartInputOne() {
        return startInputOne;
    }

    public void setStartInputOne(String startInput) {
        this.startInputOne = startInput;
    }

    public String getStartInputTwo() {
        return startInputTwo;
    }

    public void setStartInputTwo(String startInput) {
        this.startInputTwo = startInput;
    }

    public void startProgram(String startInputOne, String startInputTwo) {
        setStartInputOne(startInputOne);
        setStartInputTwo(startInputTwo);
        runProgram();
    }

    private void runProgram() {
        sg.initialize(getStartInputOne(), getStartInputTwo());
        gameCounter = 0;
        String userInput = sc.einlesen();
        if (userInput.matches(rg.getRegexQuit())) {
            sg.endGame();
        }
        while (!(userInput.matches(rg.getRegexQuit()))) {
            selectAction(userInput);
            userInput = sc.einlesen();
            if (userInput.matches(rg.getRegexQuit())) {
                sg.endGame();
            }
        }
    }

    private void selectAction(String userInput) {
        if (userInput.matches(rg.getRegexUserInput())) {
            sg.place(userInput, gameCount());
            gameCounter = sg.getGameCounter();
        } else if (userInput.matches(rg.getRegexScore())) {
            sg.score(userInput);
        } else if (userInput.matches(rg.getRegexBag())) {
            sg.printBag(userInput);
        } else if (userInput.matches(rg.getRegexPrint())) {
            sg.printField();
        } else {
            er.wrongInput();
        }
    }

    private String gameCount() {
        String currentPlayer;
        if ((gameCounter % 2) == 0) {
            currentPlayer = rg.getRegexPlayerOne();
        } else {
            currentPlayer = rg.getRegexPlayerTwo();
        }
        return currentPlayer;
    }
}
