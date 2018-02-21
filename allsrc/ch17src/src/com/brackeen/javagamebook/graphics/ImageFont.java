package com.brackeen.javagamebook.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.swing.ImageIcon;

/**
    The ImageFont class allows loading and drawing of text
    using images for the characters.

    Reads all the png images in a directory in the form
    "charXX.png" where "XX" is a decimal unicode value.

    Characters can have different widths and heights.
*/
public class ImageFont {

    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;

    private char firstChar;
    private Image[] characters;
    private Image invalidCharacter;


    /**
        Creates a new ImageFont with no characters.
    */
    public ImageFont() {
        this(null);
        firstChar = 0;
        characters = new Image[0];
    }


    /**
        Creates a new ImageFont and loads character images from
        the specified path.
    */
    public ImageFont(String path) {
        if (path != null) {
            load(path);
        }

        // make the character used for invalid characters
        invalidCharacter =
            new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        Graphics g = invalidCharacter.getGraphics();
        g.setColor(Color.RED);
        g.fillRect(0,0,10,10);
        g.dispose();
    }


    /**
        Loads the image files for each character from the
        specified path. For example, if "../fonts/large"
        is the path, this method searches for all the images
        names "charXX.png" in that path, where "XX" is a
        decimal unicode value. Not every character image needs
        to exist; you can only do numbers or uppercase letters,
        for example.
    */
    public void load(String path) throws NumberFormatException {
        // in this directory:
        // load every png file that starts with 'char'
        File dir = new File(path);
        File[] files = dir.listFiles();

        // find min and max chars
        char minChar = Character.MAX_VALUE;
        char maxChar = Character.MIN_VALUE;
        for (int i=0; i<files.length; i++) {
            int unicodeValue = getUnicodeValue(files[i]);
            if (unicodeValue != -1) {
                minChar = (char)Math.min(minChar, unicodeValue);
                maxChar = (char)Math.max(maxChar, unicodeValue);
            }
        }

        // load the images
        if (minChar < maxChar) {
            firstChar = minChar;
            characters = new Image[maxChar - minChar + 1];
            for (int i=0; i<files.length; i++) {
                int unicodeValue = getUnicodeValue(files[i]);
                if (unicodeValue != -1) {
                    int index = unicodeValue - firstChar;
                    characters[index] = new ImageIcon(
                        files[i].getAbsolutePath()).getImage();
                }
            }

        }

    }


    private int getUnicodeValue(File file)
        throws NumberFormatException
    {
        String name = file.getName().toLowerCase();
        if (name.startsWith("char") && name.endsWith(".png")) {
            String unicodeString =
                name.substring(4, name.length() - 4);
            return Integer.parseInt(unicodeString);
        }
        return -1;
    }


    /**
        Gets the image for a specific character. If no image for
        the character exists, a special "invalid" character image
        is returned.
    */
    public Image getImage(char ch) {
        int index = ch - firstChar;
        if (index < 0 || index >= characters.length ||
            characters[index] == null)
        {
            return invalidCharacter;
        }
        else {
            return characters[index];
        }
    }


    /**
        Gets the string width, in pixels, for the specified string.
    */
    public int stringWidth(String s) {
        int width = 0;
        for (int i=0; i<s.length(); i++) {
            width += charWidth(s.charAt(i));
        }
        return width;
    }


    /**
        Gets the char width, in pixels, for the specified char.
    */
    public int charWidth(char ch) {
        return getImage(ch).getWidth(null);
    }


    /**
        Gets the char height, in pixels, for the specified char.
    */
    public int charHeight(char ch) {
        return getImage(ch).getHeight(null);
    }


    /**
        Draws the specified string at the (x, y) location.
    */
    public void drawString(Graphics g, String s, int x, int y) {
        drawString(g, s, x, y, LEFT | BOTTOM);
    }


    /**
        Draws the specified string at the (x, y) location.
    */
    public void drawString(Graphics g, String s, int x, int y,
        int anchor)
    {
        if ((anchor & HCENTER) != 0) {
            x-=stringWidth(s) / 2;
        }
        else if ((anchor & RIGHT) != 0) {
            x-=stringWidth(s);
        }
        // clear horizontal flags for char drawing
        anchor &= ~HCENTER;
        anchor &= ~RIGHT;

        // draw the characters
        for (int i=0; i<s.length(); i++) {
            drawChar(g, s.charAt(i), x, y, anchor);
            x+=charWidth(s.charAt(i));
        }
    }


    /**
        Draws the specified character at the (x, y) location.
    */
    public void drawChar(Graphics g, char ch, int x, int y) {
        drawChar(g, ch, x, y, LEFT | BOTTOM);
    }


    /**
        Draws the specified character at the (x, y) location.
    */
    public void drawChar(Graphics g, char ch, int x, int y,
        int anchor)
    {
        if ((anchor & HCENTER) != 0) {
            x-=charWidth(ch) / 2;
        }
        else if ((anchor & RIGHT) != 0) {
            x-=charWidth(ch);
        }

        if ((anchor & VCENTER) != 0) {
            y-=charHeight(ch) / 2;
        }
        else if ((anchor & BOTTOM) != 0) {
            y-=charHeight(ch);
        }
        g.drawImage(getImage(ch), x, y, null);
    }
}