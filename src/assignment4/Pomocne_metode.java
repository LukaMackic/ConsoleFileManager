package assignment4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pomocne_metode {

    public static void kopiraj(File f1, File f2) {

        if (f1.isDirectory()) {
            if (!f2.exists()) {
                f2.mkdir();
            }

            File f15 = new File(f2.getAbsolutePath() + "\\" + f1.getName());
            f15.mkdir();
            String podfolderi[] = f1.list();
            for (String podfolderi1 : podfolderi) {
                kopiraj(new File(f1, podfolderi1), f15);
            }

        } else {
            File f3 = new File(f2.getAbsolutePath() + "\\" + f1.getName());

            try {
                f3.createNewFile();
            } catch (IOException ex) {
                System.out.println("wtf");
            }

            try (InputStream in = new FileInputStream(f1);
                    OutputStream out = new FileOutputStream(f3);) {
                byte[] buffer = new byte[1024];
                int length;

                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

            } catch (Exception ex) {
                System.out.println("doslo je do greske!");
            }

        }
    }

    public static void brisi(File f1) {

        if (f1.isDirectory()) {
            String podfolderi[] = f1.list();
            for (String podfolderil : podfolderi) {
                brisi(new File(f1, podfolderil));
               
              } f1.delete();
        } else {
            f1.delete();

        }
    }
}
