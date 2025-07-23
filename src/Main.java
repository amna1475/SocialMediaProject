
import auth.LoginScreen;
import session.SessionManager;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String user = SessionManager.getSession();
            if (user != null) {
                new home.HomeFeed(user);
            } else {
                new auth.LoginScreen();
            }
        });
    }
    
}
