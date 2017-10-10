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
			Object bean = beanDefinition.getBeanClass().newInstance();
			beanDefinition.setBean(bean);
			applyPropertyValues(bean, beanDefinition);
		    return bean;
	}
	
	protected Object createBeanInstance(BeanDefinition beanDefinition)throws Exception{
		return beanDefinition.getBeanClass().newInstance();
	}
	
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
