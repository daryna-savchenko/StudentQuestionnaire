import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class student {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Profile");
        JPanel mainpanel = new JPanel(new BorderLayout(5, 5));
        mainpanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));

        JTextField nameField = new JTextField(15);
        JTextField surnameField = new JTextField(15);
        JTextField groupField = new JTextField(15);
        JTextField ageField = new JTextField(15);
        JTextField emailField = new JTextField(15);

        String[] chooseYear = {"1 Year", "2 Year", "3 Year","4 Year"};
        JComboBox<String> comboBox = new JComboBox<>( chooseYear );
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        JRadioButton other = new JRadioButton("Other");
        male.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(male);
        group.add(female);
        group.add(other);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.add(male);
        genderPanel.add(female);
        genderPanel.add(other);

        JCheckBox programming = new JCheckBox("Programming");
        JCheckBox sports = new JCheckBox("Sports");

        JPanel hobbyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        hobbyPanel.add(programming);
        hobbyPanel.add(sports);

        JTextArea aboutArea = new JTextArea(4, 15);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");


        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Surname"));
        formPanel.add(surnameField);
    
        formPanel.add(new JLabel("Age"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Group"));
        formPanel.add(groupField);
       
        formPanel.add(new JLabel("E-Mail"));
        formPanel.add(emailField);
       
        formPanel.add(new JLabel("Year of study"));
        formPanel.add(comboBox);
        
        formPanel.add(new JLabel("Gender"));
        formPanel.add(genderPanel); 

        formPanel.add(new JLabel("Hobby"));
        formPanel.add(hobbyPanel); 

        formPanel.add(new JLabel("About"));
        formPanel.add(aboutArea); 


        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);



        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gr = groupField.getText().trim();
            String email = emailField.getText().trim();
            String year = (String) comboBox.getSelectedItem();
            String about = aboutArea.getText().trim();

            if (name.isEmpty() || surname.isEmpty() || ageText.isEmpty() || gr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in Name, Surname, Age and Group.");
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age <= 0) {
                    JOptionPane.showMessageDialog(frame, "Age must be a positive number.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Age must contain only numbers.");
                return;
            }

            if (!email.isEmpty() && !email.contains("@")) {
                JOptionPane.showMessageDialog(frame, "Email should contain '@'.");
                return;
            }

            String gender = "Other";
            if (male.isSelected()) {
                gender = "Male";
            } else if (female.isSelected()) {
                gender = "Female";
            }

            StringBuilder hobbies = new StringBuilder();
            if (programming.isSelected()) {
                hobbies.append("Programming");
            }
            if (sports.isSelected()) {
                hobbies.append("Sports");
            }
            if (hobbies.length() == 0) {
                hobbies.append("No hobbies");
            }

            
            try (PrintWriter writer = new PrintWriter(new FileWriter("student_profile.txt"))) {
                writer.println("Student");
                writer.println("Name: " + name);
                writer.println("Surname: " + surname);
                writer.println("Age: " + age);
                writer.println("Group: " + group);
                writer.println("Email: " + (email.isEmpty() ? "Not provided" : email));
                writer.println("Year of study: " + year);
                writer.println("Gender: " + gender);
                writer.println("Hobbies: " + hobbies.toString().trim());
                writer.println("About student: " + (about.isEmpty() ? "No additional information" : about));

                JOptionPane.showMessageDialog(frame, "Data saved to student_profile.txt");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error while saving the file.");
            }
        });


        deleteButton.addActionListener(e -> {
            nameField.setText("");
            surnameField.setText("");
            ageField.setText("");
            groupField.setText("");
            emailField.setText("");
            comboBox.setSelectedIndex(0);
            male.setSelected(true);
            programming.setSelected(false);
            sports.setSelected(false);
            aboutArea.setText("");
        });

        mainpanel.add(formPanel, BorderLayout.CENTER);
        mainpanel.add(buttonPanel, BorderLayout.SOUTH);


        frame.add(mainpanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(520, 420);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}