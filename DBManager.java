package components;

import java.sql.*;

/**
 * Classe dell'oggetto Pizza
 *
 * @author Valerio Cietto
 */
////////////////////////////////////////////////////////////////////////////////
public final class DBManager {

////////////////////////////////////////////////////////////////////////////////
// CREAZIONE DATABASE
    public static void main(String[] args) {
        try {
            Connection conn = openConnection();
            Statement st = openStatement(conn);
            try {
                creaTabelle(st);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                creaDati(st);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            closeStatement(st);
            closeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera il database creando tre tabelle: UTENTI, PIZZE e PRENOTAZIONI;
     *
     * @param st indica lo Statement creato per l'interazione con il database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static void creaTabelle(Statement st) throws SQLException {
        st.execute("CREATE TABLE UTENTI("
                + "IDUSER         INT         NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
                + "USERNAME       VARCHAR(33) NOT NULL UNIQUE,"
                + "PASSWORD       VARCHAR(33) NOT NULL,"
                + "PERMISSION     VARCHAR(10) NOT NULL,"
                + "PRIMARY KEY(IDUSER))");

        st.execute("CREATE TABLE PIZZE("
                + "IDPIZZA        INT         NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
                + "NOME           VARCHAR(33) NOT NULL UNIQUE,"
                + "INGREDIENTI    VARCHAR(99) NOT NULL,"
                + "PREZZO         DOUBLE      NOT NULL,"
                + "PRIMARY KEY(IDPIZZA))");

        st.execute("CREATE TABLE PRENOTAZIONI("
                + "IDPRENOTAZIONE INT         NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),"
                + "IDUTENTE       INT         NOT NULL    ,"
                + "IDPIZZA        INT         NOT NULL    ,"
                + "QUANTITA       INT         NOT NULL    ,"
                + "DATA           VARCHAR(16) NOT NULL    ,"
                + "STATO          VARCHAR(10) NOT NULL    ,"
                + "PRIMARY KEY(IDPRENOTAZIONE, IDUTENTE, IDPIZZA),"
                + "FOREIGN KEY(IDUTENTE) REFERENCES UTENTI(IDUSER) ON DELETE CASCADE,"
                + "FOREIGN KEY(IDPIZZA) REFERENCES PIZZE(IDPIZZA) ON DELETE CASCADE)");
    }

    /**
     * Inizializza il database con 4 utenti e 4 pizze;
     *
     * @param st indica lo Statement creato per l'interazione con il database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static void creaDati(Statement st) throws SQLException {
        addUser("admin", "Pizza1", "admin", st);
        addUser("user", "Pizza1", "user", st);
        addPizza("Margherita", "pomodoro, mozzarella, basilico", 5.00, st);
        addPizza("4 Formaggi", "pomodoro, mozzarella, fontina, gorgonzola, emmenthal", 8.00, st);
        addPizza("Wurstel", "pomodoro, mozzarella, wurstel", 6.00, st);
        addPizza("Prosciutto e funghi", "pomodoro, mozzarella, prosciutto, funghi", 7.00, st);
    }

////////////////////////////////////////////////////////////////////////////////      
// UTILITY DATABASE
    /**
     * Controlla se esiste un utente con quell'username e password;
     *
     * @param usr indica lo username dell'utente da controllare;
     * @param pwd indica la password dell'utente da controllare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return ,se esiste, l'id dell'utente; altrimenti -1;
     */
    public static int checkLogin(String usr, String pwd, Statement st) {
        try {
            ResultSet rs = st.executeQuery("SELECT IDUSER FROM UTENTI WHERE USERNAME='" + usr + "' AND PASSWORD ='" + pwd + "'");
            rs.next();
            int id = rs.getInt("IDUSER");
            return id;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Controlla se esiste il database;
     *
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se il database esiste, altrimenti "false";
     */
    public static boolean checkDatabase(Statement st) {
        try {
            return esegui("select * from UTENTI", st) && esegui("select * from PIZZE", st) && esegui("select * from PRENOTAZIONI", st);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Elimina tutte le tabelle del database;
     *
     * @param st indica lo Statement creato per l'interazione con il database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static void drop(Statement st) throws SQLException {
        if (checkDatabase(st)) {
            esegui("DROP TABLE PRENOTAZIONI", st);
            esegui("DROP TABLE PIZZE", st);
            esegui("DROP TABLE UTENTI", st);
        }
    }

    /**
     * Esegue una query SQL ritornando un booleano;
     *
     * @param sql indica il testo di una query SQL;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la query ha avuto successo;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean esegui(String sql, Statement st) throws SQLException {
        boolean tmp;
        tmp = st.execute(sql);
        return tmp;
    }

    /**
     * Esegue una query SQL ritornando un ResultSet;
     *
     * @param sql indica il testo di una query SQL;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return il resultSet della query eseguita;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet query(String sql, Statement st) throws SQLException {
        try {
            return st.executeQuery(sql);
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * crea uno Statement con il database;
     *
     * @param conn indica una connessione disponibile per creare uno Statement;
     * @return uno Statement aperto;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static Statement openStatement(Connection conn) throws SQLException {
        Statement st = conn.createStatement();
        return st;

    }

    /**
     * Apre una connessione con il database;
     *
     * @return una Connessione aperta;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static Connection openConnection() throws SQLException {
        String ur = "jdbc:derby://localhost:1527/PizzaWeb";
        String us = "pizzeria";
        String p = "pizzeria";
        Connection conn = DriverManager.getConnection(ur, us, p);
        return conn;
    }

    /**
     * Chiude uno Statement precedentemente creato;
     *
     * @param st indica lo Statement da chiudere;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static void closeStatement(Statement st) throws SQLException {
        st.close();
    }

    /**
     * Chiude la Connection precedentemente creata;
     *
     * @param conn indica la Connessione da chiudere;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }

////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
////////////////////////////////////////////////////////////////////////////////      
// METODI DI INSERIMENTO
    /**
     * Inserisce un utente nella tabella UTENTI;
     *
     * @param nome indica il nome dell'utente da aggiungere al database;
     * @param password indica la password dell'utente da aggiungere nel
     * database;
     * @param ruolo indica i permessi dell'utente;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id dell'utente aggiunto al database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static int addUser(String nome, String password, String ruolo, Statement st) throws SQLException {
        int id;
        esegui("INSERT INTO UTENTI (USERNAME, PASSWORD, PERMISSION) VALUES ('" + nome + "', '" + password + "', '" + ruolo + "')", st);
        ResultSet rs = st.executeQuery("SELECT IDUSER FROM UTENTI WHERE USERNAME='" + nome + "'");
        rs.next();
        id = rs.getInt("IDUSER");
        return id;
    }

    /**
     * Aggiunge una pizza nella tabella PIZZE del database;
     *
     * @param nome indica il nome della pizza da aggiungere al database;
     * @param ingr indica gli ingredienti della pizza da aggiungere al database;
     * @param prezzo il prezzo della pizza;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id della pizza aggiunta al database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static int addPizza(String nome, String ingr, double prezzo, Statement st) throws SQLException {
        int id;
        esegui("INSERT INTO PIZZE (NOME, INGREDIENTI, PREZZO) VALUES ('" + nome + "', '" + ingr + "', " + prezzo + ")", st);
        ResultSet rs = st.executeQuery("SELECT IDPIZZA FROM PIZZE WHERE NOME='" + nome + "'");
        rs.next();
        id = rs.getInt("IDPIZZA");
        return id;
    }

    /**
     * Aggiunge una prenotazione ad un cliente
     *
     * @param cliente indica l'id dell'utente che effettua la prenotazione;
     * @param pizza indica l'id della pizza prenotata;
     * @param quantita indica la quantità di pizze prenotate;
     * @param data indica la data della prenotazione;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id della prenotazione aggiunta al database;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static int addPrenotazione(int cliente, int pizza, int quantita, String data, Statement st) throws SQLException {
        int id;
        esegui("INSERT INTO PRENOTAZIONI(IDUTENTE,IDPIZZA,QUANTITA,DATA,STATO) VALUES (" + cliente + ", " + pizza + ", " + quantita + ", '" + data + "', 'Ordinato')", st);
        ResultSet rs = st.executeQuery("SELECT IDPRENOTAZIONE FROM PRENOTAZIONI WHERE IDUTENTE=" + cliente + " AND IDPIZZA=" + pizza + " AND DATA ='" + data + "'");
        rs.next();
        id = rs.getInt("IDPRENOTAZIONE");
        return id;
    }

////////////////////////////////////////////////////////////////////////////////      
// METODI DI RIMOZIONE
    /**
     * Elimina un utente nella tabella UTENTI;
     *
     * @param id indica l'id dell'utente da rimuovere dal database;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se l'utente viene eliminato, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean remUser(int id, Statement st) throws SQLException {
        return esegui("DELETE FROM UTENTI WHERE (IDUSER=" + id + ")", st);
    }

    /**
     * Rimuove una pizza dalla tabella PIZZE;
     *
     * @param id indica l'id della pizza da rimuovere dal database;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la pizza viene eliminata, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean remPizza(int id, Statement st) throws SQLException {
        return esegui("DELETE FROM PIZZE WHERE (IDPIZZA=" + id + ")", st);
    }

    /**
     * Rimuove una prenotazione dalla tabella PRENOTAZIONI;
     *
     * @param idP indica l'id della prenotazione da rimuovere dal database;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la prenotazione viene rimossa, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean remPrenotazione(int idP, Statement st) throws SQLException {
        return esegui("DELETE FROM PRENOTAZIONI WHERE (IDPRENOTAZIONE=" + idP + ")", st);
    }

////////////////////////////////////////////////////////////////////////////////      
// METODI DI MODIFICA
    /**
     * Modifica un utente nella tabella UTENTI;
     *
     * @param nome indica il nome attuale dell'utente;
     * @param nNome indica il nuovo nome dell'utente;
     * @param nPassword indica nuova password dell'utente;
     * @param nRuolo indica i nuovi permessi dell'utente;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se l'utente viene modificato, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean modUser(String nome, String nNome, String nPassword, String nRuolo, Statement st) throws SQLException {
        return esegui("UPDATE UTENTI SET USERNAME ='" + nNome + "', PASSWORD='" + nPassword + "', PERMISSION ='" + nRuolo + "' WHERE USERNAME = '" + nome + "'", st);
    }

    /**
     * Modifica una pizza nella tabella PIZZE;
     *
     * @param nome indica il nome della pizza da modificare;
     * @param nIngr indica i nuovi ingredienti della pizza;
     * @param nPrezzo indica il nuovo prezzo della pizza;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la pizza viene modificata, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean modPizza(String nome, String nIngr, double nPrezzo, Statement st) throws SQLException {
        return esegui("UPDATE PIZZE SET INGREDIENTI='" + nIngr + "', PREZZO=" + nPrezzo + " WHERE NOME ='" + nome + "'", st);
    }

    /**
     * Modifica una prenotazione nella tabella PRENOTAZIONI;
     *
     * @param idPrenotazione indica l'id della prenotazione da modificare;
     * @param idUtente indica l'id del nuovo utente;
     * @param idPizza indica l'id della nuova pizza;
     * @param quantita indica la nuova quantità di pizze prenotate;
     * @param data indica la nuova data di prenotazione;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la prenotazione viene modificata, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean modPrenotazione(int idPrenotazione, int idUtente, int idPizza, int quantita, String data, Statement st) throws SQLException {
        return esegui("UPDATE PRENOTAZIONI SET IDUTENTE=" + idUtente + ", IDPIZZA=" + idPizza + ", QUANTITA=" + quantita + ", DATA='" + data + "' WHERE IDPRENOTAZIONE =" + idPrenotazione, st);
    }

    /**
     * Modifica lo stato di una prenotazione nella tabella PRENOTAZIONI;
     *
     * @param idPrenotazione indica l'id della prenotazione da modificare;
     * @param stato indica il nuovo stato di gestione della prenotazione;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return "true" se la prenotazione viene modificata, altrimenti "false";
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static boolean modStatoPrenotazione(int idPrenotazione, String stato, Statement st) throws SQLException {
        return esegui("UPDATE PRENOTAZIONI SET STATO='" + stato + "' WHERE IDPRENOTAZIONE =" + idPrenotazione, st);
    }

////////////////////////////////////////////////////////////////////////////////      
// METODI DI GET ID  
    /**
     * Prende in input il nome utente e password e restituisce l'ID dell'utente;
     *
     * @param username indica il nome utente associato all'id da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id dell'utente;
     */
    public static int getIdUser(String username, Statement st) {
        try {
            ResultSet rs = st.executeQuery("SELECT IDUSER FROM UTENTI WHERE USERNAME='" + username + "'");
            rs.next();
            int id = rs.getInt("IDUSER");
            return id;
        } catch (SQLException e) {
            return -1;
        }

    }

    /**
     * Prende in input il nome della pizza e restituisce l'ID della pizza;
     *
     * @param nome indica il nome della pizza associato all'id da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id della pizza;
     */
    public static int getIdPizza(String nome, Statement st) {
        try {
            ResultSet rs = st.executeQuery("SELECT IDPIZZA FROM PIZZE WHERE NOME='" + nome + "'");
            rs.next();
            int id = rs.getInt("IDPIZZA");
            return id;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * Prende in input l'ID utente, l'ID pizza e la data e restituisce l'ID
     * della prenotazione associata;
     *
     * @param username indica l'id dell'utente associato all'id della
     * prenotazione da recuperare;
     * @param pizza indica l'id della pizza associata all'id della prenotazione
     * da recuperare;
     * @param data indica la data associata all'id della prenotazione da
     * recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return l'id della prenotazione;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static int getIdPrenotazione(int username, int pizza, String data, Statement st) throws SQLException {
        try (ResultSet rs = st.executeQuery("SELECT IDPRENOTAZIONE FROM PRENOTAZIONI WHERE IDUTENTE=" + username + " AND IDPIZZA =" + pizza + " AND DATA='" + data + "'")) {
            rs.next();
            int id = rs.getInt(1);
            return id;
        } catch (SQLException e) {
            return -1;
        }
    }

////////////////////////////////////////////////////////////////////////////////      
// METODI DI GET
    /**
     * Ritorna ResultSet contenente un utente;
     *
     * @param usr indica il nome dell'utente da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return un ResultSet contenente i dati di un Utente;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet getUser(String usr, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM UTENTI WHERE USERNAME='" + usr + "'");
        return rs;
    }

    /**
     * Ritorna ResultSet contenente un utente;
     *
     * @param id indica l'id dell'utente da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return un ResultSet contenente i dati di un Utente;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet getUser(int id, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM UTENTI WHERE IDUSER=" + id);
        return rs;
    }

    /**
     * Ritorna un ResultSet contenente una pizza;
     *
     * @param nome indica il nome della pizza da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return un ResultSet contenente i dati di una Pizza;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet getPizza(String nome, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM PIZZE WHERE NOME='" + nome + "'");
        return rs;
    }

    /**
     * Ritorna un ResultSet contenente una pizza;
     *
     * @param id indica l'id della pizza da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return un ResultSet contenente i dati di una Pizza;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet getPizza(int id, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM PIZZE WHERE IDPIZZA=" + id);
        return rs;
    }

    /**
     * Ritorna un ResultSet contenente una prenotazione;
     *
     * @param id indica l'id della prenotazione da recuperare;
     * @param st indica lo Statement creato per l'interazione con il database;
     * @return un ResultSet contenente i dati di una Prenotazione;
     * @throws java.sql.SQLException in caso di malfunzionamento del database;
     */
    public static ResultSet getPrenotazione(int id, Statement st) throws SQLException {
        ResultSet rs = st.executeQuery("SELECT * FROM PRENOTAZIONI WHERE IDPRENOTAZIONE=" + id);
        return rs;
    }

}
