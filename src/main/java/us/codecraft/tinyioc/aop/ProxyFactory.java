package us.codecraft.tinyioc.aop;

/**
 * 代理类工厂类
 *
 * @author zhujie
 */
public class ProxyFactory extends AdvisedSupport implements AopProxy {

    @Override
    public Object getProxy() {
        return createAopProxy().getProxy();
    }

    protected final AopProxy createAopProxy() {
        if (getTargetSource().getInterfaces() != null && getTargetSource().getInterfaces().length > 0) {
            return new JdkDynamicAopProxy(this);
        } else {
            return new Cglib2AopProxy(this);
        }
    }
}
