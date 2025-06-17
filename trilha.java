import java.util.*;

public class trilha {
    private static final Map<String, String> posicoes = new LinkedHashMap<>();
    private static final Map<String, List<String>> vizinhos = new HashMap<>();
    private static int turno = 0;
    public static void main(String[] args) {
        iniciarTabuleiro();
        iniciarVizinhos();
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem-vindo ao Trilha (Nine Men's Morris)!");
        System.out.println("Jogador 1 = @ | Jogador 2 = #");
        System.out.println("Digite letras de A a X para colocar peças.\n");

        while(turno < 18){
            mostrarTabuleiro();
            String jogador = (turno % 2 == 0) ? "@" : "#";
            System.out.print("Jogador " + jogador + ", escolha uma posição (A-X): ");
            String entrada = sc.nextLine().toUpperCase();

            if(!posicoes.containsKey(entrada)){
                System.out.println("Posicao invalida.");
                continue;
            }

            if(!posicoes.get(entrada).equals(" ")){
                System.out.println("Posicao ja ocupada.");
                continue;
            }

            posicoes.put(entrada, jogador);
            turno++;
        }

        System.out.println("\nTodas as peças foram colocadas! Fase de movimentacao iniciada.");
        while(true){
            mostrarTabuleiro();
            String jogador = (turno % 2 == 0) ? "@" : "#";
            System.out.println("Jogador " + jogador + ", é sua vez de mover.");

            System.out.print("Escolha a peça que deseja mover: ");
            String origem = sc.nextLine().toUpperCase();

            if(!posicoes.containsKey(origem) || !posicoes.get(origem).equals(jogador)){
                System.out.println("Essa posicao nao contem sua peca.");
                continue;
            }

            System.out.println("Escolha a nova posicao(adjacente e vazia)");
            String destino = sc.nextLine().toUpperCase();

            if (!posicoes.containsKey(destino) || !posicoes.get(destino).equals(" ")) {
                System.out.println("❌ Destino inválido ou já ocupado.");
                continue;
            }

            if (!vizinhos.get(origem).contains(destino)) {
                System.out.println("❌ As posições não são adjacentes.");
                continue;
            }

            //realizacao do movimento
            posicoes.put(origem, " ");
            posicoes.put(destino, jogador);
            turno++;
        }
    }

    private static void iniciarTabuleiro(){
        for(char c = 'A'; c <= 'X'; c++){
            posicoes.put(String.valueOf(c), " ");
        }
    }

    private static void iniciarVizinhos() {
    vizinhos.put("A", Arrays.asList("B", "J"));
    vizinhos.put("B", Arrays.asList("A", "C", "E"));
    vizinhos.put("C", Arrays.asList("B", "O"));
    vizinhos.put("D", Arrays.asList("E", "K"));
    vizinhos.put("E", Arrays.asList("B", "D", "F", "H"));
    vizinhos.put("F", Arrays.asList("E", "N"));
    vizinhos.put("G", Arrays.asList("H", "L"));
    vizinhos.put("H", Arrays.asList("E", "G", "I"));
    vizinhos.put("I", Arrays.asList("H", "M"));
    vizinhos.put("J", Arrays.asList("A", "K", "V"));
    vizinhos.put("K", Arrays.asList("D", "J", "L", "R"));
    vizinhos.put("L", Arrays.asList("G", "K", "S"));
    vizinhos.put("M", Arrays.asList("I", "N", "T"));
    vizinhos.put("N", Arrays.asList("C", "F", "M", "O"));
    vizinhos.put("O", Arrays.asList("N", "X"));
    vizinhos.put("P", Arrays.asList("Q", "L"));
    vizinhos.put("Q", Arrays.asList("P", "R", "K"));
    vizinhos.put("R", Arrays.asList("Q", "M"));
    vizinhos.put("S", Arrays.asList("L", "T"));
    vizinhos.put("T", Arrays.asList("S", "U", "M", "W"));
    vizinhos.put("U", Arrays.asList("T", "O"));
    vizinhos.put("V", Arrays.asList("J", "W"));
    vizinhos.put("W", Arrays.asList("V", "T", "X"));
    vizinhos.put("X", Arrays.asList("O", "W"));
    }

    private static void mostrarTabuleiro() {
    System.out.println();
    System.out.println(pos("A") + "-----" + pos("B") + "-----" + pos("C"));
    System.out.println("|     |     |");
    System.out.println("| " + pos("D") + "---" + pos("E") + "---" + pos("F") + " |");
    System.out.println("| |   |   | |");
    System.out.println("| | " + pos("G") + "-" + pos("H") + "-" + pos("I") + " | |");
    System.out.println("| | |   | | |");
    System.out.println(pos("J") + "-" + pos("K") + "-" + pos("L") + "   " + pos("M") + "-" + pos("N") + "-" + pos("O"));
    System.out.println("| | |   | | |");
    System.out.println("| | " + pos("P") + "-" + pos("Q") + "-" + pos("R") + " | |");
    System.out.println("| |   |   | |");
    System.out.println("| " + pos("S") + "---" + pos("T") + "---" + pos("U") + " |");
    System.out.println(pos("V") + "-----" + pos("W") + "-----" + pos("X"));
    System.out.println();
}

    private static String pos(String letra){
        String valor = posicoes.get(letra);
        return valor.equals(" ") ? letra : valor;
    }
}
