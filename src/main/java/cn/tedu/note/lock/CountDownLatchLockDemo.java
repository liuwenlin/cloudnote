package cn.tedu.note.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/3/26 22:58
 */
public class CountDownLatchLockDemo {
    private static CountDownLatch cd1 = new CountDownLatch(1);
    private static CountDownLatch cd2 = new CountDownLatch(1);

    public static void main(String[] args){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("产品经理规划新需求...");
                cd1.countDown();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cd1.await();
                    System.out.println("开发人员开发新需求功能...");
                    cd2.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cd2.await();
                    System.out.println("测试人员测试需求功能...");
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
