package Ex06;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class DeadlockDetector {
    public static void checkDeadlock() {
        System.out.println("Đang quét deadlock trong hệ thống...");
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] deadlockedThreads = bean.findDeadlockedThreads();

        if (deadlockedThreads != null && deadlockedThreads.length > 0) {
            System.out.println("⚠ CẢNH BÁO: Phát hiện " + deadlockedThreads.length + " luồng đang bị DEADLOCK!");
        } else {
            System.out.println(" Không phát hiện deadlock. Hệ thống hoạt động bình thường.");
        }
    }
}