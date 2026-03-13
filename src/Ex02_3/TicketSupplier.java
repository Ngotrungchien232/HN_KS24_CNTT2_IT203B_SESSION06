package Ex02_3;

public class TicketSupplier implements Runnable {
    TicketPool roomA;
    TicketPool roomB;
    int supplyCount;
    int interval; // delay mini giây
    int rounds;   // Số vòng lặp châm thêm vé

    // Cờ đánh dấu Nhà cung cấp đã làm xong việc chưa
    public boolean isDone = false;

    public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
        this.roomA = roomA;
        this.roomB = roomB;
        this.supplyCount = supplyCount;
        this.interval = interval;
        this.rounds = rounds;
    }

    @Override
    public void run() {
        for (int i = 0; i < rounds; i++) {
            try {
                Thread.sleep(interval);// delay 3 mini giây theo đề
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Thức dậy và tiến hành nạp vé
            roomA.addTickets(supplyCount);
            roomB.addTickets(supplyCount);
            System.out.println(">>> NHÀ CUNG CẤP: Đã thêm " + supplyCount + " vé mới vào mỗi phòng! <<<");
        }
        isDone = true;
    }
}