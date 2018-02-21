/**
    Measures the granularity of System.currentTimeMillis().
*/
public class GranularityTest {

    public static final int NUM_SAMPLES = 100;

    public static void main(String[] args) {
        long sum = 0;
        int count = 0;

        long lastTime = System.currentTimeMillis();
        while (count < NUM_SAMPLES) {
            long currTime = System.currentTimeMillis();

            // if the time changed, record the difference
            if (currTime > lastTime) {
                long granularity = currTime - lastTime;
                // keep a running sum of the granularity
                sum+=granularity;
                count++;
                lastTime = currTime;
            }
        }

        // display results
        System.out.println("Average granularity of " +
            "System.currentTimeMillis(): " + ((float)sum / count));
    }
}
