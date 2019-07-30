/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipleiria.estg.dei.es.minesfinder;

import java.awt.List;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Rui
 */
public class CampoMinado {
    
    private boolean minas[][];
    private int estado[][];
    private int largura;
    private int altura;
    private int numMinas;
    private Random gerador = new Random();
    private boolean primeiraJogada;
    private boolean jogoTerminado;
    private boolean jogadorDerrotado;
    private long inicioDeJogo;
    private long duracaoDeJogo;
    
    public static final int VAZIO = 0;
    public static final int TAPADO = 9;
    public static final int DUVIDA = 10;
    public static final int MARCADO = 11;
    public static final int REBENTADO = 12;
    
    public CampoMinado(int largura, int altura, int numMinas){
        this.largura = largura;
        this.altura = altura;
        this.numMinas = numMinas;
        this.minas = new boolean[largura][altura]; // PUS THIS.MINAS EM VEZ DE MINAS
        estado = new int[largura][altura];
        
        this.primeiraJogada = true;
        this.jogadorDerrotado = false;
        this.jogoTerminado = false;
        this.duracaoDeJogo=0;
        this.inicioDeJogo=0;
        
        for(int x=0;x<largura;x++){
            for(int y=0;y<altura;y++){
                estado[x][y] = TAPADO;
            }
        }
    }

    public boolean hasMinas(int x, int y) {
        return minas[x][y];
    }

    public int getEstado(int x, int y) {
        return estado[x][y];
    }

    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
    
    public boolean isJogoTerminado() {
        return jogoTerminado;
    }

    public boolean isJogadorDerrotado() {
        return jogadorDerrotado;
    }
    
    private void colocarMinas(int exceptX, int exceptY){
        for(int i=0;i<numMinas;i++){
            int x=0;
            int y=0;
            do{
                x=gerador.nextInt(largura);
                y=gerador.nextInt(altura);
            }while(minas[x][y]  || (x==exceptX && y==exceptY));
            minas[x][y] = true;
        }
    }
    
    private int contarMinas(int x, int y){
        int resultado = 0;
        for(int coluna=Math.max(0, x-1); coluna<Math.min(largura, x+2); coluna++){
            for(int linha=Math.max(0, y-1); linha<Math.min(altura, y+2); linha++){
                if(minas[coluna][linha]){
                    resultado++;
                }
            }
        }
        return resultado-(minas[x][y]?1:0);
    }
    
    private void revelarQuadriculasVizinhas(int x, int y){
        //IF YOU FIND A NEIGHBOURING FIELD NEXT TO A BOMB, STOP SEARCHING
        //IF A NEIGHBOURING FIELD HAS NO BOMB, KEEP EXPANDING THE NEW FIELDS NEIGHBOURS
        for(int i=0;i<largura;i++){
            for(int j=0;j<altura;j++){
                if(estado[i][j] == TAPADO && contarMinas(x, y) == 0){
                    if(x-1 >= 0){
                        revelarQuadricula(x-1, y);
                    }
                    if(x+1 < largura){
                        revelarQuadricula(x+1, y);
                    }
                    if(y-1 >= 0){
                        revelarQuadricula(x, y-1);
                    }
                    if(y+1 < altura){
                        revelarQuadricula(x, y+1);
                    } 
                    if(x-1 >= 0 && x+1 < largura && y-1 >= 0 && y+1 < altura){
                        revelarQuadricula(x-1, y-1);
                        revelarQuadricula(x-1, y+1);
                        revelarQuadricula(x+1, y-1);
                        revelarQuadricula(x+1, y+1);
                    }
                }
            }
        }
    }
    
    private boolean verificarVitoria(){
        boolean vitoria = true;
        for(int x=0;x<largura;x++){
            for(int y=0;y<altura;y++){
                if(!minas[x][y]){
                    vitoria = vitoria && estado[x][y] >= 0 && estado[x][y] < 9;
                }
            }
        }
        return vitoria;
    }
    
    public void revelarQuadricula(int x, int y){
        if(estado[x][y] == TAPADO && !jogoTerminado){
           if(primeiraJogada){
               primeiraJogada = false;
               colocarMinas(x,y);
               inicioDeJogo = System.currentTimeMillis();
               revelarQuadriculasVizinhas(x, y);
               if(contarMinas(x,y) == 0){
                    estado[x][y] = VAZIO;
                }else{
                    estado[x][y] = contarMinas(x,y);
                }
           }else{
               if(minas[x][y]){
                   estado[x][y] = REBENTADO;
                   jogoTerminado = true;
                   jogadorDerrotado = true;
                   duracaoDeJogo = System.currentTimeMillis() - inicioDeJogo;
               }else{
                   if(contarMinas(x,y) == 0){
                       estado[x][y] = VAZIO;
                       revelarQuadriculasVizinhas(x,y);
                   }else{
                       estado[x][y] = contarMinas(x,y);
                   }
                   if(verificarVitoria()){
                       jogoTerminado = true;
                       duracaoDeJogo = System.currentTimeMillis() - inicioDeJogo;
                   }
               }
               
           }
        }
    }
    
    public void marcarComoTendoMina(int x, int y){
        if(estado[x][y] == TAPADO || estado[x][y] == DUVIDA){
            estado[x][y] = MARCADO;
        }
    }
    public void marcarComoSuspeita(int x, int y){
        if(estado[x][y] == TAPADO || estado[x][y] == MARCADO){
            estado[x][y] = DUVIDA;
        }
    }
    public void desmarcar(int x, int y){
        if(estado[x][y] == DUVIDA || estado[x][y] == MARCADO){
            estado[x][y] = TAPADO;
        }
    }

    public long getDuracaoDeJogo() {
        if(primeiraJogada){
            return 0;
        }
        if(!jogoTerminado){
            return System.currentTimeMillis() - inicioDeJogo;
        }
        return duracaoDeJogo;
    }

    public int getNumMinas() {
        return numMinas;
    }
    
}

