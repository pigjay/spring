package us.codecraft.tinyioc.aop;

import us.codecraft.tinyioc.beans.factory.BeanFactory;

public interface BeanFactoryAware {

	void setBeanFactory(BeanFactory beanFactory)throws Exception;
}
