package us.codecraft.tinyioc;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.factory.BeanFactory;
import us.codecraft.tinyioc.beans.io.ResourceLoader;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;

public class BeanFactoryTest {

/*   public void helloWorldTest() {
	   //1.初始化beanfactory
		BeanFactory beanFactory = new BeanFactory();
		
		//2.注入bean
		BeanDefinition beanDefinition = new BeanDefinition(new HelloWorldService());
		beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);
		
		//3.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
   }*/
	
/*	@Test
	public void test()throws Exception {
		//1.初始化beanfactory
		BeanFactory beanFactory = new AutowireCapableBeanFactory();
		
		//2.注入bean
		BeanDefinition beanDefinition = new BeanDefinition();
		beanDefinition.setBeanClassName("us.codecraft.tinyioc.HelloWorldService");
		beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);
		
		//3.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}*/
	
/*	@Test
	public void test()throws Exception{
		//1.初始化beanfactory
		BeanFactory beanFactory =new AutowireCapableBeanFactory();
		
		//2.bean定义
		BeanDefinition beanDefinition =new BeanDefinition();
		beanDefinition.setBeanClassName("us.codecraft.tinyioc.HelloWorldService");
		
		//3.设置属性值
		PropertyValues propertyValues = new PropertyValues();
		propertyValues.addPropertyValue(new PropertyValue("text", "Hello world"));
		beanDefinition.setPropertyValues(propertyValues);
		
		//4.生成bean
		beanFactory.registerBeanDefinition("helloWorldService", beanDefinition);
		
		//5.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}*/
	
/*	@Test
	public void test() throws Exception{
		//1.读取配置
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		
		//2.初始化BeanFactory并注册bean
		BeanFactory beanFactory = new AutowireCapableBeanFactory();
		for(Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
		}
		
		//3.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}*/
	
	@Test
	public void testLazy()throws Exception{
		//1.读取配置
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		
		//2.初始化BeanFactory并注册bean
		AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
		    beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
	    }
		
		//3.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}

	@Test
	public void testPreInstantiate()throws Exception{
 		// 1.读取配置
  		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
  		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
  		
 		// 2.初始化BeanFactory并注册bean
 		AbstractBeanFactory beanFactory = new AutowireCapableBeanFactory();
  		for (Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()) {
  			beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
  		}
  		// 3.初始化bean
  		beanFactory.preInstantiateSingletons();
  		 
  	    // 4.获取bean
  		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
  		helloWorldService.helloWorld();
	}
	
}
