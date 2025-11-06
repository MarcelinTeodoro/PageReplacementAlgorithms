package MJCV.Main;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Fase 1: Planificando o terreno...");

        // 1. Crie a MATRIZ SWAP (100x6)
        Memoria swap = new Memoria(100);
        swap.inicializarSwap();

        // 2. Crie a MATRIZ RAM (10x6)
        Memoria ram = new Memoria(10);
        ram.inicializarRam(swap);

        // 3. Imprimir o estado inicial (Obs 6)
        System.out.println(">>> ESTADO INICIAL DAS MEMÓRIAS <<<");
        ram.imprimirEstado("MATRIZ RAM (Inicial)");
        swap.imprimirEstado("MATRIZ SWAP (Inicial)");


    }
}