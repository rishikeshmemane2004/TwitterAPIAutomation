package com.onlyautomationtesting.statuses;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.onlyautomationtesting.common.RestUtilities;
import com.onlyautomationtesting.constants.EndPoints;
import com.onlyautomationtesting.constants.Path;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;


public class TwitterWorkflowTest {

	
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;
	String tweetId ="";
	
	@BeforeClass
	public void setUp()
	{
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.basePath(Path.STATUSES);
		
		resSpec = RestUtilities.getResponseSpecification();
	}
	
	@Test
	public void postTweet()
	{
		Response response = given()
			.spec(RestUtilities.createQueryParam(reqSpec, "status", "My Seventh Tweet"))
		.when()
			.post(EndPoints.STATUSES_TWEET_POST)
		.then()
			.spec(resSpec)
			.extract()
			.response();
		
		JsonPath jsPath = RestUtilities.getJsonPath(response);
		tweetId = jsPath.get("id_str");
		System.out.println("Tweet Id : " + tweetId);
	}
	
	@Test(dependsOnMethods={"postTweet"}, enabled=true)
	public void readTweet()
	{
		RestUtilities.setEndPoint(EndPoints.STATUSES_TWEET_READ_SINGLE);
		Response res = RestUtilities.getResponse(
				RestUtilities.createQueryParam(reqSpec, "id",tweetId),
				"get", false);
		
		String text = res.path("text");
		System.out.println("Text : " + text);
	}

	@Test(dependsOnMethods={"readTweet"}, enabled=true)
	public void deleteTweet()
	{
		given()
			.spec(RestUtilities.createPathParam(reqSpec, "id", tweetId))
		.when()
			.post(EndPoints.STATUSES_TWEET_DESTROY)
		.then()	
			.spec(resSpec);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

