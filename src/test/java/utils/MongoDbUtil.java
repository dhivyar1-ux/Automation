package utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;

public class MongoDbUtil {

    // Default connection string when MongoDB port 27017 is mapped from the Docker container to localhost
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "duratechlms";

    /**
     * Deletes a user (or user‑group) from MongoDB.
     *
     * @param userName the login / name used to locate the record
     * @param type     the collection name (e.g. "student", "app_admin", "user_group")
     */
    public static void deleteUserByName(String userName, String type) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {

            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);

            // -----------------------------------------------------------------
            // 1️⃣  Get the target collection (the one that actually stores the data)
            // -----------------------------------------------------------------
            MongoCollection<Document> targetCollection = database.getCollection(type);

            // -----------------------------------------------------------------
            // 2️⃣  Special handling for user groups (no jhi_user entry exists)
            // -----------------------------------------------------------------
            if ("user_group".equalsIgnoreCase(type)) {
                // Delete directly by the name field
                DeleteResult groupDeleteResult = targetCollection.deleteOne(
                        new Document("name", userName));
                System.out.println(
                        "Deleted " + groupDeleteResult.getDeletedCount() +
                        " record(s) from '" + type + "' collection (user‑group case).");
                // Nothing to delete from jhi_user, so we are done.
                return;
            }

            // -----------------------------------------------------------------
            // 3️⃣  For all other types: locate the jhi_user entry to obtain _id
            // -----------------------------------------------------------------
            MongoCollection<Document> jhiUserCollection = database.getCollection("jhi_user");

            Document jhiUser = jhiUserCollection.find(
                    new Document("login", userName)).first();

            if (jhiUser == null) {
                System.out.println(
                        "No jhi_user entry found for login: " + userName +
                        ". Proceeding to delete directly from '" + type + "' collection.");
                // Fall back to a direct delete by login (some collections also store it)
                DeleteResult fallbackResult = targetCollection.deleteOne(
                        new Document("login", userName));
                System.out.println(
                        "Deleted " + fallbackResult.getDeletedCount() +
                        " record(s) from '" + type + "' collection (fallback).");
                return;
            }

            // -----------------------------------------------------------------
            // 4️⃣  Extract the _id from jhi_user
            // -----------------------------------------------------------------
            Object userId = jhiUser.get("_id");
            System.out.println("Found user _id: " + userId);

            // -----------------------------------------------------------------
            // 5️⃣  Delete from the specific collection using the _id
            // -----------------------------------------------------------------
            DeleteResult specificDeleteResult = targetCollection.deleteOne(
                    new Document("_id", userId));

            System.out.println(
                    "Deleted " + specificDeleteResult.getDeletedCount() +
                    " record(s) from '" + type + "' collection.");

            // -----------------------------------------------------------------
            // 6️⃣  Finally delete the jhi_user document itself
            // -----------------------------------------------------------------
            DeleteResult jhiUserDeleteResult = jhiUserCollection.deleteOne(
                    new Document("_id", userId));

            System.out.println(
                    "Deleted " + jhiUserDeleteResult.getDeletedCount() +
                    " record(s) from 'jhi_user' collection.");
        } catch (Exception e) {
            System.err.println("Error deleting user from MongoDB: " + e.getMessage());
            e.printStackTrace();
        }
    }
}