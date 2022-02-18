package helpers;

/**
 * Helper class that allows storage and retrieval of an object between controller classes.
 */

public class ObjectToBeUpdated {

    private static Object o;

    public static void setObject(Object obj) {
        o = obj;
    }

    public static Object getObject() {
        return o;
    }

}
