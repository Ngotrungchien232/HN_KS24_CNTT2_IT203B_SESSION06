package Ex05_5;

import Ex05_5.TimeoutManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- BẮT ĐẦU HỆ THỐNG ĐẶT VÉ (Giữ chỗ 5s) ---");

        // Tạo 1 kho duy nhất: 3 vé thường, 2 vé VIP để test nhanh
        TicketPool poolA = new TicketPool("A", 3, 2);

        TimeoutManager manager = new TimeoutManager(poolA);
        BookingCounter c1 = new BookingCounter("Quầy 1", poolA);
        BookingCounter c2 = new BookingCounter("Quầy 2", poolA);

        Thread tManager = new Thread(manager);
        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        tManager.start();
        t1.start();
        t2.start();
    }
}