package MJCV.Main;

import java.util.Random;
import MJCV.Main.Algoritmos.IAlgoritmoSubstituicao;

/**
 * (Fase 3) O motor do simulador.
 * Esta classe orquestra a execução das 1000 instruções,
 * trata os Page Hits e Page Faults (Miss), e gerencia
 * a comunicação entre a RAM e a SWAP.
 */
public class Simulador {

    // Constantes da Simulação
    private static final int TOTAL_INSTRUCOES = 1000;
    private static final int INTERVALO_RESET_BIT_R = 10;
    private static final int CHANCE_MODIFICACAO = 50; // (em %)

    // Componentes do Simulador
    private Memoria ram;
    private Memoria swap;
    private IAlgoritmoSubstituicao algoritmo; // A "Estratégia"
    private Random random;

    /**
     * Construtor do Simulador.
     * Recebe as memórias e o algoritmo (Estratégia) a ser usado.
     *
     * @param ram       A memória RAM pré-inicializada.
     * @param swap      A memória SWAP pré-inicializada.
     * @param algoritmo O algoritmo de substituição a ser executado.
     */
    public Simulador(Memoria ram, Memoria swap, IAlgoritmoSubstituicao algoritmo) {
        this.ram = ram;
        this.swap = swap;
        this.algoritmo = algoritmo;
        this.random = new Random();
    }

    /**
     * Método principal que executa o loop de 1000 instruções (Obs 1).
     */
    public void executarSimulacao() {
        System.out.println("\n--- Iniciando Simulação com o Algoritmo: " +
                algoritmo.getClass().getSimpleName() + " ---");

        for (int i = 1; i <= TOTAL_INSTRUCOES; i++) {

            // 1. Sortear a instrução (de 1 a 100)
            int instrucaoSorteada = random.nextInt(100) + 1;

            // 2. Procurar na RAM
            Pagina paginaEncontrada = ram.buscarPaginaPorInstrucao(instrucaoSorteada);

            if (paginaEncontrada != null) {
                // 3.A. PAGE HIT (Encontrou!)
                tratarHitDePagina(paginaEncontrada);
            } else {
                // 3.B. PAGE FAULT (Não encontrou!)
                tratarFaltaDePagina(instrucaoSorteada);
            }

            // 4. Resetar o Bit R a cada 10 instruções (Obs 4)
            if (i % INTERVALO_RESET_BIT_R == 0) {
                //System.out.println("--- (Ciclo " + i + ": Zerando Bits R) ---");
                ram.zerarBitsR();
            }
        }

        System.out.println("--- Simulação Concluída (" +
                algoritmo.getClass().getSimpleName() + ") ---");
    }

    /**
     * Lógica executada quando a página é encontrada na RAM (Page Hit).
     * (Obs 2)
     *
     * @param pagina A página que foi encontrada na RAM.
     */
    private void tratarHitDePagina(Pagina pagina) {
        // 1. O bit de acesso R recebe 1
        pagina.setR(1);

        // 2. A página terá 50% de chance de sofrer modificação
        if (random.nextInt(100) < CHANCE_MODIFICACAO) {

            // 2.1. O campo Dado (D) será atualizado (D = D + 1)
            pagina.setD(pagina.getD() + 1);

            // 2.2. O campo Modificado (M) será atualizado (M = 1)
            pagina.setM(1);
        }
    }

    /**
     * Lógica executada quando a página NÃO é encontrada na RAM (Page Fault).
     * (Obs 5)
     *
     * @param instrucaoQueFaltou A instrução (I) que precisa ser carregada.
     */
    private void tratarFaltaDePagina(int instrucaoQueFaltou) {

        // 1. Pedir ao algoritmo para encontrar a vítima
        int indiceVitima = algoritmo.encontrarIndiceVitima(ram.getPaginas());
        Pagina paginaVitima = ram.getPagina(indiceVitima);

        // 2. Verificar se a página vítima foi modificada (M=1) (Obs 5)
        if (paginaVitima.getM() == 1) {
            // Se M=1, ela deve ser salva de volta na SWAP.
            // O N da página vítima é o índice dela na SWAP.
            int indiceSwap = paginaVitima.getN();
            Pagina paginaNaSwap = swap.getPagina(indiceSwap);

            // Atualiza a página na SWAP (o método já seta M=0)
            paginaNaSwap.atualizarDados(paginaVitima);
        }

        // 3. Buscar a nova página na SWAP para carregar na RAM.
        // A instrução (I) é N+1. Portanto, N = I - 1.
        int indiceNovaPagina = instrucaoQueFaltou - 1;
        Pagina paginaDaSwap = swap.getPagina(indiceNovaPagina);

        // 4. Criar uma CÓPIA da página da SWAP (importante!)
        Pagina novaPaginaParaRam = new Pagina(paginaDaSwap);

        // 5. Substituir a vítima na RAM pela nova página
        ram.substituirPagina(indiceVitima, novaPaginaParaRam);
    }
}
