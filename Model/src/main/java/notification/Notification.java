package notification;

import model.Ticket;

public class Notification {
    String msg;

    public Notification() {}

    public Notification(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
