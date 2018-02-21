package com.brackeen.javagamebook.util;

/**
    Monitors heap size, allocation size, change in allocation,
    change in heap size, and detects garbage collection (when the
    allocation size decreases). Call takeSample() to take a
    "sample" of the currently memory state.
*/
public class MemMonitor {

    /**
        The Data class contains info on a series of float values.
        The min, max, sum and count of the data can be retrieved.
    */
    public static class Data {

        float lastValue = 0;
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;
        float sum = 0;
        int count = 0;

        public void addValue(float value) {
            lastValue = value;
            sum+=value;
            count++;
            min = Math.min(min, value);
            max = Math.max(max, value);
        }


        public String toString() {
            return "Min: " + toByteFormat(min) + "  " +
                   "Max: " + toByteFormat(max) + "  " +
                   "Avg: " + toByteFormat(sum / count);
        }
    }

    private Data heapSize = new Data();
    private Data allocSize = new Data();
    private Data allocIncPerUpdate = new Data();
    private int numHeapIncs = 0;
    private long startTime = System.currentTimeMillis();


    /**
        Takes a sample of the current memory state.
    */
    public void takeSample() {
        Runtime runtime = Runtime.getRuntime();
        long currHeapSize = runtime.totalMemory();
        long currAllocSize = currHeapSize - runtime.freeMemory();

        if (currHeapSize > heapSize.lastValue) {
            numHeapIncs++;
        }
        if (currAllocSize >= allocSize.lastValue) {
            allocIncPerUpdate.addValue(
                (currAllocSize - allocSize.lastValue));
        }

        heapSize.addValue(currHeapSize);
        allocSize.addValue(currAllocSize);
    }


    /**
        Convert number of bytes to string representing bytes,
        kilobytes, megabytes, etc.
    */
    public static String toByteFormat(float numBytes) {
        String[] labels = {" bytes", " KB", " MB", " GB"};
        int labelIndex = 0;

        // decide most appropriate label
        while (labelIndex < labels.length - 1 && numBytes > 1024) {
            numBytes/=1024;
            labelIndex++;
        }
        return (Math.round(numBytes*10)/10f) + labels[labelIndex];
    }


    public String toString() {
        long time = System.currentTimeMillis() - startTime;
        float timeSecs = (float)time / 1000;
        return "Total Time: " + timeSecs + "s\n" +
            "Heap: " + heapSize + "\n" +
            "Allocation: " + allocSize + "\n" +
            "Allocation inc/update: " + allocIncPerUpdate + "\n" +
            "Num Heap Incs: " + numHeapIncs;
    }
}
