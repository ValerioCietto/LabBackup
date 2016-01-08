/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *
 * @author valerio
 */
public class Utente{
    String nome="";
    String pwd="";
    String ruolo="";
    public Utente(String iNome,String iPwd, String iRuolo){
        nome=iNome;
        pwd=iPwd;
        ruolo=iRuolo;
    }
    public Utente(String []input){
        if(input==null)
            return;
        nome=input[0];
        pwd=input[1];
        ruolo=input[2];
    }
    public String getNome(){
        return nome;
    }
    public String getPwd(){
        return pwd;
    }
    public String getRuolo(){
        return ruolo;
    }
}
