package us.codecraft.tinyioc;

import us.codecraft.tinyioc.beans.BeanPostProcessor;

public class BeanInitializeLogger implements BeanPostProcessor{

	@Override
	public Object postProcessorBeforeInitialization(Object bean, String beanName) throws Exception {
		System.out.println("Initialize bean "+ beanName +" start!");
		return bean;
	}

	@Override
	public Object postProcessorAfterInitialization(Object bean, String beanName) throws Exception {
		System.out.println("Initialize bean "+beanName+" end!");
		return bean;
	}

}
