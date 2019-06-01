package college.dbProject.student;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;


public class StudentCourses {
	
	private static Connection con;
	private JFrame frame = new JFrame("Student Portal");
	private JTable tblCourses = new JTable();
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCourses = new JComboBox();
	private int studentID;
	private String selectedQuarter;
	private String selectedCourse;
	
	 /**
	  * @wbp.parser.entryPoint
	  */ 
	public StudentCourses() {
		initialize();
	}
	
	public StudentCourses(Connection conn, int id) {
		studentID = id;
		con = conn;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void initialize() {
		
    	frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setBounds(0, 0, 1000, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setBackgroundImage();
        
        DefaultTableModel tblModel = populateTable();
        tblCourses.setFont(new Font("Cambria", Font.PLAIN, 16));
        tblCourses.setModel(tblModel);
        tblCourses.setBounds(167, 105, 637, 251);
        frame.getContentPane().add(tblCourses);
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 16));
        btnBack.setBounds(424, 512, 116, 32);
        frame.getContentPane().add(btnBack);
        
        JLabel lblMyCourses = new JLabel("My Courses");
        lblMyCourses.setFont(new Font("Cambria", Font.BOLD, 20));
        lblMyCourses.setBounds(424, 41, 120, 32);
        frame.getContentPane().add(lblMyCourses);
        
        JLabel lblSelectQuarter = new JLabel("Select Quarter");
        lblSelectQuarter.setFont(new Font("Cambria", Font.BOLD, 16));
        lblSelectQuarter.setBounds(250, 408, 120, 32);
        frame.getContentPane().add(lblSelectQuarter);
        
        String[] quarters = {"Summer 2019", "Fall 2019"};
		JComboBox cbxQuarters = new JComboBox(quarters);
		cbxQuarters.setFont(new Font("Cambria", Font.PLAIN, 16));
        cbxQuarters.setSelectedIndex(0);
        selectedQuarter = (String) cbxQuarters.getSelectedItem();
        cbxQuarters.setBounds(377, 408, 130, 32);
        frame.getContentPane().add(cbxQuarters);
        cbxQuarters.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedQuarter = (String) cbxQuarters.getSelectedItem();
			}
		});
        
        JLabel lblSelectCourse = new JLabel("Select Course");
        lblSelectCourse.setFont(new Font("Cambria", Font.BOLD, 16));
        lblSelectCourse.setBounds(250, 451, 120, 32);
        frame.getContentPane().add(lblSelectCourse);
        cbxCourses.setFont(new Font("Cambria", Font.PLAIN, 16));
        
        cbxCourses.setBounds(377, 451, 130, 32);
        frame.getContentPane().add(cbxCourses);
        cbxCourses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedCourse = (String)cbxCourses.getSelectedItem();
			}
		});
        populateCourses();
        
        JButton btnAddCourse = new JButton("Add Course");
        btnAddCourse.setFont(new Font("Cambria", Font.BOLD, 16));
        btnAddCourse.setBounds(541, 408, 130, 32);
        frame.getContentPane().add(btnAddCourse);
        btnAddCourse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCourse();
			}
		});
        
        JButton btnDropCourse = new JButton("Drop Course");
        btnDropCourse.setFont(new Font("Cambria", Font.BOLD, 16));
        btnDropCourse.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dropCourse();
        	}
        });
        btnDropCourse.setBounds(541, 451, 130, 32);
        frame.getContentPane().add(btnDropCourse);
        
        btnBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Student student = new Student(con, studentID);
				student.initialize();
			}
		});
    }
	
	private DefaultTableModel getDefaultTable() {
		
		DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("Course Number");
        tblModel.addColumn("Course Name");
        tblModel.addColumn("Credits");
        tblModel.addColumn("Quarter");
        tblModel.addRow(new Object[]{"Course Number", "Course Name", "Credits", "Quarter"});
        
        return tblModel;
	}
	
	private DefaultTableModel populateTable() {
		
		String query = "select CourseNumber, CourseName, Credit, Quarter from TranscriptView where StudentID="+studentID+";";
        DefaultTableModel tblModel = getDefaultTable();
        try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			while (rs.next()) {
				tblModel.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)});
			}
		} catch (SQLException e1) {
			System.out.println("SQL exception occurred 3");
		}
        
        return tblModel;
	}
	
	@SuppressWarnings("unchecked")
	private void populateCourses() {
		//String query = "select CourseNumber from Course where CourseNumber not in (select CourseNumber from studentcourses where studentID="+studentID+");";
		String query = "select CourseNumber from Course;";
        try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			//cbxCourses.removeAllItems();
			while (rs.next()) {
				cbxCourses.addItem(rs.getString(1));
			}
			cbxCourses.setSelectedIndex(0);
			selectedCourse = (String)cbxCourses.getSelectedItem();
        } catch(SQLException e) {
        	System.out.println("SQL exception occurred 4");
        }
	}
	
	private void addCourse() {
		String query = "call total_credits("+studentID+", '"+selectedQuarter+"', @out);";
		executeQuery(query);
		
		query = "select @out;";
		int enrolledCredits = getCredits(query);
		
		query = "select Credit from Course where CourseNumber = '"+selectedCourse+"';";
		int newCredit = getCredits(query);
		
		if (enrolledCredits+newCredit <= 13) {
			query = "insert into StudentCourses values ("+studentID+", '"+selectedCourse+"', '"+selectedQuarter+"');";
			boolean status = executeQuery(query);
			if (status) {
				DefaultTableModel tblModel = populateTable();
				tblCourses.setModel(tblModel);
				//populateCourses();
				JOptionPane.showMessageDialog(frame, "Course Added Successfully!");
			} else {
				JOptionPane.showMessageDialog(frame, "Failed to add course!");
			}
		} else {
			JOptionPane.showMessageDialog(frame, "Cannot add course due to maximum credit limit!");
		}
		
	}
	
	private int getCredits(String query) {
		int credits = 0;
		try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			while (rs.next()) {
				credits = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL exception occurred 1");
			e.printStackTrace();
		}
		return credits;
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
	
	private void dropCourse() {
		String query = "delete from StudentCourses where CourseNumber='"+selectedCourse+"'";
		executeQuery(query);
		DefaultTableModel tblModel = populateTable();
		tblCourses.setModel(tblModel);
		//populateCourses();
		JOptionPane.showMessageDialog(frame, "Course Dropped Successfully!");
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
}
