package Ex06;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CinemaManager manager = new CinemaManager();

        while (true) {
            System.out.println("\n--- HỆ THỐNG RẠP CHIẾU PHIM ---");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("4. Xem thống kê");
            System.out.println("5. Phát hiện deadlock");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();

            if (choice == 1) {
                if (manager.isRunning()) {
                    System.out.println("Hệ thống đã chạy rồi!");
                    continue;
                }
                System.out.print("Nhập số phòng: "); int rooms = scanner.nextInt();
                System.out.print("Số vé mỗi phòng: "); int tickets = scanner.nextInt();
                System.out.print("Số quầy bán vé: "); int counters = scanner.nextInt();
                manager.startSimulation(rooms, tickets, counters);
            }
            else if (choice == 2) {
                manager.pauseSimulation();
            }
            else if (choice == 3) {
                manager.resumeSimulation();
            }
            else if (choice == 4) {
                manager.printStats();
            }
            else if (choice == 5) {
                DeadlockDetector.checkDeadlock();
            }
            else if (choice == 6) {
                manager.stopSimulation();
                System.out.println("Kết thúc chương trình.");
                break;
            }
        }
        scanner.close();
    }
}