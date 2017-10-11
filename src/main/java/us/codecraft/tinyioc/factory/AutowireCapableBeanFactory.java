package us.codecraft.tinyioc.factory;

import java.lang.reflect.Field;

import us.codecraft.tinyioc.BeanDefinition;
import us.codecraft.tinyioc.BeanReference;
import us.codecraft.tinyioc.PropertyValue;
/**
 * 可自动转配内容的BeanFactory
 * @author zhujie
 *
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory{

	@Override
	protected Object doCreateBean(BeanDefinition beanDefinition)throws Exception {
			
		    //实例化bean
		    Object bean = beanDefinition.getBeanClass().newInstance();
		    //将bean保存在beanDefinition中
			beanDefinition.setBean(bean);
			//为bean装配属性
			applyPropertyValues(bean, beanDefinition);
		    return bean;
	}
	
	protected Object createBeanInstance(BeanDefinition beanDefinition)throws Exception{
		return beanDefinition.getBeanClass().newInstance();
	}
	
	/**
	 * 装配属性
	 * @param bean
	 * @param mbd
	 * @throws Exception
	 */
	protected void applyPropertyValues(Object bean,BeanDefinition mbd)throws Exception{
	    for(PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValueList()) {
	    	Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
	    	declaredField.setAccessible(true);
	    	Object value = propertyValue.getValue();
	    	if(value instanceof BeanReference) {
	    		BeanReference beanReference = (BeanReference) value;
	    		value = getBean(beanReference.getName());
	    	}
	    	declaredField.set(bean, value);
	    }
	}
}
