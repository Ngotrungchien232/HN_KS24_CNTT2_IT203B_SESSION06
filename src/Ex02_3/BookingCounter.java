package Ex02_3;

import Ex02_3.TicketSupplier;

import java.util.Random;

public class BookingCounter implements Runnable {
    String counterName;
    TicketPool roomA;
    TicketPool roomB;
    TicketSupplier supplier; // Theo dõi Nhà cung cấp
    int soldCount = 0;
    Random random = new Random();

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB, TicketSupplier supplier) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
        this.supplier = supplier;
    }

    @Override
    public void run() {
        // Điều kiện lặp: Còn vé HOẶC Nhà cung cấp vẫn đang làm việc chưa xong
        while (roomA.hasTickets() || roomB.hasTickets() || !supplier.isDone) {

            TicketPool selectedRoom = random.nextBoolean() ? roomA : roomB;
            Ticket soldTicket = selectedRoom.sellTicket();

            if (soldTicket != null) {
                soldCount++;
                System.out.println(counterName + " đã bán thành công vé: " + soldTicket.ticketId);
            }

            try {
                // Cho Quầy bán nghỉ 100ms để 2 quầy đan xen và ta dễ nhìn log
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}