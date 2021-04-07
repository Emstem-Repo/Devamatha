package com.kp.cms.actions.reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.actions.admission.ExportImagesAction;
import com.kp.cms.forms.admission.PrintShortageAttendanceForm;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.codec.Base64;

public class DownloadShortageReportAction extends DownloadAction {

	private static final Log log = LogFactory.getLog(ExportImagesAction.class);

	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DownloadShortageReportAction");
		PrintShortageAttendanceForm printShortageAttendanceForm = (PrintShortageAttendanceForm) form;
		File pdfFile=null;
		if(printShortageAttendanceForm.getMessageList()!=null && !printShortageAttendanceForm.getMessageList().isEmpty()){
		String fileName = "ShortageAttendanceReport"
				+ Calendar.getInstance().getTimeInMillis();
		pdfFile=new File(request.getRealPath("")+ "//TempFiles//"+fileName+".pdf");
		if (pdfFile.exists()) {
			pdfFile.delete();
		}
		try {
			Document pdfDocument = new Document();
			List<String> list=printShortageAttendanceForm.getMessageList();
			Iterator<String> itr=list.iterator();
			while (itr.hasNext()) {
				String string = itr.next();
				Reader htmlreader = new StringReader(string);
		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        PdfWriter.getInstance(pdfDocument, baos);
		        pdfDocument.open();
		        StyleSheet styles = new StyleSheet();
		        styles.loadTagStyle("body", "font", "Bitstream Vera Sans");
		        ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader, styles);
		        for (int i = 0; i < arrayElementList.size(); ++i) {
		            Element e = (Element) arrayElementList.get(i);
		            pdfDocument.add(e);
		        }
		        pdfDocument.close();
		        byte[] bs = baos.toByteArray();
		       // String pdfBase64 = Base64.encodeBytes(bs); //output
		        FileOutputStream out = new FileOutputStream(pdfFile);
		        out.write(bs);
		        out.close();
		        break;
			}
	        
		} catch (Exception e) {
			// TODO: handle exception
		}
		  response.setHeader("Content-disposition", "attachment; filename =" + fileName + ".pdf");
		  response.setContentType("application/pdf");
		 
		}
		return new FileStreamInfo("write.txt", pdfFile);
	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
			this.contentType = contentType;
			this.bytes = myXLSBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
