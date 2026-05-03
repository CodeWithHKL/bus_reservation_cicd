import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    @Test
    public void testReservationDetails() {
        Reservation res = new Reservation("RES001", "B001", "John Doe", "12345", 2, 71.0, "CONFIRMED");
        assertEquals("CONFIRMED", res.getStatus());[cite: 5]
        assertEquals(2, res.getSeatsBooked());[cite: 5]
    }

    @Test
    public void testStatusChange() {
        Reservation res = new Reservation("RES001", "B001", "John Doe", "12345", 2, 71.0, "CONFIRMED");
        res.setStatus("CANCELLED");[cite: 5]
        assertEquals("CANCELLED", res.getStatus());[cite: 5]
    }
}