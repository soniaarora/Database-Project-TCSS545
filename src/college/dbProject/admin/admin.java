package college.dbProject.admin;


import java.awt.*;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static college.dbProject.Connection.connection.connectionToMySQL;


public class admin extends JFrame {


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

    public static Connection conn = null;
    public static JFrame adm = new JFrame("Admin screen");

    public admin() {
        initialize();
    }

    public static void main(String[] args) {

        conn = connectionToMySQL();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    admin window = new admin();
                   window.adm.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {

        final int screenHeight = SCREEN_SIZE.height;
        final int screenWidth = SCREEN_SIZE.width;
        adm.setSize(200, 200);
        adm.setBounds(0, 0, 1000, 625);
        adm.setLocationByPlatform(true);
        adm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        adm.setLayout(null);
        JButton forFaculty = makeNewJButton("Faculty Data", 400, 200, 150, 50);
        JButton forStudent = makeNewJButton("Student Data", 400, 300, 150, 50);
    }


    public static JButton makeNewJButton (String labelText ,int x, int y, int dx, int dy) {
        JButton label = new JButton(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        adm.add(label);
        return label;
    }


}