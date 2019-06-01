package college.dbProject.student;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;

public class StudentGrades {

	private static Connection con;
	private JFrame frame = new JFrame("Student Portal");
	private JTable tblGrades = new JTable();
	private String selectedQuarter = "All";
	private int studentID;
	
	 /**
	  * @wbp.parser.entryPoint
	  */
	public StudentGrades() {
		initialize();
	}
	
	public StudentGrades(Connection conn, int id) {
		con = conn;
		studentID = id;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void initialize() {
		
    	frame.setVisible(true);
        frame.setSize(200, 200);
        frame.setBounds(0, 0, 1000, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setBackgroundImage();
        
        String[] quarters = {"All", "Spring 2018", "Summer 2018", "Fall 2018", "Winter 2019", "Spring 2019", "Summer 2019", "Fall 2019"};
        JComboBox qrComboBox = new JComboBox(quarters);
        qrComboBox.setFont(new Font("Cambria", Font.PLAIN, 18));
        qrComboBox.setSelectedIndex(0);
        qrComboBox.setBounds(360, 37, 145, 32);
        qrComboBox.setForeground(Color.BLACK);
        frame.getContentPane().add(qrComboBox);
        
        qrComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedQuarter = (String) qrComboBox.getSelectedItem();
				//System.out.println(selectedQuarter);
			}
		});

        JLabel lblSelectQuarter = new JLabel("Select Quarter");
        lblSelectQuarter.setFont(new Font("Cambria", Font.BOLD, 18));
        lblSelectQuarter.setBounds(220, 37, 140, 32);
        frame.getContentPane().add(lblSelectQuarter);
        
        DefaultTableModel tblModel = getDefaultTable();
        
        tblGrades.setFont(new Font("Cambria", Font.PLAIN, 18));
        tblGrades.setModel(tblModel);
        tblGrades.setBounds(127, 161, 637, 251);
        frame.getContentPane().add(tblGrades);
        populateTable();
        
        JButton btnViewGrades = new JButton("Submit");
        btnViewGrades.setFont(new Font("Cambria", Font.BOLD, 18));
        btnViewGrades.setBounds(281, 89, 120, 32);
        frame.getContentPane().add(btnViewGrades);

        btnViewGrades.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				populateTable();
			}
		});
        
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Cambria", Font.BOLD, 20));
        btnBack.setBounds(300, 500, 120, 32);
        frame.getContentPane().add(btnBack);
        
        btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Student student = new Student(con, studentID);
				student.initialize();
			}
		});
    }
        
    private void populateTable() {
    	String query = null;
		DefaultTableModel tblModel = getDefaultTable();
    	if (selectedQuarter.equals("All"))
			query = "select CourseNumber, CourseName, Credit, Quarter, Grade from TranscriptView where StudentID="+studentID+";";
		else
			query = "select CourseNumber, CourseName, Credit, Quarter, Grade from TranscriptView where StudentID="+studentID+" and Quarter='"+selectedQuarter+"';";
		//System.out.println(query);
		try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery(query);
			while (rs.next()) {
				tblModel.addRow(new Object[] {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)});
			}
			tblGrades.setModel(tblModel);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	private DefaultTableModel getDefaultTable() {
		
		DefaultTableModel tblModel = new DefaultTableModel();
        tblModel.addColumn("Course Number");
        tblModel.addColumn("Course Name");
        tblModel.addColumn("Credits");
        tblModel.addColumn("Quarter");
        tblModel.addColumn("Grade");
        tblModel.addRow(new Object[]{"Course Number", "Course Name", "Credits", "Quarter", "Grade"});
        
        return tblModel;
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
