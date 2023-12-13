package com.mhc.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver extends AbstractActor {

    public static void start(String config, String name) {
        ActorSystem actorSystem = ActorSystem.create("ydpost", ConfigFactory.load(config));
        //创建两个 收件者
        ActorRef receiver = actorSystem.actorOf(Receiver.props(), name);
        LOGGER.info("Receiver start : {}", receiver);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    public static Props props() {
        return Props.create(Receiver.class, Receiver::new);
    }

    @Override
    public Receive createReceive() {
        LOGGER.info("Recevier createReceive");
        return receiveBuilder()
                .match(Message.class, this::onMessage )
                .build();
    }

    public void onMessage(Message message) {
        LOGGER.info("=======> {} receive message {} from {} ",   self().path().name(), message, sender().path().name());

        getContext().actorSelection("akka://ydpost@127.0.0.1:25521/user/poster").tell("receive", self());
//        sender().tell("receive", self());
    }

}
