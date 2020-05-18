/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

/**
 *
 * @author Jack
 */
public class Celula {
   public int posicao;
   public int peso;
   public boolean valid = false;
   public int pai = 0;
   
   public Celula(){
   }

   public Celula(int posicao, int peso, int pai){
       this.posicao = posicao;
       this.peso = peso;
       this.valid = true;
       this.pai = pai;
   }
}
