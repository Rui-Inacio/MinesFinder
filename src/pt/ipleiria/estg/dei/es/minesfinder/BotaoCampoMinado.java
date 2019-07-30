/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.estg.dei.es.minesfinder;

import java.awt.Color;
import javax.swing.JButton;

public class BotaoCampoMinado extends JButton{
    
    private int estado;
    private int linha;
    private int coluna;
    
    public BotaoCampoMinado(int x, int y){
        estado = CampoMinado.TAPADO;
        this.linha = y;
        this.coluna = x;
    }
    
    public void setEstado(int estado){
        this.estado = estado;
        
        switch(estado){
            case CampoMinado.TAPADO: 
                setText("");
                setBackground(null);
                break;
              
            case CampoMinado.VAZIO:
                setText("");
                setBackground(Color.WHITE);
                break;
                
            case CampoMinado.MARCADO:
                setText("!");
                setBackground(Color.RED);
                break;
                
            case CampoMinado.DUVIDA:
                setText("?");
                setBackground(Color.YELLOW);
                break;
                
            case CampoMinado.REBENTADO:
                setText("*");
                setBackground(Color.ORANGE);
                break;
                
            default:
                if(estado == 1){
                    setForeground(Color.BLUE);
                }else{
                    if(estado == 2){
                        setForeground(Color.GREEN);
                    }else{
                        setForeground(Color.RED);
                    }
                }
                
                setText("" + estado);
                setBackground(Color.WHITE);                
        }
        
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
    
}
