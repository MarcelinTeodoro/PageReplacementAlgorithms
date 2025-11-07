package MJCV.Main;

import java.util.Random;

/**
 * Gerencia um conjunto de Páginas (seja a RAM ou a SWAP).
 * Esta classe abstrai a "matriz" mencionada no problema.
 * A matriz é, na verdade, um array de objetos Pagina.
 */
public class Memoria {

    private Pagina[] paginas;
    private int tamanho;
    private Random random; // Usado para sorteios

    /**
     * Construtor da Memória.
     * @param tamanho O número de "linhas" (10 para RAM, 100 para SWAP).
     */
    public Memoria(int tamanho) {
        this.tamanho = tamanho;
        this.paginas = new Pagina[tamanho];
        this.random = new Random();
    }

    /**
     * (Fase 1) Preenche esta memória como se fosse a MATRIZ SWAP.
     * Segue as regras de inicialização do problema.
     */
    public void inicializarSwap() {
        if (this.tamanho != 100) {
            System.out.println("Erro: A inicialização SWAP é apenas para memória de tamanho 100.");
            return;
        }

        for (int i = 0; i < 100; i++) {
            int N = i;
            int I = i + 1;
            int D = random.nextInt(50) + 1;       // Sorteia de 0-49, soma 1 -> (1 a 50)
            int R = 0;
            int M = 0;
            int T = random.nextInt(9900) + 100; // Sorteia de 0-9899, soma 100 -> (100 a 9999)

            paginas[i] = new Pagina(N, I, D, R, M, T);
        }
    }

    /**
     * (Fase 1) Preenche esta memória como se fosse a MATRIZ RAM.
     * Sorteia 10 páginas da SWAP e as copia para cá.
     * @param swap A memória SWAP de onde as páginas serão copiadas.
     */
    public void inicializarRam(Memoria swap) {
        if (this.tamanho != 10) {
            System.out.println("Erro: A inicialização RAM é apenas para memória de tamanho 10.");
            return;
        }

        for (int i = 0; i < 10; i++) {
            int indiceSorteadoSwap = random.nextInt(100); // Sorteia de 0 a 99
            Pagina paginaDaSwap = swap.getPagina(indiceSorteadoSwap);

            // Usamos o "construtor de cópia" para garantir que é um NOVO objeto
            paginas[i] = new Pagina(paginaDaSwap);
        }
    }

    /**
     * (Fase 1) Imprime o estado atual da memória (RAM ou SWAP) 
     * de forma formatada (Obs 6).
     */
    public void imprimirEstado(String titulo) {
        System.out.println("\n--- " + titulo + " ---");
        // Cabeçalho da tabela
        System.out.println("+--------+--------+--------+---+---+--------+");
        System.out.println("|   N    |   I    |   D    | R | M |    T   |");
        System.out.println("+--------+--------+--------+---+---+--------+");

        for (Pagina p : paginas) {
            if (p != null) {
                // Imprime linha formatada
                System.out.printf("| %-6d | %-6d | %-6d | %d | %d | %-6d |\n",
                        p.getN(), p.getI(), p.getD(), p.getR(), p.getM(), p.getT());
            }
        }
        System.out.println("+--------+--------+--------+---+---+--------+");
    }

    /**
     * (Fase 1) Procura uma página na memória pela sua Instrução (I).
     * @return A Pagina, se encontrada; ou null, se não encontrada (Page Fault).
     */
    public Pagina buscarPaginaPorInstrucao(int instrucao) {
        for (Pagina p : paginas) {
            if (p.getI() == instrucao) {
                return p; // Encontrou
            }
        }
        return null; // Não encontrou (Page Fault)
    }

    /**
     * (Fase 1) Zera o Bit R de todas as páginas nesta memória (Obs 4).
     */
    public void zerarBitsR() {
        for (Pagina p : paginas) {
            if (p != null) {
                p.setR(0);
            }
        }
    }

    // --- Métodos de acesso que serão usados pelo Simulador e Algoritmos ---

    /**
     * Retorna o array completo de páginas.
     * (Usado pelo Simulador para passar para os algoritmos).
     */
    public Pagina[] getPaginas() {
        return paginas;
    }

    /**
     * Retorna uma página específica pelo seu índice (posição na RAM/SWAP).
     */
    public Pagina getPagina(int indice) {
        if (indice >= 0 && indice < tamanho) {
            return paginas[indice];
        }
        return null;
    }

    /**
     * Substitui uma página em um índice específico.
     * (Usado no Page Fault).
     */
    public void substituirPagina(int indice, Pagina novaPagina) {
        if (indice >= 0 && indice < tamanho) {
            paginas[indice] = novaPagina;
        }
    }

    public int getIndiceDaPagina(Pagina pagina) {
        for (int i = 0; i < tamanho; i++) {
            // Compara a referência do objeto
            if (paginas[i] == pagina) {
                return i;
            }
        }
        return -1; // Não deve acontecer
    }
}