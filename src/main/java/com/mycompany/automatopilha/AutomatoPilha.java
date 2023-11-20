package com.mycompany.automatopilha;

import java.util.Scanner;

public class AutomatoPilha {

    public static void main(String[] args) {
        
        String caminho = "./arquivosEntrada/linguagem0.txt";
        Automato auto = new Automato(caminho);
        auto.debug();
        
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while(!input.equals("SAIR")) {
            System.out.println("Insira uma palavra a ser testada, ou \"SAIR\" para sair:");
            input = scanner.nextLine();
            String resultado = auto.testaPalavra(input)?"Aceita":"Rejeitada";
            System.out.println(resultado);
        }
        scanner.close();
        
    }
}
