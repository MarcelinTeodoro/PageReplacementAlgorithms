package MJCV.Main.Algoritmos;

import MJCV.Main.Pagina;

/**.
 *
 * Esta é a implementação clássica e eficiente do conceito "Segunda Chance".
 * A lógica é funcionalmente idêntica à do AlgoritmoFIFOSC,
 * usando um ponteiro circular para encontrar uma vítima com R=0.
 */
public class AlgoritmoRelogio implements IAlgoritmoSubstituicao {

    // O "ponteiro do relógio" que aponta para a moldura a ser verificada.
    private int ponteiroIndice = 0;

    /**
     * Encontra a vítima usando a lógica do Relógio.
     *
     * @param ram O array de Páginas atualmente na Memória RAM.
     * @return O índice da página a ser substituída.
     */
    @Override
    public int encontrarIndiceVitima(Pagina[] ram) {

        // O loop 'while(true)' é idêntico ao FIFO-SC.
        // Ele garante que uma vítima será encontrada.
        while (true) {
            Pagina paginaCandidata = ram[ponteiroIndice];

            // 1. Verifica o Bit R
            if (paginaCandidata.getR() == 0) {
                // --- Vítima Encontrada (R=0) ---
                // Esta página não foi usada. É a vítima.
                int indiceVitima = ponteiroIndice;
                // Avança o ponteiro para a PRÓXIMA posição
                // (para a próxima verificação)
                ponteiroIndice = (ponteiroIndice + 1) % ram.length;
                // Retorna a vítima
                return indiceVitima;
            } else {
                // --- Segunda Chance (R=1) ---
                // Esta página foi usada.
                // 1. Zera o Bit R (dá a segunda chance)
                paginaCandidata.setR(0);
                // 2. Avança o ponteiro para verificar o próximo
                ponteiroIndice = (ponteiroIndice + 1) % ram.length;
            }
        }
    }
}
