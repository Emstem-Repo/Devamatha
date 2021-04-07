package com.kp.cms.actions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.inventory.ItemForm;


/**
 * This is a super class for all request. It will be used in generating exceptions.
 * setting the user id to the current form. This user id will be used in setting 
 * created by and modified by fields in DB.
 */
public class BaseDispatchAction extends DispatchAction {
	private static final Log log = LogFactory.getLog(BaseDispatchAction.class);
	
	/**
	 * This will get invoked when any Application exception occurs in application.
	 * @param e
	 * @return Exception e as a string
	 */
	public String handleApplicationException(Exception e) {

		Properties prop= new Properties();
		InputStream propStream=BaseDispatchAction.class.getResourceAsStream("/resources/exceptionMessage.properties");
		try {
			prop.load(propStream);
		} catch (IOException e1) {
			log.error("error in reading properties file...", e1);
		}
		String errormsg=null;
		try {
			//add exception instance check and add proper message in exceptionMessages.properties
			if(e instanceof ConstraintViolationException)
			{
				errormsg=prop.getProperty("org.hibernate.exception.ConstraintViolationException.message");

			}else if(e instanceof ArithmeticException)
			{
				errormsg=prop.getProperty("java.Lang.ArithmeticException.message");
			}
			else if(e instanceof NumberFormatException)
			{
				errormsg=prop.getProperty("java.Lang.NumberFormatException.message");
			}
			else{
				errormsg=prop.getProperty("java.Lang.Exception.message");
			}
			
		} catch (Exception e1) {
			log.error("error in reading exception handling...", e1);
		}
		return errormsg;
	}
	
	/**
	 * This will get invoked when any Business exception occurs in application.
	 * @param e
	 * @return exception e as string.
	 */
	public String handleBusinessException(Exception e) {

		String errorkey=null;
		try {
			//add exception instance check and add proper message in exceptionMessages.properties
			if(e instanceof ConstraintViolationException)
			{
				errorkey="org.hibernate.exception.ConstraintViolationException.message";

			}else{
				errorkey="java.Lang.Exception.message";
			}
		} catch (Exception e1) {
			log.error("error in reading exception handling...", e1);
		}
		return errorkey;
	}
	
	/**
	 * This method will set the user in to the form.
	 * @param request
	 * @param form
	 */
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}		
	/**
	 * This method will redirect to the required master entry.
	 * @param request
	 * @param form
	 */
	public ActionForward addNewMaster(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered into addNewMaster");
		BaseActionForm baseForm=(BaseActionForm)form;
		if(baseForm.getMainPage()!=null && !baseForm.getMainPage().isEmpty())
			request.setAttribute("actionClass",baseForm.getMainPage());
		else request.setAttribute("actionClass",baseForm.getSuperMainPage());
			log.info("Leaving into addNewMaster");
		return mapping.findForward("goToMasterPage");
	}		
}
