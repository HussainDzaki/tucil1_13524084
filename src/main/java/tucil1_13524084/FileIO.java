package tucil1_13524084;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;

public class FileIO {
    static String path = "src/main/save/";
    static String header = "Penyelesaian Permasalahan Queens LinkedIn\nFile ini dibuat saat " + LocalDateTime.now();

    public static void saveFile(Board board, List<Position> solusi, String waktuIterasi, String banyakIterasi) {
        String nameFile = "Penyelesaian_" + LocalTime.now();
        TextInputDialog dialog = new TextInputDialog("FIleKu");
        dialog.setTitle("Save Output hasil");
        dialog.setHeaderText("Anda ingin menyimpan file");
        dialog.setContentText("Tolong masukan nama file yang kamu mau: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            nameFile = result.get();
        } else {
            nameFile = LocalTime.now().toString();
        }
        try (PrintWriter pw = new PrintWriter(path + nameFile + ".txt")) {
            pw.write(header);
            pw.write("\n");
            pw.write("Solusinya:\n");
            char[][] grid = new char[board.rows][board.cols];

            for (Position p : board.getSemuaPositions()) {
                grid[p.row][p.col] = p.color;
            }

            for (Position q : solusi) {
                grid[q.row][q.col] = '#';
            }

            for (int i = 0; i < board.rows; i++) {
                for (int j = 0; j < board.cols; j++) {
                    char cell = grid[i][j];
                    if (cell == '#') {
                        pw.append("[#]");
                    } else {
                        pw.append(String.format("[%c]", cell));
                    }
                }
                pw.write('\n');

            }
            pw.write("Diperlukan waktu: " + waktuIterasi+"\n");
            pw.write("Diperlukan banyak iterasi: " + banyakIterasi);

        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("WAH TERJADI ERROR!");
            alert.setContentText("Error ini dikarenakan: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
