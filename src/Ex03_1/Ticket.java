package Ex03_1;

public class Ticket {
    public String ticketId;
    public String roomName;
    public boolean isSold;

    public Ticket(String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }
}