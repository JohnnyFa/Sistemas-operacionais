package simuladorDeEscalonamento;

/**Johnny Fagundes
 * Gianlucca
 */

import java.util.Scanner;

import javax.swing.JOptionPane;

public class Main1 {
	public static void main(String[] args) {
		try {
		System.out.println("");
		Scanner scan = new Scanner(System.in);
		boolean ciclo = true;
		while (ciclo) {
			Escalonador process = new Escalonador();
			System.out.println("- Simulador de Escalonamento de Processos -\n"
					+ " Escolha o tipo de escalonamento \n 1 - FSFC\n 2 - SJF");
			int op = scan.nextInt();
			switch (op) {
			case 1:
				process.FCFS();
				break;
			case 2:
				process.SJF();
				break;

			default:
				System.out.println("Opcao invalida...");
				System.out.println("");
				break;
			}

		}
	} catch(Exception e) {
		System.out.println("Numero de processos nao aceita letras e tambem nao aceita um numero maior que 20!!!");
		System.out.println("");
		main(args);
	}
}}
