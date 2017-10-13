package us.codecraft.tinyioc.context;

import java.util.Map;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.io.ResourceLoader;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;
/**
 * 从类路径加载资源的具体实现类。
 * 内部通过 XmlBeanDefinitionReader 
 * 解析 UrlResourceLoader 
 * 读取到的 Resource，
 * 获取 BeanDefinition 信息，
 * 然后将其保存到内置的 BeanFactory 中。
 * @author zhujie
 *
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext{

	/**
	 * xml资源路径
	 */
	private String configLocation;
	
	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation,new AutowireCapableBeanFactory());
	}
	
	public ClassPathXmlApplicationContext(String configLocation,AbstractBeanFactory beanFactory) throws Exception {
		super(beanFactory);
		this.configLocation  = configLocation;
		refresh();
	}

	@Override
	protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception{
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
	    xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
	    for(Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
	    	beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
	    }
	}

		

}
