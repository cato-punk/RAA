package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipalGUI extends JFrame {

    public MenuPrincipalGUI() {
        super("Red Ayuda Animal - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Centrar la ventana

        //  botones verticalmente
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen interno

        // Título
        JLabel titulo = new JLabel("Bienvenido a Red Ayuda Animal");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el texto
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30))); // Espacio

        // Botones de roles
        JButton btnAdmin = crearBoton("Administrador");
        JButton btnVeterinario = crearBoton("Veterinario");
        JButton btnRescatista = crearBoton("Rescatista");
        JButton btnAdoptante = crearBoton("Adoptante");

        panelPrincipal.add(btnAdmin);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnVeterinario);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnRescatista);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnAdoptante);

        // Añadir listeners a los botones
        btnAdmin.addActionListener(e -> abrirVentanaLogin("Admin"));
        btnVeterinario.addActionListener(e -> abrirVentanaLogin("Veterinario"));
        btnRescatista.addActionListener(e -> abrirVentanaLogin("Rescatista"));
        btnAdoptante.addActionListener(e -> abrirVentanaLogin("Adoptante"));

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setMaximumSize(new Dimension(200, 40)); // Tamaño fijo para los botones
        boton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el botón
        return boton;
    }

    private void abrirVentanaLogin(String tipoUsuario) {
        // Ocultar la ventana actual
        this.setVisible(false);

        // Crear y mostrar la ventana de login correspondiente
        // Aquí podrías tener una clase LoginGUI genérica o específica para cada tipo
        switch (tipoUsuario) {
            case "Admin":
                new AdminLoginGUI(this).setVisible(true); // Pasar referencia del MenuPrincipalGUI
                break;
            case "Veterinario":
                new VeterinarioLoginGUI(this).setVisible(true);
                break;
            case "Rescatista":
                new RescatistaLoginGUI(this).setVisible(true);
                break;
            case "Adoptante":
                new AdoptanteLoginGUI(this).setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuario no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
                this.setVisible(true); // Volver a mostrar si hay error
                break;
        }
    }

    public static void main(String[] args) {
        // Asegurarse de que la GUI se ejecute en el Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipalGUI().setVisible(true);
        });
    }
}