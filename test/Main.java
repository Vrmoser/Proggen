public class Main {
    public static void main(String[] args) {
        Input i = new Input();
        String token = "[0-9*+-]+";
        if (args[0].matches(token) && args[1].matches(token) && args.length < 3) {
            i.startProgram(args[0], args[1]);
        } else {
            System.out.println("Error:. not a valid start input!");
        }
    }
}
