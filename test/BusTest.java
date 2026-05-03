import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BusTest {

    @Test
    public void testBusInitialization() {
        Bus bus = new Bus("B001", "KL", "Penang", "08:00 AM", 35.50, 40);
        assertEquals(40, bus.getAvailableSeats());[cite: 1]
        assertEquals("B001", bus.getBusId());[cite: 1]
    }

    @Test
    public void testToFileString() {
        Bus bus = new Bus("B001", "KL", "Penang", "08:00 AM", 35.50, 40, 35);
        String expected = "B001,KL,Penang,08:00 AM,35.5,40,35";
        assertEquals(expected, bus.toFileString());[cite: 1]
    }
}