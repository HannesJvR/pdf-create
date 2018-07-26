package com.ey.za.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PdfObject {
  static int numOfPdfObjects = 0;	
  PdfDocument parentDocument; 	
  int id = 0;
  int length = 0;
  String type = "undefined";
  String text = "";
  String sComment = "";
   int numOfBytes = 0;
  static final DecimalFormat INT_FORMAT = new DecimalFormat("##0");
  //String filepath = "";
  
//  String testString = "This Is Test";
//  char[] stringToCharArray = testString.toCharArray();
//  byte[] jsonData = testString.getBytes(StandardCharsets.UTF_8);

  //byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));
  //ObjectMapper objectMapper4 = new ObjectMapper();
	
	//create JsonNode
  	//JsonNode rootNode4 = objectMapper4.readTree(jsonData);
//	System.out.println("rootNode4 = ");  
//	System.out.println(rootNode4);  
	//rootNode4 = objectMapper4.createObjectNode();
	//update JSON data
	//((ObjectNode) rootNode4).put("id", 500);
	//add new key value
	//((ObjectNode) rootNode4).put("test", "test value");

/*  String createObjectAttributesInfo() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		
		//update JSON data
		((ObjectNode) node).put("Title", "");
		//add new key value
		((ObjectNode) node).put("CreationDate", "");
		return node.toString();
	} 
*/  
  public byte[] getObjectBytes() {
	byte[] data = text.getBytes(StandardCharsets.UTF_8);   
	numOfBytes = data.length;  
	return data; 
  }
  
  public static byte[] readFileToBytes (String inputFilePath){
  	byte pageByteStream[] = {};
      FileInputStream inputStream = null;
      try {
          File inputFile = new File(inputFilePath);
          if(inputFile.exists() && !inputFile.isDirectory()) { 
          	inputStream = new FileInputStream(inputFile);
              pageByteStream = new byte[(int)inputFile.length()];
              inputStream.read(pageByteStream);
          }
      }
      catch (FileNotFoundException e) {
          System.out.println("File " + inputFilePath + " not found" + e);
          pageByteStream = new byte[0];//{};
          System.out.println("pageByteStream.length='" + pageByteStream.length + "'");
      }
      catch (IOException ioe) {
          System.out.println("Exception while reading file " + inputFilePath + " " + ioe);
      }
      finally {
          try {
              if (inputStream != null) {
              	inputStream.close();
              }
          }
          catch (IOException ioe) {
              System.out.println("Error while closing stream: " + ioe);
          }
      }
		return pageByteStream;
  }

  String redirect(int pageId) {
		return Integer.toString(pageId) + " 0 R";
	}  
  
	public void renderPdfCharacters() {
		System.out.println(Integer.toString(id) + " 0 obj<< % THIS SHOULD NOT BE VISIBLE object");
	}

}


/*
 JsonObject jo = Json.createObjectBuilder()
  .add("employees", Json.createArrayBuilder()
    .add(Json.createObjectBuilder()
      .add("firstName", "John")
      .add("lastName", "Doe")))
  .build();

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTest {
    public static void main(String... args) throws JSONException{
        final JSONObject res = new JSONObject();
        //adding value in JSON object
        JSONArray jarray= new JSONArray();
        for(int i=0;i<3;i++){
            JSONObject ob = new JSONObject();
            ob.put("k1"+i, "v1"+i);
            ob.put("k2"+i, "v2"+i);
            jarray.put(ob);
        }
        res.put("res", jarray);
        System.out.println("structure of created json object:"+res);
        //retrieving value from JSON object element wise
        System.out.println("retrieving value from JSON object element wise");
        JSONArray output= res.getJSONArray("res");
        for(int i=0;i<output.length();i++){
            JSONObject ob= output.getJSONObject(i);
            System.out.print(ob.get("k1"+i));
            System.out.println(ob.get("k2"+i));
        }
    }
}
*/
/*public class PdfObject {

}
*/