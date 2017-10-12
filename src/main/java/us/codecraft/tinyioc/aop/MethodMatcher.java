package us.codecraft.tinyioc.aop;

import java.lang.reflect.Method;
/**
 * 
 * @author zhujie
 *
 */
public interface MethodMatcher {

	boolean matches(Method method,Class targetClass);
}
