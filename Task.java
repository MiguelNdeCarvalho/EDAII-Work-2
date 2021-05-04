import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

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

		ArrayList<Node>[] arrNode = new ArrayList[Integer.parseInt(tarefas_regras_hardWeeks[0])]; //obviamente tem de ser melhorado

		for (int i = 0; i < Integer.parseInt(tarefas_regras_hardWeeks[0]); i++)
		{
			arrList[i] = new ArrayList<Node>();
			arrNode[i] = new ArrayList<Node>();

			Node node_list = new Node();

			node_list.setValores(i);

			arrList[i].add(node_list);
		}

		for (int i = 0; i < Integer.parseInt(tarefas_regras_hardWeeks[1]); i++)
		{
			String[] regras = buffer.readLine().split(" ");

			arrList[Integer.parseInt(regras[0])].add(arrList[Integer.parseInt(regras[1])].get(0));
			arrList[Integer.parseInt(regras[1])].get(0).antecedentes++;
		}

		for (int k = 0; k < Integer.parseInt(tarefas_regras_hardWeeks[0]); k++)
		{
			for (int i = 0; i < arrList.length; i++)
			{
				if (arrList[i].size() > 0 && arrList[i].get(0).antecedentes == 0)
				{
					arrNode[k].add(arrList[i].remove(0));
				}
			}

			for (int i = 0; i < arrNode[k].size(); i++)
			{
				while(!arrList[arrNode[k].get(i).valor].isEmpty())
				{
					Node elemento = arrList[arrNode[k].get(i).valor].remove(0);
					elemento.antecedentes--;
				}
			}
		}

		int bad_weeks = 0, count_worstess_week = 0;

		for (int i = 0; i < arrNode.length; i++)
		{
			/*System.out.print("dia " + (i + 1) + ": " );
			for (int j = 0; j < arrNode[i].size(); j++)
			{
				System.out.print(arrNode[i].get(j).valor + " ");
			}

			System.out.println();*/



			if (arrNode[i].size() > Integer.parseInt(tarefas_regras_hardWeeks[2]))
			{
				bad_weeks++;

				if (arrNode[i].size() > count_worstess_week)
					count_worstess_week = arrNode[i].size();
			}
		}

		System.out.println(count_worstess_week + " " + bad_weeks);
	}
}

//usar uma praory queue na linha 11 do slide 98 do powerpoint 7