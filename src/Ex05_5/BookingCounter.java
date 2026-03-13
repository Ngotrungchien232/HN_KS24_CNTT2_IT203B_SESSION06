package Ex05_5;
import Ex05_5.AdvancedTicket;

import java.util.Random;

public class BookingCounter implements Runnable {
    String counterName;
    TicketPool pool;
    Random random = new Random();

    public BookingCounter(String counterName, TicketPool pool) {
        this.counterName = counterName;
        this.pool = pool;
    }

    @Override
    public void run() {
        while (!pool.isAllSold()) {
            boolean wantVIP = random.nextBoolean();
            AdvancedTicket heldTicket = pool.holdTicket(wantVIP);

            if (heldTicket != null) {
                System.out.println("time " + counterName + ": Đã giữ vé " + heldTicket.ticketId + ". Đang chờ khách thanh toán...");

                // MÔ PHỎNG THỜI GIAN KHÁCH THANH TOÁN (Từ 2s đến 7s)
                // Nếu random > 5s, chắc chắn TimeoutManager sẽ vào thu hồi vé trước!
                int paymentTime = 2000 + random.nextInt(5000);

                try {
                    Thread.sleep(paymentTime);
                } catch (InterruptedException e) {}

                // Khách móc ví xong, tiến hành chốt đơn
                boolean isSuccess = pool.sellHeldTicket(heldTicket);

                if (isSuccess) {
                    System.out.println("oke " + counterName + ": Thanh toán THÀNH CÔNG vé " + heldTicket.ticketId + " (mất " + (paymentTime/1000) + "s)");
                } else {
                    System.out.println("not " + counterName + ": Thanh toán THẤT BẠI vé " + heldTicket.ticketId + " (Do khách lấy tiền quá chậm: " + (paymentTime/1000) + "s)");
                }
            }

            // Nghỉ 1 chút trước khi đón khách tiếp theo
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }
    }
}