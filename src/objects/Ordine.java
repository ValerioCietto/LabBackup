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

public class Ordine extends Pizza{
    int quantita=1;   
    public Ordine(String iNome,String iIngredienti, double iPrezzo){
        super(iNome, iIngredienti, iPrezzo);
    }
    public Ordine(Pizza p, int numero){
        super(p.getNome(), p.ingredienti, p.prezzo);
        this.quantita=numero;
    }
    public void setQuantita(int iQuantita){
        quantita=iQuantita;
    }
    public int getQuantita(){
        return quantita;
    }
}
