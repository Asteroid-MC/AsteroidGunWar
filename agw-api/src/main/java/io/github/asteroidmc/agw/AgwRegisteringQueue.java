package io.github.asteroidmc.agw;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class AgwRegisteringQueue<T extends Registerable> {

    private final Map<String, T> map;
    private final AgwRegistry<T> registry;

    public AgwRegisteringQueue(AgwRegistry<T> registry) {
        this.map = new HashMap<>();
        this.registry = registry;
    }

    public void queue(T t) {
        String rn = t.registeringName().toLowerCase();
        if(map.containsKey(rn) || registry.isRegistered(rn)) throw new IllegalArgumentException("already registered or queued: " + t.registeringName());

        map.put(rn, t);
    }

    public void reject() {
        map.clear();
    }

    public void cancel(String id) {
        String rn = id.toLowerCase();
        if(!map.containsKey(rn)) throw new IllegalArgumentException("no such object in the queue: " + id);

        map.remove(rn);
    }

    public boolean isQueued(String id) {
        String rn = id.toLowerCase();
        return map.containsKey(rn);
    }

    public T get(String id) {
        String rn = id.toLowerCase();
        if(!map.containsKey(rn)) throw new IllegalArgumentException("not registered yet: " + id);

        return map.get(rn);
    }

    public Collection<T> list() {
        return map.values();
    }

    public void registerAll(Consumer<T> onRegister) {
        HashMap<String, T> hashMap = new HashMap<>(map);
        for(Map.Entry<String, T> entry : hashMap.entrySet()) {
            String id = entry.getKey();
            T val = entry.getValue();

            if(val != null) registry.register(val);
            if(onRegister != null) onRegister.accept(val);

            map.remove(id);
        }
    }

    public void registerAll() {
        registerAll(null);
    }

}
