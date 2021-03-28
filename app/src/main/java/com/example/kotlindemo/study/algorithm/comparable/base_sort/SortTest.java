package com.example.kotlindemo.study.algorithm.comparable.base_sort;

/**
 * 给对象排序
 */
public class SortTest {

    public static void main(String[] args) {
        Student student1 = new Student(10);
        Student student2 = new Student(11);
        Comparable max = getMax(student1, student2);
        System.out.print(max.toString());
    }

    public static Comparable getMax(Comparable c1, Comparable c2) {
        int result = c1.compareTo(c2);
        if (result > 0) {
            return c1;
        } else return c2;
    }

}
