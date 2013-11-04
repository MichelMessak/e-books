<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajout d'un Contact</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

        <link rel="stylesheet" type="text/css" href="css/calendar/dhtmlxcalendar.css"/>
	<link rel="stylesheet" type="text/css" href="css/calendar/dhtmlxcalendar_dhx_skyblue.css"/>
	<script type="text/javascript" src="js/calendar/dhtmlxcalendar.js"></script>
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/login/popupStyle.css";
            @import "css/Help/helpStyle.css";
            @import "css/design.css";
            @import "css/Form/bootstrap.css";
            @import "css/Form/form.css";
            @import "css/login/popup.css";
	</style>

        <style type="text/css">
            input:focus
                {
                  border: 2px solid #000;
                  background: #F3F3F3;
                }
            select:focus
                {
                  border: 2px solid #000;
                  background: #F3F3F3;
                }
        </style>
    </head>
    <body>
         <%@include file="header.jspf"%>
        <center>
            <div id="mainContainer">
                <form:form method="POST" id="myForm" commandName="addContactForm">
                    <fieldset>
                       <div class="fieldgroup">
                                <label>Prenom</label>
                                <form:input path="firstName" title="Ajouter le prénom"/>
                                <form:errors path="firstName" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                        </div>
                        <div class="fieldgroup">
                                <label>Nom</label>
                                <form:input path="lastName" title="Ajouter le nom "/>
                                <form:errors path="lastName" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>
                        <div class="fieldgroup">
                                <label>Date de naissance</label>
                                <input placeholder="yyyy/dd/mm" id="calendar" place name="birthday" title="Ajouter la date de naissance"/>
                               
                        </div>

                        <div class="fieldgroup">
                                <label>Email</label>
                                <form:input path="email" title="Ajouter le courriel"/>
                                <form:errors path="email" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>
<p id ="message"></p>
                        <div class="fieldgroup">
                            <input id ="consult" type="submit" value="Valider" class="submit" title="Valider">
                            <div style="position: absolute;margin-left: 5px;">
                                <a href="reportContact.do?isTaskSubmit=Consult">
                                    <img alt="Retour" title="Retour" src="images/back.png" style="height: 60px;width: 60px;"/>
                                </a>
                            </div>
                        </div>
                    </fieldset>
                </form:form>
            </div>
        </center>
    <script type="text/javascript">
      // execute your scripts when the DOM is ready. this is a good habit
      $(function() {

            // select all desired input fields and attach tooltips to them
          $("#myForm :input").tooltip({

          // place tooltip on the right edge
          position: "center right",

          // a little tweaking of the position
          offset: [-2, 10],

          // use the built-in fadeIn/fadeOut effect
          effect: "fade",

          // custom opacity setting
          opacity: 0.7

          });
        });
    </script>
        <script type="text/javascript">
    var myCalendar;
    window.onload = function doOnLoad() {
            myCalendar = new dhtmlXCalendarObject(["calendar"]);
    }
</script>

<script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "80px";document.body.style.opacity = "1";}</script>
</body>
</html>
