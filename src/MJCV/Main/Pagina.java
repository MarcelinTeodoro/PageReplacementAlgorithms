package MJCV.Main;

/**
 * Representa uma única página de memória.
 * Contém todos os campos definidos no problema:
 * N: Número da Página
 * I: Instrução
 * D: Dado
 * R: Bit de Acesso (Referência)
 * M: Bit de Modificação (Dirty bit)
 * T: Tempo de Envelhecimento
 */
public class Pagina {

    private int N;
    private int I;
    private int D;
    private int R;
    private int M;
    private int T;

    /**
     * Construtor para criar uma nova página com todos os seus dados.
     */
    public Pagina(int n, int i, int d, int r, int m, int t) {
        this.N = n;
        this.I = i;
        this.D = d;
        this.R = r;
        this.M = m;
        this.T = t;
    }

    /**
     * Construtor de Cópia.
     * Essencial para copiar uma página da SWAP para a RAM sem usar a mesma
     * referência de objeto.
     */
    public Pagina(Pagina outra) {
        this.N = outra.N;
        this.I = outra.I;
        this.D = outra.D;
        this.R = outra.R;
        this.M = outra.M;
        this.T = outra.T;
    }

    // --- Getters (Métodos para ler dados) ---

    public int getN() {
        return N;
    }

    public int getI() {
        return I;
    }

    public int getD() {
        return D;
    }

    public int getR() {
        return R;
    }

    public int getM() {
        return M;
    }

    public int getT() {
        return T;
    }

    // --- Setters (Métodos para alterar dados) ---
    // Não temos setters para N e I, pois eles identificam a página.
    // Mas precisaremos alterar D, R, M, e T.

    public void setD(int d) {
        this.D = d;
    }

    public void setR(int r) {
        this.R = r;
    }

    public void setM(int m) {
        this.M = m;
    }

    public void setT(int t) {
        this.T = t;
    }

    /**
     * Método auxiliar para copiar dados de outra página.
     * Útil para quando a RAM (M=1) atualiza a SWAP.
     */
    public void atualizarDados(Pagina paginaModificada) {
        this.D = paginaModificada.getD();
        this.T = paginaModificada.getT();
        this.M = 0; // Sempre salva na SWAP com M=0
        this.R = 0; // Bit R também pode ser zerado ao salvar
    }
}