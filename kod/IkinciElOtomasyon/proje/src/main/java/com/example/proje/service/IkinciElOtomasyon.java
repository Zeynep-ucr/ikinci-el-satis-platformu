package com.example.proje.service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IkinciElOtomasyon {
    public static final UrunYonetimi urunYonetimi = new UrunYonetimi();

    // Konsol tarafı opsiyonel
    public static void runAutomation() {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("1-Ekle,2-Sil,3-Liste,4-Kat Filtre,5-Fiyat Filtre,6-Çık");
            int c = -1;
            try {
                c = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Geçersiz...");
                sc.nextLine();
            }
            if(c == 6) break;
        }
        sc.close();
    }

    public UrunEkleCikar getUrunYonetimi() {
        return urunYonetimi;
    }
}
