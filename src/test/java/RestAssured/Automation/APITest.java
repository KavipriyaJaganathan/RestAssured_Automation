package RestAssured.Automation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import static io.restassured.RestAssured.given;
import junit.framework.Assert;

public class APITest{
	
	private String baseURI="https://jsonplaceholder.typicode.com"; 
	
	@BeforeMethod
	public void setBaseURI() {
		
		RestAssured.baseURI = baseURI;
	}
	

	@Test public void getRandomEmail_Users() {

		
		int userID=10;

		//GET USER ID 
		String userEmail = given()
				. get("/users/" +userID) 
				.then()
				.statusCode(200) 
				.log().all() 
				.extract() 
				.jsonPath() 
				.getString("email");
		System.out.println(userEmail);
	}

	@Test
	public void getUsers_PostID() {

		
		int userID=10;

		//GET POST ID 
		Response response = given() 
				.when() 
				.get("/comments/" +userID);

		if(response.getStatusCode() !=200) 
		{ 
			System.out.println("Failed to fetch userand post ID details. Status code:" + response.getStatusCode());
		}
		else

		{ 
			int postID = response.jsonPath().getInt("postId"); 
			System.out.println(postID);
			assertTrue(postID >=1 && postID <= 100,"Post ID is not within the valid range(1-100)"); 
			System.out. println("Assertion passed: Post ID is within the validrange (1-100)"); 
		}

	}
@Test
	public void create_Postrequest()
	{
		

		String requestBody = "{\"userId\": 4, \"title\": \"Test Title\", \"body\": \"Test Body\"}";

		Response response = given()
				.contentType(ContentType.JSON)
				.body(requestBody)
				.post("/posts");

		assertEquals(response.getStatusCode(), 201, "Unexpected status code");
		assertNotNull(response.getBody(), "Response body is null");
		System.out.println("Response Body:");
		System.out.println(response.getBody().asString());

	}


}