package com.botscrew.nlpclient;

import com.botscrew.nlpclient.provider.dialogflow.v1.DialogFlowClient;
import com.botscrew.nlpclient.provider.dialogflow.v1.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class NlpClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NlpClientApplication.class, args);
		DialogFlowClient client = context.getBean(DialogFlowClient.class);
		Response response = client.query("test");

		System.out.println(response);
	}
}
