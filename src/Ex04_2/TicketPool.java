package Ex04_2;

import Ex01_4.Ticket; // improt từ bài cũ
import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    public String roomName;
    private List<Ticket> tickets = new ArrayList<>();
    private boolean isSupplierDone = false; // Cờ báo hiệu nhà cung cấp đã chốt ca

    public TicketPool(String roomName) {
        this.roomName = roomName;
    }

    // --- HÀM THÊM VÉ (Sản xuất) ---
    public synchronized void addTickets(int count) {
        int currentTotal = tickets.size();
        for (int i = 1; i <= count; i++) {
            tickets.add(new Ticket(roomName + "-" + String.format("%03d", currentTotal + i), roomName));
        }
        // Đánh thức TẤT CẢ các quầy đang ngủ chờ vé
        notifyAll();
    }

    // --- HÀM BÁN VÉ (Tiêu thụ) ---
    public synchronized Ticket sellTicket(String counterName) {
        // Nếu hết vé VÀ nhà cung cấp chưa nghỉ -> Đi ngủ chờ
        while (!hasTickets() && !isSupplierDone) {
            System.out.println("   ⏳ " + counterName + ": Hết vé phòng " + roomName + ", đang ngủ chờ...");
            try {
                wait(); // Quầy vé chìm vào giấc ngủ, nhả khóa (lock) ra
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Tỉnh dậy: Kiểm tra xem có vé để bán không
        for (Ticket t : tickets) {
            if (!t.isSold) {
                t.isSold = true;
                return t;
            }
        }
        return null; // Trả về null nếu tỉnh dậy mà vẫn không có vé (nhà cung cấp đã nghỉ)
    }

    // --- HÀM CHỐT CA NHÀ CUNG CẤP ---
    public synchronized void setSupplierDone() {
        this.isSupplierDone = true;
        notifyAll(); // Đánh thức các quầy đang ngủ lần cuối để tụi nó biết đường đi về
    }

    // Hàm phụ trợ kiểm tra còn vé chưa bán không
    private boolean hasTickets() {
        for (Ticket t : tickets) {
            if (!t.isSold) return true;
        }
        return false;
    }

    // Kiểm tra xem phòng này đã đóng cửa hoàn toàn chưa (hết vé + nhà cung cấp đã nghỉ)
    public synchronized boolean isCompletelyClosed() {
        return !hasTickets() && isSupplierDone;
    }
}