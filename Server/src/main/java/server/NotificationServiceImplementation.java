package server;

import model.Ticket;
import notification.Notification;
import org.springframework.jms.core.JmsOperations;
import services.INotificationService;

public class NotificationServiceImplementation implements INotificationService {
    private JmsOperations jmsOperations;
    public NotificationServiceImplementation(JmsOperations operations) {
        jmsOperations = operations;
    }

    @Override
    public void ticketBought(Ticket ticket) {
        System.out.println("New ticket notification.");
        Notification notification = new Notification("Updated.");
        jmsOperations.convertAndSend(notification);
        System.out.println("Sending to ActiveMQ... " + notification);
    }
}
