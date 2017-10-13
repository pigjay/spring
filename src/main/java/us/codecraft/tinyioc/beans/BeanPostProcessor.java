package us.codecraft.tinyioc.beans;

/**
 * 在 postProcessorAfterInitialization 方法中，
 * 使用动态代理的方式，返回一个对象的代理对象。
 * 解决了 在 IoC 容器的何处植入 AOP 的问题。
 * @author zhujie
 *
 */
public interface BeanPostProcessor {

	Object postProcessorBeforeInitialization(Object bean,String beanName) throws Exception;
	
	/**
	 * 在该方法中，使用动态代理的方式，
	 * 返回一个对象的代理对象。
	 * 解决了 在 IoC 容器的何处植入 AOP 的问题
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	Object postProcessorAfterInitialization(Object bean,String beanName) throws Exception;
}
