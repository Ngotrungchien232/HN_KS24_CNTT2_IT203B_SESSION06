package Ex01_4;
import java.util.Scanner;


import Ex01_4.BookingCount;
import Ex01_4.TicketPool;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- BẮT ĐẦU MỞ BÁN VÉ ---");

        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);

        BookingCount counter1 = new BookingCount("Quầy 1", poolA, poolB);
        BookingCount counter2 = new BookingCount("Quầy 2", poolA, poolB);

        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        t1.start();
        t2.start();

        try {
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