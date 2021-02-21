package com.onlyautomationtesting.statuses;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.onlyautomationtesting.common.RestUtilities;
import com.onlyautomationtesting.constants.EndPoints;
import com.onlyautomationtesting.constants.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class UserTimelineTest {

	RequestSpecification  reqSpec;
	ResponseSpecification resSpec;
	
	@BeforeClass
	public void setUp()
	{
		reqSpec = RestUtilities.getRequestSpecification();
		reqSpec.queryParam("screen_name", "Rishi48136864");
		reqSpec.basePath(Path.STATUSES);
		
		resSpec = RestUtilities.getResponseSpecification();
	}
	
	@Test(enabled=false)
	public void readTweets1()
	{
		given()
			.spec(RestUtilities.createQueryParam(reqSpec, "count", "1"))
		.when()
			.get(EndPoints.STATUSES_USER_TIMELINE)
		.then()
			//.log().all()
			.spec(resSpec);
	}
	
	@Test(enabled=false)
	public void readTweets2()
	{
		RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(RestUtilities.createQueryParam(reqSpec, "count","2"),"get", true);
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println(screenNameList);
		Assert.assertTrue(screenNameList.contains("Rishi48136864"));
	}

	// add query parameters using hashmap
	@Test(enabled=true)
	public void readTweets3()
	{
		
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("count", "2");
		
		RestUtilities.setEndPoint(EndPoints.STATUSES_USER_TIMELINE);
		Response res = RestUtilities.getResponse(RestUtilities.createQueryParam(reqSpec, queryMap),"get", false);
		ArrayList<String> screenNameList = res.path("user.screen_name");
		System.out.println(screenNameList);
		Assert.assertTrue(screenNameList.contains("Rishi48136864"));
	}
	
}
