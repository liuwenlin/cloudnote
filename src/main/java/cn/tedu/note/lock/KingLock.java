package cn.tedu.note.lock;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author liuwenlin
 * @version v1.0
 * @date 2019/11/6 16:15
 */
public class KingLock implements Lock {

    private AtomicReference<Thread> owner = new AtomicReference<>();

    private BlockingDeque<Thread> waiter = new LinkedBlockingDeque<>();

    @Override
    public void lock() {
        while(!owner.compareAndSet(null,Thread.currentThread())){
            waiter.add(Thread.currentThread());
            LockSupport.park();

            waiter.remove(Thread.currentThread());
        }
    }

    @Override
    public void unlock() {
        if(owner.compareAndSet(Thread.currentThread(),null)){
            Object[] objects = waiter.toArray();
            for(Object object:objects){
                Thread thread = (Thread) object;
                LockSupport.unpark(thread);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
