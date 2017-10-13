package us.codecraft.tinyioc.aop;
/**
 * 代理类工厂类
 * @author zhujie
 *
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy{

	@Override
	public Object getProxy() {
		return createAopProxy().getProxy();
	}

	protected final AopProxy createAopProxy() {
		return new Cglib2AopProxy(this);
	}
}
