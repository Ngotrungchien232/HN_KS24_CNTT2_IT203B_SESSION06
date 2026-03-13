package Ex05_5;

public class AdvancedTicket {
    public String ticketId;
    public String roomName;
    public boolean isVIP;

    public boolean isSold = false;
    public boolean isHeld = false;
    public long holdExpiryTime = 0; // Thời điểm vé sẽ bị hủy giữ chỗ

    public AdvancedTicket(String ticketId, String roomName, boolean isVIP) {
        this.ticketId = ticketId;
        this.roomName = roomName;
        this.isVIP = isVIP;
    }
}