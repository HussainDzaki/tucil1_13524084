package tucil1_13524084;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;

public class Board {
    private List<Position> semuaPosisi;
    private Set<Character> warnaUnik;
    public int rows;
    public int cols;

    public Map<Character, List<Position>> posisiPerWarna;

    public Board() {
        this.semuaPosisi = new ArrayList<>();
        this.warnaUnik = new HashSet<>();
        this.posisiPerWarna = new HashMap<>();
        this.rows = 0;
        this.cols = 0;
    }

    public List<Character> getWarna() {
        return new ArrayList<>(posisiPerWarna.keySet());
    }

    public void inputBoard(String inputText) {
        Scanner input = new Scanner(inputText);
        List<String> barisInput = new ArrayList<>();

        while (input.hasNextLine()) {
            String line = input.nextLine();

            if (line.trim().isEmpty()) {
                break;
            }
            barisInput.add(line.trim());
        }

        if (barisInput.isEmpty()) {
            System.out.println("Input kosong!");
            input.close();
            return;
        }


        this.rows = barisInput.size(); 
        this.cols = barisInput.get(0).length(); 

        for (int i = 0; i < rows; i++) {
            String line = barisInput.get(i);


            if (line.length() != cols) {
                System.out.print("Error Baris ke-" + (i + 1) + ": Panjang tidak konsisten!");
                break;
            }

            for (int j = 0; j < cols; j++) {
                char warna = line.charAt(j);
                Position pos = new Position(i, j, warna);

                semuaPosisi.add(pos);
                warnaUnik.add(warna);

                posisiPerWarna.putIfAbsent(warna, new ArrayList<>());
                posisiPerWarna.get(warna).add(pos);
            }
        }
        input.close();
    }

    public List<Position> getSemuaPositions() {
        return this.semuaPosisi;
    }

    public int getJumlahWarnaUnik() {
        return this.warnaUnik.size();
    }

    public void printBoard() {
        for (Character warna : posisiPerWarna.keySet()) {
            System.out.print(String.format("Warna %c : {", warna));
            List<Position> posisiWarna = posisiPerWarna.get(warna);
            for (int i = 0; i < posisiWarna.size(); i++) {
                Position p = posisiWarna.get(i);
                System.out.print(String.format("(%d, %d)", p.row, p.col));
                if (i < posisiWarna.size() - 1) {
                    System.out.print(", ");
                }

            }
            System.out.print("}\n");
        }
    }

    public List<Character> getWarnaTerutut() {
        List<Character> sortedColors = new ArrayList<>(posisiPerWarna.keySet());
        sortedColors.sort((c1, c2) -> {
            int size1 = posisiPerWarna.get(c1).size();
            int size2 = posisiPerWarna.get(c2).size();
            return Integer.compare(size1, size2);
        });
        return sortedColors;
    }

    public void clearBoard(){
        this.posisiPerWarna.clear();
        this.semuaPosisi.clear();
        this.posisiPerWarna.clear();
        this.cols = 0;
        this.rows = 0;
    }

}
