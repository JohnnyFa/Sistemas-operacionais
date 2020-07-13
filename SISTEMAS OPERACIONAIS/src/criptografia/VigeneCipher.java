package criptografia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class VigeneCipher {

	private static Scanner in;
	private static String message;
	private static String mappedKey;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		System.out.print("1. Criptografar\n2. Descriptografar\nEscolha uma das opções: ");
		int choice = in.nextInt();
		in.nextLine();

		if (choice == 1) {
			System.out.println("---Criptografar---");
			msgAndKey();
			cipherEncryption(message, mappedKey);
		} else if (choice == 2) {
			System.out.println("---Descriptografar---");
			msgAndKey();
			cipherDecryption(message, mappedKey);

		} else {
			System.out.println("Escolha incorreta");
		}
	}

	private static void cipherDecryption(String message, String mappedKey) {
		int[][] table = createVigenereTable();
		String decryptedText = "";

		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == (char) 32 && mappedKey.charAt(i) == (char) 32) {
				decryptedText += " ";
			} else {
				decryptedText += (char) (65 + itrCount((int) mappedKey.charAt(i), (int) message.charAt(i)));
			}
		}

		System.out.println("Descriptofrafar texto: " + decryptedText);
	}

	private static int itrCount(int key, int msg) {
		int counter = 0;
		String result = "";
		for (int i = 0; i < 26; i++) {
			if (key + i > 90) {
				// 90 being ASCII of Z
				result += (char) (key + (i - 26));

			} else {
				result += (char) (key + i);
			}
		}

		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == msg) {
				break; // letter found
			} else {
				counter++;
			}
		}
		return counter;
	}

	private static void cipherEncryption(String message, String mappedKey) {
		int[][] table = createVigenereTable();
		String encryptedText = "";
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == (char) 32 && mappedKey.charAt(i) == (char) 32) {
				encryptedText += " ";
			} else {
				encryptedText += (char) table[(int) message.charAt(i) - 65][(int) mappedKey.charAt(i) - 65];
			}
		}

		System.out.println("Texto criptografado: " + encryptedText);
	}

	private static int[][] createVigenereTable() {
		// creating 26x26 table containing alphabets
		int[][] tableArr = new int[26][26];
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 26; j++) {
				int temp;
				if ((i + 65) + j > 90) {
					temp = ((i + 65) + j) - 26;
					tableArr[i][j] = temp;
				} else {
					temp = (i + 65) + j;
					tableArr[i][j] = temp;
				}
			}
		}

		return tableArr;
	}

	private static void msgAndKey() {

		Scanner ler = new Scanner(System.in);
		String msg = "";
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

		String st;
		try {
			while ((st = br.readLine()) != null)
//				System.out.println(st);
				msg = msg + st;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(msg);
		// message input
//		System.out.print("Mensagem: ");
//		String msg = in.nextLine();
		msg = msg.toUpperCase();

		// key input
		System.out.print("Chave: ");
		String key = in.next();
		in.nextLine();
		key = key.toUpperCase();

		// mapping key to message
		String keyMap = "";
		for (int i = 0, j = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == (char) 32) {
				// ignoring space
				keyMap += (char) 32;

			} else {
				// mapping letters of key with message
				if (j < key.length()) {
					keyMap += key.charAt(j);
					j++;
				} else {
					// restarting the key from beginning once its length is complete
					// and its still not mapped to message
					j = 0;
					keyMap += key.charAt(j);
					j++; // without incrementing here, key's first letter will be mapped twice

				}
			} // if-else

		} // for
		message = msg;
		mappedKey = keyMap;

//	        System.out.println("Message: " + message);
//	        System.out.println("key: " + mappedKey);
	}

}
