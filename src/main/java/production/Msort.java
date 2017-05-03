package production;

import java.util.Arrays;
import java.util.Random;

import static java.lang.System.arraycopy;

/**
 * Created by Пользователь on 02.05.2017.
 */
public class Msort {
    public static void main(String[] args){

        int arraySize = 200;
        Random random = new Random();

        int[] inputArray = new int[arraySize];
        for (int i = 0; i < arraySize; i++) {
            inputArray[i] = random.nextInt(2000);
        }
        int [] secArray = mergeSort(inputArray);

        for (int j = 0; secArray.length > j; j++) {
            System.out.println(secArray[j]);
        }

    }

    public static int[] mergeSort(int[] arr) {
        if (arr.length <= 1)  return arr;

        int mid = arr.length / 2;
        int[] left = new int[mid];
        int[] right = new int[mid + arr.length%2];

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length / 2) {
                left[i] = arr[i];
            } else {
                right[j++] = arr[i];
            }
        }
        return merge(mergeSort(left), mergeSort(right));
    }

    public static int[] merge(int[] left, int[] right) {
        int a = 0, b = 0;
        int[] merged = new int[left.length + right.length];
        //забиваем отсортированный массив из левой и правой частей
        for (int i = 0; i < left.length + right.length; i++) {
            //поочередно берем меньший член из крайних левого и правого
            if (b < right.length && a < left.length)
                if (left[a] > right[b] && b < right.length)
                    merged[i] = right[b++];
                else
                    merged[i] = left[a++];
            else
            if (b < right.length)
                merged[i] = right[b++];
            else
                merged[i] = left[a++];
        }
        return merged;
    }

}


