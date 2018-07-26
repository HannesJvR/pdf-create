package com.ey.za.app;

import java.text.DecimalFormat;
import java.util.Objects;

public class PdfPage extends PdfObject {
	//String parent = "";
	//int[] resources;
	//MediaBox
	String streamText = "";
	byte streamBytes[];
	int contentStreamID = 0;
	//Group
	//Tabs

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {sComment = "  % Page object";}		
		text = Integer.toString(id) + " 0 obj<</Type/Page" + sComment + parentDocument.SPLIT
				+ "/Parent " + Integer.toString(parentDocument.pagetreeID) + " 0 R" + sComment + parentDocument.SPLIT
				+ "/MediaBox [0 0 595 842]" + sComment + parentDocument.SPLIT 
				+ "/Contents [" + redirect(contentStreamID) + "]" + sComment + parentDocument.SPLIT 
				+ "/Resources " + redirect(parentDocument.resourceID) + sComment + parentDocument.SPLIT 
				+ ">>" + sComment + parentDocument.SPLIT 
				+ "endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}

	public String formatRound2(double rRawNumber) {
		   String sNumberString = "";
		   DecimalFormat numberFormatter = new DecimalFormat("##0.00");
		   numberFormatter.setMaximumFractionDigits(2);
		   try
		   {
			   sNumberString = numberFormatter.format(rRawNumber);
			   while (sNumberString.length()>0 && (sNumberString.indexOf(".")>0) && (
					   (Objects.equals(sNumberString.substring(sNumberString.length()-1,sNumberString.length()),"0")) ||
					   (Objects.equals(sNumberString.substring(sNumberString.length()-1,sNumberString.length()),".")))){
				   sNumberString = sNumberString.substring(0, sNumberString.length()-1); 
			   }
		   }
		   catch( Exception e)
		   {
			   sNumberString="ERR";
		   }
	       return sNumberString;
	   } 

	  //-- Formatting of a text string to be displayed on the PDF file --------------------------------------
	public void placeText(float rX_Position, float rY_Position, String sText, String sFontname, 
		int iFontsize, String sAlignment, float rWidth) {

    	String sPlaceText = "";
    	sComment = "";
        
    	if (sText.length()>0){
	        float xPdf = 0;
	        float yPdf = rY_Position;
	        String sComment = "";
	        switch (sAlignment) {
	            case "L":
	            case "l":
	                xPdf = rX_Position;
	                break;
	            case "R":
	            case "r":
//	                xPdf = rX_Position - getStringWidth(sText, iFontsize);
	                break;
	            case "C":
	            case "c":
//	                xPdf = rX_Position + (rWidth - getStringWidth(sText, iFontsize)) / 2;
	                break;
	        }

	        if (parentDocument.ADD_COMMENTS) {
	            sComment = "  % Begin text";
	        }
	        
	        sPlaceText = "BT" + sComment + parentDocument.SPLIT;
	        
	        if (parentDocument.ADD_COMMENTS) {
	            sComment = "  % Set text font and size";
	        }
	        
	        sPlaceText = sPlaceText + "/" 
	                    + sFontname + " " 
	                    + Integer.toString(iFontsize)  + " Tf" 
	                    + sComment + parentDocument.SPLIT;
	        if (parentDocument.ADD_COMMENTS) {
	            sComment = "  % Move text position";
	        }
	        
	        sPlaceText = sPlaceText 
	                    + formatRound2(xPdf) + " " 
	                    + formatRound2(yPdf) + " Td" 
	                    + sComment + parentDocument.SPLIT;
	        if (parentDocument.ADD_COMMENTS) {
	            sComment = "  % Show text";
	        }
	        
	        sPlaceText = sPlaceText + "(" 
	                    + sText + ") Tj" 
	                    + sComment + parentDocument.SPLIT;
	        if (parentDocument.ADD_COMMENTS) {
	            sComment = "  % End text";
	        }
	        
	        sPlaceText = sPlaceText + "ET" 
	                + sComment 
	                + parentDocument.SPLIT + parentDocument.ISOLATE;
    	}
        //return sPlaceText;
    	streamText = streamText + sPlaceText;
    }
	
    public void placeImage(double g, double h, String ImageName, double i, double j, double k) {
        double a = 1;
        double b = 0;
        double c = 0;
        double d = 1;
        double e = 0;
        double f = 0;

        String sComment = "";
        
        a = a * Math.cos(k);
        b = b * Math.sin(k);
        c = c * Math.sin(k) * -1; //-Math.Sin(RotationRadians)
        d = d * Math.cos(k);
        e = e * 0;
        f = f * 0;
        // Apply Scale * [width 0 0 height 0 0]
        a = a * i;
        b = b * 0;
        c = c * 0;
        d = d * j;
        e = e * 0;
        f = f * 0;
        // Apply Translation + [0 0 0 0 x1 y1]
        a = a + 0;
        b = b + 0;
        c = c + 0;
        d = d + 0;
        e = e + g;
        f = f + h;
        
        if (parentDocument.ADD_COMMENTS) {
            sComment = "  % Save graphics state";
        }
        
        String sPlaceImage = "q" 
                    + sComment + parentDocument.SPLIT;
        // save graphics state
        
        if (parentDocument.ADD_COMMENTS) {
            sComment = "  % Concatenate matrix to current transformation matrix";
        }
        
        sPlaceImage = sPlaceImage 
                    + INT_FORMAT.format(a) + " "
                    + INT_FORMAT.format(b) + " " 
                    + INT_FORMAT.format(c) + " " 
                    + INT_FORMAT.format(d) + " "
                    + INT_FORMAT.format(e) + " " 
                    + INT_FORMAT.format(f) + " cm" 
                    + sComment + parentDocument.SPLIT;
        // Concatenate matrix to current transformation matrix
        if (parentDocument.ADD_COMMENTS) {
            sComment = "  % Invoke named XObject";
        }
        
        sPlaceImage = sPlaceImage + "/" 
                    + ImageName + " Do" 
                    + sComment + parentDocument.SPLIT;
        // Invoke named XObject
        if (parentDocument.ADD_COMMENTS) {
            sComment = "  % Restore graphics state";
        }
        
        sPlaceImage = sPlaceImage + "Q" 
                + sComment + parentDocument.SPLIT;
        		
        streamText = streamText + sPlaceImage;
    }    


}

/*	    numObj++;
	    if (ADD_COMMENTS) {sComment = "  % Page object";}//--------------------------------------------
	    String page = Integer.toString(numObj) + " 0 obj<</Type/Page" + sComment + SPLIT
	                + "/Parent " + pageTreeID + sComment + SPLIT
	                + "/MediaBox [0 0 595 842]" + sComment + SPLIT
	                + "/Contents [10 0 R]" + sComment + SPLIT
	                + "/Resources " + pageresourceID  + sComment + SPLIT
	                + ">>" + '\n' + "endobj" + sComment + SPLIT + ISOLATE;
	    fileBuff2 = (fileBuff2 + page);
	    aObjects[numObj] = page.length();
*/