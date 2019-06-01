package college.dbProject.student;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import college.dbProject.Connection.connection;
import college.dbProject.main.college_system;

import java.sql.Connection;
import java.sql.SQLException;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Student extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Connection con;
    private JFrame frame = new JFrame("Student Portal");
    private int studentID;

    /**
     * @wbp.parser.entryPoint
     */
    public Student() {
    	initialize();
    }
    
    public Student(Connection conn, int id) {
    	con = conn;
    	studentID = id;
    }

    public static void main(String[] args) {
		Connection conn = connection.connectionToMySQL();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                   Student window = new Student(conn, 30001);
                   window.initialize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void initialize() {
    	
    	frame.setVisible(true);
    	frame.setSize(200, 200);
        frame.setBounds(0, 0, 1000, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setBackgroundImage();
        
        /*JButton btnUpdateAddress = new JButton("My Profile");
    	btnUpdateAddress.setBounds(101, 166, 175, 41);
    	frame.getContentPane().add(btnUpdateAddress);
    	btnUpdateAddress.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});*/
    	
    	JButton btnAdddropCourses = new JButton("My Courses");
    	btnAdddropCourses.setFont(new Font("Cambria", Font.BOLD, 18));
    	btnAdddropCourses.setBounds(147, 202, 167, 41);
    	frame.getContentPane().add(btnAdddropCourses);
    	btnAdddropCourses.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			StudentCourses courses = new StudentCourses(con, studentID);
    			courses.initialize();
    		}
    	});
    	
    	JButton btnViewGrades = new JButton("My Grades");
    	btnViewGrades.setFont(new Font("Cambria", Font.BOLD, 18));
    	btnViewGrades.setBounds(385, 202, 175, 41);
    	frame.getContentPane().add(btnViewGrades);
    	btnViewGrades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				StudentGrades studGrade = new StudentGrades(con, studentID);
				studGrade.initialize();
			}
		});
    	
    	JButton btnLogout = new JButton("Logout");
    	btnLogout.setFont(new Font("Cambria", Font.BOLD, 18));
    	btnLogout.setBounds(421, 301, 119, 41);
    	frame.getContentPane().add(btnLogout);
    	
    	btnLogout.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				@SuppressWarnings("unused")
				college_system window = new college_system();
				college_system.frame.setVisible(true);
			}
		});
    	
    	JButton btnAccount = new JButton("My Account");
    	btnAccount.setFont(new Font("Cambria", Font.BOLD, 18));
    	btnAccount.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			StudentAccount account = new StudentAccount(con, studentID);
    			account.initialize();
    		}
    	});
    	btnAccount.setBounds(626, 202, 139, 39);
    	frame.getContentPane().add(btnAccount);
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
