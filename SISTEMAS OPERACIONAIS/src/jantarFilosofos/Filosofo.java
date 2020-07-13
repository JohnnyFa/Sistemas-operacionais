package jantarFilosofos;
/*
 * Johnny Fagundes de Paula - U260593
 */

public class Filosofo extends Thread {
    int identific;
    final int COMENDO = 2;
    final int COMFOME = 1;
    final int PENSANDO = 0;
    
    public Filosofo(String nome, int identific) {
        super(nome);
        this.identific = identific;
    }

    public void ComFome() {	
    	//estado: fome/ com fome/ pensando///
        Main.estado[this.identific] = 1;
        System.out.println("O filosofo " + getName() + " esta com FOME!");
    }

    public void Comer() {
        Main.estado[this.identific] = 2;
        System.out.println("O filosofo " + getName() + " esta COMENDO!");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Pensar() {
        Main.estado[this.identific] = 0;
        System.out.println("O filosofo " + getName() + " esta PENSANDO!");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void LargarGarfo() throws InterruptedException {
        Main.mutex.acquire();
        Pensar();
        Main.filosofos[GarfoEsquerdo()].TryCatch();
        Main.filosofos[GarfoDireito()].TryCatch();
        Main.mutex.release();
    }

    public void PegarGarfo() throws InterruptedException {
        Main.mutex.acquire();
        ComFome();
        TryCatch();        
        Main.mutex.release();
        Main.semaforos[this.identific].acquire();
    }
    //try to get the fork
    public void TryCatch() {
        if (Main.estado[this.identific] == 1
                && Main.estado[GarfoEsquerdo()] != 2
                && Main.estado[GarfoDireito()] != 2) {
            Comer();
            Main.semaforos[this.identific].release();
        } else {
            System.out.println(getName() + " NAO conseguiu comer!");
        }

    }

    @Override
    public void run() {
        try {
            Pensar();
            do {
                PegarGarfo();
                Thread.sleep(1000);
                LargarGarfo();
            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }

    //pegando os garfos de cada lado//
    public int GarfoDireito() {
        return (this.identific + 1) % 5;
    }

    public int GarfoEsquerdo() {
        if (this.identific == 0) {            
            return 4;
        } else {
            return (this.identific - 1) % 5;
        }
    }
}