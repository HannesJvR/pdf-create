package com.ey.za.app;

public class PdfCatalog extends PdfObject {
	String logicalName = "";

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {sComment = "  % DocumentCatalog object";}		
		text = Integer.toString(id) + " 0 obj<</Type/Catalog" + sComment + parentDocument.SPLIT
				+ "/Pages " + redirect(parentDocument.pagetreeID) + sComment + parentDocument.SPLIT 
				+ ">>" + sComment + parentDocument.SPLIT 
				+ "endobj" + sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}

}
