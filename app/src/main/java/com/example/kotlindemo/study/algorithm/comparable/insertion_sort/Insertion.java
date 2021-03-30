package com.example.kotlindemo.study.algorithm.comparable.insertion_sort;

/**
 * 插入排序: 类似小时候打扑克抓牌,从小到大插入排列
 * 核心: 从小到大排列时, 当未排序的元素与排序后最大的元素相比仍然大时, 最大元素之前的元素就可以不再比较
 * 原理: 默认第一个值为排序后的元素(就像抓扑克的第一张牌), 以此后续元素进行比较,比较点:未排序的每个元素与每个已排序的元素倒序依次比较
 * 最差情况: 每个未排序相比排序的元素, 都是最小的(是一种变相的冒泡排序), 最后时间复杂度为O(N的平方)
 */
public class Insertion {

    public static void main(String[] args) {

        Integer[] integers = {6, 5, 4, 3, 2, 1};

        for (int i = 0; i < integers.length - 1; i++) {//外层思考遍历几次
            for (int j = i + 1; j > 0; j--) {
                if (getMax(integers[j - 1], integers[j])) {
                    sort(integers, j - 1, j);
                } else {
                    break;
                }
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
        c[index1] = c[index2];
        c[index2] = temp;

    }

}
