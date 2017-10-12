package us.codecraft.tinyioc.beans;

import java.util.HashMap;
import java.util.Map;

import us.codecraft.tinyioc.beans.io.ResourceLoader;


/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    //存放beanDefinition的键值对
	private Map<String,BeanDefinition> registry;
	
	//资源加载器
	private ResourceLoader resourceLoader;
	
	protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
		this.registry = new HashMap<String, BeanDefinition>();
		this.resourceLoader = resourceLoader;
	}

	public Map<String, BeanDefinition> getRegistry() {
		return registry;
	}

	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
	
	
}
