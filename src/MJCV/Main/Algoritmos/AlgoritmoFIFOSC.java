package MJCV.Main.Algoritmos;

import MJCV.Main.Pagina;
import java.util.LinkedList;
import java.util.Queue;

/**
 * (Fase 4 - Refatorado) Implementação CONCEITUAL do FIFO-Second Chance (FIFO-SC).
 *
 * Esta versão usa uma Fila (Queue) real para simular a lógica
 * de "remover da frente" e "colocar no fim".
 * É didaticamente correta, mas menos eficiente que o AlgoritmoRelogio.
 */
public class AlgoritmoFIFOSC implements IAlgoritmoSubstituicao {

    // Uma fila para manter a ordem FIFO dos ÍNDICES (0-9).
    private Queue<Integer> filaFIFO;

    // Flag para inicializar a fila apenas uma vez.
    private boolean foiInicializado = false;

    /**
     * Preenche a fila inicial com os índices da RAM (0 a 9)
     * na primeira vez que o algoritmo é chamado.
     */
    private void inicializarFila(int tamanhoRam) {
        this.filaFIFO = new LinkedList<>();
        for (int i = 0; i < tamanhoRam; i++) {
            this.filaFIFO.add(i); // Adiciona 0, 1, 2, ..., 9
        }
        this.foiInicializado = true;
    }

    /**
     * Encontra a vítima usando a lógica de Fila do FIFO-SC.
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {

        // Se for a primeira execução, preenche a fila (0-9)
        if (!foiInicializado) {
            inicializarFila(ram.length);
        }

        while (true) {
            // 1. Pega o índice na FRENTE da fila (e remove)
            int indiceCandidato = filaFIFO.poll(); // Ex: remove o '0'

            Pagina paginaCandidata = ram[indiceCandidato];

            // 2. Verifica o Bit R
            if (paginaCandidata.getR() == 0) {
                // --- Vítima Encontrada (R=0) ---
                // O simulador vai colocar uma nova página neste índice.
                // Colocamos o índice no FIM da fila, pois agora é o "mais novo".
                filaFIFO.add(indiceCandidato); // Ex: adiciona o '0' no fim

                return indiceCandidato; // Retorna 0 como vítima

            } else {
                // --- Segunda Chance (R=1) ---

                // 1. Zera o Bit R
                paginaCandidata.setR(0);

                // 2. Coloca o índice de volta no FIM da fila
                filaFIFO.add(indiceCandidato);

                // O loop continua para verificar o próximo da fila...
            }
        }
    }
    @Override
    public void notificarHit(int indiceNaRam, int cicloGlobal) {
        // O FIFO não se importa com Hits.
    }

    @Override
    public void notificarMiss(int indiceNaRam, int cicloGlobal) {
        // O FIFO não se importa com o tempo do Miss.
    }
}