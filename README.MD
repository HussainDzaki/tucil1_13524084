# **Tugas Kecil 1 IF2211 Strategi Algoritma**

```
Nama: Dzaki Ahmad Al Hussainy
NIM: 13524084
Kelas: K02
```

Aplikasi GUI berbasis JavaFX untuk menyelesaikan permasalahan papan (Board Puzzle) menggunakan algoritma  **Brute Force** . Aplikasi ini memvisualisasikan proses pencarian solusi, memungkinkan pengguna mengunggah konfigurasi papan, dan menyimpan hasil solusi.Requirements

### Fitur

* **GUI** : dengan javafx
* **Visualisasi Solusi** : Menampilkan papan dan penempatan item (Block/Queen) dengan warna yang berbeda (A-Z)
* **Animasi Pencarian:** Melihat proses algoritma Brute Force bekerja secara *real-time* (update per langkah).
* **Input Fleksibel:** Mendukung upload file `.txt` untuk konfigurasi papan.
* **Simpan Solusi:**Simpan koordinat solusi ke format  **.TXT** .

### Struktur File

```
├── Makefile              # Skrip untuk automasi build/run
├── README.MD             # Dokumentasi proyek
├── docs                  # Laporan atau dokumentasi tambahan
├── pom.xml               # Konfigurasi Maven dependencies
├── save                  # Folder output default (opsional)
├── src
│   └── main
│       ├── java
│       │   ├── module-info.java
│       │   └── tucil1_13524084
│       │       ├── App.java              # Entry point aplikasi JavaFX
│       │       ├── Board.java            # Model data papan permainan
│       │       ├── FileIO.java           # Utilitas membaca/menulis file
│       │       ├── GUIController.java    # Logika interaksi UI & Threading
│       │       ├── Position.java         # Data struktur koordinat (row, col)
│       │       ├── SolutionCallback.java # Interface untuk update GUI dari Solver
│       │       └── SolverBruteForce.java # Algoritma pencarian solusi
│       └── resources
│           └── tucil1_13524084
│               ├── Queen.png             # Aset gambar
│               └── primary.fxml          # Layout GUI Utama
│         
├── target                # Hasil kompilasi (Bytecode & Jar)
└── test                  # Kumpulan test case (.txt)
    ├── test1.txt
    ├── ...
    └── test10.txt
```

### Prayarat

### Java

- **Version:** 17 or higher
- **Download links:**
  - [Oracle JDK 17](https://www.oracle.com/java/technologies/downloads)

### Maven

- **Version:** 3.2.5 or higher (recommended 3.6.3+)
- **Download links:**
  - [Direct Apache Maven Official Downloads](https://dlcdn.apache.org/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.zip)


### Cara Kompilasi

1. Pergi ke main direktori tucil1_13524084/
2. kemudian run

   `make run`
