#spring-ico学习笔记

### 1.ioc容器的实现
##### 1.BeanFactory
```
package us.codecraft.tinyioc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * bean的工厂接口
 * @author zhujie
 *
 */
public interface BeanFactory {
 
 public Object getBean(String name)throws Exception;
 
 public void registerBeanDefinition(String name,BeanDefinition beanDefinition) throws Exception;
}
```










