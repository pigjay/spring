package us.codecraft.tinyioc.aop;


import org.junit.Test;

import us.codecraft.tinyioc.HelloWorldHandler;
import us.codecraft.tinyioc.HelloWorldService;
import us.codecraft.tinyioc.HelloWorldServiceImpl;
import us.codecraft.tinyioc.context.ApplicationContext;
import us.codecraft.tinyioc.context.ClassPathXmlApplicationContext;

public class Cglib2AopProxyTest {

	@Test
	public void test() throws Exception {
		// --------- helloWorldService without AOP
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		HelloWorldHandler haha = (HelloWorldHandler) applicationContext.getBean("haha");
		helloWorldService.helloWorld();
		haha.helloWorld();
		
		// --------- helloWorldService with AOP
		//1.设置被代理对象(Joinpoint)

		HelloWorldHandler haha1 = new HelloWorldHandler();
		AdvisedSupport advisedSupport = new AdvisedSupport();
		TargetSource targetSource = new TargetSource(haha1,HelloWorldHandler.class, null);
		advisedSupport.setTargetSource(targetSource);
		
		//2.设置拦截器(advice)
		TimerInterceptor timerInterceptor = new TimerInterceptor();
		advisedSupport.setMethodInterceptor(timerInterceptor);

		//3.设置切点(MethodMatcher)
		String expression = "execution(* us.codecraft.tinyioc.*.helloWorld(..))";
		AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
		aspectJExpressionPointcut.setExpression(expression);
		advisedSupport.setMethodMatcher(aspectJExpressionPointcut);
		
		//3.创建代理对象(Proxy)
		Cglib2AopProxy cglib2AopProxy = new Cglib2AopProxy(advisedSupport);
		HelloWorldHandler helloWorldServiceProxy = (HelloWorldHandler) cglib2AopProxy.getProxy();
		
		//4.基于AOP的调用
		helloWorldServiceProxy.helloWorld();

		System.out.println("start");
		helloWorldServiceProxy.toString1();
		System.out.println("end");
	}

}
