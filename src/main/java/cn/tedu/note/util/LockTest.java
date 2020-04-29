package cn.tedu.note.util;

import cn.tedu.note.lock.KingLock;

import java.util.concurrent.locks.Lock;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/11/6 16:12
 */
public class LockTest {
    private static int num = 0;

    private static Lock lock = new KingLock();

    public static int incrementNum(){
        lock.lock();
        int val;
        try{
            return val = num++;
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        System.out.println("------Now is operating number! ------");
//        for(int i = 0; i < 100; i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("CurrentThread " +Thread.currentThread().getName()+ " compute value is: " + incrementNum());
//                }
//            },"Thread" + i).start();
//        }
//
//        Thread.sleep(3000);
//
//        System.out.println("After compute number's value is: " + num);

        int i = 0;

        i = getNum();

        System.out.println("i=" + i);
    }

    private static int getNum(){
        int[] a = new int[3];
        a[0] = 1;
        a[1] = 2;
        a[2] = 3;
        try {
            a[3] = 4;
            int i = a[4];
        } catch (Exception e){
            System.out.println("运行时异常:");
            e.printStackTrace();
            return 2;
        } finally {
            System.out.println("最终返回结果:");
            return 3;
        }
    }

}
