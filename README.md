## Java Reflection
原文地址[Java Reflection](http://tutorials.jenkov.com/java-reflection/classes.html)

#### Class对象

##### 获取class对象
Java中的所有类型包括基本类型(int, long, float等等)，即使是数组都有与之关联的Class类的对象。
如果你在编译期知道一个类的名字的话，那么你可以使用如下的方式获取一个类的Class对象

```java
        Class orderClass = Order.class;
```

如果你在编译期不知道类的名字，但是你可以在运行期获得到类名的字符串,那么你则可以这么做来获取Class对象

```java
        String className = ... ;//在运行期获取的类名字符串
        Class class = Class.forName(className);
```

在使用Class.forName()方法时，你必须提供一个类的全名，这个全名包括类所在的包的名字

##### Class Name 类名

通过`getName()` 方法返回类的全限定类名,不包含包名使用`getSimpleName()`

```java
        orderClass.getName();//com.larry.bean.Order
        orderClass.getSimpleName();
```

##### Modifiers 修饰符

通过Class对象来访问一个类的修饰符，即public,private,static等的关键字;

修饰符都被包装成一个int类型的数字，这样每个修饰符都是一个位标识(flag bit)，这个位标识可以设置和清除修饰符的类型;

使用java.lang.reflect.Modifier类中的方法来检查修饰符的类型。

```java
        int modifiers = orderClass.getModifiers();

        Modifier.isAbstract(modifiers);
        Modifier.isFinal(modifiers);
        Modifier.isInterface(modifiers);
        Modifier.isNative(modifiers);
        Modifier.isPrivate(modifiers);
        Modifier.isProtected(modifiers);
        Modifier.isPublic(modifiers);//true
        Modifier.isStatic(modifiers);
        Modifier.isStrict(modifiers);
        Modifier.isSynchronized(modifiers);
        Modifier.isTransient(modifiers);
        Modifier.isVolatile(modifiers);
```

##### Package Info 包

Package对象你可以获取包的相关信息，比如包名

```java
        Package packages = orderClass.getPackage();
        packages.getName();//com.larry.bean
```

##### Superclass 父类

superclass对象其实就是一个Class类的实例，所以你可以继续在这个对象上进行反射操作

```java
        Class superClass = orderClass.getSuperclass();
        superClass.getName();//java.lang.Object
```

##### Implemented Interfaces 实现的接口

获取指定类所实现的接口集合

```java
        Class[] interfaces = orderClass.getInterfaces();
```

getInterfaces()方法仅仅只返回当前类所实现的接口。当前类的父类如果实现了接口，这些接口是不会在返回的Class集合中的，尽管实际上当前类其实已经实现了父类接口

##### Constructors 构造函数

###### 获取构造函数的对象

```java
        Constructor[] constructors = orderClass.getConstructors();
```

可以通过构造函数的具体参数类型直接获取单独的构造函数，而不用获取全部的Constructors

```java
        try {
            Constructor constructor = orderClass.getConstructor(new Class[]{String.class});
            System.out.println(constructor.getName());//com.larry.bean.Order
        } catch (NoSuchMethodException e) {//没有找到匹配的构造函数会抛出NoSuchMethodException
            e.printStackTrace();
        }
```

###### 构造函数的参数类型

可以像下面这样获取构造函数的参数

```java
        Class[] parameterTypes = constructor.getParameterTypes();
        parameterTypes[0].getName();//java.lang.String
```

###### 用构造函数对象实例化一个类

constructor.newInstance()方法的方法参数是一个可变参数列表，但是当你调用构造方法的时候你必须提供精确的参数，即形参与实参必须一一对应。在这个例子中构造方法需要一个String类型的参数，那我们在调用newInstance方法的时候就必须传入一个String类型的参数

```java
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
```


##### Method 方法

获取类的方法，Method[]数组里面只包含类中public修饰的方法

```java
        Method[] methods = orderClass.getMethods();
```

也可以使用具体的方法名及参数类型直接获取,以Order类中的getState()，setState()方法为例:

```java
        try {
            Method methodHaveNoParameter = orderClass.getMethod("getState",null);//该方法没有参数,null省略也可以
            System.out.println(methodHaveNoParameter.getName());//getState
            
            Method methodHaveParameter = orderClass.getMethod("setState",new Class[]{String.class});//该方有String类型参数
            System.out.println(methodHaveParameter.getName());//setState

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
```

获取方法的参数类型及返回类型

```java
            Class[] parameters = methodHaveParameter.getParameterTypes();
            System.out.println(parameters[0].getName());//class java.lang.String

            Class returnType = methodHaveParameter.getReturnType();
            System.out.println(returnType);//void
```

使用`invoke`调用方法,以Order类中的pay()方法为例，该方法直接返回一个字符串

```java
        try {
            Method methodPay = orderClass.getMethod("pay",new Class[]{String.class});
            Order order = new Order();
            Object returnValue = methodPay.invoke(order,"2016050902");
            System.out.println(returnValue);//2016050902 pay success
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
```

如果方法是static的，那么在调用invoke时，第一个参数（类的实例）可以传null(传入类的实例也是可以的)。

##### 获取getter/setter方法

使用反射时，如果需要获取类的getter和setter方法，需要获取所有methods，并根据getter/setter的特点找到他们。

- Getter
    getter方法以「get」开头，没有参数，有返回值
- Setter
    setter方法以「set」开头，有一个参数，返回值可能有可能没有

下面是从类中找到get/set方法的例子

```java
        public static void printGettersSetters(Class aClass){
          Method[] methods = aClass.getMethods();

          for(Method method : methods){
            if(isGetter(method)) System.out.println("getter: " + method);
            if(isSetter(method)) System.out.println("setter: " + method);
          }
        }

        public static boolean isGetter(Method method){
          if(!method.getName().startsWith("get"))      return false;
          if(method.getParameterTypes().length != 0)   return false;  
          if(void.class.equals(method.getReturnType()) return false;
          return true;
        }

        public static boolean isSetter(Method method){
          if(!method.getName().startsWith("set")) return false;
          if(method.getParameterTypes().length != 1) return false;
          return true;
        }
```

##### Private Fields and Method 私有变量和私有方法

通常情况下，从对象外部访问私有变量和私有方法是不被允许的，但是通过反射可以很容易获取私有变量和私有方法，在单元测试的时候很有用。

###### Accessing Private Fields

获取私有变量你需要用到`Class.getDeclaredField(String name)`或者`Class.getDeclaredFields(String name)`方法。`Class.getField(String name)`和`Class.getFields(String name)`方法只能返回公有变量。下面是通过java的反射获取私有变量代码:

```java
        Order privateObject = new Order("2016051901");
        try {
            Field privateField = Order.class.getDeclaredField("orderId");
            privateField.setAccessible(true);
            String orderId = (String) privateField.get(privateObject);
            System.out.println("orderId:" + orderId);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
```

这段代码会打印出『orderId:2016051901』，值为Order实例私有变量orderId的值。

`Order.class.getDeclaredField("orderId")`这个方法返回一个私有变量，这个私有变量是Order类中自己定义的变量，而不是继承自其父类的变量。

`privateField.setAccessible(true);`这个方法会关闭实例类的反射访问检查。现在你可以访问私有的，受保护的和包级访问的变量。

###### Accessing Private Methods

要获取私有方法你需要使用方法`Class.getDeclaredMethod(String name, Class[] parameterTypes)`或者`Class.getDeclaredMethods()`。`Class.getMethod()`和`Class.getMethods()`方法只是返回的公有方法。下面是使用java反射访问私有方法的代码：

```java
        Order privateObject = new Order("2016051901");
        try {
            Method privateMethod = Order.class.getDeclaredMethod("getOrderId");
            privateMethod.setAccessible(true);
            String returnValue = (String) privateMethod.invoke(privateObject);
            System.out.println("returnValue = " + returnValue);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
```

这段代码会打印出『returnValue=2016051901』，其值是通过反射调用Order实例的私有方法『getOrderId()』获取到的。

`Order.class.getDeclaredMethod("getOrderId")`,这个方法也之后返回Order类自己的私有方法，而非其继承自父类的私有方法。

`privateMethod.setAcessible(true)`，这个方法会关闭实例类的私有方法反射访问检查，现在你可以通过反射获取的实例的私有，受保护和包级访问权限的方法。 

##### Field 变量

获取成员变量

```java
        Field[] fields = orderClass.getFields();
```

Field[]数组里面只包含类中public修饰的成员变量

如果知道类的成员变量名称，可以直接通过getField()方法获取到，成员变量的访问权限同样需要是public的

```java
        try {
            Field field = orderClass.getField("description");
            System.out.println(field.getName());//成员变量名称description
            System.out.println(field.getType());//成员变量类型class java.lang.String
        } catch (NoSuchFieldException e) {//如果不存在description的变量，会抛出NoSuchFieldException
            e.printStackTrace();
        }
```

获得了field的引用，就可以通过get()，set()方法，获取和设置成员变量的值

```java
        try {
            Field field = orderClass.getField("description");
            Order order = new Order();
            Object value = field.get(order);
            field.set(order, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
```

传入Field.get()/Field.set()方法的参数order是Order类的实例。
如果变量是静态变量的话(public static)那么在调用Field.get()/Field.set()方法的时候传入null做为参数而不用传递拥有该变量的类的实例(传入类的实例也是可以的)。

##### Annotations 注解

获取类的注解

```java
Annotation[] annotations = orderClass.getAnnotations();
```


