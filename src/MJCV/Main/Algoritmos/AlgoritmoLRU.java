package MJCV.Main.Algoritmos;
import MJCV.Main.Pagina;

public class AlgoritmoLRU implements IAlgoritmoSubstituicao {

    // Array para rastrear o "ciclo" do último acesso.
    // O índice do array (0-9) corresponde à moldura da RAM.
    private long[] ultimoAcesso;

    // Flag para inicializar o array na primeira chamada
    private boolean foiInicializado = false;

    /**
     * Inicializa o array de timestamps.
     */
    private void inicializar(int tamanhoRam) {
        this.ultimoAcesso = new long[tamanhoRam];
        for (int i = 0; i < tamanhoRam; i++) {
            this.ultimoAcesso[i] = 0; // Começa com 0 (o tempo mais antigo)
        }
        this.foiInicializado = true;
    }

    /**
     * Encontra a vítima: a página com o MENOR (mais antigo) timestamp.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {
        if (!foiInicializado) {
            inicializar(ram.length);
        }

        long tempoMaisAntigo = Long.MAX_VALUE;
        int indiceVitima = 0;

        // Procura na RAM (0-9) pela página com o
        // tempo (ciclo) de acesso mais antigo (menor).
        for (int i = 0; i < ultimoAcesso.length; i++) {
            if (ultimoAcesso[i] < tempoMaisAntigo) {
                tempoMaisAntigo = ultimoAcesso[i];
                indiceVitima = i;
            }
        }

        return indiceVitima;
    }

    /**
     * Um Page Hit ocorreu: a página neste índice ACABOU de ser usada.
     * Atualiza seu "timestamp" para o ciclo atual (o mais recente).
     */
    @Override
    public void notificarHit(int indiceNaRam, int cicloGlobal) {
        if (!foiInicializado) {
            inicializar(10); // Valor padrão
        }
        this.ultimoAcesso[indiceNaRam] = cicloGlobal;
    }

    /**
     * Um Page Miss ocorreu: uma nova página foi colocada neste índice.
     * Ela é, agora, a "mais recentemente usada".
     * Atualiza seu "timestamp".
     */
    @Override
    public void notificarMiss(int indiceNaRam, int cicloGlobal) {
        if (!foiInicializado) {
            inicializar(10); // Valor padrão
        }
        this.ultimoAcesso[indiceNaRam] = cicloGlobal;
    }
}
