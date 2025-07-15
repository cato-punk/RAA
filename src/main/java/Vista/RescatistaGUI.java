package Vista;


import Controlador.RescatistaControlador;
import Modelo.Animal;
import Modelo.Rescatista;
import Controlador.AnimalControlador; // Para obtener detalles de animales rescatados

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RescatistaGUI extends JFrame {
    private Rescatista rescatistaActual;
    private RescatistaControlador rescatistaControlador;
    private AnimalControlador animalControlador; // Necesario para buscar detalles de animales por ID
    private JFrame ventanaAnterior;

    // Pestaña: Mis Datos y Animales Rescatados
    private JTextArea areaDatosRescatista;
    private JTable tablaAnimalesRescatados;
    private DefaultTableModel tableModelAnimalesRescatados;

    // Pestaña: Informar Nuevo Rescate
    private JTextField txtRaza, txtEspecie, txtSexo, txtEstadoSalud, txtLugarEncuentro, txtHoraRescate, txtFechaRescate, txtEdadAproximada;
    private JButton btnInformarRescate;

    public RescatistaGUI(Rescatista rescatista, JFrame ventanaAnterior) {
        super("Red Ayuda Animal - Panel Rescatista: " + rescatista.getNombre());
        this.rescatistaActual = rescatista;
        this.ventanaAnterior = ventanaAnterior;
        rescatistaControlador = new RescatistaControlador();
        animalControlador = new AnimalControlador();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Mis Datos y Animales Rescatados
        JPanel panelMisDatos = new JPanel(new BorderLayout(10, 10));
        panelMisDatos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        areaDatosRescatista = new JTextArea(5, 40);
        areaDatosRescatista.setEditable(false);
        areaDatosRescatista.setText(rescatistaActual.toString());
        panelMisDatos.add(new JScrollPane(areaDatosRescatista), BorderLayout.NORTH);

        tableModelAnimalesRescatados = new DefaultTableModel();
        tableModelAnimalesRescatados.setColumnIdentifiers(new String[]{"ID Animal", "Especie", "Raza", "Estado Adopción"});
        tablaAnimalesRescatados = new JTable(tableModelAnimalesRescatados);
        panelMisDatos.add(new JScrollPane(tablaAnimalesRescatados), BorderLayout.CENTER);

        tabbedPane.addTab("Mis Datos y Rescates", panelMisDatos);

        // Cargar animales del rescatista al inicio
        cargarAnimalesRescatados();


        // Pestaña 2: Informar Nuevo Rescate
        JPanel panelNuevoRescate = new JPanel(new GridLayout(9, 2, 10, 10));
        panelNuevoRescate.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panelNuevoRescate.add(new JLabel("Raza:"));
        txtRaza = new JTextField();
        panelNuevoRescate.add(txtRaza);

        panelNuevoRescate.add(new JLabel("Especie:"));
        txtEspecie = new JTextField();
        panelNuevoRescate.add(txtEspecie);

        panelNuevoRescate.add(new JLabel("Sexo:"));
        txtSexo = new JTextField();
        panelNuevoRescate.add(txtSexo);

        panelNuevoRescate.add(new JLabel("Estado de Salud:"));
        txtEstadoSalud = new JTextField();
        panelNuevoRescate.add(txtEstadoSalud);

        panelNuevoRescate.add(new JLabel("Lugar de Encuentro:"));
        txtLugarEncuentro = new JTextField();
        panelNuevoRescate.add(txtLugarEncuentro);

        panelNuevoRescate.add(new JLabel("Hora del Rescate (HH:MM):"));
        txtHoraRescate = new JTextField();
        panelNuevoRescate.add(txtHoraRescate);

        panelNuevoRescate.add(new JLabel("Fecha del Rescate (DD-MM-AAAA):"));
        txtFechaRescate = new JTextField();
        panelNuevoRescate.add(txtFechaRescate);

        panelNuevoRescate.add(new JLabel("Edad Aproximada:"));
        txtEdadAproximada = new JTextField();
        panelNuevoRescate.add(txtEdadAproximada);

        btnInformarRescate = new JButton("Informar Rescate");
        panelNuevoRescate.add(btnInformarRescate);
        panelNuevoRescate.add(new JLabel("")); // Espacio

        tabbedPane.addTab("Informar Nuevo Rescate", panelNuevoRescate);

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

    private void cargarAnimalesRescatados() {
        tableModelAnimalesRescatados.setRowCount(0); // Limpiar tabla
        List<String> idsAnimales = rescatistaActual.getAnimalesRescatadosIds();
        for (String animalId : idsAnimales) {
            Optional<Animal> animalOpt = animalControlador.buscarAnimalPorId(animalId);
            animalOpt.ifPresent(animal -> tableModelAnimalesRescatados.addRow(new Object[]{
                    animal.getId(),
                    animal.getEspecie(),
                    animal.getRaza(),
                    animal.getEstadoAdopcion()
            }));
        }
    }

    private void setupActionListeners() {
        // Acción para informar nuevo rescate
        btnInformarRescate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String raza = txtRaza.getText().trim();
                String especie = txtEspecie.getText().trim();
                String sexo = txtSexo.getText().trim();
                String estadoSalud = txtEstadoSalud.getText().trim();
                String lugarEncuentro = txtLugarEncuentro.getText().trim();
                String horaRescate = txtHoraRescate.getText().trim();
                String fechaRescate = txtFechaRescate.getText().trim();
                String edadAproximada = txtEdadAproximada.getText().trim();

                if (raza.isEmpty() || especie.isEmpty() || sexo.isEmpty() || estadoSalud.isEmpty() ||
                        lugarEncuentro.isEmpty() || horaRescate.isEmpty() || fechaRescate.isEmpty() || edadAproximada.isEmpty()) {
                    JOptionPane.showMessageDialog(RescatistaGUI.this,
                            "Por favor, complete todos los campos para informar el rescate.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validaciones de formato de fecha y hora
                if (!isValidTimeFormat(horaRescate)) {
                    JOptionPane.showMessageDialog(RescatistaGUI.this, "Formato de hora inválido. Use HH:MM.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!isValidDateFormat(fechaRescate)) {
                    JOptionPane.showMessageDialog(RescatistaGUI.this, "Formato de fecha inválido. Use DD-MM-AAAA.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String animalId = rescatistaControlador.informarRescate(
                        rescatistaActual, raza, especie, sexo, estadoSalud,
                        lugarEncuentro, horaRescate, fechaRescate, edadAproximada
                );

                if (animalId != null) {
                    // Refrescar el objeto rescatistaActual para que contenga el nuevo ID de animal
                    Optional<Rescatista> updatedRescatista = rescatistaControlador.buscarRescatistaPorId(rescatistaActual.getId());
                    updatedRescatista.ifPresent(r -> rescatistaActual = r); // Actualizar la referencia

                    JOptionPane.showMessageDialog(RescatistaGUI.this,
                            "Rescate informado y animal registrado con ID: " + animalId, "Rescate Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    // Limpiar campos
                    txtRaza.setText("");
                    txtEspecie.setText("");
                    txtSexo.setText("");
                    txtEstadoSalud.setText("");
                    txtLugarEncuentro.setText("");
                    txtHoraRescate.setText("");
                    txtFechaRescate.setText("");
                    txtEdadAproximada.setText("");
                    cargarAnimalesRescatados(); // Recargar la tabla de animales rescatados
                } else {
                    JOptionPane.showMessageDialog(RescatistaGUI.this, "Error al informar el rescate. Verifique los datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    private boolean isValidTimeFormat(String time) {
        return time.matches("^([01]\\d|2[0-3]):([0-5]\\d)$");
    }

    private boolean isValidDateFormat(String date) {
        return date.matches("^(0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-((19|20)\\d{2})$");
    }
}