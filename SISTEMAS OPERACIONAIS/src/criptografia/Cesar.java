package criptografia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cesar {
	public void cesarCodi() throws IOException {
		Scanner ler = new Scanner(System.in);
		String code = "";
		String mensagem = "";
		System.out.printf("Informe o nome de arquivo texto:\n");
		String nome = ler.nextLine();
		File file = new File(nome);
		String cifra2 = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String texto;
		try {
			while ((texto = br.readLine()) != null)
//				System.out.println(texto);
				mensagem = mensagem + texto;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mensagem);
		char[] caracteres = mensagem.toCharArray();

		System.out.print("Digite o valor da chave > ");
		int chave = ler.nextInt();

		for (int i = 0; i < caracteres.length; i++) {
			int ascii = (int) caracteres[i] + chave;
			code = code + ((char) ascii);
			System.out.print((char) ascii);
		}

		FileWriter arq = new FileWriter("C:\\Users\\johnn\\Desktop\\ALEATORIO\\arquivoCripto.txt");
		PrintWriter gravarArq = new PrintWriter(arq);

//		cifra2 = cifra2 + chave;
		gravarArq.print(cifra2 + code);
		arq.close();

		System.out.println(
				"\nArquivo criptografado criado em \"C:\\Users\\johnn\\Desktop\\ALEATORIO\\arquivoCripto.txt\".\n");

		System.out.println();
	}

	public void cesardecodi() {
		Scanner ler = new Scanner(System.in);
		String mensagem = "";
		int chave = 0;
		System.out.printf("Informe o nome de arquivo texto:\n");
		String nome = ler.nextLine();
		File file = new File(nome);

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String texto;
		try {
			while ((texto = br.readLine()) != null)
//				System.out.println(texto);
				mensagem = mensagem + texto;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(mensagem);
		
		char[] caracteres = mensagem.toCharArray();
		chave = mensagem.charAt(0) - 48;
		
		
//		System.out.print("Digite o valor da chave > ");
		

		for (int i = 1; i < caracteres.length; i++) {
			int ascii = (int) caracteres[i] - chave;
			System.out.print((char) ascii);
		}

		System.out.println();
	}
}
