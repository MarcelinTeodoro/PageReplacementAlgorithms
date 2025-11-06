package MJCV.Main.Algoritmos;
import MJCV.Main.Pagina;

/**
 * Interface (Contrato) para todos os Algoritmos de Substituição de Página.
 * (Padrão de Projeto: Strategy)
 * * Qualquer algoritmo (FIFO, LRU, NRU, etc.) DEVE implementar esta interface
 * para ser usado pelo Simulador.
 */
public interface IAlgoritmoSubstituicao {

    /**
     * O método central do algoritmo.
     * Ele analisa o estado atual da RAM e decide qual página será
     * a "vítima" (removida).
     * * Cada classe que implementar esta interface manterá seu próprio estado
     * interno, se necessário (ex: o ponteiro do Relógio, a fila do FIFO).
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice (posição de 0 a 9) da página que deve ser substituída.
     */
    public int encontrarIndiceVitima(Pagina[] ram);

}
