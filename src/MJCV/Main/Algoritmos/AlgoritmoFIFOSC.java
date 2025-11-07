package MJCV.Main.Algoritmos;

import MJCV.Main.Pagina;

/**
 * (Fase 4) Implementação do algoritmo FIFO-Second Chance (FIFO-SC).
 *
 * É uma melhoria do FIFO que usa o Bit R (Acesso) para evitar
 * remover páginas que foram recentemente utilizadas.
 */
public class AlgoritmoFIFOSC implements IAlgoritmoSubstituicao {

    // O ponteiro guarda o ÍNDICE (0 a 9) da próxima página a ser VERIFICADA.
    private int ponteiroIndice = 0;

    /**
     * Encontra a vítima usando a lógica FIFO-SC.
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {

        // Este loop 'while(true)' é garantido de parar.
        // No pior caso, ele dá uma volta completa na RAM,
        // zera todos os bits R, e na segunda volta encontra
        // a página original (agora com R=0).
        while (true) {

            Pagina paginaCandidata = ram[ponteiroIndice];

            // Verifica o Bit R
            if (paginaCandidata.getR() == 0) {
                // --- Vítima Encontrada (R=0) ---
                // Esta página não foi usada recentemente. É a vítima.
                int indiceVitima = ponteiroIndice;

                // Avança o ponteiro para a posição SEGUINTE à vítima
                ponteiroIndice = (ponteiroIndice + 1) % ram.length;

                // Retorna a vítima
                return indiceVitima;

            } else {
                // --- Segunda Chance (R=1) ---
                // Esta página foi usada. Damos uma segunda chance.

                // 1. Zera o Bit R
                paginaCandidata.setR(0);

                // 2. Avança o ponteiro para verificar o próximo
                ponteiroIndice = (ponteiroIndice + 1) % ram.length;

                // O loop continua, e ele vai verificar a próxima página
            }
        }
    }
}