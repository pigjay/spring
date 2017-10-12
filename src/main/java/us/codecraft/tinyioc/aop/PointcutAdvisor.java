package us.codecraft.tinyioc.aop;

public interface PointcutAdvisor  extends Advisor{

	Pointcut getPointcut();
}
