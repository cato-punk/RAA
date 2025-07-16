package Vista;

import Controlador.AdminControlador;
import Modelo.Admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginGUI extends JFrame {
    private JTextField txtId;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JButton btnLogin;
    private JButton btnVolver;
    private AdminControlador adminControlador;
    private JFrame ventanaAnterior; // Para volver al menú principal

    public AdminLoginGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Login Administrador");
        this.ventanaAnterior = ventanaAnterior;
        adminControlador = new AdminControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar solo esta ventana
        setSize(400, 250);
        setLocationRelativeTo(null); // Centrar la ventana

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10)); // Filas, columnas, hgap, vgap
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("ID:"));
        txtId = new JTextField(20);
        panel.add(txtId);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo);

        panel.add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField(20);
        panel.add(txtContrasena);

        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin);

        btnVolver = new JButton("Volver");
        panel.add(btnVolver);

        add(panel, BorderLayout.CENTER);

        // Acción del botón de login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String correo = txtCorreo.getText().trim();
                String contrasena = new String(txtContrasena.getPassword()); // Obtener contraseña de forma segura

                if (id.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminLoginGUI.this,
                            "Por favor, complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Admin admin = adminControlador.iniciarSesion(correo, id, contrasena);
                if (admin != null) {
                    JOptionPane.showMessageDialog(AdminLoginGUI.this,
                            "¡Bienvenido, Administrador " + admin.getNombre() + "!", "Inicio de Sesión Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    // Abrir la ventana principal del administrador
                    new AdminGUI(admin, AdminLoginGUI.this).setVisible(true);
                    AdminLoginGUI.this.dispose(); // Cerrar la ventana de login
                } else {
                    JOptionPane.showMessageDialog(AdminLoginGUI.this,
                            "Credenciales incorrectas. Verifique su ID, correo y contraseña.", "Error de Inicio de Sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción del botón volver
        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose(); // Cerrar esta ventana
        });
    }
}
