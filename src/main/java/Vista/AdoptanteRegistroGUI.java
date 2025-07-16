package Vista;

import Controlador.AdoptanteControlador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdoptanteRegistroGUI extends JFrame {
    private JTextField txtNombre, txtRut, txtDireccion, txtTelefono, txtCorreo, txtPreferencias, txtInfoAdopcion;
    private JButton btnRegistrar;
    private JButton btnVolver;
    private AdoptanteControlador adoptanteControlador;
    private JFrame ventanaAnterior;

    public AdoptanteRegistroGUI(JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Registro Adoptante");
        this.ventanaAnterior = ventanaAnterior;
        adoptanteControlador = new AdoptanteControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(450, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
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

        panel.add(new JLabel("Preferencias de Adopción:"));
        txtPreferencias = new JTextField(20);
        panel.add(txtPreferencias);

        panel.add(new JLabel("Información Adicional para Adopción:"));
        txtInfoAdopcion = new JTextField(20);
        panel.add(txtInfoAdopcion);

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
                String preferencias = txtPreferencias.getText().trim();
                String infoAdopcion = txtInfoAdopcion.getText().trim();

                if (nombre.isEmpty() || rut.isEmpty() || correo.isEmpty()) {
                    JOptionPane.showMessageDialog(AdoptanteRegistroGUI.this,
                            "Nombre, RUT y Correo Electrónico son obligatorios.", "Campos Obligatorios", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!correo.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(AdoptanteRegistroGUI.this, "Formato de correo electrónico inválido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String idGenerado = adoptanteControlador.registrarAdoptante(nombre, rut, direccion, telefono, correo, preferencias, infoAdopcion);

                if (idGenerado != null) {
                    if ("CorreoExistente".equals(idGenerado)) {
                        JOptionPane.showMessageDialog(AdoptanteRegistroGUI.this, "Error: Ya existe un adoptante con ese correo electrónico.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AdoptanteRegistroGUI.this,
                                "Adoptante " + nombre + " registrado con éxito.\nSu ID único es: " + idGenerado + "\nRecibirá su ID por correo. Su permiso de adopción está pendiente de aprobación por un veterinario.",
                                "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        ventanaAnterior.setVisible(true);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(AdoptanteRegistroGUI.this,
                            "Error al registrar adoptante. Verifique los datos.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnVolver.addActionListener(e -> {
            ventanaAnterior.setVisible(true);
            dispose();
        });
    }
}