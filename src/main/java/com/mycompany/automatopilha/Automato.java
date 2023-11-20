package com.mycompany.automatopilha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Automato {
    
    private String estadoInicial;
    private ArrayList<String> estadosFinais = new ArrayList<>();
    private ArrayList<Transicao> listaTransicoes = new ArrayList<>();
    
    public Automato(String caminho) {
        carregaArquivo(caminho);
    }
    
    private boolean carregaArquivo(String caminho) {
        
         try {
             
            FileReader fileReader = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha;
            String[] valores;
            
            //Adiciona estado inicial
            estadoInicial = bufferedReader.readLine();        
            
            //Adiciona estados finais
            linha = bufferedReader.readLine();
            valores = linha.split(";");
            for (int i = 0; i < valores.length; i++) {
                    estadosFinais.add(valores[i]);
            }
            
            //Adiciona Transicoes
            while ((linha = bufferedReader.readLine()) != null) {
                valores = linha.split(";");
                listaTransicoes.add(new Transicao(valores[0], valores[1], valores[2].charAt(0), valores[3], valores[4]));
            }
            
            bufferedReader.close();
            fileReader.close();
            return true;
            
        } catch (Exception e) {
            
            System.out.println("Erro: " + e.getMessage());
            return false;
            
        } 
    }
    
    public void debug() {
        System.out.println("Estado Inicial: " + estadoInicial);
        System.out.print("Estados Finais: ");
        for (String estadoFinal : estadosFinais) {
            System.out.print(estadoFinal + " ");
        }
        System.out.println();

        System.out.println("Lista de Transições:");
        for (Transicao transicao : listaTransicoes) {
            System.out.println("De: " + transicao.de + ", Para: " + transicao.para + ", Lê na fita: " + transicao.letraFita + ", Leitura: " + transicao.leitura + ", Escrita: " + transicao.escrita);
        }
    }

    
}
