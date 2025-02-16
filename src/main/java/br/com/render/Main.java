package br.com.render;

import javax.swing.*;
import java.awt.*;

/**
 * Classe principal que inicializa a aplicação e configura a interface.
 */
public class Main {

    /**
     * Método principal que inicia a aplicação.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Render 3D");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            PainelRender painelRender = new PainelRender();
            frame.add(painelRender, BorderLayout.CENTER);

            JPanel controlPanel = createControlPanel(painelRender);
            frame.add(controlPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    /**
     * Cria o painel de controle contendo os botões de seleção de forma e opções de renderização.
     *
     * @param painelRender O painel de renderização a ser controlado.
     * @return Painel de controle configurado.
     */
    private static JPanel createControlPanel(PainelRender painelRender) {
        // Painel com os botões para selecionar a forma
        JPanel panelFormas = new JPanel();
        JRadioButton btnCubo = new JRadioButton("Cubo");
        JRadioButton btnPiramide = new JRadioButton("Pirâmide");
        JRadioButton btnRetangulo = new JRadioButton("Retângulo");
        JRadioButton btnPrisma = new JRadioButton("Prisma");

        ButtonGroup grupoFormas = new ButtonGroup();
        grupoFormas.add(btnCubo);
        grupoFormas.add(btnPiramide);
        grupoFormas.add(btnRetangulo);
        grupoFormas.add(btnPrisma);

        btnCubo.setSelected(true);

        btnCubo.addActionListener(e -> painelRender.definirForma(Forma3D.CUBO));
        btnPiramide.addActionListener(e -> painelRender.definirForma(Forma3D.PIRAMIDE));
        btnRetangulo.addActionListener(e -> painelRender.definirForma(Forma3D.RETANGULO));
        btnPrisma.addActionListener(e -> painelRender.definirForma(Forma3D.PRISMA));

        panelFormas.add(btnCubo);
        panelFormas.add(btnPiramide);
        panelFormas.add(btnRetangulo);
        panelFormas.add(btnPrisma);

        // Painel com as opções de renderização
        JPanel panelOpcoes = new JPanel();
        JCheckBox chkPreencherFaces = new JCheckBox("Preencher Faces");
        JButton btnEscolherCor = new JButton("Escolher Cor");
        btnEscolherCor.setEnabled(false);
        JCheckBox chkMarcarArestas = new JCheckBox("Marcar Arestas");

        chkPreencherFaces.addActionListener(e -> {
            boolean selecionado = chkPreencherFaces.isSelected();
            painelRender.setPreencherFaces(selecionado);
            btnEscolherCor.setEnabled(selecionado);
        });

        btnEscolherCor.addActionListener(e -> {
            Color novaCor = JColorChooser.showDialog(
                    null,
                    "Escolha a cor para faces",
                    painelRender.getCorFace()
            );

            if (novaCor != null) {
                painelRender.setCorFace(novaCor);
            }
        });

        chkMarcarArestas.addActionListener(e -> painelRender.setMarcarArestas(chkMarcarArestas.isSelected()));

        panelOpcoes.add(chkPreencherFaces);
        panelOpcoes.add(btnEscolherCor);
        panelOpcoes.add(chkMarcarArestas);

        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.add(panelFormas, BorderLayout.NORTH);
        controlPanel.add(panelOpcoes, BorderLayout.SOUTH);

        return controlPanel;
    }
}
