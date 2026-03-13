package Ex02_3;

public class Ticket {
    // tạo một lớp ticket với các thuộc tính
    String ticketId;
    private String roomName;
    boolean isSold;

    // tạo một constructor để khởi tạo các thuộc tính
    public Ticket (String ticketId, String roomName) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isSold = false;
    }
}
