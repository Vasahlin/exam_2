import userInteraction.UserInteraction;

public class Main {
    public static void main(String[] args) {
        UserInteraction ui = new UserInteraction();
        while (true) {
            if (ui.start() == 0) {
                break;
            }
        }
    }
}