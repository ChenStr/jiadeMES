package com.BSMES.jd.tools.my;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 自写实用小工具
 * @author DNYY
 */
public class MyUtils {

    /**
     * 修改Collection<T> T中的所有属性 把obj为中为null的属性全部设置为null
     * @param array 数组
     * @param obj   示例T的属性
     * @param <T>
     * @return
     */
    public static <T> Collection<T> AllSet(Collection<T> array, T obj) {
        if (array==null || Objects.isNull(obj)){
            return null;
        }
        //获取其类名
        Class<?> c = obj.getClass();
        //获取其属性集合
        Field[] fs = c.getDeclaredFields();
        //遍历其属性
        for (Field f : fs) {
            //得到此属性的值
            Object val = null;
            try {
                val = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //判断其属性是否为空,如果为空
            if(Objects.isNull(val)){
                for (T a:array) {
                    //把a的该属性设置为空
                    try {
                        f.set(a,null);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return array;
    }

    /**
     * 修改List<T> T中的所有属性
     * @param array  数组
     * @param fields 要保留信息的列明
     * @param <T>
     * @return
     */
    public static <T> Collection<T> AllSet(Collection<T> array,Collection<String> fields) {
        if (array==null || fields==null){
            return null;
        }
        //获取其类名
        Class<?> c = array.getClass();
        //获取其属性集合
        Field[] fs = c.getDeclaredFields();
        //遍历其属性
        for (Field f : fs) {
            for (String field : fields) {
                if (!(f.getName() == field)){
                    for (T a:array) {
                        //把a的该属性设置为空
                        try {
                            f.set(a,null);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return array;
    }

    /**
     * 将Object转化为Map
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, Object> objectToMap(Object obj){
        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
//        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            Object value = null;
            try {
                value = field.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            map.put(fieldName, value);
        }
        return map;
    }

    /**
     * 根据对象的属性名来获取对应的值(不用考虑对象的类型)
     * @param fieldName 对象的属性名(String)
     * @param object 对象
     * @return
     */
    public static Object getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            return  field.get(object);
        } catch (Exception e) {

            return null;
        }
    }

    /**
     * 获取指定字符串的后缀名
     * @param fileName
     * @param bite
     * @return
     */
    public static String getlastName(String fileName,String bite){
        int i = fileName.lastIndexOf(bite);
        String string = null;
        if (i==-1){
            string = null;
        }else{
            string=fileName.substring(i+1);
        }
        return string;
    }

    /**
     * 判断字符串是否为空,是空返回false 非空返回true
     * @param string
     * @return
     */
    public static Boolean StringIsNull(String string){
        if (string==null || "".equals(string) || string.length()==0) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * 给一个字符串创建一个字符串的类
     */
    public static Object createClassByString(String classname) throws Exception {
        //加载要反射的类
        Class cls = Class.forName(classname);
        //找到类的构造方法
        Object instance = cls.newInstance();

        return instance;

    }


}

