package sorts;

import java.util.Arrays;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/7/19 2:09 下午
 * @Version 1.0
 * 堆排序： 1. 建堆，2. 保证局部最小化, 3. 倒序输出
 */
public class HeapSort<T extends Comparable<T>> {

    public <T extends Comparable<T>> void buildHeap(T[] arr) {
        int len = arr.length;

        for (int i=len/2; i>0; i--) {
            adjustHeap(arr, len, i);
        }
    }

    public <T extends Comparable<T>> void adjustHeap(T[] arr, int len, int k) {
        arr[0] = arr[k];
        int max = k;
        for (int i=k*2; i<len; i*=2) {    //比较的是孩子，不是父亲
            if (i+1<len) {
                // left < right
                if (arr[i].compareTo(arr[i+1]) > 0) {
                    i++;
                }
            }
            if (arr[0].compareTo(arr[i]) < 0) {
                break;
            } else {
                arr[k] = arr[i];
                k=i;
            }
        }
        arr[k] = arr[0];
    }

    // 建好堆后，相邻子树大小不定，要再调整一下
    // 调整前 [10, 90, 67, 10, 45, 2, 3]
    // 调整后 [90, 90, 67, 45, 10, 3, 2]
    public void heapSort(T[] arr, int len) {
        for (int i=len-1; i>=1; i--) {
            arr[0] = arr[1];
            arr[1] = arr[i];
            arr[i] = arr[0];
            adjustHeap(arr, i, 1);
        }
    }

    public void print(T[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        Integer[] arr = {0,10,2,3,45,67,90};
        HeapSort<Integer> sort = new HeapSort<>();
        sort.buildHeap(arr);
        sort.heapSort(arr, arr.length);
        sort.print(arr);
    }
}
