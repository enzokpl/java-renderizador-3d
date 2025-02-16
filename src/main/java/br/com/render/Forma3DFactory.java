package br.com.render;

/**
 * Fábrica responsável por criar instâncias de {@link Forma3DModel} de acordo com o tipo de forma
 */
public class Forma3DFactory {

    /**
     * Cria e retorna um modelo 3D para a forma especificada
     *
     * @param forma Enum que representa o tipo de forma desejada
     * @return Instância de {@link Forma3DModel} contendo os vértices, arestas e faces
     * @throws IllegalArgumentException Se a forma não for reconhecida
     */
    public static Forma3DModel createForma(Forma3D forma) {
        switch (forma) {
            case PIRAMIDE:
                Ponto3D[] verticesPiramide = new Ponto3D[5];
                verticesPiramide[0] = new Ponto3D(-1, -1, -1);
                verticesPiramide[1] = new Ponto3D(1, -1, -1);
                verticesPiramide[2] = new Ponto3D(1, -1, 1);
                verticesPiramide[3] = new Ponto3D(-1, -1, 1);
                verticesPiramide[4] = new Ponto3D(0, 1, 0);

                int[][] arestasPiramide = {
                        {0, 1}, {1, 2}, {2, 3}, {3, 0},
                        {0, 4}, {1, 4}, {2, 4}, {3, 4}
                };

                int[][] facesPiramide = {
                        {0, 1, 2, 3},
                        {0, 1, 4},
                        {1, 2, 4},
                        {2, 3, 4},
                        {3, 0, 4}
                };
                return new Forma3DModel(verticesPiramide, arestasPiramide, facesPiramide);

            case CUBO:
                Ponto3D[] verticesCubo = new Ponto3D[8];
                verticesCubo[0] = new Ponto3D(-1, -1, -1);
                verticesCubo[1] = new Ponto3D(1, -1, -1);
                verticesCubo[2] = new Ponto3D(1, 1, -1);
                verticesCubo[3] = new Ponto3D(-1, 1, -1);
                verticesCubo[4] = new Ponto3D(-1, -1, 1);
                verticesCubo[5] = new Ponto3D(1, -1, 1);
                verticesCubo[6] = new Ponto3D(1, 1, 1);
                verticesCubo[7] = new Ponto3D(-1, 1, 1);

                int[][] arestasCubo = {
                        {0, 1}, {1, 2}, {2, 3}, {3, 0},
                        {4, 5}, {5, 6}, {6, 7}, {7, 4},
                        {0, 4}, {1, 5}, {2, 6}, {3, 7}
                };

                int[][] facesCubo = {
                        {0, 1, 2, 3},
                        {4, 5, 6, 7},
                        {0, 1, 5, 4},
                        {1, 2, 6, 5},
                        {2, 3, 7, 6},
                        {3, 0, 4, 7}
                };
                return new Forma3DModel(verticesCubo, arestasCubo, facesCubo);

            case RETANGULO:
                Ponto3D[] verticesRetangulo = new Ponto3D[8];
                verticesRetangulo[0] = new Ponto3D(-1, -0.5, -1.5);
                verticesRetangulo[1] = new Ponto3D(1, -0.5, -1.5);
                verticesRetangulo[2] = new Ponto3D(1, 0.5, -1.5);
                verticesRetangulo[3] = new Ponto3D(-1, 0.5, -1.5);
                verticesRetangulo[4] = new Ponto3D(-1, -0.5, 1.5);
                verticesRetangulo[5] = new Ponto3D(1, -0.5, 1.5);
                verticesRetangulo[6] = new Ponto3D(1, 0.5, 1.5);
                verticesRetangulo[7] = new Ponto3D(-1, 0.5, 1.5);

                int[][] arestasRetangulo = {
                        {0, 1}, {1, 2}, {2, 3}, {3, 0},
                        {4, 5}, {5, 6}, {6, 7}, {7, 4},
                        {0, 4}, {1, 5}, {2, 6}, {3, 7}
                };

                int[][] facesRetangulo = {
                        {0, 1, 2, 3},
                        {4, 5, 6, 7},
                        {0, 1, 5, 4},
                        {1, 2, 6, 5},
                        {2, 3, 7, 6},
                        {3, 0, 4, 7}
                };
                return new Forma3DModel(verticesRetangulo, arestasRetangulo, facesRetangulo);

            case PRISMA:
                int n = 6;
                Ponto3D[] verticesPrisma = new Ponto3D[n * 2];
                for (int i = 0; i < n; i++) {
                    double angle = Math.toRadians(60 * i);
                    double x = Math.cos(angle);
                    double z = Math.sin(angle);
                    verticesPrisma[i] = new Ponto3D(x, -1, z);
                    verticesPrisma[i + n] = new Ponto3D(x, 1, z);
                }

                int[][] arestasPrisma = new int[3 * n][2];
                int idx = 0;
                // Arestas da base inferior
                for (int i = 0; i < n; i++) {
                    arestasPrisma[idx++] = new int[]{i, (i + 1) % n};
                }
                // Arestas da base superior
                for (int i = n; i < 2 * n; i++) {
                    arestasPrisma[idx++] = new int[]{i, (i == 2 * n - 1 ? n : i + 1)};
                }
                // Arestas verticais
                for (int i = 0; i < n; i++) {
                    arestasPrisma[idx++] = new int[]{i, i + n};
                }

                // Faces: base inferior, base superior e faces laterais
                int[] faceInferior = new int[n];
                int[] faceSuperior = new int[n];
                for (int i = 0; i < n; i++) {
                    faceInferior[i] = i;
                    faceSuperior[i] = i + n;
                }
                int[][] lateralFaces = new int[n][4];
                for (int i = 0; i < n; i++) {
                    lateralFaces[i][0] = i;
                    lateralFaces[i][1] = (i + 1) % n;
                    lateralFaces[i][2] = ((i + 1) % n) + n;
                    lateralFaces[i][3] = i + n;
                }
                int[][] facesPrisma = new int[2 + n][];
                facesPrisma[0] = faceInferior;
                facesPrisma[1] = faceSuperior;
                System.arraycopy(lateralFaces, 0, facesPrisma, 2, n);
                return new Forma3DModel(verticesPrisma, arestasPrisma, facesPrisma);

            default:
                throw new IllegalArgumentException("Forma desconhecida: " + forma);
        }
    }
}
