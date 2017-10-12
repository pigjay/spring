package us.codecraft.tinyioc.beans.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import us.codecraft.tinyioc.beans.AbstractBeanDefinitionReader;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanReference;
import us.codecraft.tinyioc.beans.PropertyValue;
import us.codecraft.tinyioc.beans.io.ResourceLoader;
/**
 * 用于从xml文件中读取配置信息的类
 * @author zhujie
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

	//初始化 资源加载器
	public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
		super(resourceLoader);
	}

	//读取xml配置文件信息
	@Override
	public void loadBeanDefinitions(String location) throws Exception {
		//获取资源输入流
		InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
		//读取配置信息
		doLoadBeanDefinitions(inputStream);
	}
	
	/**
	 * 读取配置信息
	 * @param inputStream
	 * @throws Exception
	 */
	protected void doLoadBeanDefinitions(InputStream inputStream)throws Exception{
		//初始化DocumentBuilderFactory
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		//获取DocumentBuilder
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		//从输入流创建Document
		Document doc = docBuilder.parse(inputStream);
		//解析bean
		registerBeanDefinitions(doc);
		//关闭输入流
		inputStream.close();
	}
	
	/**
	 * 根据Document注册BeanDefinition
	 * @param doc
	 */
	public void registerBeanDefinitions(Document doc) {
		Element root = doc.getDocumentElement();
		
		parseBeanDefinitions(root);
		
	}

	/**
	 * 解析Document
	 * @param root
	 */
	protected void parseBeanDefinitions(Element root) {
		NodeList nl = root.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++) {
			Node node = nl.item(i);
			if(node instanceof Element) {
				Element ele = (Element) node;
				processBeanDefinition(ele);
			}
		}
	}
	
	/**
	 * 生成BeanDefinition
	 * @param ele
	 */
	protected void processBeanDefinition(Element ele) {
		String name = ele.getAttribute("id");
		String className = ele.getAttribute("class");
		BeanDefinition beanDefinition = new BeanDefinition();
		processProperty(ele, beanDefinition);
		beanDefinition.setBeanClassName(className);
		getRegistry().put(name, beanDefinition);
	}
	
	/**
	 * 读取bean属性
	 * @param ele
	 * @param beanDefinition
	 */
	private void processProperty(Element ele,BeanDefinition beanDefinition) {
		NodeList propertyNode = ele.getElementsByTagName("property");
		for(int i = 0; i < propertyNode.getLength(); i++) {
			Node node = propertyNode.item(i);
			if(node instanceof Element) {
				Element propertyEle = (Element) node;
				String name = propertyEle.getAttribute("name");
				String value = propertyEle.getAttribute("value");
				if(value != null && value.length() > 0) {
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
				}else {
					String ref = propertyEle.getAttribute("ref");
					if(ref == null || ref.length() == 0) {
						throw new IllegalArgumentException("Configuration problem :<property> element for property '"+
					name+"' must specify a ref or value");
					}
					BeanReference beanReference = new BeanReference(ref);
					beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
				}
			}
		}
	}
}
