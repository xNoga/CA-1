/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import echoclient.ClientObserver;
import echoserver.ClientHandlerGUI;
import echoclient.EchoClient;
import echoserver.EchoServer;
import java.io.IOException;
import static java.lang.Math.E;
import static java.lang.StrictMath.E;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;

/**
 *
 * @author kristoffernoga
 */
public class ChatGUI extends javax.swing.JFrame implements ClientObserver {

    static EchoClient ec;
    Lock lock = new ReentrantLock();

    public static String allIp;
    public static int allPort;

    DefaultListModel model1 = new DefaultListModel();

    public ChatGUI() {
        initComponents();
        userList.setModel(model1);
        userList.setSelectionModel(new DefaultListSelectionModel()
                {
                    public void setSelectionInterval(int index0, int index1){
                        if (super.isSelectedIndex(index0)) {
                            super.removeSelectionInterval(index0, index1);
                        } else {
                            super.addSelectionInterval(index0, index1);
                        }
                    }
                }
        
        
        );
        model1.addElement("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        getIPTextField = new javax.swing.JTextField();
        getPortTextField = new javax.swing.JTextField();
        OKConnectButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        userNameTextField = new javax.swing.JTextField();
        jProgressBar1 = new javax.swing.JProgressBar();
        SendButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        SendTextField = new javax.swing.JTextField();
        ConnectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        RecieveTextField = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Please enter IP adress and port number below:");

        getIPTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                getIPTextFieldFocusGained(evt);
            }
        });

        getPortTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getPortTextFieldActionPerformed(evt);
            }
        });

        OKConnectButton.setText("OK");
        OKConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKConnectButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("IP:");

        jLabel3.setText("Port:");

        jLabel4.setText("Username");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(jDialog1Layout.createSequentialGroup()
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(51, 51, 51)
                        .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(getPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(getIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(38, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDialog1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(OKConnectButton))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getIPTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(userNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OKConnectButton))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SendButton.setText("Send");
        SendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        SendTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendTextFieldActionPerformed(evt);
            }
        });

        ConnectButton.setText("Connect");
        ConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectButtonActionPerformed(evt);
            }
        });

        RecieveTextField.setColumns(20);
        RecieveTextField.setRows(5);
        jScrollPane1.setViewportView(RecieveTextField);

        userList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(userList);

        jButton1.setText("Logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SendTextField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(ConnectButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(CloseButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ConnectButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CloseButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(SendTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        if (allPort >= 1) {
            ec.send("logout#");
        }
        
        System.exit(0);
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void SendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendButtonActionPerformed

        String message = SendTextField.getText();
        List<String> receivers = userList.getSelectedValuesList();

        String finalRec = "";
//        for (String s : receivers) {
//            finalRec = finalRec + s + ",";
//        }
        for (int index = 0; index < receivers.size(); index++) {
            String currElement = receivers.get(index);
            if (index == receivers.size() - 1) {
                finalRec = finalRec + currElement;
            } else {
                finalRec = finalRec + currElement + ",";
            }
        }

        System.out.println("finalRec: " + finalRec);
        if (finalRec.equalsIgnoreCase("*,")) {
            finalRec = "*";
        }
        if (!finalRec.equalsIgnoreCase("")) {
            ec.send("SEND#" + finalRec + "#" + message);
            SendTextField.setText("");
            RecieveTextField.append("<You> " + message + "\n");
        }


    }//GEN-LAST:event_SendButtonActionPerformed

    private void SendTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SendTextFieldActionPerformed

    private void ConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectButtonActionPerformed
        jDialog1.setVisible(true);
        jDialog1.setSize(320, 220);
        getIPTextField.setText("cph-js226.cloudapp.net");
        getPortTextField.setText("9999");
        userNameTextField.setText("Kris");
    }//GEN-LAST:event_ConnectButtonActionPerformed

    private void getPortTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getPortTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getPortTextFieldActionPerformed

    private void OKConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKConnectButtonActionPerformed
        allIp = getIPTextField.getText();
        allPort = Integer.parseInt(getPortTextField.getText());

        try {

            ec = new EchoClient();
            ec.connect(allIp, allPort);
            ec.registerClientObserver(this);
            //ec.addObserver(this);
            new Thread(ec).start();
            String username = userNameTextField.getText();
            ec.sendUser(username);
            //ec.send("USER#"+username);

        } catch (IOException ex) {
            Logger.getLogger(ChatGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        jDialog1.dispose();
    }//GEN-LAST:event_OKConnectButtonActionPerformed

    private void getIPTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_getIPTextFieldFocusGained

    }//GEN-LAST:event_getIPTextFieldFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        model1.removeAllElements();
        ec.send("logout#");
        allPort = 0;
        RecieveTextField.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton ConnectButton;
    private javax.swing.JButton OKConnectButton;
    private javax.swing.JTextArea RecieveTextField;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextField SendTextField;
    private javax.swing.JTextField getIPTextField;
    private javax.swing.JTextField getPortTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> userList;
    private javax.swing.JTextField userNameTextField;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateList(String users) {
        String[] userArray = users.trim().split("#");
        String receivers = userArray[1];
        String[] receiverList = receivers.trim().split(",");
        System.out.println(Arrays.toString(receiverList));
        model1.removeAllElements();
        model1.addElement("*");
        for (String s : receiverList) {
            model1.addElement(s);
        }

        //SendTextField.setText(users);
    }

    @Override
    public void sendMessage(String msg) {
        String[] msgArray = msg.trim().split("#");
        String user = msgArray[1];
        String message = msgArray[2];
        //String[] receiverList = receivers.trim().split(",");
        RecieveTextField.append("<" + user + "> " + message + "\n");
    }

//    @Override
//    public void update(Observable o, Object arg) {
//        
//        String message = (String) arg;
//        if (message.substring(0, 6).equalsIgnoreCase("users#")) {
//            model1.addElement(message);
//        }
//        
//         RecieveTextField.append(message + "\n");
//        
//
//    }
}
