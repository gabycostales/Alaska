package com.brackeen.javagamebook.util;

/**
    The MoreMath class provides functions not contained in the
    java.lang.Math or java.lang.StrictMath classes.
*/
public class MoreMath {

    // a trig table with 4096 entries
    private static final int TABLE_SIZE_BITS = 12;
    private static final int TABLE_SIZE = 1 << TABLE_SIZE_BITS;
    private static final int TABLE_SIZE_MASK = TABLE_SIZE - 1;
    private static final int HALF_PI = TABLE_SIZE / 4;
    private static final float CONVERSION_FACTOR =
        (float)(TABLE_SIZE / (2 * Math.PI));

    private static float[] sinTable;

    // init trig table when this class is loaded
    static {
        init();
    }

    private static void init() {
        sinTable = new float[TABLE_SIZE];
        for (int i=0; i<TABLE_SIZE; i++) {
            sinTable[i] = (float)Math.sin(i / CONVERSION_FACTOR);
        }
    }


    /**
        Cosine function, where angle is from 0 to 4096 instead of
        0 to 2pi.
    */
    public static float cos(int angle) {
        return sinTable[(HALF_PI-angle) & TABLE_SIZE_MASK];
    }


    /**
        Sine function, where angle is from 0 to 4096 instead of
        0 to 2pi.
    */
    public static float sin(int angle) {
        return sinTable[angle & TABLE_SIZE_MASK];
    }


    /**
        Converts an angle in radians to the system used by this
        class (0 to 2pi becomes 0 to 4096)
    */
    public static int angleConvert(float angleInRadians) {
        return  (int)(angleInRadians * CONVERSION_FACTOR);
    }


    /**
        Returns the sign of the number. Returns -1 for negative,
        1 for positive, and 0 otherwise.
    */
    public static int sign(short v) {
        return (v>0)?1:(v<0)?-1:0;
    }


    /**
        Returns the sign of the number. Returns -1 for negative,
        1 for positive, and 0 otherwise.
    */
    public static int sign(int v) {
        return (v>0)?1:(v<0)?-1:0;
    }


    /**
        Returns the sign of the number. Returns -1 for negative,
        1 for positive, and 0 otherwise.
    */
    public static int sign(long v) {
        return (v>0)?1:(v<0)?-1:0;
    }


    /**
        Returns the sign of the number. Returns -1 for negative,
        1 for positive, and 0 otherwise.
    */
    public static int sign(float v) {
        return (v>0)?1:(v<0)?-1:0;
    }


    /**
        Returns the sign of the number. Returns -1 for negative,
        1 for positive, and 0 otherwise.
    */
    public static int sign(double v) {
        return (v>0)?1:(v<0)?-1:0;
    }


    /**
        Faster ceil function to convert a float to an int.
        Contrary to the java.lang.Math ceil function, this
        function takes a float as an argument, returns an int
        instead of a double, and does not consider special cases.
    */
    public static int ceil(float f) {
        if (f > 0) {
            return (int)f + 1;
        }
        else {
           return (int)f;
        }
    }


    /**
        Faster floor function to convert a float to an int.
        Contrary to the java.lang.Math floor function, this
        function takes a float as an argument, returns an int
        instead of a double, and does not consider special cases.
    */
    public static int floor(float f) {
        if (f >= 0) {
            return (int)f;
        }
        else {
           return (int)f - 1;
        }
    }


    /**
        Returns true if the specified number is a power of 2.
    */
    public static boolean isPowerOfTwo(int n) {
        return ((n & (n-1)) == 0);
    }


    /**
        Gets the number of "on" bits in an integer.
    */
    public static int getBitCount(int n) {
        int count = 0;
        while (n > 0) {
            count+=(n & 1);
            n>>=1;
        }
        return count;
    }
}
