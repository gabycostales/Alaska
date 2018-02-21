import com.brackeen.javagamebook.util.MoreMath;

/**
    Tests trig table speed vs. trig function speed and prints
    a neat pattern.
    Note: this isn't an accurate benchmark since there are several
    factors involved (like printing output).
*/
public class TrigTableTest {

    public static final int COUNT = 8000000;

    public static void main(String[] args) {

        long funcTime = timeFunctionTest();
        long tableTime = timeTableTest();

        System.out.println("Function time: " + funcTime);
        System.out.println("Table time: " + tableTime);
    }

    public static long timeFunctionTest() {
        long startTime = System.currentTimeMillis();
        for (int i=0; i<COUNT; i++) {
            functionTest(i);
        }
        return System.currentTimeMillis() - startTime;
    }

    public static long timeTableTest() {
        long startTime = System.currentTimeMillis();
        for (int i=0; i<COUNT; i++) {
            tableTest(i);
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void functionTest(int i) {
        float angle = i * (float)Math.PI * 2 / COUNT;
        double cosAngle = Math.cos(angle);
        if ((i & 65535) == 0) {
            printMessage((float)cosAngle, "Cosine Function");
        }
    }

    public static void tableTest(int i) {
        float angle = i * (float)Math.PI * 2 / COUNT;
        float cosAngle = MoreMath.cos(MoreMath.angleConvert(angle));
        if ((i & 65535) == 0) {
            printMessage(cosAngle, "Cosine Table");
        }
    }

    public static void printMessage(float cosAngle, String msg) {
        int x = (int)(30 * (1 + cosAngle));
        for (int j=0; j<x; j++) {
            System.out.print(" ");
        }
        System.out.println(msg);
    }

}