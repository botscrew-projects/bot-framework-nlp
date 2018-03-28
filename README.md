## NLP Client Spring Boot Starter

NLP Client project is integration with nlp providers based on Bot Framework

## Getting Started

* Add repository path to your build configuration

```
<repositories>
		<repository>
			<id>MyGet</id>
			<url>https://www.myget.org/F/bots-crew/maven</url>
		</repository>
	</repositories>
```
* Add dependency
```
<dependency>
    <groupId>com.botscrew</groupId>
    <artifactId>nlp-client-spring-boot-starter</artifactId>
    <version>${nlp-client.version}</version>
</dependency>
```

NLP Client already have dependency on bot framework so you don't need to add
it to project by yourself.

To send query to nlp provider you need autowire `com.botscrew.nlpclient.provider.NlpClient`
and send it there.

You can get entities from nlp provider as parameters in your handling method:

```
@Intent("name")
public void handleNameIntent(@Param("name") String name) {}
``` 

If you need to get some complex object you can define your own model for it and get it as param:

```
public class Age {
    private String unit;
    private Integer amount;
}

@Intent("age")
public void handleAgeIntent(@Param("age") Age age) {}
```

## Providers

* DialogFlow v1
To work with DialogFlow API you need define property `nlp.provider.dialog-flow.v1.client-token`

If you need get some specific data from original DialogFlow response you can get it like:

```
@Intent
public void intent(@NlpResponse AiResponse aiResponse) {}
```

