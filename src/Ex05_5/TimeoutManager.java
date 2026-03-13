package Ex05_5;

public class TimeoutManager implements Runnable {
    TicketPool roomA;

    public TimeoutManager(TicketPool roomA) {
        this.roomA = roomA;
    }

    @Override
    public void run() {
        // Luồng này chạy liên tục cho đến khi kho hết sạch vé
        while (!roomA.isAllSold()) {
            try {
                Thread.sleep(1000); // Cứ 1 giây đi tuần tra 1 lần
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            roomA.releaseExpiredTickets();
        }
        System.out.println("   [HỆ THỐNG]: Đã bán hết vé. TimeoutManager ngừng hoạt động.");
    }
}