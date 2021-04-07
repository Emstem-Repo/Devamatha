package com.kp.cms.utilities;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class GenericStaticResponseHeaderFilter implements Filter {

  private final static String HEADERNAME_INIT_PARAM = "headername";
  private final static String HEADERVALUE_INIT_PARAM = "headervalue";


  private String headerName = null;
  private String headerValue = null;

  public void init(FilterConfig filterConfig) throws ServletException {
	headerName = filterConfig.getInitParameter(HEADERNAME_INIT_PARAM);
	headerValue = filterConfig.getInitParameter(HEADERVALUE_INIT_PARAM);
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
	  ServletException {

	HttpServletResponse httpResp = (HttpServletResponse) response;

	// pass on to other filters or the resource
	filterChain.doFilter(request, response);

	// set HTTP header on response
	httpResp.setHeader(headerName, headerValue);
  }

  public void destroy() {
  }
}