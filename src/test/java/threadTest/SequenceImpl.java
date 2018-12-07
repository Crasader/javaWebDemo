package threadTest;

public class SequenceImpl implements Sequence {

	//线程安全
	/*private static ThreadLocal<Integer> numberContainer = new ThreadLocal<Integer>() {

		@Override
		protected Integer initialValue() {
			return 0;
		}
		
	};*/
	
	private static MyThreadLocal<Integer> numberContainer = new MyThreadLocal<Integer>(){
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};
	/*public int getNumber() {
		number=number+1;
		return number;
	}*/

	public static void main(String[] args) {
		SequenceImpl sequenceImpl = new SequenceImpl();
		ClientThread clientThread1 = new ClientThread(sequenceImpl);
		ClientThread clientThread2 = new ClientThread(sequenceImpl);
		ClientThread clientThread3 = new ClientThread(sequenceImpl);
		
		clientThread1.start();
		clientThread2.start();
		clientThread3.start();
	}

	public int getNumber() {
		numberContainer.set(numberContainer.get()+1);
		return numberContainer.get();
	}
}
