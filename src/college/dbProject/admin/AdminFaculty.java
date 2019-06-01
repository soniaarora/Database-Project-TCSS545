package college.dbProject.admin;



import college.dbProject.main.BackgroundPanel;
import college.dbProject.main.college_system;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.stream.Collectors;

import static college.dbProject.Connection.connection.connectionToMySQL;

public class AdminFaculty {
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

    public static Connection connection = null;
    public static JFrame forFaculty = new JFrame("Enter Faculty Credentials");

    public static admin admin;

    public AdminFaculty(){
        initialize();
    }
    public static void main(String arg[]){


        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminFaculty window = new AdminFaculty();
                    window.forFaculty.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void initialize(){

        final int screenHeight = SCREEN_SIZE.height;
        final int screenWidth = SCREEN_SIZE.width;

        forFaculty.setSize(200, 200);
        forFaculty.setBounds(0, 0, 1000, 625);
        forFaculty.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        forFaculty.getContentPane().setLayout(null);
        setBackgroundImage();

        makeNewJLabel("ID Number", 220, 100, 200, 50);
        makeNewJLabel("First Name", 220, 170, 200, 50);
        makeNewJLabel("Last Name", 220, 240, 200, 50);
        makeNewJLabel("Email", 220, 310, 200, 50);
//        makeNewJLabel("Password", 220, 310, 220, 50);

        JTextField Id = makeNewJTextField(460, 100, 200, 30);
        JTextField fname = makeNewJTextField(460, 170, 200, 30);
        JTextField lname = makeNewJTextField(460, 240, 200, 30);
        JTextField email = makeNewJTextField(460, 310, 200, 30);

        JButton  submit = makeNewJButton("Submit", 380,400, 100, 30 );
        JButton cancel = makeNewJButton("Cancel", 500, 400, 100, 30);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = Id.getText();
                String Fname = fname.getText();
                String Lname = lname.getText();
                String Email = email.getText();
                String pswrd = Fname;
                String query1 = "INSERT INTO Credentials VALUES ( " + id + ", '" + pswrd + "');";
                String query2 = "INSERT INTO Faculty VALUES ( " + id + ", '" + Fname + "', '" + Lname + "', '" + Email + "');";





                try {
                    connection = connectionToMySQL();
                    Statement stmt = connection.createStatement();
                    stmt.execute(query1);
                    stmt.execute(query2);

                    forFaculty.setVisible(false);
                    admin = new admin();
                    admin.adm.setVisible(true);
                    connection.close();


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                forFaculty.dispose();
                college_system coll = new college_system();
                coll.frame.setVisible(true);

            }
        });

    }

    public static String generatePassword() {
//        byte[] array = new byte[7]; // length is bounded by 7
//        new Random().nextBytes(array);
//        String generatedString = new String(array, Charset.forName("UTF-8"));

        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        String generatedString = new Random().ints(length, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());

        return generatedString;
    }



    public static void makeNewJLabel (String labelText, int x, int y, int dx, int dy) {
        JLabel label = new JLabel(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 30));
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setBounds(x, y, dx, dy);
        forFaculty.add(label);
    }

    public static JTextField makeNewJTextField (int x, int y, int dx, int dy) {
        JTextField label = new JTextField();

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.LAYOUT_LEFT_TO_RIGHT ,18));
        label.setHorizontalAlignment(JLabel.HORIZONTAL);
        label.setBounds(x, y, dx, dy);
        forFaculty.add(label);

        return label;
    }

    public static JButton makeNewJButton (String labelText ,int x, int y, int dx, int dy) {
        JButton label = new JButton(labelText);

        label.setForeground(Color.BLACK);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        forFaculty.add(label);
        return label;
    }

    public static BufferedImage readImageFile() {
        BufferedImage background = new BufferedImage(1, 1, 1);
        try {
            background = ImageIO.read(new File("images/wallpaper.jpg"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return background;
    }

    private static void setBackgroundImage() {
        try {
            String relativePath = "images\\wallpaper.jpg";
            String absolutePath = new File(relativePath).getAbsolutePath();
            Image image = new ImageIcon(ImageIO.read(new File(absolutePath))).getImage();
            ImageIcon scaledIcon = new ImageIcon(image.getScaledInstance(1000, 625, Image.SCALE_FAST));
            forFaculty.setContentPane(new JLabel(scaledIcon));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

}
