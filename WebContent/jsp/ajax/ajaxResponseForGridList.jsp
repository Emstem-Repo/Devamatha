<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${list!=null}">
		<logic:iterate id="list" name="list">
			<TList>
				<EType><bean:write name="list" property="examType"/></EType>
				<CCode><bean:write name="list" property="classcode"/></CCode>
				<EName><bean:write name="list" property="examName"/></EName>
				<PType><bean:write name="list" property="publishFor"/></PType>
				<SDate><bean:write name="list" property="downloadStartDate"/></SDate>
				<EDate><bean:write name="list" property="downloadEndDate"/></EDate>
				<RDate><bean:write name="list" property="revaluationEndDate"/> </RDate>
				<ExDate><bean:write name="list" property="extendedDate"/> </ExDate>
				<id><bean:write name="list" property="id"/> </id>
			</TList>
		</logic:iterate>
		
	</c:if>
</response>