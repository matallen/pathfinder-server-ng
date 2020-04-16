package com.redhat.pathfinder.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.redhat.pathfinder.model.Customer;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

@QuarkusTest
public class CustomerControllerTest {
	
	@BeforeEach
	public void init(){
		//Customer.deleteAll();
		Response r=given()
    .when().get("/api/pathfinder/customers");
		String body=r.getBody().asString();
		List<Map<String, String>> customers=JsonbBuilder.create().fromJson(body, List.class);
		for (Map<String, String> c:customers){
			given().when().delete("/api/pathfinder/customers/"+c.get("id"))
			.then().statusCode(204);
		}
	}
  @Test
  public void getEmptyCustomers() {
      given()
        .when().get("/api/pathfinder/customers")
        .then()
           .statusCode(200)
           .body(is("[]"));
  }
  @Test
  public void addNewCustomer() {
  	given()
	  	.body("{\"name\":\"Customer 1\"}")
	  	.contentType("application/json")
  		.when().post("/api/pathfinder/customers")
  		.then()
  			.statusCode(200)
  			.body(containsString("\"name\":\"Customer 1\""));
  			
  }
  @Test
  public void addCustomerWithSameName() {
  	given()
  	.body("{\"name\":\"Customer 2\"}")
  	.contentType("application/json")
		.when().post("/api/pathfinder/customers")
		.then()
			.statusCode(200)
			.body(containsString("\"name\":\"Customer 2\""));
  	
  	given()
  	.body("{\"name\":\"Customer 2\"}")
  	.contentType("application/json")
		.when().post("/api/pathfinder/customers")
		.then()
			.statusCode(500);
  	
  }

}