package Ex03_1;

public class BookingCounter implements Runnable {
    String counterName;
    TicketPool roomA;
    TicketPool roomB;
    boolean isDeadlockMode; // Cờ chuyển đổi kịch bản

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB, boolean isDeadlockMode) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.isDeadlockMode = isDeadlockMode;
    }

    @Override
    public void run() {
        while (roomA.hasTickets() && roomB.hasTickets()) {
            if (isDeadlockMode) {
                sellComboDeadlock(); // Chạy kiểu gây lỗi
            } else {
                sellComboSafe();     // Chạy kiểu an toàn
            }

            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
    }

    // --- KỊCH BẢN 1: GÂY DEADLOCK ---
    private void sellComboDeadlock() {
        // Quầy 1: Khóa A trước, B sau.
        // Quầy 2: Khóa B trước, A sau (Cố tình tạo ngược để gây lỗi)
        TicketPool firstLock = counterName.equals("Quầy 1") ? roomA : roomB;
        TicketPool secondLock = counterName.equals("Quầy 1") ? roomB : roomA;

        synchronized (firstLock) {
            System.out.println(counterName + ": Đã khóa phòng " + firstLock.roomName + " - Đang chờ phòng " + secondLock.roomName + "...");

            try { Thread.sleep(100); } catch (InterruptedException e) {} // Cố tình delay để tạo cơ hội khóa chéo

            synchronized (secondLock) {
                processCombo(); // Có đủ 2 khóa thì tiến hành bán
            }
        }
    }

    // --- KỊCH BẢN 2: AN TOÀN TRÁNH DEADLOCK ---
    private void sellComboSafe() {
        // Cả 2 quầy LUÔN LUÔN khóa A trước, B sau (Lock Ordering)
        synchronized (roomA) {
            // Lấy được A rồi mới xin B
            synchronized (roomB) {
                processCombo();
            }
        }
    }

    // --- LOGIC BÁN VÉ COMBO (Cần có mặt cả 2 vé) ---
    private void processCombo() {
        Ticket ticketA = roomA.sellTicket();
        Ticket ticketB = roomB.sellTicket();

        if (ticketA != null && ticketB != null) {
            System.out.println("oke  " + counterName + " bán COMBO thành công: " + ticketA.ticketId + " & " + ticketB.ticketId);
        } else {
            System.out.println("no  " + counterName + " báo lỗi: Không đủ vé Combo!");
            // Nếu có vé A mà hết vé B thì phải trả lại vé A
            if (ticketA != null) roomA.refundTicket(ticketA);
            // Ngược lại, nếu có B mà thiếu A thì trả B
            if (ticketB != null) roomB.refundTicket(ticketB);
        }
    }
}