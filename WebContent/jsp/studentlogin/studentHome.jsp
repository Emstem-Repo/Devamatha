<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<script type="text/javascript" src="js/clockh.js"></script>
<script type="text/javascript" src="js/clockp.js"></script>

<script type="text/javascript" src="js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="js/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
<script src="js/chart/amcharts.js" type="text/javascript"></script>
<script src="js/chart/serial.js" type="text/javascript"></script>


<meta name="viewport" content="width=device-width, initial-scale=1">

<style>
.img-bg {
	width: 150px;
	height: 150px;
	border-radius: 100%;
}

.user-img {
	margin: 0 auto;
	display: block;
	width: 100%;
	padding: 1px;
	height: 150px;
	width: 150px;
	border-radius: 100%;
}

.moredet:hover {
	cursor: pointer;
}

#full-det th {
	text-align: left;
}
</style>
<script type="text/javascript">
	var chart;

	var chartData =
<%=session.getAttribute("jsonChart")%>
	AmCharts.ready(function() {
		chart = new AmCharts.AmSerialChart();
		chart.dataProvider = chartData;
		chart.categoryField = "monthName";
		chart.startDuration = 1;
		chart.angle = 25;
		chart.depth3D = 10;
		var categoryAxis = chart.categoryAxis;
		categoryAxis.labelRotation = 90;
		categoryAxis.autoGridCount = false;
		categoryAxis.gridCount = chartData.length;
		categoryAxis.gridPosition = "start";
		var graph = new AmCharts.AmGraph();
		graph.valueField = "totalConduct";
		graph.balloonText = "[Conducted Periods]: <b>[[value]]</b>";
		graph.type = "column";
		graph.fillAlphas = 0.8;
		chart.addGraph(graph);

		var graph1 = new AmCharts.AmGraph();
		graph1.valueField = "totalPresent";
		graph1.balloonText = "[Attended Periods]: <b>[[value]]</b>";
		graph1.type = "column";
		graph1.fillAlphas = 0.8;
		chart.addGraph(graph1);

		var graph2 = new AmCharts.AmGraph();
		graph2.valueField = "totalAbsent";
		graph2.balloonText = "[Absented Periods]: <b>[[value]]</b>";
		graph2.type = "column";
		graph2.fillAlphas = 0.8;
		chart.addGraph(graph2);

		var graph3 = new AmCharts.AmGraph();
		graph3.valueField = "percentage";
		graph3.balloonText = "Percentage(%): <b>[[value]]</b>";
		graph3.type = "column";
		graph3.fillAlphas = 0.8;
		chart.addGraph(graph3);

		chart.write("chartdiv");
	});
</script>
<script>
	AmCharts.ready(function() {
		// chart code will go here
		var graph = new AmCharts.AmGraph();
		graph.valueField = "visits";
		graph.type = "column";
		chart.addGraph(graph);
	});
	chart.write('chartdiv');
</script>


<script type="text/javascript">
	// 1.
	$(document).ready(function() {

		$("#mobileNo").each(function() {
			$(this).keyup(function() {
				calculate();
			});
		});
	});

	function calculate() {
		$("#mobileNo")
				.each(
						function() {
							if (!isNaN(this.value) && this.value.length != 0) {
								if (this.value.length > 10
										|| this.value.length < 10) {
									document.getElementById('button').style.visibility = 'hidden';
								} else {
									document.getElementById('button').style.visibility = 'visible';
								}
							}
						});
	}
</script>

<script language="JavaScript">
	function submit() {
		var mobileNo = document.getElementById("mobileNo").value;
		document.location.href = "StudentLoginAction.do?method=submitMobileNo&mobileNo="
				+ mobileNo;
	}
	function openHtml() {
		var url = "StudentLoginAction.do?method=help";
		win2 = window
				.open(
						url,
						"Help",
						"left=1350,top=550,width=200,height=100,toolbar=0,resizable=0,scrollbars=0,addressbar=0");
	}

	function opendetailsStudent() {
		document.location.href ="StudentLoginAction.do?method=personalDataView";
	}
</script>
<body>
	<div class="pageheader"></div>
		<div class="col-sm-6">
			<div class="col-lg-12">
				<diV class="img-bg">
					<!-- <img class="user-img img-circle" src="bootstrap/assets/images/user.png" alt="user-img"> -->
					<img src='<%=request.getContextPath() + "/PhotoServlet?"%>' alt=""
						onerror='onImgError(this);' onLoad='setDefaultImage(this);'
						height="150" width="150" align="top" style="border-radius: 100%;" /><br>
					<br>
				</diV>
			</div>
			<div class="card">
				<div class="card-block">
					<div class="view-info">
						<div class="row">
							<div class="col-lg-12">
								<div class="general-info">
									<div class="row">
										<div class="col-lg-12 col-xl-12">
											<div class="table-responsive">
												<table class="table m-0">
													<tbody>
														<tr>
															<th scope="row"><bean:message
																	key="knowledgepro.studentlogin.name" /></th>
															<td><bean:write name="loginform"
																	property="studentName" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="knowledgepro.studentlogin.mobile.no" /></th>
															<td><bean:write name="loginform" property="mobileNo" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="knowledgepro.attendance.activityattendence.class" /></th>
															<td><bean:write name="loginform"
																	property="className" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionFormForm.fatherName" /></th>
															<td><bean:write name="loginform"
																	property="fatherName" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionFormForm.motherName" /></th>
															<td><bean:write name="loginform"
																	property="motherName" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionForm.studentinfo.nationality.label" /></th>
															<td><bean:write name="loginform"
																	property="nationality" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionForm.studentinfo.bloodgroup.label" /></th>
															<td><bean:write name="loginform"
																	property="bloodGroup" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionFormForm.emailId" /></th>
															<td><bean:write name="loginform"
																	property="contactMail" /></td>
														</tr>
														<logic:notEmpty name="loginform" property="bankAccNo">
															<tr>
																<th scope="row"><bean:message
																		key="knowledgepro.studentLogin.bankAcNo" /></th>
																<td><bean:write name="loginform"
																		property="bankAccNo" /></td>
															</tr>
														</logic:notEmpty>
														<tr>
															<th scope="row"><bean:message
																	key="admissionForm.studentinfo.currAddr.label" /></th>
															<td><bean:write name="loginform"
																	property="currentAddress1" />, <bean:write
																	name="loginform" property="currentAddress2" />, <bean:write
																	name="loginform" property="currentCity" />, <bean:write
																	name="loginform" property="currentState" />, <bean:write
																	name="loginform" property="currentPincode" /></td>
														</tr>
														<tr>
															<th scope="row"><bean:message
																	key="admissionForm.studentinfo.permAddr.label" /></th>
															<td><bean:write name="loginform"
																	property="permanentAddress1" />, <bean:write
																	name="loginform" property="permanentAddress2" />, <bean:write
																	name="loginform" property="permanentCity" />, <bean:write
																	name="loginform" property="permanentState" />, <bean:write
																	name="loginform" property="permanentPincode" /></td>
														</tr>

														<tr>
															<th scope="row" onclick="opendetailsStudent()"
																colspan="2" align="center"
																class="moredet btn btn-info btn-round">More Details</th>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
										<!-- end of table col-lg-6 -->
									</div>
									<!-- end of row -->
								</div>
								<!-- end of general info -->
							</div>
							<!-- end of col-lg-12 -->
						</div>
						<!-- end of row -->
					</div>
					<!-- end of view-info -->
					<!-- end of edit-info -->
				</div>
				<!-- end of card-block -->
			</div>
		</div>
		<div class="col-sm-6">
			<div class="panel panel-success">
				<div class="panel-heading">

					<Div id="LoginFormDiv">


						<span class="row"><i class="glyphicon glyphicon-hand-right"></i>
							<bean:message key="knowledgepro.studentlogin.newandevents" /></span>

						<div id="News">
							<marquee direction="up" scrolldelay="200">
								<c:out value='${loginform.description}' escapeXml='false' />
							</marquee>
						</div>
					</Div>

				</div>
				<div class="panel-body"></div>
			</div>
		</div>
</body>
<script language="JavaScript">
document.getElementById('button').style.visibility='hidden';
</script>