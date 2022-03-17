package kjstyle.playground.java.thread;

public class ClassicalJoinSample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            try {
                Thread.sleep(1500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("쓰레드 안에서 awake!!");
        });

        thread.start();

        System.out.println("Thread: " + Thread.currentThread().getName());

        try {
            System.out.println("before join");
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after join");

        System.out.println("finished!!!");

    }
}