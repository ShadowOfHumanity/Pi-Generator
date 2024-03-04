// an implementation of the Spigot Algorithm for the Digits of Pi by Rabinowitz and Wagon

// with improvements suggested by Arndt and Haenel


import javax.swing.*;

import javax.swing.text.DefaultCaret;

import java.awt.*;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import java.util.logging.Level;

import java.util.logging.Logger;


public class PiDigitsGUI extends JFrame implements ActionListener {


    private JTextArea textArea;

    private JTextField digitsField;

    private JButton calculateButton;


    public PiDigitsGUI() {

        setTitle("Pi Digits Calculator");

        setSize(800, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Create the text area with a scroll bar

        textArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(textArea);


        // Set the scroll bar to automatically scroll to the bottom as new text is added

        DefaultCaret caret = (DefaultCaret)textArea.getCaret();

        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


        // Wrap text in textarea                                                

        textArea.setLineWrap(true);                                         

        textArea.setFont(new Font("Arial", Font.PLAIN, 20));

        textArea.setText("Digits OF PI will generate here");


        // Create the digits field and calculate button

        digitsField = new JTextField();

        digitsField.setColumns(10);

        calculateButton = new JButton("Calculate");

        calculateButton.addActionListener(this);


        // Create a panel for the digits field and calculate button

        JPanel inputPanel = new JPanel(new FlowLayout());

        inputPanel.add(new JLabel("Digits: "));

        inputPanel.add(digitsField);

        inputPanel.add(calculateButton);


        // Add the input panel and scroll pane to the frame

        add(inputPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);


        setVisible(true);

    }


    public void actionPerformed(ActionEvent e) {

        // Get the number of digits from the digits field

        int digits = Integer.parseInt(digitsField.getText());


        // Calculate pi to the specified number of digits and display the result in the text area

        Thread piThread = new Thread(new Runnable(){                            

                    @Override                                                          

                    public void run() {                                                 

                        calculatePi(digits);

                    }                                                                   

                });                                                                     


        piThread.start();                                                       

    }


    private void calculatePi(int digits) {

        boolean loop = true;

        final int DIGITS = digits;


        int nrTwos = (10*DIGITS)/3;


        int A[] = new int[nrTwos + 1];


        textArea.setText(null);


        //while (loop){ // loop was made for demonstrative purposes

        int prov = 0, nines = 0;


        for (int i = 1; i <= nrTwos; i++){ // this makes array all 2

            A[i]=2;

            //System.out.println(i + ": " + A[i]);

        }


        for (int index = 0; index < DIGITS; index++){


            for (int i = 1; i <= nrTwos; i++) {

                A[i] *= 10;

            }


            for (int i = nrTwos; i > 1; i--){


                int divisor = 2*i-1;

                int q = A[i] / divisor; // Q is how much the number goes in

                int r = A[i] % divisor; //remainder


                A[i] = r;

                A[i-1] += q*(i-1);

            }


            //for (int i = nrTwos; i > 0; i--){

            //    System.out.println(A[i]);

            //}


            int q = A[1] / 10;

            A[1] = A[1] % 10;


            if (q < 9) {

                textArea.append(String.valueOf(prov));

                for (int i = 0; i < nines; i++){

                    textArea.append("9");

                }

                prov = q;

                nines = 0;

            }else if (q == 9){

                nines++;

            } else {

                prov++;

                textArea.append(String.valueOf(prov));

                prov = 0;

                nines = 0;

            }

            if (index == 1){

                textArea.append(".");

            }


            try {                                                               

                Thread.sleep(20);          // control speed of generation.                             

            }                                                                  

            catch (InterruptedException ex) {}                                  // so program dont go sleep forever

        }


        try

        {

            Thread.sleep(5000); // for a 5s delay if the loop is on

        }

        catch (InterruptedException ex)

        {}

        // sets text to nothing and repeats


        //}

    }

    public static void main(String[] args) {

        new PiDigitsGUI();

    }

}   