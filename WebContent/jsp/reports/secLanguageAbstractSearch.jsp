<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>


<html:form action="/SecLanAbstract">
<html:hidden property="method" styleId="method" value="searchStudents"/>
<html:hidden property="formName" value="secondLanguageAbstractForm"/>
<html:hidden property="pageType" value="1"/>

<table width="98%" border="0">
  <tr>
    <td><span class="heading">
				<bean:message key="knowledgepro.reports" />
			<span class="Bredcrumbs">&gt;&gt; Second Language Abstract &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Second Language Abstract</strong></div></td>
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
							<td width="22%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.admin.program.type"/></div>
							</td>
							<td width="24%" height="25" class="row-even" align="left"><html:select property="programTypeId"  styleId="programtype" styleClass="combo">
                 			<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 			<c:if test="${programTypeList != null && programTypeList != ''}">
				    			<html:optionsCollection name="programTypeList" label="programTypeName" value="programTypeId"/>
				    		</c:if>	
	     					</html:select> 
 							<span class="star"></span></td>
							<td height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="admissionForm.studentedit.sem.label"/></div></td>
			                <td class="row-even" align="left">
								<html:select property="semister" styleId="semister" styleClass="combo">
									<html:option value=" "><bean:message key="knowledgepro.admin.select" /></html:option>
									<html:option value="1">1</html:option>
									<html:option value="2">2</html:option>
									<html:option value="3">3</html:option>
									<html:option value="4">4</html:option>
									<html:option value="5">5</html:option>
									<html:option value="6">6</html:option>
									<html:option value="7">7</html:option>
									<html:option value="8">8</html:option>
									<html:option value="9">9</html:option>
									<html:option value="10">10</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="22%" class="row-odd">
								<div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.applicationform.secLang.label"/></div>
							</td>
							<td width="24%" height="25" class="row-even" align="left">
							<html:select property="language"  styleId="language" styleClass="combo" >
                 				<html:option value=""><bean:message key="knowledgepro.admin.select"/></html:option>
                 				<c:if test="${secondLanguageMap != null}">
									<html:optionsCollection name="secondLanguageMap" label="value" value="key" />
								</c:if>
	     					</html:select> 
 							<span class="star"></span></td>
							<td width="22%" class="row-odd">&nbsp;
							</td>
							<td width="22%" class="row-even">&nbsp;
							</td>

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
