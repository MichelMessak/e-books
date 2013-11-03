<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="include.jspf" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file="htmlhead.jspf" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="shortcut icon" type="image/ico" href="images/FileControl.ico"/>
        <title>Supprimer une addresse</title>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <style type="text/css" title="currentStyle">
            @import "css/reset.css";
            @import "css/Help/helpStyle.css";
            @import "css/login/popup.css";
            @import "css/login/popupStyle.css";
            @import "css/design.css";
            @import "css/Form/bootstrap.css";
            @import "css/Form/form.css";
	</style>
<style type="text/css">
input:focus
    {
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
            <form:form method="POST" id="myForm" commandName="deleteAddressForm">
              <fieldset>
                       <div class="fieldgroup">
                                <label>Numéro</label>
                                <form:input path="number" title="Ajouter le prénom" readonly="true"/>
                                <form:errors path="number" cssClass="errorBlock" element="div" cssStyle="color:#D4763F;"/>
                        </div>
                        <div class="fieldgroup">
                                <label>Rue</label>
                                <form:input path="street" title="Ajouter le nom" readonly="true"/>
                                <form:errors path="street" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>
                        <div class="fieldgroup">
                                <label>Code Postal</label>
                                <form:input path="postalCode" title="Ajouter le code postal" readonly="true"/>
                                <form:errors path="postalCode" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>

                        <div class="fieldgroup">
                                <label>Ville</label>
                                <form:input path="city" title="Ajouter le courriel" readonly="true"/>
                                <form:errors path="city" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>

                        <div style="display:none;"class="fieldgroup">
                                <label>IDContact</label>
                                <form:input path="IDContact" title="ID" readonly="true"/>
                                <form:errors path="IDContact" cssClass="errorBlock" element="div"  cssStyle="color:#D4763F;"/>
                        </div>

                        <div class="fieldgroup">
                            <input type="submit" value="Valider" class="submit" title="Valider">
                            <div style="position: absolute;margin-left: 5px;">
                                <a href="reportAddress.do?isFilterSubmit=Consult">
                                    <img alt="Retour" title="Retour" src="images/back.png" style="height: 60px;width: 60px;"/>
                                </a>
                            </div>
                        </div>
                    </fieldset>
            </form:form>
        </div>
        </center>
     <div style="bottom: 8px;position: absolute;right: 1%;">
        <div id="buttonHelp">
            <a href="#" class="help" onclick="javascript:openDialog();"><img alt="HELP" src="images/help.png"></a>
        </div>
    </div>
     <div id="popupHelp" class="avgrund-popup">
        <button id="close" onclick="javascript:closeDialog();">Fermer</button>
        <h3 align="center" style="font-size: 15px;"><p class="topHelp">Suppression d'un utilisateur</p></h3><br>
        <p class="normalHelp" style="font-size: 15px;"></p>
        <p class="descripcionHelp" style="padding-top: 30px;">
            Pour retourner au menu antérieur, appuyer sur le bouton ???
            <img alt="HELP" class="imagesPopupHelp" style="height: 35px;width: 35px;" src="images/back.png">
        </p>
        <p class="descripcionHelp" style="padding-top: 30px;">
            Puor supprimer l'utilisateur appuyez sur le boutton ???
            <img alt="AYUDA" class="imagesPopupHelp" src="images/delete.png">
        </p>
        <br><br><center>_________________________________________________________</center><br><br>
     </div>
<script type="text/javascript" language="javascript">
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
<script type="text/javascript" src="js/login/popup.js" ></script><script type="text/javascript"> function openDialog() {Avgrund.show( "#popupHelp" );}function closeDialog() {Avgrund.hide();}</script><script type="text/javascript">function body(){document.body.style.backgroundImage = "url(images/backgeneral.jpg)"; document.body.style.backgroundRepeat = "repeat-x";document.body.backgroundPosition = "top";document.body.style.top = "50px";document.body.style.opacity = "1";}</script>
</body>
</html>