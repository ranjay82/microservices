package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Quotes;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@RestController
@RequestMapping("/rest/user")
public class UserController {
	
	@Autowired
	private RestTemplate restTemplate;
	
/*	@HystrixCommand(fallbackMethod = "fallback", groupKey = "User",
			commandKey = "user",
			threadPoolKey = "userThread")*/
	
	
	@GetMapping("/{username}")
	public String getUser(@PathVariable("username") final String userName) {
		 String url = "http://db-service/rest/db/" + userName;
	        return restTemplate.getForObject(url, String.class)+ " including client";
		// return "hello";
	}

	@PostMapping("/add")
	 public String add(@RequestBody final Quotes quotes) {
		String url = "http://localhost:8051/rest/db/add";
	//	String url = "http://db-service/rest/db/add";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(quotes.toString(),headers);
		return restTemplate.postForObject(url, entity, String.class);
	 }

	
	public String fallback(Throwable hystrixCommand) {
        return "Fall Back Hello User";
	}
}