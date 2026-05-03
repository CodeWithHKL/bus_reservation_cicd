import java.io.*;
import java.util.*;

public class FileStorage {

    private static final String BUS_FILE = "buses.txt";
    private static final String RES_FILE = "reservations.txt";

    // extracted helper - eliminates duplicate read pattern in loadBuses/loadReservations
    private static List<String[]> readLines(String filename, int expectedColumns) {
        List<String[]> rows = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] col = line.split(",");
                if (col.length >= expectedColumns) {
                    rows.add(col);
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error reading file " + filename + ": " + e.getMessage());
        }
        return rows;
    }

    // extracted helper - eliminates duplicate save pattern in saveBuses/saveReservations
    private static <T> void writeLines(String filename, List<T> items) {
        try {
            FileWriter fw = new FileWriter(filename);
            for (T item : items) fw.write(item.toString() + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Error writing file " + filename + ": " + e.getMessage());
        }
    }

    public static List<Bus> loadBuses() {
        File f = new File(BUS_FILE);
        if (!f.exists()) {
            seedBuses();
        }
        List<Bus> list = new ArrayList<>();
        for (String[] col : readLines(BUS_FILE, 7)) {
            Bus b = new Bus(col[0], col[1], col[2], col[3],
                    Double.parseDouble(col[4]),
                    Integer.parseInt(col[5]),
                    Integer.parseInt(col[6]));
            list.add(b);
        }
        return list;
    }

    private static void seedBuses() {
        try {
            FileWriter fw = new FileWriter(BUS_FILE);
            fw.write("B001,Kuala Lumpur,Penang,08:00 AM,35.50,40,40\n");
            fw.write("B002,Kuala Lumpur,Johor Bahru,09:00 AM,42.00,40,40\n");
            fw.write("B003,Kuala Lumpur,Ipoh,11:00 AM,25.00,40,40\n");
            fw.write("B004,Penang,Kuala Lumpur,02:00 PM,35.50,40,40\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error seeding buses: " + e.getMessage());
        }
    }

    public static List<Reservation> loadReservations() {
        List<Reservation> list = new ArrayList<>();
        File f = new File(RES_FILE);
        if (!f.exists()) {
            return list;
        }
        for (String[] col : readLines(RES_FILE, 7)) {
            Reservation r = new Reservation(col[0], col[1], col[2], col[3],
                    Integer.parseInt(col[4]),
                    Double.parseDouble(col[5]),
                    col[6]);
            list.add(r);
        }
        return list;
    }

    public static void saveBuses(List<Bus> buses) {
        List<String> lines = new ArrayList<>();
        for (Bus b : buses) lines.add(b.toFileString());
        writeLines(BUS_FILE, lines);
    }

    public static void saveReservations(List<Reservation> reservations) {
        List<String> lines = new ArrayList<>();
        for (Reservation r : reservations) lines.add(r.toFileString());
        writeLines(RES_FILE, lines);
    }
}
