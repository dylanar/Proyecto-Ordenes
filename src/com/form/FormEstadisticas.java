
package com.form;

import com.model.Model_Card;
import dbManager.DBManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
        

public class FormEstadisticas extends javax.swing.JPanel {


    public FormEstadisticas() {
        initComponents();
        
        panelEstadisticas.setVisible(false);
    }
    
    public void llenarEstadisticas(){
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/icon/stock.png")), "Valor Total", rellenoCardValorTotal(), "Suma Total"));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/icon/profit.png")), "Ingresos", rellenoCardIngresos(), "Ingresos generados"));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/icon/flag.png")), "Ordenes Totales", rellenoCardOrdenesTotales(), "Numero de ordenes"));
        int valorTotal = 0;
        int valorIngresos = 0;
        int valorInsumos = 0;
        try(var manager = new DBManager()) {
            valorTotal = manager.getInfoValorTotal();
            valorIngresos = manager.getInfoIngresos();
            valorInsumos = manager.getInfoInsumos();
            
            DefaultPieDataset datos = new DefaultPieDataset();
            datos.setValue("Valor Total", valorTotal);
            datos.setValue("Valor Ingresos", valorIngresos);
            datos.setValue("Valor Insumos", valorInsumos);
            
            JFreeChart grafico = ChartFactory.createPieChart(
            "Valores",
                 datos,
                 true,
                 true,
                 false
            );
            ChartPanel panel = new ChartPanel(grafico);
            panel.setMouseWheelEnabled(true);
            panel.setPreferredSize(new Dimension(300,340));
            
            jPanel1.setLayout(new BorderLayout());
            jPanel1.add(panel, BorderLayout.NORTH);

            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        
        
    }
    public static String formatearNumero(long numero) {
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.US);
        return formato.format(numero);
    }
    
    private String rellenoCardIngresos(){
        int ingresos = 0;
        try(var manager = new DBManager()) {
            ingresos = manager.getInfoIngresos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        String ingresoString = "$" + formatearNumero(ingresos);
        return ingresoString;
    }
    
    private String rellenoCardValorTotal(){
        int valorTotal = 0;
        try(var manager = new DBManager()) {
            valorTotal = manager.getInfoValorTotal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        String ingresoString = "$" + formatearNumero(valorTotal);
        return ingresoString;
    }
    
    private String rellenoCardOrdenesTotales(){
        int ordenesTotales = 0;
        try(var manager = new DBManager()) {
            ordenesTotales = manager.getInfoOrdenesTotal();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        String ingresoString = formatearNumero(ordenesTotales);
        return ingresoString;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();
        panelEstadisticas = new javax.swing.JPanel();
        card1 = new com.component.Card();
        card2 = new com.component.Card();
        card3 = new com.component.Card();
        jPanel1 = new javax.swing.JPanel();

        setBackground(new java.awt.Color(242, 242, 242));
        setForeground(new java.awt.Color(242, 242, 242));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/1.png"))); // NOI18N
        jLabel3.setText("Estadisticas");

        btnGenerar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        panelEstadisticas.setBackground(new java.awt.Color(242, 242, 242));

        card1.setColor1(new java.awt.Color(0, 153, 204));
        card1.setColor2(new java.awt.Color(0, 102, 204));

        card2.setColor1(new java.awt.Color(204, 204, 204));
        card2.setColor2(new java.awt.Color(102, 102, 102));

        card3.setColor1(new java.awt.Color(255, 100, 110));
        card3.setColor2(new java.awt.Color(155, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelEstadisticasLayout = new javax.swing.GroupLayout(panelEstadisticas);
        panelEstadisticas.setLayout(panelEstadisticasLayout);
        panelEstadisticasLayout.setHorizontalGroup(
            panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadisticasLayout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelEstadisticasLayout.createSequentialGroup()
                        .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        panelEstadisticasLayout.setVerticalGroup(
            panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadisticasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelEstadisticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(464, 464, 464)
                        .addComponent(btnGenerar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(panelEstadisticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 18, Short.MAX_VALUE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEstadisticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(btnGenerar)
                .addGap(29, 29, 29))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
         if (panelEstadisticas.isVisible()) {
            panelEstadisticas.setVisible(false);
            btnGenerar.setText("Mostrar");
        } else {
            llenarEstadisticas();
            panelEstadisticas.setVisible(true);
            btnGenerar.setText("Ocultar");
        }
    }//GEN-LAST:event_btnGenerarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerar;
    private com.component.Card card1;
    private com.component.Card card2;
    private com.component.Card card3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelEstadisticas;
    // End of variables declaration//GEN-END:variables
}
