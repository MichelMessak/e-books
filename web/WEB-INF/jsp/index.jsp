<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="include.jspf" %>
<html>
    <head>
        <%@ page language="java"%>
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico">
        <meta name="Robots" content="all" >
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <link rel="stylesheet" type="text/css" href="css/reset.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/design.css" media="screen"/>
        <link rel="stylesheet" type="text/css" href="css/login/tooltips.css" media="screen"/>
	<link rel="stylesheet" type="text/css" href="css/Help/helpStyle.css"/>
        <link rel="stylesheet" type="text/css" href="css/login/popupStyle.css"/>
        <link type='text/css' href='css/login/loginTongue.css' rel='stylesheet' media='screen' />
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <title>e-books</title>

    <style type="text/css">

form input[type="submit"]{
    background: none;
    border: none;
    color: #6e9f7a;
    text-decoration: underline;
    cursor: pointer;
    position: fixed;
    margin-top: 2.9%;
    margin-left:-40%;
}
    </style>

        <style type="text/css">td, th {
            border: 1px solid black !important;
        }</style>
</head>
<body onload="checkVersion()">
<div id="header"></div>
<div>
    <div id="help" style="top:-120%;" class="forkit-curtain" >
    <div id="close" class="close-button"></div>
        <div id="LogIn">
            <div id="mainContainer">
                <form action="auth.do" method="POST" name="addUserForm" id="myForm">
                   <br><span class="titFormLogin">Ajouter son ID d'utilisateur</span>
                    <div>
                    <fieldset>
                        <div class="fieldgroup">
                            <input class="init" name="user_id" style="background:url('images/icon-username.png')no-repeat left; border: 1px; background-color: #fff;" onFocus="siguienteCampo ='user_pwd';" class="field_string" placeholder="ID de l'utilisateur" title="ID de l'utilisateur" required="required" tabindex="1"/>
                        </div>
                        <div class="fieldgroup"><br>
                             <input name="user_pwd" style="background:url('images/icon-password.png')no-repeat left; border: 1px;" onFocus="siguienteCampo ='button_submit';" class="field_pass" id="password" placeholder="Mot de Passe"type="password" title="Le mot de passe peut contenir tous les caractères"tabindex="2"/>
                        </div><br><br><br>
                        <div class="fieldgroup" style="border: black">
                        <input value="init" type="image" src="images/FileControl4-IniciarSesion-Boton.png"name="button_submit" title="Cliquer pour démarrer la session" style="margin-top: 2.5%;margin-left: 24%;"tabindex="3"/>
                        </div>
                    </fieldset>
                    </div>
                 </form>
            </div>
        </div>
    </div>
</div>
<div id="Logo"></div>
<div id="page_container">
<a style="transform: translate(0px, 0px) rotate(0deg); display: block;" id="login" class="forkit" data-text="Login" data-text-detached="Tirez vers le bas" href="#">
    <span style="transform: translate(80px, 0px) rotate(172.875deg); width: 3.13437px;" class="string"></span>
    <span style="transform: translate(80px, -15px) rotate(45deg);" class="tag"></span>
</a>
  <div id="content">
          <div style="text-align:justify">

           <c:if test="${not empty error}">
                <div id="error">
                    <span>${error}</span>
                </div>
            </c:if>
           <c:if test="${not empty exception}">
                <div id="error">
                    <span>${exception}</span>
                </div>
            </c:if>
            <c:if test="${not empty from}">
                <div id="from">
                    <span>Vous avez été redirigé depuis ${from}</span>
                </div>
            </c:if>
        </div>
  </div>
</div>

<div id="ImgIni"></div>
<script type="text/javascript">
  $(function(){$("#myForm :input").tooltip({position: "center right", offset: [6, 10],effect: "slide",opacity: 0.7});});
</script>
<script type="text/javascript">
function error()
{
var divs = document.getElementById("error");
var div2 = document.getElementById("from")
        if (divs != null || div2!= null)
            {
                document.getElementById('here').click();
            }
}
</script>
<script type="text/javascript" src="js/forkit.js" type="text/javascript"></script>
<script type="text/javascript">
function body()
        {
                document.body.style.backgroundImage = "url(images/backgeneral.jpg)";

                document.body.style.backgroundRepeat = "repeat-x";
                document.body.backgroundPosition = "top";
                document.body.style.top = "50px";
                document.body.style.opacity = "1";

         }
</script>
</body>
</html>