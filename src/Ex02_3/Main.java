package Ex02_3;

import Ex02_3.BookingCounter;
import Ex02_3.TicketSupplier;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- BẮT ĐẦU MỞ BÁN VÉ ---");

        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);

        // Khởi tạo Nhà cung cấp: Mỗi phòng 3 vé, mỗi 3000ms (3s), lặp 2 vòng
        TicketSupplier supplier = new TicketSupplier(poolA, poolB, 3, 3000, 2);

        // Khởi tạo 2 Quầy bán (Truyền supplier vào để Quầy biết mà chờ đợi)
        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA, poolB, supplier);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolA, poolB, supplier);

        Thread tSupplier = new Thread(supplier);
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        // Bắt đầu chạy song song 3 luồng
        tSupplier.start();
        t1.start();
        t2.start();


        try {
            tSupplier.join();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- KẾT THÚC CHƯƠNG TRÌNH ---");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.soldCount + " vé");
        System.out.println("Vé còn lại phòng A: " + poolA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + poolB.getRemainingTickets());
    }
}