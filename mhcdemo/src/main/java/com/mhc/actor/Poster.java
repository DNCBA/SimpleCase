package com.mhc.actor;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Poster extends AbstractActor {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("ydpost", ConfigFactory.load("akka-app"));

        //创建一个 快递员
        ActorRef poster = actorSystem.actorOf(Poster.props(), "poster");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Poster.class);

    public static Props props() {
        return Props.create(Poster.class, Poster::new);
    }

    @Override
    public Receive createReceive() {
        LOGGER.info("Poster createReceive {}", self().path().toString());
        return receiveBuilder()
                .matchEquals("start", this::onStart)
                .matchEquals("receive", message -> LOGGER.info("{} receive", sender().path().name()))
                .build();
    }

    public void onStart(String start) {
        LOGGER.info("start");
        //快递员点对点投递
        ActorSelection receiver1 = getContext().actorSelection("akka://ydpost/user/receiver1");
        ActorSelection receiver2 = getContext().actorSelection("akka://ydpost/user/receiver2");
        receiver1.tell(Message.builder().data("hello receiver1").timeStamp(System.currentTimeMillis()).build(), self());
        receiver2.tell(Message.builder().data("hello receiver2").timeStamp(System.currentTimeMillis()).build(), self());
    }
}
