import java.util.*;
import java.io.Serializable;


public class Class  implements Serializable{
    private String batchSection;
    private String classRoom;
    private Subject subject;
    private TimeSlot timeSlot;
    private boolean isLab;

    public Class(String batchSection, String classRoom, Subject subject, TimeSlot timeSlot, boolean isLab) {
        this.batchSection = batchSection;
        this.classRoom = classRoom;
        this.subject = subject;
        this.timeSlot = timeSlot;
        this.isLab = isLab;
    }

    // Getters
    public String getBatchSection() { return batchSection; }
    public TimeSlot getTimeSlot() { return timeSlot; }
    public Subject getSubject() { return subject; }
    public String getClassRoom() { return classRoom; }
    public boolean isLab() { return isLab; }
}