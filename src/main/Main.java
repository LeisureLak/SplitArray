package main;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] arr = {2,1,19,7,8,100,30,0,23,44,13,11,104,200,1000};
        int[] sortedArr = SortMinimumDifference.sortOriArray(arr);
        List<int[]> sorted2Arr = SortMinimumDifference.splitArray(arr);
        System.out.print("原数组为：{");
        for(int i = 0 ;i < arr.length; i ++){
            System.out.print(arr[i] + " ");
        }
        System.out.println("}");
        System.out.print("排序后数组为：{");
        for(int i = 0 ;i < sortedArr.length; i ++){
            System.out.print(sortedArr[i] + " ");
        }
        System.out.println("}");
        
        
        int[] arrLeft = sorted2Arr.get(0);
        int[] arrRight = sorted2Arr.get(1);
        int[] position = sorted2Arr.get(2);
        System.out.println("拆分之后的数组为：");
        System.out.print("数组1：{");
        for(int i = 0; i < arrLeft.length; i ++){
            if(i >  position[0]){
                break;
            }
            System.out.print(arrLeft[i]+" ");
        }
        System.out.println("}");
        System.out.print("数组2：{");
        for(int i = 0; i < arrRight.length; i ++){
            if(i >  position[1]){
                break;
            }
            System.out.print(arrRight[i] + " ");
        }
        System.out.println("}");
    }
}
