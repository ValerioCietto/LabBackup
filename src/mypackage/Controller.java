package mypackage;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.logging.Logger;

@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Controller extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        String action= request.getParameter("action");
        Logger logger = Logger.getLogger(getClass().getName());
        if(action!=null){
            switch (action) {
                case "switch":
                    switchPage(request);
                    break;
                case "login":
                    Model.login(request);
                    break;
                case "logout":
                    Model.logout(request);
                    
                    logger.info("Controller - logged in");
                    break;
                case "addPizza":
                    Model.addPizza(request);
                    
                    logger.info("Controller - add pizza");
                    break;
                case "remPizza":
                    Model.remPizza(request);
                    logger.info("Controller - rem pizza");
                    break;
                case "modPizza":
                    Model.modPizza(request);
                    logger.info("Controller - mod pizza");
                    break;
                case "registration":
                    Model.addUser(request);
                    logger.info("Controller - add user");
                    break;
                case "addpren":
                    Model.addPren(request);
                    
                    logger.info("Controller - add pren");
                    break;
                //TODO
                case "addlogin":
                    Model.addUser(request);
                    break;
                case "remLogin":
                    Model.remUser(request);
                    break;
                case "modLogin":
                    Model.modUser(request);
                    break;    
            }
        }
        RequestDispatcher rd;
        //request e non named perchè richiediamo una jsp
        rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.include(request, response);
    }
    
    public void switchPage(HttpServletRequest req){
        String page= req.getParameter("name");
        if(page!=null)
            req.getSession().setAttribute("view",page);
        else
            req.getSession().setAttribute("view","");
        page=(String)(req.getSession()).getAttribute("view");
        if(page.equals("catalogo"))
            req.getSession().setAttribute("dati",DBManager.query("SELECT * FROM PIZZE",true));
        else if(page.equals("loginManager"))
            req.getSession().setAttribute("dati",DBManager.query("SELECT * FROM UTENTI",true));
        else if(page.equals("prenotazioni"))
            req.getSession().setAttribute("dati",DBManager.query("SELECT * FROM PRENOTAZ", true));
    }
    /*public static void remove(HttpServletRequest req){
        HttpSession s=req.getSession();
        String name = req.getParameter("pizza");
        if(name!=null && !name.equals("")){
            DBManager.remPizza(name);
            s.setAttribute("message","rimossa pizza "+name);
            return;
        }
        name = req.getParameter("login");
        if(name!=null && !name.equals("")){
            DBManager.remLogin(name);
            s.setAttribute("message","rimosso login "+name);
            return;
        }
        s.setAttribute("message","errore di input");
    }
    public static void aggiungi(HttpServletRequest req){
        HttpSession s=req.getSession();
        String name = req.getParameter("pizza");
        if(name!=null && !name.equals("")){
            String ingredienti=req.getParameter("ingredienti");
            if(ingredienti!=null){
                try{
                    int prezzo= Integer.parseInt(req.getParameter("prezzo"));
                    DBManager.addPizza(name, ingredienti, prezzo);
                    s.setAttribute("message","aggiunta pizza "+name);
                }catch(NumberFormatException e){
                    s.setAttribute("message","errore input prezzo");
                }
            }else
                s.setAttribute("message","errore input ingredienti");
            return;
        }
        s.setAttribute("message","parametro pizza non trovato");
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
