package com.kp.cms.actions.usermanagement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.utilities.HibernateUtil;

/**
 * A filter action authorize actions agenest user name and password.
 */
@SuppressWarnings("deprecation")
public class AuthorizeUser1 implements Filter {
	
	private static final Log log = LogFactory.getLog(AuthorizeUser.class);
	
	private String onErrorUrl;

	@Override
	public void destroy() {	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException{

 		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try{
		if (req.getRequestURI() != null
				&& (req.getRequestURI().endsWith("Login.do")
						|| req.getRequestURI().endsWith("LogoutAction.do") || req
						.getRequestURI().endsWith("LoginAction.do")|| req.getRequestURI().endsWith("StudentLoginAction.do")||req.getRequestURI().endsWith("AdmissionStatus.do") || req.getRequestURI().endsWith("StudentLogin1.do") || req.getRequestURI().contains("LoginAction.do"))) {

			chain.doFilter(request, response);
		}
		else {
			HttpSession session;
			String tempLogin=request.getParameter("tempLogin");
			if(tempLogin != null && !StringUtils.isEmpty(tempLogin) && tempLogin.equals("1")){
				session = req.getSession(true);
				if(session.getAttribute("loginform") == null) {
					LoginForm loginForm = new LoginForm();
					session.setAttribute("loginform",loginForm);
				}
				session.setAttribute("onlineuser",1);
				session.setAttribute("uid",CMSConstants.ONLINE_USERID);
				
			} else {
				session = req.getSession();
			}
			String user = (String) session.getAttribute("uid");
			ActionErrors errors = new ActionErrors();
			if (user == null || session == null) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
						"knowledgepro.admin.invalidsession"));
			}
			if (errors.isEmpty()) {
				if(req.getParameter("menuName") != null){
					session.setAttribute("realPath",request.getRealPath(""));
					FileWriter fw= new FileWriter(request.getRealPath("")+ "//TempFiles//menu.txt", true);
					BufferedWriter bw=new BufferedWriter(fw);
					bw.write("Menu Name :"+req.getParameter("menuName")+"    User Id:"+user+"  Time :"+new Date());
					bw.newLine();
					bw.close();
				}
				chain.doFilter(request, response);
			} else {

				req.setAttribute(Globals.ERROR_KEY, errors);
				req.getRequestDispatcher(onErrorUrl).forward(req, res);
			}
		}
		}finally{
			HibernateUtil.closeSession();
		}

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		onErrorUrl = filterConfig.getInitParameter("onError");
		if (onErrorUrl == null || "".equals(onErrorUrl)) {
			onErrorUrl = "/index.jsp";
		}
	}

}
