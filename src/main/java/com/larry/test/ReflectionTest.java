package com.larry.test;

import com.larry.bean.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Created on 16/5/7
 */
public class ReflectionTest {
    public static void main(String[] args) throws ClassNotFoundException{
        //获取Order Class对象
        Class orderClass = Order.class;

        //运行期获取Class对象
//        String classname = ...;
//        Class orderClass2 = Class.forName(classname);

        //类名
        System.out.println(orderClass.getSimpleName());//Order
        System.out.println(orderClass.getName());//com.larry.bean.Order

        int modifiers = orderClass.getModifiers();
        System.out.println(modifiers);//1
        System.out.println(Modifier.isAbstract(modifiers));//false
        System.out.println(Modifier.isPublic(modifiers));//true

        Modifier.isAbstract(modifiers);
        Modifier.isFinal(modifiers);
        Modifier.isInterface(modifiers);
        Modifier.isNative(modifiers);
        Modifier.isPrivate(modifiers);
        Modifier.isProtected(modifiers);
        Modifier.isPublic(modifiers);
        Modifier.isStatic(modifiers);
        Modifier.isStrict(modifiers);
        Modifier.isSynchronized(modifiers);
        Modifier.isTransient(modifiers);
        Modifier.isVolatile(modifiers);

        //包
        Package packages = orderClass.getPackage();
        System.out.println(packages.getName());//com.larry.bean

        //父类
        Class superClass = orderClass.getSuperclass();
        System.out.println(superClass.getName());

        //接口
        Class[] interfaces = orderClass.getInterfaces();
        System.out.println(interfaces.length);

        //构造器
        Constructor[] constructors = orderClass.getConstructors();

        try {
            Constructor constructor = orderClass.getConstructor(new Class[]{String.class});
            System.out.println("=====constructor=====");
            System.out.println(constructor.getName());

            Class[] parameterTypes = constructor.getParameterTypes();
            System.out.println(parameterTypes[0].getName());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            Constructor constructor = Order.class.getConstructor(String.class);
            Order order = (Order) constructor.newInstance("2016050901");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //方法
        Method[] methods = orderClass.getMethods();
        System.out.println("=====Method=====");
        System.out.println(methods[0].getName());

        try {
            Method methodHaveNoParameter = orderClass.getMethod("getState");//该方法没有参数
            System.out.println(methodHaveNoParameter.getName());//getState

            Method methodHaveParameter = orderClass.getMethod("setState",new Class[]{String.class});//该方法没有参数
            System.out.println(methodHaveParameter.getName());//setState

            Class[] parameters = methodHaveParameter.getParameterTypes();
            System.out.println("======parameters======");
            System.out.println(parameters[0].getName());


            Class returnType = methodHaveParameter.getReturnType();
            System.out.println(returnType);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        try {
            System.out.println("======invoke======");
            Method methodPay = orderClass.getMethod("pay",new Class[]{String.class});

            Order order = new Order();
            Object returnValue = methodPay.invoke(order,"2016050902");
            System.out.println(returnValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //私有变量
        Order privateObject = new Order("2016051901");

        try {
            Field privateField = Order.class.getDeclaredField("orderId");

            privateField.setAccessible(true);

            String orderId = (String) privateField.get(privateObject);

            System.out.println("=========Private Field=============");
            System.out.println("orderId:" + orderId);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //私有方法
        try {
            Method privateMethod = Order.class.getDeclaredMethod("getOrderId");
            privateMethod.setAccessible(true);
            String returnValue = (String) privateMethod.invoke(privateObject);
            System.out.println("=========Private Method=============");
            System.out.println("returnValue = " + returnValue);
            System.out.println("returnValue2 = " + returnValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //变量
        Field[] fields = orderClass.getFields();
        System.out.println("=====Field=====");
        System.out.println(fields[0].getName());

        try {
            Field field = orderClass.getField("description");
            System.out.println(field.getName());
            System.out.println(field.getType());
            Order order = new Order();

            Object value = field.get(order);

            field.set(order, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        //注解
        Annotation[] annotations = orderClass.getAnnotations();
    }
}
