package com.wcd.javabase.step01.a上下文切换.a多线程一定快吗;

/**
 * 结论：
 * 1亿次以前serial耗时<concurrency耗时,最终趋近于serial耗时=concurrency耗时
 * 10亿次开始，serial耗时>concurrency耗时,最终趋近于serial耗时=2倍concurrency耗时（新线程的作用体现出来了）
 * ---------------------------------------------
 * count：循环次数  concurrency：并发(一个主线程+一个线程) serial：序列化（一个主线程）
 * ----------------------------------------------
 * count：10000 | concurrency：24ms | b=-10000
 * count：10000 | serial：0ms | b=-10000
 * ----------------------------------------------
 * count：100000 | concurrency：25ms | b=-100000
 * count：100000 | serial：1ms | b=-100000
 * ----------------------------------------------
 * count：1000000 | concurrency：28ms | b=-1000000
 * count：1000000 | serial：2ms | b=-1000000
 * ----------------------------------------------
 * count：10000000 | concurrency：31ms | b=-10000000
 * count：10000000 | serial：7ms | b=-10000000
 * ----------------------------------------------
 * count：100000000 | concurrency：54ms | b=-100000000
 * count：100000000 | serial：53ms | b=-100000000
 * ----------------------------------------------
 * count：1000000000 | concurrency：280ms | b=-1000000000
 * count：1000000000 | serial：504ms | b=-1000000000
 * ----------------------------------------------
 * count：10000000000 | concurrency：2508ms | b=-1410065408
 * count：10000000000 | serial：4688ms | b=-1410065408
 * ----------------------------------------------
 * count：100000000000 | concurrency：23838ms | b=-1215752192
 * count：100000000000 | serial：46126ms | b=-1215752192
 */
public class ConcurrencyTest {
    //count：循环次数
    private static final long count = 100000000000L;

    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    private static void concurrency() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread thread = new Thread(() -> {
            int a = 0;
            for (long i = 0; i < count; i++) {
                a += 5;
            }
        });
        thread.start();
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        thread.join();
        System.out.println("count：" + count + " | concurrency：" + time + "ms | b=" + b);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("count：" + count + " | serial：" + time + "ms | b=" + b);
    }
}
