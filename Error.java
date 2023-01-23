public class Error {
    public void wrongInput() {
        System.out.println("ERROR : wrong Input");
    }

    public void noStartInput() {
        System.out.println("ERROR : tokens are missing!");
    }

    public void tokenAmount() {
        System.out.println("ERROR : Only 3 Tokens allowed!");
    }

    public void outOfBounds() {
        System.out.println("ERROR : Token out of Bounds!");
    }

    public void notEmptyField() {
        System.out.println("ERROR : Field already used!");
    }

    public void noValidPlayer() {
        System.out.println("ERROR : Enter a valid Player!");
    }

    public void wrongTokens() {
        System.out.println("ERROR : Select different Tokens!");
    }

    public void isolatedMove() {
        System.out.println("ERROR : Token isolated!");
    }
}


