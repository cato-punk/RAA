package Vista;

import Controlador.RescatistaControlador;
import Modelo.Rescatista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RescatistaLoginGUI extends JFrame {
    private JTextField txtId;
    private JTextField txtCorreo;
    private JButton btnLogin;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private RescatistaControlador rescatistaControlador;
    private JFrame ventanaAnterior;

    public RescatistaLoginGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Login Rescatista");
        this.ventanaAnterior = ventanaAnterior;
        rescatistaControlador = new RescatistaControlador();
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
                    JOptionPane.showMessageDialog(RescatistaLoginGUI.this,
                            "Por favor, complete ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Rescatista rescatista = rescatistaControlador.iniciarSesion(id, correo);
                if (rescatista != null) {
                    JOptionPane.showMessageDialog(RescatistaLoginGUI.this,
                            "¡Bienvenido, Rescatista " + rescatista.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    new RescatistaGUI(rescatista, RescatistaLoginGUI.this).setVisible(true);
                    RescatistaLoginGUI.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(RescatistaLoginGUI.this,
                            "Credenciales incorrectas. Verifique su ID y correo.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            // Abrir una nueva ventana de registro para rescatistas
            new RescatistaRegistroGUI(this).setVisible(true);
            this.setVisible(false); // Ocultar esta ventana mientras se registra
        });

        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
    }
}