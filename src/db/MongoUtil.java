
package db;
import com.mongodb.client.*;

public class MongoUtil {
    public static MongoClient getClient() {
        return MongoClients.create("mongodb+srv://AmnaBibi:Database-1407@advancedb.our7w.mongodb.net/?authMechanism=SCRAM-SHA-1&tls=true");
    }
}
