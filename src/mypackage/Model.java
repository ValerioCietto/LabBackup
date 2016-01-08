package mypackage;

import java.util.ArrayList;
import javax.servlet.http.*;
import objects.Ordine;
import objects.Pizza;
import objects.Prenotazione;
import objects.Utente;

public class Model {
    public static void login(HttpServletRequest req){
       
        HttpSession s=req.getSession();
        String name = req.getParameter("login");
        String password = req.getParameter("password");
        if(name!=null && password!=null && !name.equals("") && !password.equals("")){
            Utente login = new Utente( DBManager.getLogin(name,password) );
            if(!login.getNome().equals("")){
                s.setAttribute("username",login.getNome());
                s.setAttribute("ruolo", login.getRuolo());
                s.setAttribute("message","login effettuato, benvenuto!");
            }else
                s.setAttribute("message","login errato, sicuro di esserti registrato?");
        }else
            s.setAttribute("message","inserisci il tuo nome utente e la tua password.");
    }
    public static void logout(HttpServletRequest req){
        HttpSession s=req.getSession();
        s.invalidate();
        s=req.getSession();
        s.setAttribute("message", "logout effettuato");
    }
    
    public static void addPizza(HttpServletRequest req){
        HttpSession s=req.getSession();
        Pizza temp=new Pizza(req.getParameter("pizza"),req.getParameter("ingredienti"),req.getParameter("prezzo"));
        if (temp.getNome()!=null){
            String n= temp.getNome();
            String i= req.getParameter("ingredienti");
            Double p= temp.getPrezzo();
            if(DBManager.addPizza(n, i, p))
               s.setAttribute("message","aggiunta pizza "+n);
            else
               s.setAttribute("message","Problema sql");
        }else
            s.setAttribute("message","inserisci un nome, gli ingredienti e il prezzo.");
    } 

    static void modPizza(HttpServletRequest req) {
        HttpSession s=req.getSession();
        Pizza temp=new Pizza(req.getParameter("pizza"),req.getParameter("ingredienti"),req.getParameter("prezzo"));
        if (temp.getNome()!=null){
            String n= temp.getNome();
            String i= req.getParameter("ingredienti");
            Double p= temp.getPrezzo();
            if(DBManager.modPizza(n, i, p))
               s.setAttribute("message","modificata pizza "+n);
            else
               s.setAttribute("message","Problema sql modPizza");
        }else
            s.setAttribute("message","inserisci un nome, gli ingredienti e il prezzo.");
    }
    public static void remPizza(HttpServletRequest req){
        String pizza= req.getParameter("pizza");
        if(!DBManager.remPizza(pizza))
            (req.getSession()).setAttribute("message","Problema sql");
    }
    static void addUser(HttpServletRequest req) {
        HttpSession s=req.getSession();
        Utente temp = new Utente(req.getParameter("username"), req.getParameter("password"),"user");
    }
    static void modUser(HttpServletRequest req) {
        HttpSession s=req.getSession();
        Utente temp = new Utente(req.getParameter("username"), req.getParameter("password"),"user");
    }
    static void remUser(HttpServletRequest req) {
        HttpSession s=req.getSession();
        Utente temp = new Utente(req.getParameter("username"), req.getParameter("password"),"user");
    }
    static void addPren(HttpServletRequest req) {
        System.out.println("addPren");
        System.out.println();
        String p=req.getParameter("pizza");
        String clName=req.getParameter("login");
        Utente temp = new Utente(req.getParameter("username"), req.getParameter("password"),"user");
        Pizza pizza = new Pizza("margherita", "pom, mozz", 4);
        Ordine ordine = new Ordine(pizza,2);
        ArrayList<Ordine> ordini= new ArrayList<Ordine>(1);
        ordini.add(ordine);
        Prenotazione prenotazione = new Prenotazione(temp,ordini,"accettato");
        int i=0;
        DBManager.addPrenotazione(clName, p, i, "2038-01-19 03:14:07");
    }
    static void modPren(HttpServletRequest req) {
        //HttpSession s=req.getSession();
        String p=req.getParameter("pizza");
        String clName=req.getParameter("login");
        int i=0;
        DBManager.addPrenotazione(clName, p, i, "2038-01-19 03:14:07");
    }
    static void remPren(HttpServletRequest req) {
        //HttpSession s=req.getSession();
        String p=req.getParameter("pizza");
        String clName=req.getParameter("login");
        int i=0;
        DBManager.addPrenotazione(clName, p, i, "2038-01-19 03:14:07");
    }
}

    
