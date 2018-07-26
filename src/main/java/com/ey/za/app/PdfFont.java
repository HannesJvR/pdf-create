package com.ey.za.app;

public class PdfFont extends PdfObject {
	String baseFont = "Arial";
	String subType = "Type1";
	String logicalName = "";

	public void renderPdfCharacters() {
		if (parentDocument.ADD_COMMENTS) {
			sComment = "  % Font object";
		}
		text = Integer.toString(id) + " 0 obj<</Type/Font" + sComment + parentDocument.SPLIT + "/BaseFont/Arial"
				+ sComment + parentDocument.SPLIT + "/Subtype/Type1>>" + sComment + parentDocument.SPLIT + "endobj"
				+ sComment + parentDocument.SPLIT + parentDocument.ISOLATE;
	}
}
