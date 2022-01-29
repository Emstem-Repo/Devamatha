
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<script language="JavaScript" src="js/ajax/AjaxUtil.js"></script>
<script language="JavaScript" src="js/ajax/Ajax.js"></script>
<title>:: CMS ::</title>
<script>
function goToFirstPage() {
		document.location.href = "ExamUpdateExcludeWithheld.do?method=init";
	}
</script>
<html:form action="/ExamUpdateExcludeWithheld.do">

	<html:hidden property="method" styleId="method"
		value="addUpdateExcludeWithheld" />
	<html:hidden property="formName"
		value="ExamUpdateExcludeWithheldForm" />
	<html:hidden property="pageType" value="2" />


	<html:hidden property="examNameId_value" styleId="examNameId_value" />
	<html:hidden property="courseId" styleId="courseId" />
	<html:hidden property="schemeNo" styleId="schemeNo" />
	                        
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><bean:message	key="knowledgepro.exam" /> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.exam.UpdateExcludeWithheld" /> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader"> <bean:message key="knowledgepro.exam.UpdateExcludeWithheld" /></strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield"><bean:message key="knowledgepro.mandatoryfields" /></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                 
 <td width="25%" height="30" class="row-odd" ><div align="right"><bean:message key="knowledgepro.exam.UpdateExcludeWithheld.examName" /> :</div></td>
                  <td width="25%" height="30" class="row-even" ><bean:write name="ExamUpdateExcludeWithheldForm" property="examNameId" /></td>              
                  <td height="26" class="row-odd"><div align="right" ><bean:message
										key="knowledgepro.admin.course" /> :</div></td>
                <td class="row-even" ><bean:write name="ExamUpdateExcludeWithheldForm" property="courseName" /></td>
                </tr>
                <tr>
                  <td height="26" class="row-odd"><div align="right" ><bean:message
										key="knowledgepro.admission.scheme.col" /> </div></td>
                <td class="row-even"  ><bean:write name="ExamUpdateExcludeWithheldForm" property="schemeNo" /></td>
                  <td height="26" class="row-odd"><div align="right" >Exclude/With held for Scheme. </div></td>
                <td class="row-even"  ><bean:write name="ExamUpdateExcludeWithheldForm" property="oldScheme" /></td>
                
                </tr>
            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
        <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         
          
          
          
          <tr>
							<td height="45" colspan="4">
							<table width="98%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5" /></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5" /></td>
								</tr>
								<tr>
									<td width="5" background="images/left.gif"></td>
									<td valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd">
											<div align="center"><bean:message
												key="knowledgepro.slno" /></div>
											</td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.registerNo" /></td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.rollNo" /></td>
											<td width="30%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.studentName" /> </td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.excludeFromResults" /> </td>
											<td width="15%" height="25" class="row-odd" align="left"><bean:message
												key="knowledgepro.exam.UpdateExcludeWithheld.withHeld" /> </td>
											
										</tr>
										
										
										
										<c:set var="temp" value="0" />

										<logic:iterate name="ExamUpdateExcludeWithheldForm"
											property="listUpdateExcludeWithheld" id="listUpdateExcludeWithheld"
											type="com.kp.cms.to.exam.ExamUpdateExcludeWithheldTO"
											indexId="count">
											<c:choose>
												<c:when test="${temp == 0}">
													<tr>
														<td  height="25" class="row-even">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td  height="25" class="row-even"
															align=left><bean:write name="listUpdateExcludeWithheld"
															property="regNumber" /></td>
														<td height="25" class="row-even"
															align="left"><bean:write name="listUpdateExcludeWithheld"
															property="rollNumber" /></td>
														<td  height="25" class="row-even"
															align="left"><bean:write name="listUpdateExcludeWithheld"
															property="studentName" /></td>
														<td  height="25" class="row-even"
															align="center">
															
															
															
															
															<logic:equal name="listUpdateExcludeWithheld" property="excludeId" value="0">
															<input type="checkbox" 	name="exclude"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:equal>
															
															<logic:notEqual name="listUpdateExcludeWithheld" property="excludeId" value="0">
															<input type="checkbox" checked	name="exclude"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:notEqual>
														
														
														
															
															
															</td>
														<td  height="25" class="row-even"
															align="center">
															
															<logic:equal name="listUpdateExcludeWithheld" property="withheldId" value="0">
															<input type="checkbox" 	name="withheld"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:equal>
															
															<logic:notEqual name="listUpdateExcludeWithheld" property="withheldId" value="0">
															<input type="checkbox" checked	name="withheld"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:notEqual>
															</td>
														
													
													<c:set var="temp" value="1" />
												</c:when>
												<c:otherwise>
													<tr>
														<td height="25" class="row-white">
														<div align="center"><c:out value="${count+1}" /></div>
														</td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listUpdateExcludeWithheld"
															property="regNumber" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listUpdateExcludeWithheld"
															property="rollNumber" /></td>
														<td  height="25" class="row-white"
															align="left"><bean:write name="listUpdateExcludeWithheld"
															property="studentName" /></td>
														<td  height="25" class="row-white"
															align="center">
															
															<logic:equal name="listUpdateExcludeWithheld" property="excludeId" value="0">
															<input type="checkbox" 	name="exclude"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:equal>
															
															<logic:notEqual name="listUpdateExcludeWithheld" property="excludeId" value="0">
															<input type="checkbox" checked	name="exclude"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:notEqual>
															</td>
														<td  height="25" class="row-white"
															align="center">
															
															
															
															<logic:equal name="listUpdateExcludeWithheld" property="withheldId" value="0">
															<input type="checkbox" 	name="withheld"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:equal>
															
															<logic:notEqual name="listUpdateExcludeWithheld" property="withheldId" value="0">
															<input type="checkbox" checked	name="withheld"  value='<bean:write name="listUpdateExcludeWithheld"
															property="exclude" />'/>
															</logic:notEqual>
															
															
															</td>
														
													</tr>
													<c:set var="temp" value="0" />
												</c:otherwise>
											</c:choose>
										</logic:iterate>


									</table>
									</td>
									<td width="5" height="30" background="images/right.gif"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5" /></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif" /></td>
								</tr>
							</table>
							</td>
						</tr>
          
          
          
          
          
          
          
          
          
         
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr><td style="height: 10px" align="center" colspan="4"></td></tr>
          <tr>
            <td width="47%" height="35"><div align="right">
              <input name="button2" type="Submit" class="formbutton" value="Submit" />
            </div></td>
            <td width="1%"></td>
          
            <td width="1%"></td>
            <td width="46%">
            
            <input type="Reset"
								class="formbutton" value="Cancel" onclick="goToFirstPage()"/>
           </td>
          </tr>          
          <tr><td style="height: 10px" align="center" colspan="4"></td></tr>          
        </table> </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>


</html:form>

