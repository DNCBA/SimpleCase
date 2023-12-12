package com.mhc.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver extends AbstractActor {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("ydpost");
        //创建两个 收件者
        ActorRef receiver1 = actorSystem.actorOf(Receiver.props(), "receiver1");
        ActorRef receiver2 = actorSystem.actorOf(Receiver.props(), "receiver2");


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

        getContext().actorSelection("akka://ydpost/user/poster").tell("receive", self());
//        sender().tell("receive", self());
    }

}
