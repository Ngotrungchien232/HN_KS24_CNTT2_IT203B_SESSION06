package Ex06;

import java.util.Random;

public class BookingCounter implements Runnable {
    private String name;
    private CinemaManager manager;
    private Random random = new Random();

    public BookingCounter(String name, CinemaManager manager) {
        this.name = name;
        this.manager = manager;
    }

    @Override
    public void run() {
        System.out.println("   " + name + " bắt đầu bán vé...");

        while (manager.isRunning()) {
            synchronized (manager.pauseLock) {
                while (manager.isPaused()) {
                    try {
                        manager.pauseLock.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            Room targetRoom = null;
            for (Room r : manager.rooms) {
                if (r.getRemaining() > 0) {
                    targetRoom = r; break;
                }
            }

            if (targetRoom == null) {
                System.out.println("   " + name + " thông báo: ĐÃ HẾT SẠCH VÉ TOÀN RẠP!");
                break;
            }

            targetRoom.sellTicket();

            try { Thread.sleep(100 + random.nextInt(200)); } catch (InterruptedException e) { return; }
        }
    }
}