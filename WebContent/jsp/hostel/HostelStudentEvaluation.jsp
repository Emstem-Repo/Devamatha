<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.to.hostel.HostelStudentEvaluationTO"%>
<%@page import="com.kp.cms.to.hostel.DisciplinaryDetailsTO"%>
<%@page import="java.util.Map"%>
<%@page import="com.kp.cms.forms.hostel.HostelStudentEvaluationForm"%>
<SCRIPT type="text/javascript">

function goToInitPage(){
	document.location.href="HostelStudentEvaluation.do?method=initHostelStudentEvaluation";
}

</SCRIPT>

<html:form action="/HostelStudentEvaluation">
	<html:hidden property="formName" value="hostelStudentEvaluationForm" />
	<html:hidden property="pageType" value="1" />

	

<div style="overflow: auto; width: 800px;">
	<table width="98%" border="0">
		<tr>
			<td><span class="heading"><a href="AdminHome.do"
				class="Bredcrumbs"><bean:message key="knowledgepro.hostel"/></a>
			<span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.studentEvaluation" /> &gt;&gt;</span></span></td>
		</tr>
		<tr>
			<td>
		<div style="overflow: auto; width: 914px; ">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10"><img src="images/Tright_03_01.gif"></td>
					<td width="100%" background="images/Tcenter.gif" class="body">
					<div align="left"><strong class="boxheader">
					  <bean:message key="knowledgepro.hostel.studentEvaluation"/></strong></div>
					</td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				<tr>
					<td valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">

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
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td width="100%" valign="top">
							<table width="99%" cellspacing="1" cellpadding="2">
								<tr>
									<td height="25" class="row-odd">
									<div align="center"><bean:message key="knowledgepro.slno" />
									</div>
									</td>
									<td height="25" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.fee.studentname" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admin.course" /></div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.exam.blockUnblock.regNo" /></div>
									</td>
									<td class="row-odd">
										<div align="center"><bean:message
											key="knowledgepro.hostel.checkin.roomNo" /></div>
										</td>
									<td width="9%" class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.attendance"/>%</div>
									</td>
									<td class="row-odd">
									<div align="center"><bean:message
										key="knowledgepro.admission.maxmarks" /></div>
									</td>
									<td class="row-odd">
									
									<div align="center"><bean:message
										key="admissionFormForm.marksObtained.required" /></div>
									
									</td>
									<td class="row-odd">
									
									<div align="center"><bean:message
										key="knowledgepro.admin.eligibility.tot.per" />Obtained</div>
									
									</td>
									<td class="row-odd">
									 <div align="center"><bean:message
										key="knowledgepro.hostel.studentEvaluation.result" /></div>
									</td>	
									<logic:notEmpty name="hostelStudentEvaluationForm" property="evaluationTO.disciplinaryDetails">
			                        <logic:iterate id="disciplinaryDetail" name="hostelStudentEvaluationForm" property="evaluationTO.disciplinaryDetails" indexId="count">
				                        <td  height="24" class="row-odd">				                        			                        
					                          <nested:write name="disciplinaryDetail" property="disciplinaryType"/>
				                         </td>
			                          </logic:iterate>
					              </logic:notEmpty>				
								</tr>
								<logic:notEmpty name="hostelStudentEvaluationForm" property="evaluationList">
								<logic:iterate name="hostelStudentEvaluationForm"
									property="evaluationList" id="evalId" indexId="count">
										
										<tr>
											<td class="row-even">
												<div align="center"><c:out value="${count+1}"></c:out>
												&nbsp;</div>
											</td>
											<td width="10%" height="25" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="studentName" />&nbsp;</div>
											 </td>
												<td width="8%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="course" /> &nbsp;</div>
												</td>
												<td width="8%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="registerNo" /> &nbsp;</div>
												</td>
												<td width="10%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="roomNo" /> &nbsp;</div>
												</td>
													<td width="7%" class="row-even">
													<div align="center"><nested:write name="evalId"
														property="attendancePercentage" /> &nbsp;</div>
													</td>
												<td class="row-even">
												<div align="center"><nested:write name="evalId"
													property="maxMarks" />&nbsp;</div>
												</td>
												<td width="9%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="marksObtained" />&nbsp;</div>
												</td>
												<td width="10%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="totalPercentageObtained" />&nbsp;</div>
												</td>
												<td width="11%" class="row-even">
												<div align="center"><nested:write name="evalId"
													property="result" />&nbsp;</div>
												</td>
												
												<logic:notEmpty name="hostelStudentEvaluationForm" property="evaluationTO.disciplinaryDetails">
							                        <logic:iterate id="disciplinaryDetail" name="hostelStudentEvaluationForm" property="evaluationTO.disciplinaryDetails" indexId="count1">
								 						<td width="11%" class="row-even">
												         <div align="center">
												         <%
												            HostelStudentEvaluationTO hostelEvalTO=(HostelStudentEvaluationTO)pageContext.getAttribute("evalId");
												            DisciplinaryDetailsTO disciplnTO=(DisciplinaryDetailsTO)pageContext.getAttribute("disciplinaryDetail");
														    Map<Integer,Integer> map=hostelEvalTO.getStudentDisciplinaryMap();
														    if(map!=null && map.size()>0){
														    	
														    	Integer disciplinaryCount=(Integer)map.get(Integer.parseInt(disciplnTO.getDisciplinaryTypeId()));
																if(disciplinaryCount==null)
																{
																	out.println("-");
																	
																}
																else
																{
												
																	out.println(disciplinaryCount);
																}
														    }
														    else
														    {
														    	out.println("-");
														    }
														  
								                        %> 
								                      </div>   
												 </td>       
								          	   </logic:iterate>
								              </logic:notEmpty>
	 									</tr>
									</logic:iterate>
                               </logic:notEmpty>
							</table>
							</td>

							<td width="5" height="30" background="images/right.gif"></td>
						</tr>

						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
							
						<tr align="center">
			              <td width="10%"></td>
			              	<td width="55%"><input type="button" class="formbutton" value="Back" onclick="goToInitPage()" /></td>   
						</tr>
					</table>

					<div align="center">
					<table width="100%" height="54" border="0" cellpadding="1"
						cellspacing="2">
						<tr>
							<td width="100%" height="50" class="heading">
							<div align="center">
							</div>
							</td>
						</tr>
					</table>
					</div>
					</td>
					<td width="10" valign="top" background="images/Tright_3_3.gif"
						class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" height="29" width="9"></td>
					<td background="images/TcenterD.gif" width="100%"></td>
					<td><img src="images/Tright_02.gif" height="29" width="9"></td>
				</tr>
			</table>
			</div>
			</td>
		</tr>
	</table></div>
</html:form>
