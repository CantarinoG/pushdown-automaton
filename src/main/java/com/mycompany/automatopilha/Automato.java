package com.mycompany.automatopilha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Automato {
    
    private String estadoInicial;
    private String estadoAtual = null;
    private char letraFitaAtual;
    private ArrayList<String> estadosFinais = new ArrayList<>();
    private ArrayList<Transicao> listaTransicoes = new ArrayList<>();
    private String pilha = "";
    
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
    
    private void reiniciaPilha() {
        estadoAtual = null;
        letraFitaAtual = ' ';
        pilha = "";
    }
    
    public boolean testaPalavra(String palavra) {
        reiniciaPilha();
        estadoAtual = estadoInicial;
        char[] fita = palavra.toCharArray();
        
        for(char letraFita: fita) {
            letraFitaAtual = letraFita;
            if(!passaFitaComSucesso()) return false;
        }
        
        if(estadosFinais.contains(estadoAtual) && pilha.length() == 0) return true;
        
        return false;
    }
    
    private boolean passaFitaComSucesso() { //Se rejeita a palvra, retorna falso
        for(Transicao transicao: listaTransicoes) {
            
            if(transicao.de.equals(estadoAtual)) { //Pega as transições que começam no estado atual               
                if(transicao.letraFita == letraFitaAtual) { //Pega as transições que leem a letra da fita atual
                    if(pilha.endsWith(transicao.leitura)) { //Se a leitura da transicao for o final da minha pilha
                        estadoAtual = transicao.para; //Atualizo o estado
                        pilha = pilha.substring(0, pilha.length() - transicao.leitura.length()); //Apago da pilha o que foi lido
                        if(!transicao.escrita.equals("e")) pilha += transicao.escrita; //Escrevo na pilha se não for vazio
                        return true;
                    } else if(transicao.leitura.equals("e")) { //Se a leitura da transicao for "e"(vazio)
                        estadoAtual = transicao.para; //Atualizo o estado
                        if(!transicao.escrita.equals("e")) pilha += transicao.escrita; //Escrevo na pilha se não for vazio
                        return true;
                    }
                } 
            }
        }
        
        for(Transicao transicao: listaTransicoes) { //Se ainda não retornou verdadeiro, passa por todas as transições para ver se tem "?"
            if(transicao.de.equals(estadoAtual)) { //Pega as transições que começam no estado atual
                if(transicao.letraFita == '?') { //Se a leitura da transicao for "?"(Checa se a pilha está vazia)
                    if(pilha.length() == 0) { //Se a pilha está de fato vazia
                        estadoAtual = transicao.para; //Atualizo o estado
                        return true;
                    }
                }
            }
        }
        
        return false; //Se não deu nenhum true, não entrou em nenhuma transição válida, a palavra é rejeitada
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
