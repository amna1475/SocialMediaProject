
package db;
import com.mongodb.client.*;

public class MongoUtil {
    public static MongoClient getClient() {
        String uri = System.getenv("MONGODB_URI");
        return MongoClients.create(uri);
    }
}

