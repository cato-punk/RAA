package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Veterinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VeterinarioLoginGUI extends JFrame {
    private JTextField txtId;
    private JTextField txtCorreo;
    private JButton btnLogin;
    private JButton btnVolver;
    private VeterinarioControlador veterinarioControlador;
    private JFrame ventanaAnterior;

    public VeterinarioLoginGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Login Veterinario");
        this.ventanaAnterior = ventanaAnterior;
        veterinarioControlador = new VeterinarioControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField(20);
        panel.add(txtId);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo);

        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String correo = txtCorreo.getText().trim();

                if (id.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(VeterinarioLoginGUI.this,
                            "Por favor, complete ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Veterinario veterinario = veterinarioControlador.iniciarSesion(correo, id);
                if (veterinario != null) {
                    JOptionPane.showMessageDialog(VeterinarioLoginGUI.this,
                            "¡Bienvenido, Veterinario " + veterinario.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    new VeterinarioGUI(veterinario, VeterinarioLoginGUI.this).setVisible(true);
                    VeterinarioLoginGUI.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(VeterinarioLoginGUI.this,
                            "Credenciales incorrectas. Verifique su ID y correo.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
    }
}
