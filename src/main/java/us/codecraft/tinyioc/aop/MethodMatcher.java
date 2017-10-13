package us.codecraft.tinyioc.aop;

import java.lang.reflect.Method;
/**
 * 方法切点
 * @author zhujie
 *
 */
public interface MethodMatcher {

	boolean matches(Method method,Class targetClass);
}
