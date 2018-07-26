package com.ey.za.app;

import java.util.Objects;

public class PdfResource extends PdfObject {

	public void renderPdfCharacters() {
		String leadText = "";
		String trailText = "";
		//int counter = 0;
		if (parentDocument.ADD_COMMENTS) {sComment = "  % Resource object";}		
		text = Integer.toString(id) + " 0 obj<<";

		//Add font resources
		if (parentDocument.resourceFonts.size() > 0) {
			//System.out.println("parentDocument.resourceFonts.size() = "+parentDocument.resourceFonts.size());
			leadText = "/Font<<";
			trailText = ">>" + sComment + parentDocument.SPLIT;
			PdfFont tempFont;
		
			for (Integer forObject : parentDocument.resourceFonts) {
				//System.out.println("forObject = "+forObject);
				//counter++;
				tempFont = (PdfFont)parentDocument.objects.get(forObject);
				leadText = leadText + "/" + tempFont.logicalName + " " + redirect(forObject);
			}
			text = text + leadText + trailText;
		}

		//Add xobject resources
		if (parentDocument.resourceImages.size() > 0) {
			leadText = "/XObject<<";
			trailText = ">>" + sComment + parentDocument.SPLIT;
			PdfXObject tempXObject;
		
			for (Integer forObject : parentDocument.resourceImages) {
				//System.out.println("forObject = "+forObject);
				//counter++;
				tempXObject = (PdfXObject)parentDocument.objects.get(forObject);
				leadText = leadText + "/" + tempXObject.logicalName + " " + redirect(forObject);
			}
			text = text + leadText + trailText;
		}
		
		text = text + ">>" + sComment + parentDocument.SPLIT;
		text = text + "endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}
}

/*numObj++;
	    if (ADD_COMMENTS) {sComment = "  % Resource object";}//----------------------------------------
	    String pageresourceID = Integer.toString(numObj) + " 0 R";
	    String pageresource = Integer.toString(numObj) + " 0 obj<<" 
	    			+ "/Font<</H1 " + FontID + ">>" + sComment + SPLIT
	                + "/XObject <</ImgJPEG1 " + imagedataID + ">>" + sComment + SPLIT
	                + ">>" + '\n' + "endobj" + sComment + SPLIT + ISOLATE;
	    fileBuff2 = (fileBuff2 + pageresource);
	    aObjects[numObj] = pageresource.length();

    	System.out.println("myPdf.resourceFonts = "+myPdf.resourceFonts+"("+myPdf.resourceFonts.size()+")");
    	System.out.println("myPdf.resourceImages = "+myPdf.resourceImages+"("+myPdf.resourceImages.size()+")");

*/