#FactoryDuke [![Build Status](https://travis-ci.org/regis-leray/factory_duke.png?branch=master)](https://travis-ci.org/regis-leray/factory_duke)

FactoryDuke is a java framework that lets you create objects as test data. All you have to do is define the templates for each of the classes that you want FactoryDuke to create objects from. After that, FactoryDuke takes care of the rest.

Have you ever heard of factory_girl a super cool Ruby framework? Well, FactoryDuke is pretty similar in its use.

Factory Duke is using a lot of lamdba, so it is only compatible with ```java 8``` and higher.

###Concept 

* Simple 
* Extensible
* Fast (no reflection)
* Very light (Only one dependency)

###Installing

Use it like a maven dependency on your project

```
<dependency>
    <groupId>com.github.regis-leray</groupId>
    <artifactId>factory-duke</artifactId>
    <version>0.3</version>
</dependency>
```

##How do we use this?

FactoryDuke is a singleton object where you can register all of the templates. For example, you can define a template as follows:

````java
FactoryDuke.define(User.class, u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(model.Role.USER);
		});
````

after you can use this definition

```java
model.User user = FactoryDuke.build(User.class);
```

If you need a full control of the bean creation you return the instance itself.

````java
FactoryDuke.define(User.class, () -> {
			User u = new User();
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(model.Role.USER);
			return u;
		});
````

You can extend factory by using the supplier approach

````java
FactoryDuke.define(User.class, "admin_user", () -> {
			User u = FactoryDuke.build(model.User.class);
			u.setRole(model.Role.ADMIN);
			return u;
		});
````

```java
User user = FactoryDuke.build(User.class, "admin_user");
```


##One Factory == One Java File

It is highly recommended that you have one factory for each class that provides the simplest set of attributes necessary to create an instance of that class.

###How to create a template factory

```java
public class UserFactory implements IFactory {

	@Override
	public void define(){
		FactoryDuke.define(model.User.class, u -> {
    			u.setLastName("Scott");
    			u.setName("Malcom");
    			u.setRole(model.Role.USER);
    		});
	}
}
```

All factories definitions can be loaded using FactoryDuke telling what package that contains the templates.
It will look by default under ```classpath:Factories, classpath:factories/**```

```
FactoryDuke.load();
```

You can specify you own package

```
FactoryDuke.load("x.x.custom.package");
```

Example of loading templates with JUnit tests

```
@BeforeClass
public static void setUp() {
    FactoryDuke.load());
}
```

The template are save in a static map, if you want to clear all the template you need to call

```
FactoryDuke.reset());
```

##Build list of object


Create a list / set of two exact same user
```
List<User> list = FactoryDuke.repeat(User.class).times(2).toList();
Set<User> sets = FactoryDuke.repeat(User.class).times(2).toSet()
```

If you need to use generator 
 
```
SequenceValuesGenerator<Long> ids = Generators.sequence();
SequenceValuesGenerator<String> names = Generators.values("Scott", "John", "Malcom");

FactoryDuke.define(User.class, u -> {
	u.setId(ids.nextValue());
	u.setName(names.nextValue());
});

List<User> users = FactoryDuke.repeat(User.class).times(2).toList();
 
``` 






##Todo(s)

* Define Hook to specify Persistence context (JPA : Hibernate / EclipseLink)

##Licence

FactoryPal is distributed under the [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html)
