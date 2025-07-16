package Vista;


import Controlador.AdoptanteControlador;
import Modelo.Adoptante;
import Modelo.Animal;
import Controlador.AnimalControlador; // Para obtener detalles de animales por ID

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class AdoptanteGUI extends JFrame {
    private Adoptante adoptanteActual;
    private AdoptanteControlador adoptanteControlador;
    private AnimalControlador animalControlador;
    private JFrame ventanaAnterior;

    private JTextArea areaDatosAdoptante;

    private JTable tablaAnimalesEnAdopcion;
    private DefaultTableModel tableModelAnimalesEnAdopcion;
    private JButton btnActualizarListaAnimales;

    private JTextField txtAnimalIdAdoptar;
    private JButton btnAdoptarAnimal;

    public AdoptanteGUI(Adoptante adoptante, JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Panel Adoptante: " + adoptante.getNombre());
        this.adoptanteActual = adoptante;
        this.ventanaAnterior = ventanaAnterior;
        adoptanteControlador = new AdoptanteControlador();
        animalControlador = new AnimalControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Mis Datos
        JPanel panelMisDatos = new JPanel(new BorderLayout(10, 10));
        panelMisDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaDatosAdoptante = new JTextArea();
        areaDatosAdoptante.setEditable(false);
        areaDatosAdoptante.setText(adoptanteActual.toString());
        panelMisDatos.add(new JScrollPane(areaDatosAdoptante), BorderLayout.CENTER);

        tabbedPane.addTab("Mis Datos", panelMisDatos);

        if (adoptanteActual.isPermisoAdopcion()) {
            // Pestaña 2: Ver Animales en Adopción
            JPanel panelVerAnimales = new JPanel(new BorderLayout(10, 10));
            panelVerAnimales.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            tableModelAnimalesEnAdopcion = new DefaultTableModel();
            tableModelAnimalesEnAdopcion.setColumnIdentifiers(new String[]{"ID", "Especie", "Raza", "Sexo", "Edad Aproximada"});
            tablaAnimalesEnAdopcion = new JTable(tableModelAnimalesEnAdopcion);
            panelVerAnimales.add(new JScrollPane(tablaAnimalesEnAdopcion), BorderLayout.CENTER);

            btnActualizarListaAnimales = new JButton("Actualizar Lista");
            JPanel panelControlesVer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelControlesVer.add(btnActualizarListaAnimales);
            panelVerAnimales.add(panelControlesVer, BorderLayout.SOUTH);

            tabbedPane.addTab("Animales en Adopción", panelVerAnimales);
            cargarAnimalesEnAdopcion();

            // Pestaña 3: Adoptar Animal
            JPanel panelAdoptarAnimal = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            panelAdoptarAnimal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            panelAdoptarAnimal.add(new JLabel("ID del Animal a Adoptar:"));
            txtAnimalIdAdoptar = new JTextField(20);
            panelAdoptarAnimal.add(txtAnimalIdAdoptar);

            btnAdoptarAnimal = new JButton("Adoptar Animal");
            panelAdoptarAnimal.add(btnAdoptarAnimal);

            tabbedPane.addTab("Adoptar Animal", panelAdoptarAnimal);
        } else {
            JPanel panelSinPermiso = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("<html><center>No tiene permiso para adoptar. <br>Contacte a un veterinario para que apruebe su solicitud.</center></html>");
            lblMensaje.setFont(new Font("Arial", Font.BOLD, 16));
            lblMensaje.setForeground(Color.RED);
            panelSinPermiso.add(lblMensaje);
            tabbedPane.addTab("Permiso Pendiente", panelSinPermiso);
        }

        add(tabbedPane, BorderLayout.CENTER);

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

    private void cargarAnimalesEnAdopcion() {
        tableModelAnimalesEnAdopcion.setRowCount(0);
        List<Animal> animales = adoptanteControlador.obtenerAnimalesEnAdopcion();
        for (Animal animal : animales) {
            tableModelAnimalesEnAdopcion.addRow(new Object[]{
                    animal.getId(),
                    animal.getEspecie(),
                    animal.getRaza(),
                    animal.getSexo(),
                    animal.getEdadAproximada()
            });
        }
    }

    private void setupActionListeners() {
        if (adoptanteActual.isPermisoAdopcion()) {
            btnActualizarListaAnimales.addActionListener(e -> cargarAnimalesEnAdopcion());

            btnAdoptarAnimal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String animalId = txtAnimalIdAdoptar.getText().trim();
                    if (animalId.isEmpty()) {
                        JOptionPane.showMessageDialog(AdoptanteGUI.this, "Ingrese el ID del animal a adoptar.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    boolean adoptado = adoptanteControlador.adoptarAnimal(adoptanteActual.getId(), animalId);
                    if (adoptado) {
                        // FIX: El método iniciarSesion devuelve Adoptante, no Optional<Adoptante>
                        Adoptante tempAdoptante = adoptanteControlador.iniciarSesion(adoptanteActual.getId(), adoptanteActual.getCorreoElectronico());
                        if (tempAdoptante != null) {
                            adoptanteActual = tempAdoptante; // Actualizar la referencia del objeto adoptanteActual
                            areaDatosAdoptante.setText(adoptanteActual.toString()); // Actualizar la visualización de los datos del adoptante
                        }

                        JOptionPane.showMessageDialog(AdoptanteGUI.this,
                                "¡Felicidades! Ha adoptado el animal con ID: " + animalId, "Adopción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                        txtAnimalIdAdoptar.setText("");
                        cargarAnimalesEnAdopcion();
                    } else {
                        JOptionPane.showMessageDialog(AdoptanteGUI.this,
                                "No se pudo adoptar el animal. Verifique el ID o si ya está en adopción.", "Error de Adopción", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        }
    }
}
