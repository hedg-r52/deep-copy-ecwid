package utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Andrei Soloviev (hedg.r52@gmail.com).
 * date: 13.10.2022
 */
public class CopyUtils {
    private static final List<Class<?>> EXPLICIT_TYPES = List.of(
            String.class,
            Boolean.class,
            Character.class,
            Byte.class,
            Integer.class,
            Long.class,
            Float.class,
            Double.class
    );

    public static <T> T deepCopy(T obj) {
        if (obj == null) {
            return null;
        }
        var explicitValue = explicitValue(obj);
        if (explicitValue.isPresent()) {
            return explicitValue.get();
        }
        try {
            var result = createNewInstance(obj);
            var fieldData = extractFieldData(obj);
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(result, fieldData.get(field.getName()));
            }
            return (T) result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Object createNewInstance(T obj) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        var ctor = obj.getClass().getDeclaredConstructors()[0];
        var ctorArgs = new Object[ctor.getParameterCount()];
        var position = 0;
        for (var parameter: ctor.getParameters()) {
            if (parameter.getType().isPrimitive()) {
                ctorArgs[position] = 0;
            }
            position++;
        }
        ctor.setAccessible(true);
        return ctor.newInstance(ctorArgs);
    }

    private static <T> Optional<T> explicitValue(T obj) {
        var clazz = obj.getClass();
        if (EXPLICIT_TYPES.contains(clazz)) {
            return Optional.of(obj);
        }
        return Optional.empty();
    }

    private static <T> HashMap<String, Object> extractFieldData(T obj) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        var result = new HashMap<String, Object>();
        var fields = obj.getClass().getDeclaredFields();
        for (Field field: fields) {
            var type = field.getType();
            field.setAccessible(true);
            if (type.isPrimitive() ||  EXPLICIT_TYPES.contains(field.getType())) {
                result.put(field.getName(), field.get(obj));
            } else if (type.getInterfaces().length > 0 && type.getInterfaces()[0] == Collection.class) {
                var collection = ((Collection) field.get(obj));
                Collection newCollection;
                if (type.getTypeName().contains(".List")) {
                    newCollection = new ArrayList();
                } else {
                    newCollection = new HashSet();
                }
                for (Object o : collection) {
                    newCollection.add(deepCopy(o));
                }
                result.put(field.getName(), newCollection);
            } else {
                result.put(field.getName(), deepCopy(field.get(obj)));
            }
        }
        return result;
    }
}
