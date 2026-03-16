package Ex06;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CinemaManager {
    public List<Room> rooms = new ArrayList<>();
    private ExecutorService executor;

    private boolean isPaused = false;
    private boolean isRunning = false;
    public final Object pauseLock = new Object();

    public void startSimulation(int roomCount, int ticketsPerRoom, int counterCount) {
        if (isRunning) {
            System.out.println("Hệ thống đang chạy rồi!"); return;
        }

        for (int i = 0; i < roomCount; i++) {
            rooms.add(new Room("Phòng " + (char)('A' + i), ticketsPerRoom));
        }

        executor = Executors.newFixedThreadPool(counterCount);
        isRunning = true;
        isPaused = false;

        System.out.println("Đã khởi tạo hệ thống với " + roomCount + " phòng, " + (roomCount * ticketsPerRoom) + " vé, " + counterCount + " quầy.");

        for (int i = 1; i <= counterCount; i++) {
            executor.submit(new BookingCounter("Quầy " + i, this));
        }
    }

    public void pauseSimulation() {
        isPaused = true;
        System.out.println("Đã phát lệnh TẠM DỪNG tất cả quầy bán vé.");
    }

    public void resumeSimulation() {
        isPaused = false;
        synchronized (pauseLock) {
            pauseLock.notifyAll();
        }
        System.out.println("Đã TIẾP TỤC hoạt động.");
    }

    public void stopSimulation() {
        isRunning = false;
        if (executor != null) executor.shutdownNow();
        System.out.println("Đang dừng hệ thống...");
    }

    public void printStats() {
        System.out.println("\n=== THỐNG KÊ HIỆN TẠI ===");
        double totalRev = 0;
        for (Room r : rooms) {
            System.out.println(r.name + ": Đã bán " + r.soldTickets + "/" + r.totalTickets + " vé (Còn " + r.getRemaining() + ")");
            totalRev += r.getRevenue();
        }
        System.out.println("Tổng doanh thu: " + (long)totalRev + " VNĐ\n");
    }

    public boolean isPaused() { return isPaused; }
    public boolean isRunning() { return isRunning; }
}