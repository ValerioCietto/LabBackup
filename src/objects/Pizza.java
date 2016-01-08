/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import mypackage.DBManager;

/**
 *
 * @author valerio
 */
public class Pizza{
    String nome;
    String ingredienti;
    double prezzo;
    public Pizza(String iNome,String iIngredienti,double iPrezzo){
        if(iNome!=null && !iNome.equals("")){
            if(iIngredienti!=null){
                if (iPrezzo>0){
                    ingredienti=iIngredienti;
                    nome=iNome;
                    prezzo=iPrezzo;
                }}}
    }
    public Pizza(String iNome,String iIngredienti,String iPrezzo){
    try{
            double tempPrezzo= Double.parseDouble(iPrezzo);
            if(iNome!=null && !iNome.equals("")){
            if(iIngredienti!=null){
                if (tempPrezzo>0){
                    ingredienti=iIngredienti;
                    nome=iNome;
                    prezzo=tempPrezzo;
                }}}
                  
       }catch(NumberFormatException e){
                    System.out.println(e);
                }
    }

    public Pizza(String margherita, String pom_mozz, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public double getPrezzo(){
        return prezzo;
    }
    public String getNome(){
    return nome;
    }
    public void setPizza(String nIngredienti,double nPrezzo){
        String oNome=this.nome;
        DBManager.modPizza(oNome, nIngredienti, nPrezzo);

    }
}
