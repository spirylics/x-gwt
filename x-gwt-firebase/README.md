# Firebase

## What does Firebase do ?

Just a [firebase](https://firebase.google.com/) wrapper.

## How use it ?

* Add js library as you want, like :

```html
<script src="bower_components/firebase/firebase.js"></script>
```

* Add maven dependency :

```xml
<dependency>
    <groupId>com.github.spirylics.xgwt</groupId>
    <artifactId>x-gwt-firebase</artifactId>
    <version>XXX</version>
</dependency>
```

* Based on [gwt-jackson](https://github.com/nmorel/gwt-jackson), initialize json mapping such as :

```java
XMapper.addMappers(ImmutableMap.of(
            Foo.class, GWT.<ObjectMapper>create(FooMapper.class),
            Bar.class, GWT.<ObjectMapper>create(BarMapper.class));
```

* Initialize Firebase :

```java
Config config = new Config();
config.setApiKey("####");
config.setAuthDomain("####");
config.setDatabaseURL("####");
config.setStorageBucket("####");
Firebase firebase = Firebase.initializeApp(config);
```

* Get foo :

```java
Reference reference = getFirebase().database().ref().xChild("foo", "1");
reference.xOnce(Event.value).then(
    fooSnapshot -> { Foo foo = starSnapshot.val(Foo.class); }, 
    err -> logger.severe(":(" + err));
```



