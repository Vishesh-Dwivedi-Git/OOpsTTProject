// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class TimeTableGUI extends JFrame {
//     private JTextField batchSectionField;
//     private JTextField facultyField;
//     private JTextField dayField;
//     private JTextField startTimeField;
//     private JTextField endTimeField;
//     private JTextField subjectField;
//     private JCheckBox isLabCheckBox;
//     private TimeTable timeTable;

//     public TimeTableGUI() {
//         setTitle("Time Table Management System");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         timeTable = new TimeTable();
//         setupUI();
//     }

//     private void setupUI() {
//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(8, 2));

//         batchSectionField = new JTextField(20);
//         facultyField = new JTextField(20);
//         dayField = new JTextField(20);
//         startTimeField = new JTextField(20);
//         endTimeField = new JTextField(20);
//         subjectField = new JTextField(20);
//         isLabCheckBox = new JCheckBox("Is Lab");

//         panel.add(new JLabel("Batch Section:"));
//         panel.add(batchSectionField);

//         panel.add(new JLabel("Faculty:"));
//         panel.add(facultyField);

//         panel.add(new JLabel("Day:"));
//         panel.add(dayField);

//         panel.add(new JLabel("Start Time (HH:MM):"));
//         panel.add(startTimeField);

//         panel.add(new JLabel("End Time (HH:MM):"));
//         panel.add(endTimeField);

//         panel.add(new JLabel("Subject:"));
//         panel.add(subjectField);

//         panel.add(isLabCheckBox);

//         JButton addButton = new JButton("Add Class");
//         addButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 addClass();
//             }
//         });

//         panel.add(addButton);
//         add(panel);
//     }

//     private void addClass() {
//         String batchSection = batchSectionField.getText();
//         String faculty = facultyField.getText();
//         String day = dayField.getText();
//         String startTime = startTimeField.getText();
//         String endTime = endTimeField.getText();
//         String subjectName = subjectField.getText();
//         boolean isLab = isLabCheckBox.isSelected();

//         TimeSlot newSlot = new TimeSlot(day, startTime, endTime);
//         Subject subject = new Subject(subjectName);

//         if (timeTable.isSlotFree(newSlot, batchSection, faculty)) {
//             Class newClass = new Class(batchSection, faculty, subject, newSlot, isLab);
//             JOptionPane.showMessageDialog(this, "Class added successfully.");
//         } else {
//             JOptionPane.showMessageDialog(this, "Time slot is not available.");
//         }
//     }

//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> {
//             TimeTableGUI gui = new TimeTableGUI();
//             gui.setVisible(true);
//         });
//     }
// }
