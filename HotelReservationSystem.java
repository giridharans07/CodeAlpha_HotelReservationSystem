import java.io.*;
import java.util.*;

public class HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";
    static class Room {
        int roomNo;
        String category;
        int price;
        boolean isBooked;

        Room(int roomNo, String category, int price) {
            this.roomNo = roomNo;
            this.category = category;
            this.price = price;
            this.isBooked = false;
        }
    }

    static ArrayList<Room> rooms = new ArrayList<>();
    static void loadRooms() {
        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(301, "Suite", 4500));
    }

    static void searchRoom() {
        System.out.print("Enter Room Category: ");
        String cat = sc.nextLine();

        boolean available = false;
        for (Room r : rooms) {
            if (r.category.equalsIgnoreCase(cat) && !r.isBooked) {
                System.out.println("Room No: " + r.roomNo + " | Rs." + r.price);
                available = true;
            }
        }
        if (!available)
            System.out.println("No rooms available in this category.");
    }

    static void bookRoom() {
        System.out.print("Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Room Category: ");
        String cat = sc.nextLine();

        for (Room r : rooms) {
            if (r.category.equalsIgnoreCase(cat) && !r.isBooked) {
                r.isBooked = true;
                processPayment(r.price);
                saveBooking(name, r);
                System.out.println("Room Booked Successfully");
                System.out.println("Room Number: " + r.roomNo);
                return;
            }
        }
        System.out.println("Room not available.");
    }

    static void cancelBooking() {
        System.out.print("Enter Room Number to Cancel: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNo == roomNo && r.isBooked) {
                r.isBooked = false;
                System.out.println("Booking Cancelled Successfully");
                return;
            }
        }
        System.out.println("Invalid Room Number.");
    }

    static void processPayment(int amount) {
        System.out.println("Processing payment of Rs." + amount + "...");
        System.out.println("Payment Successful");
    }

    static void saveBooking(String name, Room r) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(name + "," + r.roomNo + "," + r.category + "," + r.price + "\n");
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }
    }

    static void viewBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            System.out.println("\n---- BOOKING DETAILS ----");
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println( "Customer: " + d[0] + " | Room No: " + d[1] + " | Category: " + d[2] +
                " | Amount: ₹" + d[3]);
            }
        } catch (IOException e) {
            System.out.println("No bookings found.");
        }
    }
    public static void main(String[] args) {
     loadRooms();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Search Room");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> searchRoom();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    System.out.println("Thank You");
                    System.exit(0);
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

}
