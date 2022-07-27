package com.userdetail.microservice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;


@SpringBootTest

public class MicroserviceApplicationTests {

	@BeforeTest
	public void setup() {
		RestAssured.baseURI = "http://localhost:8089/";
	}

	@AfterTest
	public void tearDown() {

	}

	@org.testng.annotations.Test(priority = 1)
	public void shouldReturnNoUserDetailsFoundthenOK(){
		String BasePath = "users";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.get(URL);
		Assert.assertEquals(res.getStatusCode(),404);
		Assert.assertEquals(res.getContentType(),"application/json");
	}

	@org.testng.annotations.Test(priority = 2)
	public void shouldReturnNoUserDetailsFoundForAGivenIdthenOK(){
		String BasePath = "users/999";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.get(URL);
		Assert.assertEquals(res.getStatusCode(),404);
		Assert.assertEquals(res.getContentType(),"application/json");
	}


	@org.testng.annotations.Test(priority = 3)
	public void shouldPostUserContactDetailsthenOK() throws JSONException {

		String URL = RestAssured.baseURI +  "user";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		JSONObject jsonUser = new JSONObject();
		JSONObject jsonAddress = new JSONObject();
		jsonUser.put("firstName","firstName");
		jsonUser.put("lastName","lastName");
		jsonUser.put("phoneNo","1234567899");
		jsonAddress.put("doorNo","3A");
		jsonAddress.put("streetName","streetName");
		jsonAddress.put("postCode","postCode");
		jsonUser.put("address",jsonAddress);
		request.body(jsonUser.toJSONString());
		Response res =  request.post(URL);
		System.out.println("URL:"  + URL);
		System.out.println((res.getBody()).toString());
		System.out.println(res.getContentType());
		Assert.assertEquals(201,res.getStatusCode());
		Assert.assertEquals(res.getContentType(),"application/json");

	}

	@org.testng.annotations.Test(priority = 4)
	public void shouldNotPostUserContactDetailsForMissingAttributesthenOK() throws JSONException {

		String URL = RestAssured.baseURI +  "user";
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		JSONObject jsonUser = new JSONObject();
		JSONObject jsonAddress = new JSONObject();
		jsonUser.put("firstName","");
		jsonUser.put("lastName","lastName");
		jsonUser.put("phoneNo","");
		jsonAddress.put("doorNo","3A");
		jsonAddress.put("streetName","streetName");
		jsonAddress.put("postCode","postCode");
		jsonUser.put("address",jsonAddress);
		request.body(jsonUser.toJSONString());
		Response res =  request.post(URL);
		System.out.println("URL:"  + URL);
		System.out.println((res.getBody()).toString());
		System.out.println(res.getContentType());
		Assert.assertEquals(400,res.getStatusCode());
		Assert.assertEquals(res.getContentType(),"application/json");

	}

	@org.testng.annotations.Test(priority = 5)
	public void shouldGetUserDetailsForAGivenIdthenOK(){
		String BasePath = "user/1";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.get(URL);
		Assert.assertEquals(res.getStatusCode(),200);
		Assert.assertEquals(res.getContentType(),"application/json");
	}

	@org.testng.annotations.Test(priority = 6)
	public void shouldGetUsersDetailsForAllUsersthenOK(){
		String BasePath = "users";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.get(URL);
		Assert.assertEquals(res.getStatusCode(),200);
		Assert.assertEquals(res.getContentType(),"application/json");
	}

	@org.testng.annotations.Test(priority = 7)
	public void shouldUpdateUserDetailsForAUserthenOK(){
		String BasePath = "user/1";
		String URL = RestAssured.baseURI +  BasePath;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		JSONObject jsonUser = new JSONObject();
		JSONObject jsonAddress = new JSONObject();
		jsonUser.put("firstName","updatefirstName");
		jsonUser.put("lastName","updatelastName");
		jsonUser.put("phoneNo","1232567899");
		jsonAddress.put("doorNo","3A");
		jsonAddress.put("streetName","streetName");
		jsonAddress.put("postCode","postCode");
		jsonUser.put("address",jsonAddress);
		request.body(jsonUser.toJSONString());
		Response res =  request.put(URL);

		System.out.println((res.getBody()).toString());
		System.out.println(res.getContentType());
		Assert.assertEquals(200,res.getStatusCode());
		Assert.assertEquals(res.getContentType(),"application/json");
	}

	@org.testng.annotations.Test(priority = 9)
	public void shouldNotUpdateUserDetailsForAUserWhoIsNotExistthenOK(){
		String BasePath = "user/999";
		String URL = RestAssured.baseURI +  BasePath;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type","application/json");
		JSONObject jsonUser = new JSONObject();
		JSONObject jsonAddress = new JSONObject();
		jsonUser.put("firstName","updatefirstName");
		jsonUser.put("lastName","updatelastName");
		jsonUser.put("phoneNo","1232567899");
		jsonAddress.put("doorNo","3A");
		jsonAddress.put("streetName","streetName");
		jsonAddress.put("postCode","postCode");
		jsonUser.put("address",jsonAddress);
		request.body(jsonUser.toJSONString());
		Response res =  request.put(URL);

		System.out.println((res.getBody()).toString());
		System.out.println(res.getContentType());
		Assert.assertEquals(404,res.getStatusCode());
		Assert.assertEquals(res.getContentType(),"application/json");
	}

	@org.testng.annotations.Test(priority = 10)
	public void shouldDeleteUserDetailsForAGivenUserIdthenOK(){
		String BasePath = "user/1";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.delete(URL);
		Assert.assertEquals(res.getStatusCode(),200);
		Assert.assertEquals(res.getContentType(),"text/plain;charset=UTF-8");
	}

	@org.testng.annotations.Test(priority = 11)
	public void shouldReturnUserAlreadyDeletedForaUserthenOK(){
		String BasePath = "user/1";
		String URL = RestAssured.baseURI +  BasePath;
		Response res =  RestAssured.delete(URL);
		Assert.assertEquals(res.getStatusCode(),404);
		Assert.assertEquals(res.getContentType(),"application/json");
	}

}
