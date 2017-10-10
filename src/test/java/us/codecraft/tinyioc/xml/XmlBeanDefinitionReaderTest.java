package us.codecraft.tinyioc.xml;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import us.codecraft.tinyioc.BeanDefinition;
import us.codecraft.tinyioc.io.ResourceLoader;

public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader beanDefinitionReader =new XmlBeanDefinitionReader(new ResourceLoader());
		beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		Map<String,BeanDefinition> registry = beanDefinitionReader.getRegistry();
		assertTrue(registry.size()>0);
	}

}
