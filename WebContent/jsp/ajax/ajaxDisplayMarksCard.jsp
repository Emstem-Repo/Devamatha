<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
<c:if test="${msg==null}">
	<c:if test="${MarksCard!=null}">
			<logic:iterate id="StudentMarksTo" name="MarksCard" property="subMap">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
				<totalMark>
						<siNo> <c:out value="${count + 1}" /></siNo> 
						<subject><bean:write name="to" property="name"/></subject>
						<type><bean:write name="to" property="type"/></type>
						<cia><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal>
							 <logic:notEqual value="-" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded" /> </logic:notEqual> 
							 <logic:empty name="to" property="ciaMarksAwarded">  </logic:empty>
						</cia>
						<ese><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal>
							<logic:notEqual value="-" name="to" property="eseMaxMarks"> <bean:write name="to" property="eseMarksAwarded" /> </logic:notEqual>
							 <logic:empty name="to" property="eseMarksAwarded"> </logic:empty>
						</ese>
						<total><bean:write name="to" property="totalMarksAwarded"/></total>
						<credits><bean:write name="to" property="credits"/></credits>
						<grade><logic:equal value="-" name="to" property="grade">-</logic:equal>
							<logic:notEqual value="-" name="to" property="grade"> <bean:write name="to" property="grade" /> </logic:notEqual>
							 <logic:empty name="to" property="grade"> </logic:empty>
						</grade>
					</totalMark>
					</logic:iterate>
					
				</fields>
			</logic:iterate>
			<logic:iterate id="StudentMarksTo" name="MarksCard" property="addOnCoursesubMap">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
				<totalMark>
						<siNo> <c:out value="${count + 1}" /></siNo>
						<subject><bean:write name="to" property="name"/></subject>
						<type><bean:write name="to" property="type"/></type>
						<cia><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal>
							 <logic:notEqual value="-" name="to" property="ciaMaxMarks">   <bean:write name="to" property="ciaMarksAwarded" /> </logic:notEqual> 
							 <logic:empty name="to" property="ciaMarksAwarded">   </logic:empty>
						</cia>
						<ese><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal>
							<logic:notEqual value="-" name="to" property="eseMaxMarks">   <bean:write name="to" property="eseMarksAwarded" /> </logic:notEqual>
							 <logic:empty name="to" property="eseMarksAwarded">    </logic:empty>
						</ese>
						<total><bean:write name="to" property="totalMarksAwarded"/></total>
						<credits><bean:write name="to" property="credits"/></credits>
						<grade><logic:equal value="-" name="to" property="grade">-</logic:equal>
							<logic:notEqual value="-" name="to" property="grade"> <bean:write name="to" property="grade" /> </logic:notEqual>
							 <logic:empty name="to" property="grade"> </logic:empty>
						</grade>
					</totalMark>
					</logic:iterate>
					
				</fields>
			</logic:iterate>
			<result> <logic:equal value="-" name="MarksCard" property="result">-</logic:equal>
							<logic:notEqual value="-" name="MarksCard" property="result"> <bean:write name="MarksCard" property="result" /> </logic:notEqual>
							 <logic:empty name="MarksCard" property="result"> </logic:empty> </result>
	</c:if>
	</c:if>
	<c:if test="${msg!=null}">
		<msg><bean:write name="msg"/> </msg>
	</c:if>
</response>