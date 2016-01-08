package components;

import java.util.ArrayList;

/**
 *
 * da mettere sotto <!Doctype...> a ogni jsp
 * <jsp:useBean id="user" scope="session" class="UserBean"/>
 *
 * la riga seguente esegue setXxx(request.getParameter(xxx)) di tutti i set
 * della classe essendoci
 * <jsp:setProperty name="user" property="*"/>
 *
 * <jsp:getProperty name="user" property="*"/>
 *
 * evitando i codici strani, per richiamare il metodo Xxx usa
 * <% user.Xxx(...) %>
 */
public class UserBean {

    private String username;
    private String password;
    private String ruolo;
    private String view;
    private ArrayList<String> message = new ArrayList<>();

    public UserBean() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setMessage(String message) {
        this.message.add(message);
    }

    public String getMessage() {
        String listString = "";
        for (String s : message) {
            listString += s;
        }
        message.clear();
        return listString;
    }

    public String getView() {
        return view;
    }
}
