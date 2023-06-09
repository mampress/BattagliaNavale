/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafica;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;





/**
 *
 * @author mampreso.ivan
 */
public class FormPartita extends javax.swing.JFrame {
    DefaultListModel dlm=new DefaultListModel();
    /**
     * Creates new form FormPartita
     */
    public FormPartita() {
        initComponents();
        this.setSize(800,700);
        this.setLocationRelativeTo(null);//lo mette centrato rispetto allo schermo
        this.setIconImage(Assets.carica("/Assets/icona.png"));
        //mette tutti gli elementi invisibili
        pnlTesto.setVisible(false);
        txtNome.setVisible(false);
        pnlLista.setBounds(81,273,640,275);
        pnlLista.setVisible(false);
        pnlRegole.setBounds(27,28,745,230);
        pnlRegole.setVisible(false);
        pnlDimensione.setBounds(300,295,190,55);
        pnlDimensione.setVisible(false);
        rdbAlfabetico.setVisible(false);
        rdbPercentuale.setVisible(false);
        rdbVittorie.setVisible(false);
        rdbPartite.setVisible(false);
        rdbAlfabetico.setSelected(true);
        //a ogni rdb assegno l'attributo corrispondente
        rdbAlfabetico.setActionCommand("alfabetico");
        rdbPercentuale.setActionCommand("percentuale");
        rdbVittorie.setActionCommand("vittorie");
        rdbPartite.setActionCommand("partite");
        
        
    }
    
    public ButtonGroup getGroupStorico() {
        return groupStorico;
    }

    public JRadioButton getRdbAlfabetico() {
        return rdbAlfabetico;
    }

    public JRadioButton getRdbPartite() {
        return rdbPartite;
    }

    public JRadioButton getRdbPercentuale() {
        return rdbPercentuale;
    }

    public JRadioButton getRdbVittorie() {
        return rdbVittorie;
    }

    public JSlider getSldDimensione() {
        return sldDimensione;
    }

    public JTextArea getTxaRegole() {
        return txaRegole;
    }
    public JPanel getPnlDimensione(){
        return pnlDimensione;
    }
    public JPanel getPnlRegole(){
        return pnlRegole;
    }
    public JPanel getPnlLista(){
        return pnlLista;
    }
    
    public JPanel getPnlSfondo() {
        return pnlSfondo;
    }
    public JPanel getPnlTesto() {
        return pnlTesto;
    }

    public JTextField getTxtNome() {
        return txtNome;
    }
    public JList getLista(){
        return LIstaPartite;
    }
    public DefaultListModel getDlm(){
        return dlm;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupStorico = new javax.swing.ButtonGroup();
        layeredPaneSondo = new javax.swing.JLayeredPane();
        pnlSfondo = new javax.swing.JPanel();
        pnlTesto = new javax.swing.JPanel();
        txtNome = new javax.swing.JTextField();
        pnlLista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LIstaPartite = new javax.swing.JList<>(dlm);
        pnlRegole = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaRegole = new javax.swing.JTextArea();
        pnlDimensione = new javax.swing.JPanel();
        sldDimensione = new javax.swing.JSlider();
        rdbAlfabetico = new javax.swing.JRadioButton();
        rdbVittorie = new javax.swing.JRadioButton();
        rdbPartite = new javax.swing.JRadioButton();
        rdbPercentuale = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Battaglia Navale");
        setMinimumSize(new java.awt.Dimension(800, 700));
        setResizable(false);
        setSize(new java.awt.Dimension(800, 700));

        layeredPaneSondo.setMaximumSize(new java.awt.Dimension(800, 700));
        layeredPaneSondo.setMinimumSize(new java.awt.Dimension(800, 700));
        layeredPaneSondo.setLayout(new java.awt.BorderLayout());

        pnlSfondo.setMaximumSize(new java.awt.Dimension(800, 700));
        pnlSfondo.setMinimumSize(new java.awt.Dimension(800, 700));
        pnlSfondo.setPreferredSize(new java.awt.Dimension(800, 700));
        pnlSfondo.setRequestFocusEnabled(false);
        pnlSfondo.setLayout(null);

        pnlTesto.setBackground(new java.awt.Color(255, 255, 255));
        pnlTesto.setMaximumSize(new java.awt.Dimension(495, 50));
        pnlTesto.setMinimumSize(new java.awt.Dimension(495, 50));
        pnlTesto.setPreferredSize(new java.awt.Dimension(495, 50));

        txtNome.setBackground(new java.awt.Color(0, 0, 0));
        txtNome.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        txtNome.setForeground(new java.awt.Color(51, 153, 255));
        txtNome.setCaretColor(new java.awt.Color(51, 153, 255));
        txtNome.setMargin(new java.awt.Insets(2, 5, 5, 2));
        txtNome.setMaximumSize(new java.awt.Dimension(495, 50));
        txtNome.setMinimumSize(new java.awt.Dimension(495, 50));
        txtNome.setPreferredSize(new java.awt.Dimension(495, 50));

        javax.swing.GroupLayout pnlTestoLayout = new javax.swing.GroupLayout(pnlTesto);
        pnlTesto.setLayout(pnlTestoLayout);
        pnlTestoLayout.setHorizontalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTestoLayout.createSequentialGroup()
                .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        pnlTestoLayout.setVerticalGroup(
            pnlTestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pnlSfondo.add(pnlTesto);
        pnlTesto.setBounds(26, 104, 495, 50);

        pnlLista.setMaximumSize(new java.awt.Dimension(640, 275));
        pnlLista.setMinimumSize(new java.awt.Dimension(640, 275));
        pnlLista.setPreferredSize(new java.awt.Dimension(640, 275));

        LIstaPartite.setBackground(new java.awt.Color(0, 0, 0));
        LIstaPartite.setFont(new java.awt.Font("Courier New", 1, 24)); // NOI18N
        LIstaPartite.setForeground(new java.awt.Color(51, 153, 255));
        LIstaPartite.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        LIstaPartite.setDoubleBuffered(true);
        LIstaPartite.setMaximumSize(new java.awt.Dimension(640, 275));
        LIstaPartite.setMinimumSize(new java.awt.Dimension(640, 275));
        LIstaPartite.setPreferredSize(new java.awt.Dimension(640, 275));
        LIstaPartite.setVisibleRowCount(4);
        jScrollPane1.setViewportView(LIstaPartite);

        javax.swing.GroupLayout pnlListaLayout = new javax.swing.GroupLayout(pnlLista);
        pnlLista.setLayout(pnlListaLayout);
        pnlListaLayout.setHorizontalGroup(
            pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
        );
        pnlListaLayout.setVerticalGroup(
            pnlListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        pnlSfondo.add(pnlLista);
        pnlLista.setBounds(78, 285, 640, 275);

        pnlRegole.setMaximumSize(new java.awt.Dimension(745, 230));
        pnlRegole.setMinimumSize(new java.awt.Dimension(745, 230));
        pnlRegole.setPreferredSize(new java.awt.Dimension(745, 230));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerifyInputWhenFocusTarget(false);

        txaRegole.setEditable(false);
        txaRegole.setBackground(new java.awt.Color(0, 0, 0));
        txaRegole.setColumns(66);
        txaRegole.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        txaRegole.setForeground(new java.awt.Color(51, 153, 255));
        txaRegole.setRows(48);
        txaRegole.setWrapStyleWord(true);
        txaRegole.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txaRegole.setDragEnabled(true);
        txaRegole.setMaximumSize(new java.awt.Dimension(745, 230));
        txaRegole.setMinimumSize(new java.awt.Dimension(745, 230));
        jScrollPane2.setViewportView(txaRegole);

        javax.swing.GroupLayout pnlRegoleLayout = new javax.swing.GroupLayout(pnlRegole);
        pnlRegole.setLayout(pnlRegoleLayout);
        pnlRegoleLayout.setHorizontalGroup(
            pnlRegoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(pnlRegoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE))
        );
        pnlRegoleLayout.setVerticalGroup(
            pnlRegoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 230, Short.MAX_VALUE)
            .addGroup(pnlRegoleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
        );

        pnlSfondo.add(pnlRegole);
        pnlRegole.setBounds(0, 100, 800, 230);

        pnlDimensione.setMaximumSize(new java.awt.Dimension(190, 60));
        pnlDimensione.setMinimumSize(new java.awt.Dimension(190, 60));
        pnlDimensione.setPreferredSize(new java.awt.Dimension(190, 60));

        sldDimensione.setBackground(new java.awt.Color(0, 0, 0));
        sldDimensione.setFont(new java.awt.Font("Courier New", 1, 11)); // NOI18N
        sldDimensione.setForeground(new java.awt.Color(51, 153, 255));
        sldDimensione.setMajorTickSpacing(2);
        sldDimensione.setMaximum(16);
        sldDimensione.setMinimum(8);
        sldDimensione.setMinorTickSpacing(1);
        sldDimensione.setPaintLabels(true);
        sldDimensione.setPaintTicks(true);
        sldDimensione.setSnapToTicks(true);
        sldDimensione.setToolTipText("");
        sldDimensione.setValue(10);
        sldDimensione.setFocusable(false);
        sldDimensione.setMaximumSize(new java.awt.Dimension(190, 60));
        sldDimensione.setMinimumSize(new java.awt.Dimension(190, 60));
        sldDimensione.setPreferredSize(new java.awt.Dimension(190, 60));
        sldDimensione.setValueIsAdjusting(true);

        javax.swing.GroupLayout pnlDimensioneLayout = new javax.swing.GroupLayout(pnlDimensione);
        pnlDimensione.setLayout(pnlDimensioneLayout);
        pnlDimensioneLayout.setHorizontalGroup(
            pnlDimensioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDimensioneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sldDimensione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnlDimensioneLayout.setVerticalGroup(
            pnlDimensioneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDimensioneLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(sldDimensione, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlSfondo.add(pnlDimensione);
        pnlDimensione.setBounds(20, 580, 190, 60);

        groupStorico.add(rdbAlfabetico);
        rdbAlfabetico.setText("Ordine alfabetico");
        pnlSfondo.add(rdbAlfabetico);
        rdbAlfabetico.setBounds(290, 600, 120, 23);

        groupStorico.add(rdbVittorie);
        rdbVittorie.setText("Per numero di vittorie");
        pnlSfondo.add(rdbVittorie);
        rdbVittorie.setBounds(550, 600, 150, 23);

        groupStorico.add(rdbPartite);
        rdbPartite.setText("Per numero di partite giocate");
        pnlSfondo.add(rdbPartite);
        rdbPartite.setBounds(290, 630, 170, 23);

        groupStorico.add(rdbPercentuale);
        rdbPercentuale.setText("Per percentuale di vittorie");
        pnlSfondo.add(rdbPercentuale);
        rdbPercentuale.setBounds(550, 630, 160, 23);

        layeredPaneSondo.setLayer(pnlSfondo, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPaneSondo.add(pnlSfondo, java.awt.BorderLayout.CENTER);

        getContentPane().add(layeredPaneSondo, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> LIstaPartite;
    private javax.swing.ButtonGroup groupStorico;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLayeredPane layeredPaneSondo;
    private javax.swing.JPanel pnlDimensione;
    private javax.swing.JPanel pnlLista;
    private javax.swing.JPanel pnlRegole;
    private javax.swing.JPanel pnlSfondo;
    private javax.swing.JPanel pnlTesto;
    private javax.swing.JRadioButton rdbAlfabetico;
    private javax.swing.JRadioButton rdbPartite;
    private javax.swing.JRadioButton rdbPercentuale;
    private javax.swing.JRadioButton rdbVittorie;
    private javax.swing.JSlider sldDimensione;
    private javax.swing.JTextArea txaRegole;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
