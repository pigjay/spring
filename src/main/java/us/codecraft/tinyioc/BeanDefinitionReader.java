package us.codecraft.tinyioc;
/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public interface BeanDefinitionReader {

	//从一个地址加载类定义
	void loadBeanDefinitions(String location)throws Exception;
}
