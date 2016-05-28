package com.larry.annotation;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created on 16/5/28
 */
public class MethodTest {
    public static void main(String[] args) {
        try {
            Method method = TheClass2.class.getMethod("doSomething");
            Annotation[] annotations = method.getDeclaredAnnotations();
            for(Annotation annotation : annotations){
                if(annotation instanceof MyAnnotation){
                    MyAnnotation myAnnotation = (MyAnnotation) annotation;
                    System.out.println("name: " + myAnnotation.name());
                    System.out.println("value: " + myAnnotation.value());
                }
            }


            Annotation annotation = method.getAnnotation(MyAnnotation.class);
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name:"+myAnnotation.name());
                System.out.println("value:"+myAnnotation.value());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
