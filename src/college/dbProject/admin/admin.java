package college.dbProject.admin;


import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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

//    public static Connection conn = null;
    public static JFrame adm = new JFrame("Admin screen");

    public admin() {
        initialize();
    }

    public static void main(String[] args) {

//        conn = connectionToMySQL();
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

        adm.setSize(200, 200);
        adm.setBounds(0, 0, 1000, 625);
        adm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adm.getContentPane().setLayout(null);
        setBackgroundImage();
        JButton forFaculty = makeNewJButton("Faculty Portal", 400, 200, 250, 50);
        JButton forStudent = makeNewJButton("Student Portal", 400, 300, 250, 50);
//        JButton deleteId = makeNewJButton("Delete Profile", 400, 400, 250, 50 );
        forFaculty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminFaculty adFac = new AdminFaculty();
                adm.setVisible(false);
                adFac.forFaculty.setVisible(true);
            }
        });

        forStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StudentAdmin stuAd = new StudentAdmin();
                adm.setVisible(false);
                stuAd.forStudent.setVisible(true);
            }
        });


    }

    public static JButton makeNewJButton(String labelText ,int x, int y, int dx, int dy) {
        JButton label = new JButton(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        adm.add(label);
        return label;
    }

    private static void setBackgroundImage() {
        try {
            String relativePath = "images\\wallpaper.jpg";
            String absolutePath = new File(relativePath).getAbsolutePath();
            Image image = new ImageIcon(ImageIO.read(new File(absolutePath))).getImage();
            ImageIcon scaledIcon = new ImageIcon(image.getScaledInstance(1000, 625, Image.SCALE_FAST));
            adm.setContentPane(new JLabel(scaledIcon));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

}