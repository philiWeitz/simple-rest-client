package futurice.org.restfulmobileclient;


import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestUtil {

    private TestUtil() {

    }

    public static void changePrivateVariable(Object obj, String fieldName, Object newValue) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(obj, newValue);
    }

    public static void changePrivateStaticVariable(Class clazz, String fieldName, Object newValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}
