<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>

<script language="javascript" type="text/javascript">
	function printActionReport(){		
		var rep="";		
		for(var k=0;k<document.getElementById("noOfReports").value;k++){
			if(document.getElementById("printId"+k).checked==true){				
				rep=rep+document.getElementById("printId"+k).value+" ";
			}
		}		
		if(rep!=""){
			var url ="hostelActionReport.do?method=printHostelActionReport&checks="+rep;
			myRef = window.open(url,"actionReport","left=20,top=20,width=800,height=200,toolbar=1,resizable=0,scrollbars=1");
		}
   	}
	function backToHostelActionReportMain(){		
		document.location.href="hostelActionReport.do?method=displayHostelActionReportMainPage";
	}
</script>

	<table width="99%" border="0">
		<tr>
			<td><span class="heading"><bean:message
				key="knowledgepro.hostel" /> <span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.actionReport" /> &gt;&gt;</span> </span></td>
		</tr>
		<tr>
			<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="9"><img src="images/Tright_03_01.gif" width="9"
						height="29"></td>
					<td background="images/Tcenter.gif" class="heading_white"><bean:message
						key="knowledgepro.hostel.actionReport" /></td>
					<td width="10"><img src="images/Tright_1_01.gif" width="9"
						height="29"></td>
				</tr>
				
				<tr>
					<td height="43" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						
						
						
						<tr>							
							<td valign="top" style="height:10px"></td>
						</tr>
						<tr>
							<td><img src="images/01.gif" width="5" height="5" /></td>
							<td width="914" background="images/02.gif"></td>
							<td><img src="images/03.gif" width="5" height="5" /></td>
						</tr>
						<tr>
							<td width="5" background="images/left.gif"></td>
							<td valign="top">
								<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">									
									<tr>										
										<td valign="top">
											<table width="100%" cellspacing="1" cellpadding="2">
												<tr>												
													<td style="height:25px;width:50px;align:left" class="row-odd"><bean:message key="knowledgepro.slno" /></td>
													<td style="height:25px;width:200px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.name" /></td>
													<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.type" /></td>
													<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.hostelerdatabase.regno/staffId" /></div></td>
													<td style="height:25px;width:300px;align:left" class="row-odd"><bean:message key="knowledgepro.studentlogin.name" /></td>
													<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.noOfDaysAbsent" /></td>
													<td style="height:25px;width:100px;align:left" class="row-odd"><bean:message key="knowledgepro.hostel.actionReport.approvedNoOfDays" /></td>
													<td style="height:25px;width:50px;align:center" class="row-odd"><bean:message key="knowledgepro.exam.blockUnblock.select" /></div></td>
												</tr>

												<logic:notEmpty name="hostelActionReportForm" property="hostelActionReportList">
												
												<nested:iterate id="actionReportTO" property="hostelActionReportList" name="hostelActionReportForm" type="com.kp.cms.to.hostel.HostelActionReportTO" indexId="cnt" >
													<%String str="printId"+cnt; 
														//int len=hostelActionReportForm.hostelActionReportList.length;
													%>
												<tr>															
													<td style="height:25px;align:left" class="row-even"><c:out value="${cnt+1}" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="hostelName" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="typeId" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="regNo" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="name" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="daysOfAbsent" /></td>
													<td style="height:25px;align:left" class="row-even"><nested:write name="actionReportTO" property="daysOfAbsent" /></td>
													<td style="height:25px;align:center" class="row-even">
														<input type="checkbox" id='<%=str %>' value='<nested:write name="actionReportTO" property="srlNo" />' />
														<input type="hidden" id="noOfReports" value='<nested:write name="actionReportTO" property="sizeOfActionReports" />' />																											
													</td>
												</tr>
												</nested:iterate> 
												</logic:notEmpty>			
												

											</table>
										</td>										
									</tr>									
								</table>
							</td>
							<td width="5" height="29" background="images/right.gif"></td>
						</tr>
						<tr>
							<td height="5"><img src="images/04.gif" width="5" height="5" /></td>
							<td background="images/05.gif"></td>
							<td><img src="images/06.gif" /></td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>







				<tr>
					<td height="37" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="49%" height="35">
								<div align="right">
									<!--<html:button property="" styleClass="formbutton" value="Print" onclick="printActionReport()" />-->
									<input type="button" onclick="printActionReport()"  value="Print"/>	
								</div>
							</td>
							<td width="2%"></td>
							<td width="49%">
								<div align="left">
									<!--<html:button property="" styleClass="formbutton" value="Cancel" onclick="backToHostelActionReportMain();" />-->
									<input type="button" onclick="backToHostelActionReportMain()"  value="Cancel"/>
								</div>
							</td>
						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>







				<tr>
					<td height="13" valign="top" background="images/Tright_03_03.gif"></td>
					<td valign="top" class="news">
					
					</td>
					<td valign="top" background="images/Tright_3_3.gif" class="news"></td>
				</tr>
				<tr>
					<td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
					<td width="0" background="images/TcenterD.gif"></td>
					<td><img src="images/Tright_02.gif" width="9" height="29"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

