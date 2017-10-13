package us.codecraft.tinyioc.beans.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
/**
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构，但是把生成 Bean 的具体实现方式留给子类实现
 * @author zhujie
 *
 */
public abstract class AbstractBeanFactory implements BeanFactory{

	
	/**
	 * 存放BeanDefinition信息
	 */
	private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

	/**
	 * 存放BeanDefinition的名称
	 */
	private final List<String> beanDefinitionNames = new ArrayList<String>();
	
	private List<BeanPostProcessor>  beanPostProcessors = new ArrayList<BeanPostProcessor>();
	
	
	@Override
	public Object getBean(String name) throws Exception {
		BeanDefinition beanDefinition = beanDefinitionMap.get(name);
		if(beanDefinition == null) {
			throw new IllegalArgumentException("No bean named "+name+" is defined");
		}
		Object bean = beanDefinition.getBean();
		if(bean == null) {
			bean = doCreateBean(beanDefinition);
			bean = initializeBean(bean, name);
			beanDefinition.setBean(bean);
		}
		return bean;
	}

	/**
	 * 初始化Bean
	 * 从BeanPostProcessor列表中,依次取出BeanPostProcessor 
	 * 执行bean = postProcessBeforeInitialization(bean,beanName)。
	 * 从 BeanPostProcessor 列表中， 依次取出 BeanPostProcessor 
	 * 执行其 bean = postProcessAfterInitialization(bean,beanName)。
	 * @param bean
	 * @param name
	 * @return
	 * @throws Exception
	 */
	protected Object initializeBean(Object bean,String name)throws Exception{
		for(BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessorBeforeInitialization(bean, name);
		}
		
		// TODO:call initialize method
		for(BeanPostProcessor beanPostProcessor : beanPostProcessors) {
			bean = beanPostProcessor.postProcessorAfterInitialization(bean, name);
		}
		return bean;
	}
	
	/**
	 * 生成一个新的实例
	 * @param beanDefinition
	 * @return
	 * @throws Exception
	 */
	protected Object createBeanInstance(BeanDefinition beanDefinition)throws Exception{
		return beanDefinition.getBeanClass().newInstance();
	}
	
	public void registerBeanDefinition(String name, BeanDefinition beanDefinition)throws Exception {
    
		beanDefinitionMap.put(name, beanDefinition);
		beanDefinitionNames.add(name);
	}

	public void preInstantiateSingletons()throws Exception{
		for(Iterator it = this.beanDefinitionNames.iterator();it.hasNext();) {
			String beanName = (String) it.next();
           getBean(beanName);
		}
	}
	
    /**
     * 实例化Bean
     * @param beanDefinition
     * @return
     * @throws Exception
     */
	protected  Object doCreateBean(BeanDefinition beanDefinition) throws Exception{
		Object bean = createBeanInstance(beanDefinition);
		beanDefinition.setBean(bean);
		applyPropertyValues(bean, beanDefinition);
		return bean;
	}
	
	/**
	 * 注入属性，包括依赖注入的过程，在依赖注入的过程中，
	 * 如果Bean实现了BeanFactoryAware接口,
	 * 则将容器的引用传入到Bean中去,
	 * 这样，Bean 将获取对容器操作的权限，
	 * 也就允许了 编写扩展 IoC 容器的功能的 Bean。
	 * @param bean
	 * @param beanDefinition
	 * @throws Exception
	 */
	protected void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception{
		
	}

	public void addPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception{
		this.beanPostProcessors.add(beanPostProcessor);
	}
	
	/**
	 * 根据Class类型获取Bean集合
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public List getBeansForType(Class type)throws Exception{
		List beans = new ArrayList<Object>();
		for(String beanDefinitionName : beanDefinitionNames) {
			if(type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
				beans.add(getBean(beanDefinitionName));
			}
		}
		return beans;
	}
}
