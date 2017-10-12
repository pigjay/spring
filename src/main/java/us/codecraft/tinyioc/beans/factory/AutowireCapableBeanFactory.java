package us.codecraft.tinyioc.beans.factory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import us.codecraft.tinyioc.aop.BeanFactoryAware;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanReference;
import us.codecraft.tinyioc.beans.PropertyValue;
/**
 * 可自动转配内容的BeanFactory
 * @author zhujie
 *
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory{
	
	/**
	 * 装配属性
	 * @param bean
	 * @param mbd
	 * @throws Exception
	 */
	protected void applyPropertyValues(Object bean,BeanDefinition mbd)throws Exception{
	    if(bean instanceof BeanFactoryAware) {
	    	((BeanFactoryAware)bean).setBeanFactory(this);
	    }
		
		for(PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValueList()) {
	    	Object value = propertyValue.getValue();
	    	if(value instanceof BeanReference) {
	    		BeanReference beanReference = (BeanReference) value;
	    		value = getBean(beanReference.getName());
	    	}
	    	
           try {
	   	    	Method declaredMethod = bean.getClass().getDeclaredMethod("set"+propertyValue.getName().substring(0,1).toUpperCase()+propertyValue.getName().substring(1), value.getClass());
		        declaredMethod.setAccessible(true);
		        declaredMethod.invoke(bean, value);
			} catch (NoSuchMethodException e) {
				Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
				declaredField.setAccessible(true);
				declaredField.set(bean, value);
			}
		}
	}
}
