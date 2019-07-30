/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.estg.dei.es.minesfinder;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Rui
 */
public class TabelaRecordes implements Serializable{
    
    private String nome;
    private long tempo;
    
    private transient ArrayList<TabelaRecordesListener> listeners;
    
    public TabelaRecordes(){
        this.nome = "Anonimo";
        this.tempo = 9999999;
        listeners = new ArrayList<>();
    }

    public void addTabelaRecordesListener(TabelaRecordesListener list){
        if(listeners == null){
            listeners = new ArrayList<>();
        }
        listeners.add(list);
    }
    
    public void removeTabelaRecordesListener(TabelaRecordesListener lis){
        if(listeners != null){
            listeners.remove(lis);
        }
    }
    
    public void notifyRecordesAtualizados(){
        if(listeners != null){
            for(TabelaRecordesListener list:listeners){
                list.recordesAtualizados(this);
            }
        }
    }
    
    public void setRecord(String name, long time){
        nome=name;
        tempo=time;
        notifyRecordesAtualizados();
    }
    
    public String getNome() {
        return nome;
    }

    public long getTempo() {
        return tempo;
    }
      
}
