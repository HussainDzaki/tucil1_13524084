package tucil1_13524084;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Platform;

public class GUIController {

    @FXML
    private TextArea StatePapan;

    @FXML
    private GridPane stateConfigurasi;

    @FXML
    private RadioButton buttonToggleHeuristic;

    @FXML
    private Label labelOutputHasil;

    @FXML
    private Label labelOutputWaktuIterasi;

    @FXML
    private Label labelOutputBanyakIterasi;

    private Board board;

    private Boolean heuristicState = false;

    private boolean problemSolved = false;
    private List<Position> currSolusi;

    private final Image queenImage = new Image(getClass().getResourceAsStream("/tucil1_13524084/Queen.png"));

    FileChooser fileChooser = new FileChooser();

    private static final Color[] WARNA_AZ = {
            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE,
            Color.PURPLE, Color.CYAN, Color.MAGENTA, Color.LIME, Color.PINK,
            Color.TEAL, Color.LAVENDER, Color.BROWN, Color.MAROON, Color.OLIVE,
            Color.NAVY, Color.CORAL, Color.TURQUOISE, Color.GOLD, Color.SILVER,
            Color.INDIGO, Color.VIOLET, Color.KHAKI, Color.SALMON, Color.BEIGE, Color.CRIMSON
    };

    @FXML
    public void initialize() {
        File homeDir = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(homeDir);
    }

    @FXML
    void cariSolusi(ActionEvent event) {
        problemSolved = false;
        SolverBruteForce solver = new SolverBruteForce(board);
        drawBoard(null);

        solver.setCallback((currpos) -> {
            Platform.runLater(() -> {
                drawBoard(currpos);
            });
        }, 100);

        Thread solverThred = new Thread(() -> {
            Long startTime = System.nanoTime();
            problemSolved = solver.solve(heuristicState);
            Long endTime = System.nanoTime();
            Platform.runLater(() -> {
                if (problemSolved) {
                    drawBoard(solver.getSolusi());
                    currSolusi = solver.getSolusi();
                    labelOutputHasil.setText("DITEMUKAN SOLUSI");

                } else {
                    drawBoard(null);
                    labelOutputHasil.setText("TIDAK DITEMUKAN SOLUSI");
                }
                labelOutputWaktuIterasi.setText(String.format("%.2f ms", (endTime - startTime) / 1000000.0));
                labelOutputBanyakIterasi.setText(String.format("%d iterasi", solver.getIteration()));
            });
        });

        solverThred.setDaemon(true);
        solverThred.start();

    }

    @FXML
    void toggleHeuristicApproach(ActionEvent event) {
        if (buttonToggleHeuristic.isSelected()) {
            heuristicState = true;
        } else {
            heuristicState = false;
        }
    }

    @FXML
    void saveToTxt(ActionEvent event) {
        if (problemSolved) {
            FileIO.saveFile(board, currSolusi, labelOutputWaktuIterasi.getText(), labelOutputBanyakIterasi.getText());
        }else{
            labelOutputHasil.setText("Terjadi Kesalahan");
        }
        
    }

    @FXML
    void uploadStatePapan(ActionEvent event) {
        String input = StatePapan.getText().toUpperCase();
        if (input.isEmpty()) {
            board = new Board();
            return;
        }

        Scanner scanner = new Scanner(input);
        board = new Board();
        try {
            board.inputBoard(input);
            drawBoard(null);
        } catch (Exception e) {
            labelOutputHasil.setText(e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }

        if (board.cols != board.rows) {
            labelOutputHasil.setText(String.format("Ukuran Board tidak sama (%d x %d)", board.cols, board.rows));
        }

    }

    @FXML
    void uploadTxt(ActionEvent event) {
        File file = fileChooser.showOpenDialog(new Stage());
        StatePapan.clear();
        if (file != null) {
            try {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    StatePapan.appendText(sc.nextLine() + "\n");
                }
                sc.close();
            } catch (Exception e) {
                StatePapan.setText(e.getMessage());
            }

        } else {
            StatePapan.appendText("");
        }

    }

    public void drawBoard(List<Position> solusi) {
        stateConfigurasi.getChildren().clear();
        stateConfigurasi.setHgap(0);
        stateConfigurasi.setVgap(0);
        stateConfigurasi.getColumnConstraints().clear();
        stateConfigurasi.getRowConstraints().clear();
        stateConfigurasi.setSnapToPixel(true);

        boolean[][] mapQueen = new boolean[board.cols][board.rows];

        if (solusi != null) {
            for (Position p : solusi) {
                if (p.row >= 0 && p.row < board.rows && p.col >= 0 && p.col < board.rows) {
                    mapQueen[p.col][p.row] = true;
                }
            }
        }

        for (Position p : board.getSemuaPositions()) {

            int colorIndex = p.color - 'A';
            Color cellColor = Color.GRAY;

            StackPane cellStack = new StackPane();
            if (colorIndex >= 0 && colorIndex < WARNA_AZ.length) {
                cellColor = WARNA_AZ[colorIndex];
            }

            Rectangle rect = new Rectangle(20, 20);
            rect.setFill(cellColor);
            rect.setStroke(Color.BLACK);

            cellStack.getChildren().add(rect);

            if (mapQueen[p.col][p.row]) {
                ImageView imgv = new ImageView(queenImage);
                imgv.setFitWidth(15);
                imgv.setFitHeight(15);
                imgv.setPreserveRatio(true);
                cellStack.getChildren().add(imgv);
            }
            stateConfigurasi.add(cellStack, p.col, p.row);
        }
    }

}
