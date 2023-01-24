public class Error {
    public void wrongInput() {
        System.out.println("Error:. wrong Input");
    }

    public void noStartInput() {
        System.out.println("Error:. tokens are missing!");
    }

    public void tokenAmount() {
        System.out.println("Error:. Only 3 Tokens allowed!");
    }

    public void outOfBounds() {
        System.out.println("Error:. Token out of Bounds!");
    }

    public void notEmptyField() {
        System.out.println("Error:. Field already used!");
    }

    public void noValidPlayer() {
        System.out.println("Error:. Enter a valid Player!");
    }

    public void wrongTokens() {
        System.out.println("Error:. Select different Tokens!");
    }

    public void isolatedMove() {
        System.out.println("Error:. Token isolated!");
    }

    public void noPostFix() {
        System.out.println("Error:. Move not allowed according to post fix expression!");
    }
}


