package br.com.render;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Modelo que encapsula os vértices, as arestas e as faces de uma forma 3D.
 */
@AllArgsConstructor
@Data
public class Forma3DModel {

    /** Vetor de vértices da forma */
    private final Ponto3D[] vertices;

    /** Matriz de arestas, onde cada linha representa uma aresta através dos índices dos vértices */
    private final int[][] arestas;

    /** Matriz de faces, onde cada linha representa uma face através dos índices dos vértices que a compõem */
    private final int[][] faces;
}
