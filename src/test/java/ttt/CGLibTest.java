package ttt;

public class CGLibTest {

	public static void main(String[] args) {

		CGLibProxy cglibProxy = CGLibProxy.getInstance();
		HelloWorldImpl helloWorldImpl = cglibProxy.getProxy(HelloWorldImpl.class);
		//helloWorldImpl.say();
	}

}
