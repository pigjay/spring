package us.codecraft.tinyioc.xml;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.io.ResourceLoader;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;

public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader beanDefinitionReader =new XmlBeanDefinitionReader(new ResourceLoader());
		beanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
		Map<String,BeanDefinition> registry = beanDefinitionReader.getRegistry();
		assertTrue(registry.size()>0);
	}

}
