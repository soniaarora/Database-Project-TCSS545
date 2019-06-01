package college.dbProject.student;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;

public class StudentAccount {

	private static Connection con;
	private JFrame frame = new JFrame("Student Portal");
	private int studentID;
	private JTextField txtFname;
	private JTextField txtLname;
	private JTextField txtEmail;
	private JTextField txtAddress;
	private JPasswordField passwordField;
	private JTextField txtId;
	
	/**
	  * @wbp.parser.entryPoint
	  */
	public StudentAccount() {
		initialize();
	}
	
	public StudentAccount(Connection conn, int id) {
		con = conn;
		studentID = id;
	}
	
    public void initialize() {
		
    	frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setBounds(0, 0, 1000, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setBackgroundImage();
        
        JLabel lblAccountInfo = new JLabel("My Account Information");
        lblAccountInfo.setFont(new Font("Cambria", Font.BOLD, 20));
        lblAccountInfo.setBounds(345, 28, 240, 45);
        frame.getContentPane().add(lblAccountInfo);
        
        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setFont(new Font("Cambria", Font.BOLD, 16));
        lblFirstName.setBounds(223, 141, 100, 31);
        frame.getContentPane().add(lblFirstName);
        
        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setFont(new Font("Cambria", Font.BOLD, 16));
        lblLastName.setBounds(223, 193, 100, 20);
        frame.getContentPane().add(lblLastName);
        
        JLabel lblEmailId = new JLabel("Email ID");
        lblEmailId.setFont(new Font("Cambria", Font.BOLD, 16));
        lblEmailId.setBounds(223, 237, 100, 20);
        frame.getContentPane().add(lblEmailId);
        
        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(new Font("Cambria", Font.BOLD, 16));
        lblAddress.setBounds(223, 288, 100, 20);
        frame.getContentPane().add(lblAddress);
        
        txtFname = new JTextField();
        txtFname.setFont(new Font("Cambria", Font.PLAIN, 16));
        txtFname.setEditable(false);
        txtFname.setBounds(367, 148, 194, 20);
        frame.getContentPane().add(txtFname);
        txtFname.setColumns(10);
        String query = "select FirstName from Student where ID = "+studentID+";";
        txtFname.setText(getString(query));
        
        txtLname = new JTextField();
        txtLname.setFont(new Font("Cambria", Font.PLAIN, 16));
        txtLname.setEditable(false);
        txtLname.setBounds(367, 195, 194, 20);
        frame.getContentPane().add(txtLname);
        txtLname.setColumns(10);
        query = "select LastName from Student where ID = "+studentID+";";
        txtLname.setText(getString(query));
        
        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Cambria", Font.PLAIN, 16));
        txtEmail.setEditable(false);
        txtEmail.setColumns(10);
        txtEmail.setBounds(367, 239, 194, 20);
        frame.getContentPane().add(txtEmail);
        query = "select Email from Student where ID = "+studentID+";";
        txtEmail.setText(getString(query));
        
        txtAddress = new JTextField();
        txtAddress.setFont(new Font("Cambria", Font.PLAIN, 16));
        txtAddress.setColumns(10);
        txtAddress.setBounds(367, 288, 194, 92);
        frame.getContentPane().add(txtAddress);
        query = "select Address from Student where ID="+studentID+";";
        txtAddress.setText(getString(query));
        
        
        JButton btnUpdateAddress = new JButton("Update Address");
        btnUpdateAddress.setFont(new Font("Cambria", Font.BOLD, 14));
        btnUpdateAddress.setBounds(603, 323, 166, 31);
        frame.getContentPane().add(btnUpdateAddress);
        btnUpdateAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String address = txtAddress.getText();
				String query = "update Student set Address='"+address+"' where ID="+studentID+";";
				boolean status = executeQuery(query);
				if (status) {
					JOptionPane.showMessageDialog(frame, "Address updated successfully!");
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to update address!");
				}
			}
		});
        
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Cambria", Font.BOLD, 16));
        lblPassword.setBounds(223, 419, 100, 20);
        frame.getContentPane().add(lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(367, 421, 194, 20);
        frame.getContentPane().add(passwordField);
        
        JButton btnUpdatePassword = new JButton("Update Password");
        btnUpdatePassword.setFont(new Font("Cambria", Font.BOLD, 14));
        btnUpdatePassword.setBounds(603, 419, 166, 31);
        frame.getContentPane().add(btnUpdatePassword);
        btnUpdatePassword.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = passwordField.getText();
				String query = "update Credentials set Password='"+password+"' where ID="+studentID+";";
				boolean status = executeQuery(query);
				if (status) {
					JOptionPane.showMessageDialog(frame, "Password updated successfully!");
				} else {
					JOptionPane.showMessageDialog(frame, "Failed to update password!");
				}
			}
		});
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 16));
        btnBack.setBounds(394, 505, 89, 31);
        frame.getContentPane().add(btnBack);
        btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Student student = new Student(con, studentID);
				student.initialize();
			}
		});
        
        JLabel lblStudentId = new JLabel("Student ID");
        lblStudentId.setFont(new Font("Cambria", Font.BOLD, 16));
        lblStudentId.setBounds(223, 104, 100, 20);
        frame.getContentPane().add(lblStudentId);
        
        txtId = new JTextField();
        txtId.setFont(new Font("Cambria", Font.PLAIN, 16));
        txtId.setEditable(false);
        txtId.setColumns(10);
        txtId.setBounds(367, 106, 194, 20);
        frame.getContentPane().add(txtId);
        txtId.setText(String.valueOf(studentID));
                
    }
	
	private void setBackgroundImage() {
    	try {
    		String relativePath = "images\\wallpaper.jpg";
        	String absolutePath = new File(relativePath).getAbsolutePath();
        	Image image = new ImageIcon(ImageIO.read(new File(absolutePath))).getImage();
        	ImageIcon scaledIcon = new ImageIcon(image.getScaledInstance(1000, 625, Image.SCALE_FAST));
			frame.setContentPane(new JLabel(scaledIcon));
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    }
	
	private String getString(String query) {
		String result = "";
		try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			while (rs.next()) {
				result = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean executeQuery(String query) {
		try {
			Statement stmnt = con.createStatement();
			stmnt.execute(query);
		} catch (SQLException e) {
			System.out.println("SQL exception occurred in executeQuery");
			return false;
		}
		return true;
	}
}
