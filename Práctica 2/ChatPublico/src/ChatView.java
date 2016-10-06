
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ChatView extends javax.swing.JFrame {

    private Cliente cliente;

    public ChatView() {
        initComponents();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                desconectar();
            }
        });
    }
    
    public void showView () {
        this.setVisible(true);
    }
    
    public void setNombre (String nombre) {
        nombreUsuario.setText("Bienvenido, " + nombre);
        this.setTitle("Oh, my chat! - " + nombre);
    }

    public void setCliente (Cliente cliente) {
        this.cliente = cliente;
        this.setNombre(cliente.getNombre());
    }
    
    public void enviarMensaje () {
        if (!"".equals(mensaje.getText())) { // Si el mensaje está vacío no tiene sentido enviarse
            cliente.difundirMensaje(mensaje.getText());
            mensaje.setText("");
        }
    }
    
    public void desconectar () {
        cliente.desconectar();
        System.exit(0);
    }
    
    public void mostrarMensaje (String nombre, String mensajeRecibido) {
        historial.setEditable(true);
        if (cliente.getNombreServidor().equals(nombre)) { // Si el mensaje es del servidor, no aparece el nombre y el mensaje aparece en naranaja
            append(new SimpleDateFormat("HH:mm").format(new Date()) + " ", Color.LIGHT_GRAY);
            append(mensajeRecibido + "\n", Color.ORANGE);
        } else if (cliente.getNombre().equals(nombre)) { // Si el mensaje es del mismo autor, su nombre aparece verde
            append(new SimpleDateFormat("HH:mm").format(new Date()) + " ", Color.LIGHT_GRAY);
            append(nombre + ": ", Color.GREEN);
            append(mensajeRecibido + "\n", Color.BLACK);
        } else { // Si es un mensaje de cualquier otro usuario, el nombre aparece en azul
            append(new SimpleDateFormat("HH:mm").format(new Date()) + " ", Color.LIGHT_GRAY);
            append(nombre + ": ", Color.BLUE);
            append(mensajeRecibido + "\n", Color.BLACK);
        }
        historial.setEditable(false);
    }

    private void append (String mensaje, Color color) {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        AttributeSet attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.FontFamily, "Lucida Console");
        attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int longitud = historial.getDocument().getLength();
        historial.setCaretPosition(longitud);
        historial.setCharacterAttributes(attributeSet, false);
        historial.replaceSelection(mensaje);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        mensaje = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        historial = new javax.swing.JTextPane();
        buttonEnviar = new javax.swing.JButton();
        buttonDesconectar = new javax.swing.JButton();
        nombreUsuario = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mensaje.setColumns(20);
        mensaje.setRows(5);
        mensaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                mensajeKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(mensaje);

        historial.setEditable(false);
        jScrollPane2.setViewportView(historial);

        buttonEnviar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        buttonEnviar.setText("Enviar");
        buttonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEnviarActionPerformed(evt);
            }
        });

        buttonDesconectar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        buttonDesconectar.setText("Desconectar");
        buttonDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDesconectarActionPerformed(evt);
            }
        });

        nombreUsuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nombreUsuario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nombreUsuario.setText("Bienvenido, [username]");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Chat Público");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(buttonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(buttonDesconectar, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(5, 5, 5)
                        .addComponent(nombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(nombreUsuario)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buttonDesconectar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mensajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mensajeKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar() == KeyEvent.VK_ENTER) { // Se ha pulsado INTRO
            if (evt.isShiftDown()) { // Si se pulsa shift se captura el salto de línea
                mensaje.append("\n");
            } else { // En caso contrario se envía el mensaje
                evt.consume();
                enviarMensaje();
            }
        }
    }//GEN-LAST:event_mensajeKeyPressed

    private void buttonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEnviarActionPerformed
        // TODO add your handling code here:
        enviarMensaje();
    }//GEN-LAST:event_buttonEnviarActionPerformed

    private void buttonDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDesconectarActionPerformed
        // TODO add your handling code here:
        desconectar();
    }//GEN-LAST:event_buttonDesconectarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonDesconectar;
    private javax.swing.JButton buttonEnviar;
    private javax.swing.JTextPane historial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea mensaje;
    private javax.swing.JLabel nombreUsuario;
    // End of variables declaration//GEN-END:variables

}
