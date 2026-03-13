package Ex04_2;

public class Main {
    public static void main(String[] args) {
        // 1. Khởi tạo kho trống không (0 vé) để test tính năng Wait ngay từ đầu
        TicketPool poolA = new TicketPool("A");
        TicketPool poolB = new TicketPool("B");

        // 2. Nhà cung cấp: 3 giây (3000ms) sau mới mang 3 vé tới (Lặp 2 vòng)
        TicketSupplier supplier = new TicketSupplier(poolA, poolB, 3, 3000, 2);

        // 3. Khởi tạo 2 Quầy bán vé
        BookingCounter counter1 = new BookingCounter("Quầy 1", poolA, poolB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", poolA, poolB);

        // 4. Cho vào Thread
        Thread tSup = new Thread(supplier);
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        System.out.println("--- BẮT ĐẦU (KHO ĐANG TRỐNG, CÁC QUẦY SẼ PHẢI CHỜ) ---");

        // 5. Chạy đồng loạt
        tSup.start();
        t1.start();
        t2.start();

        // 6. Đợi các luồng chạy xong mới in tổng kết
        try {
            tSup.join();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--- HỆ THỐNG ĐÓNG CỬA ---");
        System.out.println("Quầy 1 bán được: " + counter1.soldCount + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.soldCount + " vé");
    }
}