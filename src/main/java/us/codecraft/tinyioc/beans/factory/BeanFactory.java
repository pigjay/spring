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
 
 public Object getBean(String name)throws Exception;
 
}
