/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


import javax.swing.*;

/**
 *
 * @author Jack
 */
public final class start extends JFrame {
    Game game;
    int campo = 8;
    char sentidoStart = 'B';
    char sentido = sentidoStart;
    boolean gameRun = false;
    boolean gameIA = false;
    int indiceMoveIA = 0;
    int indiceIA = -1;
    Caminho caminho[];
    public static final long TEMPO = 200;
    Random gerador = new Random();
    IA ia = new IA();
    
    KeyListener evento =  new KeyListener(){
        @Override
        public void keyTyped(KeyEvent e){}

        @Override
        public void keyReleased(KeyEvent e)
        {
            char sentidoTemp = ' ';
            if (e.getKeyCode() == 27) {
                btnStart.setText(gameRun ? "Continuar" : "Pausar");
                gameRun = !gameRun;
            }
            if (e.getKeyCode() == 37) sentidoTemp = 'L';
            if (e.getKeyCode() == 38) sentidoTemp = 'T';
            if (e.getKeyCode() == 39) sentidoTemp = 'R';
            if (e.getKeyCode() == 40) sentidoTemp = 'B';
            if (sentidoTemp != ' ') sentido = sentidoTemp;
        }

        @Override
        public void keyPressed(KeyEvent e) {}
    };

    /**
     * Creates new form start
     */
    public start() {
        initComponents();
        
        ck_body_shock.setSelected(true);
        
        generateSentido();
        sentido = sentidoStart;
        game = new Game(campo, sentido);
        lbCampo.setText(game.Draw());

        btnStart.addKeyListener(evento);
        btnTop.addKeyListener(evento);
        btnBottom.addKeyListener(evento);
        btnLeft.addKeyListener(evento);
        btnRight.addKeyListener(evento);
        ck_body_shock.addKeyListener(evento);
        btnRestart.addKeyListener(evento);
        update();
    }
    
    public void update(){
        Timer timer = null;
        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {
                    try {
                        lbStatusGame.setText(game.returnStatus());
                        if (gameRun){
                            game.move(0, sentido);
                            game.checkFood();
                            lbCampo.setText(game.Draw());
                        } else if(gameIA){
                            if (game.maca != 0){
                                boolean isMove = false;
                                if (indiceIA == -1) {
                                    caminho = ia.a_star(game);
                                    lbCampo.setText(game.Draw());
                                    for (int i = 0; i < caminho.length; i++) {
                                        // System.out.println("caminho: " + caminho[i].posicao);
                                        if(!caminho[i].valid){
                                            caminho[i].posicao = game.maca;
                                            caminho[i].status = game.Draw();
                                            i = caminho.length;
                                        }
                                    }
                                    indiceIA++;
                                }

                                if (indiceIA != -2 && indiceIA != -3 && indiceIA != -1){
                                    // System.out.println("indice: " + indiceIA);
                                    lbCampo.setText(caminho[indiceIA].status);
                                    indiceIA++;
                                } else if(indiceIA != -3 && indiceIA != -1){
                                    int diff = game.snake[0] - caminho[indiceMoveIA].posicao;
                                    /* System.out.println("game.snake[0]: " + game.snake[0]);
                                    System.out.println("caminho[indiceMoveIA].posicao: " + caminho[indiceMoveIA].posicao);
                                    System.out.println("game.getSetido(diff): " + game.getSetido(diff)); */
                                    game.move(0, game.getSetido(diff));
                                    lbCampo.setText(game.Draw());

                                    indiceMoveIA++;

                                    if (indiceMoveIA >= caminho.length || "".equals(caminho[indiceMoveIA].status)){
                                        /* diff = game.snake[0] - game.maca;
                                        game.move(0, game.getSetido(diff)); */
                                        game.checkFood();
                                        lbCampo.setText(game.Draw());
                                        indiceIA = -1;
                                        isMove = true;
                                    }
                                }


                                if (indiceIA != -2 && indiceIA != -3){
                                    // System.out.println("indice: " + indiceIA);
                                    if (indiceIA == -1){
                                        if(!isMove){
                                            /* int diff = game.snake[0] - game.maca;
                                            game.move(0, game.getSetido(diff)); */
                                            game.checkFood();
                                            lbCampo.setText(game.Draw());
                                        }
                                        indiceIA = -1;
                                    }
                                    else if (indiceIA >= caminho.length-1 || "".equals(caminho[indiceIA].status)){
                                        indiceIA = -2;
                                        indiceMoveIA = 1;
                                        game.clearTable();
                                        lbCampo.setText(game.Draw());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
        }
    }
    
    public void generateSentido(){
        switch(gerador.nextInt(4)){
            case 0: sentidoStart = 'L'; break;
            case 1: sentidoStart = 'T'; break;
            case 2: sentidoStart = 'R'; break;
            case 3: sentidoStart = 'B'; break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        btnStart = new javax.swing.JToggleButton();
        lbCampo = new javax.swing.JLabel();
        btnLeft = new javax.swing.JToggleButton();
        btnRight = new javax.swing.JToggleButton();
        btnTop = new javax.swing.JToggleButton();
        btnBottom = new javax.swing.JToggleButton();
        btnRestart = new javax.swing.JToggleButton();
        ck_body_shock = new javax.swing.JCheckBox();
        btnIA = new javax.swing.JToggleButton();
        lbStatusGame = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Jogo Snake");

        btnStart.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        btnStart.setText("Iniciar");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnLeft.setText("<");
        btnLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeftActionPerformed(evt);
            }
        });

        btnRight.setText(">");
        btnRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRightActionPerformed(evt);
            }
        });

        btnTop.setText("/\\");
            btnTop.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnTopActionPerformed(evt);
                }
            });

            btnBottom.setText("\\/");
            btnBottom.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnBottomActionPerformed(evt);
                }
            });

            btnRestart.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            btnRestart.setText("Reiniciar");
            btnRestart.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnRestartActionPerformed(evt);
                }
            });

            ck_body_shock.setText("O corpo é café com leite");
            ck_body_shock.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    ck_body_shockActionPerformed(evt);
                }
            });

            btnIA.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            btnIA.setText("IA");
            btnIA.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btnIAActionPerformed(evt);
                }
            });

            lbStatusGame.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
            lbStatusGame.setText("Carregando...");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lbStatusGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnIA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnRestart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ck_body_shock)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnLeft, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnRight, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btnTop, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnBottom, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnStart, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnRestart, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnIA, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(37, 37, 37)
                            .addComponent(ck_body_shock)
                            .addGap(77, 77, 77)
                            .addComponent(btnTop)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnRight)
                                .addComponent(btnLeft))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnBottom))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                .addComponent(lbStatusGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap(25, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        btnStart.setText(gameRun ? "Continuar" : "Pausar");
        gameRun = !gameRun;
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeftActionPerformed
        game.move(0,'L');
        lbCampo.setText(game.Draw());
    }//GEN-LAST:event_btnLeftActionPerformed

    private void btnTopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTopActionPerformed
        game.move(0,'T');
        lbCampo.setText(game.Draw());
    }//GEN-LAST:event_btnTopActionPerformed

    private void btnRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRightActionPerformed
        game.move(0,'R');
        lbCampo.setText(game.Draw());
    }//GEN-LAST:event_btnRightActionPerformed

    private void btnBottomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBottomActionPerformed
        game.move(0,'B');
        lbCampo.setText(game.Draw());
    }//GEN-LAST:event_btnBottomActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        generateSentido();
        sentido = sentidoStart;
        game = new Game(campo, sentidoStart);
        lbCampo.setText(game.Draw());
        btnStart.setText("Iniciar");
        indiceIA = -1;
        gameRun = false;
        gameIA = false;
    }//GEN-LAST:event_btnRestartActionPerformed

    private void ck_body_shockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ck_body_shockActionPerformed
        game.setCk_body_shock(!ck_body_shock.isSelected());
    }//GEN-LAST:event_ck_body_shockActionPerformed

    private void btnIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIAActionPerformed
        gameIA = !gameIA;
        /* Caminho caminho[] = ia.a_star(game);

        System.out.println("Caminho");
        for (int i = 1; i < caminho.length; i++) {
            if (caminho[i].valid){
                lbCampo.setText(caminho[i].status);
                System.out.println("Indice " + i + " = " + caminho[i].posicao);
            }
        }
        System.out.println("Maca " + game.maca); */
    }//GEN-LAST:event_btnIAActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(start.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new start().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnBottom;
    private javax.swing.JToggleButton btnIA;
    private javax.swing.JToggleButton btnLeft;
    private javax.swing.JToggleButton btnRestart;
    private javax.swing.JToggleButton btnRight;
    private javax.swing.JToggleButton btnStart;
    private javax.swing.JToggleButton btnTop;
    private javax.swing.JCheckBox ck_body_shock;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbCampo;
    private javax.swing.JLabel lbStatusGame;
    // End of variables declaration//GEN-END:variables
}
