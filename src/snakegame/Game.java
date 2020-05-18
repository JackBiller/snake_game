/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.util.Random;

/**
 *
 * @author Jack
 */
public class Game {
    int campo;
    int snake[];
    int maca;
    Random gerador = new Random();
    boolean ck_body_shock = false;
    public Celula TabelaAberta[];
    public Celula TabelaFechada[];
    
    public Game(int campo, char sentido){
        
        TabelaAberta = new Celula[(campo*campo)];
        for (int i = 0; i < TabelaAberta.length; i++) TabelaAberta[i] = new Celula();
        TabelaFechada = new Celula[(campo*campo)];
        for (int i = 0; i < TabelaFechada.length; i++) TabelaFechada[i] = new Celula();
        
        this.campo = campo;
        this.snake = new int[(campo*campo)];
        for (int i = 0; i < this.snake.length; i++) this.snake[i] = 0;

        switch(sentido){
            case 'L':
                this.snake[0] = gerador.nextInt(((this.campo * this.campo) -2)); // -2;
                this.snake[0]++;
                if (this.snake[0] % this.campo == 0) this.snake[0] -= 2;
                if (this.snake[0] % this.campo == this.campo-1) this.snake[0] -= 1;
                this.snake[1] = this.snake[0]+1;
                this.snake[2] = this.snake[1]+1;
                break;
            case 'T': 
                this.snake[0] = gerador.nextInt(((this.campo * this.campo))); // - (this.campo*2)));
                this.snake[0]++;
                if (this.snake[0] > (this.campo * this.campo) - (this.campo*2)) this.snake[0] -= (this.campo*2);
                if (this.snake[0] <= (this.campo*2)) this.snake[0] += (this.campo*2);
                this.snake[1] = this.snake[0]+campo;
                this.snake[2] = this.snake[1]+campo;
                break;
            case 'R':
                this.snake[0] = gerador.nextInt(((this.campo * this.campo)));
                this.snake[0]++;
                if (this.snake[0] % this.campo == 1) this.snake[0] = this.snake[0]+2;
                if (this.snake[0] % this.campo == 2) this.snake[0] = this.snake[0]+1;
                this.snake[1] = this.snake[0]-1;
                this.snake[2] = this.snake[1]-1;
                break;
            case 'B':
                this.snake[0] = gerador.nextInt(((this.campo * this.campo) - (this.campo*2))) + (this.campo*2);
                this.snake[0]++;
                this.snake[1] = this.snake[0]-campo;
                this.snake[2] = this.snake[1]-campo;
                break;
        }

        getPositionMaca();
    }

    public String Draw(){
        String color;
        String html = "<html>"
                   +    "<table>";
        
        for (int i = 0; i < this.campo; i++){
            html += "<tr>";
            for (int j = 0; j < this.campo; j++){
                color = this.getColor((j + 1) + (campo * i));
                html += ""
                    +   "<td style='background:"+ color + ";color:black;width:20px;height:20px'>"
                    // +   "<td style='background:"+ color + ";color:" + color + ";width:20px;height:20px'>"
                    +       ((j + 1) + (campo * i))
                    +   "</td>";
            }
            html += "</tr>";
        }
        html += "</table>"
            + "</html>";
        return html;
    }
    
    public String returnStatus(){
        int posSnake = 0;
        for (int i = 0; i < snake.length; i++) {
            if(snake[i] != 0){
                posSnake++;
            }
        }
        return "Cobra com tamanho " + posSnake + " falta comer " + (campo*campo - posSnake) + " maca(s)";
    }
    
    public String getColor(int num){
        String color = "gray";
        
        
        for (int i = 0; i < TabelaAberta.length; i++) {
            if (num == TabelaAberta[i].posicao) color = "green";
        }
        
        for (int i = 0; i < TabelaFechada.length; i++) {
            if (num == TabelaFechada[i].posicao) color = "black";
        }
        
        if (maca == num) color = "red";

        for (int i = this.snake.length-1; i >= 0; i--)
            if (num == this.snake[i])
                color = i == 0 ? "orange" : "yellow";
        return color;
    }
    
    public void clearTable(){
        TabelaAberta = new Celula[campo*campo];
        for (int i = 0; i < TabelaAberta.length; i++) TabelaAberta[i] = new Celula();
        TabelaFechada = new Celula[campo*campo];
        for (int i = 0; i < TabelaFechada.length; i++) TabelaFechada[i] = new Celula();
    }
    
    public char getSetido(int diff){
        if (diff > 0)   return diff >  1 ? 'T' : 'L';
        else            return diff < -1 ? 'B' : 'R';
    }


    public void move(int indice, char direction){
        if (indice >= this.snake.length || this.snake[indice] == 0) return;

        int num = this.returnValMove(this.snake[indice], direction, indice);
        char newDirection = ' ';

        if ( (indice + 1) < this.snake.length && this.snake[(indice+1)] != 0 ){
            int diffPos = this.snake[indice] - this.snake[ (indice + 1) ];
            if (diffPos > 0)    newDirection = diffPos >  1 ? 'B' : 'R';
            else                newDirection = diffPos < -1 ? 'T' : 'L';
        }

        if (num != 0) {
            this.snake[indice] = num;
            indice++;

            if (newDirection != ' ') this.move(indice, newDirection);
        }
    }
    
    public void checkFood(){
        int check = 0;
        if (this.snake[0] == maca){
            for (int i = 0; i < snake.length; i++) {
                if (snake[i] == 0){
                    check = i+1;
                    snake[i] = snake[i-2];
                    i = snake.length;
                }
            }
            if (check < snake.length){
                if (check != 0){
                    getPositionMaca();
                }
            } else {
                maca = 0;
            }
        }
    }
    
    private void getPositionMaca(){
        int check = 0, cont = 0, contValid = 0;
        boolean ocupado;
        
        for (int i = 0; i < snake.length; i++) {
            if (snake[i] == 0){
                check = i;
                i = snake.length;
            }
        }
        
        check = snake.length - check;
        check = gerador.nextInt(check)+1;
        for (int i = 0; i < snake.length; i++) {
            cont++;
            ocupado = false;
            for (int j = 0; j < snake.length; j++) {
               if (cont == snake[j]) ocupado = true;
            }
            if (!ocupado) contValid++;
            if (contValid == check){
                maca = cont;
                i = snake.length;
            }
        }
    }
    
    public int returnValMove(int num, char direction, int indice){
        int numNew = 0;
        
        switch(direction){
            case 'L': 
                if (num%this.campo != 1) numNew = num-1;
                break;
            case 'R': 
                if (num%this.campo != 0) numNew = num+1;
                break;
            case 'T': 
                if (num-this.campo > 0) numNew = num-this.campo;
                break;
            case 'B': 
                if ((num+this.campo) <= (this.campo*this.campo)) numNew = num+this.campo;
                break;
        }
        // Se a cabeça na cobra ta indo na direção do corpo e ta valido essa condição
        if (isNumSnake(numNew) && ck_body_shock && indice == 0) numNew = 0;

        return numNew;
    }

    public boolean isNumSnake(int num){
        for (int i = 0; i < snake.length; i++) {
            if (num == snake[i]) return true;
        }
        return false;
    }

    public int calDistance(int start, int end){
        int distance = 0;

        int lineStart = (start / campo);
        int lineEnd = (end / campo);

        if(start%campo==0) lineStart--;
        if(end%campo==0) lineEnd--;

        int distanceLine = lineStart - lineEnd;
        if (distanceLine < 0) distanceLine *= -1;

        int columnStart = start%campo;
        if (columnStart == 0) columnStart = campo;
        int columnEnd = end%campo;
        if (columnEnd == 0) columnEnd = campo;
        int distanceColumn = columnStart - columnEnd;
        if (distanceColumn < 0) distanceColumn *= -1;

        distance = distanceColumn + distanceLine;

        return distance;
    }

    public boolean isCk_body_shock() {
        return ck_body_shock;
    }

    public void setCk_body_shock(boolean ck_body_shock) {
        this.ck_body_shock = ck_body_shock;
    }
}
