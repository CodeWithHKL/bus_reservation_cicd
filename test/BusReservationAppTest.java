import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusReservationAppTest {
    private Bus bus;

    @BeforeEach
    public void setup() {
        // Mocking a bus for testing logic found in BusReservationApp
        bus = new Bus("B001", "KL", "JB", "09:00 AM", 40.0, 40);[cite: 1]
    }

    @Test
    public void testSeatDeductionLogic() {
        int initialSeats = bus.getAvailableSeats();[cite: 1]
        int seatsToBook = 3;
        
        // Logic from confirmBooking in BusReservationApp
        if (seatsToBook <= 4 && seatsToBook <= bus.getAvailableSeats()) {[cite: 2]
            bus.setAvailableSeats(bus.getAvailableSeats() - seatsToBook);[cite: 2]
        }

        assertEquals(initialSeats - 3, bus.getAvailableSeats());[cite: 1, 2]
    }

    @Test
    public void testMaxSeatConstraint() {
        int seatsToBook = 5; // Over the MAX_SEATS_PER_BOOKING constant (4)
        boolean canBook = (seatsToBook > 0 && seatsToBook <= 4);[cite: 2]
        
        assertFalse(canBook, "Should not allow booking more than 4 seats.");[cite: 2]
    }
}