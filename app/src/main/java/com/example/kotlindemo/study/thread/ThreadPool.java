package com.example.kotlindemo.study.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池：区别于新建线程的好处是，可以复用线程，并且有回收机制
 *
 * 线程池的大小设置与CPU核数设置关联，是为了性能在不同设备上有同样的流畅度
 *
 * ExecutorService 为 Executor的拓展，里面有控制线程操作的方法
 */
public class ThreadPool {


    public static void main(String[] args) {
        Runnable runnable = () -> {
            //do task
        };

        //通用
        //线程数为0 ~ max，即需要使用就创建给你，不需要当超过keepAlive时间后就回收
        //线程数的最小值为一直存在的线程，如果为0，则不存在
        Executor cacheThreadPool = Executors.newCachedThreadPool();
        cacheThreadPool.execute(runnable);

        //线程数恒定为1，即会先创建好一个线程，且始终维持着这一个线程在工作,不常用
        Executor singleThreadPool = Executors.newSingleThreadExecutor();
        singleThreadPool.execute(runnable);

        //线程数恒定为传入的参数，应用于集中处理多个任务，为了节省时间，比如同时下载多个图片
        //20个线程并行执行该任务
        ExecutorService fixThreadPool = Executors.newFixedThreadPool(20);
        fixThreadPool.execute(runnable);
        fixThreadPool.shutdown();//注意使用完立即切断

        // TODO: 3/28/21
        //app 统一线程池与RxJava(单例全局)

    }

}
