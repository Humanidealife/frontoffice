/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.directmedia.onlinestore.frontoffice.controller;

import com.directmedia.onlinestore.core.entity.Catalogue;
import com.directmedia.onlinestore.core.entity.Work;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author wangq
 */
@WebServlet(name = "WorkDetailsServlet", urlPatterns = {"/work-details"})
public class WorkDetailsServlet extends HttpServlet {

    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //On va récupérer ici un paramètre le "id"
        String id = request.getParameter("id");
        //Il faut que l'on recherche dans le catalogue quel film possède cet "id"?
        
        //Avec Java 8, On peut écrire cette recherche à la syntaxe de l'API "Stream"
        //On doit importer les Class "Work" et "Catalogue" du module "core, on va également filtrer le flux de "stream" par
        //  l'identifiant de ces oeuvres. 
        //On va prendre le premier élément filtré
        //Attention, il ne faut pas utilser deux fois le même nom de varaible dans "Work work" et "filter(w -> w.getId)"
        //Cette ligne de code nous permet d'obtenir l'oeuvre du catalogue qui a le même "id" que 
        //  l'"id" reçu en paramètre dans la requête. Mais cette syntaxe peut générer de multiples exceptions, 
        //  notamment si cet "id" n'existe pas dans le catalogue. 
        //  On va faire l'abstraction de cette ligne de code pour l'instant
        
        /*Work work = Catalogue.listOfWorks.stream().filter(w -> w.getId()== Long.parseLong(id)).findFirst().get();*/
        
        //Si l'on veut obtenir le même résultat sasn l'API "stream", on va faire ainsi 
        //On va d'abod déclarer une variable "work" pour commencer
        Work work = null;
        //On va itérer sur la liste des oeuvres du catalogue, 
        for (Work nextWork : Catalogue.listofWorks){
            //puis on va faire un "if"
            if (nextWork.getId()== Long.parseLong(id)){
                //Cela signifie que l'on trouve l'oeuvre recherchée
                work = nextWork;
                //On va sortir de la boucle
                break;
            }
        }
        //On va bien sûr faire en sorte que dans le catalogue le lien cliquable fournit cet "id", on va faire cela ultérieurement
        
        PrintWriter out = response.getWriter();
        //Le titre sera 
        out.print("<html><body><h1>Descriptif de l'oeuvre</h1>");
        
        //On va avoir 5 propriétés à afficher
        out.print("Titre : <BR/>");
        out.print("Année de parution : <BR/>");
        out.print("Genre : <BR/>");
        out.print("Artiste : <BR/>");
        out.print("Résumé : <BR/>");
        out.print("</body></html>");
    }


}
