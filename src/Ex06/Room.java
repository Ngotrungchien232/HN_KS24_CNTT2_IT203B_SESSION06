package Ex06;

public class Room {
    public String name;
    public int totalTickets;
    public int soldTickets = 0;
    public double ticketPrice = 50000;

    public Room(String name, int totalTickets) {
        this.name = name;
        this.totalTickets = totalTickets;
    }

    public synchronized boolean sellTicket() {
        if (soldTickets < totalTickets) {
            soldTickets++;
            return true;
        }
        return false;
    }

    public synchronized int getRemaining() { return totalTickets - soldTickets; }
    public synchronized double getRevenue() { return soldTickets * ticketPrice; }
}