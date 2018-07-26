package com.ey.za.app;

import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public class App 
{
    public static void main( String[] args ) throws IOException, ImageProcessingException
    {
         	
        boolean isPageStreamsDeflated = false;
    	PdfDocument myPdf = new PdfDocument();

    	myPdf.isPageStreamsDeflated = isPageStreamsDeflated;
    	
    	myPdf.addFont("H1");
    	myPdf.addImage("ImgJPEG1","C:\\1Hannes\\workspace\\StatementPDF\\src\\Files\\EFCLogo-30.jpg");
    	myPdf.addImage("ImgJPEG2","C:\\1Hannes\\workspace\\StatementPDF\\src\\Files\\EFCLogo-30Gray.jpg");
    	//		String filePath = "C:\\1Hannes\\workspace\\StatementPDF\\src\\Files\\EFCLogo-30.jpg";

    	myPdf.addPage();
    	myPdf.addPage();
    	myPdf.addPage();
    	myPdf.addPage();
    	
    	PdfPage[] pageArr = new PdfPage[myPdf.pages.size()];
    	pageArr = myPdf.pages.toArray(pageArr);
    	
        verifyObjectPositions();
        
        String Pg1 = readAllBytesJava7("C:\\1Hannes\\workspace\\StatementPDF\\STM\\ML0000480137.STM");
        String Pg2 = readAllBytesJava7("C:\\1Hannes\\workspace\\StatementPDF\\STM\\ML0000480137.ST2");
        
    	//Test content streams
        pageArr[0].streamText = Pg1; //We need to fix the image before this will work
        pageArr[1].streamText = Pg2;
    	pageArr[2].placeText(86, 703, "This is page 3","H1",10,"L",0);
    	pageArr[3].placeText(86, 703, "This is page 4","H1",10,"L",0);
    	pageArr[2].placeImage(42, 768, "ImgJPEG2", 125.150,38.595,0);
    	
    	myPdf.saveAs("C:\\Temp\\TestMe.PDF");

    	//jsonExamples();

    	System.out.println("Done!");
    }
    
    static void verifyObjectPositions() throws UnsupportedEncodingException {
        byte pdfFileBytes[] = readFileToBytes("C:\\temp\\TestMe.PDFBYT.PDF");
        System.out.println("pdfFileBytes.length = " + pdfFileBytes.length);
        int testPos = 7;
        byte[] data = new byte[9];
        String value = "";
        int[] objectPositions = {11,101,163,341,409,457,519,624,720,826,875};
        //----------------------------------
        for (int loop : objectPositions) {
            testPos = loop;
            for (int i=0;i<9;i++) {
            	data[i] = pdfFileBytes[i+testPos];
            }
            value = new String(data, "UTF-8");
            System.out.println("data("+testPos+") = '"+value+"'");
        }
        //----------------------------------

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

    private static String readAllBytesJava7(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }

    static void jsonExamples() throws IOException {
    	String s = "{\"list\":[\"item3\",\"item4\"],\"stringVal\":\"test string2\",\"intVal\":5}";
  	  // ObjectMapper om = new ObjectMapper();
  	  // MyObject obj = om.readValue(s, MyObject.class);
  	  // System.out.println(obj)

  	  byte[] jsonData = s.getBytes(StandardCharsets.UTF_8);

		
		//create ObjectMapper instance
		ObjectMapper mapper = new ObjectMapper();
		//JsonNode node = mapper.createObjectNode();
		ObjectNode node = mapper.createObjectNode();
		
		//update JSON data
		((ObjectNode) node).put("id", 500);
		//add new key value
		((ObjectNode) node).put("test", "test value");
		
//-- Object nodes for testing --------------------------------------------------------------

      /**
       * Create three JSON Objects objectNode1, objectNode2, objectNode3
       * Add all these three objects in the array
       */

      ObjectNode objectNode1 = mapper.createObjectNode();
      objectNode1.put("bookName", "Java");
      objectNode1.put("price", "100");

      ObjectNode objectNode2 = mapper.createObjectNode();
      objectNode2.put("bookName", "Spring");
      objectNode2.put("price", "200");

      ObjectNode objectNode3 = mapper.createObjectNode();
      objectNode3.put("bookName", "Liferay");
      objectNode3.put("price", "500");

//-- Object nodes End --------------------------------------------------------------
		
//-- Add Array Method 1 --------------------------------------------------------------
		JsonNodeFactory factory = JsonNodeFactory.instance;
		//ArrayNode arrayNode = mapper.createArrayNode();
      ArrayNode arrayNode = factory.arrayNode();
		 
      arrayNode.add(objectNode1);
      arrayNode.add(objectNode2);
      arrayNode.add(objectNode3);
      
      ((ObjectNode) node).set("ArrayMethod1", arrayNode);
//-- Add Array Method 1 End --------------------------------------------------------------
      
//-- Add Array Method 2 --------------------------------------------------------------
		ObjectNode dataTable = mapper.createObjectNode();
		ArrayNode aa = node.putArray("ArrayMethod2");

		aa.add(objectNode1);
      aa.add(objectNode2);
      //aa.add(objectNode3);
//-- Add Array Method 2 End --------------------------------------------------------------
      
      //ArrayNode retrievedNode = mapper.readTree(node.path("ArrayMethod2").toString().getBytes(StandardCharsets.UTF_8));
      //retrievedNode.add(objectNode3);
      
      jsonData = node.path("ArrayMethod2").toString().getBytes(StandardCharsets.UTF_8);
      JsonNode rootNode4 = mapper.readTree(jsonData);
      
      String jsonString = node.path("ArrayMethod2").toString();
      JsonNode rootNode5 = mapper.readValue(jsonString, JsonNode.class);
      /**
       * We can directly write the JSON in the console.
       * But it wont be pretty JSON String
       */
      //System.out.println(node.toString());

      /**
       * To make the JSON String pretty use the below code
       */
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
      System.out.println("---------------");
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode4));
      System.out.println("---------------");
      System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode5));
      
      if (rootNode5.isArray()) {
          for (final JsonNode objNode : rootNode5) {
              System.out.println(objNode);
          }
      }
      else System.out.println("BooBoo");
      
      jsonString = node.toString();
      //text = builder.toString();
      //ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> map = mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
      });

      List mainMap2 = (List) map.get("ArrayMethod1");
      for (Object item : mainMap2) {
          System.out.println("itemResult" + item.toString());
      }
      
      System.out.println("$$$$$");
      System.out.println(((Map)mainMap2.get(0))); //first object
      System.out.println(((Map)mainMap2.get(0)).get("price")); //price of first object
      System.out.println(((Map)mainMap2.get(1)).get("price"));
      System.out.println(((Map)mainMap2.get(2)).get("price"));
      
      int test = Integer.parseInt(((Map)mainMap2.get(0)).get("price").toString());
      System.out.println("test = '"+test+"'");
      //int id = (int)((Map)mainMap2.get(0)).get("price");
      //System.out.println(id);
      
		//convert json string to object
		//Employee emp = objectMapper.readValue(jsonData, Employee.class);
		
		//System.out.println("Employee Object\n"+emp);

  		//System.out.println(s);
  	
    }
}
//---------
