package us.codecraft.tinyioc.aop;

import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.BeanFactory;

public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor,BeanFactoryAware{

	private AbstractBeanFactory beanFactory;
	
	@Override
	public Object postProcessorBeforeInitialization(Object bean, String beanName) throws Exception {
		return bean;
	}

	@Override
	public Object postProcessorAfterInitialization(Object bean, String beanName) throws Exception {
        if(bean instanceof AspectJExpressionPointcutAdvisor) {
        	return bean;
        }
        List<AspectJExpressionPointcutAdvisor> advisors = beanFactory
        		.getBeansForType(AspectJExpressionPointcutAdvisor.class);
        for(AspectJExpressionPointcutAdvisor advisor : advisors) {
        	if(advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
        		AdviseSupport adviseSupport = new AdviseSupport();
        		adviseSupport.setMethodInterceptor((MethodInterceptor)advisor.getAdvice());
        	    adviseSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
        	    
        	    TargetSource targetSource = new TargetSource(bean, bean.getClass().getInterfaces());
        	    adviseSupport.setTargetSource(targetSource);
        	    
        	    return new JdkDynamicAopProxy(adviseSupport).getProxy();
        	}
        }
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws Exception {
		this.beanFactory = (AbstractBeanFactory)beanFactory;
	}

}
