<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.fee.feeassignment"/></title>
</head>
<body>
<table width="98%" border="0">
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.fee"/><span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.fee.assignmentaccount"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.fee.assignmentaccount"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
        		  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
               	    <td height="20" colspan="6" class="body" align="left">
               	    <div id="errorMessage">
                       <FONT color="red"><html:errors/></FONT>
                       <FONT color="green">
							<html:messages id="msg" property="messages" message="true">
									<c:out value="${msg}" escapeXml="false"></c:out><br>
							</html:messages>
					  </FONT>
					  &nbsp;
					  </div>
               	    </td>
                   </tr>
                   <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top">
                      <table width="100%" cellspacing="1" cellpadding="2">
                          <tr >
          					<td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program.type"/></div></td>
                            <td width="10%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="programTypeName"/></td>
                            <td width="12%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.program.name"/>:</div></td>
                            <td width="14%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="programName"/></td>
                            <td width="15%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.admin.course"/>:</div></td>
                            <td width="13%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="courseName"/></td>
                            <td width="15%" height="25" class="row-odd" >&nbsp;</td>
                          </tr>
                           <tr >
                            <td width="21%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.academicyear"/>:</div></td>
                            <td width="10%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="academicYear"/></td>
                            <td width="12%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.semister"/>:</div></td>
                            <td width="14%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="semister"/></td>
                            <td width="15%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.fee.aidedUnaided"/>:</div></td>
                            <td width="13%" height="25" class="row-even" ><bean:write name="feeAssignmentForm" property="aidedUnAided"/></td>
                            <td width="15%" height="25" class="row-odd" >&nbsp;</td>
                          </tr>
                      </table></td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                </table>
        </div>
        </td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
       
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
        <div align="center">
              <table width="100%" height="156"  border="0" cellpadding="0" cellspacing="0">
                 <tr>
                   <td height="10" colspan="6" class="body" ></td>
                 </tr>
                 <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                 </tr>
                 
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td valign="top"  >
                   <c:set var="count" value="0"/>
                   <logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap">
                       <c:set var="count" value="${count + 1 }"/>
                   </logic:iterate>
                                 
                   <table width="100%" cellspacing="1" cellpadding="2">
                      <c:set var="dataId" value="0"/>
                      <c:set var="globalcount" value="0"/>
	                  <logic:iterate id="admitedThrough" name="feeAssignmentForm" property="admitedThroughMap">
	                      <tr >
	                           <td height="25" class="row-even" width="30%"><div align="center" class="heading"><bean:message key="knowledgepro.fee.admissioncategory"/></div></td>
	                           <td colspan='<c:out value="${count}"/>' class="row-even" align="left"> <div align="left" class="heading"><bean:write name="admitedThrough" property="value"/></div></td>
	                      </tr>
	                      <logic:iterate id="feeGroupMap" name="feeAssignmentForm" property="feeHeadingsMap" indexId="groupCount">
							<tr >
	                           <td height="25" class="row-even" width="30%"><div align="center" class="heading">Fee Group</div></td>
	                           <td colspan='<c:out value="${count}"/>' class="row-even" align="left"> <div align="left" class="heading"><bean:write name="feeGroupMap" property="key"/></div></td>
	                      	</tr>
	                      	<tr >
		                       <td height="25" class="row-odd" width="30%"><div align="center"><bean:message key="knowledgepro.fee.feename"/></div></td>
	                    	   <logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap"> 
		                     	  <td height="25" class="row-odd" ><div align="left"><bean:write name="feeAccount" property="value"/></div></td>
	                   		   </logic:iterate>  
	                     	</tr>
	                     <logic:iterate id="feeHeading" name="feeGroupMap" property="value">
	                     <tr >
	                           <td width="16%" height="25" class="row-even" width="30%"><div align="center"><bean:write name="feeHeading" property="value"/></div></td>
							   <logic:iterate id="feeAccount" name="feeAssignmentForm" property="feeAccountsMap">
			                       <td height="25" class="row-even" align="left"><div align="left">
			                         <nested:iterate id="feeAssignment" name="feeAssignmentForm" property="feeAssignmentList" indexId="c" type="com.kp.cms.to.fee.FeeAccountAssignmentTO">
			                             <c:set var="feeAccId"><bean:write name="feeAccount" property="key"/></c:set>	
							       		 <c:set var="feeHeadingId"><bean:write name="feeHeading" property="key"/></c:set>
							       		 <c:set var="admittedThId"><bean:write name="admitedThrough" property="key"/></c:set>
			                             <c:if test="${feeAssignment.feeAccountTo.id == feeAccId && feeAssignment.feeHeadingTo.id == feeHeadingId && feeAssignment.admittedThroughTO.id == admittedThId}">
			                             	<table><tr><td>
			                             	<bean:write name='feeAssignment' property='amount'/>
											</td>
											<td>&nbsp;<bean:write name='feeAssignment' property='currencyName'/></td>
											<td>LIG</td><td>&nbsp;<bean:write name='feeAssignment' property='ligAmount'/></td>
											<td></td></tr>
											<tr><td></td><td></td><td>Caste</td><td>&nbsp;<bean:write name='feeAssignment' property='casteAmount'/></td>
											<td></td></tr>
											</table>
							             </c:if>
			                  		 </nested:iterate>
			                         <c:set var="dataId" value="${dataId + 1 }"/>
			                       </div></td>
		                       </logic:iterate>
		                 </tr>
	                 </logic:iterate> 
		            </logic:iterate>       
	               </logic:iterate>
                   </table>
                   
                  </td>
                 <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
                 <tr>
	                  <td>
	                  
	                  </td>
                 </tr>    
                                
                 <tr>
                   <td width="5"  background="images/left.gif"></td>
                   <td height="35" valign="top" class="body" >
                   <table width="100%" height="27"  border="0" cellpadding="0" cellspacing="0">
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
                     <tr>
                       <td colspan="3" align="center"><div align="center">
                       <input type="button" class="formbutton" value="Close" onclick="javascript:self.close();"/>                       
                       </div></td>                      
                     </tr>                     
                     <tr><td style="height: 10px" align="center" colspan="1"></td></tr>                     
                   </table>
                   </td>
                   <td width="5" align="right" background="images/right.gif"></td>
                 </tr>
                 <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                </tr>
                <tr>
                      <td colspan="3"> &nbsp;</td>
                      
                </tr>
              </table>
            </div>
            </td>
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
</body>
</html>