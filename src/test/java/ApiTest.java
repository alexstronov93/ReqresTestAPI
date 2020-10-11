import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class ApiTest {
    @Test(description = "GET")
    public void getAllUsers() {
        System.out.println("-----DATA-----");

        get("https://reqres.in/api/users?page=1")
                .then()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("page", equalTo(1));
    }

    @Test(description = "GET")
    public void getSingleUser() {
        System.out.println("-----DATA-----");

        get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .log().body()
                .assertThat()
                .body("data.id", equalTo(2));
    }

    @Test(description = "GET")
    public void getNotFoundUser() {
        System.out.println("-----DATA-----");

        get("https://reqres.in/api/users/23")
                .then()
                .log().body()
                .statusCode(404)
                .assertThat()
                .body("data", equalTo(null));
    }

    @Test(description = "POST")
    public void postUser() {
        System.out.println("-----DATA-----");

        String user = "{\n" +
                "  \"name\": \"morpheus\",\n" +
                "  \"job\": \"coder\"}";
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .assertThat()
                .body("job", equalTo("coder"))
                .log().body()
                .extract().response();
    }

    @Test(description = "PUT")
    public void updateUser() {
        System.out.println("-----DATA-----");

        String user = "{\n" +
                "  \"name\": \"morpheus\",\n" +
                "  \"job\": \"zion resident\"}";
        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users/2")
                .then()
                .statusCode(201)
                .assertThat()
                .body("name", equalTo("morpheus"))
                .log().body();

    }

    @Test(description = "POST")
    public void registerNewUserFail(){
        System.out.println("-----DATA-----");
        String email="{\n" +
                "  \"email\": \"sydney@fife\"}";
        given()
                .contentType(ContentType.JSON)
                .body(email)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .assertThat()
                .body("error", equalTo("Missing password"))
                .log().body();
    }

}
