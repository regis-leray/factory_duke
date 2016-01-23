#FactoryDuke [![Build Status](https://travis-ci.org/regis-leray/factory_duke.png?branch=master)](https://travis-ci.org/regis-leray/factory_duke)

FactoryDuke is a java framework that lets you create objects as test data. All you have to do is define the templates for each of the classes that you want FactoryDuke to create objects from. After that, FactoryDuke takes care of the rest.

Have you ever heard of factory_girl a super cool Ruby framework? Well, FactoryDuke is pretty similar in its use.

##How do we use this?

FactoryDuke is a singleton object where you can register all of the templates. For example, you can define a template as follows:

````java
FactoryDuke.define(model.User.class, u -> {
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(model.Role.USER);
		});
````

after you can use this definition

```java
model.User user = FactoryDuke.build(model.User.class);
```

If you need a full control of the bean creation you return the instance itself.

````java
FactoryDuke.define(model.User.class, () -> {
			User u = new User();
			u.setLastName("Scott");
			u.setName("Malcom");
			u.setRole(model.Role.USER);
			return u;
		});
````

or

````java
FactoryDuke.define(model.User.class, "admin_user", () -> {
			User u = FactoryDuke.build(model.User.class);
			u.setRole(model.Role.ADMIN);
			return u;
		});
````


##One Factory == One Java File

By default FactoryDuke will load all available factories definitions through the classloader.

```
classpath:Factories
classpath:factories/**
```

It is highly recommended that you have one factory for each class that provides the simplest set of attributes necessary to create an instance of that class.


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

or with Annotations

```java
@Factory
public class UserFactory {

	@Factory.Define
	public void define(){
		FactoryDuke.define(model.User.class, u -> {
    			u.setLastName("Scott");
    			u.setName("Malcom");
    			u.setRole(model.Role.USER);
    		});
	}
}
```

##Todo(s)

* Add Block FactoryDuke.define(User.class, "").andThen(User::setAddr, () -> { return User; })
FactoryDuke.define(User.class, "").andThen(User::setAddr,)
* Add custom packages for factories
* Call multiple time factory (repeat)
* Create sequence generator
* Define hook to link a DI context (Spring / Guice)
* Define Hook to specify Persistence context (JPA : Hibernate / EclipseLink)


##Licence

FactoryPal is distributed under the [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html)
