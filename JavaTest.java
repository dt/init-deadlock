class A { 
    public static A instance = new A();

    public A() {
        System.out.println("A init starting");
        Slowly.sleep();
        System.out.println("A call: " + B.instance.foo());
        System.out.println("A init complete");
    }

    public String foo() { return "A.foo()"; }
}

class B {
    public static B instance = new B();
    
    public B() {
        System.out.println("B init starting");
        Slowly.sleep();
        System.out.println("B call: " + A.instance.foo());
        System.out.println("B init complete");
    }

	public String foo() { return "B.foo()"; }
}

class Slowly {
	public static void sleep() {
		try{
			Thread.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		return;
	}
}

public class JavaTest {
 	public static void main(String[] argv) throws Exception {
 		Thread a = new Thread(){ public void run() { A.instance.foo(); } };
 		Thread b = new Thread(){ public void run() { B.instance.foo(); } };
 		a.start();
 		b.start();

 		a.join();
 		b.join();
 	}
 }