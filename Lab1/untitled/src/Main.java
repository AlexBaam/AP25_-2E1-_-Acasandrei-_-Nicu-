import java.util.*;

class Convertor {
    public int binaryToDecimal(String n){
        String num = n; // Nr primit
        int decimal = 0; // Nr de return
        int power = 1; // Puterea la inceput
        int len = num.length(); // Ptr for
        for(int i = len - 1; i >= 0; i--){ // O luam de la coada la cap fiindca nu stim cat de mare e nr
            if(num.charAt(i) == '1'){ // Cand dam de un 1 adaugam 2 la puterea la care ne aflam;
                decimal += power;
            }
            power *= 2; // La fiecare pas inmultim cu 2 ptr a aduna corect
        }
        return decimal; // Return nr ca decimal
    }

    public int hexToDecimal(String n){
        String num = n; // De convertit
        int decimal = 0; // Ptr return
        int len = num.length(); // Lungime cuvant
        int power = 1; // 16 la puterea 0
        for(int i = len - 1; i >= 0; i--){ // For ptr puteri si constructie nr
            if(num.charAt(i) >= '0' && num.charAt(i) <= '9'){
                decimal += (num.charAt(i) - '0') * power;
                power *= 16;
            } else if(num.charAt(i) >= 'A' && num.charAt(i) <= 'F'){
                decimal += (num.charAt(i) - 'A' + 10) * power;
                power *= 16;
            }
        }
        return decimal;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String languages[] = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        int n = (int) (Math.random() * 1_000_000);
        System.out.println("Random number: " + n);

        int compute = n * 3;
        System.out.println("Random number * 3: " + compute);

        Convertor c = new Convertor();
        int BintoDec = c.binaryToDecimal("10101");
        System.out.println("10101 in decimal: " + BintoDec);

        compute = compute + BintoDec;
        System.out.println("(Random number * 3) + 10101: " + compute);

        int HextoDec = c.hexToDecimal("FF");
        System.out.println("FF in decimal: " + HextoDec);

        compute = compute + HextoDec;
        System.out.println("((Random number * 3) + 10101) + FF: " + compute);

        compute = compute * 6;
        System.out.println("(((Random number * 3) + 10101) + FF) * 6: " + compute);

        int sum = 0;
        while(compute > 9){
            sum = 0;
            while(compute > 0){
                sum += compute % 10;
                compute /= 10;
            }
            compute = sum;
        }

        System.out.println("The result of the computation is: " + sum);
        System.out.println("Willy-nilly, this semester I will learn " + languages[sum] + "!");
    }
}
