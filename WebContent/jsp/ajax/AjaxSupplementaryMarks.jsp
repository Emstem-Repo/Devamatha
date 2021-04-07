<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
<c:if test="${msg==null}">
	<c:if test="${MarksCardSup!=null}">
			<logic:iterate id="StudentMarksTo" name="MarksCardSup" property="subMap">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
					
				<totalMark>
						<theory><bean:write name="to" property="theory"/></theory> 
						<practical><bean:write name="to" property="practical"/></practical> 
						<appearedPractical><bean:write name="to" property="appearedPractical"/></appearedPractical> 
						<appearedTheory><bean:write name="to" property="appearedTheory"/></appearedTheory> 
						<c:if test="${to.theory!=null && to.theory==true && to.displaySubject==true && to.appearedTheory==true}">
						
						<siNo> <c:out value="${count + 1}" /></siNo> 
						<subject><bean:write name="to" property="name"/></subject>
						<type><bean:write name="to" property="type"/></type>
						<cia><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal>
							 <logic:notEqual value="-" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded" /> </logic:notEqual> 
							 <logic:empty name="to" property="ciaMarksAwarded">  </logic:empty>
						</cia>
						<ese><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal>
							<logic:notEqual value="-" name="to" property="eseMaxMarks"> <bean:write name="to" property="eseMarksAwarded" /> </logic:notEqual>
							 <logic:empty name="to" property="eseMarksAwarded">  </logic:empty>
						</ese>
						<total><bean:write name="to" property="totalMarksAwarded"/></total>
						<credits><bean:write name="to" property="credits"/></credits>
						<grade><logic:equal value="-" name="to" property="grade">-</logic:equal>
							<logic:notEqual value="-" name="to" property="grade"> <bean:write name="to" property="grade" /> </logic:notEqual>
							 <logic:empty name="to" property="grade"> </logic:empty>
						</grade>
					</c:if>
					<c:if test="${to.practical!=null && to.practical==true && to.displaySubject==true && to.appearedPractical==true}">
					
						<pracsiNo> <c:out value="${count + 1}" /></pracsiNo> 
						<pracsubject><bean:write name="to" property="name"/></pracsubject>
						<practype><bean:write name="to" property="subjectType"/></practype>
						<praccia><logic:equal value="-" name="to" property="practicalCiaMaxMarks">-</logic:equal>
							 <logic:notEqual value="-" name="to" property="practicalCiaMaxMarks"> <bean:write name="to" property="practicalCiaMarksAwarded" /> </logic:notEqual> 
							 <logic:empty name="to" property="practicalCiaMarksAwarded">  </logic:empty>
						</praccia>
						<pracese><logic:equal value="-" name="to" property="practicalEseMaxMarks">-</logic:equal>
							<logic:notEqual value="-" name="to" property="practicalEseMaxMarks"> <bean:write name="to" property="practicalEseMarksAwarded" /> </logic:notEqual>
							 <logic:empty name="to" property="practicalEseMarksAwarded">  </logic:empty>
						</pracese>
						<practotal><bean:write name="to" property="practicalTotalMarksAwarded"/></practotal>
						<praccredits><bean:write name="to" property="practicalCredits"/></praccredits>
						<pracgrade><logic:equal value="-" name="to" property="practicalGrade">-</logic:equal>
							<logic:notEqual value="-" name="to" property="practicalGrade"> <bean:write name="to" property="practicalGrade" /> </logic:notEqual>
							 <logic:empty name="to" property="practicalGrade"> </logic:empty>
						</pracgrade>
					</c:if>
					</totalMark>
					</logic:iterate>
					
				</fields>
			</logic:iterate>
			<logic:iterate id="StudentMarksTo" name="MarksCardSup" property="addOnCoursesubMap">
			<fields>
				<value> <bean:write name="StudentMarksTo" property="key"/> </value>
					<logic:iterate id="to" name="StudentMarksTo" property="value" indexId="count">
				<totalMark>
						<siNo> <c:out value="${count + 1}" /></siNo>
						<subject><bean:write name="to" property="name"/></subject>
						<type><bean:write name="to" property="type"/></type>
						<cia><logic:equal value="-" name="to" property="ciaMaxMarks">-</logic:equal>
							 <logic:notEqual value="-" name="to" property="ciaMaxMarks"> <bean:write name="to" property="ciaMarksAwarded" /> </logic:notEqual> 
							 <logic:empty name="to" property="ciaMarksAwarded">   </logic:empty>
						</cia>
						<ese><logic:equal value="-" name="to" property="eseMaxMarks">-</logic:equal>
							<logic:notEqual value="-" name="to" property="eseMaxMarks"> <bean:write name="to" property="eseMarksAwarded" /> </logic:notEqual>
							 <logic:empty name="to" property="eseMarksAwarded">   </logic:empty>
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
			<result> <logic:equal value="-" name="MarksCardSup" property="result">-</logic:equal>
							<logic:notEqual value="-" name="MarksCardSup" property="result"> <bean:write name="MarksCardSup" property="result" /> </logic:notEqual>
							 <logic:empty name="MarksCardSup" property="result"> </logic:empty> </result>
	</c:if>
	</c:if>
	<c:if test="${msg!=null}">
		<msg><bean:write name="msg"/> </msg>
	</c:if>
</response>