package profile;

import javax.swing.*;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.awt.*;
import java.util.List;

public class UserProfile extends JFrame {
    public UserProfile(MongoDatabase db, String userEmail) {
        setTitle("User Profile - " + userEmail);
        setSize(500, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);  // center on screen

        MongoCollection<Document> users = db.getCollection("User");
        MongoCollection<Document> posts = db.getCollection("Post");

        Document user = users.find(Filters.eq("email", userEmail)).first();

        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
            // close the frame since no user found
            dispose();
            return;
        }

        String userName = user.getString("name");
        List<String> followers = user.getList("followers", String.class);
        int totalFollowers = followers != null ? followers.size() : 0;

        List<Document> userPosts = posts.find(Filters.eq("userEmail", userEmail)).into(new java.util.ArrayList<>());
        int totalPosts = userPosts.size();
        int totalLikes = 0;
        for (Document post : userPosts) {
            totalLikes += post.getInteger("likes", 0);
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        infoPanel.add(new JLabel("👤 Name: " + userName));
        infoPanel.add(new JLabel("📩 Email: " + userEmail));
        infoPanel.add(new JLabel("📝 Total Posts: " + totalPosts));
        infoPanel.add(new JLabel("👍 Total Likes: " + totalLikes));
        infoPanel.add(new JLabel("👥 Followers: " + totalFollowers));

        add(infoPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
