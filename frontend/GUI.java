package frontend;

import java.awt.BorderLayout;
import java.awt.Insets;
import javax.swing.*;
import java.awt.event.*;

import Backend.*;

public class GUI implements ActionListener {

    private static JButton proceed;
    private static JTextPane inpuTextPane;
    private static JTextArea outpuTextPane;
    private static ButtonGroup algoButtonGroup;
    private static ButtonGroup methodButtonGroup;
    private static JRadioButton encryButton;
    private static JRadioButton decryButton;
    private static JRadioButton rsaButton;
    private static JRadioButton elgButton;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();

        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ENCRYPTION/DECRYPTION TOOL");
        frame.add(panel);
        panel.setLayout(null);
        // panel.setBackground(Color.GRAY);
        // frame.setBackground(Color.GRAY);
        JLabel heading = new JLabel("ENCRYPTION/DECRYPTION TOOL");
        heading.setBounds(175, 50, 300, 25);
        panel.add(heading);

        JLabel algo = new JLabel("Algorithm");
        algo.setBounds(10, 110, 85, 25);
        panel.add(algo);

        algoButtonGroup = new ButtonGroup();
        rsaButton = new JRadioButton("RSA");
        rsaButton.setBounds(100, 110, 85, 25);
        elgButton = new JRadioButton("ELGAMAL");
        elgButton.setBounds(250, 110, 85, 25);
        algoButtonGroup.add(elgButton);
        algoButtonGroup.add(rsaButton);

        panel.add(rsaButton);
        panel.add(elgButton);

        JLabel method = new JLabel("Method");
        method.setBounds(10, 160, 85, 25);
        panel.add(method);

        methodButtonGroup = new ButtonGroup();
        encryButton = new JRadioButton("Encryption");
        encryButton.setBounds(100, 160, 150, 25);
        decryButton = new JRadioButton("Decryption");
        decryButton.setBounds(250, 160, 150, 25);
        methodButtonGroup.add(encryButton);
        methodButtonGroup.add(decryButton);

        panel.add(encryButton);
        panel.add(decryButton);

        JLabel inputJLabel = new JLabel("Your input");
        inputJLabel.setBounds(10, 210, 85, 25);
        panel.add(inputJLabel);

        inpuTextPane = new JTextPane();
        inpuTextPane.setText("Enter your message here...");
        JScrollPane inpJSP = new JScrollPane(inpuTextPane);
        inpJSP.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        inpJSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inpJSP.setBounds(100, 210, 400, 200);
        panel.add(inpJSP, BorderLayout.CENTER);

        JLabel outJLabel = new JLabel("Output");
        outJLabel.setBounds(10, 450, 85, 25);
        panel.add(outJLabel);

        outpuTextPane = new JTextArea();
        outpuTextPane.setText("");
        outpuTextPane.setLineWrap(true);
        outpuTextPane.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane outJSP = new JScrollPane(outpuTextPane);
        outJSP.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outJSP.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outJSP.setBounds(100, 450, 400, 200);
        panel.add(outJSP, BorderLayout.CENTER);

        proceed = new JButton("Proceed");
        proceed.setBounds(250, 700, 100, 25);
        proceed.addActionListener(new GUI());
        panel.add(proceed);

        frame.setResizable(false);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Enumeration<AbstractButton> allMethodButtons =
        // methodButtonGroup.getElements();
        // Enumeration<AbstractButton> allAlgoButtons = algoButtonGroup.getElements();

        // if (e.getSource().equals(proceed)) {
        // String input = inpuTextPane.getText();
        // // System.out.println(outpuTextPane.getText());
        // while (allMethodButtons.hasMoreElements() ||
        // allAlgoButtons.hasMoreElements()) {
        // JRadioButton temp = (JRadioButton) allAlgoButtons.nextElement();
        // JRadioButton temp2 = (JRadioButton) allMethodButtons.nextElement();

        // if (temp.isSelected() && temp2.isSelected()) {
        // System.out.println("jgjgjvjh");
        // if (temp.getText().equals("RSA") && temp2.getText().equals("Encryption")) {
        // System.out.println("Algorithm RSA selected for encryption");

        // String encryOutput = RSA.RSACrypto(input);
        // text.setText(encryOutput);
        // System.out.println(encryOutput);
        // } else if (temp.getText().equals("RSA") &&
        // temp2.getText().equals("Decryption")) {
        // System.out.println("Algorithm RSA selected for decryption");
        // } else if (temp.getText().equals("ELGAMAL") &&
        // temp2.getText().equals("Encryption")) {
        // System.out.println("Algorithm ELGAMAL selected for encryption");
        // } else if (temp.getText().equals("ELGAMAL") &&
        // temp2.getText().equals("Decryption")) {
        // System.out.println("Algorithm ELGAMAL selected for decryption");
        // }

        // }
        if (e.getSource().equals(proceed)) {
            String input = inpuTextPane.getText();

            if (rsaButton.isSelected() && encryButton.isSelected()) {
                System.out.println("Algorithm RSA selected for encryption");
                String encryptMsg = RSA.RSAEncryption(input);
                outpuTextPane.setText(encryptMsg);

            } else if (rsaButton.isSelected() && decryButton.isSelected()) {
                System.out.println("Algorithm RSA selected for decryption");
                String decryMsg = RSA.RSADecryption(input);
                outpuTextPane.setText(decryMsg);

            } else if (elgButton.isSelected() && encryButton.isSelected()) {

                String ELG_EncryptMsg = elGamal.ElgamalEncryption(input);
                outpuTextPane.setText(ELG_EncryptMsg);

            } else if (elgButton.isSelected() && decryButton.isSelected()) {

                String ELG_DecryptMsg = elGamal.ElgamalDecryption(input);
                outpuTextPane.setText(ELG_DecryptMsg);

            }
        }

    }
}
// }
