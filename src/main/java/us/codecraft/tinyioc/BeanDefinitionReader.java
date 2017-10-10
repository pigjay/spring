package us.codecraft.tinyioc;
/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public interface BeanDefinitionReader {

	void loadBeanDefinitions(String location)throws Exception;
}
