package ttt;

public class Test {

	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorldImpl();
		DynamicProxy dynamicProxy = new DynamicProxy(helloWorld);
		HelloWorld helloWorldProxy = dynamicProxy.getProxy();
		helloWorldProxy.say();
	}

}

