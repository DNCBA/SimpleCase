package com.mhc.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;


public class App {

    public static void main(String[] args) {
        localModel();



    }

    private static void localModel() {
        ActorSystem actorSystem = ActorSystem.create("ydpost");

        actorSystem.actorOf(Poster.props(), "poster");
        actorSystem.actorOf(Receiver.props(), "receiver1");
        actorSystem.actorOf(Receiver.props(), "receiver2");

        actorSystem.actorSelection("akka://ydpost/user/poster").tell("start", ActorRef.noSender());
    }
}
