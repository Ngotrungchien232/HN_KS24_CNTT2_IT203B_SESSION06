package Ex02_3;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    private String roomName;
    private List<Ticket> tickets;

    public TicketPool(String roomName, int quantity) {
        this.roomName = roomName;
        this.tickets = new ArrayList<>();
        addTickets(quantity); // Tận dụng luôn hàm addTickets để khởi tạo vé lúc đầu
    }


    public synchronized void addTickets(int count) {
        // Lấy số lượng vé hiện tại để đặt ID nối tiếp cho khỏi trùng
        int currentTotal = tickets.size();
        for (int i = 1; i <= count; i++) {
            String id = roomName + "-" + String.format("%03d", currentTotal + i);
            tickets.add(new Ticket(id, roomName));
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

    public synchronized boolean hasTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold) return true;
        }
        return false;
    }

    public synchronized int getRemainingTickets() {
        int count = 0;
        for (Ticket t : tickets) {
            if (!t.isSold) count++;
        }
        return count;
    }
}