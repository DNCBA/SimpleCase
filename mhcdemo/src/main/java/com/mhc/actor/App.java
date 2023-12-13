package com.mhc.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.ConfigFactory;


public class App {

    public static void main(String[] args) {

//        localModel();


        remoteModel();


    }

    private static void remoteModel() {
        Poster.start();
        Receiver.start("receiver1", "receiver1");
        Receiver.start("receiver2", "receiver2");
        ActorSystem actorSystem = ActorSystem.create("ydpost", ConfigFactory.load("akka-app"));

        actorSystem.actorSelection("akka://ydpost@127.0.0.1:25521/user/poster").tell("start", ActorRef.noSender());
    }

    private static void localModel() {
        ActorSystem actorSystem = ActorSystem.create("ydpost");

        actorSystem.actorOf(Poster.props(), "poster");
        actorSystem.actorOf(Receiver.props(), "receiver1");
        actorSystem.actorOf(Receiver.props(), "receiver2");

        actorSystem.actorSelection("akka://ydpost/user/poster").tell("start", ActorRef.noSender());
    }
}
