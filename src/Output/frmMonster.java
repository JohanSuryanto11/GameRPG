/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import models.Monster;

/**
 *
 * @author johan
 */
public class frmMonster extends javax.swing.JFrame {
    protected Monster tblmonster = new Monster();
    /**
     * Creates new form frmPlayer
     */
    public frmMonster() {
        initComponents();
        tampilkanDataAll();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtcari = new javax.swing.JTextField();
        cmbcari = new javax.swing.JComboBox<>();
        btncari = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jspsiswa = new javax.swing.JScrollPane();
        jtbsiswa = new javax.swing.JTable();
        btnakhir = new javax.swing.JButton();
        btnsesudah = new javax.swing.JButton();
        btnsebelum = new javax.swing.JButton();
        btnawal = new javax.swing.JButton();

        txtcari.setText("jTextField1");

        cmbcari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id", "Nama", " " }));

        btncari.setText("Cari");
        btncari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncariActionPerformed(evt);
            }
        });

        jButton1.setText("Resfresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jtbsiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtbsiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbsiswaMouseClicked(evt);
            }
        });
        jtbsiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtbsiswaKeyReleased(evt);
            }
        });
        jspsiswa.setViewportView(jtbsiswa);

        btnakhir.setText("Akhir");
        btnakhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnakhirActionPerformed(evt);
            }
        });

        btnsesudah.setText("Sesudah");
        btnsesudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsesudahActionPerformed(evt);
            }
        });

        btnsebelum.setText("Sebelum");
        btnsebelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsebelumActionPerformed(evt);
            }
        });

        btnawal.setText("Awal");
        btnawal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnawalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnawal)
                        .addGap(45, 45, 45)
                        .addComponent(btnsebelum)
                        .addGap(47, 47, 47)
                        .addComponent(btnsesudah)
                        .addGap(47, 47, 47)
                        .addComponent(btnakhir))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btncari, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jButton1))
                    .addComponent(jspsiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(91, 91, 91))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btncari)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jspsiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnawal)
                    .addComponent(btnsebelum)
                    .addComponent(btnsesudah)
                    .addComponent(btnakhir))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncariActionPerformed

        if(!txtcari.getText().trim().equalsIgnoreCase("")){
            Vector<String> tableHeader = new Vector<String>();
            tableHeader.add("Id");
            tableHeader.add("Nama");
            tableHeader.add("Atk");
            tableHeader.add("Def");
            tableHeader.add("Spd");
            tableHeader.add("HP");

            Vector tableData = tblmonster.LookUp(cmbcari.getSelectedItem().toString(), txtcari.getText());

            jtbsiswa.setModel(new DefaultTableModel(tableData,tableHeader));
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.RIGHT);
            if(jtbsiswa.getRowCount()>0){
                klikawal();
                jtbsiswa.grabFocus();

            }else jtbsiswa.removeAll();
        }
    }//GEN-LAST:event_btncariActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilkanDataAll();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtbsiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbsiswaMouseClicked

    }//GEN-LAST:event_jtbsiswaMouseClicked

    private void jtbsiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtbsiswaKeyReleased

    }//GEN-LAST:event_jtbsiswaKeyReleased

    private void btnakhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnakhirActionPerformed
        klikAkhir();
    }//GEN-LAST:event_btnakhirActionPerformed

    private void btnsesudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsesudahActionPerformed
        klikSesudah();
    }//GEN-LAST:event_btnsesudahActionPerformed

    private void btnsebelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsebelumActionPerformed
        klikSebelum();
    }//GEN-LAST:event_btnsebelumActionPerformed

    private void btnawalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnawalActionPerformed
        klikawal();
    }//GEN-LAST:event_btnawalActionPerformed
    
    private void posisiPergi(int p){jtbsiswa.setRowSelectionInterval(p, p);}
    private void klikawal(){jspsiswa.getVerticalScrollBar().setValue(0);posisiPergi(0);}
    
     private void klikSebelum(){
    if(jtbsiswa.getSelectedRow()>0){
    jspsiswa.getVerticalScrollBar().setValue((jtbsiswa.getSelectedRow()-1) * jtbsiswa.getRowHeight());
        posisiPergi(jtbsiswa.getSelectedRow()-1);
    }
    }
    
        private void klikSesudah(){
    if(jtbsiswa.getSelectedRow()<jtbsiswa.getRowCount()-1){
    jspsiswa.getVerticalScrollBar().setValue((jtbsiswa.getSelectedRow()-1)*jtbsiswa.getRowHeight());
     posisiPergi(jtbsiswa.getSelectedRow()+1);
    }
    }
        private void klikAkhir(){  
    jspsiswa.getVerticalScrollBar().setValue(jtbsiswa.getRowCount()*jtbsiswa.getRowHeight());
     posisiPergi(jtbsiswa.getRowCount()-1);    
    }
        
        public void tampilkanDataAll(){
     Vector<String> tableHeader = new Vector<String>();
        tableHeader.add("Id");
        tableHeader.add("Nama");
        tableHeader.add("Atk");
        tableHeader.add("Def");
        tableHeader.add("Spd");
        tableHeader.add("HP");
        Vector tableData = tblmonster.Load();
        if(tableData!=null){
            jtbsiswa.setModel(new DefaultTableModel(tableData,tableHeader));
            DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.RIGHT);
        }
        klikawal();
        jtbsiswa.grabFocus();
    }
        
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
            java.util.logging.Logger.getLogger(frmMonster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMonster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMonster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMonster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMonster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnakhir;
    private javax.swing.JButton btnawal;
    private javax.swing.JButton btncari;
    private javax.swing.JButton btnsebelum;
    private javax.swing.JButton btnsesudah;
    private javax.swing.JComboBox<String> cmbcari;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jspsiswa;
    private javax.swing.JTable jtbsiswa;
    private javax.swing.JTextField txtcari;
    // End of variables declaration//GEN-END:variables
}
