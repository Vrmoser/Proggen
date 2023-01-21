public class Main {
    public static void main(String[] args) {
        Input i = new Input();
        Error e = new Error();
        args = new String[1];
        args[0] = "ScrabbleGame 1234++-*+++ 4321++-*---";
        if (args.length == 1) {
            i.setStartInput(args[0]);
        } else {
            e.noStartInput();
        }
        i.startProgram();
    }
}
