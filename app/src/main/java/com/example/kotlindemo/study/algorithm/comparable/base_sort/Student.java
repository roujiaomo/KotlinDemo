package com.example.kotlindemo.study.algorithm.comparable.base_sort;

public class Student implements Comparable<Student> {

    public Student(int age) {
        this.age = age;
    }

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Student o) {
        return age - o.age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                '}';
    }
}
