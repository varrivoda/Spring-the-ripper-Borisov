package org.example.quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {

    Map<String, Class> map;
    ProfilingController controller = new ProfilingController();

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if(beanClass.isAnnotationPresent(Profiling.class)){
            map.put(beanName, beanClass);
        }
        return bean;
    }

    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if(beanClass!=null){
            return Proxy.newProxyInstance(beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if(controller.isEnabled()) {
                        System.out.println("Профилируем...");

                        long start = System.nanoTime();
                        Object returnValue = method.invoke(proxy, args);
                        long finish = System.nanoTime();
                        System.out.println("Время выполнения: " + (finish - start));
                        return returnValue;
                    }else
                        return method.invoke(proxy, args);
                }
            });
        }

        return bean;
    }

}

