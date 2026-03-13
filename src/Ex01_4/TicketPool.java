package Ex01_4;
import java.util.ArrayList;
import java.util.List;


import java.util.List;

public class TicketPool {
   String roomName;
    private List<Ticket> tickets;
public  TicketPool(String roomName, int quantity){
    this.roomName = roomName;
     tickets = new ArrayList<>();
    for (int i = 1; i <= quantity; i++) {
        // Tạo vé với định dạng ví dụ: A-001, B-002
        String id = roomName + "-" + String.format("%03d", i);
        tickets.add(new Ticket(id, roomName));
    }
}

// sử dụng synchronized để đảm bảo tính thread-safe khi bán vé
public synchronized Ticket sellTicket() {
    for (Ticket t : tickets) {
        if (!t.isSold) {
            t.isSold = true; // Đánh dấu đã bán
            return t;
        }
    }
    return null; // Nếu duyệt hết mà không còn vé nào -> Hết vé
}

    // Hàm phụ trợ kiểm tra xem kho còn vé không
    public synchronized boolean hasTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold) return true;
        }
        return false;
    }

    // Hàm phụ trợ đếm vé còn lại
    public synchronized int getRemainingTickets() {
        int count = 0;
        for (Ticket t : tickets) {
            if (!t.isSold) count++;
        }
        return count;
    }
}
