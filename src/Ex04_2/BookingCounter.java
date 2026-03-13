package Ex04_2;

import Ex01_4.Ticket; // Nhập class Ticket từ bài cũ sang
import java.util.Random;

public class BookingCounter implements Runnable {
    String counterName;
    TicketPool roomA;
    TicketPool roomB;
    int soldCount = 0;
    Random random = new Random();

    public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
        this.counterName = counterName;
        this.roomA = roomA;
        this.roomB = roomB;
    }

    @Override
    public void run() {
        // Vòng lặp chỉ dừng khi CẢ 2 PHÒNG đều cạn sạch vé VÀ nhà cung cấp đã nghỉ
        while (!roomA.isCompletelyClosed() || !roomB.isCompletelyClosed()) {

            // Random chọn phòng A hoặc B
            TicketPool selectedRoom = random.nextBoolean() ? roomA : roomB;

            // Nếu phòng vừa chọn đã đóng cửa thì chuyển sang phòng còn lại
            if (selectedRoom.isCompletelyClosed()) {
                selectedRoom = (selectedRoom == roomA) ? roomB : roomA;
            }

            // Gọi hàm bán vé (Nếu hết vé, nó sẽ tự động rơi vào trạng thái wait())
            Ticket soldTicket = selectedRoom.sellTicket(counterName);

            if (soldTicket != null) {
                soldCount++;
                System.out.println("oke " + counterName + " bán thành công vé: " + soldTicket.ticketId);
            }

            // Nghỉ 50ms để nhường CPU cho quầy khác
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}