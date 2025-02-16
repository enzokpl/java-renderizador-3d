package br.com.render;

/**
 * Classe utilitária responsável por realizar transformações 3D,
 * como rotações e projeção perspectiva para pontos 3D
 */
public class Renderizador3D {

    /** Distância da câmera utilizada na projeção perspectiva */
    private static final double DISTANCIA_CAMERA = 4.0;

    /**
     * Aplica rotação em torno do eixo Y
     *
     * @param p O ponto a ser rotacionado
     * @param angulo O ângulo de rotação em graus
     * @return Novo ponto resultante da rotação em Y
     */
    public static Ponto3D rotacionarY(Ponto3D p, double angulo) {
        double rad = Math.toRadians(angulo);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double xNovo = p.getX() * cos - p.getZ() * sin;
        double zNovo = p.getX() * sin + p.getZ() * cos;
        return new Ponto3D(xNovo, p.getY(), zNovo);
    }

    /**
     * Aplica rotação em torno do eixo X
     *
     * @param p O ponto a ser rotacionado
     * @param angulo O ângulo de rotação em graus
     * @return Novo ponto resultante da rotação em X
     */
    public static Ponto3D rotacionarX(Ponto3D p, double angulo) {
        double rad = Math.toRadians(angulo);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);
        double yNovo = p.getY() * cos - p.getZ() * sin;
        double zNovo = p.getY() * sin + p.getZ() * cos;
        return new Ponto3D(p.getX(), yNovo, zNovo);
    }

    /**
     * Projeta um ponto 3D para coordenadas 2D utilizando uma projeção perspectiva
     *
     * @param p O ponto 3D a ser projetado
     * @param larguraTela Largura da tela (ou componente)
     * @param alturaTela Altura da tela (ou componente)
     * @param zoom Fator de zoom
     * @return Array contendo as coordenadas {x, y} projetadas
     */
    public static int[] projetar(Ponto3D p, int larguraTela, int alturaTela, double zoom) {
        double fator = DISTANCIA_CAMERA / (DISTANCIA_CAMERA - p.getZ());
        int xTela = (int) (p.getX() * fator * zoom * larguraTela / 2) + larguraTela / 2;
        int yTela = (int) (-p.getY() * fator * zoom * alturaTela / 2) + alturaTela / 2;
        return new int[] { xTela, yTela };
    }
}
