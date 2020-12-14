package kjstyle.playground.java.thread;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamForkJoinPool {

    public static final int THREAD_SLEEP_MILLISEC = 3;
    public static final int MORE_THREAD = 30;
    public static final int LESS_THREAD = 2;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test();
    }

    private static void test() throws ExecutionException, InterruptedException {
        List<Integer> intList = IntStream.range(1,100).boxed().collect(Collectors.toList());

        System.out.println("normal stream!!");
        long startTime = System.currentTimeMillis();

        intList.stream().forEach((i) -> {
            System.out.println("Thread : " + Thread.currentThread().getName() + ", '"+i+"'");
            try {
                Thread.sleep(THREAD_SLEEP_MILLISEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long endTime = System.currentTimeMillis();
        System.out.println("It takes ["+(endTime - startTime)+"]millisec elapsed with normal stream");

        System.out.println("common forkJoinPool");
        startTime = System.currentTimeMillis();

        intList.parallelStream().forEach((i) -> {
            System.out.println("Thread : " + Thread.currentThread().getName() + ", '"+i+"'");
            try {
                Thread.sleep(THREAD_SLEEP_MILLISEC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        endTime = System.currentTimeMillis();
        System.out.println("It takes ["+(endTime - startTime)+"]millisec elapsed in jvm common thread pool");

        System.out.println("==============================");


        ForkJoinPool lessForkJoinPool = new ForkJoinPool(LESS_THREAD);
        System.out.println("ForkJoinPool("+ LESS_THREAD +")");
        startTime = System.currentTimeMillis();
        lessForkJoinPool.submit(() -> {
            intList.parallelStream().forEach((i) -> {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", '"+i+"'");
                try {
                    Thread.sleep(THREAD_SLEEP_MILLISEC);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).get();
        endTime = System.currentTimeMillis();
        System.out.println("It takes ["+(endTime - startTime)+"]millisec elapsed in "+ LESS_THREAD +" thread pool");

        System.out.println("==============================");
        ForkJoinPool moreForkJoinPool = new ForkJoinPool(MORE_THREAD);
        System.out.println("ForkJoinPool("+ MORE_THREAD +")");
        startTime = System.currentTimeMillis();
        moreForkJoinPool.submit(() -> {
            intList.parallelStream().forEach((i) -> {
                System.out.println("Thread : " + Thread.currentThread().getName() + ", '"+i+"'");
                try {
                    Thread.sleep(THREAD_SLEEP_MILLISEC);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).get();
        endTime = System.currentTimeMillis();
        System.out.println("It takes ["+(endTime - startTime)+"]millisec elapsed in "+ MORE_THREAD +" thread pool");
    }
}
