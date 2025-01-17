/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package bookbuddy;

import java.awt.CardLayout;
import java.util.HashMap;
import javax.swing.SwingUtilities;

/**
 * Author: Rosie and Anita
 * First panel where user searches
 * for a book
 * Date: June 6, 2024
 */
public class SearchPanel extends javax.swing.JPanel {

    
    /**
     * Creates new form SearchPanel
     */
    public static boolean searchDone = false;
    public static HashMap bookInfo;

    //-----------------------------------------------------------------------------
    
    public SearchPanel() {
        initComponents();
    }
    
    //-----------------------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcomeLabel = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        outputLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 57, 85));

        welcomeLabel.setFont(new java.awt.Font("Georgia", 0, 48)); // NOI18N
        welcomeLabel.setForeground(new java.awt.Color(251, 246, 229));
        welcomeLabel.setText("WELCOME TO BOOKBUDDY");

        searchTextField.setBackground(new java.awt.Color(251, 246, 229));
        searchTextField.setFont(new java.awt.Font("Georgia", 0, 18)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(153, 57, 85));

        searchButton.setBackground(new java.awt.Color(240, 150, 197));
        searchButton.setFont(new java.awt.Font("Georgia", 0, 12)); // NOI18N
        searchButton.setForeground(new java.awt.Color(251, 246, 229));
        searchButton.setText("SEARCH");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        outputLabel.setFont(new java.awt.Font("Georgia", 0, 36)); // NOI18N
        outputLabel.setForeground(new java.awt.Color(251, 246, 229));
        outputLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        outputLabel.setText("ENTER ISBN OR TITLE");
        outputLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(128, 128, 128)
                                .addComponent(welcomeLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(164, 164, 164)
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(404, 404, 404)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 122, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addComponent(welcomeLabel)
                .addGap(39, 39, 39)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(outputLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(170, 170, 170))
        );
    }// </editor-fold>//GEN-END:initComponents

    //Rosie
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        searchDone = true;
        //store input string
        String input = searchTextField.getText().trim();
        
        //check if entered input is ISBN or title
        if(isISBN(input)){
            //search by ISBN 
            bookInfo = BookManager.findByISBN(input);
        } else{
            //search by title
            bookInfo = BookManager.findByTitle(input);
        }
        
        //determine if book is found in directory
        if (!bookInfo.isEmpty()) {
            //book is found, switch to next panel
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            CardLayout c = (CardLayout) topFrame.bgPanel.getLayout();
            c.show(topFrame.bgPanel, "card2");
        } else {
            outputLabel.setText("BOOK NOT FOUND. TRY AGAIN.");
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    /**
     * Rosie
     * Determine if entered String
     * is an ISBN value or not
     * @param input String
     * @return true if is an ISBN#, false otherwise
     */
    public boolean isISBN(String input){
        //google API only searches isbn-10 & isbn-13
        if (input.length() == 10 || input.length() == 13){
            //ensure all chars are digits
            for (int i = 0; i < input.length(); i++){
                char c = input.charAt(i);
                if (!(Character.isDigit(c))){
                    return false;
                }                
            }
            return true;
        }
        return false;
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel outputLabel;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
