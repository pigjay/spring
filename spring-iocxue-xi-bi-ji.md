# spring-ico学习笔记

### 1.ioc容器的实现

##### 1.BeanDefinitioin
* UML类图
![](/assets/BeanDefinition-BeanDefinitionReader.png)
* 以 **BeanDefinition** 类为核心发散出的几个类，都是用于解决 **Bean** 的具体定义问题，包括 **Bean** 的名字是什么、它的类型是什么，它的属性赋予了哪些值或者引用，也就是 如何在 **IoC** 容器中定义一个 **Bean**，使得 **IoC** 容器可以根据这个定义来生成实例 的问题。

```
package us.codecraft.tinyioc;
/**
 * 该类保存了 Bean 定义。
 * 包括 Bean 的 名字 String beanClassName、
 * 类型 Class beanClass、属性 PropertyValues propertyValues。
 * 根据其 类型 可以生成一个类实例，然后可以把 属性 注入进去。
 * propertyValues 里面包含了一个个 PropertyValue 条目，
 * 每个条目都是键值对 String - Object，分别对应要生成实例的属性的名字与类型。
 * 在 Spring 的 XML 中的 property 中，键是 key ，值是 value 或者 ref。
 * 对于 value 只要直接注入属性就行了，但是 ref 要先进行解析。
 * Object 如果是 BeanReference 类型，则说明其是一个引用，
 * 其中保存了引用的名字，需要用先进行解析，转化为对应的实际 Object。
 * @author zhujie
 *
 */
public class BeanDefinition {

    //存放bean
    private Object bean;

    //存放bean的Class
    private Class beanClass;

    //存放bean的ClassName
    private String beanClassName;

    //存放bean的属性信息
    private PropertyValues propertyValues = new PropertyValues();

    public BeanDefinition() {

    }

    #####
    set/get方法...
    #####

    //beanClassName的set方法，同时对beanClass属性进行初始化
    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
```

* **BeanDefinitionReader** 解析 **BeanDefinition** 的接口。通过 **loadBeanDefinitions\(String\)** 来从一个地址加载类定义。

```
package us.codecraft.tinyioc;
/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public interface BeanDefinitionReader {

    //从一个地址加载类定义
    void loadBeanDefinitions(String location)throws Exception;
}
```

* **AbstractBeanDefinitionReader** 实现 **BeanDefinitionReader** 接口的抽象类（未具体实现 **loadBeanDefinitions**，而是规范了 **BeanDefinitionReader** 的基本结构）。内置一个 **HashMap rigistry**，用于保存 **String - beanDefinition** 的键值对。内置一个 **ResourceLoader resourceLoader**，用于保存类加载器。用意在于，使用时，只需要向其 **loadBeanDefinitions\(\)** 传入一个资源地址，就可以自动调用其类加载器，并把解析到的 **BeanDefinition** 保存到 **registry** 中去。

```
package us.codecraft.tinyioc;

import java.util.HashMap;
import java.util.Map;

import us.codecraft.tinyioc.io.ResourceLoader;

/**
 * 从配置中读取BeanDefinitionReader
 * @author zhujie
 *
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    //存放beanDefinition的键值对
    private Map<String,BeanDefinition> registry;

    //资源加载器
    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<String, BeanDefinition>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }


}
```

* **XmlBeanDefinitionReader** 具体实现了 loadBeanDefinitions\(\) 方法，从 XML 文件中读取类定义。

```
package us.codecraft.tinyioc.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import us.codecraft.tinyioc.AbstractBeanDefinitionReader;
import us.codecraft.tinyioc.BeanDefinition;
import us.codecraft.tinyioc.BeanReference;
import us.codecraft.tinyioc.PropertyValue;
import us.codecraft.tinyioc.io.ResourceLoader;
/**
 * 用于从xml文件中读取配置信息的类
 * @author zhujie
 *
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{

    //初始化 资源加载器
    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    //读取xml配置文件信息
    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        //获取资源输入流
        InputStream inputStream = getResourceLoader().getResource(location).getInputStream();
        //读取配置信息
        doLoadBeanDefinitions(inputStream);
    }

    /**
     * 读取配置信息
     * @param inputStream
     * @throws Exception
     */
    protected void doLoadBeanDefinitions(InputStream inputStream)throws Exception{
        //初始化DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //获取DocumentBuilder
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        //从输入流创建Document
        Document doc = docBuilder.parse(inputStream);
        //解析bean
        registerBeanDefinitions(doc);
        //关闭输入流
        inputStream.close();
    }

    /**
     * 根据Document注册BeanDefinition
     * @param doc
     */
    public void registerBeanDefinitions(Document doc) {
        Element root = doc.getDocumentElement();

        parseBeanDefinitions(root);

    }

    /**
     * 解析Document
     * @param root
     */
    protected void parseBeanDefinitions(Element root) {
        NodeList nl = root.getChildNodes();
        for(int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            if(node instanceof Element) {
                Element ele = (Element) node;
                processBeanDefinition(ele);
            }
        }
    }

    /**
     * 生成BeanDefinition
     * @param ele
     */
    protected void processBeanDefinition(Element ele) {
        String name = ele.getAttribute("name");
        String className = ele.getAttribute("class");
        BeanDefinition beanDefinition = new BeanDefinition();
        processProperty(ele, beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(name, beanDefinition);
    }

    /**
     * 读取bean属性
     * @param ele
     * @param beanDefinition
     */
    private void processProperty(Element ele,BeanDefinition beanDefinition) {
        NodeList propertyNode = ele.getElementsByTagName("property");
        for(int i = 0; i < propertyNode.getLength(); i++) {
            Node node = propertyNode.item(i);
            if(node instanceof Element) {
                Element propertyEle = (Element) node;
                String name = propertyEle.getAttribute("name");
                String value = propertyEle.getAttribute("value");
                if(value != null && value.length() > 0) {
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, value));
                }else {
                    String ref = propertyEle.getAttribute("ref");
                    if(ref == null || ref.length() == 0) {
                        throw new IllegalArgumentException("Configuration problem :<property> element for property '"+
                    name+"' must specify a ref or value");
                    }
                    BeanReference beanReference = new BeanReference(ref);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name, beanReference));
                }
            }
        }
    }
}
```

##### 2.BeanFactory

* UML类图  
![](/assets/BeanFactory-Conext-UML .png)

* 接口，标识一个 **IoC** 容器。通过 **getBean\(String\)** 方法来 获取一个对象

```
package us.codecraft.tinyioc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.BeanDefinition;

/**
 * bean的工厂接口,标识一个 IoC 容器。通过 getBean(String) 方法来 获取一个对象
 * @author zhujie
 *
 */
public interface BeanFactory {

 //获取bean的方法
 public Object getBean(String name)throws Exception;

 //注册bean的方法
 public void registerBeanDefinition(String name,BeanDefinition beanDefinition) throws Exception;
}
```

* **AbstractBeanFactory BeanFactory** 的一种抽象类实现，规范了 **IoC** 容器的基本结构，但是把生成 **Bean** 的具体实现方式留给子类实现。**IoC** 容器的结构：**AbstractBeanFactory** 维护一个 **beanDefinitionMap** 哈希表用于保存类的定义信息**（BeanDefinition）**。获取 **Bean** 时，如果 **Bean** 已经存在于容器中，则返回之，否则则调用 **doCreateBean** 方法装配一个 **Bean**。（所谓存在于容器中，是指容器可以通过 **beanDefinitionMap** 获取 **BeanDefinition** 进而通过其 **getBean\(\)** 方法获取 **Bean**。）

```
package us.codecraft.tinyioc.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.codecraft.tinyioc.BeanDefinition;
/**
 * BeanFactory 的一种抽象类实现，规范了 IoC 容器的基本结构，但是把生成 Bean 的具体实现方式留给子类实现
 * @author zhujie
 *
 */
public abstract class AbstractBeanFactory implements BeanFactory{

    //存放BeanDefinition信息
    private Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

    //存放BeanDefinition的名称
    private final List<String> beanDefinitionNames = new ArrayList<String>();


    @Override
    public Object getBean(String name) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if(beanDefinition == null) {
            throw new IllegalArgumentException("No bean named "+name+" is defined");
        }
        Object bean = beanDefinition.getBean();
        if(bean == null) {
            bean = doCreateBean(beanDefinition);
        }
        return bean;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition)throws Exception {

        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    public void preInstantiateSingletons()throws Exception{
        for(Iterator it = this.beanDefinitionNames.iterator();it.hasNext();) {
            String beanName = (String) it.next();
            getBean(beanName);
        }
    }

    /**
     * 初始化bean
     * @param beanDefinition
     * @return
     */
    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;
}
```

* **AutowireCapableBeanFactory** 可以实现自动装配的 **BeanFactory**。在这个工厂中，实现了 **doCreateBean** 方法，该方法分三步：1，通过 **BeanDefinition** 中保存的类信息实例化一个对象；2，把对象保存在 **BeanDefinition** 中，以备下次获取；3，为其装配属性。装配属性时，通过 **BeanDefinition** 中维护的 **PropertyValues** 集合类，把 **String - Value** 键值对注入到 Bean 的属性中去。如果 **Value** 的类型是 **BeanReference** 则说明其是一个引用（对应于 **XML** 中的 **ref**），通过 **getBean** 对其进行获取，然后注入到属性中。

```
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
```



