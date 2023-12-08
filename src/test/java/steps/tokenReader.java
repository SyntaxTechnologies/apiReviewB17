package steps;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utlis.Constants;
import utlis.Payload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;

import static io.restassured.RestAssured.given;

public class tokenReader {
public static String token;
    @Given("a JWT is generated")
    public void a_jwt_is_generated() throws IOException {
//        checking if the previous token is still valid or not
        if(!isTokenValid()){
//             generate a new token
            RequestSpecification request = given().header(Constants.Header_Content_Type_Key, Constants.Content_type_Value)
                    .body(Payload.generateTokenPayload());
            Response response = request.when().post(Constants.GENERATE_TOKEN_URI);
            String rawToken = response.jsonPath().getString("token");
            token="Bearer "+rawToken;
//            get the expiration time
            LocalDateTime expirationTime = getTheExpirationTime(rawToken);
//            write the token and expiration time to file
            saveTokenToFile(token,expirationTime);
        }
        else{
              token=readTokenFromFile();

        }
    }

//    return true if token is valid
//    false if token is expired
    public static boolean isTokenValid() throws IOException {
// read the file for the token
        String content = new String(Files.readAllBytes(Paths.get("token.txt")));

//      the file is empty
     if(content.isEmpty()){
         return false;
     }

//     get the time form the content string
        String[] parts = content.split("\n");
//     get the time out of the string array
//        and convert it into the local date Time format
        LocalDateTime expirationTime = LocalDateTime.parse(parts[1]);
//        compare the time now and expiration time
        LocalDateTime currentDateTime = LocalDateTime.now();
//        return true if local time is before else return false
        return currentDateTime.isBefore(expirationTime);


    }

    public static LocalDateTime getTheExpirationTime(String token){
//        decode the jwt token
        DecodedJWT jwt = JWT.decode(token);

//        retrieve the expiration of the token
        Date exp = jwt.getExpiresAt();
//        convert the expiration time to LOCAL DATE AND TIME FORMAT
        return exp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

    }

    public void saveTokenToFile(String token, LocalDateTime exptime) throws IOException {
        String content=token+"\n"+exptime;
//writing hte string content in the file token.txt after
//        converting it in the format of bytes  (content.getBytes())
        Files.write(Paths.get("token.txt"),content.getBytes());

    }


    public  static String readTokenFromFile() throws IOException {
//read the content of the file and convert it into a string
        String content = new String(Files.readAllBytes(Paths.get("token.txt")));
//        extract the token from the content
        return content.split("\n")[0];

    }
}
