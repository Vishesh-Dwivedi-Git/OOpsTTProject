import java.util.*;
import java.io.*;
import java.io.Serializable;

public class Main {
    private static final String DATA_FOLDER = "timetable_data";
    private static final String DATA_FILE = DATA_FOLDER + File.separator + "timetable.dat";

    public static void main(String[] args) {
        TimeTable timeTable = loadTimeTable();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n╔════════════════════════════════╗");
            System.out.println("║   Timetable Management System  ║");
            System.out.println("╠════════════════════════════════╣");
            System.out.println("║ 1. Add Class                   ║");
            System.out.println("║ 2. Add Lab                     ║");
            System.out.println("║ 3. View Timetable              ║");
            System.out.println("║ 4. Find Free Slots             ║");
            System.out.println("║ 5. Exit                        ║");
            System.out.println("╚════════════════════════════════╝");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch (choice) {
                case 1:
                    addClass(scanner, timeTable);
                    break;
                case 2:
                    addLab(scanner, timeTable);
                    break;
                case 3:
                    viewTimetable(scanner, timeTable);
                    break;
                case 4:
                    findFreeSlots(scanner, timeTable);
                    break;
                case 5:
                    saveTimeTable(timeTable);
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private static void addClass(Scanner scanner, TimeTable timeTable) {
        System.out.print("Enter batch section (A/B): ");
        String batchSection = scanner.nextLine().toUpperCase();
        String classroom = batchSection.equals("A") ? "C102" : "C101";
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter course title: ");
        String courseTitle = scanner.nextLine();
        System.out.print("Enter faculty name: ");
        String faculty = scanner.nextLine();
        System.out.print("Enter credits (L-T-P-S-C): ");
        String credits = scanner.nextLine();
        System.out.print("Enter day (MON/TUE/WED/THU/FRI): ");
        String day = scanner.nextLine().toUpperCase();
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();

        Subject subject = new Subject(courseCode, courseTitle, faculty, credits, "", "");
        TimeSlot timeSlot = new TimeSlot(day, startTime, endTime);
        Class newClass = new Class(batchSection, classroom, subject, timeSlot, false);
        
        if (timeTable.addClass(newClass)) {
            System.out.println("Class added successfully!");
        } else {
            System.out.println("Failed to add class. Time slot conflict!");
        }
    }

    private static void addLab(Scanner scanner, TimeTable timeTable) {
        System.out.print("Enter batch section (A/B): ");
        String batchSection = scanner.nextLine().toUpperCase();
        System.out.print("Enter lab room: ");
        String labRoom = scanner.nextLine();
        System.out.print("Enter course code: ");
        String courseCode = scanner.nextLine();
        System.out.print("Enter course title: ");
        String courseTitle = scanner.nextLine();
        System.out.print("Enter faculty name: ");
        String faculty = scanner.nextLine();
        System.out.print("Enter lab assistants (comma-separated): ");
        String labAssistants = scanner.nextLine();
        System.out.print("Enter credits (L-T-P-S-C): ");
        String credits = scanner.nextLine();
        System.out.print("Enter day (MON/TUE/WED/THU/FRI): ");
        String day = scanner.nextLine().toUpperCase();
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();

        Subject subject = new Subject(courseCode, courseTitle, faculty, credits, "", labAssistants);
        TimeSlot timeSlot = new TimeSlot(day, startTime, endTime);
        Class newLab = new Class(batchSection, labRoom, subject, timeSlot, true);
        
        if (timeTable.addClass(newLab)) {
            System.out.println("Lab added successfully!");
        } else {
            System.out.println("Failed to add lab. Time slot conflict!");
        }
    }

    private static void viewTimetable(Scanner scanner, TimeTable timeTable) {
        System.out.print("Enter batch section to view (A/B): ");
        String viewBatch = scanner.nextLine().toUpperCase();
        timeTable.displayTimetable(viewBatch);
    }

    private static void findFreeSlots(Scanner scanner, TimeTable timeTable) {
        System.out.print("Enter batch section (A/B): ");
        String freeBatch = scanner.nextLine().toUpperCase();
        System.out.print("Enter day (MON/TUE/WED/THU/FRI): ");
        String day = scanner.nextLine().toUpperCase();
        List<TimeSlot> freeSlots = timeTable.findFreeSlots(freeBatch, day);
        System.out.println("Free slots for " + day + ":");
        for (TimeSlot slot : freeSlots) {
            System.out.println(slot);
        }
    }

    private static TimeTable loadTimeTable() {
        File dataFile = new File(DATA_FILE);
        if (dataFile.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
                return (TimeTable) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading timetable data. Starting with a new timetable.");
            }
        }
        return new TimeTable();
    }

    private static void saveTimeTable(TimeTable timeTable) {
        File dataFolder = new File(DATA_FOLDER);
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(timeTable);
            System.out.println("Timetable data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving timetable data: " + e.getMessage());
        }
    }
}