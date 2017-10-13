package us.codecraft.tinyioc.beans.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.beans.BeanDefinition;

/**
 * bean的容器
 * @author zhujie
 *
 */
public interface BeanFactory {
 
	/**
	 * 从工厂中取出所需的Bean
	 * 在AbstractBeanFactory中
	 * 规定了基本的构造和执行流程:生成bean，在进行一些初始化操作
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Object getBean(String name)throws Exception;
 
}
