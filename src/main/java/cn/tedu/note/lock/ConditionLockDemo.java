package cn.tedu.note.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/3/26 22:43
 */
public class ConditionLockDemo {

    private static Lock lock= new ReentrantLock();

    private static boolean t1run = false;

    private static boolean t2run = false;

    public static void main(String[] args){

        final Condition c1 = lock.newCondition();
        final Condition c2 = lock.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println("产品经理规划新需求...");
                t1run = true;
                c1.signal();
                lock.unlock();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    if(!t1run){
                        c1.await();
                    }
                    System.out.println("开发人员开发新需求功能...");
                    t2run = true;
                    c2.signal();
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    if(!t2run){
                        c2.await();
                    }
                    System.out.println("测试人员测试需求功能...");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        System.out.println("-------Start Sequence-------");
        t3.start();
        t1.start();
        t2.start();
    }

}
