package com.mycompany.automatopilha;

public class AutomatoPilha {

    public static void main(String[] args) {
        
        String caminho = "./arquivosEntrada/linguagem0.txt";
        
        Automato auto = new Automato(caminho);
        auto.debug();
        
    }
}
