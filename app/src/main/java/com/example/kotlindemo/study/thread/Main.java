package com.example.kotlindemo.study.thread;

/**
 * 进程和线程区别：类似家庭和家庭成员的区别，一个家庭里可以有很多个家庭成员同时做事，而每个家庭成员就是一个线程
 *
 * 线程分为Cpu线程和操作系统线程
 * Cpu线程：正常4核为4个Cpu线程
 * 操作系统线程：模拟了Cpu线程，可以有很多个
 *
 * 线程模型：一次总会运行完成的任务(刻意死循环除外)，例如一个线程内，执行的任务总会执行完毕。Android的UI线程
 * 不会结束是因为，他内部也是重复着死循环。
 *
 *
 */
public class Main {

    public static void main(String[] args) {
        //基础方法 创建thread
        Thread thread = new Thread(){
            @Override
            public void run() {
                //内部调用target.run
                super.run();

            }
        };
        //主线程调用，系统会调用内部的run()
        thread.start();
    }

}
