package MergeSort;

public class Main {
    public static void main(String[] args){
        int[] tab = new int[] {5, 6, 3, -5, -20, -10};
        float[] tab1 = new float[] {1.2f, -2.6f, 2.3f, 1.1f};
        double[] tab2 = new double[] {0.9, -1.2, 7.5, -8.3, 0.5, -0.3, 4.1};
        StringBuilder res = new StringBuilder();
        for (var j : tab2) {
            res.append(j).append(" ");
        }
        System.out.println(res);
        tab2 = (double[])MergeSortT.sort(tab2);
        res = new StringBuilder(" ");
        for (var j : tab2) {
            res.append(j).append(" ");
        }
        System.out.println(res);
    }
}
