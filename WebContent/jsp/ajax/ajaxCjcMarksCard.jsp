<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${MarksCardCjc!=null}">
			<logic:iterate id="StudentMarksTo" name="MarksCardCjc" property="subMap" indexId="count1">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
					
				<totalMark>
						<siNo><c:out value="${count + 1}" /></siNo> 
						<subject><bean:write name="to" property="name"/></subject>
						<theory><bean:write name="to" property="theoryMarks"/> </theory>
						<practical><bean:write name="to" property="practicalMarks"/> </practical>
						<total><bean:write name="to" property="totalMarksAwarded"/></total>
						
					</totalMark>
					</logic:iterate>
				</fields>
			</logic:iterate>
			<logic:iterate id="StudentMarksTo" name="MarksCardCjc" property="addOnCoursesubMap">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
				<totalMark>
						<siNo><c:out value="${count + 1}" /></siNo>
						<subject><bean:write name="to" property="name"/></subject>
						<theory><bean:write name="to" property="theoryMarks"/> </theory>
						<practical><bean:write name="to" property="practicalMarks"/> </practical>
						<total><bean:write name="to" property="totalMarksAwarded"/></total>
					</totalMark>
					</logic:iterate>
				</fields>
			</logic:iterate>
	</c:if>
</response>