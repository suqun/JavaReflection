package com.larry.generic;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 16/5/29
 */
public class GenericTest {
    public static void main(String[] args) {
        try {
            Method method = MyClass.class.getMethod("getStringList");
            Type returnType = method.getGenericReturnType();
            if (returnType instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) returnType;
                Type[] typeArguments = type.getActualTypeArguments();
                for (Type typeArgument : typeArguments) {
                    Class typeArgClass = (Class) typeArgument;
                    System.out.println("typeArgClass:" + typeArgClass);//typeArgClass:class java.lang.String
                }
            }


            Method method2 = MyClass.class.getMethod("setStringList", List.class);
            Type[] genericParameterTypes = method2.getGenericParameterTypes();
            for (Type genericParameterType : genericParameterTypes) {
                if(genericParameterType instanceof ParameterizedType){
                    ParameterizedType aType = (ParameterizedType) genericParameterType;
                    Type[] parameterArgTypes = aType.getActualTypeArguments();
                    for(Type parameterArgType : parameterArgTypes){
                        Class parameterArgClass = (Class) parameterArgType;
                        System.out.println("parameterArgClass = " + parameterArgClass);//parameterArgClass = class java.lang.String
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
