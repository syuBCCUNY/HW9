/*citations: http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/
https://stackoverflow.com/questions/25073997/sequential-execution-of-threads-using-synchronized
https://codereview.stackexchange.com/questions/51486/sequential-thread-execution-using-wait-and-notifyall
http://javabypatel.blogspot.in/2017/06/running-threads-in-sequence-in-java.html
http://www.devinline.com/2016/01/print-pattern-1-2-3-using-three-threads.html
Textbook chapter 30 multithreading and parallel programming
*/

public class HW9v1{

public static void main(String[] args) throws Throwable{
		Lock lock = new Lock();
		
		ThreadA a = new ThreadA(lock);
		ThreadB b = new ThreadB(lock);
		ThreadC c = new ThreadC(lock);
		ThreadD d = new ThreadD(lock);
		
		a.start();
		b.start();
		c.start();
		d.start();				
		
	}
}


class ThreadA extends Thread{
	
	private String[] arrA = {"A", "E", "I", "M", "Q", "U", "Y"};
	
	Lock lock;
	
	ThreadA(Lock lock){
		this.lock = lock;
	}

	public void run(){
		try{
			synchronized (lock){
				
				for(int i = 0; i < arrA.length; i++){
					while (lock.turn != 1){
						lock.wait();
					}	
					System.out.print(arrA[i] + " ");
					lock.turn = 2;
					lock.notifyAll();
				}	

			}
		}
		catch (Exception ex){
		}
	}
}

class ThreadB extends Thread{
	private String[] arrB ={"B", "F", "J", "N", "R", "V", "Z"};
	
	Lock lock;
	
	ThreadB(Lock lock){
		this.lock = lock;
	}

	public void run(){
		try{
			synchronized (lock){
				
				for(int i = 0; i < arrB.length; i++){
					while (lock.turn != 2){
						lock.wait();
					}					
					System.out.print(arrB[i] + " ");					
					lock.turn = 3;					
					lock.notifyAll();
				}	

			}
		} 
		catch (Exception ex){
		}	
	}
}

class ThreadC extends Thread{
	private String[] arrC ={"C", "G", "K", "O", "S", "W"};
	
	Lock lock;
	
	ThreadC(Lock lock){
		this.lock = lock;
	}

	public void run(){
		try{
			synchronized (lock){
				
				for(int i=0; i <arrC.length; i++){
					while (lock.turn != 3){
						lock.wait();
					}					
					System.out.print(arrC[i] + " ");
					lock.turn = 4;
					lock.notifyAll();
				}

			}
		}
		catch (Exception ex){
		}
	}
}

class ThreadD extends Thread{
	private String[] arrD = {"D", "H", "L", "P", "T", "X"};
	
	Lock lock;
	
	ThreadD(Lock lock){
		this.lock = lock;
	}

	public void run(){
		try{
			synchronized (lock){
				
				for(int i=0; i <arrD.length; i++){
					while (lock.turn != 4){
						lock.wait();
					}
					System.out.print(arrD[i] + " ");
					lock.turn = 1;
					lock.notifyAll();
				}
			
			}
		} 
		catch (Exception ex){	
		}
	}
}

class Lock{
	public int turn = 1;
}