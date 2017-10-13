package us.codecraft.tinyioc.aop;
/**
 * AOP代理抽象类
 * @author zhujie
 *
 */
public abstract class AbstractAopProxy implements AopProxy{

	protected AdvisedSupport advised;
	
	public AbstractAopProxy(AdvisedSupport advised) {
		this.advised = advised;
	}
	
	
}
