package MJCV.Main.Algoritmos;

import MJCV.Main.Pagina;
/**
 *
 * Classifica as páginas em 4 classes com base nos bits R e M
 * e escolhe uma vítima da classe mais baixa (menos usada).
 */
public class AlgoritmoNRU implements IAlgoritmoSubstituicao {

    /**
     * Encontra a vítima usando a lógica NRU.
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {

        // O algoritmo NRU funciona melhor quando os bits R são resetados
        // periodicamente, o que o nosso Simulador (Fase 3) já faz (Obs 4).

        // Pass 1: Procurar por Classe 0 (R=0, M=0)
        for (int i = 0; i < ram.length; i++) {
            Pagina p = ram[i];
            if (p.getR() == 0 && p.getM() == 0) {
                return i; // Encontrou a melhor vítima
            }
        }

        // Pass 2: Procurar por Classe 1 (R=0, M=1)
        for (int i = 0; i < ram.length; i++) {
            Pagina p = ram[i];
            if (p.getR() == 0 && p.getM() == 1) {
                return i;
            }
        }

        // Pass 3: Procurar por Classe 2 (R=1, M=0)
        for (int i = 0; i < ram.length; i++) {
            Pagina p = ram[i];
            if (p.getR() == 1 && p.getM() == 0) {
                return i;
            }
        }

        // Pass 4: Procurar por Classe 4 (R=1, M=1)
        for (int i = 0; i < ram.length; i++) {
            Pagina p = ram[i];
            if (p.getR() == 1 && p.getM() == 1) {
                return i;
            }
        }

        // Caso extremo (não deveria acontecer, mas por segurança):
        // Se todas as páginas forem R=1, M=1, apenas retorne a primeira.
        return 0;
    }
    @Override
    public void notificarHit(int indiceNaRam, int cicloGlobal) {
        //Não se importa com Hits.
    }

    @Override
    public void notificarMiss(int indiceNaRam, int cicloGlobal) {
        //Não se importa com o tempo do Miss.
    }
}
