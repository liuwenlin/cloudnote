package cn.tedu.note.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/3/26 22:32
 */
public class CycleBarrierLockDemo {

    private static CyclicBarrier cb1 = new CyclicBarrier(2);
    private static CyclicBarrier cb2 = new CyclicBarrier(2);

    public static void main(String[] args){

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("产品经理规划新需求...");
                    cb1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cb1.await();
                    System.out.println("开发人员开发新需求功能...");
                    cb2.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cb2.await();
                    System.out.println("测试人员测试需求功能...");
                } catch (Exception e) {
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
