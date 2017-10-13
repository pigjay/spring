package us.codecraft.tinyioc.aop;

import org.aopalliance.aop.Advice;
/**
 * AspectJ切点通知器
 * @author zhujie
 *
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor{

	private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	
	/**
	 * 通知对象
	 */
	private Advice advice;
	
	
	
	public void setPointcut(AspectJExpressionPointcut pointcut) {
		this.pointcut = pointcut;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	@Override
	public Advice getAdvice() {
		return advice;
	}

	@Override
	public Pointcut getPointcut() {
		return pointcut;
	}

	public void setExpression(String expression) {
		this.pointcut.setExpression(expression);
	}
}
