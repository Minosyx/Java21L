package MergeSort;

public class MergeSortT
{
    public static Object sort(Object array){
        SorterT m1 = new SorterT(array);
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
