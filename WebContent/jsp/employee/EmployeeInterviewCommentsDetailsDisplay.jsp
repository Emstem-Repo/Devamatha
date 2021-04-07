<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	
	<LINK REL=StyleSheet HREF= "css/styles.css" TYPE="text/css">
<script type="text/javascript">
		function back(){
			 document.location.href ="newInterviewComments.do?method=searchDetails";
			}
</script>

</head>
<body>
<html:form action="/newInterviewComments">
	<html:hidden property="formName" value="newInterviewCommentsForm"/>
	<html:hidden property="method" styleId="method" value="viewInterviewComments"/>
<table width="98%" border="0">
  
  <tr>
  <td><span class="heading">
				Employee
			<span class="Bredcrumbs">&gt;&gt; Interview Comments&gt;&gt;</span></span> </td>
	</tr>
	<tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" ></td>
        <td width="100%" background="images/Tcenter.gif" class="body" ><div align="left" class="boxheader"><strong class="boxheader">Interview Comments</strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            
            
          </table>
            <div align="center">
              <table width="100%" height="1095"  border="0" cellpadding="1" cellspacing="2">
              
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                      <tr>
                        <td ><img src="images/01.gif" width="5" height="5"></td>
                        <td width="914" background="images/02.gif"></td>
                        <td><img src="images/03.gif" width="5" height="5"></td>
                      </tr>
                      <tr>
                        <td width="5"  background="images/left.gif"></td>
                        <td height="57" valign="top"><table width="100%" height="192" border="0" cellpadding="0" cellspacing="1">
                        <logic:notEmpty name="newInterviewCommentsForm" property="empOnlineResumeList">
             			 <logic:iterate id="empDetailsList" name="newInterviewCommentsForm" property="empOnlineResumeList">
                            <tr>
                              <td width="24%" height="23" class="row-odd" valign="top"><div align="right">Name.:</div></td>
                              <td  height="23" class="row-even" valign="top">&nbsp; &nbsp;&nbsp;<bean:write property="name" name="empDetailsList"/></td>
                              <td width="20%" height="20" class="row-odd" valign="top"><div align="right"> Address:</div></td>
                              <td width="34%" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="address" name="empDetailsList"/><br/>
                                                           </td>
                           </tr>
                          <tr>
                          <td height="25" class="row-odd" ><div align="right">Nationality:</div></td>
                          <td height="25" class="row-even" >&nbsp; &nbsp;&nbsp;<bean:write property="nationality" name="empDetailsList"/></td>
                           <td height="23" class="row-odd"><div align="right">Zip Code:</div></td>
                              <td height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="zipCode" name="empDetailsList"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Gender:</div></td>
                          <td height="25" class="row-even" >&nbsp; &nbsp;&nbsp;<bean:write property="gender" name="empDetailsList"/></td>
                             <td height="23" class="row-odd"><div align="right">Country:</div></td>
                              <td height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="country" name="empDetailsList"/></td>
                        </tr>
                             <tr >
                          <td height="25" class="row-odd" ><div align="right">Marital Status:</div></td>
                          <td height="25" class="row-even" >&nbsp; &nbsp;&nbsp;<bean:write property="maritalStatus" name="empDetailsList"/></td>
                          <td height="23"  class="row-odd"><div align="right">City:</div></td>
                          <td height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="city" name="empDetailsList"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Date of Birth:</div></td>
                          <td width="22%" height="25" class="row-even" >&nbsp; &nbsp;&nbsp;<bean:write property="dateofBirth" name="empDetailsList"/></td>
                  <td height="20" class="row-odd"><div align="right">Phone:</div></td>
                              <td height="20" class="row-even"> &nbsp;&nbsp;&nbsp;<bean:write property="phone" name="empDetailsList"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Age:</div></td>
                          <td width="22%" height="25" class="row-even" >&nbsp; &nbsp;&nbsp;<bean:write property="age" name="empDetailsList"/> </td>
                  <td height="20" class="row-odd"><div align="right">Mobile:</div></td>
                              <td height="20" class="row-even"> &nbsp; &nbsp;&nbsp;<bean:write property="mobileNo" name="empDetailsList"/> </td>
                        </tr>
                        <tr >
                              <td height="20" class="row-odd" ><div align="right">E-mail:</div></td>
                              <td height="20" class="row-even" colspan="3" >&nbsp; &nbsp;&nbsp;<bean:write property="email" name="empDetailsList"/></td>
                             
                        </tr>
                           
                            
                           </logic:iterate>
              				  </logic:notEmpty> 
                        </table></td>
                        <td  background="images/right.gif" width="5" height="57"></td>
                      </tr>
                      <tr>
                        <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                        <td background="images/05.gif"></td>
                        <td><img src="images/06.gif" ></td>
                      </tr>
                  </table></td>
                </tr>
                
               
                
                 <tr>
                  <td colspan="2" class="heading">Desired Job Structure</td>
                </tr>
                <tr>
                  <td class="heading" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="25" valign="top"><table width="100%" height="150" border="0" cellpadding="0" cellspacing="1">
                       <logic:notEmpty name="newInterviewCommentsForm" property="empOnlineResumeList">
             			 <logic:iterate id="empDetailsList" name="newInterviewCommentsForm" property="empOnlineResumeList">
                          <tr class="row-white">
                            <td width="37%" height="33" class="row-odd">
                              <div align="right">Job Type:</div></td>
                            <td width="63%" height="33" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="empJobType" name="empDetailsList"/> </td>
                            </tr>
                          <tr class="row-white">
                            <td height="20" class="row-odd"><div align="right">Employment Status:</div></td>
                             <td width="63%" height="20" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="employmentStatus" name="empDetailsList"/></td>
                            </tr>
                          <tr class="row-white">
                            <td height="30" class="row-odd"><div align="right">Expected salary(Per Annum):</div></td>
                            <td height="30" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="expectedSalary" name="empDetailsList"/></td>
                            </tr>
                            <tr class="row-white">
                            <td width="37%" height="27" class="row-odd">
                              <div align="right">Desired Post :</div></td>
                            <td width="63%" height="27" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="empDesiredPost" name="empDetailsList"/></td>
                            </tr>
                            <tr class="row-white">
                            <td width="37%" height="24" class="row-odd">
                              <div align="right">Department Applied For:</div></td>
                            <td width="63%" height="24" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="department" name="empDetailsList"/></td>
                            </tr>
                            
                       
                         </logic:iterate>
                            </logic:notEmpty>
                      </table></td>
                      <td  background="images/right.gif" width="5" height="31"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                  </tr>
                
                <tr>
                  <td colspan="2" class="heading">Educational Details</td>
                </tr>
                
                <tr>
                  <td colspan="2" class="heading"><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="31" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td class="row-odd" >Sl.No</td>
                <td class="row-odd" >Course</td>
                <td class="row-odd" >Year of Passing</td>
                <td class="row-odd" ><div align="center"> <p>Grade %</p> </div></td>
                <td width="21%" class="row-odd" ><div align="left">Name of the Institute/University</div></td>
              </tr>
              <logic:notEmpty name="newInterviewCommentsForm" property="educationalDetailsList">
             			 <logic:iterate id="empDetailsList" name="newInterviewCommentsForm" property="educationalDetailsList" indexId="count">
               <tr>
                <td class="row-even" align="center"><c:out value="${count+1}"/></td>
                <td class="row-even" height="30" ><bean:write property="degree" name="empDetailsList"/></td>
                <td class="row-even" height="30"><bean:write property="yearOfPassing" name="empDetailsList"/></td>
                <td class="row-even" height="30"><bean:write property="marks" name="empDetailsList"/></td>
                <td  class="row-even" height="30"><bean:write property="university" name="empDetailsList"/></td>
              </tr>
             </logic:iterate>
                </logic:notEmpty>
            </table></td>
                      <td  background="images/right.gif" width="5" height="31"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                </tr>
                
                 <tr>
                  <td class="heading">&nbsp;Professional Experience</td>
                 
                </tr>
                
               <tr>
                  <td class="heading" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5"></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5"></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td height="57" valign="top"><table width="100%" height="57" border="0" cellpadding="0" cellspacing="1">
                      <logic:notEmpty name="newInterviewCommentsForm" property="empOnlineResumeList">
             			 <logic:iterate id="empDetailsList" name="newInterviewCommentsForm" property="empOnlineResumeList">
                        <tr class="row-white">
                          <td width="25%" height="23" class="row-odd"><div align="right">Currently Working:</div></td>
                          <td width="21%" height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="currentlyWorking" name="empDetailsList"/></td>
                          <td width="29%" height="20" class="row-odd"><div align="right">Teaching Experience:</div></td>
                          <td width="25%" height="20" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="teachingExperience" name="empDetailsList"/></td>
                        </tr>
                        <tr>
                          <td width="25%" height="23" class="row-odd"><div align="right">Education:</div></td>
                          <td width="21%" height="23" class="row-even"></td>
                          <td width="29%" height="23" class="row-odd"><div align="right">Industry Experience:</div></td>
                          <td height="20" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="industryExperience" name="empDetailsList"/></td>
                        </tr>
                        <tr class="row-white">
                          <td width="25%" height="23" class="row-odd"><div align="right">Qualification Level:</div></td>
                          <td width="21%" height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="qualificationName" name="empDetailsList"/></td>
                          <td height="20" class="row-odd"><div align="right">Total Experience:</div></td>
                          <td height="20" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="totExp" name="empDetailsList"/></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Functional Area:</div></td>
                          <td width="21%" height="23" class="row-even"></td>
                          <td class="row-odd"><div align="right">Current Designation:</div></td>
                          <td class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="currentDesignation" name="empDetailsList"/></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Organisation Currently Working:</div></td>
                          <td width="21%" height="23" class="row-even">&nbsp; &nbsp;&nbsp;<bean:write property="currentOrganization" name="empDetailsList"/> </td>
                          <td class="row-odd"><div align="right">Current Salary(Per Annum):</div></td>
                          <td class="row-even"></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Previous Organisation Worked:</div></td>
                          <td height="23" class="row-even" colspan="3">&nbsp; &nbsp;&nbsp;<bean:write property="empPreviousOrg" name="empDetailsList"/> </td>
                        </tr></logic:iterate></logic:notEmpty>
                       </table></td>
                      <td  background="images/right.gif" width="5" height="57"></td>
                    </tr>
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" ></td>
                    </tr>
                  </table></td>
                 
                </tr>
                 <tr>
							<td class="heading">&nbsp;Achievements</td>
						</tr>


						<tr>
							<td colspan="2" class="heading">
							<table width="99%" border="0" align="center" cellpadding="0"
								cellspacing="0">
								<tr>
									<td><img src="images/01.gif" width="5" height="5"></td>
									<td width="914" background="images/02.gif"></td>
									<td><img src="images/03.gif" width="5" height="5"></td>
								</tr>

								<tr>
									<td width="5" background="images/left.gif"></td>
									<td height="31" valign="top">
									<table width="100%" cellspacing="1" cellpadding="2">
										<tr>
											<td height="25" class="row-odd" colspan="2">
											<div align="center">Sl.No</div>
											</td>
											<td height="25" class="row-odd" colspan="2">
											<div align="center">Achievements</div>
											</td>
											<td height="25" colspan="2" class="row-odd">
											<div align="center">Details</div>
											</td>
										</tr>
										<logic:notEmpty name="newInterviewCommentsForm" property="acheivementSet">
             							 <logic:iterate id="empDetailsList" name="newInterviewCommentsForm" property="acheivementSet" indexId="count">
										<tr>
										
             							 <td align="center" class="row-even" colspan="2" ><c:out value="${count+1}"/></td>
											<td height="25" class="row-even" colspan="2">
											<div align="center"><bean:write property="acheivementName" name="empDetailsList"/></div>
											</td>
											<td height="25" colspan="2" class="row-even">
											<div align="center"><bean:write property="details" name="empDetailsList"/></div>
											</td>
										</tr>
										</logic:iterate></logic:notEmpty>
									</table>
									</td>
									<td background="images/right.gif" width="5" height="31"></td>
								</tr>
								<tr>
									<td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
									<td background="images/05.gif"></td>
									<td><img src="images/06.gif"></td>
								</tr>
							</table>
							</td>
						</tr>
						
               
                <tr>
                  <td colspan="2"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="49%" height="35"><div align="right">
                      <html:button property="" styleClass="formbutton" value="Back" onclick="back()"></html:button>
                      </div></td>
                      <td width="2%"></td>
                      <td width="49%"></td>
                    </tr>
                  </table></td>
                </tr>
                
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" ></td>
        <td  background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>
</body>
</html>
