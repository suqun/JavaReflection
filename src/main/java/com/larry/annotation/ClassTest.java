package com.larry.annotation;


import java.lang.annotation.Annotation;

/**
 * Created on 16/5/28
 */
public class ClassTest {
    public static void main(String[] args) {
        Class aClass =TheClass.class;
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof MyAnnotation) {
                MyAnnotation myAnnotation = (MyAnnotation) annotation;
                System.out.println("name:"+myAnnotation.name());
                System.out.println("value:"+myAnnotation.value());
            }
        }

        Annotation annotation = aClass.getAnnotation(MyAnnotation.class);
        if (annotation instanceof MyAnnotation) {
            MyAnnotation myAnnotation = (MyAnnotation) annotation;
            System.out.println("name:"+myAnnotation.name());
            System.out.println("value:"+myAnnotation.value());
        }
    }
}
