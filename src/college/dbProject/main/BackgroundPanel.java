package college.dbProject.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

/**
 * Creates a background image for the Shrek-tris game.
 *

 *
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JComponent {

    /** A default size for the JPanel. */
    public static final Dimension DEFAULT_SIZE = new Dimension(1000, 625);

    /** My background image. */
    private final BufferedImage myBackgroundImage;


    /**
     * The constructor for the background panel.
     *
     * @param theImage the image
     */
    public BackgroundPanel(final BufferedImage theImage) {
        super();
        myBackgroundImage = theImage;
        setPreferredSize(DEFAULT_SIZE);

    }

    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(myBackgroundImage, null, 0, 0);
    }
}
