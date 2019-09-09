package exercise;

public class TestPrinter {

	public static void main(String[] args) {
//		final Printer p = new Printer();

		Thread t1 = new Thread(() -> {
			while (true) {
				Printer.print(1);
			}
		});
		Thread t2 = new Thread(() -> {
			while (true) {
				Printer.print(2);
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException exn) {
			System.out.println("Some thread was interrupted");
		}
	}

}

class Printer {
	public static void print(int i) {
		synchronized (Printer.class) {
			System.out.print("-");
			try {
				Thread.sleep(50);
			} catch (InterruptedException exn) {
			}
			System.out.print("|");
		}
	}
}