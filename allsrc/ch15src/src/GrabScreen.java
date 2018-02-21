import java.awt.*;                      // Frame, Graphics, Color, ..
import java.awt.image.*;                // BufferedImage
import java.awt.event.*;                // KeyAdapter, KeyEvent
import java.io.*;                       // File

import javax.swing.JFrame;
import javax.imageio.*;                 // ImageIO

class GrabScreen extends JFrame {

// fields
private BufferedImage screenImage;
private Rectangle screenRect;

// constructor
public GrabScreen() {
    setUndecorated(true);

    Dimension screenDims = Toolkit.getDefaultToolkit().getScreenSize();
    screenRect = new Rectangle(0,0, screenDims.width, screenDims.height);

    screenImage = grabScreen(screenRect);

    try {
        ImageIO.write(screenImage, "jpeg", new File("screen.jpg"));
    } catch (Exception ignored) {
        System.out.println("OOPS " + ignored);
    }

    saveScreenSnapshotAsThumbnail(  screenRect, new Dimension(160, 120),
                                            "thumb.jpg");

    // darken the image for display
    darkenImage(screenImage);

    // allow user to exit with keypress
    addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            dispose();
        }
    });

    // show screenshot
    setBounds(screenRect);
    show();
}

/*************************************************************************
 * Overridden Component.update()
 *
 * @param g the Graphics context to paint in.
 *************************************************************************/
public void update(Graphics g) {
    paint(g);
}

/*************************************************************************
 * Overridden Component.paint()
 *
 * @param g the Graphics context to paint to.
 *************************************************************************/
public void paint(Graphics g) {

    if ( screenImage != null ) {
        g.drawImage(screenImage, 0,0, null);
    }

}

/*************************************************************************
 * Darkens a BufferedImage
 *************************************************************************/
 public void darkenImage(BufferedImage image) {
    int width = image.getWidth();
    int height= image.getHeight();

    for (int y=0; y < height; y++) {
        for (int x=0; x < width; x++) {
            int color = image.getRGB(x,y);
            color = color >> 1;
            color = color & 0x7F7F7F;
            image.setRGB(x,y, color);
        }
    }

}

/*************************************************************************
 * Grab the visible screen
 *
 * @return a BufferedImage containing the specified screen sub-rectangle
 *************************************************************************/
private BufferedImage grabScreen(Rectangle screenRect) {

    Robot robot = null;
    try {
        robot = new Robot();
    } catch (AWTException awtException) {
        System.out.println(awtException);
        return null;
    }

    BufferedImage screenImage = robot.createScreenCapture(screenRect);

    return screenImage;
}

/*************************************************************************
 * Grab the visible screen, turn into thumbnail, and save as JPEG.
 *
 * @param screenRect the Rectangle defining the screen
 * @param thumbnailDimension the desird thumbnail Dimension
 * @param thumbnailFilename the generated JPEG filename
 *************************************************************************/
private void saveScreenSnapshotAsThumbnail( Rectangle screenRect,
                                            Dimension thumbnailDimension,
                                            String thumbnailFilename) {

    BufferedImage screenImage = grabScreen(screenRect);

    BufferedImage screenThumbnail = new BufferedImage(
        thumbnailDimension.width,
        thumbnailDimension.height,
        BufferedImage.TYPE_INT_RGB);

    // scale the image
    Graphics2D g = screenThumbnail.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(screenImage, 0, 0,
        thumbnailDimension.width,
        thumbnailDimension.height, null);
    g.dispose();

    // write it to a file
    try {
        ImageIO.write(screenThumbnail, "jpeg", new File(thumbnailFilename));
    } catch (Exception ignored) {
        System.out.println("OOPS " + ignored);
    }
}


//-------------------------------------------------------------------
// main() entry point
//-------------------------------------------------------------------
public static void main (String[] args) {
    new GrabScreen();
}

}
