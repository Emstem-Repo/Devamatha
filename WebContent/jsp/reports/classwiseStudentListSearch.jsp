<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script type="text/javascript">
function getClasses(year) {
	getClassesByYear("classMap", year, "class", updateClasses);
}
function updateClasses(req) {
	updateOptionsFromMap(req, "class", " - Select -");
}
function getSubReligion(religion){
	getSubReligionByReligion("subreligionMap", religion, "subRelId",
			updateSubReligion);
	resetOption("subRelId");		
}
function updateSubReligion(req) {
	updateOptionsFromMap(req, "subRelId", "- Select -");
}
</script>

<html:form action="/ClassStudentList">
<html:hidden property="method" styleId="method" value="SearchStudentList"/>
<html:hidden property="formName" value="classwiseStudentListForm"/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.reports" />
			<span class="Bredcrumbs">&gt;&gt; Student List &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Student List</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
              <table width="100%" height="50"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
               	    <td height="20" colspan="6" align="left">
               	    <div align="right" style="color:red"> <span class='MandatoryMark'><bean:message key="knowledgepro.mandatoryfields"/></span></div>
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  </div>
               	    </td>
                 </tr>
                 <tr>
                 <tr>
                   <td height="49" colspan="6" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                     <tr>
                       <td ><img src="images/01.gif" width="5" height="5" /></td>
                       <td width="914" background="images/02.gif"></td>
                       <td><img src="images/03.gif" width="5" height="5" /></td>
                     </tr>
                     <tr>
                       <td width="5"  background="images/left.gif"></td>
                       <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
						<tr>
							<td height="25" class="row-odd" width="15%"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.attendanceentry.class" /></div></td>
				                <td height="25"  class="row-even" align="left" width="30%">
								<html:select name="classwiseStudentListForm" styleId="class" property="selClassids" size="5" style="width: 200px;" multiple="multiple">
									<html:optionsCollection name="classMap" label="value" value="key" />
								</html:select>
			                </td>
							<td width="15%" class="row-odd" valign="top">
								<div align="right"><bean:message key="knowledgepro.applicationform.secLang.label"/></div>
							</td>
							<td width="20%" height="25" class="row-even" align="left" valign="top" >
							<html:select property="language"  styleId="language" styleClass="combo" >
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 				<c:if test="${secondLanguageMap != null}">
									<html:optionsCollection name="secondLanguageMap" label="value" value="key" />
								</c:if>
	     					</html:select> 
 							<span class="star"></span></td>
							<td width="10%" class="row-odd">
								<div align="right"><bean:message key="knowledgepro.admin.religion.name"/></div>
							</td>
							<td width="10%" height="25" class="row-even" align="left">
							<html:select property="religionId"  styleId="religionId" styleClass="combo" onchange="getSubReligion(this.value)">
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 				<c:if test="${religionList != null}">
									<html:optionsCollection name="religionList" label="religionName" value="religionId" />
								</c:if>
	     					</html:select> 
 							<span class="star"></span></td>
						</tr>
						<tr>
							<td width="15%" class="row-odd" valign="top">
								<div align="right">Caste Category:</div>
							</td>
							<td width="30%" height="25" class="row-even" align="left" valign="top">
							<html:select property="subRelId"  styleId="subRelId" styleClass="combo" >
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 				<c:if test="${subreligionMap != null}">
									<html:optionsCollection name="subreligionMap" label="value" value="key" />
								</c:if>
	     					</html:select> 
 							<span class="star"></span></td>
							
							<td class="row-odd" valign="top" width="15%">
								<div align="right"><bean:message key="admissionForm.studentinfo.residentcatg.label"/></div>
							</td>
				                <td height="25" class="row-even" align="left" valign="top" width="20%">
								<html:select name="classwiseStudentListForm" styleId="studentCatId" property="selStCategoryIds" size="5" style="width: 200px;" multiple="multiple">
									<html:optionsCollection name="residentList" label="name" value="id" />
								</html:select>
			                </td>
							<c:choose>
							<c:when test="${classwiseStudentListForm.casteDisplay == true}">
							<td width="10%" class="row-odd" valign="top">
								<div align="right"><bean:message key="admissionForm.studentinfo.subreligion.label"/></div>
							</td>
							<td width="10%" height="25" class="row-even" align="left" valign="top">
							<html:select property="casteId"  styleId="casteId" styleClass="combo" >
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 				<c:if test="${casteList != null}">
									<html:optionsCollection name="casteList" label="casteName" value="casteId" />
								</c:if>
	     					</html:select> 
 							<span class="star"></span></td>
							</c:when>
							<c:otherwise>
								<td width="10%" class="row-odd">&nbsp;</td>
								<td width="10%" class="row-even">&nbsp;</td>
							</c:otherwise>
						</c:choose>

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
                 </tr>
                 <tr>
                   <td height="20" colspan="6" valign="top" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2"><tr ></tr>
                     <tr>
                       <td height="20" colspan="2" >   
		                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
		                  <tr>
		                    <td height="20"><div align="center">
			   				  <html:submit property="" styleId="print" styleClass="formbutton" value="Search"></html:submit>
		                      <html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
		                    </div></td>
		                    </tr>
		                </table>
                        </td>
                     </tr>
                   </table></td>
                </tr>
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
              </table>
            </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
<script type="text/javascript">
</script>