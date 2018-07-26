package com.ey.za.app;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class PdfXObject extends PdfObject {
	String logicalName = "";
	String filePath = "";
	int imageWidth = 0;
	int imageHeight = 0;
	int imageType = 0;
	// byte[] JPG_BYTES = readFileToBytes(filePath);
	BufferedImage bufferedImage;

//	PdfXObject() throws IOException {
//		System.out.println("filePath = '"+filePath+"'");
//		checkImageProperties();
//	}

	public void renderPdfCharacters() {
		byte[] JPG_BYTES = readFileToBytes(filePath);

		if (parentDocument.ADD_COMMENTS) {
			sComment = "  % XObject object";
		}
		text = Integer.toString(id) + " 0 obj<</Type/XObject" + sComment + parentDocument.SPLIT + "/Subtype/Image"
				+ sComment + parentDocument.SPLIT + "/Filter [/DCTDecode ]" + sComment + parentDocument.SPLIT
				+ "/Width " + INT_FORMAT.format(imageWidth) + sComment + parentDocument.SPLIT + "/Height "
				+ INT_FORMAT.format(imageHeight) + sComment + parentDocument.SPLIT + "/ColorSpace/DeviceRGB" + sComment
				+ parentDocument.SPLIT + "/BitsPerComponent 8" + sComment + parentDocument.SPLIT + "/Length "
				+ INT_FORMAT.format(JPG_BYTES.length) + sComment + parentDocument.SPLIT + "/Name/ImgJPEG1>>" + sComment
				+ parentDocument.SPLIT + "stream" + parentDocument.SPLIT;
		text = text + "#IMAGE_BYTES#";
		text = text + parentDocument.SPLIT + "endstream" + sComment + parentDocument.SPLIT + "endobj" + sComment
				+ parentDocument.SPLIT + parentDocument.ISOLATE;
	}

	public byte[] getObjectBytes() {
		byte[] JPG_BYTES = readFileToBytes(filePath);
		String colorSpace = "DeviceRGB";
		if (imageType == 10) {
			colorSpace = "DeviceGray";
		}

		if (parentDocument.ADD_COMMENTS) {
			sComment = "  % XObject object";
		}
		String introText = Integer.toString(id) + " 0 obj<</Type/XObject" + sComment + parentDocument.SPLIT
				+ "/Subtype/Image" + sComment + parentDocument.SPLIT + "/Filter [/DCTDecode ]" + sComment
				+ parentDocument.SPLIT + "/Width " + INT_FORMAT.format(imageWidth) + sComment + parentDocument.SPLIT
				+ "/Height " + INT_FORMAT.format(imageHeight) + sComment + parentDocument.SPLIT + "/ColorSpace/"
				+ colorSpace + sComment + parentDocument.SPLIT + "/BitsPerComponent 8" + sComment + parentDocument.SPLIT
				+ "/Length " + INT_FORMAT.format(JPG_BYTES.length) + sComment + parentDocument.SPLIT
				+ "/Name/ImgJPEG1>>" + sComment + parentDocument.SPLIT + "stream" + parentDocument.SPLIT;

		String trailingText = parentDocument.SPLIT + "endstream" + sComment + parentDocument.SPLIT + "endobj" + sComment
				+ parentDocument.SPLIT + parentDocument.ISOLATE;

		byte[] introBytes = introText.getBytes(StandardCharsets.UTF_8);
		byte[] trailingBytes = trailingText.getBytes(StandardCharsets.UTF_8);
		byte[] combined = new byte[introBytes.length + JPG_BYTES.length + trailingBytes.length];

		System.arraycopy(introBytes, 0, combined, 0, introBytes.length);
		System.arraycopy(JPG_BYTES, 0, combined, introBytes.length, JPG_BYTES.length);
		System.arraycopy(trailingBytes, 0, combined, introBytes.length + JPG_BYTES.length, trailingBytes.length);
		numOfBytes = combined.length;
		return combined;
	}

	public void checkImageProperties() throws IOException {
		System.out.println("filePath = '" + filePath + "'");
		bufferedImage = ImageIO.read(new File(filePath));
		imageWidth = bufferedImage.getWidth();
		imageHeight = bufferedImage.getHeight();
		imageType = bufferedImage.getType();
		ColorModel colorModel = bufferedImage.getColorModel();
		System.out.println("image.getType() = " + bufferedImage.getType());
		switch (bufferedImage.getType()) {
		case 5:
			System.out.println("TYPE_3BYTE_BGR");
			System.out.println(
					"Represents an image with 8-bit RGB color components, corresponding to a Windows-style BGR color model) with the colors Blue, Green, and Red stored in 3 bytes. There is no alpha. The image has a ComponentColorModel. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 6:
			System.out.println("TYPE_4BYTE_ABGR");
			System.out.println(
					"Represents an image with 8-bit RGBA color components with the colors Blue, Green, and Red stored in 3 bytes and 1 byte of alpha. The image has a ComponentColorModel with alpha. The color data in this image is considered not to be premultiplied with alpha. The byte data is interleaved in a single byte array in the order A, B, G, R from lower to higher byte addresses within each pixel.");
			break;
		case 7:
			System.out.println("TYPE_4BYTE_ABGR_PRE");
			System.out.println(
					"Represents an image with 8-bit RGBA color components with the colors Blue, Green, and Red stored in 3 bytes and 1 byte of alpha. The image has a ComponentColorModel with alpha. The color data in this image is considered to be premultiplied with alpha. The byte data is interleaved in a single byte array in the order A, B, G, R from lower to higher byte addresses within each pixel.");
			break;
		case 12:
			System.out.println("TYPE_BYTE_BINARY");
			System.out.println(
					"Represents an opaque byte-packed 1, 2, or 4 bit image. The image has an IndexColorModel without alpha. When this type is used as the imageType argument to the BufferedImage constructor that takes an imageType argument but no ColorModel argument, a 1-bit image is created with an IndexColorModel with two colors in the default sRGB ColorSpace: {0, 0, 0} and {255, 255, 255}.\r\n"
							+ "Images with 2 or 4 bits per pixel may be constructed via the BufferedImage constructor that takes a ColorModel argument by supplying a ColorModel with an appropriate map size.\r\n"
							+ "\r\n"
							+ "Images with 8 bits per pixel should use the image types TYPE_BYTE_INDEXED or TYPE_BYTE_GRAY depending on their ColorModel.\r\n"
							+ "\r\n"
							+ "When color data is stored in an image of this type, the closest color in the colormap is determined by the IndexColorModel and the resulting index is stored. Approximation and loss of alpha or color components can result, depending on the colors in the IndexColorModel colormap.");
			break;
		case 10:
			System.out.println("TYPE_BYTE_GRAY");
			System.out.println(
					"Represents a unsigned byte grayscale image, non-indexed. This image has a ComponentColorModel with a CS_GRAY ColorSpace. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 13:
			System.out.println("TYPE_BYTE_INDEXED");
			System.out.println(
					"Represents an indexed byte image. When this type is used as the imageType argument to the BufferedImage constructor that takes an imageType argument but no ColorModel argument, an IndexColorModel is created with a 256-color 6/6/6 color cube palette with the rest of the colors from 216-255 populated by grayscale values in the default sRGB ColorSpace.\r\n"
							+ "When color data is stored in an image of this type, the closest color in the colormap is determined by the IndexColorModel and the resulting index is stored. Approximation and loss of alpha or color components can result, depending on the colors in the IndexColorModel colormap.");
			break;
		case 0:
			System.out.println("TYPE_CUSTOM");
			System.out.println(
					"Image type is not recognized so it must be a customized image. This type is only used as a return value for the getType() method.");
			break;
		case 2:
			System.out.println("TYPE_INT_ARGB");
			System.out.println(
					"Represents an image with 8-bit RGBA color components packed into integer pixels. The image has a DirectColorModel with alpha. The color data in this image is considered not to be premultiplied with alpha. When this type is used as the imageType argument to a BufferedImage constructor, the created image is consistent with images created in the JDK1.1 and earlier releases.");
			break;
		case 3:
			System.out.println("TYPE_INT_ARGB_PRE");
			System.out.println(
					"Represents an image with 8-bit RGBA color components packed into integer pixels. The image has a DirectColorModel with alpha. The color data in this image is considered to be premultiplied with alpha.");
			break;
		case 4:
			System.out.println("TYPE_INT_BGR");
			System.out.println(
					"Represents an image with 8-bit RGB color components, corresponding to a Windows- or Solaris- style BGR color model, with the colors Blue, Green, and Red packed into integer pixels. There is no alpha. The image has a DirectColorModel. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 1:
			System.out.println("TYPE_INT_RGB");
			System.out.println(
					"Represents an image with 8-bit RGB color components packed into integer pixels. The image has a DirectColorModel without alpha. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 9:
			System.out.println("TYPE_USHORT_555_RGB");
			System.out.println(
					"Represents an image with 5-5-5 RGB color components (5-bits red, 5-bits green, 5-bits blue) with no alpha. This image has a DirectColorModel. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 8:
			System.out.println("TYPE_USHORT_565_RGB");
			System.out.println(
					"Represents an image with 5-6-5 RGB color components (5-bits red, 6-bits green, 5-bits blue) with no alpha. This image has a DirectColorModel. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		case 11:
			System.out.println("TYPE_USHORT_GRAY");
			System.out.println(
					"Represents an unsigned short grayscale image, non-indexed). This image has a ComponentColorModel with a CS_GRAY ColorSpace. When data with non-opaque alpha is stored in an image of this type, the color data must be adjusted to a non-premultiplied form and the alpha discarded, as described in the AlphaComposite documentation.");
			break;
		default:
			System.out.println("IMAGE TYPE NOT DEFINED!");
			break;
		}

		System.out.println(bufferedImage.getColorModel());
		System.out.println("colorModel.getPixelSize() = " + colorModel.getPixelSize());
		System.out.println("colorModel.getNumComponents() = " + colorModel.getNumComponents());
		System.out.println("colorModel.getNumColorComponents() = " + colorModel.getNumColorComponents());
		System.out.println("colorModel.getColorSpace() = " + colorModel.getColorSpace());
		System.out.println("colorModel.getTransparency() = " + colorModel.getTransparency());
		System.out.println("colorModel.hasAlpha() = " + colorModel.hasAlpha());
		System.out.println("colorModel.isAlphaPremultiplied() = " + colorModel.isAlphaPremultiplied());

		System.out.println("imageWidth = " + imageWidth);
		System.out.println("imageHeight = " + imageHeight);

	}
}
