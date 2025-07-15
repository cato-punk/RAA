package Vista;

import Controlador.AdminControlador;
import Modelo.*; // Importar todas las clases del modelo

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.UUID; // Para generar ID de nuevo veterinario

public class AdminGUI extends JFrame {
    private Admin adminActual;
    private AdminControlador adminControlador;
    private JFrame ventanaAnterior; // Para volver al login o menú principal

    // Componentes para el registro de veterinario
    private JTextField txtRegVetNombre, txtRegVetRut, txtRegVetDireccion, txtRegVetTelefono, txtRegVetCorreo, txtRegVetEspecialidad, txtRegVetLicencia;
    private JButton btnRegVetRegistrar;

    // Componentes para la eliminación de entidades
    private JComboBox<String> cmbEliminarTipo;
    private JTextField txtEliminarId;
    private JButton btnEliminarEntidad;

    // Componentes para la visualización de listados
    private JComboBox<String> cmbListarTipo;
    private JTable tablaDatos;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JButton btnListar;

    public AdminGUI(Admin admin, JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Panel de Administración");
        this.adminActual = admin;
        this.ventanaAnterior = ventanaAnterior;
        adminControlador = new AdminControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Registrar Veterinario
        JPanel panelRegistrarVet = new JPanel(new GridLayout(8, 2, 10, 10));
        panelRegistrarVet.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelRegistrarVet.add(new JLabel("Registrar Nuevo Veterinario"));
        panelRegistrarVet.add(new JLabel("")); // Espacio

        panelRegistrarVet.add(new JLabel("Nombre Completo:"));
        txtRegVetNombre = new JTextField();
        panelRegistrarVet.add(txtRegVetNombre);

        panelRegistrarVet.add(new JLabel("RUT:"));
        txtRegVetRut = new JTextField();
        panelRegistrarVet.add(txtRegVetRut);

        panelRegistrarVet.add(new JLabel("Dirección:"));
        txtRegVetDireccion = new JTextField();
        panelRegistrarVet.add(txtRegVetDireccion);

        panelRegistrarVet.add(new JLabel("Teléfono:"));
        txtRegVetTelefono = new JTextField();
        panelRegistrarVet.add(txtRegVetTelefono);

        panelRegistrarVet.add(new JLabel("Correo Electrónico:"));
        txtRegVetCorreo = new JTextField();
        panelRegistrarVet.add(txtRegVetCorreo);

        panelRegistrarVet.add(new JLabel("Especialidad:"));
        txtRegVetEspecialidad = new JTextField();
        panelRegistrarVet.add(txtRegVetEspecialidad);

        panelRegistrarVet.add(new JLabel("Licencia (Activa/Inactiva):"));
        txtRegVetLicencia = new JTextField();
        panelRegistrarVet.add(txtRegVetLicencia);

        btnRegVetRegistrar = new JButton("Registrar Veterinario");
        panelRegistrarVet.add(btnRegVetRegistrar);
        panelRegistrarVet.add(new JLabel("")); // Espacio para el botón

        tabbedPane.addTab("Registrar Veterinario", panelRegistrarVet);

        // Pestaña 2: Eliminar Entidad
        JPanel panelEliminar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelEliminar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelEliminar.add(new JLabel("Tipo de Entidad:"));
        cmbEliminarTipo = new JComboBox<>(new String[]{"Animal", "Veterinario", "Adoptante", "Rescatista"});
        panelEliminar.add(cmbEliminarTipo);

        panelEliminar.add(new JLabel("ID a Eliminar:"));
        txtEliminarId = new JTextField(20);
        panelEliminar.add(txtEliminarId);

        btnEliminarEntidad = new JButton("Eliminar Entidad");
        panelEliminar.add(btnEliminarEntidad);

        tabbedPane.addTab("Eliminar Entidad", panelEliminar);

        // Pestaña 3: Listar Entidades
        JPanel panelListar = new JPanel(new BorderLayout(10, 10));
        panelListar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelControlesListar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelControlesListar.add(new JLabel("Listar:"));
        cmbListarTipo = new JComboBox<>(new String[]{"Animales", "Veterinarios", "Adoptantes", "Rescatistas"});
        panelControlesListar.add(cmbListarTipo);
        btnListar = new JButton("Mostrar Listado");
        panelControlesListar.add(btnListar);
        panelListar.add(panelControlesListar, BorderLayout.NORTH);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel();
        tablaDatos = new JTable(tableModel);
        scrollPane = new JScrollPane(tablaDatos);
        panelListar.add(scrollPane, BorderLayout.CENTER);

        tabbedPane.addTab("Listar Entidades", panelListar);


        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            ventanaAnterior.setVisible(true); // Mostrar la ventana anterior (login o menú principal)
            dispose(); // Cerrar la ventana actual
        });

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnCerrarSesion);
        add(panelSur, BorderLayout.SOUTH);

        add(tabbedPane, BorderLayout.CENTER);

        // Acciones de los botones
        setupActionListeners();
    }

    private void setupActionListeners() {
        // Acción para registrar veterinario
        btnRegVetRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtRegVetNombre.getText().trim();
                String rut = txtRegVetRut.getText().trim();
                String direccion = txtRegVetDireccion.getText().trim();
                String telefono = txtRegVetTelefono.getText().trim();
                String correo = txtRegVetCorreo.getText().trim();
                String especialidad = txtRegVetEspecialidad.getText().trim();
                String licencia = txtRegVetLicencia.getText().trim();

                if (nombre.isEmpty() || rut.isEmpty() || correo.isEmpty() || especialidad.isEmpty() || licencia.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminGUI.this, "Por favor, complete todos los campos obligatorios.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!correo.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                    JOptionPane.showMessageDialog(AdminGUI.this, "Formato de correo electrónico inválido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String idGenerado = adminControlador.registrarVeterinario(nombre, rut, direccion, telefono, correo, especialidad, licencia);

                if (idGenerado != null) {
                    if ("CorreoExistente".equals(idGenerado)) {
                        JOptionPane.showMessageDialog(AdminGUI.this, "Error: Ya existe un veterinario con ese correo electrónico.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AdminGUI.this,
                                "Veterinario " + nombre + " registrado con éxito.\nID: " + idGenerado, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        // Limpiar campos
                        txtRegVetNombre.setText("");
                        txtRegVetRut.setText("");
                        txtRegVetDireccion.setText("");
                        txtRegVetTelefono.setText("");
                        txtRegVetCorreo.setText("");
                        txtRegVetEspecialidad.setText("");
                        txtRegVetLicencia.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(AdminGUI.this, "Error al registrar el veterinario. Verifique los datos o si el ID/correo ya existe.", "Error de Registro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para eliminar entidad
        btnEliminarEntidad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) cmbEliminarTipo.getSelectedItem();
                String id = txtEliminarId.getText().trim();

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminGUI.this, "Por favor, ingrese el ID a eliminar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(AdminGUI.this,
                        "¿Está seguro de que desea eliminar el " + tipo + " con ID: " + id + "?",
                        "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean eliminado = adminControlador.eliminarEntidad(tipo, id);
                    if (eliminado) {
                        JOptionPane.showMessageDialog(AdminGUI.this, tipo + " con ID " + id + " eliminado con éxito.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                        txtEliminarId.setText(""); // Limpiar campo
                        // Opcional: actualizar tabla si se está viendo un listado
                    } else {
                        JOptionPane.showMessageDialog(AdminGUI.this, "No se pudo eliminar el " + tipo + ". Verifique el ID o si existe.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción para listar entidades
        btnListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) cmbListarTipo.getSelectedItem();
                tableModel.setRowCount(0); // Limpiar filas existentes
                tableModel.setColumnCount(0); // Limpiar columnas existentes

                switch (tipo) {
                    case "Animales":
                        tableModel.setColumnIdentifiers(new String[]{"ID", "Especie", "Raza", "Sexo", "Estado Salud", "Estado Adopción", "Rescatista ID", "Veterinario ID"});
                        for (Animal animal : adminControlador.obtenerTodosAnimales()) {
                            tableModel.addRow(new Object[]{
                                    animal.getId(),
                                    animal.getEspecie(),
                                    animal.getRaza(),
                                    animal.getSexo(),
                                    animal.getEstadoSalud(),
                                    animal.getEstadoAdopcion(),
                                    animal.getIdRescatistaEncontro(),
                                    animal.getVeterinarioAtiendeId()
                            });
                        }
                        break;
                    case "Veterinarios":
                        tableModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "RUT", "Correo", "Especialidad", "Licencia"});
                        for (Veterinario vet : adminControlador.obtenerTodosVeterinarios()) {
                            tableModel.addRow(new Object[]{
                                    vet.getId(),
                                    vet.getNombre(),
                                    vet.getRut(),
                                    vet.getCorreoElectronico(),
                                    vet.getEspecialidad(),
                                    vet.getLicencia()
                            });
                        }
                        break;
                    case "Adoptantes":
                        tableModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "RUT", "Correo", "Permiso Adopción"});
                        for (Adoptante adoptante : adminControlador.obtenerTodosAdoptantes()) {
                            tableModel.addRow(new Object[]{
                                    adoptante.getId(),
                                    adoptante.getNombre(),
                                    adoptante.getRut(),
                                    adoptante.getCorreoElectronico(),
                                    adoptante.isPermisoAdopcion() ? "SI" : "NO"
                            });
                        }
                        break;
                    case "Rescatistas":
                        tableModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "RUT", "Correo", "Animales Rescatados"});
                        for (Rescatista res : adminControlador.obtenerTodosRescatistas()) {
                            tableModel.addRow(new Object[]{
                                    res.getId(),
                                    res.getNombre(),
                                    res.getRut(),
                                    res.getCorreoElectronico(),
                                    res.getAnimalesRescatadosIds().size()
                            });
                        }
                        break;
                }
            }
        });
    }
}
