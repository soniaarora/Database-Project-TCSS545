package college.dbProject.student;
import college.dbProject.main.BackgroundPanel;
import college.dbProject.student.ItemChangeListener;
import college.dbProject.student.MyTableModel;

import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

/** A user interface for a FacultyPage.
 */
public class FacultyPage {

    //constants to capture screen dimensions
    /**
     * A ToolKit.
     */
    private static final Toolkit KIT = Toolkit.getDefaultToolkit();

    /**
     * The Dimension of the screen.
     */
    public static final Dimension SCREEN_SIZE = KIT.getScreenSize();

    /**
     * MySQL connection to Faculty DB
     */
    public static Connection connect = null;


    /** An list of courses taught.*/
    public static ArrayList<String> courses;

    /** An list of varieties.*/
    public static ArrayList<String> theCourses;

    /** The course selected by the faculty.*/
    public static String courseSelected = "ANY";

    /** Combo boxes for the selections.*/
    public static JComboBox<String> coursesTaught = new JComboBox<String>();

    /**
     * The search results.
     */
    public static String searchResults = "";

    /**
     * The student ID.
     */
    public static String studentID = "";

    /** The table model.*/
    public static final MyTableModel model = new MyTableModel();

    /**
     * The results table.
     */
    public static JTable table = new JTable();

    /**
     * The scroll pane for the table.
     */
    public static JScrollPane scrollPane = new JScrollPane(table);

    /**
     * The number of rows in the table.
     */
    public static int rowsInTable = 0;

    /**
     * A renderer for the table that centers the data in the cells.
     */
    public static DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();


    /**
     * The main frame for the GUI.
     */
    public static JFrame frame = new JFrame("Faculty Portal");

    /**
     * Launch the GUI.
     */
    public static void main(String[] args) {
        connectionToMySQL();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FacultyPage window = new FacultyPage("", connect);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Connect to MySQL driver.
     */
    public static void connection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect to MySQL database.
     */
    public static void connectionToMySQL() {
        connection();
        String host = "jdbc:mysql://localhost:3306/CollegeDB?useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "root";
        String password = "tcss545spring2019";
        try {
            connect = DriverManager.getConnection(host, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the GUI.
     */
    public FacultyPage(String id, Connection conn) {
        connect = conn;
        initialize(id);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String id) {

        final int screenHeight = SCREEN_SIZE.height;
        final int screenWidth = SCREEN_SIZE.width;

        frame.setSize(200, 200);
        frame.setBounds(0, 0, 1000, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        setBackgroundImage();

        //add combobox to main faculty page:
        //get the list of courses taught by a faculty from the DB
        try {
            Statement stmt2;
            stmt2 = connect.createStatement();
            ResultSet coursesOfFaculty = stmt2.executeQuery("SELECT CourseNumber from FacultyCourses WHERE FacultyID = '" + id + "'");
            courses = new ArrayList<String>();
            while (coursesOfFaculty.next()) {
                courses.add(coursesOfFaculty.getString("CourseNumber"));
            }
            populateCoursesTaughtComboBox();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        makeNewJLabel("Select a course you are teaching:", 0, 75, 360, 25);

        //add buttons to main faculty page:

        // Update grades for students button
        JButton actionButton = new JButton("Update students' grades");
        actionButton.setForeground(Color.RED);
        actionButton.setBackground(Color.PINK);
        actionButton.setFont(new Font("Arial", Font.BOLD,  16));
        actionButton.setContentAreaFilled(true);
        actionButton.setFocusPainted(true);
        actionButton.setOpaque(true);
        actionButton.setBorderPainted(true);
        actionButton.setBounds(650, 100, 225, 70);
        frame.add(actionButton);


        actionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JFrame frame3 = new JFrame("Students' grades");
                frame3.setResizable(false);
                frame3.setSize(700, 500);
                frame3.setLayout(null);
                frame3.setLocation(screenWidth / 2 - frame3.getWidth() / 2,
                        screenHeight / 2 - frame3.getHeight() / 2);
                frame3.setVisible(true);

                JPanel panel = new JPanel();
                panel.setSize(700, 500);
                frame3.getContentPane().add(panel);
                panel.setBackground(new Color(115, 34, 68));
                panel.setLayout(new FlowLayout());

                final JTextField studentIDField = new JTextField("Please enter student ID", 20);
                studentIDField.setFont(new Font("Serif", Font.PLAIN, 18));
                panel.add(studentIDField);

                studentIDField.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        studentID = studentIDField.getText();
                    }
                });


                final JTextField courseIDField = new JTextField("Please enter course ID", 20);
                courseIDField.setFont(new Font("Serif", Font.PLAIN, 18));
                panel.add(courseIDField);

                final JTextField gradeField = new JTextField("Please enter grade", 41);
                gradeField.setFont(new Font("Serif", Font.PLAIN, 18));
                panel.add(gradeField);


                // submit button
                JButton submitButton = new JButton("submit");
                submitButton.setContentAreaFilled(false);
                submitButton.setFocusPainted(false);
                submitButton.setOpaque(true);
                submitButton.setBorderPainted(false);
                submitButton.setBounds(50, 370, 175, 100);
                panel.add(submitButton);

                submitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        String studentID = studentIDField.getText();
                        String CourseNumber = courseIDField.getText();
                        String grade = gradeField.getText();

                        if(!courses.contains(CourseNumber)) {
                            JOptionPane.showMessageDialog(frame, "You do not teach this course. Please try again.");
                        }

                        //String addOrUpdateGrade = "INSERT INTO StudentCourses VALUES ( '" + studentID + "', '" + courseID + "', '" + grade + "');";

                        String addOrUpdateGrade = "UPDATE Studentgrades SET Grade = '" + grade + "' WHERE StudentID = '" + studentID + "' AND CourseNumber = '" + CourseNumber + "'";
                        JOptionPane.showMessageDialog(frame, "Grade Update Successful");

                        try {

                            Statement stmt = connect.createStatement();
                            stmt.execute(addOrUpdateGrade);
                            System.out.print("Student grade successfully updated!");

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }


    /**
     * This is a helper method that creates the table when a search is performed.
     */
    public static void makeTable() {

        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        JTableHeader header = table.getTableHeader();

        Color c = new Color(145, 5, 5);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();

        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setBackground(c);

        headerRenderer.setFont(new Font("Arial", Font.BOLD, 60));
        headerRenderer.setForeground(Color.WHITE);

        header.setBorder(BorderFactory.createEtchedBorder());

        header.setDefaultRenderer(headerRenderer);

        Dimension dim = new Dimension(10, 60);
        header.setPreferredSize(dim);

        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

//        DefaultTableCellRenderer col5Renderer = new DefaultTableCellRenderer();
//        col5Renderer.setToolTipText("Click to select a quantity");
//        col5Renderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn column0 = table.getColumnModel().getColumn(0);
        column0.setPreferredWidth(100);
        column0.setHeaderValue("<html><b>Student ID</b></html>");

        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setPreferredWidth(80);
        column1.setCellRenderer(centerRenderer);
        column1.setHeaderValue("<html><b>LastName</b></html>");

        TableColumn column2 = table.getColumnModel().getColumn(2);
        column2.setPreferredWidth(80);
        column2.setCellRenderer(centerRenderer);
        column2.setHeaderValue("<html><b>FirstName</b></html>");

        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setPreferredWidth(80);
        column3.setCellRenderer(centerRenderer);
        column3.setHeaderValue("<html><b>Course ID</b></html>");


        TableColumn column4 = table.getColumnModel().getColumn(4);
        column4.setPreferredWidth(55);
        column4.setCellRenderer(centerRenderer);
        column4.setHeaderValue("<html><b>Grade</b></html>");


        //column5.setHeaderValue("<html><b>&nbsp;&nbsp;Select<br>Quantity</b></html>");

        final JComboBox<String> comboBox = new JComboBox<String>();

        for (int j = 0; j <= 10; j++) {
            comboBox.addItem("" + j);
        }

        DefaultCellEditor editor = new DefaultCellEditor(comboBox);
        column3.setCellEditor(editor);

        comboBox.addItemListener(new ItemChangeListener());
        table.setFont(new Font("Serif", Font.BOLD, 15));
        scrollPane.setBounds(40, 150, 400, 350);
        frame.add(scrollPane);
    }


    /**
     * This is a helper method that retrieves the background image file.
     *
     * @return my background image
     */
    public BufferedImage readImageFile() {
        BufferedImage background = new BufferedImage(1, 1, 1);
        try {
            background = ImageIO.read(new File("images/wallpaper.jpg"));
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return background;
    }

    /**
     * This is a helper method that populates the Courses Taught combo box.
     */
    public void populateCoursesTaughtComboBox() {
        frame.remove(coursesTaught);
        coursesTaught = new JComboBox<String>();
        coursesTaught.setBounds(150, 50, 175, 50);
        //coursesTaught.addItem("Courses You Are Teaching");
        Collections.sort(courses);
        for (int i = 0; i < courses.size(); i++) {
            coursesTaught.addItem(courses.get(i));
        }
        //the combo box action listener selects the course to be searched in the database
        coursesTaught.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                String courseQuery = "";

                courseSelected = (String) coursesTaught.getSelectedItem();// store course selected
                //courseSelected = "\"" + courseSelected + "\"";
                System.out.print(courseSelected);

                String courseView = "CREATE OR REPLACE VIEW ViewDetails AS SELECT ID, LastName, FirstName, CourseNumber, Grade FROM Student A, Studentgrades B WHERE A.ID = B.StudentID AND B.CourseNumber = '" + courseSelected + "'";
                Statement stmt3 = null;
                try {
                    stmt3 = connect.createStatement();
                    stmt3.executeUpdate(courseView);
                    System.out.println("View is Successfully Created!");
                } catch (SQLException e) {
                    System.out.println("error: failed to create a connection object.");
                    e.printStackTrace();
                }
                courseQuery = "SELECT * FROM ViewDetails";
                try {
                    Statement stmt = connect.createStatement();

                    ResultSet coursesRS = stmt.executeQuery(courseQuery);
                    rowsInTable = 0;
                    while (coursesRS.next()) {
                        // model.tavleData.....
                        MyTableModel.tableData[rowsInTable][0] = coursesRS.getString("ID");
                        MyTableModel.tableData[rowsInTable][1] = coursesRS.getString("LastName");
                        MyTableModel.tableData[rowsInTable][2] = coursesRS.getString("FirstName");
                        MyTableModel.tableData[rowsInTable][3] = coursesRS.getString("CourseNumber");
                        MyTableModel.tableData[rowsInTable][4] = coursesRS.getString("Grade");
                        //MyTableModel.tableData[rowsInTable][3] = coursesRS.getString("CourseID");
                        //MyTableModel.tableData[rowsInTable][4] = coursesRS.getString("Grade");
                        rowsInTable++;
                    }
                    for (int m = rowsInTable; m < 40; m++) {
                        for (int j = 0; j < 5; j++){
                            MyTableModel.tableData[m][j] = "";
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                makeTable();
                frame.revalidate();
                frame.pack();
            }
        });
        frame.add(coursesTaught);
        coursesTaught.setBounds(30, 100, 225, 25);
        frame.revalidate();
        frame.pack();
    }

    /**
     * Helper method makes a JLabel.
     *
     * @param labelText text for the label
     * @param x the x location
     * @param y the y location
     * @param dx the size of the rect in x direction
     * @param dy the size of the rect in y diretion
     */
    public static void makeNewJLabel (String labelText, int x, int y, int dx, int dy) {
        JLabel label = new JLabel(labelText);

        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(x, y, dx, dy);
        frame.add(label);
    }

    private static void setBackgroundImage() {
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












