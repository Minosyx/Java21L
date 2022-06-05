package MergeSort;

public class MergerR implements Runnable {
    private final Object arr;
    private final Object left;
    private final Object right;
    public MergerR(Object arr, Object left, Object right){
        this.arr = arr;
        this.left = left;
        this.right = right;
    }

    public Object getArray() { return this.arr; }

    private void merge(int[] arr, int[] left, int[] right){
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length){
            if (left[i] <= right[j])
                arr[k] = left[i++];
            else
                arr[k] = right[j++];
            k++;
        }

        while (i < left.length){
            arr[k] = left[i++];
            k++;
        }

        while (j < right.length){
            arr[k] = right[j++];
            k++;
        }
    }

    private void merge(long[] arr, long[] left, long[] right){
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length){
            if (left[i] <= right[j])
                arr[k] = left[i++];
            else
                arr[k] = right[j++];
            k++;
        }

        while (i < left.length){
            arr[k] = left[i++];
            k++;
        }

        while (j < right.length){
            arr[k] = right[j++];
            k++;
        }
    }

    private void merge(float[] arr, float[] left, float[] right){
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length){
            if (left[i] <= right[j])
                arr[k] = left[i++];
            else
                arr[k] = right[j++];
            k++;
        }

        while (i < left.length){
            arr[k] = left[i++];
            k++;
        }

        while (j < right.length){
            arr[k] = right[j++];
            k++;
        }
    }

    private void merge(double[] arr, double[] left, double[] right){
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length){
            if (left[i] <= right[j])
                arr[k] = left[i++];
            else
                arr[k] = right[j++];
            k++;
        }

        while (i < left.length){
            arr[k] = left[i++];
            k++;
        }

        while (j < right.length){
            arr[k] = right[j++];
            k++;
        }
    }

    @Override
    public void run(){
        if(arr instanceof int[]){
            merge((int[]) arr, (int[]) left, (int[]) right);
        }
        else if (arr instanceof long[]){
            merge((long[]) arr, (long[]) left, (long[]) right);
        }
        else if (arr instanceof float[]){
            merge((float[]) arr, (float[]) left, (float[]) right);
        }
        else if (arr instanceof double[]){
            merge((double[]) arr, (double[]) left, (double[]) right);
        }
    }
}
