package MJCV.Main.Algoritmos;

import MJCV.Main.Pagina;
import java.util.Random;

/**
 *
 * Combina o ponteiro do Relógio com o conceito de Working Set,
 * usando o Tempo de Envelhecimento (T) da página.
 *

 */
public class AlgoritmoWSClock implements IAlgoritmoSubstituicao {

    private int ponteiroIndice = 0;
    private Random random; //

    public AlgoritmoWSClock() {
        this.random = new Random();
    }

    /**
     * Encontra a vítima usando a lógica WS-Clock (com 3 passes).
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {

        int tamanhoRam = ram.length;
        int inicioDaVarredura = ponteiroIndice;

        // --- PASSE 1: Encontrar (R=0), Velha (EP>T) e Limpa (M=0) ---
        // (Durante este passe, se R=1, seta para R=0)
        do {
            Pagina p = ram[ponteiroIndice];

            if (p.getR() == 1) {
                // Se R=1, dá segunda chance e zera.
                p.setR(0);
            } else {
                // Se R=0, verifica a idade
                int T = p.getT();
                int EP = sortearEP(); // (Obs 3)

                if (EP > T) {
                    // É "velha". Verifica se está "limpa".
                    if (p.getM() == 0) {
                        // VÍTIMA PERFEITA (Velha e Limpa)
                        int indiceVitima = ponteiroIndice;
                        ponteiroIndice = (ponteiroIndice + 1) % tamanhoRam;
                        return indiceVitima;
                    }
                    // Se for "velha e suja", ignora por enquanto.
                }
                // Se for "jovem" (EP <= T), ignora.
            }

            // Avança o ponteiro
            ponteiroIndice = (ponteiroIndice + 1) % tamanhoRam;

        } while (ponteiroIndice != inicioDaVarredura); // Terminou a primeira volta


        // --- PASSE 2: Encontrar (R=0), Velha (EP>T) e Suja (M=1) ---
        // (Todos os R=1 já foram zerados no Passe 1)
        do {
            Pagina p = ram[ponteiroIndice];

            // Agora só nos importamos com R=0 (todos devem estar 0)
            int T = p.getT();
            int EP = sortearEP(); // (Obs 3)

            if (EP > T) {
                // É "velha". Não importa se M=0 ou M=1.
                // Esta é a melhor vítima que temos agora.
                int indiceVitima = ponteiroIndice;
                ponteiroIndice = (ponteiroIndice + 1) % tamanhoRam;
                return indiceVitima;
            }

            // Avança o ponteiro
            ponteiroIndice = (ponteiroIndice + 1) % tamanhoRam;

        } while (ponteiroIndice != inicioDaVarredura); // Terminou a segunda volta


        // --- PASSE 3: Fallback (Falha dos passes 1 e 2) ---
        // Se chegamos aqui, TODAS as páginas são "jovens" (EP <= T).
        // O Working Set é maior que a RAM (Thrashing).
        // O algoritmo "falha" e apenas remove a página atual (FIFO).
        int indiceVitima = ponteiroIndice;
        ponteiroIndice = (ponteiroIndice + 1) % tamanhoRam;
        return indiceVitima;
    }

    /**
     * Método auxiliar para sortear o EP.
     * @return um valor aleatório entre 100 e 9999.
     */
    private int sortearEP() {
        // Sorteia de 0 a 9899, depois soma 100.
        return random.nextInt(9900) + 100;
    }


    // --- Métodos da Interface (Não usados pela Obs 3) ---
    // A Obs 3 simplificou o WS-Clock, fazendo-o não precisar
    // rastrear o tempo de acesso (que o LRU precisava).


    @Override
    public void notificarHit(int indiceNaRam, int cicloGlobal) {
        //Não se importa com Hits.
    }

    @Override
    public void notificarMiss(int indiceNaRam, int cicloGlobal) {
        //Não se importa com o tempo do Miss.
    }
}
