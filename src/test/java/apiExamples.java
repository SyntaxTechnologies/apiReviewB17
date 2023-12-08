import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class apiExamples {

//    set the base uri
String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MDE5MDk2OTgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcwMTk1Mjg5OCwidXNlcklkIjoiNjE2MCJ9.MP5EF86_JTVIk6QdBqqWa2WUU-36Nmya-k7x0VJ62tg";

@Test
    public void createAnEmployee(){
//prepare the request
    RequestSpecification request = given().header("Content-Type", "application/json").header("Authorization", token)
            .body("{\n" +
                    "  \"emp_firstname\": \"abra\",\n" +
                    "  \"emp_lastname\": \"ca\",\n" +
                    "  \"emp_middle_name\": \"dabra\",\n" +
                    "  \"emp_gender\": \"M\",\n" +
                    "  \"emp_birthday\": \"2023-12-01\",\n" +
                    "  \"emp_status\": \"unemployee\",\n" +
                    "  \"emp_job_title\": \"none\"\n" +
                    "}");

//    send the request to the endpoint
    Response response = request.when().post("/createEmployee.php");

//    response.prettyPrint();
//    extract the response in the form of string
    ResponseBodyExtractionOptions resp = response.then().extract().body();
    System.out.println(resp.asString());

//    extract the value of the key message
    String message = response.jsonPath().getString("Message");
//    asseretion
    Assert.assertEquals(message,"Employee Created");

//    Assert that the employee firstname is abra
    String fname = response.jsonPath().getString("Employee.emp_firstname");

    Assert.assertEquals(fname,"abra");







}



}
