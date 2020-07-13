package jantarFilosofos;
/*

 * Johnny Fagundes de Paula - U260593
 */

import java.util.concurrent.Semaphore;

public class Main {

	static Semaphore mutex = new Semaphore(1);

	static Semaphore[] semaforos = new Semaphore[5];

	static Filosofo[] filosofos = new Filosofo[5];
	
	static int[] estado = new int[5];

	public static void main(String[] args) {
		System.out.println("\33[2J");

		// Start dos filosofos // Todos iniciam pensando visto que o estado incial e igual a 0;
		filosofos[0] = new Filosofo("ARISTOTELES", 0);
		filosofos[1] = new Filosofo("PLATAO", 1);
		filosofos[2] = new Filosofo("KANT", 2);
		filosofos[3] = new Filosofo("NIETZSCHE", 3);
		filosofos[4] = new Filosofo("SOCRATES", 4);

		System.out.println(" POSICAO DOS GARFOS E FILOSOFOS NA MESA:  ");
		// Garfo de cada filosofo
		for (int i = 0; i < filosofos.length; i++) {			
			System.out.println("- Garfo " + i + " - Filosofo  " + i + " - Garfo " + (i + 1) % 5);
		}
		System.out.println(" ");
		for (int i = 0; i < semaforos.length; i++) {
			semaforos[i] = new Semaphore(0);
		}
		System.out.println(" ");
		for (int i = 0; i < filosofos.length; i++) {
			filosofos[i].start();
		}
	}

}
