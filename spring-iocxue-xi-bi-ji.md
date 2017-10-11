#spring-ico学习笔记

### 1.ioc容器的实现
##### 1.BeanDefinitioin
* 以 BeanDefinition 类为核心发散出的几个类，都是用于解决 Bean 的具体定义问题，包括 Bean 的名字是什么、它的类型是什么，它的属性赋予了哪些值或者引用，也就是 如何在 IoC 容器中定义一个 Bean，使得 IoC 容器可以根据这个定义来生成实例 的问题。

```

package us.codecraft.tinyioc;
/**
 * 该类保存了 Bean 定义。
 * 包括 Bean 的 名字 String beanClassName、
 * 类型 Class beanClass、属性 PropertyValues propertyValues。
 * 根据其 类型 可以生成一个类实例，然后可以把 属性 注入进去。
 * propertyValues 里面包含了一个个 PropertyValue 条目，
 * 每个条目都是键值对 String - Object，分别对应要生成实例的属性的名字与类型。
 * 在 Spring 的 XML 中的 property 中，键是 key ，值是 value 或者 ref。
 * 对于 value 只要直接注入属性就行了，但是 ref 要先进行解析。
 * Object 如果是 BeanReference 类型，则说明其是一个引用，
 * 其中保存了引用的名字，需要用先进行解析，转化为对应的实际 Object。
 * @author zhujie
 *
 */
public class BeanDefinition {

	//存放bean
	private Object bean;
	
	//存放bean的Class
	private Class beanClass;
	
	//存放bean的ClassName
	private String beanClassName;
	
	//存放bean的属性信息
	private PropertyValues propertyValues = new PropertyValues();
	
	public BeanDefinition() {
		
	}
	set/get方法...
	
	//beanClassName的set方法，同时对beanClass属性进行初始化
	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
}



```

##### 1.BeanFactory
* 接口，标识一个 IoC 容器。通过 getBean(String) 方法来 获取一个对象

```
package us.codecraft.tinyioc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * bean的工厂接口,标识一个 IoC 容器。通过 getBean(String) 方法来 获取一个对象
 * @author zhujie
 *
 */
public interface BeanFactory {
 
 //获取bean的方法
 public Object getBean(String name)throws Exception;
 
 //注册bean的方法
 public void registerBeanDefinition(String name,BeanDefinition beanDefinition) throws Exception;
}
```










