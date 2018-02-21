
import java.awt.*;
import com.brackeen.javagamebook.graphics.*;
import com.brackeen.javagamebook.test.GameCore;


public class ImageFontTest extends GameCore {

    public static void main(String[] args) {
        new ImageFontTest().run();
    }

    private static final long TOTAL_TIME = 6500;

    private ImageFont bigFont;
    private ImageFont medFont;
    private long remainingTime;
    private CharMovement[] charMovement;

    public void init() {
        super.init();

        remainingTime = TOTAL_TIME;
        // load image fonts
        bigFont = new ImageFont("../fonts/big");
        medFont = new ImageFont("../fonts/medium");

        String message = "Good Times!";
        int stringWidth = medFont.stringWidth(message);
        charMovement = new CharMovement[message.length()];
        for (int i=0; i<message.length(); i++) {
            charMovement[i] = new CharMovement(message, i,
                (screen.getWidth() - stringWidth) / 2,
                screen.getHeight() / 2);
        }
    }

    public void update(long elapsedTime) {
        remainingTime -= elapsedTime;
        if (remainingTime <= 0) {
            stop();
        }
    }

    public void draw(Graphics2D g) {
        // erase background
        g.setColor(Color.BLACK);
        g.fillRect(0,0,screen.getWidth(), screen.getHeight());

        // draw some aligned text
        medFont.drawString(g, "Left", 0, 0,
            ImageFont.LEFT | ImageFont.TOP);
        medFont.drawString(g, "Center", screen.getWidth()/2, 0,
            ImageFont.HCENTER | ImageFont.TOP);
        medFont.drawString(g, "Right", screen.getWidth(), 0,
            ImageFont.RIGHT | ImageFont.TOP);

        // draw seconds remaining
        String timeLeft = "" + (remainingTime / 1000);
        bigFont.drawString(g, timeLeft, 0, screen.getHeight());

        // draw moving characters
        double p = (double)(TOTAL_TIME - remainingTime) / TOTAL_TIME;
        for (int i=0; i<charMovement.length; i++) {
            charMovement[i].draw(g, p);
        }
    }

    /**
        Simple animation class to animate a character along a
        path.
    */
    public class CharMovement {
        char ch;
        Point[] path;

        public CharMovement(String s, int charIndex, int x, int y) {
            int stringWidth = medFont.stringWidth(s);
            for (int i=0; i<charIndex; i++) {
                x+=medFont.charWidth(s.charAt(i));
            }
            ch = s.charAt(charIndex);

            path = new Point[4];

            // start offscreen
            path[0] = new Point(x-2000, y);

            // move to the center of the screen and pause there
            path[1] = new Point(x, y);
            path[2] = path[1];

            // "explode" at a random angle far away
            double angle = Math.random() * 2 * Math.PI;
            double distance = 1000 + 1000*Math.random();
            path[3] = new Point(
                (int)Math.round(x + Math.cos(angle) * distance),
                (int)Math.round(y + Math.sin(angle) * distance));
        }

        /**
            Draws this character in the path, where p is the
            location in the path from 0 to 1.
        */
        public void draw(Graphics g, double p) {
            int points = path.length - 1;
            int index = (int)(p*points);
            p = p * points - index;
            Point start = path[index % path.length];
            Point goal = path[(index + 1) % path.length];
            int x = (int)Math.round(goal.x * p + start.x * (1-p));
            int y = (int)Math.round(goal.y * p + start.y * (1-p));
            medFont.drawChar(g, ch, x, y);
        }
    }
}
