
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.lang.*;
public class Unit {
    public static HashMap<String, Throwable> testClass(String name) {
        HashMap<String, Throwable> tests = new HashMap<>();
        Class test = null;
        try {
            test = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object instance = null;
        try {
            instance = test.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<Method> m_list = new ArrayList<>();

        for (Method m : test.getMethods()) {
            if (m.getAnnotations().length > 1) {
                try {
                    throw new IllegalAccessException();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            m_list.add(m);
        }
        List<Method> Before = sort_section(m_list, Before.class, true);
        List<Method> BeforeClass = sort_section(m_list, BeforeClass.class, true);
        List<Method> Test = sort_section(m_list, Test.class, true);
        List<Method> After = sort_section(m_list, After.class, true);
        List<Method> AfterClass = sort_section(m_list, AfterClass.class, true);


        for (Method m : BeforeClass) {
            if (Modifier.isStatic(m.getModifiers())) {
                try {
                    m.invoke(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Method m : Test) {
            for (Method m1 : Before) {
                try {
                    m1.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            try {
                try {
                    Object returnValue = m.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                tests.put(m.getName(), null);
            } catch (InvocationTargetException ex){
                Throwable cause = ex.getCause();
                tests.put(m.getName(), cause);
            }
            for (Method m2 : After) {
                try {
                    m2.invoke(instance);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        for (Method m : AfterClass) {
            if (Modifier.isStatic(m.getModifiers())) {
                try {
                    m.invoke(null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return tests;
    }

    public static HashMap<String, Object[]> quickCheckClass(String name)  {
        HashMap<String, Object[]> t_map = new HashMap<>();
        Class test = null;
        try {
            test = Class.forName(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Object instance = null;
        try {
            instance = test.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        List<Method> m_list = new ArrayList<>();

        for (Method m : test.getMethods()) {
            m_list.add(m);
        }
        List<Method> Property = sort_section(m_list, Property.class, false);

        for (Method m : Property) {
            List<List<Object>> r_list = new ArrayList<List<Object>>();
            try {
                r_list = range_list(m, instance);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<List<Object>> list = new ArrayList<List<Object>>();
            list = complete_list(r_list);
            if (list.size() >= 100) {
                throw new IllegalArgumentException();
            }
            for (List<Object> sub_list : list) {

                try {
                    Object ReturnValue = null;
                    try {
                        ReturnValue = m.invoke(instance, sub_list.toArray());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (ReturnValue.equals(false)) {
                    t_map.put(m.getName(), sub_list.toArray());
                    break;
                }
                t_map.put(m.getName(), null);
            } catch (InvocationTargetException ex){
                t_map.put(m.getName(), sub_list.toArray());
                break;
            }
            }
        }
        return t_map;
    }
// help from stack overflow
    private static List<List<Object>> complete_list(List<List<Object>> lists) {
        List<List<Object>> resultLists = new ArrayList<List<Object>>();
        if (lists.size() == 0) {
            resultLists.add(new ArrayList<Object>());
            return resultLists;
        } else {
            List<Object> firstList = lists.get(0);
            List<List<Object>> remainingLists = complete_list(lists.subList(1, lists.size()));
            for (Object condition : firstList) {
                for (List<Object> remainingList : remainingLists) {
                    List<Object> resultList = new ArrayList<Object>();
                    resultList.add(condition);
                    resultList.addAll(remainingList);
                    resultLists.add(resultList);
                }
            }
        }
        return resultLists;
    }

    private static List<List<Object>> range_list(Method m, Object instance) throws Exception{
        List<List<Object>> l = new ArrayList<List<Object>>();
        Annotation[][] a = m.getParameterAnnotations();
        if (a.length != 0) {
            for (Annotation[] b : a) {
                for (Annotation c : b) {
                    if (c.annotationType() == IntRange.class) {
                        List<Object> list = new ArrayList<>();
                        list = int_list(c);
                        l.add(list);
                    } else if (c.annotationType() == StringSet.class) {
                        List<Object> list = new ArrayList<>();
                        list = string_list(c);
                        l.add(list);
                    } else if (c.annotationType() == ForAll.class) {
                        List<Object> list = new ArrayList<>();
                        list = forall_list(c, instance);
                        l.add(list);
                    } else if (c.annotationType() == ListLength.class) {
                        List<List<Object>> list = new ArrayList<List<Object>>();
                        list = list_list(c, m.getAnnotatedParameterTypes(), instance);
                        l.add(new ArrayList<>(list));

                    } else {
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return l;
    }
    private static List<List<Object>> list_list(Annotation anno, AnnotatedType[] ap, Object instance) {
        List<List<Object>> resultLists = new ArrayList<List<Object>>();
        List<Object> ray = new ArrayList<>();
        if (ap[0] instanceof AnnotatedParameterizedType) {
            AnnotatedParameterizedType x = (AnnotatedParameterizedType) ap[0];
            AnnotatedType[] a = x.getAnnotatedActualTypeArguments();
            if (a[0].getAnnotations()[0].annotationType() == IntRange.class) {
                ray = int_list(a[0].getAnnotations()[0]);
            } else if (a[0].getAnnotations()[0].annotationType() == StringSet.class) {
                ray = string_list(a[0].getAnnotations()[0]);
            }  else if (a[0].getAnnotations()[0].annotationType() == ForAll.class) {
                try {
                    ray = forall_list(a[0].getAnnotations()[0], instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ListLength l = (ListLength) anno;
        int min = l.min();
        int max = l.max();

        resultLists.add(new ArrayList<Object>());
        return resultLists;
    }


    private static List<Object> int_list(Annotation anno) {
        List<Object> list = new ArrayList<>();
        IntRange ma = (IntRange) anno;
        int min = ma.min();
        int max = ma.max();
        while (min != max + 1) {
            list.add(min);
            min++;
        }
        return list;
    }
    private static List<Object> string_list(Annotation anno) {
        List<Object> list = new ArrayList<>();
        StringSet s = (StringSet) anno;
        String[] s_array = s.strings();
        for (String key : s_array) {
            list.add(key);
        }
        return list;
    }
    private static List<Object> forall_list(Annotation anno, Object instance) throws Exception {

        List<Object> list = new ArrayList<>();
        ForAll var = (ForAll) anno;
        String n = var.name();
        int mul = var.times();

        for (int i = 0; i < mul; i++) {
            Method m = instance.getClass().getMethod(n);
            Object Return = m.invoke(instance);
            list.add(Return);
        }
        return list;
    }
    private static List<Method> sort_alphabet(List<Method> list) {
        String smallest = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        List<Method> updated = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                String m_1 = list.get(j).getName();
                if (updated.contains(list.get(j))) {
                    continue;
                }
                if (m_1.compareTo(smallest) < 0) {
                    smallest = m_1;
                }
            }
            for (Method m : list) {
                if (m.getName() == smallest) {
                    updated.add(m);
                    break;
                }
            }
            smallest = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
        }
        return updated;
    }
    private static List<Method> sort_section(List<Method> list, Class<? extends Annotation> anno, boolean x) {
        List<Method> specified = new ArrayList<Method>();
        for (Method method : list) {
            Annotation[] a = method.getAnnotations();
            if (a.length == 0 && x == true) {
                continue;
            }
            if (method.isAnnotationPresent(anno)) {
                specified.add(method);
            }
        }
        return sort_alphabet(specified);
    }
}