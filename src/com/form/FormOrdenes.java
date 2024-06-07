/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.form;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import dbManager.DBManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAVEN
 */
public class FormOrdenes extends javax.swing.JPanel {
    private DecimalFormat formatter = new DecimalFormat("#,###");

    List<JTextField> misCampos = new ArrayList<>();
    DefaultTableModel modelo;
    public FormOrdenes() {
        initComponents();
        rellenoTabla();
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        misCampos.add(tfCedula);
        misCampos.add(tfChasis);
        misCampos.add(tfColor);
        misCampos.add(tfFecha);
        misCampos.add(tfInsumos);
        misCampos.add(tfKilometraje);
        misCampos.add(tfMatricula);
        misCampos.add(tfModelo);
        misCampos.add(tfServicios);
        misCampos.add(tfTotal);

    }
    
    private void agregar(){
         try(var manager = new DBManager()) {
             
            if(verificarCamposLlenos(misCampos)){
                
                List<List<String>> clientes = manager.getInfoClientes();
                boolean existe = false;
                for (List<String> cliente : clientes) {
                    if(cliente.get(0).equals(tfCedula.getText())){
                        existe = true;
                    }
                }
                if(existe){
                    List<String> orden = new ArrayList<>();
                    orden.add(tfFecha.getText());
                    orden.add(tfCedula.getText());
                    orden.add(tfMatricula.getText());
                    orden.add(tfModelo.getText());
                    orden.add(tfColor.getText());
                    orden.add(tfChasis.getText());
                    orden.add(tfKilometraje.getText());
                    orden.add(String.valueOf(sliderGasolina.getValue()));
                    orden.add(taDescripcion.getText());
                    orden.add(tfServicios.getText());
                    orden.add(tfInsumos.getText());
                    orden.add(tfTotal.getText());

                    boolean resp = manager.agregarOrden(orden);
                    rellenoTabla();
                    if (resp) {
                        JOptionPane.showMessageDialog(this,"Orden Agregado Correctamente");  
                    } else{
                         JOptionPane.showMessageDialog(this,"Hubo un error agregando la orden","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else{
                    JOptionPane.showMessageDialog(this,"La cedula no esta registrada","Error",JOptionPane.ERROR_MESSAGE);
                }
                
         
            } else{
                JOptionPane.showMessageDialog(this,"Debe llenar todos los campos","Error",JOptionPane.ERROR_MESSAGE);
             
            }
              
                 
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
    }     
    
    private void buscar(){
        
        try(var manager = new DBManager()) {
            List<List<String>> ordenes = manager.getInfoOrdenes();
            modelo = (DefaultTableModel) tabla.getModel();
            
            if(tfNumeroOrden.getText().equals("") && tfFecha.getText().equals("") && tfCedula.getText().equals("") && tfTotal.getText().equals("") && tfMatricula.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Debe llenar algún campo valido (orden, fecha, cedula, total, matricula)","Error",JOptionPane.ERROR_MESSAGE);
            } else if(!tfNumeroOrden.getText().equals("") && !tfFecha.getText().equals("") && !tfCedula.getText().equals("") && !tfTotal.getText().equals("") && !tfMatricula.getText().equals("")){
                JOptionPane.showMessageDialog(this,"Solo debe haber 1 campo lleno","Error",JOptionPane.ERROR_MESSAGE);
            } else{
                
               if(!tfNumeroOrden.getText().equals("") && tfFecha.getText().equals("") && tfCedula.getText().equals("") && tfTotal.getText().equals("") && tfMatricula.getText().equals("")){ 
                   modelo.setRowCount(0);
                   for (List<String> orden : ordenes) {
                    if (orden.get(0).startsWith(tfNumeroOrden.getText())){
                        modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
                        }    
                    }  
                
                } else if(tfNumeroOrden.getText().equals("") && !tfFecha.getText().equals("") && tfCedula.getText().equals("") && tfTotal.getText().equals("") && tfMatricula.getText().equals("")){ 
                   modelo.setRowCount(0);
                   for (List<String> orden : ordenes) {
                    if (orden.get(1).startsWith(tfFecha.getText())){
                        modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
                        }    
                    }  
                
                
                } else if(tfNumeroOrden.getText().equals("") && tfFecha.getText().equals("") && !tfCedula.getText().equals("") && tfTotal.getText().equals("") && tfMatricula.getText().equals("")){ 
                   modelo.setRowCount(0);
                   for (List<String> orden : ordenes) {
                    if (orden.get(2).startsWith(tfCedula.getText())){
                        modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
                        }    
                    }  
                
                
                } else if(tfNumeroOrden.getText().equals("") && tfFecha.getText().equals("") && tfCedula.getText().equals("") && !tfTotal.getText().equals("") && tfMatricula.getText().equals("")){ 
                   modelo.setRowCount(0);
                   for (List<String> orden : ordenes) {
                    if (orden.get(12).startsWith(tfTotal.getText())){
                        modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
                        }    
                    }  
                
                
                } else if(tfNumeroOrden.getText().equals("") && tfFecha.getText().equals("") && tfCedula.getText().equals("") && tfTotal.getText().equals("") && !tfMatricula.getText().equals("")){ 
                   modelo.setRowCount(0);
                   for (List<String> orden : ordenes) {
                    if (orden.get(3).startsWith(tfMatricula.getText())){
                        modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
                        }    
                    }  
                
                
                }

            }
              
                 
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        
    }
   
    public void eliminar(){
        try(var manager = new DBManager()) {
            if(!tfNumeroOrden.getText().equals("")){
                manager.eliminarOrden(tfNumeroOrden.getText());
                JOptionPane.showMessageDialog(this,"Orden eliminada Correctamente");
                rellenoTabla();   
            } else{
                JOptionPane.showMessageDialog(this,"Debe llenar el numero de orden","Error",JOptionPane.ERROR_MESSAGE);
            }
            
                
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
    
    }

    private void actualizar(){
        
        try(var manager = new DBManager()) {
            if(verificarCamposLlenos(misCampos)){
               List<String> orden = new ArrayList<>();
               orden.add(tfFecha.getText());
               orden.add(tfCedula.getText());
               orden.add(tfMatricula.getText());
               orden.add(tfModelo.getText());
               orden.add(tfColor.getText());
               orden.add(tfChasis.getText());
               orden.add(tfKilometraje.getText());
               orden.add(String.valueOf(sliderGasolina.getValue()));
               orden.add(taDescripcion.getText());
               orden.add(tfInsumos.getText());
               orden.add(tfServicios.getText());
               orden.add(tfTotal.getText());
                boolean resp = manager.actualizarOrden(orden, tfNumeroOrden.getText());
                if (resp) {
                
                    rellenoTabla();
                    JOptionPane.showMessageDialog(this,"Cliente Actualizado Correctamente");    
                
                } else {
                    JOptionPane.showMessageDialog(this,"Error al Actualizado cliente","Error",JOptionPane.ERROR_MESSAGE);
                               
                }
            } else{
                JOptionPane.showMessageDialog(this,"Debe llenar todos los campos","Error",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }  
    }    
    
    private void rellenoTabla(){
        try(var manager = new DBManager()) {
            List<List<String>> ordenes = manager.getInfoOrdenes();
            modelo = (DefaultTableModel) tabla.getModel();
            modelo.setRowCount(0);
            for (List<String> orden : ordenes) {
                modelo.addRow(new Object[]{orden.get(0), orden.get(1), orden.get(2),orden.get(2),orden.get(4),orden.get(5),orden.get(6),orden.get(7), orden.get(8), orden.get(10), orden.get(11), orden.get(12)});
            }   
                
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        
    }
   
    public static boolean verificarCamposLlenos(List<JTextField> campos) {
        for (JTextField campo : campos) {
            if (campo.getText().equals("")) {
                return false; 
            }
        }
        return true; 
    }
    
    public List<String> sacarInfoCliente(String cedula){
        List<String> cliente = new ArrayList<>();
        try(var manager = new DBManager()) {
            cliente = manager.encontrarCliente(cedula);
                     
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Excepción SQL: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
            System.out.println("Código de error: " + ex.getErrorCode());
        }
        return cliente;
    }
    
    public void descargarOrden() throws FileNotFoundException, MalformedURLException{
            if(verificarCamposLlenos(misCampos)){
               List<String> orden = new ArrayList<>();
                orden.add(tfNumeroOrden.getText());
                orden.add(tfFecha.getText());
                orden.add(tfCedula.getText());
                orden.add(tfMatricula.getText());
                orden.add(tfModelo.getText());
                orden.add(tfColor.getText());
                orden.add(tfChasis.getText());
                orden.add(tfKilometraje.getText());
                orden.add(String.valueOf(sliderGasolina.getValue()));
                orden.add(taDescripcion.getText());
                orden.add(tfServicios.getText());
                orden.add(tfInsumos.getText());
                orden.add(tfTotal.getText()); 
                List<String> cliente = sacarInfoCliente(orden.get(2));
                String dest = System.getProperty("user.home") + "/Downloads/"+ "orden" +orden.get(0) +".pdf";
                String LOGO_PATH = "C:\\Users\\RYZEN\\Desktop\\java-ui-dashboard-001-main\\part 3\\proyectoPadre\\src\\com\\icon\\logoOrdenes.png";
                
                File file = new File(dest);
                file.getParentFile().mkdirs();

                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
                Document doc = new Document(pdfDoc);

                // Estilo para el texto centrado y con negrita
                Style centeredBold = new Style()
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(16); // Tamaño de fuente
                
                Style titulo = new Style()
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setFontSize(20); // Tamaño de fuente
                

                Style texto = new Style()
                        .setFontSize(15); // Tamaño de fuente

                        


                Image logo = new Image(ImageDataFactory.create(LOGO_PATH));
                logo.setWidth(70); // Ancho de la imagen (ajustar según necesidad)
                logo.setHeight(70); // Alto de la imagen (ajustar según necesidad)
                doc.add(logo.setFixedPosition(45, 740)); // Posición absoluta (ajustar según necesidad)

                // Contenido del formulario
                doc.add(new Paragraph("AUTOS Y MOTOS BMW S.A.S").addStyle(titulo));
                doc.add(new Paragraph("Nit 72.269.525-2 – Régimen simplificado").addStyle(centeredBold));
                doc.add(new Paragraph("_____________________________________________________________________________"));
                doc.add(new Paragraph("Recepción de vehículo").addStyle(centeredBold));
                doc.add(new Paragraph("Fecha: " + orden.get(1)).addStyle(texto));
                doc.add(new Paragraph("Cedula de propietario: " + cliente.get(0)).addStyle(texto));
                doc.add(new Paragraph("Nombre de propietario: " + cliente.get(1)).addStyle(texto));
                doc.add(new Paragraph("Teléfono: " + cliente.get(2)).addStyle(texto));
                doc.add(new Paragraph("Dirección: " + cliente.get(3)).addStyle(texto));
                doc.add(new Paragraph("_____________________________________________________________________________"));
                doc.add(new Paragraph("Datos del vehículo").addStyle(centeredBold));
                doc.add(new Paragraph("Matricula: " + orden.get(3)).addStyle(texto));
                doc.add(new Paragraph("Modelo: " + orden.get(4)).addStyle(texto));
                doc.add(new Paragraph("Color: " + orden.get(5)).addStyle(texto));
                doc.add(new Paragraph("Nr Chasis: " + orden.get(6)).addStyle(texto));
                doc.add(new Paragraph("Marca: " + orden.get(7)).addStyle(texto));
                doc.add(new Paragraph("Medidor de gasolina: " + orden.get(8)).addStyle(texto));
                doc.add(new Paragraph("Técnico a cargo del vehículo: Luis Arevalo").addStyle(texto));
                doc.add(new Paragraph("Descripcion del servicio: " + orden.get(9)).addStyle(texto));
                doc.add(new Paragraph("_____________________________________________________________________________"));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph(" "));
                doc.add(new Paragraph("Firma: ").addStyle(texto));

                doc.close();
                JOptionPane.showMessageDialog(this,"Orden descargada correctamente");
                

         
            } else{
                JOptionPane.showMessageDialog(this,"Debe llenar todos los campos","Error",JOptionPane.ERROR_MESSAGE);
             
            } 
    }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        labelOrden = new javax.swing.JLabel();
        labelFecha = new javax.swing.JLabel();
        labelCedula = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelColor = new javax.swing.JLabel();
        tfFecha = new javax.swing.JTextField();
        tfNumeroOrden = new javax.swing.JTextField();
        tfMatricula = new javax.swing.JTextField();
        tfInsumos = new javax.swing.JTextField();
        tfColor = new javax.swing.JTextField();
        tfModelo = new javax.swing.JTextField();
        tfChasis = new javax.swing.JTextField();
        tfCedula = new javax.swing.JTextField();
        labelChasis = new javax.swing.JLabel();
        labelKilometraje = new javax.swing.JLabel();
        labelGasolina = new javax.swing.JLabel();
        tfKilometraje = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfServicios = new javax.swing.JTextField();
        tfTotal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taDescripcion = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        sliderGasolina = new javax.swing.JSlider();
        labelContador = new javax.swing.JLabel();

        jScrollPane3.setViewportView(jEditorPane1);

        setBackground(new java.awt.Color(242, 242, 242));
        setPreferredSize(new java.awt.Dimension(915, 600));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/2.png"))); // NOI18N
        jLabel3.setText("Orden de Servicios");

        jPanel1.setBackground(new java.awt.Color(242, 242, 242));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tabla", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N

        tabla.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# Orden", "Fecha", "Cedula", "Matricula", "Modelo", "Color", "Chasis", "Marca", "Gasolina", "V. Insumo", "V. Ingreso", "V. Servicio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(242, 242, 242));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N

        labelOrden.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelOrden.setText("Orden:");

        labelFecha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelFecha.setText("Fecha:");
        labelFecha.setToolTipText("");

        labelCedula.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelCedula.setText("Cedula: ");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Matricula:");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Modelo:");

        labelColor.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelColor.setText("Color:");

        tfFecha.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfNumeroOrden.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfMatricula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfInsumos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfColor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfModelo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfChasis.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        tfCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        labelChasis.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelChasis.setText("Chasis: ");

        labelKilometraje.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelKilometraje.setText("Marca:");

        labelGasolina.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelGasolina.setText("Gasolina:");

        tfKilometraje.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setText("Valor Insumos: ");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Valor Ingreso: ");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Valor Servicio:");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Descripción:");

        tfServicios.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfServicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfServiciosActionPerformed(evt);
            }
        });

        tfTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfTotalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfTotalFocusLost(evt);
            }
        });
        tfTotal.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                tfTotalInputMethodTextChanged(evt);
            }
        });
        tfTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTotalActionPerformed(evt);
            }
        });

        taDescripcion.setColumns(20);
        taDescripcion.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        taDescripcion.setRows(5);
        jScrollPane2.setViewportView(taDescripcion);

        jPanel2.setBackground(new java.awt.Color(242, 242, 242));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Operaciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 18))); // NOI18N

        btnAgregar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial", 0, 16)); // NOI18N
        jButton1.setText("Descargar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnAgregar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnActualizar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditar)
                    .addComponent(btnBuscar)
                    .addComponent(btnActualizar))
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sliderGasolina.setMajorTickSpacing(1);
        sliderGasolina.setMinorTickSpacing(1);
        sliderGasolina.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderGasolinaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelOrden)
                            .addComponent(labelCedula)
                            .addComponent(labelFecha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfFecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(tfCedula, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfNumeroOrden))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfTotal, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfInsumos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(tfServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelColor)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(6, 6, 6)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfMatricula)
                            .addComponent(tfModelo)
                            .addComponent(tfColor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labelGasolina)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(143, 143, 143)
                                        .addComponent(labelContador))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sliderGasolina, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelChasis)
                                    .addComponent(labelKilometraje))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfChasis, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                                    .addComponent(tfKilometraje))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfNumeroOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelOrden))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(labelFecha)
                                .addGap(14, 14, 14)
                                .addComponent(labelCedula))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(tfFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addComponent(tfServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(3, 3, 3)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfInsumos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(58, 58, 58))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(labelContador))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(labelChasis)
                                    .addComponent(tfChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(7, 7, 7)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(labelKilometraje)
                                    .addComponent(tfKilometraje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(tfColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelColor)
                                            .addComponent(labelGasolina)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(sliderGasolina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) { 
            try(var manager = new DBManager()) {
                List<List<String>> ordenes = manager.getInfoOrdenes();
                String NumeroOrden = tabla.getValueAt(filaSeleccionada, 0).toString();
                String fecha = tabla.getValueAt(filaSeleccionada, 1).toString();
                String cedula = tabla.getValueAt(filaSeleccionada, 2).toString();
                String matricula = tabla.getValueAt(filaSeleccionada, 3).toString();
                String modelo = tabla.getValueAt(filaSeleccionada, 4).toString();
                String color = tabla.getValueAt(filaSeleccionada, 5).toString();
                String numeroChasis = tabla.getValueAt(filaSeleccionada, 6).toString();
                String kilometraje = tabla.getValueAt(filaSeleccionada, 7).toString();
                String gasolina = tabla.getValueAt(filaSeleccionada, 8).toString();
                String insumo= tabla.getValueAt(filaSeleccionada, 9).toString();
                String servicio = tabla.getValueAt(filaSeleccionada, 10).toString();
                String total = tabla.getValueAt(filaSeleccionada, 11).toString();
                String descripcion = "";
                for (List<String> orden : ordenes) {
                    if (orden.get(0).equals(NumeroOrden)) {
                        descripcion = orden.get(9);
                    }
                }

                tfCedula.setText(cedula);
                tfChasis.setText(numeroChasis);
                tfColor.setText(color);
                tfFecha.setText(fecha);
                sliderGasolina.setValue(Integer.parseInt(gasolina));
                tfInsumos.setText(cedula);
                tfKilometraje.setText(kilometraje);
                tfMatricula.setText(matricula);
                tfModelo.setText(modelo);
                tfNumeroOrden.setText(NumeroOrden);
                tfInsumos.setText(insumo);
                tfServicios.setText(servicio);
                tfTotal.setText(total);
                taDescripcion.setText(descripcion);
                
            } catch (SQLException ex){
                ex.printStackTrace();
                System.out.println("Excepción SQL: " + ex.getMessage());
                System.out.println("Estado SQL: " + ex.getSQLState());
                System.out.println("Código de error: " + ex.getErrorCode());
            }
            
            

        }
    }//GEN-LAST:event_tablaMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        tfCedula.setText("");
        tfChasis.setText("");
        tfColor.setText("");
        tfFecha.setText("");
        sliderGasolina.setValue(0);
        tfInsumos.setText("");
        tfKilometraje.setText("");
        tfMatricula.setText("");
        tfModelo.setText("");
        tfNumeroOrden.setText("");
        tfInsumos.setText("");
        tfServicios.setText("");
        tfTotal.setText("");
        taDescripcion.setText("");      
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        rellenoTabla();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        agregar();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void tfTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTotalActionPerformed
        if(!tfTotal.getText().equals("") && !tfInsumos.getText().equals("")){
            int total = Integer.parseInt(tfTotal.getText());
            int insumos = Integer.parseInt(tfInsumos.getText());
            int servicios = total - insumos;
            tfServicios.setText(String.valueOf(servicios));
        }
       
    }//GEN-LAST:event_tfTotalActionPerformed

    private void tfServiciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfServiciosActionPerformed
        if(!tfServicios.getText().equals("") && !tfInsumos.getText().equals("")){
            int servicios = Integer.parseInt(tfServicios.getText());
            int insumos = Integer.parseInt(tfInsumos.getText());
            int total = servicios + insumos;
            tfTotal.setText(String.valueOf(total));
        }
    }//GEN-LAST:event_tfServiciosActionPerformed

    private void tfTotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfTotalFocusLost

    }//GEN-LAST:event_tfTotalFocusLost

    private void tfTotalInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_tfTotalInputMethodTextChanged
   
    }//GEN-LAST:event_tfTotalInputMethodTextChanged

    private void tfTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfTotalFocusGained
     
        
    }//GEN-LAST:event_tfTotalFocusGained

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void sliderGasolinaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderGasolinaStateChanged
        labelContador.setText(String.valueOf(sliderGasolina.getValue()));
    }//GEN-LAST:event_sliderGasolinaStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            descargarOrden();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(FormOrdenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton jButton1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCedula;
    private javax.swing.JLabel labelChasis;
    private javax.swing.JLabel labelColor;
    private javax.swing.JLabel labelContador;
    private javax.swing.JLabel labelFecha;
    private javax.swing.JLabel labelGasolina;
    private javax.swing.JLabel labelKilometraje;
    private javax.swing.JLabel labelOrden;
    private javax.swing.JSlider sliderGasolina;
    private javax.swing.JTextArea taDescripcion;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField tfCedula;
    private javax.swing.JTextField tfChasis;
    private javax.swing.JTextField tfColor;
    private javax.swing.JTextField tfFecha;
    private javax.swing.JTextField tfInsumos;
    private javax.swing.JTextField tfKilometraje;
    private javax.swing.JTextField tfMatricula;
    private javax.swing.JTextField tfModelo;
    private javax.swing.JTextField tfNumeroOrden;
    private javax.swing.JTextField tfServicios;
    private javax.swing.JTextField tfTotal;
    // End of variables declaration//GEN-END:variables
}
