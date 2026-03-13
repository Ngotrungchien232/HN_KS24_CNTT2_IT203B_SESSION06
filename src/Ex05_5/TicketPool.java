package Ex05_5;
import Ex05_5.AdvancedTicket;

import java.util.ArrayList;
import java.util.List;

public class TicketPool {
    public String roomName;
    private List<AdvancedTicket> tickets = new ArrayList<>();

    public TicketPool(String roomName, int normalCount, int vipCount) {
        this.roomName = roomName;
        // Tạo vé VIP
        for (int i = 1; i <= vipCount; i++) {
            tickets.add(new AdvancedTicket(roomName + "-VIP" + i, roomName, true));
        }
        // Tạo vé thường
        for (int i = 1; i <= normalCount; i++) {
            tickets.add(new AdvancedTicket(roomName + "-N" + i, roomName, false));
        }
    }

    // 1. Hàm Giữ chỗ (Tạo Booking)
    public synchronized AdvancedTicket holdTicket(boolean wantVIP) {
        for (AdvancedTicket t : tickets) {
            // Tìm vé đúng loại, chưa bán và chưa bị ai giữ
            if (t.isVIP == wantVIP && !t.isSold && !t.isHeld) {
                t.isHeld = true;
                // Hẹn giờ chết: Thời gian hiện tại + 5000 milli-giây (5 giây)
                t.holdExpiryTime = System.currentTimeMillis() + 5000;
                return t;
            }
        }
        return null; // Không tìm thấy vé trống
    }

    // 2. Hàm Thanh toán (Chốt đơn)
    public synchronized boolean sellHeldTicket(AdvancedTicket t) {
        // Cực kỳ quan trọng: Phải kiểm tra xem vé còn đang được giữ cho mình không
        // (Lỡ như khách thanh toán quá 5s, TimeoutManager đã thu hồi vé rồi)
        if (t.isHeld && !t.isSold) {
            t.isSold = true;
            t.isHeld = false; // Bán xong thì không gọi là giữ nữa
            return true;
        }
        return false; // Thanh toán thất bại (đã quá hạn)
    }

    // 3. Hàm Dọn dẹp vé quá hạn (Dành cho TimeoutManager)
    public synchronized void releaseExpiredTickets() {
        long currentTime = System.currentTimeMillis();
        for (AdvancedTicket t : tickets) {
            // Nếu vé đang giữ MÀ thời gian hiện tại đã vượt qua vạch kẻ hết hạn
            if (t.isHeld && currentTime > t.holdExpiryTime) {
                t.isHeld = false; // Trả lại vé vào kho
                t.holdExpiryTime = 0;
                System.out.println("   [HỆ THỐNG]: Vé " + t.ticketId + " đã quá hạn thanh toán 5s. Đã thu hồi lại vào kho!");
            }
        }
    }

    public synchronized boolean isAllSold() {
        for (AdvancedTicket t : tickets) {
            if (!t.isSold) return false;
        }
        return true;
    }
}