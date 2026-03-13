package Ex04_2;

public class TicketSupplier implements Runnable {
    TicketPool roomA;
    TicketPool roomB;
    int supplyCount;
    int interval;
    int rounds;

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
                Thread.sleep(interval); // Ngủ 3 giây theo đề bài
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("\n>>> NHÀ CUNG CẤP: Đã thêm " + supplyCount + " vé vào mỗi phòng! <<<");
            roomA.addTickets(supplyCount);
            roomB.addTickets(supplyCount);
        }

        // Xong việc, treo biển báo nghỉ
        roomA.setSupplierDone();
        roomB.setSupplierDone();
        System.out.println(">>> NHÀ CUNG CẤP: Đã hết ca làm việc, chốt sổ! <<<");
    }
}