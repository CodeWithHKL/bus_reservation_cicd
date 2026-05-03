public class Bus {

    private String busId;
    private String origin;
    private String destination;
    private String departureTime;
    private double price;
    private int totalSeats;
    private int availableSeats;

    public Bus(String busId, String origin, String destination, String departureTime, double price, int totalSeats) {
        this.busId = busId;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.price = price;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public Bus(String busId, String origin, String destination, String departureTime, double price, int totalSeats, int availableSeats) {
        this(busId, origin, destination, departureTime, price, totalSeats);
        this.availableSeats = availableSeats;
    }

    public String getBusId()           { return busId; }
    public String getOrigin()          { return origin; }
    public String getDestination()     { return destination; }
    public String getDepartureTime()   { return departureTime; }
    public double getPrice()           { return price; }
    public int getTotalSeats()         { return totalSeats; }
    public int getAvailableSeats()     { return availableSeats; }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String toFileString() {
        return busId + "," + origin + "," + destination + "," + departureTime + "," + price + "," + totalSeats + "," + availableSeats;
    }
}
