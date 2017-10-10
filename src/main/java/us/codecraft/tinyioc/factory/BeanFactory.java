package us.codecraft.tinyioc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * 
 * @author zhujie
 *
 */
public interface BeanFactory {
 
 public Object getBean(String name)throws Exception;
 
 public void registerBeanDefinition(String name,BeanDefinition beanDefinition) throws Exception;
}
