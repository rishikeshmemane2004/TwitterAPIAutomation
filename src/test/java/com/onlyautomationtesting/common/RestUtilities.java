package com.onlyautomationtesting.common;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.onlyautomationtesting.constants.Auth;
import com.onlyautomationtesting.constants.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RestUtilities {

	public static String                ENDPOINT;
	public static RequestSpecBuilder    REQUEST_BUILDER;
	public static RequestSpecification  REQUEST_SPEC;
	public static ResponseSpecBuilder   RESPONSE_BUILDER;
	public static ResponseSpecification RESPONSE_SPEC;

	public static void setEndPoint(String ePoint) {
		ENDPOINT = ePoint;
	}

	// Create common REQUEST_SPEC object for APIs
	public static RequestSpecification getRequestSpecification() {
		AuthenticationScheme authScheme = RestAssured.oauth(Auth.CONSUMER_KEY, Auth.CONSUMER_SECRET, Auth.ACCESS_TOKEN,
				Auth.ACCESS_SECRET);

		REQUEST_BUILDER = new RequestSpecBuilder();
		REQUEST_BUILDER.setBaseUri(Path.BASE_URI);
		REQUEST_BUILDER.setAuth(authScheme);

		REQUEST_SPEC = REQUEST_BUILDER.build();

		return REQUEST_SPEC;
	}

	public static ResponseSpecification getResponseSpecification() {
		RESPONSE_BUILDER = new ResponseSpecBuilder();
		RESPONSE_BUILDER.expectStatusCode(200);
		RESPONSE_BUILDER.expectResponseTime(lessThan(20L), TimeUnit.SECONDS);

		RESPONSE_SPEC = RESPONSE_BUILDER.build();
		return RESPONSE_SPEC;
	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, String value) {
		return rspec.queryParam(param, value);
	}

	// Create Query Parameter overloaded method to get different types of value and
	// accepts only one query parameter
	public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, int value) {
		return rspec.queryParam(param, value);
	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, float value) {
		return rspec.queryParam(param, value);
	}

	public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, boolean value) {
		return rspec.queryParam(param, value);
	}

// 	Add multiple query parameters using Map<String, String>
	public static RequestSpecification createQueryParam(RequestSpecification rspec, Map<String, String> queryMap) {
		return rspec.queryParams(queryMap);
	}

// 	*** How to Add multiple query parameters using Map<String, Integer, Boolean, Float etc>

// Add path param of string value
	public static RequestSpecification createPathParam(RequestSpecification rspec, String param, String value) {
		return rspec.pathParam(param, value);
	}

	// Add path param of int value
	public static RequestSpecification createPathParam(RequestSpecification rspec, String param, int value) {
		return rspec.pathParam(param, value);
	}

	// Add path param of float value
	public static RequestSpecification createPathParam(RequestSpecification rspec, String param, float value) {
		return rspec.pathParam(param, value);
	}

	// Add path param of boolean value
	public static RequestSpecification createPathParam(RequestSpecification rspec, String param, boolean value) {
		return rspec.pathParam(param, value);
	}
// create multiple path parameters using map	
	public static RequestSpecification createPathParam(RequestSpecification rspec, 
			Map<String, String> pathParam) {
		return rspec.pathParams(pathParam);
	}
	

	public static Response getResponse() {
		return given().get(ENDPOINT);
	}

	public static Response getResponse(RequestSpecification reqspec, String type, boolean logFlag) {
		REQUEST_SPEC.spec(reqspec);
		Response response = null;

		if (type.equalsIgnoreCase("get")) {
			response = given().spec(REQUEST_SPEC).get(ENDPOINT);
		} else if (type.equalsIgnoreCase("post")) {
			response = given().spec(REQUEST_SPEC).post(ENDPOINT);
		} else if (type.equalsIgnoreCase("put")) {
			response = given().spec(REQUEST_SPEC).put(ENDPOINT);
		} else if (type.equalsIgnoreCase("delete")) {
			response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
		} else {
			System.out.println("Type is not suported " + type);
		}
		if (logFlag)
			response.then().log().all();
		
		response.then().spec(RESPONSE_SPEC).log().ifValidationFails();  // validate response like status code, response time etc

		return response;
	}

	// converting Response JAva object to JSON object
	public static JsonPath getJsonPath(Response res)
	{
		String path = res.asString();
		return new JsonPath(path);
	}
	
	// converting Response JAva object to XML object
	public static XmlPath getXmlPath(Response res)
	{
		String path = res.asString();
		return new XmlPath(path);
	}
	
	public static void resetBasePath()
	{
		RestAssured.basePath = null;
	}
	
	// can set Content type as JSON or XML
	public static void setContentType(ContentType type)
	{
		given().contentType(type);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
