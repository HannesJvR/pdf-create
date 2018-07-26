package com.ey.za.app;

public class PdfPagetree extends PdfObject {
	String logicalName = "";

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {sComment = "  % PageTree object";}		
		text = Integer.toString(id) + " 0 obj<</Type/Pages" + sComment + parentDocument.SPLIT
				+ "/Count " + Integer.toString(parentDocument.pageIds.size()) + sComment + parentDocument.SPLIT 
				+ "/Kids ["; 
		
		int counter = 0;
		for (Integer forObject : parentDocument.pageIds) {
			//System.out.println("forObject = "+forObject);
			counter++;
			text = text + redirect(forObject);
			if (counter < parentDocument.pageIds.size()) {text = text + " ";}
		}
		
		text = text + "]" + sComment + parentDocument.SPLIT 
				+ ">>" + sComment + parentDocument.SPLIT 
				+ "endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}
}
/*	    if (ADD_COMMENTS) {sComment = "  % PageTree object";}//----------------------------------------
	    String pageTreeID = (Integer.toString(numObj) + " 0 R");
	    String pageTree = "";
	    if (bHidePage2) {
	        pageTree = Integer.toString(numObj) + " 0 obj<</Type/Pages" + sComment + SPLIT
	                    + "/Count 1" + sComment + SPLIT
	                    + "/Kids [7 0 R]" + sComment + SPLIT
	                    + ">>" + '\n' + "endobj" + sComment + SPLIT + ISOLATE;
	    }
	    else {
	        pageTree = Integer.toString(numObj) + " 0 obj<</Type/Pages" + sComment + SPLIT
	                    + "/Count 2" + sComment + SPLIT
	                    + "/Kids [7 0 R 8 0 R]" + sComment + SPLIT
	                    + ">>" + '\n' + "endobj" + sComment + SPLIT + ISOLATE;
	    }
*/