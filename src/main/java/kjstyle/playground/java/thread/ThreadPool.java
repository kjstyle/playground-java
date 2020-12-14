package kjstyle.playground.java.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * newFixedThreadPool 에 대한 study
 execute()와 submit() 메소드의 차이점은 두 가지 입니다.
 - execute()는 작업 처리 결과를 받지 못하지만, submit()은 작업 처리 결과를 Future 타입으로 리턴합니다.
 - execute()는 작업 처리 중 예외가 발생하면 스레드가 종료되고 해당 스레드는 스레드 풀에서 제거되고, 스레드 풀은 다른 작업 처리를 위해 새로운 스레드를 생성합니다.
 - submit()은 작업 처리 중 예외가 발생하면 스레드는 종료되지 않고 다음 작업을 위해 재사용 됩니다.
 출처: http://palpit.tistory.com/732 [palpit Vlog]
 */
public class ThreadPool {

	public static void main(String[] args) {
		executeMethodTest();
//		submitMethodTest();
	}

	public static void executeMethodTest() {
		// 쓰레드풀에 쓰레드 10마리를 풀어둔다
		ExecutorService executor = Executors.newFixedThreadPool(10);

		// 10번 반복해서 쓰레드풀의 쓰레드들에게 일을 시키는데 (쓰레드풀 안에 하나가 선택되어 일처리를 할 것임)
		IntStream.range(0,10).forEach(i -> executor.execute( () -> { // execute메소드를 실행하는데 리턴이 void라 일만처리하고 처리보고는 하지 않음
			try {
				TimeUnit.MILLISECONDS.sleep(300); // 300밀리초를 쉬고
				String threadName = Thread.currentThread().getName();
				System.out.println("current thread name is " + threadName); // 현재 쓰레드 이름을 출력한다.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
		executor.shutdown();
	}

	public static void submitMethodTest() {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		final List<Integer> integerList = Arrays.asList(1,2,3,4,5);
		System.out.println("before submit");

		// Future는 작업결과가 아닌, 작업이 완료될 때까지 기다렸다가 최종 결과를 얻는데 사용하는 놈
		/*
		Future를 이용한 블로킹 방식의 작업 완료 통보에서 주의할 점은 작업을 처리하는 스레드가 작업을 완료하기 전까지는
		get() 메소드가 블로킹되므로 다른 코드를 실행할 수 없습니다.
		다른 코드를 하는 스레드가 get() 메소드를 호출하면 작업을 완료하기 전까지 다른 코드를 처리할 수 없게 됩니다.
		그렇기 때문에 get() 메소드를 호출하는 스레드는 새로운 스레드이거나 스레드 풀의 또 다른 스레드가 되어야 합니다.
		출처: http://palpit.tistory.com/732 [palpit Vlog]
		*/
		Future<Integer> future = executor.submit( () -> { // submit으로 일을 시키면 Future를 리턴한다.
			TimeUnit.MILLISECONDS.sleep(5000); // 5000밀리초를 기다렸다가
			int result = integerList.stream().mapToInt(i -> i.intValue()).sum(); // 1~5 사이의 숫자들의 합을 구해서
			return result; // 합을 리턴
		});
		System.out.println("after submit");

		try {
			System.out.println("before future.get()");
			Integer result = future.get(); // get메소드는 블럭된다
			System.out.println("after future.get()");
			System.out.println("result is " + result);
			executor.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
