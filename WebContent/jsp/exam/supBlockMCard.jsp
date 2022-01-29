<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
    <%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<body>
	
	<logic:notEmpty name="supMarksCard" scope="session">
                  		<logic:iterate id="to" name="supMarksCard" scope="session" type="com.kp.cms.to.exam.ShowMarksCardTO">
                  			<c:if test="${to.cnt==count}">
                  				<%
							String reason = ""; 
							if(to.getSupMCBlockReason()!=null){
								reason = to.getSupMCBlockReason();
							}
							%>
							<font color="red"> Your Marks Card is blocked due to <%=reason%></font>
                   		 </c:if>
                  		</logic:iterate>
                  </logic:notEmpty>
	
	</body>
</html>

										