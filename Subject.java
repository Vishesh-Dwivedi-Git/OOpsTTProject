import java.util.*;
import java.io.Serializable;

public class Subject  implements Serializable{
    private String courseCode;
    private String courseTitle;
    private String faculty;
    private String credits;
    private String prerequisite;
    private String labAssistants;

    public Subject(String courseCode, String courseTitle, String faculty, 
                   String credits, String prerequisite, String labAssistants) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.faculty = faculty;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.labAssistants = labAssistants;
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getFaculty() { return faculty; }
    public String getCourseTitle() { return courseTitle; }
    public String getCredits() { return credits; }
    public String getPrerequisite() { return prerequisite; }
    public String getLabAssistants() { return labAssistants; }
}