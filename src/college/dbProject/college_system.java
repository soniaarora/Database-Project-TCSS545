package college.dbProject;


import java.awt.EventQueue;

import javax.sound.midi.Soundbank;
import javax.swing.JFrame;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;


public class college_system {

    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /**
     * The Dimension of the screen.
     */
    public static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * The grid height for the grid bag layout.
     */
    public static final int GRID_HEIGHT = 4;

    /**
     * An spacing of 5 for the grid bag layout.
     */
    public static final int SPACING_5 = 5;

    /**
     * An spacing of 20 for the grid bag layout.
     */
    public static final int SPACING_20 = 20;

    /**
     * An spacing of 25 for the grid bag layout.
     */
    public static final int SPACING_25 = 25;

    /**
     * An spacing of 50 for the grid bag layout.
     */
    public static final int SPACING_50 = 50;

    /**
     * An spacing of 150 for the grid bag layout.
     */
    public static final int SPACING_150 = 150;

    /**
     * The combobox of roles
     */
    JComboBox<String> rolesCombo;


    String roleSelected;

    /**
     * MySQL connection to Wine DB
     */
    public static Connection conn = null;

    public static JFrame frame = new JFrame("College Management System");

    public static void main(String[] args) {
        connectionToMySQL();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    college_system window = new college_system();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public college_system(){
        initialize();
    }

    private void initialize() {

        final int screenHeight = SCREEN_SIZE.height;
        final int screenWidth = SCREEN_SIZE.width;

        frame.setIconImage(new ImageIcon("images/").getImage());
//        frame.setResizable(false);
        frame.setSize(200,200);
          frame.setBounds(0, 0, 1000, 625);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //adds Background Image to the JFrame
//       frame.setContentPane(new BackgroundPanel(readImageFile()));

        //set the layout for the frame
        frame.setLayout(null);
        makeNewJLabel("College Management System ", 100, 80, 1000, 50);
        makeNewJLabel("Username", 250, 250, 160, 25);
        makeNewJLabel("Password", 250, 320, 160, 25);
        makeNewJLabel("Login As", 250, 390, 160, 35);
        JTextField uname = makeNewJTextField(450, 250, 160, 25);
        JTextField pass = makeNewJTextField(450, 320, 160, 25);

        JButton login = makeNewJButton("LOGIN",480, 500, 120,50 );

        String[] Roles = {"student", "faculty", "admin"};
        rolesCombo = new JComboBox<String>();
        rolesCombo.addItem("ANY");
        for(String item : Roles) {
            rolesCombo.addItem(item);
        }
        rolesCombo.setBounds(450, 390, 140, 35);
        rolesCombo.setFont(new Font("Serif", Font.CENTER_BASELINE, 18));
        frame.add(rolesCombo);
        rolesCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               roleSelected = (String) rolesCombo.getSelectedItem();

            }
        });
       login.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {


                String Id = uname.getText();
                String pswrd = pass.getText();


                String authenticationPswrd = "select Password from credentials where ID = " + Id + "";

               try {
                   Statement stmt = conn.createStatement();
                   System.out.print(stmt);
                 ResultSet RS = stmt.executeQuery(authenticationPswrd);

                 while(RS.next()) {

                     String pwd = RS.getString("Password");
                     if (pswrd.equals(pwd) ){

                         JOptionPane.showMessageDialog(frame, "Login Successful");
                         String movePage = roleSelected + "()";
//
                     } else {

                         System.out.println("Login Failed");
                     }
                 }
               }
               catch (Exception ex){
                   ex.printStackTrace();
               }
           }
       });

    }

    /** Connect to MySQL driver. */
    public static void connection() {
        try {
//            Class.forName("jdbc:mysql://localhost:3306/collegedb?autoReconnect=true&useSSL=false", "root", "root");
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    /** Connect to MySQL database. */
    public static void connectionToMySQL() {
        connection();
        String host = "jdbc:mysql://localhost:3306/collegedb";
        String username = "root";
        String password = "root";
        try {
            //connect = DriverManager.getConnection(host, username, password);
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost/collegedb?" +
                            "user=root&password=root");

            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    /**
     * This is a helper method that retrieves the background image file.
     *
     * @return my background image
     */
    public BufferedImage readImageFile() {
        BufferedImage background = new BufferedImage(1, 1, 1);
        try {
            background = ImageIO.read(new File("images/image1.jpg"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return background;
    }

    public static void makeNewJLabel (String labelText, int x, int y, int dx, int dy) {
        JLabel label = new JLabel(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 30));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        frame.add(label);
    }

    public static JTextField makeNewJTextField (int x, int y, int dx, int dy) {
        JTextField label = new JTextField();

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.CENTER_BASELINE, 15));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        frame.add(label);

        return label;
    }

    public static JButton makeNewJButton (String labelText ,int x, int y, int dx, int dy) {
        JButton label = new JButton(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.CENTER_BASELINE, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        frame.add(label);
        return label;
    }
}
