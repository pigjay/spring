package us.codecraft.tinyioc.context;
import java.util.List;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
/**
 * 
 * @author zhujie
 *
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

	protected AbstractBeanFactory beanFactory;
	
	public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
	
	public void refresh() throws Exception{
		loadBeanDefinitions(beanFactory);
		registerBeanPostProcessors(beanFactory);
		onRefresh();
	}

	protected abstract void loadBeanDefinitions(AbstractBeanFactory beanFactory)throws Exception;
	
	protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory)throws Exception{
		List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
		for(Object beanPostProcessor : beanPostProcessors) {
			beanFactory.addPostProcessor((BeanPostProcessor)beanPostProcessor);
		}
	}
	
	protected void onRefresh()throws Exception{
		beanFactory.preInstantiateSingletons();
	}
	
	@Override
	public Object getBean(String name) throws Exception {
		return beanFactory.getBean(name);
	}
	
	
}