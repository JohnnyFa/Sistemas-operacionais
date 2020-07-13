package simuladorDeEscalonamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Escalonador {
	public Scanner scan = new Scanner(System.in);
	String entrada;
	public ArrayList<Process> lista = new ArrayList<>();
	public ArrayList<Process> listaOrd = new ArrayList<>();
	public int n = 0, j, i, tEsp[] = new int[20], quantum; /* Tempo de espera */
	public float mEsp = 0, /* Media de espera */
			mResp = 0;/* Media de resposta */

	public void entrada(int i, int k) {
		System.out.println("Numero de processos (MAX 20): ");
		n = scan.nextInt();
		if (k == 1) {
			System.out.println("Quantum: ");
			quantum = scan.nextInt();
		}

		int burst, tcheg;
		System.out.println("Definir tamanho do burst de cada processo?(S/N)");
		String op = scan.next();
		if (op.toUpperCase().equals("S")) {

			for (int l = 0; l < n; l++) {
				System.out.println("P" + (l + 1) + " :");
				burst = scan.nextInt();
				if (i == 1) {
					lista.add(new Process(l + 1, burst));
				} else if (i == 2) {
					System.out.println("Tempo de Chegada P" + (l + 1) + " :");
					tcheg = scan.nextInt();
					lista.add(new Process(l + 1, burst, tcheg));
				}
			}
		}
		if (op.toUpperCase().equals("N")) {

			Random rd = new Random();
			for (int l = 0; l < n; l++) {
				burst = 1 + rd.nextInt(25);
				if (i == 1)
					lista.add(new Process(l + 1, burst));
				else if (i == 2) {
					tcheg = rd.nextInt(25);
					lista.add(new Process(l + 1, burst, tcheg));
				}
			}
		} else {
			System.out.println("opcao invalida");
			System.out.println("");

		}
	}

	public void imprimir(ArrayList<Process> p) {
		tEsp[0] = 0;
		int cont = 0;
		for (i = 1; i < n; i++) {
			tEsp[i] = 0;
			for (j = 0; j < i; j++)
				tEsp[i] += p.get(j).getBurst();
			cont += p.get(i).getBurst();
		}
		for (i = 0; i < n; i++) {
			int tCheg = p.get(i).getBurst() + tEsp[i];
			p.get(i).settCheg(tCheg);
			mEsp += tEsp[i];
			mResp += p.get(i).gettCheg();
			System.out.println("P" + p.get(i).getId() + " \t| Burst: " + p.get(i).getBurst() + "  \t  | T.Espera: "
					+ tEsp[i] + " " + "   | T.Parada: " + p.get(i).gettCheg());
		}
		mEsp /= i;
		mResp /= i;
		System.out.println("\nTempo medio de espera: " + mEsp);
		System.out.println("Tempo medio de entrega: " + mResp);
		System.out.println();
	}

	public ArrayList<Process> ordenar(ArrayList<Process> li) {
		Collections.sort(li); // metodo sobrescrito na Classe Process
		return li;
	}

	// Simulador do escalonamento FCFS
	public void FCFS() {
		System.out.println("First Come, First Served");
		entrada(1, 0);
		imprimir(lista);
	}

	// Simulador do escalonamento SJF
	public void SJF() {
		System.out.println("Shortest Job First");
		System.out.println("");
		entrada(1, 0);
		imprimir(ordenar(lista));
	}

	public int qualIndice(int idx) {
		if (idx >= n)
			return 0;
		else
			return idx;
	}

	public void tMedio(ArrayList<Process> li) {
		double tM = 0, tMA = 0;
		int i = 0;
		for (Process process : li) {
			tM += process.tM;
			i++;
			tMA += process.tMA;
		}
		System.out.println("Tempo Medio de espera: " + (double) (tM / i));
		System.out.println("Tempo Medio de TurnAround: " + (double) (tMA / i));
	}

	public void resolveRR(ArrayList<Process> listaaux) {
		int indice = 0, t = 0;
		// listaaux = ordenar(listaaux);
		for (Process process : listaaux) {
			t += process.burst;
			System.out.println(
					"P" + process.id + "   | burst: " + process.burst + "       |Tempo de Chegada: " + process.tCheg);
		}
		System.out.println("Consumindo: ");
		for (int i = 0; i < t;) {
			int c = indice;
			// Encontrar o proximo processo a chegar
			while (i < listaaux.get(indice).tCheg & listaaux.get(indice).burst != 0) {
				indice = qualIndice(++indice);
				if (c == indice)
					i++;
			}

			if (listaaux.get(indice).burst >= quantum) {
				System.out.println("P" + (listaaux.get(indice).id) + ": " + quantum);
				listaaux.get(indice).burst -= quantum; // consumindo o burst
				listaaux.get(indice).tChegada.add(i);
				i += quantum;
				listaaux.get(indice).tParada.add(i);
				indice = qualIndice(++indice);

			} else if (listaaux.get(indice).burst > 0) {
				System.out.println("P" + (listaaux.get(indice).id) + ": " + listaaux.get(indice).burst);
				listaaux.get(indice).tChegada.add(i);
				i += listaaux.get(indice).burst;
				listaaux.get(indice).tParada.add(i);
				listaaux.get(indice).burst = 0;
				indice = qualIndice(++indice);
			} else
				indice = qualIndice(++indice);
		}

		System.out.println();
		for (Process process : listaaux) {
			process.calculaTempo();
		}
		System.out.println();

		tMedio(listaaux);
	}

}
