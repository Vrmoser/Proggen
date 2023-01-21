import java.util.Objects;

public class ScrabbleField {
    private String[][] field = new String[10][10];
    Extractor ex = new Extractor();
    Error er = new Error();
    ScrabbelGame sg = new ScrabbelGame();

    public void printField() {
        printer();
    }

    public void setField() {
        createField();
        sg.createPlayerIDField();
    }


    private void printer() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                System.out.print(eachField(field, i, j));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void createField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = " ";
            }
        }
    }

    private String eachField(String[][] field, int i, int j) {
        return "[" + field[i][j] + "]";
    }

    public void placeToken(String userInput, String currentPlayer) {
        if (directionCheck(userInput)) {
            sg.fillWords(userInput);
            if (ex.getDirection(userInput).matches("H")) {
                tokenPlacerH(userInput, currentPlayer);
            } else if (ex.getDirection(userInput).matches("V")) {
                tokenPlacerV(userInput, currentPlayer);
            }
        }
    }

    private void tokenPlacerV(String userInput, String currentPlayer) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            field[ex.getRow(userInput) + i][ex.getColumn(userInput)] = ex.getToken(userInput).substring(i, i + 1);
            sg.setFieldToPlayer(ex.getRow(userInput) + i, ex.getColumn(userInput), currentPlayer);
        }
    }

    private void tokenPlacerH(String userInput, String currentPlayer) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            field[ex.getRow(userInput)][ex.getColumn(userInput) + i] = ex.getToken(userInput).substring(i, i + 1);
            sg.setFieldToPlayer(ex.getRow(userInput), ex.getColumn(userInput) + i, currentPlayer);
        }
    }

    private boolean isEmptyFieldH(String userInput) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            if (!Objects.equals(field[ex.getRow(userInput) + i][ex.getColumn(userInput)], " ")) {
                er.notEmptyField();
                return false;
            }
        }
        return true;
    }

    private boolean isEmptyFieldV(String userInput) {
        for (int i = 0; i < ex.getToken(userInput).length(); i++) {
            if (!Objects.equals(field[ex.getRow(userInput)][ex.getColumn(userInput) + i], " ")) {
                er.notEmptyField();
                return false;
            }
        }
        return true;
    }

    private boolean isInOfBoundsH(String userInput) {
        if (ex.getColumn(userInput) + ex.getToken(userInput).length() - 1 < field.length) {
            return true;
        } else {
            er.outOfBounds();
            return false;
        }

    }

    private boolean isInOfBoundsV(String userInput) {
        if (ex.getRow(userInput) + ex.getToken(userInput).length() - 1 < field[0].length) {
            return true;
        } else {
            er.outOfBounds();
            return false;
        }
    }

    private boolean isBoundsEmptyLengthH(String userInput) {
        if (tokenLength(userInput) && isInOfBoundsH(userInput)) {
            return isEmptyFieldH(userInput);
        }
        return false;
    }

    private boolean isBoundsEmptyLengthV(String userInput) {
        if (tokenLength(userInput) && isInOfBoundsV(userInput)) {
            return isEmptyFieldV(userInput);
        }
        return false;
    }

    private boolean tokenLength(String userInput) {
        if (ex.getToken(userInput).length() <= 3) {
            return true;
        } else {
            er.tokenAmount();
            return false;
        }
    }

    private boolean directionCheck(String userInput) {
        if (ex.getDirection(userInput).matches("H")) {
            return isBoundsEmptyLengthH(userInput);
        }
        if (ex.getDirection(userInput).matches("V")) {
            return isBoundsEmptyLengthV(userInput);
        }
        return false;
    }

    private boolean moveConditionIsolated(String userInput) {
        return field[ex.getRow(userInput) + 1][ex.getColumn(userInput)].equals(" ")
                && field[ex.getRow(userInput) - 1][ex.getColumn(userInput)].equals(" ")
                && field[ex.getRow(userInput)][ex.getColumn(userInput) + 1].equals(" ")
                && field[ex.getRow(userInput)][ex.getColumn(userInput) - 1].equals(" ");
    }

    private boolean onlyOneTokenMove(String userInput) {
        return ex.getToken(userInput).length() == 1;
    }

    public boolean validMoveCheck(String userInput) {
        if (onlyOneTokenMove(userInput)) {
            return !moveConditionIsolated(userInput);
        }
        return true;
    }
    public void scoreOut(){
        System.out.println(sg.calculateScoreOne());
    }


}
