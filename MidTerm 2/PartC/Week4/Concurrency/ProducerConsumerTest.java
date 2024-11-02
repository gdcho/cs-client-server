public class ProducerConsumerTest {
	public static void main(String[] args) {
		CubbyHole c1 = new CubbyHole();
		Producer p11 = new Producer(c1, 1);
		Consumer c11 = new Consumer(c1, 1);
                CubbyHole c2 = new CubbyHole();
		Producer p12 = new Producer(c2, 2);
		Consumer c12 = new Consumer(c2, 2);
		p11.start();
		c11.start();
		p12.start();
		c12.start();
	}
}