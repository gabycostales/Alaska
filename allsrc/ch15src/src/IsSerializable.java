class IsSerializable {

// list of all full-qualified class names in java.lang and java.util
private final static String[] CLASS_NAMES = {
    "java.lang.Boolean",
    "java.lang.Byte",
    "java.lang.Character",
    "java.lang.Class",
    "java.lang.ClassLoader",
    "java.lang.Compiler",
    "java.lang.Double",
    "java.lang.Float",
    "java.lang.InheritableThreadLocal",
    "java.lang.Integer",
    "java.lang.Long",
    "java.lang.Math",
    "java.lang.Number",
    "java.lang.Object",
    "java.lang.Package",
    "java.lang.Process",
    "java.lang.Runtime",
    "java.lang.RuntimePermission",
    "java.lang.SecurityManager",
    "java.lang.Short",
    "java.lang.StackTraceElement",
    "java.lang.StrictMath",
    "java.lang.String",
    "java.lang.StringBuffer",
    "java.lang.System",
    "java.lang.Thread",
    "java.lang.ThreadGroup",
    "java.lang.ThreadLocal",
    "java.lang.Throwable",
    "java.lang.Void",

    "java.util.AbstractCollection",
    "java.util.AbstractList",
    "java.util.AbstractMap",
    "java.util.AbstractSequentialList",
    "java.util.AbstractSet",
    "java.util.ArrayList",
    "java.util.Arrays",
    "java.util.BitSet",
    "java.util.Calendar",
    "java.util.Collections",
    "java.util.Currency",
    "java.util.Date",
    "java.util.Dictionary",
    "java.util.EventListenerProxy",
    "java.util.EventObject",
    "java.util.GregorianCalendar",
    "java.util.HashMap",
    "java.util.HashSet",
    "java.util.Hashtable",
    "java.util.IdentityHashMap",
    "java.util.LinkedHashMap",
    "java.util.LinkedHashSet",
    "java.util.LinkedList",
    "java.util.ListResourceBundle",
    "java.util.Locale",
    "java.util.Observable",
    "java.util.Properties",
    "java.util.PropertyPermission",
    "java.util.PropertyResourceBundle",
    "java.util.Random",
    "java.util.ResourceBundle",
    "java.util.SimpleTimeZone",
    "java.util.Stack",
    "java.util.StringTokenizer",
    "java.util.Timer",
    "java.util.TimerTask",
    "java.util.TimeZone",
    "java.util.TreeMap",
    "java.util.TreeSet",
    "java.util.Vector",
    "java.util.WeakHashMap",
};

public static void main (String[] args) {

    if ( args.length == 1 ) {
        reportSerializabilityFor(args[0]);
    } else {
        String[] classNames = CLASS_NAMES;
        for (int i=0; i < classNames.length; i++) {
            reportSerializabilityFor(classNames[i]);
        }
    }
}

/*************************************************************************
 * Check whether the named class is Serializable or not, and report
 * status to console.
 *************************************************************************/
private static void reportSerializabilityFor(String className) {

    try {
        Class clazz = Class.forName(className);
        boolean serializable = java.io.Serializable.class.isAssignableFrom(clazz);
        System.out.println("is " + (serializable ? "" : "NOT ") + "serializable \t: " + className);
    } catch (Exception ignored) {
        System.out.println("Can't locate class " + className);
    }
}

}
