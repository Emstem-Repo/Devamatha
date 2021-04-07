<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<script language='JavaScript'>

	function viewDetails() {
		var url = "html/about.html";
		window.open(url,'aboutus','left=20,top=20,width=800,height=500,toolbar=1,resizable=0,scrollbars=1');
	}
	function viewHelp() {
		var url = "jsp/helpFiles/IndexHelp.jsp";
		window.open(url,'Help','left=20,top=20,width=900,height=500,toolbar=1,resizable=0,scrollbars=1');
	
	}
	function viewContactUsDetails() {
		var url = "html/contact_us.html";
		window.open(url,'contactus','left=20,top=20,width=325,height=175,toolbar=0,resizable=0,scrollbars=0');
	}
</script>
<head>
<style>
.headerwrapper {
    background-color:#2196f3;
    min-height: 60px;
    position: fixed;
    width: 100%;
    z-index: 1000;
}
.pageheader {
    padding: 0px;
    border-bottom: 0px solid #eee;
    padding-bottom: 20px;
    height: 25px;
    }
    .navbar-default {
    background-color:#21e0f36b;
   
}
.panel-body {
    padding: 0px;
}

</style>

</head>


            <div class="headerwrapper">
          
                <div class="header-left">
                    <a href="#" class="logo">
                        <img src="dist/images/logo.png" alt="Logo" /> 
                    </a>
                    <div class="pull-right">
                        <a href="" class="menu-collapse">
                            <i class="fa fa-bars"></i>
                        </a>
                    </div>
                </div><!-- header-left -->
               
                <div class="header-right" style="float: right;">
                  
                    <div class="pull-right">
                                <!--<div class="btn-group btn-group-list btn-group-notification">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-bell-o"></i>
                              <span class="badge">1</span>
                            </button>
                            <div class="dropdown-menu pull-right">
                                <a href="" class="link-right"><i class="fa fa-search"></i></a>
                                <h5>Notification</h5>
                                <ul class="media-list dropdown-list">
                                    <li class="media">
                                        
                                        <div class="media-body">
                                         <a href="" class="link-right"></a>
                                          <strong>Sample Notification</strong>
                                          
                                        </div>
                                    </li>
                                    
                                </ul>
                                 <ul class="media-list dropdown-list">
                                    <li class="media">
                                       
                                        <div class="media-body">
                                         <a href="" class="link-right"></a>
                                          <strong></strong> 
                                          <small class="date"><i class="fa fa-thumbs-up"></i></small>
                                        </div>
                                    </li>
                                    <li class="media">
                                    
                                        <div class="media-body">
                                          <strong></strong> shared a photo of you in your <strong>Mobile Uploads</strong> album.
                                          <small class="date"><i class="fa fa-calendar"></i> July 04, 2014</small>
                                        </div>
                                    </li>
                                    </ul>
                                    <div class="dropdown-footer text-center">
                                    <a href="" class="link">See All Notifications</a>
                                </div>
                            </div>
                            --><!-- dropdown-menu -->
                        </div>
                        <!-- btn-group -->
                              <!--<div class="btn-group btn-group-list btn-group-messages">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                                <i class="fa fa-envelope-o"></i>
                                <span class="badge">2</span>
                            </button>
                            <div class="dropdown-menu pull-right">
                                <a href="" class="link-right"><i class="fa fa-plus"></i></a>
                                <h5>New Messages</h5>
                                <ul class="media-list dropdown-list">
                                    <li class="media">
                                        <span class="badge badge-success">New</span>
                                        <img class="img-circle pull-left noti-thumb" src="dist/images/photos/user1.png" alt="">
                                        <div class="media-body">
                                          <strong>Sample Message</strong>
                                          
                                        </div>
                                    </li>
                                    
                                </ul>
                                <div class="dropdown-footer text-center">
                                    <a href="" class="link">See All Messages</a>
                                </div>
                            </div> dropdown-menu 
                        </div>
                        --><!-- btn-group -->  
                                               
                        <div class="btn-group btn-group-option">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-caret-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                              <li><a href="ChangePassword.do?method=initChangePassword"  onclick="appendMethodOnBrowserClose(),appendMethodOnBrowserClose()"><i class="glyphicon glyphicon-user"></i> Change Password</a></li>
                             
                              <li><a href="LogoutAction.do?method=logout" onclick="appendMethodOnBrowserClose(),appendMethodOnBrowserClose()"><i class="fa fa-lock"></i>Logout</a></li>
                            </ul>
                        </div><!-- btn-group -->
                        
                    </div><!-- pull-right -->
                    
                     </div><!-- header-right -->
                     
                </div><!-- headerwrapper -->          
                </header>   
                  <main class="main">         
          <div class="mainwrapper">     
         <div class="mainpanel">
                      <nav class="navbar navbar-default">
  <div class="container">
  
    <ul class="nav navbar-nav">
      <li class="breadcrumb-item"><a href="LoginAction.do?method=loginAction"  onclick="appendMethodOnBrowserClose(),appendMethodOnBrowserClose()"><i class="glyphicon glyphicon-home"></i>  &nbsp <b> Home</b></a></li>
      <li class="breadcrumb-item"><a href="javascript:void(0)"  onclick="viewDetails()"><i class="glyphicon glyphicon-user"></i>  &nbsp  <b>About Us</b></a></li>
      <li class="breadcrumb-item"><a href="javascript:void(0)" onclick="viewContactUsDetails()"><i class="glyphicon glyphicon-earphone"></i>  &nbsp    <b>Contact Us</b></a></li>
      <li class="breadcrumb-item"><a href=""><b>Welcome:&nbsp;</b>  &nbsp  <%=session.getAttribute("empname")%></a></li>
    <!--  <li class="breadcrumb-item"><a href="javascript:void(0)"  onclick="viewHelp()"><i class="fa fa-question-circle"></i>  &nbsp   <b>Help</b></a></li>
    -->
    </ul>
   
     
  </div>
</nav>
</div>
</div>
 </main>
             
             
               