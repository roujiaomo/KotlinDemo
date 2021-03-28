package com.example.kotlindemo.study.algorithm.comparable.bubble_sort;

/**
 * 冒泡排序
 * 核心：相邻元素逐个比较
 * <p>
 * 原理：比较每个相邻元素的大小，将最大的往一个方向移动(求最大值或最小值)，每一次比较确定一个最大或最小值，为一次冒泡
 * 最差情况：数组里有n个元素，需要经历确定n-1个最大或最小值，所以就会经历 n-1 次冒泡排序，每次冒泡排序经历n -1 ,n -2直到1次
 * 冒泡排序时间复杂度：O(n的平方) 效率较低
 */
public class Bubble {

    public static void main(String[] args) {
        //包装类都实现了Comparable接口
        Integer[] comparable = {3, 2, 1, 7, 6, 5};
        //自己写的冒泡
        for (int i = 0; i < comparable.length - 1; i++) {//最外层需要 n-1次
            for (int r = 0; r < comparable.length - 1 - i; r++) {//每一次冒泡排序后，都少一个比较的元素
                sort(comparable, r, r + 1);
            }
        }
        //简单写法冒泡
        for (int i = comparable.length - 1; i > 0; i--) { //冒泡次数 = 总元素数-1
            for(int r = 0;r < i ; r++){ //每次冒泡要比较的元素比上次少一个
                sort(comparable, r, r + 1);
            }
        }

    }

    /**
     * 将较大的元素与较小的元素替换位置（注意声明临时值的思路）
     * 需要传入集合，以及集合中两个比较的值的索引
     */
    public static void sort(Comparable[] c, int index1, int index2) {
        Comparable temp = c[index1];//储存第一个值
        if (getMax(c[index1], c[index2])) { //第一个值更大
            c[index1] = c[index2];//将第二个值挪到第一个值的位置
            c[index2] = temp;//将第一个值挪到第二个值的位置
        }

    }

    /**
     * 比较两个元素大小
     */
    public static boolean getMax(Comparable c1, Comparable c2) {
        return c1.compareTo(c2) > 0;
    }

}
