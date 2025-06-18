import java.util.*;

public class trilha {
    private static final Map<String, String> posicoes = new LinkedHashMap<>();
    private static final Map<String, List<String>> vizinhos = new HashMap<>();
    private static final List<List<String>> trilhas = new ArrayList<>();
    private static int turno = 0;

    public static void main(String[] args) {
        iniciarTabuleiro();
        iniciarVizinhos();
        iniciarTrilhas();
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem-vindo ao Trilha (Nine Men's Morris)!");
        System.out.println("Jogador 1 = @ | Jogador 2 = #");
        System.out.println("Digite letras de A a X para colocar peças.\n");

        while (turno < 18) {
            mostrarTabuleiro();
            String jogador = (turno % 2 == 0) ? "@" : "#";
            System.out.print("Jogador " + jogador + ", escolha uma posição (A-X): ");
            String entrada = sc.nextLine().toUpperCase();

            if (!posicoes.containsKey(entrada)) {
                System.out.println("Posicao invalida.");
                continue;
            }

            if (!posicoes.get(entrada).equals(" ")) {
                System.out.println("Posicao ja ocupada.");
                continue;
            }

            posicoes.put(entrada, jogador);

            if (formouTrilha(entrada, jogador)) {
                System.out.println("🎯 Jogador " + jogador + " formou uma trilha!");
                removerPeca(jogador, sc);
            }

            turno++;
        }

        System.out.println("\nTodas as peças foram colocadas! Fase de movimentacao iniciada.");
        while (true) {
            mostrarTabuleiro();
            String jogador = (turno % 2 == 0) ? "@" : "#";
            System.out.println("Jogador " + jogador + ", é sua vez de mover.");

            System.out.print("Escolha a peça que deseja mover: ");
            String origem = sc.nextLine().toUpperCase();

            if (!posicoes.containsKey(origem) || !posicoes.get(origem).equals(jogador)) {
                System.out.println("Essa posicao nao contem sua peca.");
                continue;
            }

            System.out.println("Escolha a nova posicao(adjacente e vazia)");
            String destino = sc.nextLine().toUpperCase();

            if (!posicoes.containsKey(destino) || !posicoes.get(destino).equals(" ")) {
                System.out.println("❌ Destino inválido ou já ocupado.");
                continue;
            }

            if (contarPecas(jogador) > 3 && !vizinhos.get(origem).contains(destino)) {
                System.out.println("❌ As posições não são adjacentes.");
                continue;
            }

            // realizacao do movimento
            posicoes.put(origem, " ");
            posicoes.put(destino, jogador);
            if (formouTrilha(destino, jogador)) {
                System.out.println("🎯 Jogador " + jogador + " formou uma trilha!");
                removerPeca(jogador, sc);
            }

            // // verifica vitoria
            // String oponente = jogador.equals("@") ? "#" : "@";
            // if (contarPecas(oponente) < 3 || !temMovimentosPossiveis(oponente)){
            //     System.out.println("🏁 Jogador " + jogador + " venceu! Oponente sem jogadas possíveis.");
            //     break;
            // }

            turno++;
        }
    }

    private static void iniciarTabuleiro() {
        for (char c = 'A'; c <= 'X'; c++) {
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
        vizinhos.put("K", Arrays.asList("D", "J", "L", "S"));
        vizinhos.put("L", Arrays.asList("G", "K", "P"));
        vizinhos.put("M", Arrays.asList("I", "N", "R"));
        vizinhos.put("N", Arrays.asList("F", "M", "O", "U"));
        vizinhos.put("O", Arrays.asList("N", "C", "X"));
        vizinhos.put("P", Arrays.asList("Q", "L"));
        vizinhos.put("Q", Arrays.asList("P", "R", "T"));
        vizinhos.put("R", Arrays.asList("Q", "M"));
        vizinhos.put("S", Arrays.asList("K", "T"));
        vizinhos.put("T", Arrays.asList("S", "U", "Q", "W"));
        vizinhos.put("U", Arrays.asList("T", "N"));
        vizinhos.put("V", Arrays.asList("J", "W"));
        vizinhos.put("W", Arrays.asList("V", "T", "X"));
        vizinhos.put("X", Arrays.asList("O", "W"));
    }

    private static void iniciarTrilhas() {
        // trilhas horizontais
        trilhas.add(Arrays.asList("A", "B", "C"));
        trilhas.add(Arrays.asList("D", "E", "F"));
        trilhas.add(Arrays.asList("G", "H", "I"));
        trilhas.add(Arrays.asList("J", "K", "L"));
        trilhas.add(Arrays.asList("M", "N", "O"));
        trilhas.add(Arrays.asList("P", "Q", "R"));
        trilhas.add(Arrays.asList("S", "T", "U"));
        trilhas.add(Arrays.asList("V", "W", "X"));
        // trilhas verticais
        trilhas.add(Arrays.asList("A", "J", "V"));
        trilhas.add(Arrays.asList("D", "K", "S"));
        trilhas.add(Arrays.asList("G", "L", "P"));
        trilhas.add(Arrays.asList("B", "E", "H"));
        trilhas.add(Arrays.asList("Q", "T", "W"));
        trilhas.add(Arrays.asList("I", "M", "R"));
        trilhas.add(Arrays.asList("F", "N", "U"));
        trilhas.add(Arrays.asList("C", "O", "X"));
    }

    private static void mostrarTabuleiro() {
        System.out.println();
        System.out.println(pos("A") + "-----" + pos("B") + "-----" + pos("C"));
        System.out.println("|     |     |");
        System.out.println("| " + pos("D") + "---" + pos("E") + "---" + pos("F") + " |");
        System.out.println("| |   |   | |");
        System.out.println("| | " + pos("G") + "-" + pos("H") + "-" + pos("I") + " | |");
        System.out.println("| | |   | | |");
        System.out.println(
                pos("J") + "-" + pos("K") + "-" + pos("L") + "   " + pos("M") + "-" + pos("N") + "-" + pos("O"));
        System.out.println("| | |   | | |");
        System.out.println("| | " + pos("P") + "-" + pos("Q") + "-" + pos("R") + " | |");
        System.out.println("| |   |   | |");
        System.out.println("| " + pos("S") + "---" + pos("T") + "---" + pos("U") + " |");
        System.out.println(pos("V") + "-----" + pos("W") + "-----" + pos("X"));
        System.out.println();
    }

    private static String pos(String letra) {
        String valor = posicoes.get(letra);
        return valor.equals(" ") ? letra : valor;
    }

    private static boolean formouTrilha(String pos, String jogador) {
        for (List<String> linha : trilhas) {
            if (linha.contains(pos)) {
                if (linha.stream().allMatch(p -> posicoes.get(p).equals(jogador))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void removerPeca(String jogadorAtual, Scanner sc) {
        String oponente = jogadorAtual.equals("@") ? "#" : "@";

        List<String> removiveis = new ArrayList<>();

        // encontra pecas do oponente fora de trilhas
        for (String pos : posicoes.keySet()) {
            if (posicoes.get(pos).equals(oponente) && !formouTrilha(pos, oponente)){
                removiveis.add(pos);
            }
        }

        // se nao existe nenhuma peca fora da trilha permite a remocao de uma peca de uma trilha
        if (removiveis.isEmpty()){
            for (String pos : posicoes.keySet()) {
                if (posicoes.get(pos).equals(oponente)) {
                    removiveis.add(pos);
                }
            }
        }

        if (removiveis.isEmpty()){
            System.out.println("Nenhuma peca do jogador esta disponivel para remocao.");
            System.out.println("Jogador " + jogadorAtual + " ganhou o jogo!");
        }

        while (true) {
            System.out.print("Escolha uma peça do oponente (" + oponente + ") para remover: ");
            String entrada = sc.nextLine().toUpperCase();
            if(removiveis.contains(entrada)){
                posicoes.put(entrada, " ");
                System.out.println("✅ Peça em " + entrada + " removida com sucesso.");
                break;
            } else { 
                System.out.println("❌ Escolha inválida. Tente uma das seguintes: " + removiveis);
            }
        }

        if (contarPecas(oponente) < 3 || !temMovimentosPossiveis(oponente)) {
            System.out.println("🏁 Jogador " + jogadorAtual + " venceu! Oponente não pode mais jogar.");
            System.exit(0); // Encerra o jogo
        }

    }

    private static int contarPecas(String jogador){
        int count = 0;
        for(String p : posicoes.values()){
            if (p.equals(jogador)){
                count++;
            }
        }
        return count;
    }

    private static boolean temMovimentosPossiveis(String jogador) {
        if (contarPecas(jogador) <= 3) return true;

        for(String origem : posicoes.keySet()) {
            if (posicoes.get(origem).equals(jogador)){
                for(String vizinho : vizinhos.get(origem)){
                    if(posicoes.get(vizinho).equals(" ")){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
