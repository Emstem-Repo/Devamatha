<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">

</script>
<html:form action="/InterviewComments"
	enctype="multipart/form-data">
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" value="getDetails" styleId="method" />
	<html:hidden property="formName" value="InterviewCommentsForm" />


<table width="98%" border="0">
  
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
                            <tr>
                              <td width="24%" height="23" class="row-odd" valign="top"><div align="right">Name.:</div></td>
                              <td  height="23" class="row-even" valign="top"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.name"/></td>
                              <td width="20%" height="20" class="row-odd" valign="top"><div align="right"> Address:</div></td>
                              <td width="34%" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.addressLine1"/><br/>
                               <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.addressLine2"/><br/>
                                <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.addressLine3"/>                                </td>
                           </tr>
                          <tr>
                          <td height="25" class="row-odd" ><div align="right">Nationality:</div></td>
                          <td height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.nationality"/></td>
                           <td height="23" class="row-odd"><div align="right">Zip Code:</div></td>
                              <td height="23" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.zipCode"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Gender:</div></td>
                          <td height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.gender"/></td>
                             <td height="23" class="row-odd"><div align="right">Country:</div></td>
                              <td height="23" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.country"/></td>
                        </tr>
                             <tr >
                          <td height="25" class="row-odd" ><div align="right">Marital Status:</div></td>
                          <td height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.maritalStatus"/></td>
                          <td height="23"  class="row-odd"><div align="right">City:</div></td>
                          <td height="23" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.city"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Date of Birth:</div></td>
                          <td width="22%" height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.dateOfBirth"/></td>
                  <td height="20" class="row-odd"><div align="right">Phone:</div></td>
                              <td height="20" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.phone1"/> &nbsp; <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.phone2"/> &nbsp;&nbsp;&nbsp; <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.phone3"/></td>
                        </tr>
                        <tr >
                          <td height="25" class="row-odd" ><div align="right">Age:</div></td>
                          <td width="22%" height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.age"/>                 </td>
                  <td height="20" class="row-odd"><div align="right">Mobile:</div></td>
                              <td height="20" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.mobPhone1"/> &nbsp; <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.mobPhone2"/> &nbsp;&nbsp; <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.mobPhone3"/></td>
                        </tr>
                        <tr >
                              <td height="20" class="row-odd" ><div align="right">E-mail:</div></td>
                              <td height="20" class="row-even" colspan="3" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.email"/></td>
                             
                        </tr>
                           
                            
                            
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
                          <tr class="row-white">
                            <td width="37%" height="33" class="row-odd">
                              <div align="right">Job Type:</div></td>
                            <td width="63%" height="33" class="row-even"> <bean:write name="InterviewCommentsForm" property="interviewCommentsTO.jobType"/></td>
                            </tr>
                          <tr class="row-white">
                            <td height="20" class="row-odd"><div align="right">Employment Status:</div></td>
                             <td width="63%" height="20" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.employmentStatus"/></td>
                            </tr>
                          <tr class="row-white">
                            <td height="30" class="row-odd"><div align="right">Expected salary(Per Annum):</div></td>
                            <td height="30" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.expectedSalary"/></td>
                            </tr>
                            <tr class="row-white">
                            <td width="37%" height="27" class="row-odd">
                              <div align="right">Desired Post :</div></td>
                            <td width="63%" height="27" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.desiredPost"/></td>
                            </tr>
                            <tr class="row-white">
                            <td width="37%" height="24" class="row-odd">
                              <div align="right">Department Applied For:</div></td>
                            <td width="63%" height="24" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.departmentAppliedFor"/></td>
                            </tr>
                            <tr class="row-white">
                            <td width="37%" height="24" class="row-odd">
                              <div align="right"> Date of Joining(if Selected):</div></td>
                            <td width="23%" height="25" class="row-even" ><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.dateOfJoining"/></td>
                            </tr>
                        <tr class="row-white">
                            <td width="37%" height="27" class="row-odd">
                              <div align="right">How did you get the information about the vacancy available at Christ University:</div></td>
                            <td width="63%" height="27" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.vacancyType"/></td>
                        </tr>
                        <tr class="row-white">
                            <td width="37%" height="27" class="row-odd">
                              <div align="right">Recommended By:</div></td>
                            <td width="63%" height="27" class="row-even"><bean:write name="InterviewCommentsForm" property="interviewCommentsTO.recommendedBy"/></td>
                            </tr>    
                          
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
                <td class="row-odd" >&nbsp;</td>
                <td height="25" class="row-odd" >Course</td>
                <td class="row-odd" >Year of Passing</td>
                <td class="row-odd" ><div align="center"> <p>Grade %</p> </div></td>
                <td width="21%" class="row-odd" ><div align="left">Name of the Institute/University</div></td>
              </tr>
              
              <logic:notEmpty property="interviewCommentsTO.listOfEdicationDetails" name="InterviewCommentsForm">
                 <nested:iterate property="interviewCommentsTO.listOfEdicationDetails"   indexId="edcount">
                   <c:choose>
						<c:when test="${edcount%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-white">
						</c:otherwise>
                    </c:choose>
                     <td width="15%" height="25"  ><nested:write property="course" /></td>
                     <td width="22%" height="25"  ><nested:write property="courseName" /></td>
                     <td width="23%" height="25"  ><nested:write property="yearOfPassing" /></td>
                     <td width="6%"  ><div align="center"><nested:write property="grade" /></div></td>
                     <td ><nested:write property="instituteUniversity" /></td>
                      </tr>
                 </nested:iterate>
               
               
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
                
             <logic:notEmpty property="interviewCommentsTO.listOfProfessionalExperience" name="InterviewCommentsForm">
               <nested:iterate property="interviewCommentsTO.listOfProfessionalExperience"   indexId="proExpcount">
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
                        <tr class="row-white">
                          <td width="25%" height="23" class="row-odd"><div align="right">Currently Working:</div></td>
                          <td width="21%" height="23" class="row-even"><nested:write property="currentlyWorking" /></td>
                          <td width="29%" height="20" class="row-odd"><div align="right">Teaching Experience:</div></td>
                          <td width="25%" height="20" class="row-even"><nested:write property="teachingExperience" /></td>
                        </tr>
                        <tr>
                          <td width="25%" height="23" class="row-odd"><div align="right">Education:</div></td>
                          <td width="21%" height="23" class="row-even"><nested:write property="education" /></td>
                          <td width="29%" height="23" class="row-odd"><div align="right">Industry Experience:</div></td>
                          <td height="20" class="row-even"><nested:write property="industryExperience" /></td>
                        </tr>
                        <tr class="row-white">
                          <td width="25%" height="23" class="row-odd"><div align="right">Qualification Level:</div></td>
                          <td width="21%" height="23" class="row-even"><nested:write property="qualificationLevel" /></td>
                          <td height="20" class="row-odd"><div align="right">Total Experience in:</div></td>
                          <td height="20" class="row-even"><nested:write property="totalExperience" /></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Functional Area:</div></td>
                          <td width="21%" height="23" class="row-even"><nested:write property="functionalArea" /></td>
                          <td class="row-odd"><div align="right">Current designation:</div></td>
                          <td class="row-even"><nested:write property="currentDesignation" /></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Organisation Currently Working:</div></td>
                          <td width="21%" height="23" class="row-even"><nested:write property="currentOrganisation" /></td>
                          <td class="row-odd"><div align="right">Current Salary(Per Annum):</div></td>
                          <td class="row-even"><nested:write property="currentSalary" /></td>
                        </tr>
                        <tr >
                          <td width="25%" height="23" class="row-odd"><div align="right">Previous Organisation Worked:</div></td>
                          <td height="23" class="row-even" colspan="3"><nested:write property="previousOrganisation" /></td>
                        </tr>
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
               
               </nested:iterate>
             </logic:notEmpty>
                
                
                
                
                
                
                
                 
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
											<div align="center">Achievements</div>
											</td>
											<td height="25" colspan="2" class="row-odd">
											<div align="center">Details</div>
											</td>
										</tr>

										<logic:notEmpty property="interviewCommentsTO.listOfAchievements" name="InterviewCommentsForm">
							                 <nested:iterate property="interviewCommentsTO.listOfAchievements"   indexId="account">
							                   <c:choose>
													<c:when test="${account%2 == 0}">
														<tr class="row-even">
													</c:when>
													<c:otherwise>
														<tr class="row-white">
													</c:otherwise>
							                    </c:choose>
													<td height="25" class="row-even" colspan="2"><nested:write property="achievements" /></td>
													<td height="25" class="row-even" colspan="2"><nested:write property="details" /></td>
												</tr>
											</nested:iterate>
										</logic:notEmpty>
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
                          <input name="button" type="submit" class="formbutton" value="Back" />
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