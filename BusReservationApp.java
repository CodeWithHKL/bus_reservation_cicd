import java.util.*;

public class BusReservationApp {

    // magic strings replaced with named constants
    private static final String STATUS_CONFIRMED = "CONFIRMED";
    private static final String STATUS_CANCELLED = "CANCELLED";
    private static final int MAX_SEATS_PER_BOOKING = 4;

    private List<Bus> busList;
    private List<Reservation> resList;
    private Scanner sc;
    private int resCount;

    public BusReservationApp() {
        busList = FileStorage.loadBuses();
        resList = FileStorage.loadReservations();
        sc = new Scanner(System.in);
        resCount = resList.size() + 1;
    }

    public void run() {
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt();

            // switch replaces long if-else chain
            switch (choice) {
                case 1: viewAllBuses();       break;
                case 2: makeReservation();    break;
                case 3: viewMyReservations(); break;
                case 4: cancelReservation();  break;
                case 5: viewAllReservations();break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Bus Reservation System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // extracted method - removes duplicate menu print logic
    private void printMenu() {
        System.out.println("\n==========================================");
        System.out.println("        BUS RESERVATION SYSTEM");
        System.out.println("==========================================");
        System.out.println("1. View All Buses");
        System.out.println("2. Make a Reservation");
        System.out.println("3. View My Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. View All Reservations");
        System.out.println("6. Exit");
        System.out.println("==========================================");
        System.out.print("Enter choice: ");
    }

    // extracted helper - eliminates duplicate bus print block in viewAllBuses + makeReservation
    private void printBusList() {
        if (busList.isEmpty()) {
            System.out.println("No buses available.");
            return;
        }
        for (Bus b : busList) {
            System.out.println("------------------------------------------");
            System.out.println("Bus ID     : " + b.getBusId());
            System.out.println("From       : " + b.getOrigin());
            System.out.println("To         : " + b.getDestination());
            System.out.println("Time       : " + b.getDepartureTime());
            System.out.println("Price      : RM" + b.getPrice());
            System.out.println("Seats Left : " + b.getAvailableSeats() + "/" + b.getTotalSeats());
            System.out.println("------------------------------------------");
        }
    }

    // extracted helper - eliminates duplicate linear search in makeReservation + cancelReservation
    private Bus findBusById(String busId) {
        for (Bus b : busList) {
            if (b.getBusId().equalsIgnoreCase(busId)) {
                return b;
            }
        }
        return null;
    }

    // extracted helper - eliminates duplicate linear search in cancelReservation
    private Reservation findReservationById(String reservationId) {
        for (Reservation r : resList) {
            if (r.getReservationId().equals(reservationId)) {
                return r;
            }
        }
        return null;
    }

    // extracted helper - removes duplicate int parsing try/catch blocks
    private int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private void viewAllBuses() {
        System.out.println("\n========== AVAILABLE BUSES ==========");
        printBusList();
    }

    // makeReservation broken into sub-methods: selectBus, collectPassengerInfo, confirmBooking
    private void makeReservation() {
        System.out.println("\n========== MAKE RESERVATION ==========");
        printBusList();

        Bus selectedBus = selectBus();
        if (selectedBus == null) return;

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter your IC number: ");
        String ic = sc.nextLine().trim();

        int seats = collectSeatCount(selectedBus);
        if (seats == -1) return;

        confirmBooking(selectedBus, name, ic, seats);
    }

    // extracted from makeReservation - handles bus selection and seat availability check
    private Bus selectBus() {
        System.out.print("Enter Bus ID: ");
        String busId = sc.nextLine().trim();

        Bus selectedBus = findBusById(busId);
        if (selectedBus == null) {
            System.out.println("Bus not found.");
            return null;
        }
        if (selectedBus.getAvailableSeats() == 0) {
            System.out.println("No available seats on this bus.");
            return null;
        }
        return selectedBus;
    }

    // extracted from makeReservation - handles seat count input and validation
    private int collectSeatCount(Bus bus) {
        System.out.print("Enter number of seats (max " + MAX_SEATS_PER_BOOKING + "): ");
        int seats = readInt();

        if (seats <= 0 || seats > MAX_SEATS_PER_BOOKING) {
            System.out.println("Invalid seat count. Must be between 1 and " + MAX_SEATS_PER_BOOKING + ".");
            return -1;
        }
        if (seats > bus.getAvailableSeats()) {
            System.out.println("Not enough available seats.");
            return -1;
        }
        return seats;
    }

    // extracted from makeReservation - handles reservation creation and confirmation print
    private void confirmBooking(Bus bus, String name, String ic, int seats) {
        double totalPrice = bus.getPrice() * seats;
        String resId = "RES" + String.format("%03d", resCount++);

        Reservation res = new Reservation(resId, bus.getBusId(), name, ic, seats, totalPrice, STATUS_CONFIRMED);
        resList.add(res);
        bus.setAvailableSeats(bus.getAvailableSeats() - seats);

        FileStorage.saveReservations(resList);
        FileStorage.saveBuses(busList);

        System.out.println("\n========== BOOKING CONFIRMED ==========");
        System.out.println("Reservation ID : " + resId);
        System.out.println("Bus            : " + bus.getBusId());
        System.out.println("Passenger      : " + name);
        System.out.println("Seats Booked   : " + seats);
        System.out.println("Total Price    : RM" + totalPrice);
        System.out.println("Status         : " + STATUS_CONFIRMED);
        System.out.println("========================================");
    }

    private void viewMyReservations() {
        System.out.print("Enter your IC number: ");
        String ic = sc.nextLine().trim();

        System.out.println("\n========== MY RESERVATIONS ==========");
        boolean found = false;
        for (Reservation r : resList) {
            if (r.getPassengerIc().equals(ic)) {
                r.printSummary();  // uses single unified print method from Reservation
                found = true;
            }
        }
        if (!found) {
            System.out.println("No reservations found for IC: " + ic);
        }
    }

    private void cancelReservation() {
        System.out.print("Enter Reservation ID to cancel: ");
        String resId = sc.nextLine().trim();

        Reservation target = findReservationById(resId);  // uses extracted helper
        if (target == null) {
            System.out.println("Reservation not found.");
            return;
        }
        if (target.getStatus().equals(STATUS_CANCELLED)) {  // uses constant
            System.out.println("This reservation is already cancelled.");
            return;
        }

        target.setStatus(STATUS_CANCELLED);  // uses constant

        Bus bus = findBusById(target.getBusId());  // uses extracted helper
        if (bus != null) {
            bus.setAvailableSeats(bus.getAvailableSeats() + target.getSeatsBooked());
        }

        FileStorage.saveReservations(resList);
        FileStorage.saveBuses(busList);
        System.out.println("Reservation " + resId + " has been successfully cancelled.");
    }

    private void viewAllReservations() {
        System.out.println("\n========== ALL RESERVATIONS ==========");
        if (resList.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (Reservation r : resList) {
            r.printSummary();  // uses Reservation.printSummary() — no more duplicate print block
        }
    }
}
