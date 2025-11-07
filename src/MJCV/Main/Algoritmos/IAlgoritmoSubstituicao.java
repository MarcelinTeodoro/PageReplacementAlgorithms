package MJCV.Main.Algoritmos;
import MJCV.Main.Pagina;

/**
 * Interface (Contrato) para todos os Algoritmos de Substituição de Página.
 * (Atualizado para suportar LRU)
 */
public interface IAlgoritmoSubstituicao {

    /**
     * O método central do algoritmo (chamado em Page Fault).
     * Decide qual página será a "vítima" (removida).
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice (posição de 0 a 9) da página que deve ser substituída.
     */
    public int encontrarIndiceVitima(Pagina[] ram);

    // --- NOVOS MÉTODOS PARA O LRU E OUTROS ALGORITMOS "TIME-BASED" ---

    /**
     * Notifica o algoritmo que um Page Hit ocorreu.
     * (O Simulador chama isso).
     *
     * @param indiceNaRam A posição (0-9) na RAM que foi acessada.
     * @param cicloGlobal O "timestamp" do acesso (ciclo de 1 a 1000).
     */
    public void notificarHit(int indiceNaRam, int cicloGlobal);

    /**
     * Notifica o algoritmo que um Page Miss foi tratado.
     * (O Simulador chama isso após carregar a nova página).
     *
     * @param indiceNaRam A posição (0-9) na RAM onde a nova página foi colocada.
     * @param cicloGlobal O "timestamp" do acesso (ciclo de 1 a 1000).
     */
    public void notificarMiss(int indiceNaRam, int cicloGlobal);
}


