package com.kp.cms.utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.ValidatorUtil;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.Resources;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.actions.employee.DownloadEmployeeResumeAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.hostel.HostelLeaveHandler;
import com.kp.cms.utilities.jms.MailQueueSender;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.jms.SMS_Message;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

@SuppressWarnings("deprecation")
public class CommonUtil {

	private static Log log = LogFactory.getLog(CommonUtil.class);
	private static Map<Integer, String> monthMap = null;
	
	static {
		monthMap = new HashMap<Integer, String>();
		monthMap.put(1, "JANUARY");
		monthMap.put(2, "FEBRUARY");
		monthMap.put(3, "MARCH");
		monthMap.put(4, "APRIL");
		monthMap.put(5, "MAY");
		monthMap.put(6, "JUNE");
		monthMap.put(7, "JULY");
		monthMap.put(8, "AUGUST");
		monthMap.put(9, "SEPTEMBER");
		monthMap.put(10, "OCTOBER");
		monthMap.put(11, "NOVEMBER");
		monthMap.put(12, "DECEMBER");
		
	}

	CommonUtil() {

	}

	/**
	 * @param number
	 * @return
	 */
	public static String getMonthForNumber(int number) {
		return monthMap.get(number);
	}

	/**
	 * converts string to sql date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date ConvertStringToSQLDate(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "dd/MM/yyyy",
					"MM/dd/yyyy");
		java.sql.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.sql.Date(date.getTime());
		}
		return sqldate;
	}

	public static java.sql.Timestamp ConvertStringToSQLDateTime(
			String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString,
					"dd/MM/yyyy k:mm:ss", "MM/dd/yyyy h:mm:ss a");
		java.sql.Timestamp sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.sql.Timestamp(date.getTime());
		}
		return sqldate;
	}

	public static java.sql.Date ConvertStringToSQLDateValue(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "yyyy-MM-dd",
					"MM/dd/yyyy");
		java.sql.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.sql.Date(date.getTime());
		}
		return sqldate;
	}

	/**
	 * converts string to sql date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date ConvertStringToSQLExactDate(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "dd/MM/yyyy",
					"MM/dd/yyyy");
		java.sql.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			// cal.set(Calendar.HOUR, new Date().getHours());
			// cal.set(Calendar.MINUTE, new Date().getMinutes());
			// cal.set(Calendar.SECOND, new Date().getSeconds());
			sqldate = new java.sql.Date(cal.getTimeInMillis());
		}
		return sqldate;
	}

	public static java.sql.Date ConvertStringToSQLExactDate1(String dateString) {
		String formatDate = "";
		java.sql.Date sqldate = null;
		Calendar cal = Calendar.getInstance();
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "dd/MM/yyyy",
					"MM/dd/yyyy");

		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			cal.setTime(date);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE));
			sqldate = new java.sql.Date(cal.getTimeInMillis());
		}
		return sqldate;
	}

	/**
	 * checks decimal or not
	 * 
	 * @param decimal
	 * @return
	 */
	public static boolean isValidDecimal(String decimal) {
		boolean result = false;
		StringTokenizer tokenizer = new StringTokenizer(decimal, ".");
		if (tokenizer.countTokens() == 0) {
			if (StringUtils.isNumeric(decimal)) {
				result = true;
			}
		} else if (tokenizer.countTokens() <= 3) {
			while (tokenizer.hasMoreTokens()) {
				if (StringUtils.isNumeric(tokenizer.nextToken())) {
					result = true;
				} else {
					result = false;
					break;
				}
			}
		} else {

			result = false;
		}
		return result;
	}

	/**
	 * converts string to sql date
	 * 
	 * @param date
	 * @return
	 */
	public static Date ConvertStringToDate(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			date = new Date(formatDate);
		}
		return date;
	}

	/**
	 * Converts the InputDateString to a user defined date format.
	 * 
	 * @param dateString
	 *            - Represents String.
	 * @param inputdateFormat
	 *            - Format of the date String.
	 * @param outputDateFormat
	 *            - User defined date format.
	 * @return User defined date format.
	 */

	// example input date string = 2009-01-08
	// inputdateFormat = yyyy-mm-dd
	// outputDateFormat required = dd-mm-yyyy
	// output obtained will be - 08-01-2008
	public static String ConvertStringToDateFormat(String dateString,
			String inputdateFormat, String outputDateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				inputdateFormat);
		Date date = null;
		String formattedDate = "";
		if (dateString != null && dateString.length() != 0) {
			try {
				
				date = simpleDateFormat.parse(dateString);
				formattedDate = CommonUtil.formatDate(date, outputDateFormat);
			} catch (ParseException e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
		}

		return formattedDate;
	}

	/**
	 * method formatDate returns date as specified date pattern
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * This method gives number of difference days between src and dest by
	 * ignoring time
	 * 
	 * @param src
	 * @param dest
	 * @return number of days between two days.
	 */
	public static int getDaysDiff(Date src, Date dest) {
		int diff = -1;

		if (src != null && dest != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(src);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			src = cal.getTime();
			cal.setTime(dest);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			dest = cal.getTime();
			diff = (int) ((dest.getTime() - src.getTime()) / 86400000);// 24 *
			// 60 *
			// 60 *
			// 1000
		}
		return diff;
	}

	/**
	 * This method gives number of difference Years between src and dest by
	 * ignoring time
	 * 
	 * @param src
	 * @param dest
	 * @return number of days between two days.
	 */

	public static int getYearsDiff(Date src, Date dest) {
		int diff = -1;
		// src = new Date();
		if (src != null && dest != null) {
			Calendar cal = Calendar.getInstance();
			
			cal.setTime(src);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			src = cal.getTime();
			cal.setTime(dest);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			dest = cal.getTime();
			diff = (int) ((dest.getTime() - src.getTime()) / 31536000000l);// 365
			// *
			// 24
			// *
			// 60
			// *
			// 60
			// *
			// 1000
			// =
			// 1
			// year
		}
		return diff;
	}

	/**
	 * This takes the date object as argument return date in format
	 * 
	 * @param date
	 */
	public static String getStringDate(Date date) {
		String stringDate = "";
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			stringDate = calendar.get(Calendar.DATE) + "-"
					+ getMonthName(calendar.get(Calendar.MONTH)) + "-"
					+ calendar.get(Calendar.YEAR);
		}
		return stringDate;

	}

	/**
	 * This takes index as argument returns corresponding 3 digit month name.
	 * 
	 * @param index
	 */
	public static String getMonthName(int index) {
		String months[] = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		return months[index];
	}

	/**
	 * to upload a file in a server location
	 * 
	 * @param file
	 * @param uploadFilePath
	 * @param fileName
	 * @return
	 */
	public static boolean uploadFile(FormFile file, String uploadFilePath,
			String fileName) {
		// get the content of the file copy that in server
		// copy the file in documents folder
		// write to file
		// Process the FormFile
		boolean isCopied = false;
		// if file name is empty then take the name of the file from UI
		if (fileName.equalsIgnoreCase("")) {
			fileName = file.getFileName();
		}
		try {
			byte[] fileData = file.getFileData();
			// server location
			File dir = new File(uploadFilePath);
			File destFile = new File(dir, fileName);
			try {
				BufferedOutputStream bos = null;
				if (!dir.exists()) {
					dir.mkdirs();
					bos = new BufferedOutputStream(new FileOutputStream(
							destFile));
					bos.write(fileData);
					isCopied = true;
					bos.flush();
					bos.close();
				} else {
					if (!destFile.exists()) {
						bos = new BufferedOutputStream(new FileOutputStream(
								destFile));
						bos.write(fileData);
						isCopied = true;
						bos.flush();
						bos.close();
					} else {
						destFile.delete();
						bos = new BufferedOutputStream(new FileOutputStream(
								destFile));
						bos.write(fileData);
						isCopied = true;
						bos.flush();
						bos.close();
					}
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return isCopied;
	}

	/**
	 * gives the type of the file for eg .doc,.txt
	 */
	public static String getFileExtension(String filename) {
		return filename.substring(filename.indexOf("."));
	}

	/**
	 * Returns whether the string is null or void.
	 * 
	 * @param The
	 *            string to be checked
	 * @return boolean indicating whether the String is empty
	 * 
	 */
	public static boolean isEmpty(String s) {
		return ((s == null) || (StringUtils.isEmpty(s)));
	}

	/**
	 * Used to send mail.
	 * 
	 * @param fromName
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param msg
	 * @return
	 */

	
	public static boolean sendMail(String fromName, String fromAddress,
			String toAddress, String subject, String msg)

	{

		boolean isSent = false;
		if(CMSConstants.mailSend){
		try {
			GenerateMail generateMail=new GenerateMail(fromName,fromAddress,toAddress,subject,msg,null);
			isSent=PropertyUtil.getInstance().save(generateMail);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		}
		return isSent;
	}
	

/*	
	public static boolean sendMail(String fromName, String fromAddress,
			String toAddress, String subject, String msg)

	{

		boolean isSent = false;
		try {

			// Set properties
			Properties props = new Properties();
			Properties prop = new Properties();
			try {
				InputStream in = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.MAIL_FILE_CFG);
				prop.load(in);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			props.put("mail.smtp.host", prop.getProperty("mailServer"));
			props.put("mail.smtp.port", prop.getProperty("port"));

			// Set session & message
			Session session = Session.getInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			// Set from & to addresses
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			InternetAddress toAssociate = new InternetAddress(toAddress);
			message.setFrom(from);
			message.addRecipient(Message.RecipientType.TO, toAssociate);

			// Set subject & message content
			message.setSubject(subject);
			//message.setContent(msg, "text/html");

			// Send message
			Transport.send(message);

			// Set to true if no errors encountered
			isSent = true;
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return isSent;
	}
	*/
	/**
	 * Used to send mail.
	 * 
	 * @param fromName
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param msg
	 * @return
	 */ 
	
	
	public static boolean sendMail(MailTO mailTO)	{

		boolean isSent = false;
		if(CMSConstants.mailSend){
		try {
			GenerateMail generateMail=new GenerateMail(mailTO.getFromName(),mailTO.getFromAddress(),mailTO.getToAddress(),mailTO.getSubject(),mailTO.getMessage(),mailTO.getAttachedFile());
			isSent=PropertyUtil.getInstance().save(generateMail);
		} catch (Exception e) {
			// No need to process exception because
			// if one is thrown, isSent will be false
			log.error("Error in sending mail...::" + e);
		}
		}
		return isSent;
	}


	/*
	public static boolean sendMail(MailTO mailTO)

	{

		boolean isSent = false;
		try {

			// Set properties
			Properties props = new Properties();
			Properties prop = new Properties();
			try {
				InputStream in = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.MAIL_FILE_CFG);
				prop.load(in);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			props.put("mail.smtp.host", prop.getProperty("mailServer"));
			props.put("mail.smtp.port", prop.getProperty("port"));

			// Set session & message
			Session session = Session.getInstance(props, null);
			MimeMessage message = new MimeMessage(session);

			// Set from & to addresses
			InternetAddress from = new InternetAddress(mailTO.getFromAddress(),
					mailTO.getFromName());
			InternetAddress toAssociate = new InternetAddress(mailTO
					.getToAddress());
			message.setFrom(from);
			message.addRecipient(Message.RecipientType.TO, toAssociate);

			// Set subject & message content
			message.setSubject(mailTO.getSubject());
			// message.setContent(mailTO.getMessage(), "text/html");

			// Create the Multipart to be added the parts to
			Multipart mp = new MimeMultipart();

			// Create and fill the first message part
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(mailTO.getMessage(), "UTF-8", "html");

			String attachment = mailTO.getAttachedFile();
			// Attach the files to the message
			if (attachment != null || !StringUtils.isEmpty(attachment)) {
				FileDataSource fds = new FileDataSource(attachment);
				mbp.setDataHandler(new DataHandler(fds));
				mbp.setFileName(fds.getName());
				// mp.addBodyPart(mbp);
			}
			mp.addBodyPart(mbp);
			// Add the Multipart to the message
			message.setContent(mp);

			// Send message
			Transport.send(message);

			// Set to true if no errors encountered
			isSent = true;
		} catch (IOException e) {
			log.error("Error in sending mail...::" + e);
		} catch (MessagingException m) {
			isSent = false;
			log.error("Error in sending mail...::" + m);
		} catch (Exception e) {
			// No need to process exception because
			// if one is thrown, isSent will be false
			log.error("Error in sending mail...::" + e);
		}

		return isSent;
	}
*/
	
	/**
	 * Checks if the field's length is greater than the value. A
	 * <code>Null</code> will be considered an error.
	 * 
	 * @param bean
	 *            The bean validation is being performed on.
	 * @param va
	 *            The <code>ValidatorAction</code> that is currently being
	 *            performed.
	 * @param field
	 *            The <code>Field</code> object associated with the current
	 *            field being validated.
	 * @param errors
	 *            The <code>ActionErrors</code> object to add errors to if any
	 *            validation errors occur.
	 * @param request
	 *            Current request object.
	 * @return True if stated conditions met.
	 */
	public static boolean validateSelectControl(Object bean,
			ValidatorAction va, Field field, ActionErrors errors,
			HttpServletRequest request) {

		String value = ValidatorUtil
				.getValueAsString(bean, field.getProperty());
		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (Integer.parseInt(value) == 0) {
					errors.add(field.getKey(), Resources.getActionMessage(
							request, va, field));
					return false;
				}
			} catch (Exception e) {
				errors.add(field.getKey(), Resources.getActionMessage(request,
						va, field));
				return false;
			}
		}
		return true;
	}

	/**
	 * @param one
	 * @param two
	 * @return
	 */
	public static String getTime(String one, String two) {
		String time = null;
		time = one + ":" + two;
		return time;
	}

	/**
	 * @param one
	 * @return
	 */
	public static String[] getDates(String one) {

		int idx = 0;
		int tokenCount;
		String words[];
		StringTokenizer st = new StringTokenizer(one, ",");
		tokenCount = st.countTokens();
		words = new String[tokenCount];

		try {
			while (st.hasMoreTokens()) // make sure there is stuff to get
			{
				words[idx] = st.nextToken();
				idx++;
			}
		} catch (Exception e) {
			log.error("error occured in getDates method in CommonUtil class.",
					e);
		}

		return words;
	}

	/**
	 * @param one
	 * @return
	 */
	public static String[] getInterviewType(String one) {

		int idx = 0;
		int tokenCount;
		String words[];
		StringTokenizer st = new StringTokenizer(one, "_");
		tokenCount = st.countTokens();
		words = new String[tokenCount];

		try {
			while (st.hasMoreTokens()) // make sure there is stuff to get
			{
				words[idx] = st.nextToken();
				idx++;
			}
		} catch (Exception e) {
			log
					.error(
							"error occured in getInterviewType method in CommonUtil class.",
							e);
		}

		return words;
	}

	/**
	 * This method gives number of difference time between src and dest by
	 * ignoring time
	 * 
	 * @param src
	 * @param dest
	 * @return number of days between two days.
	 */
	public static int getTimeDiff(int srcHrs, int srsMins, int destHrs,
			int destMins) {
		int diff = -1;

		if (srcHrs != 0 && srsMins != 0 && destHrs != 0 && destMins != 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, srcHrs);
			cal.set(Calendar.MINUTE, srsMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date source = cal.getTime();

			cal.set(Calendar.HOUR_OF_DAY, destHrs);
			cal.set(Calendar.MINUTE, destMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dest = cal.getTime();
			diff = (int) ((dest.getTime() - source.getTime()) / 3600000);
			// 60 *
			// 60 *
			// 1000
		}
		return diff;
	}

	/**
	 * @param srcHrs
	 * @param srsMins
	 * @param destHrs
	 * @param destMins
	 * @return
	 */
	public static long getDifferenceInTime(int srcHrs, int srsMins,
			int destHrs, int destMins) {
		long diff = -1;

		if (Integer.valueOf(srcHrs) != null && Integer.valueOf(srsMins) != null
				&& Integer.valueOf(destHrs) != null
				&& Integer.valueOf(destMins) != null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, srcHrs);
			cal.set(Calendar.MINUTE, srsMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date source = cal.getTime();

			cal.set(Calendar.HOUR_OF_DAY, destHrs);
			cal.set(Calendar.MINUTE, destMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dest = cal.getTime();
			diff = ((dest.getTime() - source.getTime()) / 60000);
			// 60 *
			// 60 *
			// 1000
		}
		return diff;
	}

	/**
	 * @param srcHrs
	 * @param srsMins
	 * @return
	 */
	public static long getMinitues(int srcHrs, int srsMins) {
		// long diff = -1;
		long diff = 0;

		if (new Integer(srcHrs) != null && new Integer(srsMins) != null) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, srcHrs);
			cal.set(Calendar.MINUTE, srsMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date source = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dest = cal.getTime();
			diff = ((source.getTime() - dest.getTime()) / 60000);

			// 60 *
			// 60 *
			// 1000
		}

		return diff;
	}

	/**
	 * @param srcTime
	 * @param destTime
	 * @param destTimeTwo
	 * @param destTimeThree
	 * @return
	 */
	public static long getTimeAfterBreak(long srcTime, long destTime,
			long destTimeTwo, long destTimeThree) {
		long diff = 0;
		if (Long.valueOf(srcTime) != null && Long.valueOf(destTime) != null) {
			diff = srcTime - (destTime + destTimeTwo + destTimeThree);
		}
		return diff;
	}

	/**
	 * @param srcTime
	 * @param destTime
	 * @param destTimeTwo
	 * @return
	 */
	public static long getTimeAfterBreak(long srcTime, long destTime,
			long destTimeTwo) {
		long diff = 0;
		if (Long.valueOf(srcTime) != null && Long.valueOf(destTime) != null) {
			diff = srcTime - (destTime + destTimeTwo);
		}
		return diff;
	}

	/**
	 * @param srcTime
	 * @param destTime
	 * @return
	 */
	public static long getTimeAfterBreak(long srcTime, long destTime) {
		long diff = 0;
		if (Long.valueOf(srcTime) != null && Long.valueOf(destTime) != null) {
			diff = srcTime - (destTime);
		}
		return diff;
	}

	/**
	 * @param timeAfterBreak
	 * @param interviewTime
	 * @return
	 */
	public static long noOfInterviews(long timeAfterBreak, long interviewTime) {
		long diff = 0;
		if (Long.valueOf(timeAfterBreak) != null && Long.valueOf(interviewTime) != null) {
			if (interviewTime != 0) {
				diff = timeAfterBreak / interviewTime;
			}
		}

		return diff;
	}

	/**
	 * @param noOfInterviews
	 * @param interviewers
	 * @param noOfDays
	 * @return
	 */
	public static long calculateEligibility(long noOfInterviews,
			long interviewers, long noOfDays) {
		long diff = 0;
		if (Long.valueOf(noOfInterviews) != null && Long.valueOf(interviewers) != null
				&& new Long(noOfDays) != null) {
			diff = noOfInterviews * interviewers * noOfDays;
		}
		return diff;
	}

	/**
	 * @param starTime
	 * @param endTime
	 * @param interviewTime
	 * @param breaktimestart
	 * @param breaktimeend
	 * @return
	 */
	public static String[] calculateTime(long starTime, long endTime,
			long interviewTime, long breaktimestart, long breaktimeend) {
		List timeList = new ArrayList();
		String[] arrayOfTime = null;
		Hashtable interviewTimeMap = new Hashtable();
		long starttime = starTime;
		long endtime = endTime;
		long interviewtime = interviewTime;

		long breaktimestart1 = breaktimestart;
		double bstart = breaktimestart1;

		long breaktimeend1 = breaktimeend;
		double bend = breaktimeend1;
		long duration = endtime - starttime;
		int noofSlot = 0;
		if (duration != 0 | interviewTime != 0) {
			if (interviewTime != 0) {
				noofSlot = Long.valueOf(duration / interviewtime).intValue();
			}
		}
		double[] interviewtimes = new double[noofSlot + 1];
		long tempTime = starttime;
		interviewtimes[0] = starttime;
		if (noofSlot != 0) {
			for (int i = 1; i <= noofSlot; i++) {
				tempTime = tempTime + interviewtime;
				interviewtimes[i] = tempTime;
			}
		}
		//String[] temp = new String[interviewtimes.length];

		for (int i = 0; i < interviewtimes.length; i++) {
			DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
			double time = 0;
			double breakstart = 0;
			double breakend = 0;
			if (interviewtimes[i] != 0 | breaktimestart1 / 60 != 0
					| breaktimeend1 / 60 != 0) {
				time = new Double(df2.format(interviewtimes[i] / 60))
						.doubleValue();
				breakstart = new Double(df2.format(bstart / 60)).doubleValue();
				breakend = new Double(df2.format(bend / 60)).doubleValue();
			}
			if ((time < breakstart) | (time > breakend)) {
				interviewTimeMap.put("time", time);
				timeList.add(interviewTimeMap.get("time"));
			}
		}
		if (timeList != null) {
			int arraySize = timeList.size();
			arrayOfTime = new String[arraySize];
			int i = 0;
			Iterator itr = timeList.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				arrayOfTime[i] = obj.toString();
				i++;
			}
		}

		return arrayOfTime;
	}

	/**
	 * @param starTime
	 * @param endTime
	 * @param interviewTime
	 * @return
	 */
	public static String[] calculateTimeNoBreak(long starTime, long endTime,
			long interviewTime) {
		List timeList = new ArrayList();
		String[] arrayOfTime = null;
		Hashtable interviewTimeMap = new Hashtable();
		long starttime = starTime;
		long endtime = endTime;
		long interviewtime = interviewTime;
		long duration = endtime - starttime;
		int noofSlot = 0;
		if (duration != 0 | interviewTime != 0) {
			if (interviewTime != 0) {
				noofSlot = Long.valueOf(duration / interviewtime).intValue();
			}
		}
		double[] interviewtimes = new double[noofSlot + 1];
		long tempTime = starttime;
		interviewtimes[0] = starttime;
		if (noofSlot != 0) {
			for (int i = 1; i <= noofSlot; i++) {
				tempTime = tempTime + interviewtime;
				interviewtimes[i] = tempTime;
			}
		}
		//String[] temp = new String[interviewtimes.length];
		;

		for (int i = 0; i < interviewtimes.length; i++) {
			DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
			double time = 0;
			double breakstart = 0;
			double breakend = 0;
			if (interviewtimes[i] != 0) {
				time = new Double(df2.format(interviewtimes[i] / 60))
						.doubleValue();
				interviewTimeMap.put("time", time);
				timeList.add(interviewTimeMap.get("time"));
			}
		}
		if (timeList != null) {
			int arraySize = timeList.size();
			arrayOfTime = new String[arraySize];
			int i = 0;
			Iterator itr = timeList.iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				arrayOfTime[i] = obj.toString();
				i++;
			}
		}
		return arrayOfTime;
	}

	/**
	 * @param check
	 * @return
	 */
	public static boolean checkForEmpty(String check) {
		boolean flage = false;
		if (check != null && !check.isEmpty()) {
			flage = true;
		}
		return flage;
	}

	/**
	 * @param firstArray
	 * @param secondArray
	 * @return
	 */
	public static String[] compareTwoStringArrays(String[] firstArray,
			String[] secondArray) {
		String[] first = new String[firstArray.length];
		List tempList = new ArrayList();
		if (firstArray != null && secondArray != null) {

			for (int i = 0; i < firstArray.length; i++) {
				for (int j = 0; j < secondArray.length; j++) {
					if (firstArray[i] != null) {
						if (firstArray[i].equalsIgnoreCase(secondArray[j])) {
							tempList.add(firstArray[i]);
							first = (String[]) tempList
									.toArray(new String[tempList.size()]);
						}
					}

				}
			}
		}

		return first;
	}

	/**
	 * @param srcHrs
	 * @param srsMins
	 * @param destHrs
	 * @param destMins
	 * @return
	 */
	public static boolean compareTime(int srcHrs, int srsMins, int destHrs,
			int destMins) {
		boolean flage = false;

		if (srcHrs != 0 && srsMins != 0 && destHrs != 0 && destMins != 0) {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, srcHrs);
			cal.set(Calendar.MINUTE, srsMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date source = cal.getTime();

			cal.set(Calendar.HOUR_OF_DAY, destHrs);
			cal.set(Calendar.MINUTE, destMins);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date dest = cal.getTime();
			if (source.getTime() > dest.getTime()) {
				flage = true;
			}
		}
		return flage;
	}

	/**
	 * @return
	 */
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * @return
	 */
	public static String getTodayDate() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * @return
	 */
	public static String getCurrentYear() {
		return Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

	}

	/**
	 * checks date pattern
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isValidDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date testDate = null;

		try {
			testDate = sdf.parse(date);
		} catch (ParseException e) {
			return false;
		}

		if (!sdf.format(testDate).equals(date)) {
			return false;
		}
		return true;

	}
	public static boolean isValidDateCutOff(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date testDate = null;

		try {
			testDate = sdf.parse(date);
		} catch (ParseException e) {
			return false;
		}

		if (!sdf.format(testDate).equals(date)) {
			return false;
		}
		return true;

	}

	/**
	 * @param startday
	 * @param endday
	 * @return
	 */
	public static long getDaysBetweenDates(Calendar startday, Calendar endday) {
		// Get the represented date in milliseconds
		long milis1 = startday.getTimeInMillis();
		long milis2 = endday.getTimeInMillis();
		// Calculate difference in milliseconds
		long diff = milis2 - milis1;
		// Calculate difference in days
		long diffDays = diff / (24 * 60 * 60 * 1000) + 1;
		return diffDays;
	}

	/**
	 * @param mail
	 * @return
	 */
	public static boolean postMail(MailTO mail) {
		MailQueueSender sender= new MailQueueSender();
		boolean mailsent=sender.sendMail(mail);
		//boolean mailsent = sendMail(mail);
		return mailsent;
	}

	/**
	 * @param year
	 * @return
	 */
	public static boolean isFutureYear(int year) {
		boolean flage = false;
		Calendar cal = Calendar.getInstance();
		int curyear = cal.get(Calendar.YEAR);
		if (year > curyear)
			flage = true;
		return flage;
	}

	/**
	 * @param year
	 * @return
	 */
	public static boolean isPastYear(int year) {
		boolean flage = false;
		Calendar cal = Calendar.getInstance();
		int curyear = cal.get(Calendar.YEAR);
		if (year < curyear)
			flage = true;
		return flage;
	}

	/**
	 * @param month
	 * @return
	 */
	public static boolean isFutureMonth(int month) {
		boolean flage = false;
		Calendar cal = Calendar.getInstance();
		int curmonth = cal.get(Calendar.MONTH);
		if (month > curmonth)
			flage = true;
		return flage;
	}

	/**
	 * @param year
	 * @return
	 */
	public static boolean isFutureOrCurrentYear(int year) {
		boolean flage = false;
		Calendar cal = Calendar.getInstance();
		int curyear = cal.get(Calendar.YEAR);
		if (year >= curyear)
			flage = true;
		return flage;
	}

	/**
	 * @param year
	 * @return
	 */
	public static boolean isFutureNotCurrentYear(int year) {
		boolean flage = false;
		Calendar cal = Calendar.getInstance();
		int curyear = cal.get(Calendar.YEAR);
		if (year != curyear && year > (curyear+1))
			flage = true;
		return flage;
	}

	/**
	 * @param floatValue
	 * @param roundingOff
	 *            Value
	 * @return
	 */
	public static float roundToDecimal(float Rval, int Rpl) {
		float p = (float) Math.pow(10, Rpl);
		Rval = Rval * p;
		float tmp = Math.round(Rval);
		return (float) tmp / p;
	}

	/**
	 * Replaces the message constants.
	 * 
	 * @param messageTemplate
	 * @param admAppln
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String replaceMessageText(String messageTemplate,
			AdmAppln admAppln, HttpServletRequest request) throws Exception {

		String program = "";
		String course = "";
		String selectedCourse = "";
		String applicationNo = "";
		String academicYear = "";
		String applicantName = "";
		String dateOfBirth = "";
		String placeOfBirth = "";
		String nationality = "";
		String religion = "";
		String subreligion = "";
		String category = "";
		String gender = "";
		String email = "";
		String contextPath = "";
		String logoPath = "";
		StringBuffer currentAddress = new StringBuffer();
		StringBuffer permanentAddress = new StringBuffer();
		byte[] photo = null;
		byte[] logo = null;

		String message = messageTemplate;

		HttpSession session = request.getSession(false);
		if (admAppln != null && admAppln.getPersonalData() != null) {
			if (admAppln.getPersonalData() != null) {
				if (admAppln.getPersonalData().getDateOfBirth() != null) {
					dateOfBirth = CommonUtil.getStringDate(admAppln
							.getPersonalData().getDateOfBirth());
				}
				if (admAppln.getPersonalData().getBirthPlace() != null) {
					placeOfBirth = admAppln.getPersonalData().getBirthPlace();
				}
				if (admAppln.getPersonalData().getFirstName() != null
						&& !admAppln.getPersonalData().getFirstName().trim()
								.isEmpty()) {
					applicantName = admAppln.getPersonalData().getFirstName();
				}
				if (admAppln.getPersonalData().getMiddleName() != null
						&& !admAppln.getPersonalData().getMiddleName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getMiddleName();
				}
				if (admAppln.getPersonalData().getLastName() != null
						&& !admAppln.getPersonalData().getLastName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getLastName();
				}
				if (admAppln.getPersonalData().getNationalityOthers() != null) {
					nationality = admAppln.getPersonalData()
							.getNationalityOthers();
				} else if (admAppln.getPersonalData().getNationality() != null) {
					nationality = admAppln.getPersonalData().getNationality()
							.getName();
				}
				if (admAppln.getPersonalData().getReligionOthers() != null) {
					religion = admAppln.getPersonalData().getReligionOthers();
				} else if (admAppln.getPersonalData().getReligion() != null) {
					religion = admAppln.getPersonalData().getReligion()
							.getName();
				}

				if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSectionOthers();
				} else if (admAppln.getPersonalData().getReligionSection() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSection().getName();
				}

				if (admAppln.getPersonalData().getGender() != null) {
					gender = admAppln.getPersonalData().getGender();
				}
				if (admAppln.getPersonalData().getEmail() != null) {
					email = admAppln.getPersonalData().getEmail();
				}
				if (admAppln.getPersonalData().getCasteOthers() != null) {
					category = admAppln.getPersonalData().getCasteOthers();
				} else if (admAppln.getPersonalData().getCaste() != null) {
					category = admAppln.getPersonalData().getCaste().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					course = admAppln.getCourseBySelectedCourseId().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					selectedCourse = admAppln.getCourseBySelectedCourseId()
							.getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null
						&& admAppln.getCourseBySelectedCourseId().getProgram() != null) {
					program = admAppln.getCourseBySelectedCourseId().getProgram().getName();
				}
				applicationNo = String.valueOf(admAppln.getApplnNo());
				academicYear = String.valueOf(admAppln.getAppliedYear());

				if (admAppln.getApplnDocs() != null) {
					Iterator<ApplnDoc> applnDocItr = admAppln.getApplnDocs()
							.iterator();

					while (applnDocItr.hasNext()) {
						ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
						if (applnDoc.getIsPhoto() != null
								&& applnDoc.getIsPhoto()) {
							photo = applnDoc.getDocument();

							if (session != null) {
								contextPath = request.getContextPath();
								contextPath = "<img src="
										+ contextPath
										+ "/PhotoServlet alt='Photo not available' width='150' height='150'>";
								session.setAttribute("PhotoBytes", photo);
							}
						}
					}
				}

				Organisation organisation = OrganizationHandler.getInstance()
						.getRequiredFile();
				if (organisation != null) {
					logo = organisation.getLogo();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
				}
				logoPath = request.getContextPath();
				logoPath = "<img src="
						+ logoPath
						+ "/LogoServlet alt='Logo not available' width='210' height='100'>";
			}

			if (admAppln.getPersonalData().getCurrentAddressLine1() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressLine1());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getCurrentAddressLine2() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressLine2());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCityByCurrentAddressCityId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCityByCurrentAddressCityId());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getStateByCurrentAddressStateId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getStateByCurrentAddressStateId().getName());
				currentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getCurrentAddressStateOthers() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressStateOthers());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData()
					.getCountryByCurrentAddressCountryId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCountryByCurrentAddressCountryId().getName());
				currentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getCurrentAddressCountryOthers() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressCountryOthers());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCurrentAddressZipCode() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressZipCode());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getParentAddressLine1() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressLine1());
				permanentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getParentAddressLine2() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressLine2());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCityByPermanentAddressCityId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getCityByPermanentAddressCityId());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getStateByParentAddressStateId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getStateByParentAddressStateId().getName());
				permanentAddress.append(' ');
			} else if (admAppln.getPersonalData().getParentAddressStateOthers() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressStateOthers());
				permanentAddress.append(' ');
			}

			if (admAppln.getPersonalData()
					.getCountryByPermanentAddressCountryId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getCountryByPermanentAddressCountryId().getName());
				permanentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getPermanentAddressCountryOthers() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getPermanentAddressCountryOthers());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getPermanentAddressZipCode() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getPermanentAddressZipCode());
				permanentAddress.append(' ');
			}
		}

		message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,
				applicantName);
		message = message.replace(CMSConstants.TEMPLATE_DOB, dateOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_POB, placeOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_NATIONALITY,
				nationality);
		message = message.replace(CMSConstants.TEMPLATE_SUBRELIGION,
				subreligion);
		message = message.replace(CMSConstants.TEMPLATE_RELIGION, religion);
		message = message.replace(CMSConstants.TEMPLATE_GENDER, gender);
		message = message.replace(CMSConstants.TEMPLATE_EMAIL, email);
		message = message.replace(CMSConstants.TEMPLATE_CASTE, category);

		message = message.replace(CMSConstants.TEMPLATE_PHOTO, contextPath);
		message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
		message = message.replace(CMSConstants.TEMPLATE_PROGRAM, program);
		message = message.replace(CMSConstants.TEMPLATE_COURSE, course);
		message = message.replace(CMSConstants.TEMPLATE_SELECTED_COURSE,
				selectedCourse);
		message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,
				applicationNo);
		message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR,
				academicYear);
		message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS,
				currentAddress);
		message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS,
				permanentAddress);
		return message;

	}

	public static final String numberToWord(final int num) {

		// special case to simplify later on
		if (num == 0) {

			return "zero";

		}

		// constant number names for each category

		// single digits
		final String n_1_9[] = new String[] { "", "One", "Two", "Three",
				"Four", "Five", "Six", "Seven", "Eight", "Nine" };
		// unfortunate special cases for ten, eleven, twelve, and teens
		final String n_10_19[] = new String[] { "Ten", "Eleven", "Twelve",
				"Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
				"Eighteen", "Nineteen" };
		// tens
		final String n_20_90[] = new String[] { "", "Twenty", "Thirty",
				"Fourty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };
		final String n_100 = "Hundred";
		final String n_1000 = "Thousand";
		final String n_1000000 = "Million";

		// use StringBuilder for efficient modifications
		StringBuilder numWord = new StringBuilder();

		int n = num;

		// append with selective recursion for all our cases
		if (n >= 1000000) {

			numWord.append(numberToWord(n / 1000000));
			numWord.append(' ');
			numWord.append(n_1000000);
			numWord.append(' ');
			n %= 1000000;

		}

		if (n >= 1000) {

			numWord.append(numberToWord(n / 1000));
			numWord.append(' ');
			numWord.append(n_1000);
			numWord.append(' ');
			n %= 1000;

		}
		if (n >= 100) {

			numWord.append(n_1_9[n / 100]);
			numWord.append(' ');
			numWord.append(n_100);
			numWord.append(' ');
			n %= 100;

		}
		if (n >= 20) {

			numWord.append(n_20_90[(n / 10) - 1]);
			numWord.append(' ');
			n %= 10;

		}
		if (n >= 10) {

			numWord.append(n_10_19[n - 10]);

		}
		if (n < 10) {

			numWord.append(n_1_9[n]);

		}

		return numWord.toString().trim();

	}

	// start murthy

	public static Integer getIntegerFromAlphaNumeric(String s) {
		Character ch = null;
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			ch = s.charAt(i);
			if (Character.isDigit(ch)) {
				temp.append(ch.toString());
			}
		}
		return Integer.valueOf(temp.toString());
	}

	public static Date ConvertsqlStringToDate(String dateString) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return date;
	}

	public static String formatSqlDate(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return formatDate;
	}

	// 2010-07-01
	public static String formatSqlDate1(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage());

		}
		return formatDate;
	}

	// End murthy
	public static String formatSqlDateTime(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return formatDate;
	}

	// Lohith
	public static String formatSqlDateTimeToString(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return formatDate;
	}

	public static String formatDateToDesiredFormatString(String dateString,
			String inputFormat, String outPutFormat) {
		String formatDate = "";
		Date date = null;
		try {

			DateFormat dateFormat = new SimpleDateFormat(inputFormat);
			SimpleDateFormat pattern = new SimpleDateFormat(outPutFormat);
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return formatDate;
	}

	public static java.sql.Timestamp ConvertDateToSQLTime(String dateString,
			String outPutFormat) {

		java.sql.Timestamp sqldate = null;
		if (dateString != null & !dateString.isEmpty()) {
			try {
				SimpleDateFormat pattern = new SimpleDateFormat(outPutFormat);
				Date date = (Date) pattern.parse(dateString);
				sqldate = new java.sql.Timestamp(date.getTime());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		return sqldate;
	}

	public static java.sql.Date ConvertDateToSQLDate(String dateString,
			String outPutFormat) {
		java.sql.Date sqldate = null;

		if (dateString != null & !dateString.isEmpty()) {

			SimpleDateFormat pattern = new SimpleDateFormat(outPutFormat);
			try {
				Date date = (Date) pattern.parse(dateString);
				java.sql.Timestamp timestamp = new Timestamp(date.getTime());
				long milliseconds = timestamp.getTime()
						+ (timestamp.getNanos() / 1000000);
				java.util.Date javadate = new java.util.Date(milliseconds);
				sqldate = new java.sql.Date(javadate.getTime());

			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}
		return sqldate;

	}

	public static Date ConvertStringToDateTime(String dateString) {
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss a");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return date;
	}

	public static String formatDate(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.error(e.getMessage());

		}
		return formatDate;
	}

	/**
	 * Used to send mail.
	 * 
	 * @param fromName
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param msg
	 * @return
	 */
	public static boolean sendSMS(SMS_Message smsTo)

	{

		boolean isSent = false;
		try {

			// Set properties
			Properties props = new Properties();
			Properties prop = new Properties();
			try {
				InputStream in = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.MAIL_FILE_CFG);
				prop.load(in);
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			}
			props.put("mail.smtp.host", prop.getProperty("mailServer"));
			props.put("mail.smtp.port", prop.getProperty("port"));

			isSent = true;
		} catch (Exception e) {
			// No need to process exception because
			// if one is thrown, isSent will be false
			log.error("Error in sending mail...::" + e);
		}

		return isSent;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Map sortMapByValue(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Map sortMapByValueDesc(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Map sortMapByValueForAlphaNumeric(Map map) {
		List list = new LinkedList(map.entrySet());
		
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {

		        String firstString = ((Map.Entry) (o1)).getValue().toString();
		        String secondString =((Map.Entry) (o2)).getValue().toString();
		 
		        if (secondString == null || firstString == null) {
		            return 0;
		        }
		 
		        int lengthFirstStr = firstString.length();
		        int lengthSecondStr = secondString.length();
		 
		        int index1 = 0;
		        int index2 = 0;
		 
		        while (index1 < lengthFirstStr && index2 < lengthSecondStr) {
		            char ch1 = firstString.charAt(index1);
		            char ch2 = secondString.charAt(index2);
		 
		            char[] space1 = new char[lengthFirstStr];
		            char[] space2 = new char[lengthSecondStr];
		 
		            int loc1 = 0;
		            int loc2 = 0;
		 
		            do {
		                space1[loc1++] = ch1;
		                index1++;
		 
		                if (index1 < lengthFirstStr) {
		                    ch1 = firstString.charAt(index1);
		                } else {
		                    break;
		                }
		            } while (Character.isDigit(ch1) == Character.isDigit(space1[0]));
		 
		            do {
		                space2[loc2++] = ch2;
		                index2++;
		 
		                if (index2 < lengthSecondStr) {
		                    ch2 = secondString.charAt(index2);
		                } else {
		                    break;
		                }
		            } while (Character.isDigit(ch2) == Character.isDigit(space2[0]));
		 
		            String str1 = new String(space1);
		            String str2 = new String(space2);
		 
		            int result;
		 
		            if (Character.isDigit(space1[0]) && Character.isDigit(space2[0])) {
		                Integer firstNumberToCompare = Integer.valueOf(Integer
		                        .parseInt(str1.trim()));
		                Integer secondNumberToCompare = Integer.valueOf(Integer
		                        .parseInt(str2.trim()));
		                result = firstNumberToCompare.compareTo(secondNumberToCompare);
		            } else {
		                result = str1.compareTo(str2);
		            }
		 
		            if (result != 0) {
		                return result;
		            }
		        }
		        return lengthFirstStr - lengthSecondStr;
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Map sortMapByKey(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getKey())
						.compareTo(((Map.Entry) (o2)).getKey());
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public static Map sortMapByKeyDesc(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getKey())
						.compareTo(((Map.Entry) (o1)).getKey());
			}
		});
		Map result = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	 /**
	  * Rounding to  n Decimal Points
	 * @param Rval
	 * @param Rpl
	 * @return
	 */
	public static double Round(double Rval, int Rpl) {
		  double p = (double)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  double tmp = Math.round(Rval);
		  return (double)tmp/p;
		    }
	
	
	public static final String numberToWord1(int num) {
		String[] SingleDigits = new String[] {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
		String[] DoubleDigits =new String[] {"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
		

        String Words = "";
        boolean St=false;
        if (num > 9999)
        {
                return "The number exceeds 9999.";
        }
        if (num == 0)
        {
                return SingleDigits[0];
        }
        for (int i = 9; i >= 1; i--)
        {
                if (num >= i * 1000)
                {
                        Words += SingleDigits[i];
                        St =true;
                        Words += " Thousand";
                        if (num != i * 1000) Words += " And ";
                        {
                                num -= i*1000;
                        }
                        i=0;
                }
        }
        for (int i = 9; i >= 1; i--)
        {
                if (num >= i * 100)
                {
                        Words += SingleDigits[i];
                        St = true;
                        Words += " Hundred";
                        if (num != i * 100) Words += " And ";
                        {
                                num -= i*100;
                        }
                        i=0;
                }
        }
        for (int i = 9; i >= 2; i--)
        {
                if (num >= i * 10)
                {
                        Words += (St?DoubleDigits[i-2]:DoubleDigits[i-2]);
                        St = true;
                        if (num != i * 10) Words += " ";
                        {
                                num -= i*10;
                        }
                        i=0;
                }
        }
       
        for (int i = 1; i < 20; i++)
        {
                if (num == i)
                {
                        Words += (St?SingleDigits[i]:SingleDigits[i]);
                }
        }
        return Words;

	}

	
	public static String getStringDateWithFullMonthName(Date date) {
		String stringDate = "";
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			stringDate = calendar.get(Calendar.DATE) + " "
					+ getMonthFullName(calendar.get(Calendar.MONTH)) + " "
					+ calendar.get(Calendar.YEAR);
		}
		return stringDate;
	}
	
	/**
	 * This takes index as argument returns corresponding 3 digit month name.
	 * 
	 * @param index
	 */
	public static String getMonthFullName(int index) {
		String months[] = { "January", "February", "March", "April", "May", "June", "July",
				"August", "September", "October", "November", "December" };
		return months[index];
	}
	/**
	 * This takes date (type String) as argument and returns next day date.
	 * 
	 * @param date
	 * @return
	 */
	public static String getNextDate(String date){
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		Date date1 = CommonUtil.ConvertStringToDate(date);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String nextDate = dateFormat.format(date1.getTime() + MILLIS_IN_DAY);
		return nextDate;
	}
	public static String formatDates(Date date) {
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		String dates=formater.format(date);
		return dates;
	}
	
	 /**
	 * @param d
	 * @return
	 */
	public static String sayDayName(Date d) {
	      DateFormat f = new SimpleDateFormat("EEEE");
	      try {
	        return f.format(d);
	      }
	      catch(Exception e) {
	        e.printStackTrace();
	        return "";
	      }
	    }
	/**
	 * @param string
	 * @return
	 */
	public  static boolean isStringContainsNumbers(String string)
	{
	      char[] c = string.toCharArray();
	      for(int i=0; i < string.length(); i++)
	      {
	          if ( !Character.isDigit(c[i]))
	          {
	             return false;
	          }
	     }
	     return true;
	}
	public static Date ConvertStringToDateNew(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "yyyy-MM-dd","MM/dd/yyyy");
		Date date = null;
		Date sqldate=null;
		try{
		if (formatDate != null & !formatDate.isEmpty()) {
			date = new Date(formatDate);
			sqldate = new java.sql.Date(date.getTime());
		}}catch(Exception e){
			e.printStackTrace();
		}
		return sqldate;
	}
	public static String ConvertStringToDate1(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "yyyy-MM-dd","MM/dd/yyyy");
		/*Date date = null;
		if (formatDate != null & formatDate != "") {
			date = new Date(formatDate);
		}*/
		return formatDate;
	}
	
	
	/**
	 * @param fromName
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param msg
	 * @return
	 */
	public static boolean sendMailToMoreRecepients(String fromName, String fromAddress,
			String toAddress, String subject, String msg)
	{
		boolean isSent = false;
		if(CMSConstants.mailSend){
			try{
				GenerateMail generateMail=new GenerateMail(fromName,fromAddress,toAddress,subject,msg,null);
				isSent=PropertyUtil.getInstance().save(generateMail);
			}catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return isSent;
	}
	
	
	/**
	*/
	public static int getSundaysForDateRange(String startDate,String endDate){
	int count=0;
	if (CommonUtil.isValidDate(startDate) && CommonUtil.isValidDate(endDate)) {
	GregorianCalendar firstdate = getDate(startDate,"/");
	GregorianCalendar seconddate = getDate(endDate,"/");
	count = countNumberOfSaturdaysAndSundays(firstdate, seconddate);
	}
	return count;
	}


	public static GregorianCalendar getDate(String datestring,String index){
	int day = toInt(datestring.substring(0,datestring.indexOf(index)));
	int month = toInt(datestring.substring(datestring.indexOf(index)+1,datestring.lastIndexOf(index)));
	int year = toInt(datestring.substring(datestring.lastIndexOf( index)+1));
	return new GregorianCalendar(year,month-1,day);
	}

	private static int toInt(String s){
	int n = -1;
	try{
	n = Integer.parseInt(s);
	}catch(NumberFormatException nfe){
	System.err.println("NumberFormatException: " + nfe.getMessage());
	}
	return n;
	}

	public static int countNumberOfSaturdaysAndSundays(GregorianCalendar first, GregorianCalendar second){
	int count = 0;
	Calendar currentcalendarday = first;
	Calendar lastcalendarday = second;
	while (!currentcalendarday.equals(lastcalendarday)){
//		if (isSaturday(currentcalendarday)) count++;
		if (isSunday(currentcalendarday)) count++;
		currentcalendarday.add(Calendar.DATE,1);
	}
	return count;
	}

	public static boolean isSaturday(Calendar calendardate){
		return (calendardate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
	}

	public static boolean isSunday(Calendar calendardate){
		return (calendardate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}
	
	public static boolean checkIsSunday(String date){
		GregorianCalendar firstdate = getDate(date,"/");
		Calendar currentcalendarday = firstdate;
		return isSunday(currentcalendarday);
	}
	public static java.sql.Date ConvertDateStringToSQLDate(
			String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString,
					"yyyy-MM-dd HH:mm:ss.SSS", "yyyy/MM/dd");
		java.sql.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.sql.Date(date.getTime());
		}
		return sqldate;
	}
	
	public static boolean checkIsSaturday(String date){
//		GregorianCalendar firstdate = getDate(date,"/");
		Calendar currentcalendarday = Calendar.getInstance();
		currentcalendarday.setTime(CommonUtil.ConvertStringToSQLDate(date));
		return isSaturday(currentcalendarday);
	}
	/**
	 * @param str
	 * @return
	 */
	public static String insertCommas(String str) {
		if(str.length() < 4){
			return str;
		}
		return insertCommas(str.substring(0, str.length() - 3)) + "," + str.substring(str.length() - 3, str.length());
	}
	
	public static String getFiveMinsTimeBack(){
		Calendar calendar = new GregorianCalendar();
		  String am_pm;
		  int hour = calendar.get(Calendar.HOUR);
		  int minute = calendar.get(Calendar.MINUTE);
		 if(minute <5){
			  hour = hour-1;
			  minute = (60+minute)- 5;
		  }else{
		  	minute = minute - 5;
		  }
		  if(calendar.get(Calendar.AM_PM) == 0)
			  am_pm = "AM";
			  else
			  am_pm = "PM";
		  if(am_pm.equalsIgnoreCase("PM")){
			  hour=hour+12;
		  }
		  if(minute<=9){
			  if(hour<=9)
			  return "0"+hour + ":0"+minute+ ":00";
			  else
			  return hour + ":0"+minute+ ":00";
		  }else{
			  if(hour<=9)
				  return "0"+hour + ":" + minute + ":00";
			  else
				  return hour + ":" + minute + ":00";
		  }
	}
	/**
	 * @return
	 */
	public static String getCurrentTime(){
		Calendar calendar = new GregorianCalendar();
		String am_pm;
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);

		if(calendar.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";
		if(am_pm.equalsIgnoreCase("PM")){
			hour=hour+12;
		}
		
		if(minute<=9){
			if(hour<=9)
				return "0"+hour + ":0"+minute+ ":00";
			else
				return hour + ":0"+minute+ ":00";
		}else{
			if(hour<=9)
				return "0"+hour + ":" + minute + ":00";
			else
				return hour + ":" + minute + ":00";
		}
	}
	
	/**
	 * Splits any string to a desired length
	 * @param value
	 * @return
	 */
	public static String splitString(String value, int length){
		StringBuffer appendedvalue = new StringBuffer();
		int len = value.length();
		String[] temp = new String[len];
		int begindex = 0, endindex = length;
		int count = 0;

		while (true) {
			if (endindex < len) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + length;
				endindex = endindex + length;
				appendedvalue.append(temp[count]).append(" ").toString();
			} else {
				if (count == 0)
					temp[count] = value.substring(0, len);
				temp[count] = value.substring(begindex, len);
				appendedvalue.append(temp[count]).toString();
				break;
			}
			count++;
		}
		return appendedvalue.toString();
	}
	
	/**
	 * @param minuend
	 * @param subtrahend
	 * @return
	 */
	public static int monthsBetweenTwoDates(Date minuend, Date subtrahend)  {
		Calendar cal = Calendar.getInstance();
		// default will be Gregorian in US Locales
		cal.setTime(minuend);
		int minuendMonth = cal.get(Calendar.MONTH);
		int minuendYear = cal.get(Calendar.YEAR);
		int minuendDay = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(subtrahend);
		int subtrahendMonth = cal.get(Calendar.MONTH);
		int subtrahendYear = cal.get(Calendar.YEAR);
		int subtrahendDay = cal.get(Calendar.DAY_OF_MONTH);

		if (minuendDay <= subtrahendDay)
			return Math.abs(((minuendYear - subtrahendYear) * 12) + (minuendMonth - subtrahendMonth));
		else
			return Math .abs((((minuendYear - subtrahendYear) * 12) + (minuendMonth - subtrahendMonth))) - 1;
	}
	public static String getYear(Date date){
		SimpleDateFormat format=new SimpleDateFormat("yyyy");
        String year=format.format(date);
		return year;
	}
	
	/**
	 * returns current month
	 * @return
	 */
	public static String getCurrentMonth() {
		return Integer.toString(Calendar.getInstance().get(Calendar.MONTH)+1);

	}
	/**
	 * returns the previous month in number
	 * @return
	 */
	public static String getPreviousMonth(){
		int previousMonth;
		int currentMonth=Integer.parseInt(CommonUtil.getCurrentMonth());
		previousMonth=currentMonth-1;
		if(previousMonth==0){
			previousMonth=12;
		}
		return Integer.toString(previousMonth);
	}
	/**
	 * returns the date of previous month
	 * @return
	 */
	public static String getPreviousMonthDate(){
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MONTH, -1);
	    String previousMonthDate= cal.get(Calendar.DATE)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.YEAR);
	    return previousMonthDate;
	}
	
	/**
	 * returns list of dates in a month
	 * @return
	 */
	public static List<String> getDatesOfMonth(String month,String year,boolean uptoCurrentDate){
		int mon= Integer.parseInt(month);
		int i;
		int lastDate=0;
		String day;
		if(uptoCurrentDate){
			Calendar c=Calendar.getInstance();
			lastDate=c.get(Calendar.DATE);
		}
		else{
		if(mon==1 || mon==3 || mon==5 || mon==7 || mon==8 || mon==10 || mon==12){
			lastDate=31;
		}
		else if(mon==4 || mon==6 || mon==9 || mon==11){
			lastDate=30;
		}
		else{
			if((Integer.parseInt(year))%4==0){
				lastDate=29;
			}
			else{
				lastDate=28;
			}
		}
	}
		if(month.length()==1){
			month="0"+month;
		}
		List<String> datesOfmonth= new ArrayList<String>();
		for(i=1;i<=lastDate;i++){
			if(String.valueOf(i).length()==1){
				day="0"+String.valueOf(i);
			}else day=String.valueOf(i);
			datesOfmonth.add(day+"-"+month+"-"+year);
		}
	    return datesOfmonth;
	}
	
    /**
     * returns a list of dates between two dates
     * @param date1
     * @param date2
     * @return
     */
    public static List<String> getDatesBetween(Date fromDate, Date toDate) {
    	List<String> dates = new ArrayList<String>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(fromDate);
	
	    while (calendar.getTime().before(toDate))
	    {
	        Date result = calendar.getTime();
	        dates.add(CommonUtil.getStringDate(result));
	        calendar.add(Calendar.DATE, 1);
	    }
	    if(dates!=null && !dates.isEmpty()){
	    	dates.add(CommonUtil.getStringDate(toDate));
	    }
	    else {
	    	dates.add(CommonUtil.getStringDate(fromDate));
	    }
	    return dates;
    }
    /**
     * @param dateString
     * @return
     */
    public static java.sql.Date ConvertStringToSQLDate1(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "yyyy/MM/dd",
					"dd/MM/yyyy");
		java.sql.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.sql.Date(date.getTime());
		}
		return sqldate;
	}
    
    public static String getTimeByDate(Date dateString) {
    	final long timestamp = dateString.getTime();

    	// with java.util.Date/Calendar api
    	final Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timestamp);
    	// and here's how to get the String representation
    	final String timeString =
    	    new SimpleDateFormat("HH:mm:ss").format(cal.getTime());
		 return timeString;
			}
    public static String ConvertStringToSQLDate2(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "yyyy-MM-dd",
					"dd/MM/yyyy");
		
		return formatDate;
	}
    
    /**
     * @param date
     * @return
     */
    public static String getDayForADate(String date) {
    	Date d=CommonUtil.ConvertStringToDate(date);
    	int dayOfWeek=d.getDay();
    	String day="";
    	if(dayOfWeek==0){
    		day="Sunday";
    	}else if(dayOfWeek==1){
    		day="Monday";
    	}else if(dayOfWeek==2){
    		day="Tuesday";
    	}else if(dayOfWeek==3){
    		day="Wednesday";
    	}else if(dayOfWeek==4){
    		day="Thursday";
    	}else if(dayOfWeek==5){
    		day="Friday";
    	}else if(dayOfWeek==6){
    		day="Saturday";
    	}
    	return day;
    }
    /**
     * @param outputStream
     * @param message
     * @throws Exception
     */
	public static void writePdf(ByteArrayOutputStream outputStream,
			String message, String realPath, HttpServletRequest request, String

			appNo) throws Exception {
		Document document = new Document();
		File file = new File(realPath);
		try {
			FileUtils.writeStringToFile(file, message);
			Reader htmlreader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			PdfWriter.getInstance(document, outputStream);
			document.open();
			StyleSheet styles = new StyleSheet();
			styles.loadTagStyle("body", "font", "Bitstream Vera Sans");
			// added by sudhir
			String relativePath;
			String s1 = request.getRealPath("");
			Properties prop = new Properties();
			InputStream in =

			DownloadEmployeeResumeAction.class.getClassLoader()
					.getResourceAsStream

					(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(in);
			String path = prop.getProperty

			("knowledgepro.admin.downloadResumePhotos.photosource");
			relativePath = s1 + path;
			File f = new File(relativePath);

			File[] filearr = f.listFiles();

			log.error("relativePath********" + relativePath);
			log.error("filearr.length   " + filearr);
			if (filearr.length != 0) {
				for (int j = 0; j < filearr.length; j++) {
					File f3 = filearr[j];
					String str1 = f3.toString();
					String str = f3.getName();
					if (str.contains(appNo)) {
						Image img = Image.getInstance(str1);
						img.scaleToFit(50f, 50f);
						img.setAlignment(2);
						document.add(img);
						f3.delete();
					}
				}
			}
			//
			ArrayList arrayElementList = HTMLWorker.parseToList(htmlreader,
					styles);
			for (int i = 0; i < arrayElementList.size(); ++i) {
				Element e = (Element) arrayElementList.get(i);
				document.add(e);
			}

			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean sendMailForPdfAttachment(MailTO mailTO,String realPath,HttpServletRequest request)	{
		boolean isSent = false;
		if(CMSConstants.mailSend){
		try {
			/*Properties config = new Properties() {
				{
					put("mail.smtps.auth", "true");
					put("mail.smtp.host", "smtp.gmail.com");
					put("mail.smtp.port", "465");
					put("mail.smtp.starttls.enable", "true");
					put("mail.transport.protocol", "smtp");
				}
			};
			Session carrierSession = Session.getInstance(config, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(CMSConstants.MAIL_USERID,
							CMSConstants.MAIL_PASSWORD);
				}
			});
			Transport transport = carrierSession.getTransport("smtps");
			transport.connect("smtp.gmail.com",CMSConstants.MAIL_USERID,
					CMSConstants.MAIL_PASSWORD);*/
			
				ByteArrayOutputStream outputStream = null;
				String fileName = "Application-";
				// now write the PDF content to the output stream
				outputStream = new ByteArrayOutputStream();
				/*MimeBodyPart pdfBodyPart;   //set
				MimeBodyPart mailBody = new MimeBodyPart();
				mailBody.setText(mailTO.getMailBody());
				MimeMultipart mimeMultipart = new MimeMultipart();  //set
*/				if (mailTO.getMessages() != null) {
					Iterator itr = mailTO.getMessages().keySet
					().iterator();
					System.out.println(realPath);
					String directory=PasswordGenerator.getPassword();
					File f=new File(realPath+"Application-"+directory);
					f.mkdir();
					String[] recipients=mailTO.getToAddress().split(",");
					for (int i = 0; i < recipients.length; i++){
						if(CommonUtil.validateEmailID(mailTO.getFromAddress()) && CommonUtil.validateEmailID(recipients[i])){
							isSent = sendMailForPDF(mailTO.getFromName(), mailTO.getFromAddress(), recipients[i], mailTO.getSubject(), mailTO.getMailBody(), realPath+"Application-"+directory);
						}
					}
					
					
					while(itr.hasNext()) {
						//pdfBodyPart = new MimeBodyPart();  //set
						String appNo = itr.next().toString();
						writePdf(outputStream, mailTO.getMessages().get (appNo), realPath+"Application-"+directory+"/" + fileName + appNo + ".pdf", request, appNo);
						byte[] bytes = outputStream.toByteArray();
						File pdfFile = new File(realPath+"Application-"+directory+"/"+ fileName + appNo+ ".pdf");
						FileOutputStream out = new FileOutputStream(pdfFile);
						out.write(bytes);
						out.close();
						
						/*DataSource dataSource = new

						ByteArrayDataSource(bytes, "application/pdf");  //set to mail
						pdfBodyPart.setDataHandler(new DataHandler

						(dataSource));
						pdfBodyPart.setFileName(fileName + appNo + ".pdf");
						mimeMultipart.addBodyPart(pdfBodyPart);  //set
*/					}
				}
				/*mimeMultipart.addBodyPart(mailBody);     //set
				InternetAddress iaSender = new InternetAddress

				(CMSConstants.MAIL_CARRER_USERID, mailTO.getFromName());
				InternetAddress iaRecipient = new InternetAddress(mailTO
						.getToAddress());

				// construct the mime message
				MimeMessage mimeMessage = new MimeMessage(MailMessage.carrierSession);
				// mimeMessage.setSender(iaSender);
				mimeMessage.setFrom(iaSender);
				mimeMessage.setSubject(mailTO.getSubject());
				mimeMessage.setRecipient(Message.RecipientType.TO,

				iaRecipient);
				mimeMessage.setContent(mimeMultipart);  //
*/			// Send message
//			Transport.send(mimeMessage);


//			transport.connect();
	        /*transport.sendMessage(mimeMessage, 
	        		mimeMessage.getRecipients(Message.RecipientType.TO));  //set
	        transport.close();*/
			// Set to true if no errors encountered
			//isSent = true;
		} catch (IOException e) {
			log.error("Error in sending mail...::" + e);
		} catch (MessagingException m) {
			isSent = false;
			log.error("Error in sending mail...::" + m);
		} catch (Exception e) {
			// No need to process exception because
			// if one is thrown, isSent will be false
			log.error("Error in sending mail...::" + e);
		}
		}
		return isSent;
	}
	/**
	 * Email Validation
	 * 
	 * @param date
	 */
	public static boolean validateEmailID(String email) {
		email = email.trim();
	    String reverse = new StringBuffer(email).reverse().toString();
	    if (email == null || email.length() == 0 || email.indexOf("@") == -1) {
	        return false;
	    }
	    if(email.contains(" ")){
	    	return false;
	    }
	    int emailLength = email.length();
	    int atPosition = email.indexOf("@");
	    int atDot = reverse.indexOf(".");

	    String beforeAt = email.substring(0, atPosition);
	    String afterAt = email.substring(atPosition + 1, emailLength);

	    if (beforeAt.length() == 0 || afterAt.length() == 0) {
	        return false;
	    }
	    for (int i = 0; email.length() - 1 > i; i++) {
	        char i1 = email.charAt(i);
	        char i2 = email.charAt(i + 1);
	        if (i1 == '.' && i2 == '.') {
	            return false;
	        }
	    }
	    if (email.charAt(atPosition - 1) == '.' || email.charAt(0) == '.' || email.charAt(atPosition + 1) == '.' || afterAt.indexOf("@") != -1 || atDot < 2) {
	        return false;
	    }

	    return true;
	}

	public static boolean sendMailInBulk(List<MailTO> mailToList) throws Exception {

		boolean isSent = false;
		if(CMSConstants.mailSend){
			org.hibernate.Session session=null;
			Transaction transaction = null;
		try {
			if(mailToList!=null && !mailToList.isEmpty()){
				Iterator<MailTO> itr=mailToList.iterator();
				int count = 0;
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				transaction = session.beginTransaction();
				transaction.begin();
				while (itr.hasNext()) {
					MailTO mailTO = (MailTO) itr.next();
					GenerateMail generateMail=new GenerateMail(mailTO.getFromName(),mailTO.getFromAddress(),mailTO.getToAddress(),mailTO.getSubject(),mailTO.getMessage(),mailTO.getAttachedFile());
					session.save(generateMail);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
				isSent=true;
			}
			
		}  catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				session.close();
			}
			isSent=false;
		}
		}
		return isSent;
	}
	/**
	 * @param fromName
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param msg
	 * @return
	 */
	public static boolean sendMailForPDF(String fromName, String fromAddress,
			String toAddress, String subject, String msg,String filePath)

	{

		boolean isSent = false;
		if(CMSConstants.mailSend){
		try {
			GenerateMail generateMail=new GenerateMail(fromName,fromAddress,toAddress,subject,msg,null,filePath);
			isSent=PropertyUtil.getInstance().save(generateMail);
			System.out.println(isSent);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		}
		return isSent;
	}
	public static String formatSqlDate2(String dateString) {
		String formatDate = "";
		Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat pattern = new SimpleDateFormat("yyyyMMdd");
			if (dateString != null & !dateString.isEmpty()) {
				date = dateFormat.parse(dateString);
				formatDate = pattern.format(date);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());

		}
		return formatDate;
	}
	/**
	 * @param fromDate
	 * @param toDate
	 * @param month1 
	 * @param month 
	 * @return
	 * @throws Exception
	 */
	public static int getMonthsBetweenTwoYears(Date fromDate,Date toDate, String month, String month1)throws Exception{
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();
		int months = 0;
		
		cal1.setTime(fromDate);
		cal2.setTime(toDate);
		
		int monthsBetween = 0;
        //difference in month for years
        monthsBetween = (cal2.get(Calendar.YEAR)-cal1.get(Calendar.YEAR))*12;
        //difference in month for months
        monthsBetween += cal2.get(Calendar.MONTH)-cal1.get(Calendar.MONTH);
        //difference in month for days
        months= monthsBetween % 12;
        
        if(Integer.parseInt(month1)<Integer.parseInt(month)){
        	if(months!=0){
        	months=months-1;
        }
        }
		return months;
	}
	public static java.util.Date ConvertStringToDateForAuditorium(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString, "MMM d,yyyy",
					"MM/dd/yyyy");
		java.util.Date sqldate = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			Date date = new Date(formatDate);
			sqldate = new java.util.Date(date.getTime());
		}
		return sqldate;
	}
	public static String ConvertStringToDateForAuditorium1(String dateString) {
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = ConvertStringToDateFormat(dateString,"dd/MM/yyyy","MMM dd, yyyy");
		return formatDate;
	}
	public static boolean isMonday(Calendar calendardate){
		return (calendardate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
	}
	public static boolean isHoliday(String holiday) throws Exception{
		boolean isHoliday = false;
//		List<String> holidayList = HostelLeaveHandler.getInstance().getHolidays();
		 isHoliday = HostelLeaveHandler.getInstance().getHolidays(holiday);
//		if(holidayList.contains(holiday)){
//			isHoliday = true;
//		}
		return isHoliday;
	}
	//dIlIp
	public static String getStringDateWithOrdianlFullMonthNameCommaYear(Date date) {
		String stringDate = "";
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			stringDate = getOrdinal(calendar.get(Calendar.DATE)) + " "
					+ getMonthFullName(calendar.get(Calendar.MONTH)) + ", "
					+ calendar.get(Calendar.YEAR);
		}
		return stringDate;
	}
	public static String getOrdinal(int i) {
	    return i % 100 == 11 || i % 100 == 12 || i % 100 == 13 ? i + "th" : i + new String[]{"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"}[i % 10];
	}
	//dIlIp
	
	public static boolean compareDates(Date tillDate,Date currentDate){
		boolean value = false;
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		 String tilldate = df.format(tillDate);
		 String current = df.format(currentDate);
		 if(tilldate.equals(current)){
			 value=true;
		 }
		 return value;
	}
	
	//raghu
	 public static boolean  isAlphaSpaceDot(String str) {

        if (str == null) {
            return false;
        }

        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != '.') && (str.charAt(i) != ' ')) {
                return false;
            }
        }
        return true;
    }
	 
	//Bhargav
	 public static String getStringDateAndTime(Date date){
			String stringDate = "";
			Calendar calendar = Calendar.getInstance();
			if (date != null) {
				calendar.setTime(date);
				stringDate = calendar.get(Calendar.DATE) + "-"
						+ getMonthName(calendar.get(Calendar.MONTH)) + "-"
						+ calendar.get(Calendar.YEAR);
				
				String smsMin = String.format("%02d", calendar.getTime().getMinutes());
				stringDate=stringDate+" "+calendar.getTime().getHours()+ ":"+smsMin+":00";
			}
			return stringDate;

		}
	
	 //Bhargav
	public static String ConvertStringToddMMyyyyFormat(String dateString)throws ParseException {
		Date date1 = new SimpleDateFormat("dd-MM-yy").parse(dateString);
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String challanDate = dateFormat.format(date1);
		return challanDate;

	}
	
	public static boolean validateddMMyyDate(String strDate) {

		if (strDate.trim().equals("")) {
			return true;
		}

		else {
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd-MM-yy");
			sdfrmt.setLenient(false);
			try {
				Date javaDate = sdfrmt.parse(strDate);

			} catch (ParseException e) {
				System.out.println(strDate + " is Invalid Date format");
				return false;
			}
			return true;
		}
	}

	public static boolean validateddMMyyyyDate(String strDate) {

		if (strDate.trim().equals("")) {
			return true;
		}

		else {
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			sdfrmt.setLenient(false);
			try {
				Date javaDate = sdfrmt.parse(strDate);

			} catch (ParseException e) {
				System.out.println(strDate + " is Invalid Date format");
				return false;
			}
			return true;
		}
	}

	public static Double round(Double d, int decimalPlace) {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setRoundingMode(RoundingMode.HALF_UP);
	        return Double.parseDouble(df.format(d));
	 }
	
	 
}