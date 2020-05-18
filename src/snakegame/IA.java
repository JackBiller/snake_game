/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jack
 */
public class IA {
    public Game game;
    public Celula TabelaAberta[];
    public Celula TabelaFechada[];
    

    public Caminho[] a_star(Game game){
        this.game = game;
        int origem = this.game.snake[0];
        int campo  = this.game.campo;
        int tamanho = campo*campo;


        TabelaAberta = new Celula[tamanho];
        for (int i = 0; i < TabelaAberta.length; i++)  TabelaAberta[i] = new Celula();
        TabelaFechada = new Celula[tamanho];
        for (int i = 0; i < TabelaFechada.length; i++)  TabelaFechada[i] = new Celula();

        TabelaAberta[0] = new Celula(origem,0,0);
        
        Caminho caminho[] = retornarCaminho(1);
        Caminho caminhoFinal[] = new Caminho[campo*campo];
        for (int i = 0; i < caminhoFinal.length; i++) caminhoFinal[i] = new Caminho();
        int caminhoPais[] = new int[campo*campo];
        for (int i = 0; i < caminhoPais.length; i++) caminhoPais[i] = -1;
        int paiOld = -1, indice = 0;
        
        caminhoFinal[0] = caminho[0];
        
        for (int i = caminho.length-1; i >= 0; i--) {
            if (paiOld == -1){
                if (caminho[i].pai != 0){
                    caminhoPais[indice] = i;
                    paiOld = caminho[i].pai;
                    indice++;
                }
            } else if(caminho[i].posicao == paiOld){
                caminhoPais[indice] = i;
                paiOld = caminho[i].pai;
                indice++;
            }
        }

        boolean fisrt = true;
        indice = 0;
        for (int i = caminhoPais.length-1; i >= 0; i--) {
            if(caminhoPais[i] != -1){
                if (fisrt) {
                    fisrt = false;
                } else {
                    // System.out.println("caminho[caminhoPais[i]]: " + caminho[caminhoPais[i]].posicao);
                    indice++;
                    caminhoFinal[indice] = caminho[caminhoPais[i]];
                }
            }
        }
        
        return caminhoFinal;
    }
    
    public Caminho[] retornarCaminho(int pesoOrigem){
        int campo  = this.game.campo;
        Caminho caminho[] = new Caminho[campo*campo];
        for (int i = 0; i < caminho.length; i++) caminho[i] = new Caminho();
        game.TabelaAberta = TabelaAberta;
        game.TabelaFechada = TabelaFechada;
        caminho[0] = new Caminho(TabelaAberta[0].posicao, game.Draw(), TabelaAberta[0].pai);
        
        // int origem = this.game.snake[0];
        int destino = this.game.maca;
        
        boolean isStop = false;
        
        int possibilidades[] = new int[4];
        for (int i = 0; i < possibilidades.length; i++) possibilidades[i] = 0;
        int indice = 0;
        
        if (TabelaAberta[0].posicao - campo > 0){
            possibilidades[0] = TabelaAberta[0].posicao - campo;
        }
        
        if ((TabelaAberta[0].posicao) % campo != 1){
            possibilidades[1] = TabelaAberta[0].posicao - 1;
        }

        if ((TabelaAberta[0].posicao) % campo != 0){          
            possibilidades[2] = TabelaAberta[0].posicao + 1; 
        }

        if ((TabelaAberta[0].posicao + campo) < ((campo*campo)+1)){
           possibilidades[3] = TabelaAberta[0].posicao + campo;
        }
        
        // boolean ck_body_shock = game.ck_body_shock ? 
        for (int i = 0; i < possibilidades.length; i++) {
            if (possibilidades[i] == destino) isStop = true;
            
            if (
                possibilidades[i] != 0 && 
                !isTabelaFechada(possibilidades[i]) &&
                ( !game.ck_body_shock || !game.isNumSnake(possibilidades[i]) )
            ){
                indice++;
                TabelaAberta[indice] = new Celula(
                    possibilidades[i],
                    game.calDistance(possibilidades[i], destino) + pesoOrigem,
                    TabelaAberta[0].posicao
                    // game.calDistance(possibilidades[i], origem)
                );
            }
        }

        indice = -1;
        for (int i = 0; i < TabelaFechada.length; i++) {
            if (!TabelaFechada[i].valid) {
                indice = i;
                i = TabelaFechada.length;
            }
        }
        if(indice != -1) TabelaFechada[indice] = TabelaAberta[0];
        
        reordenarTabelaAberta();
        
        /* try {
            Thread.sleep(500);
            lbCampo.setText(game.Draw());
        } catch (InterruptedException ex) {
            Logger.getLogger(IA.class.getName()).log(Level.SEVERE, null, ex);
        } */
        
        Caminho caminhoTemp[] = new Caminho[0];
        if (!isStop) caminhoTemp = retornarCaminho(pesoOrigem+1);

        for (int i = 0; i < caminhoTemp.length; i++) {
            if (caminhoTemp[i].valid) caminho[i+1] = caminhoTemp[i];
        }

        return caminho;
    }
    
    public boolean isTabelaFechada(int posicao){
        for (int i = 0; i < TabelaFechada.length; i++) {
            if(TabelaFechada[i].valid && TabelaFechada[i].posicao == posicao){
                return true;
            }
        }
        return false;
    }
    
    public void reordenarTabelaAberta(){
        Celula tabelaAbertaTemp[] = new Celula[TabelaAberta.length];
        for (int i = 0; i < tabelaAbertaTemp.length; i++)  tabelaAbertaTemp[i] = new Celula();

        int menorCusto = -1;
        int menorPosicao = -1;
        int indiceOld[] = new int[TabelaAberta.length];
        int indiceTemp = 0;
        for (int i = 0; i < indiceOld.length; i++) indiceOld[i] = 0;

        for (int i = 1; i < TabelaAberta.length; i++) {
            if (TabelaAberta[i].valid){
                menorCusto = -1;
                for (int j = 1; j < TabelaAberta.length; j++) {
                    if (!isArray(indiceOld, j) && TabelaAberta[j].valid && (menorCusto == -1 || menorCusto > TabelaAberta[j].peso)){
                        menorCusto = TabelaAberta[j].peso;
                    }
                }

                menorPosicao = -1;
                for (int j = 1; j < TabelaAberta.length; j++) {
                    if (
                        !isArray(indiceOld, j)              && 
                        menorCusto == TabelaAberta[j].peso  && 
                        TabelaAberta[j].valid               && 
                        (menorPosicao == -1 || menorPosicao > TabelaAberta[j].posicao)
                    ){
                        menorPosicao = TabelaAberta[j].posicao;
                    }
                }

                for (int j = 1; j < TabelaAberta.length; j++) {
                    if (menorPosicao == TabelaAberta[j].posicao){
                        tabelaAbertaTemp[indiceTemp] = TabelaAberta[j];
                        indiceOld[indiceTemp] = j;
                        indiceTemp++;
                    }
                }
            }
        }
        TabelaAberta = tabelaAbertaTemp;
    }
    
    public boolean isArray(int array[], int val){
        for (int i = 0; i < array.length; i++) {
            if(val == array[i]) return true;
        }
        return false;
    }
}
