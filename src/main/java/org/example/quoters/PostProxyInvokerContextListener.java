package org.example.quoters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
@Component
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private ConfigurableListableBeanFactory factory;

//    @Autowired
//    public PostProxyInvokerContextListener(ConfigurableListableBeanFactory factory){
//        this.factory = factory;
//    }

//    public PostProxyInvokerContextListener(){}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String[] names = context.getBeanDefinitionNames();

        //BeanFactory bf = context.getParentBeanFactory();
        //ConfigurableListableBeanFactory clFactory = (ConfigurableListableBeanFactory) factory;
        if (factory!=null) {
            for (String name : names) {
                BeanDefinition beanDefinition = factory.getBeanDefinition(name);
                String originalClassName = beanDefinition.getBeanClassName();
                try {
                    Class<?> originalClass = Class.forName(originalClassName);
                    Method[] originalMethods = originalClass.getMethods();
                    for (Method method : originalMethods) {
                        if(method.isAnnotationPresent(PostProxy.class)){
                            //и только теперь,убедившись,мы создаем бин
                            Object bean = context.getBean(name);
                            Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                            currentMethod.invoke(bean);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }else{
            System.out.println("@Autowired factory is null!");
        }

    }

}
