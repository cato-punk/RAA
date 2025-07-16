package Launcher;

import Vista.MenuPrincipalGUI;

import javax.swing.SwingUtilities;

public class Inicio {
    public static void main(String[] args) {
        // Asegurarse de que la GUI se ejecute en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipalGUI().setVisible(true);
        });
    }
}
