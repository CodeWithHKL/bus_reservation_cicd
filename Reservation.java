public class Reservation {

    private String reservationId;
    private String busId;
    private String passengerName;
    private String passengerIc;
    private int seatsBooked;
    private double totalPrice;   // renamed from: tot
    private String status;       // renamed from: stat

    public Reservation(String reservationId, String busId, String passengerName, String passengerIc, int seatsBooked, double totalPrice, String status) {
        this.reservationId = reservationId;
        this.busId = busId;
        this.passengerName = passengerName;
        this.passengerIc = passengerIc;
        this.seatsBooked = seatsBooked;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getReservationId()  { return reservationId; }
    public String getBusId()          { return busId; }
    public String getPassengerName()  { return passengerName; }
    public String getPassengerIc()    { return passengerIc; }
    public int getSeatsBooked()       { return seatsBooked; }
    public double getTotalPrice()     { return totalPrice; }
    public String getStatus()         { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toFileString() {
        return reservationId + "," + busId + "," + passengerName + "," + passengerIc + "," + seatsBooked + "," + totalPrice + "," + status;
    }

    // single print method (removed duplicate showReservationInfo)
    public void printSummary() {
        System.out.println("------------------------------------------");
        System.out.println("Reservation ID : " + reservationId);
        System.out.println("Bus ID         : " + busId);
        System.out.println("Passenger      : " + passengerName);
        System.out.println("IC             : " + passengerIc);
        System.out.println("Seats Booked   : " + seatsBooked);
        System.out.println("Total Price    : RM" + totalPrice);
        System.out.println("Status         : " + status);
        System.out.println("------------------------------------------");
    }
}
