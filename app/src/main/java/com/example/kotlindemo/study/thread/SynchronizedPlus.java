package com.example.kotlindemo.study.thread;

/**
 * synchronized锁的意义: 锁住的是一个类下的资源(牺牲性能)
 * <p>
 * 同一时间只能被同一个线程访问的原理:
 * 给需要保护的资源设置一个监视器Monitor, 当 Monitor监视到资源被一个线程访问时, 就会阻止第二个线程访问
 * Monitor可以是任意对象, 只作为一个tag的标识, 相同Monitor保护的资源, 也是只允许同一时间被同一个线程访问
 * <p>
 * 线程安全/不安全的处理与意义:
 * 一个类的数据是放在内存中的, 当一个线程去访问一个类里的数据, 是在线程中开出一块cpu高速缓存, 将类中的值复制到高速缓存中,然后进行操作
 * 这样是线程不安全的, 因为可以同时被多个线程这么操作(这么设计 是因为 cpu高速缓存上读写要比内存快的多)
 * 如果加入了 synchronized 关键字, 就会默认关掉线程访问类后会开出的cpu高速缓存, 而直接在内存上读写,
 * 而此时内存上的数据被synchronized 的Monitor保护着, 所以无法被多个线程同时操作读写, 保证了数据同步, 线程安全
 * 所以线程不安全时, 要比加入synchronized后的性能要好
 */
public class SynchronizedPlus {

    private int firstValue = 0;
    private int secondValue = 0;
    private String thirdValue = "";
    private String forthValue = "";
    private final Object intValueMonitor = new Object();
    private final Object strValueMonitor = new Object();


    /**
     * 直接给方法加 synchronized 为简写方法, 即这个方法的Monitor为这个类本身
     * 即这个类里其他加入synchronized的方法, 也同时被监视
     * 当一个线程访问其中一个synchronized方法时, 其他synchronized方法也不可以被访问
     * 应用于: 当访问方法A时, 不希望方法B被访问, 此时就让两个方法拥有相同的Monitor
     */
    public synchronized void setValue(int value) {
        firstValue = value;
        secondValue = value;
    }

    public synchronized void setValueAdd(int value) {
        firstValue = value - 1;
        secondValue = value - 1;
    }

    /**
     * 当下面两个方法设置Monitor为 strValueMonitor 时, 下面两个方法不能被同一线程访问
     * 但是与该类中 Monitor 为其他值的方法不互斥, 可以被不同线程同时访问
     */
    public void setThirdValue(String strValue) {
        synchronized (strValueMonitor) { //当线程执行到这里, 可以通过Monitor知道是否被占用, 如果没有占用就执行, 并且锁住该资源
            thirdValue = strValue;
        }
    }

    public void setForthValue(String strValue) {
        synchronized (strValueMonitor) { //当线程执行到这里, 可以通过Monitor知道是否被占用, 如果没有占用就执行, 并且锁住该资源
            forthValue = strValue;
        }
    }

    /**
     * 静态方法锁的写法
     * 静态方法里只能使用静态变量, 无法取到对象实例
     * 以下前两种写法相同
     */
    public synchronized static void doStatic1() {

    }

    public static void doStatic2() {
        synchronized (SynchronizedPlus.class) {

        }
    }

    public static volatile Object instance; // volatile可以避免未实例化完成之前, 对象还不为null的阶段

    public static Object getSingleInstance() {
        //双重校验+锁实现单例模式
        if (instance != null) { // synchronized很重, 且实例化好以后就不需要锁了, 所以加锁前判断下
            //当实例为空
            synchronized (SynchronizedPlus.class) {
                instance = new Object();
                return instance;
            }
        }
        return instance;
    }

    /**
     * 死锁模型(只有双锁才会出现死锁)
     * 两个线程互相等待对方占用的Monitor
     */

    public void deadLock1(int value1, int value2, String value3, String value4) {
        //线程1开始执行
        synchronized (intValueMonitor) {//此时没有锁, 继续执行
            firstValue = value1;
            secondValue = value2;
            //到达此处, 切换至线程2
            synchronized (strValueMonitor) {//从线程2切换回时, 此时strValueMonitor已被线程2占用, 无法继续执行, 开始等待
                thirdValue = value3;
                forthValue = value4;
            }
        }
    }

    public void deadLock2(int value1, int value2, String value3, String value4) {
        //线程2开始执行
        synchronized (strValueMonitor) { //此时没有锁, 继续执行
            thirdValue = value3;
            forthValue = value4;
            //到达此处, 切换回线程1
            synchronized (intValueMonitor) { //再切换回线程2时, 此时intValueMonitor已被线程1占用, 无法继续执行, 开始等待
                firstValue = value1;
                secondValue = value2;
            }
        }

    }


}
