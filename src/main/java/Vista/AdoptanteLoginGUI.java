package Vista;
import Controlador.AdoptanteControlador;
import Modelo.Adoptante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdoptanteLoginGUI extends JFrame {
    private JTextField txtId;
    private JTextField txtCorreo;
    private JButton btnLogin;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private AdoptanteControlador adoptanteControlador;
    private JFrame ventanaAnterior;

    public AdoptanteLoginGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Login Adoptante");
        this.ventanaAnterior = ventanaAnterior;
        adoptanteControlador = new AdoptanteControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField(20);
        panel.add(txtId);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo);

        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin);

        btnRegistrar = new JButton("Registrarse");
        panel.add(btnRegistrar);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);
        panel.add(new JLabel("")); // Espacio para alineación

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String correo = txtCorreo.getText().trim();

                if (id.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(AdoptanteLoginGUI.this,
                            "Por favor, complete ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Adoptante adoptante = adoptanteControlador.iniciarSesion(id, correo);
                if (adoptante != null) {
                    if (adoptante.isPermisoAdopcion()) {
                        JOptionPane.showMessageDialog(AdoptanteLoginGUI.this,
                                "¡Bienvenido, Adoptante " + adoptante.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        new AdoptanteGUI(adoptante, AdoptanteLoginGUI.this).setVisible(true);
                        AdoptanteLoginGUI.this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(AdoptanteLoginGUI.this,
                                "Sus credenciales son correctas, pero aún no tiene permiso para adoptar. Por favor, contacte a un veterinario.", "Permiso Pendiente", JOptionPane.WARNING_MESSAGE);
                        // No cierra la ventana de login, o puede volver al menú principal
                    }
                } else {
                    JOptionPane.showMessageDialog(AdoptanteLoginGUI.this,
                            "Credenciales incorrectas. Verifique su ID y correo.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            // Abrir una nueva ventana de registro para adoptantes
            new AdoptanteRegistroGUI(this).setVisible(true);
            this.setVisible(false); // Ocultar esta ventana mientras se registra
        });

        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
    }
}
