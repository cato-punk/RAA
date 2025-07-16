package Vista;

import Controlador.VeterinarioControlador;
import Modelo.Adoptante;
import Modelo.Animal;
import Modelo.Rescatista;
import Modelo.Veterinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class VeterinarioGUI extends JFrame {
    private Veterinario veterinarioActual;
    private VeterinarioControlador veterinarioControlador;
    private JFrame ventanaAnterior;

    // Componentes para la pestaña de búsqueda
    private JComboBox<String> cmbBuscarTipo;
    private JTextField txtBuscarId;
    private JButton btnBuscar;
    private JTextArea areaResultadoBusqueda;

    // Componentes para la pestaña de permiso de adopción
    private JTextField txtAdoptanteIdPermiso;
    private JRadioButton rbPermisoSi, rbPermisoNo;
    private ButtonGroup bgPermiso;
    private JButton btnActualizarPermiso;

    // Componentes para la pestaña de datos de animal
    private JTextField txtAnimalIdDatos, txtRazaAnimal, txtEstadoSaludAnimal, txtEdadAnimal, txtVetAtiendeAnimal;
    private JComboBox<Animal.EstadoAdopcion> cmbEstadoAdopcionAnimal;
    private JButton btnCargarAnimalDatos, btnActualizarAnimalDatos;


    public VeterinarioGUI(Veterinario veterinario, JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Panel Veterinario: " + veterinario.getNombre());
        this.veterinarioActual = veterinario;
        this.ventanaAnterior = ventanaAnterior;
        veterinarioControlador = new VeterinarioControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Ver/Buscar Entidades
        JPanel panelBuscar = new JPanel(new BorderLayout(10, 10));
        panelBuscar.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelControlesBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelControlesBusqueda.add(new JLabel("Buscar Tipo:"));
        cmbBuscarTipo = new JComboBox<>(new String[]{"Rescatista", "Adoptante", "Animal"});
        panelControlesBusqueda.add(cmbBuscarTipo);
        panelControlesBusqueda.add(new JLabel("ID:"));
        txtBuscarId = new JTextField(15);
        panelControlesBusqueda.add(txtBuscarId);
        btnBuscar = new JButton("Buscar");
        panelControlesBusqueda.add(btnBuscar);
        panelBuscar.add(panelControlesBusqueda, BorderLayout.NORTH);

        areaResultadoBusqueda = new JTextArea();
        areaResultadoBusqueda.setEditable(false);
        JScrollPane scrollBusqueda = new JScrollPane(areaResultadoBusqueda);
        panelBuscar.add(scrollBusqueda, BorderLayout.CENTER);
        tabbedPane.addTab("Buscar Entidad", panelBuscar);

        // Pestaña 2: Cambiar Permiso de Adopción
        JPanel panelPermisoAdopcion = new JPanel(new GridLayout(4, 2, 10, 10));
        panelPermisoAdopcion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelPermisoAdopcion.add(new JLabel("ID Adoptante:"));
        txtAdoptanteIdPermiso = new JTextField();
        panelPermisoAdopcion.add(txtAdoptanteIdPermiso);

        panelPermisoAdopcion.add(new JLabel("Permiso de Adopción:"));
        JPanel panelRadioPermiso = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbPermisoSi = new JRadioButton("Sí");
        rbPermisoNo = new JRadioButton("No");
        bgPermiso = new ButtonGroup();
        bgPermiso.add(rbPermisoSi);
        bgPermiso.add(rbPermisoNo);
        panelRadioPermiso.add(rbPermisoSi);
        panelRadioPermiso.add(rbPermisoNo);
        panelPermisoAdopcion.add(panelRadioPermiso);

        btnActualizarPermiso = new JButton("Actualizar Permiso");
        panelPermisoAdopcion.add(btnActualizarPermiso);
        panelPermisoAdopcion.add(new JLabel("")); // Espacio

        tabbedPane.addTab("Permiso Adopción", panelPermisoAdopcion);

        // Pestaña 3: Cambiar Datos de Animal
        JPanel panelDatosAnimal = new JPanel(new GridLayout(8, 2, 10, 10));
        panelDatosAnimal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelDatosAnimal.add(new JLabel("ID Animal:"));
        txtAnimalIdDatos = new JTextField();
        panelDatosAnimal.add(txtAnimalIdDatos);
        btnCargarAnimalDatos = new JButton("Cargar Datos Animal"); // Botón para cargar info del animal
        panelDatosAnimal.add(btnCargarAnimalDatos);
        panelDatosAnimal.add(new JLabel("")); // Espacio

        panelDatosAnimal.add(new JLabel("Raza:"));
        txtRazaAnimal = new JTextField();
        panelDatosAnimal.add(txtRazaAnimal);

        panelDatosAnimal.add(new JLabel("Estado de Salud:"));
        txtEstadoSaludAnimal = new JTextField();
        panelDatosAnimal.add(txtEstadoSaludAnimal);

        panelDatosAnimal.add(new JLabel("Edad Aproximada:"));
        txtEdadAnimal = new JTextField();
        panelDatosAnimal.add(txtEdadAnimal);

        panelDatosAnimal.add(new JLabel("Estado de Adopción:"));
        cmbEstadoAdopcionAnimal = new JComboBox<>(Animal.EstadoAdopcion.values());
        panelDatosAnimal.add(cmbEstadoAdopcionAnimal);

        panelDatosAnimal.add(new JLabel("ID Veterinario que Atiende:"));
        txtVetAtiendeAnimal = new JTextField();
        txtVetAtiendeAnimal.setText(veterinarioActual.getId()); // Por defecto, es el veterinario actual
        txtVetAtiendeAnimal.setEditable(true); // Se puede cambiar
        panelDatosAnimal.add(txtVetAtiendeAnimal);

        btnActualizarAnimalDatos = new JButton("Actualizar Datos Animal");
        panelDatosAnimal.add(btnActualizarAnimalDatos);
        panelDatosAnimal.add(new JLabel("")); // Espacio

        tabbedPane.addTab("Actualizar Animal", panelDatosAnimal);

        add(tabbedPane, BorderLayout.CENTER);

        // Botón para cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Sesión cerrada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            ventanaAnterior.setVisible(true);
            dispose();
        });

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.add(btnCerrarSesion);
        add(panelSur, BorderLayout.SOUTH);

        setupActionListeners();
    }

    private void setupActionListeners() {
        // Acción para buscar entidades
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) cmbBuscarTipo.getSelectedItem();
                String id = txtBuscarId.getText().trim();
                areaResultadoBusqueda.setText(""); // Limpiar área

                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Por favor, ingrese un ID para buscar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                switch (tipo) {
                    case "Rescatista":
                        Optional<Rescatista> rescatista = veterinarioControlador.verRescatistaPorId(id);
                        if (rescatista.isPresent()) {
                            areaResultadoBusqueda.setText(rescatista.get().toString());
                        } else {
                            areaResultadoBusqueda.setText("Rescatista con ID " + id + " no encontrado.");
                        }
                        break;
                    case "Adoptante":
                        Optional<Adoptante> adoptante = veterinarioControlador.verAdoptantePorId(id);
                        if (adoptante.isPresent()) {
                            areaResultadoBusqueda.setText(adoptante.get().toString());
                        } else {
                            areaResultadoBusqueda.setText("Adoptante con ID " + id + " no encontrado.");
                        }
                        break;
                    case "Animal":
                        Optional<Animal> animal = veterinarioControlador.verAnimalPorId(id);
                        if (animal.isPresent()) {
                            areaResultadoBusqueda.setText(animal.get().toString());
                        } else {
                            areaResultadoBusqueda.setText("Animal con ID " + id + " no encontrado.");
                        }
                        break;
                }
            }
        });

        // Acción para actualizar permiso de adopción
        btnActualizarPermiso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adoptanteId = txtAdoptanteIdPermiso.getText().trim();
                boolean permiso = rbPermisoSi.isSelected();

                if (adoptanteId.isEmpty()) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Por favor, ingrese el ID del adoptante.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!rbPermisoSi.isSelected() && !rbPermisoNo.isSelected()) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Por favor, seleccione si tiene permiso o no.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean actualizado = veterinarioControlador.cambiarPermisoAdopcion(adoptanteId, permiso);
                if (actualizado) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Permiso de adopción del adoptante " + adoptanteId + " actualizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "No se pudo actualizar el permiso. Verifique el ID del adoptante.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para cargar datos del animal en el formulario de actualización
        btnCargarAnimalDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String animalId = txtAnimalIdDatos.getText().trim();
                if (animalId.isEmpty()) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Ingrese el ID del animal para cargar sus datos.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Optional<Animal> animalOptional = veterinarioControlador.verAnimalPorId(animalId);
                if (animalOptional.isPresent()) {
                    Animal animal = animalOptional.get();
                    txtRazaAnimal.setText(animal.getRaza());
                    txtEstadoSaludAnimal.setText(animal.getEstadoSalud());
                    txtEdadAnimal.setText(animal.getEdadAproximada());
                    cmbEstadoAdopcionAnimal.setSelectedItem(animal.getEstadoAdopcion());
                    txtVetAtiendeAnimal.setText(animal.getVeterinarioAtiendeId() != null ? animal.getVeterinarioAtiendeId() : "");
                } else {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Animal con ID " + animalId + " no encontrado.", "Animal No Encontrado", JOptionPane.ERROR_MESSAGE);
                    // Limpiar campos si no se encuentra
                    txtRazaAnimal.setText("");
                    txtEstadoSaludAnimal.setText("");
                    txtEdadAnimal.setText("");
                    cmbEstadoAdopcionAnimal.setSelectedIndex(0); // Seleccionar el primer elemento
                    txtVetAtiendeAnimal.setText(veterinarioActual.getId());
                }
            }
        });

        // Acción para actualizar datos del animal
        btnActualizarAnimalDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String animalId = txtAnimalIdDatos.getText().trim();
                String raza = txtRazaAnimal.getText().trim();
                String estadoSalud = txtEstadoSaludAnimal.getText().trim();
                String edadAproximada = txtEdadAnimal.getText().trim();
                Animal.EstadoAdopcion estadoAdopcion = (Animal.EstadoAdopcion) cmbEstadoAdopcionAnimal.getSelectedItem();
                String vetAtiendeId = txtVetAtiendeAnimal.getText().trim();

                if (animalId.isEmpty() || raza.isEmpty() || estadoSalud.isEmpty() || edadAproximada.isEmpty() || vetAtiendeId.isEmpty()) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Por favor, complete todos los campos para actualizar el animal.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean actualizado = veterinarioControlador.cambiarDatosAnimal(
                        animalId, raza, estadoSalud, edadAproximada, estadoAdopcion, vetAtiendeId
                );

                if (actualizado) {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "Datos del animal " + animalId + " actualizados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(VeterinarioGUI.this, "No se pudo actualizar el animal. Verifique el ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
