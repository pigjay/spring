package us.codecraft.tinyioc.aop;

import org.junit.Test;

import us.codecraft.tinyioc.HelloWorldService;
import us.codecraft.tinyioc.context.ApplicationContext;
import us.codecraft.tinyioc.context.ClassPathXmlApplicationContext;

public class JdkDynamicAopProxyTest {

	@Test
	public void test() throws Exception {
		// --------- helloWorldService without AOP
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tinyioc-postbeanprocessor.xml");
		HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
		helloWorldService.helloWorld();
		
		// --------- helloWorldService with AOP
		//1.设置代理对象(Joinpoint)
		AdviseSupport adviseSupport = new AdviseSupport();
		TargetSource targetSource = new TargetSource(helloWorldService, HelloWorldService.class);
		adviseSupport.setTargetSource(targetSource);
		
		//2.设置拦截器(Advice)
		TimerInterceptor timerInterceptor = new TimerInterceptor();
		adviseSupport.setMethodInterceptor(timerInterceptor);
		
		//3.设置切点(MethodMatcher)
        String expression = "execution(* us.codecraft.tinyioc.*.*(..))";
        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(expression);
        adviseSupport.setMethodMatcher(aspectJExpressionPointcut);
		
		//3.创建代理(Proxy)
		JdkDynamicAopProxy jdkDynamicAopProxy = new JdkDynamicAopProxy(adviseSupport);
		HelloWorldService helloWorldServiceProxy = (HelloWorldService) jdkDynamicAopProxy.getProxy();
		
		//4.基于AOP的调用
		helloWorldServiceProxy.helloWorld();
	}

}
