package MergeSort;

public class MergeSortR
{
    public static Object sort(Object array){
        SorterR m1 = new SorterR(array);
        Thread t1 = new Thread(m1);
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return m1.getArray();
    }
}
