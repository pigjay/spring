package us.codecraft.tinyioc.aop;
/**
 * 
 * @author zhujie
 *
 */
public interface Pointcut {

	ClassFilter getClassFilter();
	
	MethodMatcher getMethodMatcher();
}
