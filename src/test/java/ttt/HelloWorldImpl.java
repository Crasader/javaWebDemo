package ttt;

public class HelloWorldImpl implements HelloWorld {

	public String say() {
		System.out.println("helloworld");
		return null;
	}
	
	public void goodMorning(String name) {
		System.out.println("goodMorning"+name);
	}
	
	public void goodNight(String name) {
		System.out.println("goodnight"+name);
	}
}
