package us.codecraft.tinyioc.aop;

public interface ClassFilter {

	boolean matches(Class targetClass);
}
