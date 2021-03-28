package com.example.kotlindemo.study.algorithm.comparable.selection_sort;

/**
 * 选择排序：一种更直观简单的排序方法（这里以最终结果升序为例）
 * 核心：每次选择第一个元素与集合内其他元素比，求最小索引，将最小索引处的值赋值给第一个索引
 * <p>
 * 原理：
 * 1.每次遍历都假定第一个索引处(最小值后的)的元素是最小值的索引，逐个比较，当集合内其他索引处(例：index = 10)元素的值小于第一个索引的值，
 * 则此时最小值的索引为10。
 * 2.替换第一个索引处和最小值索引处的值。
 * <p>
 * 最坏情况：因为每次都要找一个最小值出来，当元素个数为n时，需要选出n-1个最小值，即最外层需要n-1次选择排序
 */
public class Selection {

    public static void main(String[] args) {
        Integer[] integers = {9, 8, 7, 6, 5, 4, 3, 2, 1};

        for (int i = 0; i < integers.length - 1; i++) {//每次选出来一个最小的，还有n-1个元素要和第一个索引比较
            for (int r = i; r < integers.length - 1; r++) { //假设最小索引从0->1...n-1
                sort(integers, i, r + 1);
            }
        }
        for (int i = 0; i < integers.length; i++) {
            System.out.println(integers[i]);
        }

    }


    /**
     * 比较两个元素大小
     */
    public static boolean getMax(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) > 0;
    }

    /**
     * 临时值存的是首先要被赋值的元素
     * 需要传入集合，以及集合中两个比较的值的索引
     */
    public static void sort(Comparable[] c, int index1, int index2) {

        Comparable temp = c[index1];//存储第一次的值
        if (getMax(c[index1], c[index2])) { //第二个值更小
            c[index1] = c[index2];
            c[index2] = temp;
        }

    }
}
