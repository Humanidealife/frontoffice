/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.directmedia.onlinestore.frontoffice.controller;

import com.directmedia.onlinestore.core.entity.Catalogue;
import com.directmedia.onlinestore.core.entity.ShoppingCart;
import com.directmedia.onlinestore.core.entity.Work;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 *
 * @author wangq
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/addToCart"})
public class AddToCartServlet extends HttpServlet {


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //La première chose que cette Servlet va faire, c'est de récupérer "identifiant" passé en paramètre.
        //  Comme d'habitude, avec "request.getParameter()", on va recevoir une String, même cet "identifiant" est un "long"
        String idAsString = request.getParameter("identifiant");
        //On va tout de suite le convertir en "long"
        long idAsLong = Long.parseLong(idAsString);
        
        //Maintenant on s'occupe du caddie. Ce caddie se trouve en Session.
        // On va d'abord récupérer la Session en utilisant "request.getSession()"
        //  Soit la Session existe déjà et on récupère la Session courante
        //  Soit la Session n'existe pas encore et en utilisant "request.getSession()" on va créer une nouvelle Session
        //  De cette Session, on va essayer de récupérer le caddie utilisateur avec "getAttribute".
        //  Admettons que ce caddie soit stocké dans la Session sous la clé "cart".
        //  Nous seul savons que ce qui se trouve sous la clé "cart" (en Session) est un "ShoppingCart",
        //  nous devons donc caster(pour récupérer) ce résultat "ShoppingCart",
        //  et nous allons stocker ce résultat dans une variable de type "ShoppingCart"
        ShoppingCart cart = (ShoppingCart)request.getSession().getAttribute("cart");
        
        //Si c'est la première fois que l'utilisateur rentre dans cette Servlet, 
        //  ici la variable "cart" vaut "null", il va falloir instancier le "ShoppingCart"
        if (cart==null){
            cart = new ShoppingCart();
            //Puis mettre le "ShoppingCart" en Session
            request.getSession().setAttribute("cart", cart);
            //Maintenant notre "ShoppingCart" est en Session, on pourrait le récupérer dans les requêtse suivantes.
        }
        
        //Maintenant on va essayer de retrouer notre oeuvre dans le Catalogue.
        //On peut le faire avec 2 manières
        
        /*
        //Voici la manière traditionnelles avant Java 8, c'est-à-dire avant l'apparition de l'API "stream"
        
        //On va d'abord itérer sur le Catalogue
        for (Work work : Catalogue.listOfWorks){
            //On va donc vérifier si l'un des éléments dispose d'un "id" qui est équivalent à celui qui est reçu en paramètre du formulaire
            if (work.getId()==idAsLong){
                //Si c'est le cas, cela veut dire que l'on a trouvé l'oeuvre qu'il s'agit d'ajouter au caddie d'achat.
                //  On va donc ajouter dans le "cart" cette oeuvre("work") que l'on vient de trouver
                cart.getItems().add(work);
            }
        }
        */
        
        //La version avec l'API "stream"
        //On va extraire un "stream" de la Collection "listOfWorks", à partir cela on va filtrer cette Collection(avec la méthode "filter") pour 
        //  en conserver que les oeuvres qui disposent d'un "id" égale à celui reçu en paramètre. Il ne devrait y en avoir qu'une seule normalement.
        //On va utiliser la méthode "findAny()", cette méthode va nous renvoyer un "Optional<Work>"(une oeuvre optionnelle), le mot "optionnel" car potentillement
        //  ce filtre peut ne rien retourner. 
        //Il faut importer "Optional" 
        Optional<Work> optionalWork = Catalogue.listOfWorks.stream().filter(work -> work.getId()==idAsLong).findAny();
        //Et si le filtre retourne quelqu chose, on va donc utiliser la méthode "isPresent()", dans ce cas, on va pouvoir ajouter au caddie l'élément filtré
        if (optionalWork.isPresent()){
            cart.getItems().add(optionalWork.get());
        }
        
        //Maintenant on a retrouver notre oeuvre et on l'a ajoutée au caddie d'achat, on va retourner un messsage à l'utilisateur
        PrintWriter out = response.getWriter();
        //On va afficher un de HTML, entre () on va affichier la taille du caddie
        //On ajoute enfin un lien pour retourner au Catalogue
        out.print("<html><body>L'oeuvre choisie est bien ajoutée au cadddie("+ cart.getItems().size() +")<br><a href=\"catalogue\">Retour au catalogue</a></body></html>");
        
    }

}
