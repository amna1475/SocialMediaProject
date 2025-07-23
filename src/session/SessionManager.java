
package session;
import java.io.*;

public class SessionManager {
    static String file = "session.txt";

    public static void saveSession(String email) {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(email);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSession() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public static void clearSession() {
        new File(file).delete();
    }
}
