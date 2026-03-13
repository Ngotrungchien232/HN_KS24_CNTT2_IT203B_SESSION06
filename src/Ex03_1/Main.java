package Ex03_1;

public class Main {
    public static void main(String[] args) {
        TicketPool poolA = new TicketPool("A", 10);
        TicketPool poolB = new TicketPool("B", 10);

        //  2 TRƯỜNG HỢP:
        // true = Bật kịch bản Deadlock (Chương trình treo cứng)
        // false = Bật kịch bản An toàn (Bán combo mượt mà)
        boolean testDeadlock = true;

        BookingCounter c1 = new BookingCounter("Quầy 1", poolA, poolB, testDeadlock);
        BookingCounter c2 = new BookingCounter("Quầy 2", poolA, poolB, testDeadlock);

        System.out.println("--- BẮT ĐẦU CHẠY (Chế độ Deadlock: " + testDeadlock + ") ---");

        Thread t1 = new Thread(c1);
        Thread t2 = new Thread(c2);

        t1.start();
        t2.start();
    }
}