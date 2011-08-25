class A { 
	public static int x = Slowly.get(B.y+2);
	public static void foo() { System.out.println("A foo() "+B.y); }
}

class B {
	public static int y = Slowly.get(A.x+1);
	public static void foo() { System.out.println("B foo() "+A.x); }
}

class Slowly {
	public static int get(int x) {
		try{
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
		return x;
	}
}

public class JavaTest {
 	public static void main(String[] argv) throws Exception {
 		Thread a = new Thread(){ public void run() { A.foo(); } };
 		Thread b = new Thread(){ public void run() { B.foo(); } };
 		a.start();
 		b.start();

 		a.join();
 		b.join();
 	}
 }