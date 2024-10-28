import java.util.*;
import java.io.Serializable;

public class TimeTable implements Serializable {
    private List<Class> classes;
    private Map<String, List<TimeSlot>> teacherSchedule;
    private static final String HOD = "Dr. John Doe"; // Replace with actual HOD name

    public TimeTable() {
        classes = new ArrayList<>();
        teacherSchedule = new HashMap<>();
    }

    public boolean isSlotFree(TimeSlot newSlot, String batchSection, String faculty) {
        for (Class cls : classes) {
            if (cls.getBatchSection().equals(batchSection) && 
                cls.getTimeSlot().getDay().equals(newSlot.getDay()) &&
                isTimeOverlap(cls.getTimeSlot(), newSlot)) {
                return false;
            }
        }
        
        if (teacherSchedule.containsKey(faculty)) {
            for (TimeSlot existingSlot : teacherSchedule.get(faculty)) {
                if (existingSlot.getDay().equals(newSlot.getDay()) &&
                    isTimeOverlap(existingSlot, newSlot)) {
                    return false;
                }
            }
        }
        
        return true;
    }

    private boolean isTimeOverlap(TimeSlot slot1, TimeSlot slot2) {
        int start1 = timeToMinutes(slot1.getStartTime());
        int end1 = timeToMinutes(slot1.getEndTime());
        int start2 = timeToMinutes(slot2.getStartTime());
        int end2 = timeToMinutes(slot2.getEndTime());

        return (start1 < end2 && start2 < end1);
    }

    private int timeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public boolean addClass(Class newClass) {
        if (isSlotFree(newClass.getTimeSlot(), 
                       newClass.getBatchSection(), 
                       newClass.getSubject().getFaculty())) {
            classes.add(newClass);
            
            String faculty = newClass.getSubject().getFaculty();
            teacherSchedule.computeIfAbsent(faculty, k -> new ArrayList<>())
                          .add(newClass.getTimeSlot());
            return true;
        }
        return false;
    }

    public void displayTimetable(String batchSection) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                       Timetable for Section " + batchSection + "                                                    ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║   Time   │   Monday   │   Tuesday  │  Wednesday │  Thursday  │   Friday   ║");
        System.out.println("╠══════════╪════════════╪════════════╪════════════╪════════════╪════════════╣");

        String[] timeSlots = {"09:00-10:30", "10:45-12:15", "12:15-13:15", "14:30-16:00", "16:00-17:30"};
        String[] days = {"MON", "TUE", "WED", "THU", "FRI"};

        for (String timeSlot : timeSlots) {
            System.out.printf("║ %8s │", timeSlot);
            for (String day : days) {
                boolean slotFilled = false;
                for (Class cls : classes) {
                    if (cls.getBatchSection().equals(batchSection) &&
                        cls.getTimeSlot().getDay().equals(day) &&
                        isTimeOverlap(cls.getTimeSlot(), new TimeSlot(day, timeSlot.split("-")[0], timeSlot.split("-")[1]))) {
                        System.out.printf(" %-10s │", cls.getSubject().getCourseCode());
                        slotFilled = true;
                        break;
                    }
                }
                if (!slotFilled) {
                    System.out.print("           │");
                }
            }
            System.out.println();
        }

        System.out.println("╚══════════╧════════════╧════════════╧════════════╧════════════╧════════════╝");
        System.out.println("\nCourse Details:");
        System.out.println("═══════════════");

        for (Class cls : classes) {
            if (cls.getBatchSection().equals(batchSection)) {
                Subject subject = cls.getSubject();
                System.out.printf("%-10s: %s (%s)\n", subject.getCourseCode(), subject.getCourseTitle(), subject.getCredits());
                System.out.printf("           Instructor: %s\n", subject.getFaculty());
                if (cls.isLab()) {
                    System.out.printf("           Lab Assistants: %s\n", subject.getLabAssistants());
                }
                System.out.println();
            }
        }

        System.out.println("HOD: " + HOD);
    }

    public List<TimeSlot> findFreeSlots(String batchSection, String day) {
        List<TimeSlot> freeSlots = new ArrayList<>();
        List<TimeSlot> occupiedSlots = new ArrayList<>();

        for (Class cls : classes) {
            if (cls.getBatchSection().equals(batchSection) && cls.getTimeSlot().getDay().equals(day)) {
                occupiedSlots.add(cls.getTimeSlot());
            }
        }

        occupiedSlots.sort(Comparator.comparing(TimeSlot::getStartTime));

        String[] standardTimes = {"09:00", "10:45", "12:15", "14:30", "16:00", "17:30"};
        
        for (int i = 0; i < standardTimes.length - 1; i++) {
            TimeSlot potentialSlot = new TimeSlot(day, standardTimes[i], standardTimes[i+1]);
            boolean isFree = true;

            for (TimeSlot occupiedSlot : occupiedSlots) {
                if (isTimeOverlap(potentialSlot, occupiedSlot)) {
                    isFree = false;
                    break;
                }
            }

            if (isFree) {
                freeSlots.add(potentialSlot);
            }
        }

        return freeSlots;
    }
}