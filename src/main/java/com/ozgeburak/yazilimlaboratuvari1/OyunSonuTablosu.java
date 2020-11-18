/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ozgeburak.yazilimlaboratuvari1;

import static com.ozgeburak.yazilimlaboratuvari1.SecimEkrani.menuArkaplan;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author burak
 */
public class OyunSonuTablosu extends javax.swing.JPanel {
    BufferedImage menuArkaplan;
    /**
     * Creates new form OyunSonuTablosu
     */
    public OyunSonuTablosu() throws IOException {
        
            initComponents();
            
            
            
            menuArkaplan = ImageIO.read(new File("kaynaklar/MenuArkasi.png"));
            
            this.setBackground(new Color(141, 183, 242));
       
    }
    
    @Override
    public void paintComponent(Graphics g){
         super.paintComponent(g);
        
        int offset = 10;
        
        g.drawImage(menuArkaplan, ciktiTablosu.getX() - offset, ciktiTablosu.getY() - offset,
                ciktiTablosu.getX() + ciktiTablosu.getWidth() + offset, ciktiTablosu.getY() + ciktiTablosu.getHeight() + offset,
                0, 0, menuArkaplan.getWidth(), menuArkaplan.getHeight(), null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ciktiTablosu = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cikti1 = new javax.swing.JLabel();
        cikti2 = new javax.swing.JLabel();
        cikti3 = new javax.swing.JLabel();
        cikti4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cikti5 = new javax.swing.JLabel();
        cikti6 = new javax.swing.JLabel();
        cikti7 = new javax.swing.JLabel();
        cikti8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        cikti9 = new javax.swing.JLabel();
        cikti10 = new javax.swing.JLabel();
        cikti11 = new javax.swing.JLabel();
        cikti12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cikti13 = new javax.swing.JLabel();
        cikti14 = new javax.swing.JLabel();
        cikti15 = new javax.swing.JLabel();
        cikti16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        ciktiTablosu.setOpaque(false);
        ciktiTablosu.setPreferredSize(new java.awt.Dimension(436, 430));
        ciktiTablosu.setLayout(new java.awt.GridLayout(5, 5));

        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel4);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Adım Sayısı");
        jLabel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel3);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Kalan Altın");
        jLabel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel9);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Toplanan Altın");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel10);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Harcanan Altın");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel2);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("A Oyuncusu");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel1);

        cikti1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti1.setText("cikti1");
        cikti1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti1);

        cikti2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti2.setText("cikti2");
        cikti2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti2);

        cikti3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti3.setText("cikti3");
        cikti3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti3);

        cikti4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti4.setText("cikti4");
        cikti4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti4);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("B Oyuncusu");
        jLabel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel11);

        cikti5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti5.setText("cikti5");
        cikti5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti5);

        cikti6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti6.setText("cikti6");
        cikti6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti6);

        cikti7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti7.setText("cikti7");
        cikti7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti7);

        cikti8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti8.setText("cikti8");
        cikti8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti8);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("C Oyuncusu");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel12);

        cikti9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti9.setText("cikti9");
        cikti9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti9);

        cikti10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti10.setText("cikti10");
        cikti10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti10);

        cikti11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti11.setText("cikti11");
        cikti11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti11);

        cikti12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti12.setText("cikti12");
        cikti12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti12);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("D Oyuncusu");
        jLabel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(jLabel17);

        cikti13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti13.setText("cikti13");
        cikti13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti13);

        cikti14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti14.setText("cikti14");
        cikti14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti14);

        cikti15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti15.setText("cikti15");
        cikti15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti15);

        cikti16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cikti16.setText("cikti16");
        cikti16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ciktiTablosu.add(cikti16);

        jButton1.setText("Çıkış");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Ciktilar dosyalara yazdirildi.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(332, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ciktiTablosu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(333, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(ciktiTablosu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(100, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel cikti1;
    public javax.swing.JLabel cikti10;
    public javax.swing.JLabel cikti11;
    public javax.swing.JLabel cikti12;
    public javax.swing.JLabel cikti13;
    public javax.swing.JLabel cikti14;
    public javax.swing.JLabel cikti15;
    public javax.swing.JLabel cikti16;
    public javax.swing.JLabel cikti2;
    public javax.swing.JLabel cikti3;
    public javax.swing.JLabel cikti4;
    public javax.swing.JLabel cikti5;
    public javax.swing.JLabel cikti6;
    public javax.swing.JLabel cikti7;
    public javax.swing.JLabel cikti8;
    public javax.swing.JLabel cikti9;
    private javax.swing.JPanel ciktiTablosu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
