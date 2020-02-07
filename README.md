## NLP Client Spring Boot Starter

NLP Client project provides integration with various nlp clients (only dialog-flow for now). It is based on Bot Framework.
It uses [IntentContainer](https://github.com/botscrew-projects/bot-framework-core/blob/boot-starter/src/main/java/com/botscrew/botframework/container/IntentContainer.java)
from Bot Framework. To read more about it, check the link below:

https://github.com/botscrew-projects/bot-framework-core


## Getting Started

* Add `NLP Client` dependency

```xml
<dependency>
    <groupId>com.botscrew</groupId>
    <artifactId>bot-framework-nlp-spring-boot-starter</artifactId>
    <version>1.1.2</version>
</dependency>
```

NLP Client already depends on the [Bot Framework](https://github.com/botscrew-projects/bot-framework-core) so you don't need to add
it to project by yourself.

### Usage
To send query to the nlp provider you need to autowire [NlpClient](src/main/java/com/botscrew/nlpclient/provider/NlpClient.java)
and call `NlpClient#query()` method

```java
public class TextHandler {
    @Autowired
    private NlpClient nlpClient;
    
    @Text
    public void handleText(User user, @Text String text) {
        nlpClient.query(user, text);
    }
}
```

You will be able to get an answer in your Intent handling method:

```java
@Intent
public void handleIntent(User user, @Text String originalQuery) {
    // ...
}
```

You can get entities from the nlp provider as parameters in your handling method:

```java
@Intent("name")
public void handleNameIntent(User user, @Param("name") String name) {
    // ...
}
``` 

If you need to get any complex objects you can define your own model and get them as parameters:

```java
public class Age {
    private String unit;
    private Integer amount;
}

// ...

@Intent("age")
public void handleAgeIntent(@Param("age") Age age) {}
```

`IntentContainer` supports user states so, you will be able to define different intent handlers for different user states:

```java
@Intent(value = "PHONE_NUMBER", states = {"DEFAULT"})
public void handlePhoneNumberIntent(User user, @Param("number") String number) {
    // ...
}

@Intent(value = "PHONE_NUMBER", states = {"ONBOARDING"})
public void handlePhoneNumberInOnboarding(User user, @Param("number") String number) {
    // ...
}
```

## Providers

* DialogFlow APIv1

To work with DialogFlow API you need define the following properties:
 `nlp.provider.dialog-flow.v1.client-token`

If you need get some specific data from the original DialogFlow response, 
you can get it by using the `@NlpResponse` annotation:

```java
@Intent
public void intent(@NlpResponse AiResponse aiResponse) {
    // ...
}
```

