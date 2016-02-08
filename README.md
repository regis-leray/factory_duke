#FactoryDuke 

[![Build Status](https://travis-ci.org/regis-leray/factory_duke.png?branch=master)](https://travis-ci.org/regis-leray/factory_duke)
[![Maven Central](https://img.shields.io/maven-central/v/com.github.regis-leray/factory-duke.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.github.regis-leray/factory-duke)
[![Coverage Status](https://coveralls.io/repos/github/regis-leray/factory_duke/badge.svg?branch=master)](https://coveralls.io/github/regis-leray/factory_duke?branch=master)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://vtence.mit-license.org)


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
    <version>0.4</version>
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
User user = FactoryDuke.build(User.class);
```

You can override some fields during the creation of the instance

```java
User user = FactoryDuke.build(User.class, u -> u.setRole(Role.ADMIN));
```

If you need a full control of the bean creation you can return the instance itself.

````java
FactoryDuke.define(User.class, () -> {
			User u = new User();
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(model.Role.USER);
			return u;
		});
````

You can use factory inside a definition by using the supplier approach, which provide you the ability to extends Factory

````java
FactoryDuke.define(User.class, "admin_user", () -> {
			//create instance from the default definition of User.class
			User u = FactoryDuke.build(User.class);
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
public class UserFactory implements TFactory {

	@Override
	public void define(){
		FactoryDuke.define(User.class, u -> {
    			u.setLastName("Scott");
    			u.setName("Malcom");
    			u.setRole(model.Role.USER);
    		});
	}
}
```

Use ```load()``` to load all factories definitions from the default locations : ```classpath:Factories, classpath:factories/**```, 

```
FactoryDuke.load();
```

You can specify you own package

```
FactoryDuke.load("x.x.custom.package");
```

Example of loading templates with JUnit tests

```
@Before
public void setUp() {
    FactoryDuke.load();
}
```

The templates are save in a static map, if you want to clear all the templates you need to call

```
FactoryDuke.reset();
```

##Build list of object


Create a list / set of two exact same user
```
List<User> list = FactoryDuke.repeat(User.class).times(2).toList();

Set<User> sets = FactoryDuke.repeat(User.class).times(2).toSet()
```

If you need to use generator 
 
```
// the default generator is starting at 1 and incrementing by 1
SequenceValuesGenerator<Long> ids = Generators.sequence();

// constant will return always the same sequence of values
SequenceValuesGenerator<String> names = Generators.values("Scott","John","Malcom");

FactoryDuke.define(User.class, "generator_users", u -> {
	//each generator's id call will return a unique value (1,2,3,4,5, ...)
	u.setId(ids.nextValue());
	//each generator's name call will return a value ("Scott","John","Malcom","Scott","John","Malcom", ...)
	u.setName(names.nextValue());
});

// will return 3 differents users
List<User> users = FactoryDuke.repeat(User.class, "generator_users").times(3).toList();
 
``` 

##Global callback

If you need to setup a share behavior(s) you can now register global callback(s)

```
FactoryDuke.load().registerGlobalCallback(System.out::println)
```


##Licence

FactoryDuke is distributed under the [MIT licence](https://opensource.org/licenses/MIT)
