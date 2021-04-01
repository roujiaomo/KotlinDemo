package com.example.kotlindemo.study.thread;

/**
 * synchronized : 解决了数据同步问题, 且线程是安全的
 */
public class Synchronized {

    public static int firstValue = 0;
    public static int secondValue = 0;

    public static void main(String[] args) {
        /*
         * 切换线程，是可以在任意一个时间点，方法执行点进行切换的
         */
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    setValue(i);
                    setValueByLock(i);
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    setValue(i);
                    setValueByLock(i);
                }
            }
        }.start();
    }


    public static void setValue(int value) {
        firstValue = value; //例如当线程1执行到这里时，此时系统切换到线程2，那么此时两个value值就会不同，会执行if
        secondValue = value;
        if (firstValue != secondValue) {
            System.out.println("firstValue=" + firstValue);
            System.out.println("secondValue=" + secondValue);
        }
    }

    /**
     * 当加上synchronized后，该方法在同一时间只能被一个线程访问
     */
    public synchronized static void setValueByLock(int value) {
        firstValue = value;
        secondValue = value;
        if (firstValue != secondValue) {
            System.out.println("firstValue=" + firstValue);
            System.out.println("secondValue=" + secondValue);
        }
    }
}
