package us.codecraft.tinyioc.context;


import java.lang.reflect.Method;

import org.junit.Test;

import us.codecraft.tinyioc.HelloWorldService;
import us.codecraft.tinyioc.HelloWorldServiceImpl;
import us.codecraft.tinyioc.OutputService;
import us.codecraft.tinyioc.OutputServiceImpl;

public class ApplicationContextTest {

	@Test
	public void test() throws Exception{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}
	
	@Test
	public void test1() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc-postbeanprocessor.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();
		
	}
	
	@Test
	public void test2() throws ClassNotFoundException, NoSuchMethodException, SecurityException {
		Class clazz = Class.forName("us.codecraft.tinyioc.HelloWorldServiceImpl");
		Method method = clazz.getMethod("setOutputService", OutputService.class);
	}

}
