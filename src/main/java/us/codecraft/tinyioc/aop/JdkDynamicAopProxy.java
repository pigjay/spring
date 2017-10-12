package us.codecraft.tinyioc.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.aopalliance.intercept.MethodInterceptor;

public class JdkDynamicAopProxy implements AopProxy,InvocationHandler{

	private AdviseSupport advised;
	
	public  JdkDynamicAopProxy(AdviseSupport advised) {
		this.advised = advised;
	}
	
	@Override
	public Object invoke(final Object proxy,final Method method,final Object[] args) throws Throwable {
		MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
		if(advised.getMethodMatcher() != null && advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
			
			return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(),method,args));
		}else {
			return method.invoke(advised.getTargetSource().getTarget(), args);
		}
	}

	@Override
	public Object getProxy() {
		return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
	}

}
