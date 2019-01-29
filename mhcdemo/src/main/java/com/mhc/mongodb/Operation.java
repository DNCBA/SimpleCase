package com.mhc.mongodb;


import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Operation {

    private static final String host="localhost";
    private  static  final Integer port = 27017;
    private static MongoClient client = null;

    static {
        client = new MongoClient(host,port);
    }

    public static void main(String[] args) {


        springDataMongodb();

        // mongodbClient();



    }

    private static void springDataMongodb() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        MongoTemplate mongoTemplate = applicationContext.getBean(MongoTemplate.class);

        FindIterable<Document> documents = mongoTemplate.getCollection("test").find();

        for (Document document : documents){
            System.out.println(document);
        }
    }

    private static void mongodbClient() {
        //database
        MongoDatabase database = client.getDatabase("test");
        //client.getDatabase("test").drop();


        //collection
        MongoCollection<Document> collection = database.getCollection("test");
        database.createCollection("c-name");
        database.getCollection("c-name").drop();


        //document
        collection.insertOne(Document.parse("{\"age\":13,\"address\":\"hz\"}"));
        //collection.updateOne();
        FindIterable<Document> documents = collection.find();
        for (Document document : documents){
            System.out.println(document);
        }
        //collection.deleteOne(BsonDocument.parse(""));
    }
}
