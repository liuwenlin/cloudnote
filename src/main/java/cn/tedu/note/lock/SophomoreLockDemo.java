package cn.tedu.note.lock;

import java.util.concurrent.Semaphore;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/3/26 22:02
 */
public class SophomoreLockDemo {


    private static Semaphore s1 = new Semaphore(1);

    private static Semaphore s2 = new Semaphore(1);

    private static Semaphore s3 = new Semaphore(0);

    public static void main(String[] args){
        final Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("产品经理规划新需求...");
                s1.release();
            }
        });

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s1.acquire();
                    t1.join();
                    System.out.println("开发人员开发新需求功能...");
                    s2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    s2.acquire();
                    t2.join();
                    System.out.println("测试人员测试需求功能...");
                    //s2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("-------Start Sequence-------");
        t2.start();
        t3.start();
        t1.start();
    }

}
