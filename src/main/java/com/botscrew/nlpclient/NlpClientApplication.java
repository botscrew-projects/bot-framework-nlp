package com.botscrew.nlpclient;

import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowClient;
import com.botscrew.nlpclient.provider.dialogflow.v1.model.Response;
import com.botscrew.nlpclient.provider.dialogflow.v1.model.parameter.Age;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

@SpringBootApplication
public class NlpClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NlpClientApplication.class, args);
		DialogFlowClient client = context.getBean(DialogFlowClient.class);
//		Response response = client.query("My name is Vlad");
		Response response = client.query("My name is Vlad and I am 20 years old");

		System.out.println(response);
		System.out.println(response.getResult().getParameters());
	}
}
