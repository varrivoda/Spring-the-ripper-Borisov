package org.example.screensaver;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.LocalTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;


public class PeriodicalScopeResolver implements Scope {
    Map<String, SimpleEntry<LocalTime, Object>> map= new HashMap<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if(map.containsKey(name)){
            int secondsSinceLastRequest = LocalTime.now().getSecond() - map.get(name).getKey().getSecond();
            if(secondsSinceLastRequest>1){
                map.put(name, new SimpleEntry<>(LocalTime.now(), objectFactory.getObject()));
            }
        }else{
            map.put(name, new SimpleEntry<>(LocalTime.now(), objectFactory.getObject()));
        }

        return map.get(name).getValue();
    }

    @Override
    public Object remove(String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
