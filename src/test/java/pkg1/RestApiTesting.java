package pkg1;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiTesting {

	/*String baseURI = "http://restapi.demoqa.com/utilities/weather/city";
	String webMethod = "GET";
	String inputParameter = "Chennai";
	String inputKeyToVerify = "City";*/

	String baseURI = "https://sit-hydra.excelacom.in/centuryreports/service/eurekaServices/dynamicDashboard/getDashboardlist";
	String webMethod = "POST";
	String inputParameter = "?userId=1197";
	String inputKeyToVerify = "dashboardName";

	Response response;

	@Test
	public void webserviceValidation()
	{
		GetDetails(baseURI, webMethod, inputParameter, inputKeyToVerify);
	}


	public void GetDetails(String baseURI, String webMethod, String inputParameter, String inputKeyToVerify)
	{   
		RestAssured.baseURI = baseURI;

		RequestSpecification httpRequest = RestAssured.given();

		if(webMethod == "GET")
		{
			response = httpRequest.request(Method.GET, inputParameter);
		}
		else if(webMethod == "POST")
		{
			response = httpRequest.request(Method.POST, inputParameter);
		}

		int responseCode = response.getStatusCode();
		//String responseStatusLine = response.getStatusLine();
		String responseBody = response.asString();

		System.out.println("Response Status Code is : "+responseCode);
		System.out.println("Response Status Line is : "+response.getStatusLine());
		System.out.println("Response Body is : " + responseBody);

		System.out.println();

		if ( responseCode==200 && !(responseBody.contains("FaultId")) )
		{	

			JsonPath jsonPathEvaluator = response.jsonPath();

			//Check whether response value is a String or List
			String tempString =  jsonPathEvaluator.get(inputKeyToVerify).toString();
			char firstCharacter = tempString.charAt(0);

			if(firstCharacter == '[') //Code for List
			{
				List<String> jsonList =  jsonPathEvaluator.getList(inputKeyToVerify);
				System.out.println("Total Response Values Count : "+ jsonList.size());
				System.out.println();
				for (String temp: jsonList)
				{					 
					System.out.println("Response Value for - "+inputKeyToVerify+" = "+temp.trim());
				}
			}
			else //Code for single value
			{	
				String jsonValue =  jsonPathEvaluator.get(inputKeyToVerify).toString();
				System.out.println("Response Value for - "+inputKeyToVerify+" = "+jsonValue.trim());
			}

		}

		System.out.println();
		if ( responseCode==200)
		{
			System.out.println("HTTP Request Passed");
		}
		else
		{
			System.out.println("HTTP Request Failed");
		}
		System.out.println();
		
		

		/*		//Simple way

		JsonPath jsonPathEvaluator = response.jsonPath();

		//System.out.println("City received from Response : " + jsonPathEvaluator.get("City"));

		System.out.println("dashboardName received from Response : " + jsonPathEvaluator.get("dashboardName"));

		//String jsonValue =  jsonPathEvaluator.get("City").toString();
		String jsonValue =  jsonPathEvaluator.get("dashBoardId").toString();

		int arrayExist = jsonValue.indexOf(",");

		if(arrayExist >= 0)
		{

			String jsonValue2 = jsonValue.substring(1, jsonValue.length()-1);
			System.out.println(jsonValue2);

			String jsonArray[]= jsonValue2.split(",");
			System.out.println("Response Values Count : "+ jsonArray.length);

			for (String temp: jsonArray){					 
				System.out.println("Response Value is : "+temp.trim());
			}
		}
		else
		{
			System.out.println("Response Value is : "+jsonValue);
		}
		 */


	
	}

}

