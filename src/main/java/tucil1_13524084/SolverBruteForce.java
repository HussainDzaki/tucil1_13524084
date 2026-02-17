package tucil1_13524084;

import java.util.ArrayList;
import java.util.List;

public class SolverBruteForce {
    private Board board;
    private int boardSizeCol;
    private int boardSizeRow;
    private long iteration;

    private boolean[] rowsTerpilih;
    private boolean[] colsTerpilih;
    private boolean[][] grid;


    private List<Position> solusi;

    private SolutionCallback callback;
    private int delay = 10;

    public SolverBruteForce(Board board) {
        this.board = board;
        
        this.solusi = new ArrayList<>();
        this.boardSizeCol = board.cols;
        this.boardSizeRow = board.rows;

        this.iteration = 0;
        this.rowsTerpilih = new boolean[boardSizeRow];
        this.colsTerpilih = new boolean[boardSizeCol];

        this.grid = new boolean[boardSizeRow][boardSizeCol];
    }

    public long getIteration(){
        return this.iteration;
    }

    public boolean isSafe(Position p) {
        int r = p.row;
        int c = p.col;

        if (rowsTerpilih[r] || colsTerpilih[c]) {
            return false;
        }

        // Melakukan cek tetangga
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1 ; j <= c + 1; j++) {
                if (i >= 0 && i < boardSizeRow && j>= 0 && j < boardSizeCol) {
                    if (grid[i][j]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void placeQueen(Position p) {
        int r = p.row;
        int c = p.col;

        rowsTerpilih[r] = true;
        colsTerpilih[c] = true;
        grid[p.row][p.col] = true;

        solusi.add(p);
    }

    public void removeQueen(Position p) {
        int r = p.row;
        int c = p.col;

        rowsTerpilih[r] = false;
        colsTerpilih[c] = false;
        grid[p.row][p.col] = false;

        // Delete elemen terakhir
        solusi.remove(solusi.size() - 1);
    }
    

    public boolean solve(boolean heuristic){
        this.iteration = 0;
        if (heuristic) {
            return solveRecursive(0, board.getWarnaTerutut());
        }
        else{
            return solveRecursive(0, board.getWarna());
        }
        
    }

    /**
     * Berikut adalah algoritma searching menggunakan pencarian pada State Space Tree dengan backtracking
     * 1. Cara kerjanya melakuan iterasi semua warna dengan index warna pada map kemudian meload posisi apa saja yang tersedia 
     * 2. untuk setiap posisi yang tersedia akan melakukan checking apakah bisa ditaruh queen atau tidak
     * 3. Jika bisa bisa ditaruh queen akan memanggil fungsinya lagi untuk index warna yang berbeda kemudian mengulangi step 1-2
     * 4. Jika 
     * 
     * 
     * 
     *  */ 
    private boolean solveRecursive(int idxWarna, List<Character> warna){
        this.iteration++;
        if (idxWarna == warna.size()){
            return true;
        }

        char currWarna = warna.get(idxWarna);

        List<Position> posisiAvailableWarna = board.posisiPerWarna.get(currWarna);
        for (Position p : posisiAvailableWarna) {
            if (isSafe(p)) {
                placeQueen(p);
                updateVisual();
                if (solveRecursive(idxWarna + 1, warna)) {
                    return true;
                }
                removeQueen(p);
                updateVisual();
            }
        }
        return false;
    }


    public void printSolution() {
        char[][] grid = new char[boardSizeRow][boardSizeCol];

        for (Position p : board.getSemuaPositions()) {
            grid[p.row][p.col] = p.color;
        }

        for (Position q : solusi) {
            grid[q.row][q.col] = '#';
        }

        for (int i = 0; i < boardSizeRow; i++) {
            for (int j = 0; j < boardSizeCol; j++) {
                char cell = grid[i][j];
                if (cell == '#') {
                    System.out.print("[#]");
                } else {
                    System.out.print(String.format("[%c]", cell));
                }
            }
            System.out.println();
        }
    }

    public List<Position> getSolusi(){
        return this.solusi;
    }

    public void setCallback(SolutionCallback cb, int delayMilSec){
        this.callback = cb;
        this.delay = delayMilSec;
    }

    private void updateVisual(){
        if (callback != null) {
            callback.onStep(new ArrayList<>(solusi));
            try {
                Thread.sleep(delay);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }

    }

}
