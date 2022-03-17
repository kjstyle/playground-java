package kjstyle.playground.java.threadlocal;

public class ThreadLocalTest {
    public static void main(String[] args) {
        Runnable userA = () -> {
            System.out.println("userA thread is running");
        };

        Runnable userB = () -> {
            System.out.println("userB thread is running");
        };

        Thread t1 = new Thread(userA);
        t1.start();
    }
}
