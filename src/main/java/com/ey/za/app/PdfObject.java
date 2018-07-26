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
	// String filepath = "";

//  String testString = "This Is Test";
//  char[] stringToCharArray = testString.toCharArray();
//  byte[] jsonData = testString.getBytes(StandardCharsets.UTF_8);

	// byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));
	// ObjectMapper objectMapper4 = new ObjectMapper();

	// create JsonNode
	// JsonNode rootNode4 = objectMapper4.readTree(jsonData);
//	System.out.println("rootNode4 = ");  
//	System.out.println(rootNode4);  
	// rootNode4 = objectMapper4.createObjectNode();
	// update JSON data
	// ((ObjectNode) rootNode4).put("id", 500);
	// add new key value
	// ((ObjectNode) rootNode4).put("test", "test value");

	/*
	 * String createObjectAttributesInfo() { ObjectMapper mapper = new
	 * ObjectMapper(); ObjectNode node = mapper.createObjectNode();
	 * 
	 * //update JSON data ((ObjectNode) node).put("Title", ""); //add new key value
	 * ((ObjectNode) node).put("CreationDate", ""); return node.toString(); }
	 */
	public byte[] getObjectBytes() {
		byte[] data = text.getBytes(StandardCharsets.UTF_8);
		numOfBytes = data.length;
		return data;
	}

	public static byte[] readFileToBytes(String inputFilePath) {
		byte pageByteStream[] = {};
		FileInputStream inputStream = null;
		try {
			File inputFile = new File(inputFilePath);
			if (inputFile.exists() && !inputFile.isDirectory()) {
				inputStream = new FileInputStream(inputFile);
				pageByteStream = new byte[(int) inputFile.length()];
				inputStream.read(pageByteStream);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + inputFilePath + " not found" + e);
			pageByteStream = new byte[0];// {};
			System.out.println("pageByteStream.length='" + pageByteStream.length + "'");
		} catch (IOException ioe) {
			System.out.println("Exception while reading file " + inputFilePath + " " + ioe);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error while closing stream: " + ioe);
			}
		}
		return pageByteStream;
	}

	String redirect(int pageId) {
		return Integer.toString(pageId) + " 0 R";
	}

	public void renderPdfCharacters() {
		System.out.println("Default version of renderPdfCharacters() used for object " + Integer.toString(id)
				+ " (type: " + type + ")");
	}

}
