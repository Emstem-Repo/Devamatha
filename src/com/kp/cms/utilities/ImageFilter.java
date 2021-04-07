package com.kp.cms.utilities;


import java.io.File;

public class ImageFilter {

		    String GIF = "gif";
		    String PNG = "png";
		    String JPG = "jpg";
		    String BMP = "bmp";
		    String JPEG = "jpeg";
		    String WORD ="doc";
		    String WORD1="docx";
		    String EXCEL="xls";
		    String EXCEL1="xlsx";
		    String PDF="pdf";
		    String TEXT="txt";
		    String RTF="rtf";
		    public boolean accept(String file) {
		    if(file != null) {
		    String extension = getExtension(file);
		    if(extension != null && isSupported(extension))
		    return true;
		    }
		    return false;
		    }
		    private String getExtension(String file) {
		    if(file != null) {
		    int dot = file.lastIndexOf('.');
		    if(dot > 0 && dot < file.length()-1)
		    return file.substring(dot+1).toLowerCase();
		    }
		    return null;
		    }
		    private boolean isSupported(String ext) {
		    return ext.equalsIgnoreCase(GIF) || ext.equalsIgnoreCase(PNG) ||
		    ext.equalsIgnoreCase(JPG) || ext.equalsIgnoreCase(BMP) ||
		    ext.equalsIgnoreCase(JPEG)|| ext.equalsIgnoreCase(WORD) || ext.equalsIgnoreCase(WORD1)
		    || ext.equalsIgnoreCase(EXCEL)|| ext.equalsIgnoreCase(EXCEL1)|| ext.equalsIgnoreCase(PDF)
		    || ext.equalsIgnoreCase(TEXT) ||  ext.equalsIgnoreCase(RTF);
		    }
}
