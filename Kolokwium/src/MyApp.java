// 2N

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyApp {
    private JFrame frame;
    private TextField textfield;

    // Konstruktor
    MyApp(String[] args) {
        frame = new JFrame("Możesz dodawać prostokąty przyciskami na panelu bocznym lub ręcznie");
    }

    // Metoda do tworzenia i wyświetlania GUI
    public void show() {
        // Ustawienia głównego okna
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Zamyka aplikację po kliknięciu "X"
        frame.setSize(800, 600); // Ustawienie rozmiaru okna
        frame.setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        // Panel boczny
        JPanel sidePanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0; // Pierwsza kolumna
        gbc.gridy = 0; // Pierwszy wiersz
        gbc.weightx = 1; // Waga w poziomie
        gbc.weighty = 1; // Waga w pionie (taka sama dla obu paneli)
        gbc.fill = GridBagConstraints.BOTH; // Wypełnienie zarówno poziome, jak i pionowe
        frame.add(sidePanel, gbc);

        // Główny panel rysujący
        DrawingPanel drawingPanel = new DrawingPanel();
        gbc.gridx = 1; // Druga kolumna
        gbc.weightx = 3; // Waga w poziomie
        frame.add(drawingPanel, gbc);

        // Przycisk do dodawania prostokątów
        JButton addButtonSmall = new JButton("Dodaj mały prostokąt");
        addButtonSmall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.addFigSmall();
            }
        });

        // Przycisk do dodawania prostokątów
        JButton addButtonBig = new JButton("Dodaj duży prostokąt");
        addButtonBig.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.addFigBig();
            }
        });

        String name = JOptionPane.showInputDialog(frame, "Wpisz tu cokolwiek jeśli chcesz początkowy prostokąt");
        JTextField textField = new JTextField();
        textField.setText("W okienku wpisałeś: " + name);
        textField.setEditable(false);
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawingPanel.addFigBig();
            }
        });
        if(name!= null) {
            drawingPanel.addFigBig();
        }

        GridBagConstraints gbcside = new GridBagConstraints();

        gbcside.gridx = 0; // Pierwsza kolumna
        gbcside.gridy = 0; // Pierwszy wiersz
        sidePanel.add(textField, gbcside);


        gbcside.gridy = 1;
        sidePanel.add(addButtonBig, gbcside);

        gbcside.gridy = 2; // Druga kolumna
        sidePanel.add(addButtonSmall, gbcside);

        // Wyświetlenie okna
        frame.setVisible(true);
    }

    // Metoda główna
    public static void main(final String[] args) {
        // Uruchamiamy GUI na wątku EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyApp(args).show();
            }
        });
    }
}


