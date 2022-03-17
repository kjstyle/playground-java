package kjstyle.playground.java.thread;

public class ThreadWithRunnable {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread : " + Thread.currentThread().getName());
            }
        });
        thread.start();

        System.out.println("Thread : " + Thread.currentThread().getName());

        Thread thread2 = new Thread(() -> System.out.println("Thread : " + Thread.currentThread().getName()));
        thread2.start();

        System.out.println("Thread : " + Thread.currentThread().getName());
    }
}