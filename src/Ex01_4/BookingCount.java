package Ex01_4;

import java.util.Random;


    public class BookingCount implements Runnable {
        String counterName;
        TicketPool roomA;
        TicketPool roomB;
        int soldCount = 0;
        Random random = new Random();

        public BookingCount(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
        }

        @Override
        public void run() {
            // Chạy lặp cho đến khi CẢ 2 PHÒNG đều hết vé
            while (roomA.hasTickets() || roomB.hasTickets()) {

                // Random chọn phòng để bán (như tung đồng xu lấy true/false)
                TicketPool selectedRoom = random.nextBoolean() ? roomA : roomB;

                // In thông báo đang thao tác
                System.out.println(counterName + " đang cố gắng bán vé phòng " + selectedRoom.roomName);

                // Tiến hành lấy vé từ kho
                Ticket soldTicket = selectedRoom.sellTicket();

                if (soldTicket != null) {
                    soldCount++;
                    System.out.println(counterName + " đã bán thành công vé: " + soldTicket.ticketId);
                }

                // delay 2 mili giây
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

