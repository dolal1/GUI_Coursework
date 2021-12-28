package com.ooad.kmis.teacher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import com.ooad.kmis.student.Student;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;

public class EditStudent extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private static Student student;
	private static StudentsPage studentsPage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditStudent frame = new EditStudent(student, studentsPage);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con;
	PreparedStatement pst;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:8889/kps", "root", "root");
		} catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(EditStudent.this, "Failed to located Class");
		} catch (SQLException e) {
            JOptionPane.showMessageDialog(EditStudent.this, "Failed to connect to database");
		}
	}

	/**
	 * Create the frame.
	 */
	public EditStudent(Student studentObj, StudentsPage students) {
		Connect();
		student = studentObj;
		studentsPage = students;
		
		setBounds(100, 100, 471, 416);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(new Color(204, 204, 204));
		panel_1.setBounds(6, 6, 459, 337);
		contentPane.add(panel_1);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblFirstName.setBounds(30, 70, 64, 16);
		panel_1.add(lblFirstName);
		
		JLabel lblNewLabel_1 = new JLabel("Edit Student records");
		lblNewLabel_1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(150, 6, 155, 24);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblLastName.setBounds(30, 98, 71, 16);
		panel_1.add(lblLastName);
		
		txtFirstName = new JTextField();
		txtFirstName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(121, 65, 313, 26);
		panel_1.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtLastName.setColumns(10);
		txtLastName.setBounds(121, 93, 313, 26);
		panel_1.add(txtLastName);
		
		JLabel lblGender = new JLabel("Gender");
		lblGender.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblGender.setBounds(30, 128, 64, 16);
		panel_1.add(lblGender);
		
		JComboBox<String> txtGender = new JComboBox<String>();
		txtGender.setModel(new DefaultComboBoxModel<String>(new String[] {"Male", "Female"}));
		txtGender.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtGender.setBounds(121, 124, 130, 27);
		panel_1.add(txtGender);
		
		JLabel lbDob = new JLabel("Date of birth");
		lbDob.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lbDob.setBounds(30, 163, 64, 16);
		panel_1.add(lbDob);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(121, 153, 313, 26);
		panel_1.add(dateChooser);
		
		JLabel lblClass = new JLabel("Class");
		lblClass.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		lblClass.setBounds(30, 196, 64, 16);
		panel_1.add(lblClass);
		
		JComboBox<String> txtClass = new JComboBox<String>();
		txtClass.setModel(new DefaultComboBoxModel<String>(new String[] {"P1", "P2", "P3", "P4", "P5", "P6", "P7"}));
		txtClass.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		txtClass.setBounds(121, 192, 130, 27);
		panel_1.add(txtClass);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String gender = txtGender.getSelectedItem().toString();
                String genderAbr;
                if(gender == "Male") genderAbr = "M";
                else genderAbr = "F";
                String pupilClass = txtClass.getSelectedItem().toString();
                java.util.Date dob = dateChooser.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(dob);
                Date theDate = Date.valueOf(date);
                
            	try {
                    pst = con.prepareStatement("UPDATE students SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, class = ? WHERE reg_no = ?");
                    pst.setString(1, firstName);
                    pst.setString(2, lastName);
                    pst.setDate(3, theDate);
                    pst.setString(4, genderAbr);
                    pst.setString(5, pupilClass);
                    pst.setString(6, studentObj.registrationNo);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(EditStudent.this, "Student records edited sucefully");
                    EditStudent.this.setVisible(false);
                    studentsPage.loadAllStudents();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, sexp);
                }
			}
		});
		btnEdit.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnEdit.setBounds(30, 246, 117, 29);
		panel_1.add(btnEdit);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditStudent.this.setVisible(false);
			}
		});
		btnCancel.setForeground(new Color(255, 0, 0));
		btnCancel.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnCancel.setBounds(313, 246, 117, 29);
		panel_1.add(btnCancel);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
            	try {
                    pst = con.prepareStatement("DELETE FROM students WHERE reg_no = ?");
                    
                    pst.setString(1, studentObj.registrationNo);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(EditStudent.this, "Student deleted sucefully");
                    EditStudent.this.setVisible(false);
                    studentsPage.loadAllStudents();
                } catch (SQLException sexp) {
                	Logger.getLogger(EditStudent.class.getName()).log(Level.SEVERE, null, sexp);
                }
			}
		});
		btnDelete.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnDelete.setBounds(174, 246, 117, 29);
		panel_1.add(btnDelete);
		
		txtFirstName.setText(student.firstName);
		txtLastName.setText(student.lastName);
		txtGender.setSelectedItem(student.gender);
		txtClass.setSelectedItem(student.studentClass);
		dateChooser.setDate(student.dateOfBirth);
	}

}
