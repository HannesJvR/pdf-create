package com.ey.za.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PdfStream extends PdfObject {
	PdfPage parentPage;
	
//    byte Pg1Strm[] = readFileToBytes((parentDocument.isPageStreamsDeflated ? "C:\\1Hannes\\workspace\\StatementPDF\\DEF\\PL0000511584.STM.DEF" : "C:\\1Hannes\\workspace\\StatementPDF\\STM\\PL0000511584.STM"));
//    byte Pg2Strm[] = readFileToBytes((parentDocument.isPageStreamsDeflated ? "C:\\1Hannes\\workspace\\StatementPDF\\DEF\\PL0000511584.ST2.DEF" : "C:\\1Hannes\\workspace\\StatementPDF\\STM\\PL0000511584.ST2"));
	//MediaBox
	//int contentStreamID = 0;
	//Group
	//Tabs

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {sComment = "  % Stream object";}		
		text = Integer.toString(id) + " 0 obj<<" + sComment + parentDocument.SPLIT
				+ (parentDocument.isPageStreamsDeflated ? "/Filter /FlateDecode " + sComment + parentDocument.SPLIT + parentDocument.ISOLATE : "" ) 
				+ "/Length " + Integer.toString(parentPage.streamText.length()) + ">>stream" + sComment + parentDocument.SPLIT;
		text = text + parentPage.streamText;
		text = text + "endstream" + sComment + parentDocument.SPLIT //+ parentDocument.ISOLATE
				+ "endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
		
		//byte[] b = parentPage.streamText.getBytes(StandardCharsets.UTF_8);
		//System.out.println("b.length = "+b.length);
	}
}

/*	    numObj++;
	    if (ADD_COMMENTS) {sComment = "  % Stream object";}//------------------------------------------
	    String contents = Integer.toString(numObj) + " 0 obj<<" 
	                + (bIsPageStreamsDeflated ? "/Filter /FlateDecode " : "" ) + "/Length " 
	                + Integer.toString(Pg2Strm.length) + ">>stream" + SPLIT;
	    fileBuff2 = (fileBuff2 + contents);
	    String fileBuff3 = SPLIT + "endstream" + sComment + SPLIT + "endobj" + sComment + SPLIT + ISOLATE;
	    aObjects[numObj] = (contents.length() + (Pg2Strm.length + fileBuff3.length()));
	    
	    numObj++;
	    if (ADD_COMMENTS) {sComment = "  % Stream object";}//------------------------------------------
	    contents = Integer.toString(numObj) + " 0 obj<<" 
	                + (bIsPageStreamsDeflated ? "/Filter /FlateDecode " : "" ) + "/Length " 
	                + Integer.toString(Pg1Strm.length) + ">>stream" + SPLIT;
	    fileBuff3 += contents;
	    String fileBuff4 = SPLIT + "endstream" + sComment + SPLIT + "endobj" + sComment + SPLIT + ISOLATE;
	    aObjects[numObj] = contents.length() + Pg1Strm.length + fileBuff4.length();
*/