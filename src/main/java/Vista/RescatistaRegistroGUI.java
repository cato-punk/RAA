package Vista;
import Controlador.RescatistaControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RescatistaRegistroGUI extends JFrame {
    private JTextField txtNombre, txtRut, txtDireccion, txtTelefono, txtCorreo;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private RescatistaControlador rescatistaControlador;
    private JFrame ventanaAnterior;

    public RescatistaRegistroGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Registro Rescatista");
        this.ventanaAnterior = ventanaAnterior;
        rescatistaControlador = new RescatistaControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField(20);
        panel.add(txtNombre);

        panel.add(new JLabel("RUT:"));
        txtRut = new JTextField(20);
        panel.add(txtRut);

        panel.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(20);
        panel.add(txtDireccion);

        panel.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono);

        panel.add(new JLabel("Correo Electrónico:"));
        txtCorreo = new JTextField(20);
        panel.add(txtCorreo);

        btnRegistrar = new JButton("Registrar");
        panel.add(btnRegistrar);

        btnVolver = new JButton("Volver al Login");
        panel.add(btnVolver);

        add(panel, BorderLayout.CENTER);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText().trim();
                String rut = txtRut.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String telefono = txtTelefono.getText().trim();
                String correo = txtCorreo.getText().trim();

                if (nombre.isEmpty() || rut.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(RescatistaRegistroGUI.this,
                            "Nombre, RUT y Correo Electrónico son obligatorios.", "Campos Obligatorios", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!correo.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(RescatistaRegistroGUI.this, "Formato de correo electrónico inválido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String idGenerado = rescatistaControlador.registrarRescatista(nombre, rut, direccion, telefono, correo);

                if (idGenerado != null) {
                    if ("CorreoExistente".equals(idGenerado)) {
                        JOptionPane.showMessageDialog(RescatistaRegistroGUI.this, "Error: Ya existe un rescatista con ese correo electrónico.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(RescatistaRegistroGUI.this,
                                "Rescatista " + nombre + " registrado con éxito.\nSu ID único es: " + idGenerado + "\nRecibirá su ID por correo.",
                                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        ventanaAnterior.setVisible(true); // Volver a la ventana de login
                        dispose(); // Cerrar esta ventana
                    }
                } else {
                    JOptionPane.showMessageDialog(RescatistaRegistroGUI.this,
                            "Error al registrar rescatista. Verifique los datos.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
    }
}