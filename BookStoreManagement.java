package Bookstore;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class ReqResTests {

	@BeforeClass
	public void setup() {
		RestAssured.baseURI = "https://reqres.in/api";
	}

	@Test
	public void testGetUsers() {
		RestAssured.given().when().get("/users").then().statusCode(200).body("page", equalTo(1))
				.body("data", notNullValue()).log().all();
	}

	@Test
	public void testGetUserById() {
		RestAssured.given().pathParam("id", 2).when().get("/users/{id}").then().statusCode(200)
				.body("data.id", equalTo(2)).body("data.email", equalTo("janet.weaver@reqres.in"))
				.body("data.first_name", equalTo("Janet")).body("data.last_name", equalTo("Weaver"));
	}

	@Test
	public void testCreateUser() {
		String requestBody = "{ \"name\": \"John\", \"job\": \"Developer\" }";

		RestAssured.given()
		.body(requestBody)
		.when()
		.post("/users")
		.then()
		.statusCode(201);
	}

	@Test
	public void testUpdateUser() {
		String requestBody = "{ \"name\": \"John\", \"job\": \"Senior Developer\" }";

		RestAssured.given()
		.pathParam("id", 2)
		.body(requestBody)
		.when()
		.put("/users/{id}")
		.then()
		.statusCode(200);
	}

	@Test
	public void testPatchUser() {
		String requestBody = "{ \"job\": \"Lead Developer\" }";

		RestAssured.given()
		.pathParam("id", 2)
		.body(requestBody)
		.when()
		.patch("/users/{id}")
		.then()
		.statusCode(200);
	}

	@Test
	public void testDeleteUser() {
		RestAssured.given()
		.pathParam("id", 2)
		.when()
		.delete("/users/{id}")
		.then()
		.statusCode(204); 
	}
}