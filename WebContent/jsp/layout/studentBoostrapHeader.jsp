<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.kp.cms.constants.CMSConstants"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<script src="jquery/js/jquery-1.9.0.js" type="text/javascript"></script>
<header>
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
                
                <div class="header-right">
                    
                    <div class="pull-right">
                        
                         <div class="btn-group btn-group-list btn-group-notification">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-table"></i>
                              <span class="badge"></span>
                            </button>
                            <div class="dropdown-menu pull-right">
                             
                                
                                <ul class="media-list dropdown-list">
                                    <li class="media" onclick="gotPage('StudentLoginAction.do?method=getStudentTimeTable')" >
                                     
                                        <div class="media-body">
                                      
                                            <a href="StudentLoginAction.do?method=getStudentTimeTable" onclick="appendMethodOnBrowserClose(),appendMethodOnBrowserClose()">  <i class="fa fa-table"></i> Timetable</a>
                                        </div>
                                    </li>
                                    
                                </ul>
                              
                            </div><!-- dropdown-menu -->
                        </div><!-- btn-group -->  
                        
                        
                        
                        
                        
                        
                       <!--  <div class="btn-group btn-group-list btn-group-notification">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-bell-o"></i>
                              <span class="badge">0</span>
                            </button>
                            <div class="dropdown-menu pull-right">
                                <a href="" class="link-right"><i class="fa fa-search"></i></a>
                                <h5>Notification</h5>
                                <ul class="media-list dropdown-list">
                                    <li class="media">
                                        <img class="img-circle pull-left noti-thumb" src="images/photos/user1.png" alt="">
                                        <div class="media-body">
                                          <strong>Sample Notification</strong> likes a photo of you
                                          
                                        </div>
                                    </li>
                                    
                                </ul>
                                <div class="dropdown-footer text-center">
                                    <a href="" class="link">See All Notifications</a>
                                </div>
                            </div>dropdown-menu
                        </div>btn-group
                        
                        <div class="btn-group btn-group-list btn-group-messages">
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
                                        <img class="img-circle pull-left noti-thumb" src="images/photos/user1.png" alt="">
                                        <div class="media-body">
                                          <strong>Sample Message</strong>
                                          
                                        </div>
                                    </li>
                                    
                                </ul>
                                <div class="dropdown-footer text-center">
                                    <a href="" class="link">See All Messages</a>
                                </div>
                            </div>dropdown-menu
                        </div>btn-group -->
                        
                        
                       
                        
                        
                        
                        
                        
                        
                        
                        <div class="btn-group btn-group-option">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                              <i class="fa fa-caret-down"></i>
                            </button>
                            <ul class="dropdown-menu pull-right" role="menu">
                              <!--<li><a href="ChangePassword.do?method=initChangeStudentPassword"><i class="glyphicon glyphicon-user"></i> Change Password</a></li>-->
                             
                              <li><a href="StudentLoginAction.do?method=studentLogoutAction" ><i class="glyphicon glyphicon-log-out"></i>Sign Out</a></li>
                            </ul>
                        </div><!-- btn-group -->
                        
                    </div><!-- pull-right -->
                    
                </div><!-- header-right -->
                
            </div><!-- headerwrapper -->
        </header>
        <script type="text/javascript">
function gotPage(val) {
	document.location.href = val;
}
</script>