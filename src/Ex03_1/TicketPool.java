package Ex03_1;
import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    public String roomName;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int quantity) {
        this.roomName = roomName;
        for (int i = 1; i <= quantity; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", i), roomName));
        }
    }

    public synchronized Ticket sellTicket() {
        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }
        return null;
    }

    // YÊU CẦU MỚI: Hàm hoàn trả vé nếu combo thất bại
    public synchronized void refundTicket(Ticket t) {
        if (t != null) {
            t.isSold = false; // Trả lại trạng thái chưa bán
            System.out.println("   -> Hoàn trả lại vé: " + t.ticketId + " vào kho.");
        }
    }

    public synchronized boolean hasTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold) return true;
        }
        return false;
    }
}