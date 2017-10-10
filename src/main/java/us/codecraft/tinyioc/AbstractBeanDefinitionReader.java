package us.codecraft.tinyioc;

import java.util.HashMap;
import java.util.Map;

import us.codecraft.tinyioc.io.ResourceLoader;

/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

	private Map<String,BeanDefinition> registry;
	
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
