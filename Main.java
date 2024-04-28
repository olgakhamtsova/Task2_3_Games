import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        File dir3 = new File("//Users//olgakhamtsova//Desktop//Games//src");
        File dir4 = new File("//Users//olgakhamtsova//Desktop//Games//res");
        File dir5 = new File("//Users//olgakhamtsova//Desktop//Games//savegames");
        File dir6 = new File("//Users//olgakhamtsova//Desktop//Games//temp");
        File dir7 = new File("//Users//olgakhamtsova//Desktop//Games//src//main");
        File dir8 = new File("//Users//olgakhamtsova//Desktop//Games//src//test");
        File dir9 = new File("//Users//olgakhamtsova//Desktop//Games//res//drawables");
        File dir10 = new File("//Users//olgakhamtsova//Desktop//Games//res//vectors");
        File dir11 = new File("//Users//olgakhamtsova//Desktop//Games//res//icons");
        File File1 = new File("//Users//olgakhamtsova//Desktop//Games//src//main//Main.java");
        File File2 = new File("//Users//olgakhamtsova//Desktop//Games//src//main//Utils.java");
        File File3 = new File("//Users//olgakhamtsova//Desktop//Games//temp//temp.txt");

        FileWriter writer = null;

        dir3.mkdir();
        dir4.mkdir();
        dir5.mkdir();
        dir6.mkdir();
        dir7.mkdir();
        dir8.mkdir();
        dir9.mkdir();
        dir10.mkdir();
        dir11.mkdir();

        try {
            File1.createNewFile();
            File2.createNewFile();
            File3.createNewFile();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            writer = new FileWriter(File3, false);

            if (dir3.exists() && dir4.exists() && dir5.exists() && dir6.exists() && dir7.exists() && dir8.exists() && dir9.exists() && dir10.exists() && dir11.exists())
                writer.write("Директории созданы успешно");
            if (File1.exists() && File2.exists() && File3.exists()) writer.append('\n');
            writer.write("Файлы созданы успешно");

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader("//Users//olgakhamtsova//Desktop//Games//temp//temp.txt"));

            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            br.close(); // Don't forget to close the BufferedReader
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        GameProgress gameProgress1 = new GameProgress(100, 5, 1, 250.0);
        GameProgress gameProgress2 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress3 = new GameProgress(99, 7, 3, 258.0);

        saveGame("//Users//olgakhamtsova//Desktop//Games//savegames//gameProgress1.txt", gameProgress1);
        saveGame("//Users//olgakhamtsova//Desktop//Games//savegames//gameProgress2.txt", gameProgress2);
        saveGame("//Users//olgakhamtsova//Desktop//Games//savegames//gameProgress3.txt", gameProgress3);

        ArrayList<String> files_to_zip = new ArrayList<String>();

        files_to_zip.add("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress1.txt");
        files_to_zip.add("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress2.txt");
        files_to_zip.add("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress3.txt");

        zipFiles("//Users//olgakhamtsova//Desktop//Games//savegames//zip.zip", files_to_zip);

        for (String file : files_to_zip) {

            for (File file1 : dir5.listFiles()) {

                if (file1.getPath().equals(file)) {
                    file1.delete();
                }
            }
        }

        openZip("//Users//olgakhamtsova//Desktop//Games//savegames//zip.zip", "//Users//olgakhamtsova//Desktop//Games//savegames");


        System.out.println(openProgress("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress1.txt"));
        System.out.println(openProgress("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress2.txt"));
        System.out.println(openProgress("//Users//olgakhamtsova//Desktop/Games/savegames/gameProgress3.txt"));


    }


    public static void saveGame(String file, GameProgress gameProgress) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gameProgress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void zipFiles(String dir, ArrayList<String> Files) {
        ZipOutputStream zout = null;
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(dir));
            for (String file : Files) {
                zout.putNextEntry(new ZipEntry(file));
                fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zout.write(buffer, 0, len);
                }
                fis.close();
            }
            zout.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void openZip(String dir, String file) {
        ZipInputStream zin = null;
        FileOutputStream fout = null;
        String name;
        try {
            zin = new ZipInputStream(new FileInputStream(dir));
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                name = ze.getName();
                fout = new FileOutputStream(name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();

            }
            zin.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static GameProgress openProgress(String file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        GameProgress gameProgress = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            // десериализуем объект и скастим его в класс
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }

}