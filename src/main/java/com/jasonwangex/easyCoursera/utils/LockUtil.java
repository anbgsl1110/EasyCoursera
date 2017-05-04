package com.jasonwangex.easyCoursera.utils;

import java.util.Stack;

/**
 * Created by wangjz
 * on 17/5/3.
 */
public class LockUtil {

    public static void lock(String lock, Runnable runnable) {
        synchronized (lock.intern()) {
            runnable.run();
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        for (int i = 0; i < 999; i++) {
            new Thread(()-> lock("test_1", () -> stack.push(stack.pop() + 1))).start();
        }
        for (int i = 0; i < 999; i++) {
            new Thread(()-> lock(new String("test_1"), () -> stack.push(stack.pop() + 1))).start();
        }
        Thread.sleep(1000);
    }
}
