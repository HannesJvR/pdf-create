package com.ey.za.app;

public class PdfInfo extends PdfObject {
	String title = "EFC Statement ML0000480137";
	String createDate = "20180522115421";

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {sComment = "  % Info object";}		
		text = Integer.toString(id) + " 0 obj<</Title (" + title + ")" + sComment + parentDocument.SPLIT;
		text = text + "/CreationDate (D:" + createDate + ")" + sComment + parentDocument.SPLIT;
		text = text + ">>" + sComment + parentDocument.SPLIT + 
	    			"endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}
}
