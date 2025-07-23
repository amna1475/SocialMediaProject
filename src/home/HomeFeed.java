package home;

import com.mongodb.client.*;
import org.bson.Document;
import db.MongoUtil;
import profile.UserProfile;
import session.SessionManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class HomeFeed extends JFrame {
    private MongoCollection<Document> postsCollection;
    private MongoCollection<Document> usersCollection;
    private MongoCollection<Document> notificationsCollection;
    private String currentUserEmail;

    private JPanel feedPanel;
    private JScrollPane scrollPane;

    public HomeFeed(String userEmail) {
        this.currentUserEmail = userEmail;
        MongoClient client = MongoUtil.getClient();
        MongoDatabase db = client.getDatabase("SocialMediaManagement");
        postsCollection = db.getCollection("Post");
        usersCollection = db.getCollection("User");
        notificationsCollection = db.getCollection("Notification");

        setTitle("Home Feed - " + userEmail);
        setSize(900, 700);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Header
        JLabel header = new JLabel("🏠 Home Feed", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setOpaque(true);
        header.setBackground(new Color(59, 89, 152));
        header.setForeground(Color.WHITE);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        add(header, BorderLayout.NORTH);

        // NavBar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        navBar.setBackground(new Color(240, 240, 255));

        JButton homeBtn = new JButton("🏠 Home");
        JButton searchBtn = new JButton("🔍 Search");
        JButton notificationsBtn = new JButton("🔔 Notifications");
        JButton createPostBtn = new JButton("➕ Create Post");
        JButton profileBtn = new JButton("👤 My Profile");

        navBar.add(homeBtn);
        navBar.add(searchBtn);
        navBar.add(notificationsBtn);
        navBar.add(createPostBtn);
        navBar.add(profileBtn);

        add(navBar, BorderLayout.PAGE_START);

        // Feed panel with scroll
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);
        scrollPane = new JScrollPane(feedPanel);
        add(scrollPane, BorderLayout.CENTER);
        

        
        loadAllPosts();

        homeBtn.addActionListener(e -> loadAllPosts());

        searchBtn.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Enter User Name or ID:");
            if (input != null && !input.trim().isEmpty()) {
                Document userDoc = null;
                try {
                    int id = Integer.parseInt(input.trim());
                    userDoc = usersCollection.find(new Document("UserID", id)).first();
                } catch (NumberFormatException ex) {
                    userDoc = usersCollection.find(new Document("Name", input.trim())).first();
                }
                
            }
        });

        notificationsBtn.addActionListener(e -> {
            feedPanel.removeAll();
            for (Document notif : notificationsCollection.find(new Document("UserID", getCurrentUserID()))) {
                JLabel label = new JLabel("🔔 " + notif.getString("Message"));
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                feedPanel.add(label);
            }
            refreshFeed();
        });

        createPostBtn.addActionListener(e -> {
            String content = JOptionPane.showInputDialog(this, "Enter post content:");
            if (content != null && !content.trim().isEmpty()) {
                Document newPost = new Document("UserID", getCurrentUserID())
                        .append("Content", content.trim())
                        .append("CreatedAt", new Date());
                postsCollection.insertOne(newPost);
                JOptionPane.showMessageDialog(this, "Post created.");
                loadAllPosts();
            }
        });

        // Footer Panel with Buttons
JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
footerPanel.setBackground(new Color(240, 240, 255));

JButton topLatestBtn = new JButton("🆕 Top 20 Latest Posts");
JButton topLikedBtn = new JButton("🔥 Top 10 Liked Posts");
JButton topFollowedBtn = new JButton("🌟 Top 10 Most Followed Users' Posts");

footerPanel.add(topLatestBtn);
footerPanel.add(topLikedBtn);
footerPanel.add(topFollowedBtn);

add(footerPanel, BorderLayout.SOUTH);

// Button Listeners
topLatestBtn.addActionListener(e -> loadTopLatestPosts());
topLikedBtn.addActionListener(e -> loadTopLikedPosts());
topFollowedBtn.addActionListener(e -> loadTopFollowedUsersPosts());
    
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void loadTopLatestPosts() {
    feedPanel.removeAll();
    FindIterable<Document> posts = postsCollection.find()
            .sort(new Document("CreatedAt", -1))
            .limit(20);
    for (Document post : posts) {
        feedPanel.add(createPostPanel(post));
    }
    refreshFeed();
}
private void loadTopLikedPosts() {
    feedPanel.removeAll();
    FindIterable<Document> posts = postsCollection.find()
            .sort(new Document("Likes", -1))
            .limit(10);
    for (Document post : posts) {
        feedPanel.add(createPostPanel(post));
    }
    refreshFeed();
}

private void loadTopFollowedUsersPosts() {
    feedPanel.removeAll();
    
    // Get top 10 most followed users
    ArrayList<Integer> topUserIDs = new ArrayList<>();
    for (Document user : usersCollection.find()
            .sort(new Document("FollowersCount", -1))
            .limit(10)) {
        topUserIDs.add(user.getInteger("UserID"));
    }

    // Get random post from each top user (if exists)
    for (Integer userID : topUserIDs) {
        Document post = postsCollection.find(new Document("UserID", userID))
                .sort(new Document("CreatedAt", -1))
                .first();
        if (post != null) {
            feedPanel.add(createPostPanel(post));
        }
    }

    refreshFeed();
}

    private void loadAllPosts() {
        feedPanel.removeAll();
        for (Document post : postsCollection.find()) {
            feedPanel.add(createPostPanel(post));
        }
        refreshFeed();
    }

    private JPanel createPostPanel(Document post) {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 89, 152), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        postPanel.setBackground(new Color(245, 245, 255));
        postPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        postPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        String userName = getUserName(post.getInteger("UserID"));

        JLabel userLabel = new JLabel("👤 " + userName);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(new Color(59, 89, 152));

        JLabel contentLabel = new JLabel("<html><body style='width: 700px'>📝 " + post.getString("Content") + "</body></html>");
        contentLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentLabel.setForeground(Color.DARK_GRAY);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 255));
        topPanel.add(userLabel, BorderLayout.WEST);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(new Color(245, 245, 255));

        JButton likeBtn = new JButton("👍 Like");
        JButton commentBtn = new JButton("💬 Comment");
        JButton followBtn = new JButton("➕ Follow");
        JButton profileBtn = new JButton("👤 See Profile");

        buttonsPanel.add(likeBtn);
        buttonsPanel.add(commentBtn);
        buttonsPanel.add(followBtn);
        buttonsPanel.add(profileBtn);

        postPanel.add(topPanel, BorderLayout.NORTH);
        postPanel.add(contentLabel, BorderLayout.CENTER);
        postPanel.add(buttonsPanel, BorderLayout.SOUTH);

        likeBtn.addActionListener(e -> {
            Document notification = new Document("UserID", post.getInteger("UserID"))
                    .append("Message", getUserName(getCurrentUserID()) + " liked your post")
                    .append("CreatedAt", new Date())
                    .append("IsRead", false);
            notificationsCollection.insertOne(notification);
        });

        commentBtn.addActionListener(e -> {
            String comment = JOptionPane.showInputDialog(this, "Enter your comment:");
            if (comment != null && !comment.trim().isEmpty()) {
                Document notification = new Document("UserID", post.getInteger("UserID"))
                        .append("Message", getUserName(getCurrentUserID()) + " commented: \"" + comment + "\"")
                        .append("CreatedAt", new Date())
                        .append("IsRead", false);
                notificationsCollection.insertOne(notification);
            }
        });

        followBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Follow feature not implemented yet.");
        });

        

        return postPanel;
    }

    private String getUserName(int userId) {
        Document user = usersCollection.find(new Document("UserID", userId)).first();
        return user != null ? user.getString("Name") : "Unknown";
    }

    private int getCurrentUserID() {
        Document currentUser = usersCollection.find(new Document("Email", currentUserEmail)).first();
        return currentUser != null ? currentUser.getInteger("UserID") : -1;
    }

    private void refreshFeed() {
        feedPanel.revalidate();
        feedPanel.repaint();
    }
}