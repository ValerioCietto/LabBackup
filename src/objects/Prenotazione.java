/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

import java.util.ArrayList;

/**
 *
 * @author valerio
 */

public class Prenotazione{
    static int id;
    Utente cliente;
    ArrayList <Ordine> prenotaz;
    String stato;
    public Prenotazione(Utente iCliente, ArrayList <Ordine> iPrenotaz, String iStato){
        id+=1;
        cliente=iCliente;
        for(int i=0;i<iPrenotaz.size();i++)
            prenotaz.add(iPrenotaz.get(i));
        stato=iStato;
    }
    public int getId(){
        return id;
    }
    public Utente getUtente(){
        return cliente;
    }
    public String getStato(){
        return stato;
    }
    public Ordine getOrdine(int i){
        return prenotaz.get(i);
    }
}


