
package auth;
import com.mongodb.client.*;
import db.MongoUtil;
import org.bson.Document;
import session.SessionManager;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private MongoCollection<Document> users;

    public LoginScreen() {
        MongoClient client = MongoUtil.getClient();
        MongoDatabase db = client.getDatabase("SocialMediaManagement");
        users = db.getCollection("User");

        setTitle("Login / Signup");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();

        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Password:")); add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Sign Up");

        JPanel panel = new JPanel();
        panel.add(loginBtn); panel.add(signupBtn);
        add(panel);

        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> signup());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        Document user = users.find(new Document("Email", email)).first();

        if (user != null && user.getString("Password").equals(password)) {
            SessionManager.saveSession(email);
            JOptionPane.showMessageDialog(this, "✅ Logged in as " + user.getString("Name"));
            new home.HomeFeed(email);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid credentials or user not found.");
        }
    }

    private void signup() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (users.find(new Document("Email", email)).first() != null) {
            JOptionPane.showMessageDialog(this, "⚠️ User already exists.");
            return;
        }

        String name = JOptionPane.showInputDialog(this, "Enter your name:");
        String bio = JOptionPane.showInputDialog(this, "Enter your bio:");
        Document newUser = new Document("UserID", (int)(Math.random() * 10000))
            .append("Name", name).append("Email", email)
            .append("Password", password).append("Bio", bio)
            .append("CreatedAt", new java.util.Date());
        users.insertOne(newUser);
        SessionManager.saveSession(email);
        JOptionPane.showMessageDialog(this, "✅ Signed up as " + name);
        new home.HomeFeed(email);
        this.dispose();
    }
}
