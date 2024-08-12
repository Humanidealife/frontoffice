<%-- 
    Document   : work-details
    Created on : 2 août 2024, 21:59:44
    Author     : wangq
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Détail de l'oeuvre</title>
    </head>
    <body>
        <h1>Descriptif de l'oeuvre</h1>
        <!--
        "work" qui est l'identifiant de la variable stockée dans le Scope "request" avec le nom de la propriété, en sachant que 
          Java va utiliser le "getter"
        -->
        Titre : ${work.title}<BR/>
        Année de parution : ${work.release}<BR/>
        Genre : ${work.genre}<BR/>
        <!--
        Avec "mainArtist" c'est un peu plus compliqué, parce "mainArtist" est lui-même un Objet au format Java Beans,
          on pourra utiliser l'ajout de ".name" pour accéder à "getName"
        -->
        
        Artiste : ${work.mainArtist.name}<BR/>
        Résumé : ${work.summary}<BR/>
        
        <!--
        On va ajouter un petit formulaire contenant un bouton,
        l'"action" de ce formulaire c'set l'URL de la Servlet qui va recevoir l'information,
        la "method" de soumission c'est "POST" parqu'il s'agit d'ajouter quelque chose au caddie.
        -->
        <form action="addToCart" method="POST">
        <!--
        Au milieu on va ajouter un champ de type "hidden", le paramètre qui va être envoyé à la Servlet c'est "identifiant"
        Et la valeur, on la connaît, elle sera en dur. Il va s'agir de l'identifiant de l'oeuvre dont l'on affiche le descriptif.
        Pour la "value" l'intervennat a fait une concaténation, afin d'ajouter la valeur de "id" de l'oeuvre concernée 
        Et il reste ce "input" caché qui permet d'ajouter l'oeuvre au Caddie.
        -->
        <input type="hidden" name="identifiant" value="${work.id}">
        <!-- 
        Il faut également ajouter un bouton de soumission de ce formulaire, la "value" sera le petit libellé sur le bouton 
        -->
        <input type="submit" value="Ajouter au caddie">

        </form>
    </body>
</html>
