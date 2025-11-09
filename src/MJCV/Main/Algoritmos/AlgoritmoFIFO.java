package MJCV.Main.Algoritmos;
import MJCV.Main.Pagina;

/**
 * Mantém um ponteiro para a página mais antiga (a primeira a entrar)
 * e a substitui quando ocorre um Page Fault.
 */
public class AlgoritmoFIFO implements IAlgoritmoSubstituicao {

    // O ponteiro guarda o ÍNDICE (0 a 9) da próxima vítima.
    // Começa em 0 (a primeira moldura de página).
    private int ponteiroIndice = 0;

    /**
     * Encontra a vítima usando a lógica FIFO.
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {
        // 1. A vítima é a página para a qual o ponteiro está apontando.
        int indiceVitima = ponteiroIndice;

        // 2. Avança o ponteiro para a próxima posição.
        // Usamos o operador de módulo (%) para "dar a volta" (de 9 para 0).
        // Ex: (0+1)%10 = 1; (9+1)%10 = 0.
        ponteiroIndice = (ponteiroIndice + 1) % ram.length;

        // 3. Retorna o índice da vítima que encontramos no passo 1.
        return indiceVitima;
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