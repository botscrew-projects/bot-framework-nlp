## NLP Client Spring Boot Starter

NLP Client project is integration with nlp providers based on Bot Framework.
It uses [IntentContainer](https://gitlab.com/bots-crew/bot-framework/blob/boot-starter/src/main/java/com/botscrew/botframework/container/IntentContainer.java)
from Bot Framework. To read more check it here:

https://gitlab.com/bots-crew/bot-framework/blob/boot-starter/README.md

##### 1. [Getting started] (https://gitlab.com/bots-crew/nlp-client/blob/develop/README.md#getting-started)
##### 2. [Usage] (https://gitlab.com/bots-crew/nlp-client/blob/develop/README.md#usage)
##### 3. [Providers] (https://gitlab.com/bots-crew/nlp-client/blob/develop/README.md#providers)

## Getting Started

* Add repository path to your build configuration

```xml
<repositories>
    <repository>
        <id>MyGet</id>
        <url>https://www.myget.org/F/bots-crew/maven</url>
    </repository>
</repositories>
```
* Add dependency

```xml
<dependency>
    <groupId>com.botscrew</groupId>
    <artifactId>nlp-client-spring-boot-starter</artifactId>
    <version>${nlp-client.version}</version>
</dependency>
```

NLP Client already have dependency on bot framework so you don't need to add
it to project by yourself.

### Usage
To send query to nlp provider you need autowire [NlpClient](src/main/java/com/botscrew/nlpclient/provider/NlpClient.java)
and send it there.

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

You can get entities from nlp provider as parameters in your handling method:

```java
@Intent("name")
public void handleNameIntent(User user, @Param("name") String name) {
    // ...
}
``` 

If you need to get some complex object you can define your own model for it and get it as param:

```java
public class Age {
    private String unit;
    private Integer amount;
}

// ...

@Intent("age")
public void handleAgeIntent(@Param("age") Age age) {}
```

`IntentContainer` supports user states, so you are able to define different intent handlers for different user states:

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

To work with DialogFlow API you need define property `nlp.provider.dialog-flow.v1.client-token`

If you need get some specific data from original DialogFlow response you can get it like:

```java
@Intent
public void intent(@NlpResponse AiResponse aiResponse) {
    // ...
}
```

