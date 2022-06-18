package ams;

import notification.Notification;
import org.springframework.jms.core.JmsOperations;
import services.INotificationReceiver;
import services.INotificationSubscriber;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class NotificationReceiverImplementation implements INotificationReceiver {
    private JmsOperations jmsOperations;
    private boolean running;
    ExecutorService service;
    INotificationSubscriber subscriber;

    public NotificationReceiverImplementation(JmsOperations operations) {
        jmsOperations=operations;
    }

    private void run(){
        while(running){
            Notification notification = (Notification)jmsOperations.receiveAndConvert();
            System.out.println("Received Notification... " + notification);
            subscriber.notificationReceived(notification);
        }
    }

    @Override
    public void start(INotificationSubscriber subscriber) {
        System.out.println("Starting notification receiver ...");
        running = true;
        this.subscriber = subscriber;
        service = Executors.newSingleThreadExecutor();
        service.submit(this::run);
    }

    @Override
    public void stop() {
        running = false;
        try {
            service.awaitTermination(100, TimeUnit.MILLISECONDS);
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Stopped notification receiver");
    }
}
