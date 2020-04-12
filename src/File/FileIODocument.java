package File;

import java.io.*;

public class FileIODocument {
    public static void zapiszPlik(String nazwaPliku,String nickname,int score) {
        try {
            Writer output = new BufferedWriter(new FileWriter("aaa.txt", true));
            output.append(nickname+"#"+score+"\n");
            output.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
