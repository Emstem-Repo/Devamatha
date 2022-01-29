package com.kp.cms.utilities.print;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.view.SVFractionalFormat;
import org.hibernate.pretty.Printer;



import java.awt.print.*;


public class SamplePrinting implements Printable{
	
	//StudentDetails_VO studentDetailsVO = new StudentDetails_VO();
	private static final Log log = LogFactory.getLog(SamplePrinting.class);
	public int print(Graphics g, PageFormat pf, int page) throws
    PrinterException {
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}
		
		//g.drawString("Marks Verification Sheet1", 100, 100);
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		Graphics2D g2 = (Graphics2D)g;
        Double theta = Math.toRadians(180);
        g2.rotate(theta, 80, 80);
		
        g2.drawString(regNOS, -50, -260);
		g2.drawString(stdName, -50, -245);
		/*g.drawString("Marks Verification Sheet", 200, 100);
		g.drawString(studentDetailsVO.getRegisterNO(), 225, 600);
		g.drawString(studentDetailsVO.getStudentName(), 225, 675);*/
		//System.
		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}
	String regNOS = "";
	String stdName ="";
	public void samplePrint(String regNO, String studentName){
		if(regNO != null){
			regNOS = regNO;
			stdName = studentName;
			PrinterJob job = PrinterJob.getPrinterJob();
		    job.setPrintable(this);
		    try { job.print(); }
		       catch (PrinterException e) {
		       }
		    /*if (job.printDialog()){
		       try { job.print(); }
		       catch (PrinterException e) { 
		        }
		    }*/
		}
	}
}

