import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Task
{
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException
	{
		//declarar um buffer para puder ler inputs
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		
		//ler o num de tarefas existentes, num de regras e significado de uma semana má
		String[] tarefasRegrasHardWeeks = buffer.readLine().split(" ");

		//limites do programa
		//2 <= num de tarefas <= 30 000
		//1 <= num regras <= 300 000
		//1 <= sig má semana <= num de tarefas
		if (Integer.parseInt(tarefasRegrasHardWeeks[0]) < 2 || Integer.parseInt(tarefasRegrasHardWeeks[0]) > 30000) return;

		if (Integer.parseInt(tarefasRegrasHardWeeks[1]) < 1 || Integer.parseInt(tarefasRegrasHardWeeks[1]) > 3000000) return;

		if (Integer.parseInt(tarefasRegrasHardWeeks[2]) < 1 || Integer.parseInt(tarefasRegrasHardWeeks[2]) >= Integer.parseInt(tarefasRegrasHardWeeks[0])) return;

		//declarar lista de adjacencias e pilha
		//a primeira é a lista de adjacencias representar o grafo
		//em ultimo temos a fila
		ArrayList<Node>[] tarefasPrecedencias = new ArrayList[Integer.parseInt(tarefasRegrasHardWeeks[0])];
		Queue<Node> queue = new LinkedList<Node>();

		//criação dos nós para o grafo
		for (int i = 0; i < Integer.parseInt(tarefasRegrasHardWeeks[0]); i++)
		{
			//criar uma lista para guardar todos as adjacencias
			tarefasPrecedencias[i] = new ArrayList<Node>();

			//criar o nó
			Node node = new Node();

			//atribuir valores ao nó
			node.setValores(i);

			//add o nó à lista, onde o primeiro elemento tem de ser lido primeiro que todos os seus precedentes
			tarefasPrecedencias[i].add(node);
		}

		//criação do grafo
		for (int i = 0; i < Integer.parseInt(tarefasRegrasHardWeeks[1]); i++)
		{
			//ler a intrepetação do arco (u,v)
			String[] regras = buffer.readLine().split(" ");

			//indicar que o arco tem um sentido de u para v e em seguida indica que o nó v tem um antecessor
			tarefasPrecedencias[Integer.parseInt(regras[0])].add(tarefasPrecedencias[Integer.parseInt(regras[1])].get(0));
			tarefasPrecedencias[Integer.parseInt(regras[1])].get(0).antecessor++;
		}

		//verificar quais sao os nós que não tem adjacencias e coloca na fila
		for (int i = 0; i < Integer.parseInt(tarefasRegrasHardWeeks[0]); i++)
		{
			if (tarefasPrecedencias[i].get(0).antecessor == 0)
			{
				tarefasPrecedencias[i].get(0).week++;
				queue.add(tarefasPrecedencias[i].get(0));
			}
		}

		int badWeeks = 0, worstestWeek = 0, semana = 1;

		//Wrong answer
		//enquanto a fila nao tiver vazio vai sempre lendo o que está na fila
		while (!queue.isEmpty())
		{
			//quando achar a comparação significa que existe todas as tarefas dessa semana na fila
			if (queue.peek().week == semana)
			{
				//ve quantas tarefas existem para fazer nessa semana (tamanho da queue)
				int size = queue.size();

				//verificar se as tarefas que existem sao maior que o numero que tem o significado de uma semana má
				if (size > Integer.parseInt(tarefasRegrasHardWeeks[2]))
					badWeeks++;

				//verifica se é a pior semana
				if (size > worstestWeek)
					worstestWeek = size;
				semana++;
			}

			Node atual = queue.remove();

			//verifica todas as adjacencias do nó atual do grafo
			for (Node removido : tarefasPrecedencias[atual.valor])
			{
				//atualizar semana para o antecessor (como o nó so vai para a fila quando todos os antecessores
				//forem visitados entao quando for a vez do nó removido ir para a fila o ultimo antecessor é o maior
				//pois como é uma pesquisa em largura, vimos por niveis)
				removido.week = atual.week;

				//diminuimos o numero de antecessores
				removido.antecessor--;

				//quando nao houver mais antecessores vai para a fila
				if (removido.antecessor == 0)
				{
					removido.week++;
					queue.add(removido);
				}
			}
		}
		System.out.println(worstestWeek + " " + badWeeks);
	}
}