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
public class Caminho {
    public int posicao;
    public String status = "";
    public boolean valid = false;
    public int pai = 0;
    
    public Caminho(){
    }

    public Caminho(int posicao, String status, int pai){
        this.posicao = posicao;
        this.status = status;
        this.pai = pai;
        valid = true;
    }
    
}
