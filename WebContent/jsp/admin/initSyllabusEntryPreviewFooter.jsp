<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<body>
<html:form action="/syllabusEntry" >
<html:hidden property="formName" value="syllabusEntryForm" />
<body>
<table width="98%" border="0">
		 <tr>
			<td colspan="2">
				 <div id="footer">
				<bean:write name="syllabusEntryForm" property="departmentName"/>,MOUNT CARMEL
				</div>
			</td>
		</tr>
</table>
</body>
</html:form>