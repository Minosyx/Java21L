package MergeSort;

import java.util.Arrays;

public class SorterR implements Runnable
{
    private final Object arr;
    public SorterR(Object arr){
        this.arr = arr;
    }

    public Object getArray(){
        return this.arr;
    }

    private void sort(Object arr){
        if(arr instanceof int[]){
            if (((int[])arr).length < 2) { return; }

            int[] left = Arrays.copyOfRange((int[])arr, 0, ((int[])arr).length / 2);
            int[] right = Arrays.copyOfRange((int[])arr, ((int[])arr).length / 2, ((int[])arr).length);

            SorterT leftM = new SorterT(left);
            SorterT rightM = new SorterT(right);
            Thread t1 = new Thread(leftM);
            Thread t2 = new Thread(rightM);
            t1.start();
            t2.start();
            try{
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MergerR m = new MergerR(arr, left, right);
            Thread nThread = new Thread(m);
            nThread.start();
            try {
                nThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(arr instanceof long[]){
            if (((long[])arr).length < 2) { return; }

            long[] left = Arrays.copyOfRange((long[])arr, 0, ((long[])arr).length / 2);
            long[] right = Arrays.copyOfRange((long[])arr, ((long[])arr).length / 2, ((long[])arr).length);

            SorterT leftM = new SorterT(left);
            SorterT rightM = new SorterT(right);
            Thread t1 = new Thread(leftM);
            Thread t2 = new Thread(rightM);
            t1.start();
            t2.start();
            try{
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MergerR m = new MergerR(arr, left, right);
            Thread nThread = new Thread(m);
            nThread.start();
            try {
                nThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(arr instanceof float[]){
            if (((float[])arr).length < 2) { return; }

            float[] left = Arrays.copyOfRange((float[])arr, 0, ((float[])arr).length / 2);
            float[] right = Arrays.copyOfRange((float[])arr, ((float[])arr).length / 2, ((float[])arr).length);

            SorterT leftM = new SorterT(left);
            SorterT rightM = new SorterT(right);
            Thread t1 = new Thread(leftM);
            Thread t2 = new Thread(rightM);
            t1.start();
            t2.start();
            try{
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MergerR m = new MergerR(arr, left, right);
            Thread nThread = new Thread(m);
            nThread.start();
            try {
                nThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(arr instanceof double[]){
            if (((double[])arr).length < 2) { return; }

            double[] left = Arrays.copyOfRange((double[])arr, 0, ((double[])arr).length / 2);
            double[] right = Arrays.copyOfRange((double[])arr, ((double[])arr).length / 2, ((double[])arr).length);

            SorterT leftM = new SorterT(left);
            SorterT rightM = new SorterT(right);
            Thread t1 = new Thread(leftM);
            Thread t2 = new Thread(rightM);
            t1.start();
            t2.start();
            try{
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MergerR m = new MergerR(arr, left, right);
            Thread nThread = new Thread(m);
            nThread.start();
            try {
                nThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        sort(arr);
    }
}
