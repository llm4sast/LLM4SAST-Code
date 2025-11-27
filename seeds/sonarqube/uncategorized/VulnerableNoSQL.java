import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class VulnerableNoSQL {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws UnknownHostException {
    String input = req.getParameter("input");

    MongoClient mongoClient = new MongoClient();
    DB database = mongoClient.getDB("ExampleDatabase");
    DBCollection collection = database.getCollection("exampleCollection");
    BasicDBObject query = new BasicDBObject();

    query.append("$where", "this.field == \"" + input + "\"");

    collection.find(query);
  }
}
