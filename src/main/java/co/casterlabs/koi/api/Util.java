package co.casterlabs.koi.api;

import java.lang.reflect.Method;

import lombok.SneakyThrows;

public class Util {

    @SneakyThrows
    public static void reflectInvoke(EventListener listener, Object event) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventHandler.class) && (method.getParameterCount() == 1) && method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                method.invoke(listener, event);
            }
        }
    }

}
