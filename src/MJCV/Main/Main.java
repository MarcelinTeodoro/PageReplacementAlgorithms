package MJCV.Main;

import MJCV.Main.Algoritmos.*;

/**
 * Ponto de entrada do Simulador (Fase 5).
 *
 * Este Main orquestra a execução completa:
 * 1. Cria as memórias.
 * 2. Imprime o estado inicial.
 * 3. Cria e executa o simulador para cada algoritmo.
 * 4. Imprime o estado final.
 */
public class Main {

    public static void main(String[] args) {

        executarSimulacaoParaAlgoritmo(new AlgoritmoFIFO());
        executarSimulacaoParaAlgoritmo(new AlgoritmoNRU());
        executarSimulacaoParaAlgoritmo(new AlgoritmoFIFOSC());
        executarSimulacaoParaAlgoritmo(new AlgoritmoRelogio());
        executarSimulacaoParaAlgoritmo(new AlgoritmoLRU());

    }

    /**
     * Método auxiliar para rodar UMA simulação completa (Obs 6).
     * Ele cria um conjunto "limpo" de memórias, roda as 1000 instruções
     * e imprime o estado antes e depois.
     *
     * @param algoritmo A Estratégia (algoritmo) a ser usada.
     */
    private static void executarSimulacaoParaAlgoritmo(IAlgoritmoSubstituicao algoritmo) {

        // 1. Crie a MATRIZ SWAP (100x6)
        Memoria swap = new Memoria(100);
        swap.inicializarSwap();

        // 2. Crie a MATRIZ RAM (10x6)
        Memoria ram = new Memoria(10);
        ram.inicializarRam(swap);

        // 3. Imprimir o estado inicial (Obs 6 - Início)
        System.out.println("\n=======================================================");
        System.out.println(">>> ESTADO INICIAL (Algoritmo: " + algoritmo.getClass().getSimpleName() + ") <<<");
        System.out.println("=======================================================");
        ram.imprimirEstado("MATRIZ RAM (Inicial)");
        swap.imprimirEstado("MATRIZ SWAP (Inicial)");

        // 4. Criar e rodar o simulador
        Simulador simulador = new Simulador(ram, swap, algoritmo);
        simulador.executarSimulacao();

        // 5. Imprimir o estado final (Obs 6 - Final)
        System.out.println("\n=======================================================");
        System.out.println(">>> ESTADO FINAL (Algoritmo: " + algoritmo.getClass().getSimpleName() + ") <<<");
        System.out.println("=======================================================");
        ram.imprimirEstado("MATRIZ RAM (Final)");
        swap.imprimirEstado("MATRIZ SWAP (Final)");
    }
}