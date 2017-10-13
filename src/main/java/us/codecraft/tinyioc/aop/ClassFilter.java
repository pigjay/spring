package us.codecraft.tinyioc.aop;
/**
 * 类切点
 * @author zhujie
 *
 */
public interface ClassFilter {

	boolean matches(Class targetClass);
}
