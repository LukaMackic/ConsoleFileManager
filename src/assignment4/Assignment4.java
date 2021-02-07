/*
Ovo je konzolni file manager koji ima opcije:LIST INFO CREATE_DIR RENAME COPY MOVE DELETE

Ovo je ujedino i jedan od mojih prvih projekata.
2019.

*/




package assignment4;

import assignment4.EnumeracijaFunkcija.FunkcijeEnum;
import static assignment4.Pomocne_metode.brisi;
import static assignment4.Pomocne_metode.kopiraj;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Assignment4 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("odaberite funkciju! ponudjene su:");
        for (FunkcijeEnum d : FunkcijeEnum.values()) {

            System.out.print(d + " ");
        }
        System.out.println("");
        String biranje = sc.nextLine();
        biranje = biranje.toUpperCase();

        try {
            FunkcijeEnum funkcijeEnum = FunkcijeEnum.valueOf(biranje);

            switch (funkcijeEnum) {

                case LIST:
                    System.out.println("izabrali ste LIST");
                    System.out.println("unesite putanju:");
                    File file = new File(sc.nextLine());
                    if (file.exists() && file.isDirectory()) {
                        String[] strings = file.list();
                        for (String s : strings) {
                            System.out.println(s);
                        }
                    } else {
                        System.out.println("putanja ne ukazuje na direktorij ili ne postoji");
                    }
                    break;

                case INFO:
                    System.out.println("izabrali ste INFO");
                    System.out.println("unesite putanju:");
                    File file1 = new File(sc.nextLine());
                    System.out.println("ime: " + file1.getName());
                    System.out.println("Absolutna putanja: " + file1.getAbsolutePath());
                    System.out.println("velicina: " + file1.length() + " B");
                    Instant instant = Instant.ofEpochMilli(file1.lastModified());
                    LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd. MMMM yyyy. HH:mm:ss");
                    System.out.println("poslednji put modifikovano: " + dateTime.format(dateTimeFormatter));
                    FileTime vremekreiranja = Files.readAttributes(file1.toPath(), BasicFileAttributes.class).creationTime();
                    LocalDateTime dateTime1 = LocalDateTime.ofInstant(vremekreiranja.toInstant(), ZoneId.systemDefault());
                    System.out.println("fajl/folder je kreiran:" + dateTime1.format(dateTimeFormatter));
                    break;

                case CREATE_DIR:
                    System.out.println("izabrali ste CREATE_DIR");
                    System.out.println("unesite putanju:");
                    File kreirajdir = new File(sc.nextLine());
                    try {
                        if (!kreirajdir.exists()) {
                            kreirajdir.mkdir();
                            System.out.println("direktori kreiran i zove se: " + kreirajdir.getName());
                        } else {
                            System.out.println("direktori " + kreirajdir.getName() + " vec postoji");
                        }

                    } catch (Exception e) {
                        System.out.println("nije moguce kreirati direktorija");

                    }
                    break;

                case RENAME:
                    System.out.println("izabrali ste RENAME");
                    System.out.println("unesite putanju do foldera/fajla kojem mjenjate ime:");
                    String putanja = sc.nextLine();
                    System.out.println("unesite ime foldera/fajla kojem mjenjate ime:");
                    File oldfile = new File(putanja + "\\" + sc.nextLine());
                    System.out.println("Unesite novo zeljeno ime:");
                    File newfile = new File(putanja + "\\" + sc.nextLine());
                    if (!oldfile.exists()) {
                        System.out.println("fajl kojem zelite da promjenite ime ne postoji!");
                        return;
                    }
                    if (newfile.exists()) {
                        System.out.println("zeljeno ime se vec koristi na zeljenoj lokaciji!");
                        return;
                    }
                    if (oldfile.renameTo(newfile)) {
                        System.out.println("ime uspjesno promjenjeno");
                    } else {
                        System.out.println("nije moguce zamjeniti ime!");
                    }
                    break;
                case COPY:
                    System.out.println("izabrali ste COPY");
                    System.out.println("odaberite putanju za fajl koji kopirate:");
                    File file2 = new File(sc.nextLine());
                    System.out.println("odaberite putanju foldera na koji kopirate podatke:");
                    File file3 = new File(sc.nextLine());

                    if (!file2.exists()) {
                        System.out.println("fajl koji zelite da kopirate ne postoji!");

                    } else {
                        kopiraj(file2, file3);
                    }
                    System.out.println("kopiranje uspjesno zavrseno");

                    break;

                case MOVE:
                    System.out.println("izabrali ste MOVE");
                    System.out.println("odaberite putanju za fajl koji pomjerate:");
                    File file4 = new File(sc.nextLine());
                    System.out.println("odaberite putanju foldera na koji pomjerate podatke:");
                    File file5 = new File(sc.nextLine());

                    if (!file4.exists()) {
                        System.out.println("fajl koji zelite da pomjerite ne postoji!");

                    } else {
                        kopiraj(file4, file5);

                    }
                    if (file4.exists()) {
                        brisi(file4);
                        System.out.println("uspjesno obavljeno pomjeranje");
                    }

                    break;
                case DELETE:
                    System.out.println("izabrali ste DELETE");
                    System.out.println("unesite putanju fajla/foldera koji brisete:");
                    File filea = new File(sc.nextLine());
                    if (filea.exists()) {
                        brisi(filea);
                        System.out.println("uspjesno ste izbrisali fajl/foder");
                    } else {
                        System.out.println("birali ste nepostojeci fajl/folder");
                    }
                    break;

            }

        } catch (IllegalArgumentException ex) {
            System.out.println("birali ste nepostojecu funkcionalnost");
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
