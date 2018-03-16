package main;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算拆分数组最小差值
 */
public class SortMinimumDifference {
    public static List<int[]> splitArray(int[] oriArray) {
        if(oriArray.length == 0){
            System.out.println("array长度为0，不能拆分！");
            return null;
        }
        if(oriArray.length == 1){
            System.out.println("array长度为1，不能拆分！");
            return null;
        }
        int len = oriArray.length;
        List<int[]> arrList = new ArrayList<>();
        int amount = getArrAmount(oriArray);
        try {
            int[] position = new int[2];
            int[] sortedArray = sortOriArray(oriArray);
            //如果最大的数已经大于或者等于总和的一半，那么将此数单独作为一组
            if(Double.valueOf(sortedArray[len - 1]) >= Double.valueOf(amount)/2){
                int[] arrLeft = new int[1];
                int[] arrRight = new int[len - 1];
                for(int i = 0; i < len - 1; i ++){
                    arrRight[i] = sortedArray[i];
                }
                arrLeft[0] = sortedArray[len - 1];
                arrList.add(arrLeft);
                arrList.add(arrRight);
                position[0] = 0;
                position[1] = len - 2;
                arrList.add(position);
            }else{
                int[] arrLeft = new int[len];
                int[] arrRight = new int[len];
                int diffPre;//上次两边的差值
                int diffNow;//本次两边的差值
                int i = 0;//左边数组的当前下标
                int j = len - 2;//右边数组的当前下标
                int temp;
                for(int k = 0; k < len - 1; k ++){
                    arrRight[k] = sortedArray[k];
                }
                arrLeft[0] = sortedArray[len - 1];
                diffPre = getArrAmount(arrLeft) - getArrAmount(arrRight);
                while(true){
                    temp = j;
                    arrLeft[++i] = arrRight[j--];
                    arrRight[temp] = 0;
                    diffNow = getArrAmount(arrLeft) - getArrAmount(arrRight);
                    if(diffNow > 0){
                        if(Math.abs(diffPre) < diffNow){//回滚上次状况
                            temp = i;
                            arrRight[++j] = arrLeft[i--];
                            arrLeft[temp] = 0;
                        }
                        break;
                    }else{
                        diffPre =  diffNow;
                    }
                }
                arrList.add(arrLeft);
                arrList.add(arrRight);
                position[0] = i;
                position[1] = j;
                arrList.add(position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrList;
    }

    /**
     * 获取数组总和
     * @param oriArray
     * @return
     */
    private static int getArrAmount(int[] oriArray){
        int amount = 0;
        for(int i = 0; i < oriArray.length; i ++){
            amount += oriArray[i];
        }
        return amount;
    }

    /**
     * 排序原数组
     * @return
     */
    public static int[] sortOriArray(int[] oriArray) throws Exception {
        if(oriArray.length == 0){
            return null;
        }
        if(oriArray.length == 1){
            return oriArray;
        }
        int i = 0;
        int j = oriArray.length - 1;
        int x = oriArray[0];
        int temp;
        while(i < j){
            if(oriArray[j] < x){
                while(i < j){
                    if(oriArray[i] > x){
                        oriArray = exchangeIntArrayLocation(oriArray, i, j);
                        break;
                    }
                    i ++;
                }
            }
            if(i == j){
                continue;
            }
            j --;
        }
        //交换0位置和当前i和j相遇的位置的值
        oriArray = exchangeIntArrayLocation(oriArray, 0, i);
        //拆分数组
        List<int[]> arrLR = splitArray(oriArray, i);
        int[] arrLeft = null;
        if(arrLR.get(0) != null){
            arrLeft = sortOriArray(arrLR.get(0));
        }
        int[] arrRight = null;
        if(arrLR.get(1) != null){
            arrRight = sortOriArray(arrLR.get(1));
        }
        int[] finalArray = mergeArray(arrLeft, x, arrRight);
        return finalArray;
    }

    /**
     * 交换int数组i和j位置的值
     * @param array
     * @param i
     * @param j
     * @return
     */
    private static int[] exchangeIntArrayLocation(int[] array, int i, int j){
        int temp;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        return array;
    }

    /**
     * 从指定位置拆分成两个数组
     * @param array
     * @param i
     * @return
     */
    private static List<int[]> splitArray(int[] array, int i) throws Exception {
        List arrLR = new ArrayList();
        if(array.length == 0){
            throw new Exception("splitArray-->array长度为0");
        }
        if(array.length == 1){
            arrLR.add(null);
            arrLR.add(null);
            return arrLR;
        }
        if(i == 0){
            arrLR.add(null);
            int[] arr = new int[array.length - 1];
            for(int j = 0; j < arr.length; j ++){
                arr[j] = array[j + 1];
            }
            arrLR.add(arr);
            return arrLR;
        }
        if(i == array.length - 1){
            int[] arr = new int[array.length - 1];
            for(int j = 0; j < arr.length; j ++){
                arr[j] = array[j];
            }
            arrLR.add(arr);
            arrLR.add(null);
            return arrLR;
        }
        int[] arrLeft = new int[i];
        int[] arrRight = new int[array.length - i - 1];
        for(int j = 0; j < i; j ++){
            arrLeft[j] =  array[j];
        }
        for(int j = 0; j < array.length - i - 1;j ++){
            arrRight[j] = array[j + i + 1];
        }
        arrLR.add(arrLeft);
        arrLR.add(arrRight);
        return arrLR;
    }

    /**
     * 合并数组（左数组，中间元素，右数组）
     * @param arrLeft
     * @param middleNum
     * @param arrRight
     * @return
     */
    private static int[] mergeArray(int[] arrLeft, int middleNum, int[] arrRight){
        int lenLeft;
        int lenRight;
        if(null == arrLeft && null == arrRight){
            int[] arr = new int[1];
            arr[0] = middleNum;
            return arr;
        }
        if(null == arrLeft){
            lenRight = arrRight.length;
            int[] arr = new int[1 + lenRight];
            for(int i = 0; i < arr.length; i ++){
                if(i == 0){
                    arr[i] = middleNum;
                }else{
                    arr[i] = arrRight[i - 1];
                }
            }
            return arr;
        }
        if(null == arrRight){
            lenLeft = arrLeft.length;
            int[] arr = new int[lenLeft + 1];
            for(int i = 0; i < arr.length; i ++){
                if(i == arr.length - 1){
                    arr[i] = middleNum;
                }else{
                    arr[i] = arrLeft[i];
                }
            }
            return arr;
        }
        
        lenLeft = arrLeft.length;
        lenRight = arrRight.length;
        int[] arr = new int[lenLeft + 1 + lenRight];
        for(int i = 0; i < arr.length; i ++){
            if(i < lenLeft){
                arr[i] = arrLeft[i];
                continue;
            }
            if(i > lenLeft){
                arr[i] = arrRight[i - lenLeft - 1];
            }
            if(i == lenLeft){
                arr[i] = middleNum;
            }
        }
        return arr;
    }
}
