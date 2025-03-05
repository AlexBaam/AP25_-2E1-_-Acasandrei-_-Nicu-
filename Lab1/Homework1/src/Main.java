import java.util.*;

public class Main {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println(
                    "Usage: Two integers are required as arguments");
            System.exit(-1);
        }

        long startTimer = System.nanoTime(); // Timp in nanosecunde;

        // Cele 2 argumente sunt luate din parametri de la edit;
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        if( n >= 2*k) {
            int[][] graph_matrix = new int[n][n]; // Matrice de n*n

            // Initializam matricea cu 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    graph_matrix[i][j] = 0;
                }
            }

            // Trebuie construita clica
            //Ptr a randomiza clica facem un array de la 1 la n
            int[] nodesArray = new int[n];
            for (int i = 0; i < n; i++) {
                nodesArray[i] = i + 1;
            }

            if(n > 50){
                System.out.println("Array over 50");
            }else{
                System.out.println("Initial array:");
                for (int i = 0; i < n; i++) {
                    System.out.print(nodesArray[i] + " ");
                }
            }

            shuffleArray(nodesArray);
            if(n > 50){
                System.out.println("\nArray over 50");
            }else {
                System.out.println("\nShuffled array:");
                for (int i = 0; i < n; i++) {
                    System.out.print(nodesArray[i] + " ");
                }
            }

            // Luam primele k noduri din array randomizat ptr a avea o clica random
            int cliqueNodes[] = new int[k];
            for (int i = 0; i < k; i++) {
                cliqueNodes[i] = nodesArray[i];
            }

            System.out.println("\nClique nodes:");
            for (int i = 0; i < k; i++) {
                System.out.print(cliqueNodes[i] + " ");
            }

            // Acum folosim acelasi shuffled Array dar luam urmatoarele k noduri  ptr stable set
            int[] stableSet = new int[k];
            for(int i = 0; i < k; i++){
                stableSet[i] = nodesArray[k+i];
            }

            System.out.println("\nStable set:");
            for (int i = 0; i < k; i++) {
                System.out.print(stableSet[i] + " ");
            } // Ca idee, eu am initializat cu 0 matricea ptr graf, deci nu e nevoie sa construiesc stable set
            // Doar sar urmatoarele k noduri pana la 2*k

            // Acum cream clica in graf (conexiuni intre toate nodurile din clica):
            for(int i = 0; i < k; i++){
                for(int j = i+1; j < k; j++){
                    graph_matrix[cliqueNodes[i]-1][cliqueNodes[j]-1] = 1;
                    graph_matrix[cliqueNodes[j]-1][cliqueNodes[i]-1] = 1;
                }
            }

            if(n > 2*k){
                for(int i = 2*k; i < n; i++){
                    for(int j = 0; j < n; j++){
                        double rand = Math.random();
                        if(rand == 0.5){
                            graph_matrix[nodesArray[i]-1][nodesArray[j]-1] = 1;
                            graph_matrix[nodesArray[j]-1][nodesArray[i]-1] = 1;
                        }
                    }
                }

                if(n > 50){
                    System.out.println("\nn over 50");

                    long endTimer = System.nanoTime();
                    long timeUsage = endTimer - startTimer;
                    System.out.println("Running time(nano): " + timeUsage);
                    System.out.println("Running time(milli): " + timeUsage/1000000);
                }else{
                    System.out.println("\nGraph matrix:");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            System.out.print(graph_matrix[i][j] + " ");
                        }
                        System.out.println();
                    }
                }

                int degreeSum = 0;
                int maxDegree = 0, minDegree = 999999;
                for(int i = 0; i < n; i++){
                    int degree = 0;
                   for(int j = 0; j < n; j++){
                       if(graph_matrix[i][j] == 1){
                           degree++;
                           degreeSum++;
                       }
                   }
                   if(degree > maxDegree){
                       maxDegree = degree;
                   }
                   if(degree < minDegree){
                       minDegree = degree;
                   }
                }

                int edgeCount = degreeSum/2;
                System.out.println("\nEdge count: " + edgeCount);
                System.out.println("\u0394(m) (Max degree): " + maxDegree + "\n\u03B4(m) (Min degree): " + minDegree);
                if(edgeCount*2 == degreeSum){
                    System.out.println("\nTRUE_1: The sum of degrees = 2*m");
                } else {
                    System.out.println("\nFALSE_1: The sum of degrees = 2*m");
                }

                bonus(n,k, graph_matrix);

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        if(graph_matrix[i][j] == 1){
                            graph_matrix[i][j] = 0;}
                        else if(graph_matrix[i][j] == 0){
                            graph_matrix[i][j] = 1;
                        }
                    }
                }

                bonus(n,k, graph_matrix);
                
            } else {
                if(n > 50){
                    System.out.println("\nn over 50");

                    long endTimer = System.nanoTime();
                    long timeUsage = endTimer - startTimer;
                    System.out.println("Running time(nano): " + timeUsage);
                    System.out.println("Running time(milli): " + timeUsage/1000000);
                }else{
                    System.out.println("\nGraph matrix:");
                    for (int i = 0; i < n; i++) {
                        for (int j = 0; j < n; j++) {
                            System.out.print(graph_matrix[i][j] + " ");
                        }
                        System.out.println();
                    }
                }

                int degreeSum = 0;
                int maxDegree = 0, minDegree = 999999;
                for(int i = 0; i < n; i++){
                    int degree = 0;
                    for(int j = 0; j < n; j++){
                        if(graph_matrix[i][j] == 1){
                            degree++;
                            degreeSum++;
                        }
                    }
                    if(degree > maxDegree){
                        maxDegree = degree;
                    }
                    if(degree < minDegree){
                        minDegree = degree;
                    }
                }

                int edgeCount = degreeSum/2;
                System.out.println("\nEdge count: " + edgeCount);
                System.out.println("\n\u0394(m) (Max degree): " + maxDegree + "\n \u03B4(m) (Min degree): " + minDegree);
                if(edgeCount*2 == degreeSum){
                    System.out.println("\nTRUE_2: The sum of degrees = 2*m");
                } else {
                    System.out.println("\nFALSE_2: The sum of degrees = 2*m");
                }

                bonus(n,k, graph_matrix);

                for(int i = 0; i < n; i++){
                    for(int j = 0; j < n; j++){
                        if(graph_matrix[i][j] == 1){
                            graph_matrix[i][j] = 0;}
                        else if(graph_matrix[i][j] == 0){
                            graph_matrix[i][j] = 1;
                        }
                    }
                }

                bonus(n,k,graph_matrix);

            }
        } else {
            System.out.println("Cannot build a random graph with both a clique number = k and stable set size = k");

            long endTimer = System.nanoTime();
            long timeUsage = endTimer - startTimer;
            System.out.println("Running time(nano): " + timeUsage);
            System.out.println("Running time(milli): " + timeUsage/1000000);
        }
    }

    //Array shuffling algorithm:
    private static void shuffleArray(int[] array){
        Random rand = new Random();
        for(int i = array.length - 1; i > 0; i--){
            int j = rand.nextInt(i + 1);
            //Swap array[i] and array[j]
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }

    static int[] candidates, viz, rezultat;
    static int csize = 0, rezultatSize = 0;

    private static void bonus(int n, int k, int[][] graph_matrix){
        candidates = new int[n];
        viz = new int[n];
        rezultat = new int[k];

        for (int i = 0; i < n; i++) {
            int egrad = 0;
            for (int j = 0; j < n; j++)
                if (graph_matrix[i][j] == 1)
                    egrad++;

            if (egrad >= k - 1)
                candidates[csize++] = i;
        }

        for (int i = 0; i < csize; i++)
        {
            for (int j = 0; j < n; j++)
                viz[j] = 0;
            rezultatSize = 0;
            bkt(candidates[i], k, graph_matrix);
        }
    }

    public static void bkt(int idx, int k, int[][]  graph_matrix) {
        viz[idx] = 1;
        rezultat[rezultatSize++] = candidates[idx];

        if (rezultatSize == k) {
            System.out.println("There is a clique");
            for (int i = 0; i < k; i++)
                System.out.print(rezultat[i] + " ");
            System.out.println();
            return;
        }

        for (int i = idx + 1; i < csize; i++) {
            if (viz[i] == 0) {
                boolean ok = true;
                for (int j = 0; j < rezultatSize; j++)
                    if (graph_matrix[rezultat[j]][candidates[i]] == 0 || graph_matrix[candidates[i]][rezultat[j]] == 0) {
                        ok = false;
                        break;
                    }
                if (ok) {
                    bkt(i, k, graph_matrix);
                    viz[i] = 0;
                    rezultatSize--;
                }
            }
        }
    }
}