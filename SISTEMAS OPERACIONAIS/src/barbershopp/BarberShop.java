package barbershopp;
/*
 * Johnny Fagundes de Paula - U260593
 * Gianlucca Portela Claudino - U260984
 */
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BarberShop {
    public static void main(String a[])
    {
        Bar barbearia = new Bar();
 
        Barbeiro Barbeiro = new Barbeiro(barbearia);
        GeradorClientes gc = new GeradorClientes(barbearia);
 
        Thread thbarbereiro = new Thread(Barbeiro);
        Thread thgc = new Thread(gc);
        thgc.start();
        thbarbereiro.start();
    }
} 
class Barbeiro implements Runnable
{
    Bar barbearia; 
    public Barbeiro(Bar barbearia)
    {
        this.barbearia = barbearia;
    }
    public void run()
    {
        try
        {
            Thread.sleep(10000);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println("Barbeiro comecou os trabalhos..");
        System.out.println(" "); 
        while(true)
        {
            barbearia.cortarCabelo();
        }
    }
}
class Cliente implements Runnable
{
    String name;
    Bar barbearia;
 
    public Cliente(Bar barbearia)
    {
        this.barbearia = barbearia;
    }
 
    public String getName() {
        return name;
    } 
    public void setName(String name) {
        this.name = name;
    }
    public void run()
    {
        cortandoCabelo();
    }
    private synchronized void cortandoCabelo()
    {
        barbearia.adicionar(this);
    }
} 
class GeradorClientes implements Runnable
{
    Bar barbearia;
    int i = 1;
    public GeradorClientes(Bar barbearia)
    {
        this.barbearia = barbearia;
    } 
    public void run()
    {
        while(true)
        {
            Cliente cliente = new Cliente(barbearia);
            Thread thCliente = new Thread(cliente);
			cliente.setName("Cliente " + i);
			i++;
            thCliente.start();
            try
            {
                TimeUnit.SECONDS.sleep((long)(Math.random()*10));
            }
            catch(InterruptedException iex)
            {
                iex.printStackTrace();
            }
        }
    } 
}
 
class Bar
{
    int nCadeiras;
    List<Cliente> listCliente; 
    public Bar()
    {
        nCadeiras = 3;
        listCliente = new LinkedList<Cliente>();
    } 
    public void cortarCabelo()
    {
        Cliente Cliente;
        synchronized (listCliente)
        {
 
            while(listCliente.size()==0)
            {
                System.out.println("Barbeiro esperando por clientes(dormindo)...");
                try
                {
                    listCliente.wait();
                }
                catch(InterruptedException iex)
                {
                    iex.printStackTrace();
                }
            }
            System.out.println("Barbeiro chamou cliente da sala de espera.");
            System.out.println(" "); 
            Cliente = (Cliente)((LinkedList<?>)listCliente).poll();
        }
        long duracao=0;
        try
        {    
            System.out.println("Cortando cabelo do "+Cliente.getName());
            System.out.println(" ");
            duracao = (long)(Math.random()*10);
            TimeUnit.SECONDS.sleep(duracao);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println("Terminou de cortar o cabelo do "+Cliente.getName());
        System.out.println(" "); 
    } 
    public void adicionar(Cliente Cliente)
    {
        System.out.println(Cliente.getName()+ " Entrou na barbearia.");
        System.out.println(" ");
        synchronized (listCliente)
        {
            if(listCliente.size() == nCadeiras)
            {
                System.out.println("Nenhuma cadeira disponivel para o "+Cliente.getName());
                System.out.println(Cliente.getName()+" vai embora...");
                System.out.println(" ");
                return ;
            } 
            ((LinkedList<Cliente>)listCliente).offer(Cliente);
            System.out.println(Cliente.getName()+ " pegou uma cadeira.");
            System.out.println(" "); 
            if(listCliente.size()==1)
                listCliente.notify();
        }
    }
}