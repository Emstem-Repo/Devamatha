<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<SCRIPT type="text/javascript">
function cancelAction() {
	document.location.href = "AdminHome.do";
}
</SCRIPT>
<html:form action="/PublishAdmitcard">
<html:hidden property="method" styleId="method" value="initPublishAdmitCard" />
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><a href="AdminHome.do" class="Bredcrumbs"><bean:message key="knowledgepro.admission"/></a> <span class="Bredcrumbs">&gt;&gt;<bean:message key="knowledgepro.admission.publishadmitcard"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> <bean:message key="knowledgepro.admission.publishadmitcard"/></strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="99%" cellspacing="1" cellpadding="2">
						
						<tr>
							<td height="15" colspan="10" class="row-white">
							<div align="left"><FONT color="green"> <html:messages
								id="msg" property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages> </FONT></div>
							</td>
						</tr>

						<tr>
							<td height="25" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.slno" /></div>
							</td>
							<td class="row-odd"><bean:message
								key="knowledgepro.admin.name" /></td>
							<td  class="row-odd">
							<bean:message
								key="knowledgepro.admission.applicationnumber" />
							</td>
						</tr>

						<c:set var="temp" value="0" />
						<nested:iterate name="publishAdmitcardForm" property="candidatesList"
							id="id" indexId="count">
							<c:choose>

								<c:when test="${temp == 0}">
									<tr>
										<td width="7%" height="25" class="row-even">
										<div align="center"><c:out value="${count+1}" /></div>
										</td>
										<td width="10%" class="row-even"><nested:write name="id"
											property="applicantName" /> &nbsp;</td>
										<td width="10%" height="25" class="row-even"><nested:write
											name="id" property="applicationNumber" />&nbsp;</td>

									</tr>
									<c:set var="temp" value="1" />
								</c:when>
								<c:otherwise>
									<tr>
									
										<td width="7%" height="25" class="row-white">
										<div align="center"><c:out value="${count+1}" /></div>
										</td>
										<td class="row-white"><nested:write name="id"
											property="applicantName" />&nbsp;</td>
										<td height="25" class="row-white"><nested:write name="id"
											property="applicationNumber" />&nbsp;</td>

									</tr>
									<c:set var="temp" value="0" />
								</c:otherwise>
							</c:choose>
						</nested:iterate>

					</table>
        <div align="center">
              <table width="100%" height="54"  border="0" cellpadding="1" cellspacing="2">
                <tr>
                  <td width="100%" height="50" class="heading"><div align="center">
                    <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                      
                      <tr>
                        <td><div align="center">
                            <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                <td width="52%" height="45"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                                  <tr>
                                 
                                    <td width="2%"></td>
                                    <td width="53%"><div align="center">
                                      <html:button property="cancel"  onclick="cancelAction()"  styleClass="formbutton"><bean:message key="knowledgepro.cancel"/></html:button></div>
                                    </td>
                                  </tr>
                                </table></td>
                              </tr>
                            </table>
                        </div></td>
                      </tr>
                    </table>
                  </div></td>
                </tr>
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/Tcenter.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
