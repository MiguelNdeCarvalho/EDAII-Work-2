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
		
		//ler o numero de tarefas existemtes e num de regras
		String[] tarefas_regras_hardWeeks = buffer.readLine().split(" ");

		if (2 > Integer.parseInt(tarefas_regras_hardWeeks[0]) && Integer.parseInt(tarefas_regras_hardWeeks[0]) < 30000) return;

		if (1 > Integer.parseInt(tarefas_regras_hardWeeks[1]) && Integer.parseInt(tarefas_regras_hardWeeks[1]) < 3000000) return;

		if (1 > Integer.parseInt(tarefas_regras_hardWeeks[2]) && Integer.parseInt(tarefas_regras_hardWeeks[2]) < Integer.parseInt(tarefas_regras_hardWeeks[0])) return;


		ArrayList<Node>[] arrList = new ArrayList[Integer.parseInt(tarefas_regras_hardWeeks[0])];

		ArrayList<Node>[] arrNode = new ArrayList[Integer.parseInt(tarefas_regras_hardWeeks[0])];

		Stack<Node> stack = new Stack<Node>();

		for (int i = 0; i < Integer.parseInt(tarefas_regras_hardWeeks[0]); i++)
		{
			arrList[i] = new ArrayList<Node>();
			arrNode[i] = new ArrayList<Node>();

			Node node = new Node();

			node.setValores(i);

			arrList[i].add(node);
		}

		for (int i = 0; i < Integer.parseInt(tarefas_regras_hardWeeks[1]); i++)
		{
			String[] regras = buffer.readLine().split(" ");

			arrList[Integer.parseInt(regras[0])].add(arrList[Integer.parseInt(regras[1])].get(0));
			arrList[Integer.parseInt(regras[1])].get(0).antecedentes++;
		}

		for (int i = 0; i < Integer.parseInt(tarefas_regras_hardWeeks[0]); i++)
		{
			if (arrList[i].get(0).antecedentes == 0)
				stack.push(arrList[i].get(0));
		}

		//Wrong answer
		while (!stack.isEmpty())
		{
			/*int i=0;
			while(queue.peek().antecedentes != 0)
			{
				i++;
				System.out.println(i);
				order(queue);
			}*/

			/*for (Node elemento: queue)
			{
				System.out.println("no: " + elemento.valor + " week: " + elemento.week + " antecedentes: " + elemento.antecedentes);
			}*/
			//System.out.println();
			Node atual = stack.pop();
			arrList[atual.valor].remove(0);

			atual.week++;
			
			while (arrList[atual.valor].size() > 0)
			{
				Node removido = arrList[atual.valor].remove(0);
				removido.week = atual.week;
				removido.antecedentes--;
				//System.out.println("nó removido: " + removido.valor + " com " + removido.antecedentes + " antecedentes e incrementou para a semana: " + (atual.week+1) + "\n");

				if (removido.antecedentes == 0)
					stack.push(removido);
			}

			//System.out.println("no: " + atual.valor + " na semana: " + (atual.week+1));
			arrNode[atual.week].add(atual);
			
			/*for (int i = 0; i < arrNode.length && arrNode[i].size() > 0; i++)
			{
				System.out.print("week " + (i + 1) + ": " );
				for (int j = 0; j < arrNode[i].size(); j++)
					System.out.print(arrNode[i].get(j).valor + "(" + (arrNode[i].get(j).week+1) +") ");
				System.out.println();
			}*/
		}

		/*
		//Time limit
		for (int k = 0; k < Integer.parseInt(tarefas_regras_hardWeeks[0]); k++)
		{
			for (int i = 0; i < arrList.length; i++)
			{
				if (arrList[i].size() > 0 && arrList[i].get(0).antecedentes == 0)
					arrNode[k].add(arrList[i].remove(0));
			}

			for (int i = 0; i < arrNode[k].size(); i++)
			{
				while(!arrList[arrNode[k].get(i).valor].isEmpty())
				{
					Node elemento = arrList[arrNode[k].get(i).valor].remove(0);
					elemento.antecedentes--;
				}
			}
		}*/

		int bad_weeks = 0, count_worstest_week = 0;

		for (int i = 0; i < arrNode.length && arrNode[i].size() > 0; i++)
		{		
			/*System.out.print("week " + (i + 1) + ": " );
			for (int j = 0; j < arrNode[i].size(); j++)
				System.out.print(arrNode[i].get(j).valor + "(" + (arrNode[i].get(j).week+1) +") ");
			System.out.println();*/

			if (arrNode[i].size() > Integer.parseInt(tarefas_regras_hardWeeks[2]))
			{
				bad_weeks++;

				if (arrNode[i].size() > count_worstest_week)
					count_worstest_week = arrNode[i].size();
			}
		}
		System.out.println(count_worstest_week + " " + bad_weeks);
	}

	/*public static void order(PriorityQueue<Node> queue)
	{
		queue.add(queue.remove());
	}*/
}

/*class NodeComparation implements Comparator<Node>
{
	public int compare (Node n, Node v)
	{
		//Arrays.sort(queue, (a,b) -> {return a.antecedentes < b.antecedentes ? -1 : a.antecedentes > b.antecedentes ? 1 : 0;})
		if(n.antecedentes < v.antecedentes)
			return -1;
		else
			return 1;
	}
}*/

//usar uma priority queue na linha 11 do slide 98 do powerpoint 7
//o prof falou em heapsort