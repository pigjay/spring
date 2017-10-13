package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.BeanFactory;

/**
 * 标记接口，继承了 BeanFactory。
 * 通常，要实现一个 IoC 容器时，
 * 需要先通过 ResourceLoader 获取一个 Resource，
 * 其中包括了容器的配置、Bean 的定义信息。接着，
 * 使用 BeanDefinitionReader 读取该 Resource 中的 BeanDefinition 信息。
 * 最后，把 BeanDefinition 保存在 BeanFactory 中，
 * 容器配置完毕可以使用。
 * 注意到 BeanFactory 只实现了 Bean 的 装配、获取，
 * 并未说明 Bean 的 来源 也就是 BeanDefinition 是如何 加载 的。
 * 该接口把 BeanFactory 和 BeanDefinitionReader 结合在了一起。
 * @author zhujie
 *
 */
public interface ApplicationContext extends BeanFactory{

}
