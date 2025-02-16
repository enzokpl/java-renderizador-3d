package br.com.render;

import lombok.Getter;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Painel responsável pela renderização da forma 3D.
 * Permite a aplicação de transformações (rotação e zoom) e
 * exibe as faces e arestas conforme as opções configuradas.
 */
public class PainelRender extends JPanel {

    /**
     * Modelo da forma 3D atualmente exibida
     */
    private Forma3DModel shapeModel;

    /**
     * Indica se as faces devem ser preenchidas
     */
    private boolean preencherFaces = false;

    /**
     * Cor utilizada para preencher as faces
     */
    @Getter
    private Color corFace = Color.RED;

    /**
     * Indica se as arestas devem ser desenhadas em cor fixa (preto).
     */
    private boolean marcarArestas = false;

    // Parâmetros de transformação
    private double zoom = 1.0;
    private double rotacaoX = 0;
    private double rotacaoY = 0;
    private int mouseXAnterior;
    private int mouseYAnterior;

    /**
     * Construtor do painel, configura o tamanho e a forma padrão.
     */
    public PainelRender() {
        setPreferredSize(new Dimension(800, 600));
        definirForma(Forma3D.CUBO);
        initMouseListeners();
    }

    /**
     * Define a forma atual utilizando a {@link Forma3DFactory} e reseta as transformações.
     *
     * @param forma Enum que define a forma a ser renderizada.
     */
    public void definirForma(Forma3D forma) {
        this.shapeModel = Forma3DFactory.createForma(forma);
        resetTransformations();
        repaint();
    }

    /**
     * Reseta os parâmetros de transformação (zoom e rotações).
     */
    private void resetTransformations() {
        zoom = 1.0;
        rotacaoX = 0;
        rotacaoY = 0;
    }

    /**
     * Inicializa os listeners de mouse para manipulação (rotação e zoom).
     */
    private void initMouseListeners() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseXAnterior = e.getX();
                mouseYAnterior = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mouseXAnterior;
                int dy = e.getY() - mouseYAnterior;
                rotacaoY -= dx;
                rotacaoX -= dy;
                mouseXAnterior = e.getX();
                mouseYAnterior = e.getY();
                repaint();
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);

        addMouseWheelListener(e -> {
            int notches = e.getWheelRotation();
            zoom -= notches * 0.1;
            if (zoom < 0.1) {
                zoom = 0.1;
            }
            repaint();
        });
    }

    /**
     * Configura se as faces devem ser preenchidas.
     *
     * @param preencherFaces {@code true} para preencher, {@code false} caso contrário.
     */
    public void setPreencherFaces(boolean preencherFaces) {
        this.preencherFaces = preencherFaces;
        repaint();
    }

    /**
     * Define a cor utilizada para preencher as faces.
     *
     * @param corFace A nova cor.
     */
    public void setCorFace(Color corFace) {
        this.corFace = corFace;
        repaint();
    }

    /**
     * Configura se as arestas devem ser desenhadas com a cor fixa preta.
     *
     * @param marcarArestas {@code true} para desenhar as arestas em preto, {@code false} caso contrário.
     */
    public void setMarcarArestas(boolean marcarArestas) {
        this.marcarArestas = marcarArestas;
        repaint();
    }

    /**
     * Aplica as transformações (rotação) a um vértice.
     *
     * @param p O vértice original.
     * @return O vértice transformado.
     */
    private Ponto3D transformVertex(Ponto3D p) {
        p = Renderizador3D.rotacionarX(p, rotacaoX);
        p = Renderizador3D.rotacionarY(p, rotacaoY);
        return p;
    }

    /**
     * Projeta todos os vértices da forma para as coordenadas 2D.
     *
     * @param width  Largura do componente.
     * @param height Altura do componente.
     * @return Matriz com as coordenadas projetadas de cada vértice.
     */
    private int[][] projectVertices(int width, int height) {
        Ponto3D[] vertices = shapeModel.getVertices();
        int[][] projected = new int[vertices.length][2];
        for (int i = 0; i < vertices.length; i++) {
            Ponto3D p = transformVertex(vertices[i]);
            projected[i] = Renderizador3D.projetar(p, width, height, zoom);
        }
        return projected;
    }

    /**
     * Desenha as faces preenchidas (se ativado) no contexto gráfico.
     *
     * @param g2d       Contexto gráfico.
     * @param projected Matriz de vértices projetados.
     */
    private void drawFaces(Graphics2D g2d, int[][] projected) {
        if (preencherFaces && shapeModel.getFaces() != null) {
            g2d.setColor(corFace);
            for (int[] face : shapeModel.getFaces()) {
                int n = face.length;
                int[] xPoints = new int[n];
                int[] yPoints = new int[n];
                for (int i = 0; i < n; i++) {
                    xPoints[i] = projected[face[i]][0];
                    yPoints[i] = projected[face[i]][1];
                }
                g2d.fillPolygon(xPoints, yPoints, n);
            }
        }
    }

    /**
     * Desenha as arestas da forma.
     * Se a opção "marcar arestas" estiver ativa, utiliza cor preta; caso contrário,
     * se as faces estiverem preenchidas utiliza a cor da face; se não, utiliza azul.
     *
     * @param g2d       Contexto gráfico.
     * @param projected Matriz de vértices projetados.
     */
    private void drawEdges(Graphics2D g2d, int[][] projected) {
        Color edgeColor = marcarArestas ? Color.BLACK : (preencherFaces ? corFace : Color.BLUE);
        g2d.setColor(edgeColor);
        for (int[] edge : shapeModel.getArestas()) {
            g2d.drawLine(
                    projected[edge[0]][0],
                    projected[edge[0]][1],
                    projected[edge[1]][0],
                    projected[edge[1]][1]
            );
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int[][] projected = projectVertices(width, height);
        drawFaces(g2d, projected);
        drawEdges(g2d, projected);
    }
}
