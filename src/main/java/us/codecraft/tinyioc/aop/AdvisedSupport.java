package us.codecraft.tinyioc.aop;
import org.aopalliance.intercept.MethodInterceptor;
/**
 * 代理相关的元数据
 * @author zhujie
 *
 */
public class AdvisedSupport {

	/**
	 * 被代理对象
	 */
	private TargetSource targetSource;
	
	/**
	 * AOP在切点处指定的逻辑
	 */
	private MethodInterceptor methodInterceptor;
	
	/**
	 * 切点
	 */
	private MethodMatcher methodMatcher;

	public TargetSource getTargetSource() {
		return targetSource;
	}

	public void setTargetSource(TargetSource targetSource) {
		this.targetSource = targetSource;
	}

	public MethodInterceptor getMethodInterceptor() {
		return methodInterceptor;
	}

	public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
		this.methodInterceptor = methodInterceptor;
	}

	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

	public void setMethodMatcher(MethodMatcher methodMatcher) {
		this.methodMatcher = methodMatcher;
	}
	
	
}
