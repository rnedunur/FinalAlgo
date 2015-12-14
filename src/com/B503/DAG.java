package com.B503;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;




public class DAG 

{
	Map<Integer, Set<Integer>> parent=new HashMap <Integer, Set<Integer>> ();
	Map<Integer, Set<Integer>> children=new HashMap <Integer, Set<Integer>> ();
	static Map<Integer,Integer>   dependents =new HashMap<Integer, Integer>(); 
	ArrayList<Set<Integer>> subGraphs=new ArrayList<Set<Integer>>();
	//Set<Set<Integer>> subGraphs=new HashSet<Set<Integer>>();
	static PrintWriter pr1;
	static int countGraphs;

	void addParent(Integer node, Integer par)
	{
		Set<Integer>tempParent=parent.get(node);
		if(tempParent==null)
			tempParent=new HashSet<Integer>();
		tempParent.add(par);
		parent.put(node,tempParent );
	}

	void addChildren(Integer node, Integer child )
	{
		Set<Integer>tempChild=children.get(node);
		if(tempChild==null)
			tempChild=new HashSet<Integer>();
		tempChild.add(child);
		children.put(node,tempChild );

	}

	Set<Integer> getParents(Integer node)
	{

		return parent.get(node);
	}
	Set<Integer> getChildren(Integer node)
	{

		return children.get(node);
	}

	void addDependentCount()
	{

		for(Integer i: children.keySet())
		{
			if(children.get(i)!=null)
				dependents.put(i, children.get(i).size());
			else
				dependents.put(i, Integer.valueOf(0));	
		}

	}

	void PrintDependentCount()
	{
		System.out.println(dependents);
	}

	void PrintAdjacencyList()
	{
		for(Integer i:parent.keySet()){
			System.out.print("node :="+ i+"parents :="+getParents(i)+"children :="+getChildren(i));
			System.out.println();
		}
	}

	void init(Integer node)
	{

		if(!parent.containsKey(node))
			parent.put(node,null);
		if(!children.containsKey(node))
			children.put(node,null);

	}


	void generateSubGraphs(List<Entry<Integer, Integer>> sortedDependents)
	{

		Iterator parit=null;
		for(int ind=0;ind<sortedDependents.size();ind++)
		{

			Entry node=sortedDependents.get(ind);
			//System.out.println("Entry"+node);
			Set<Integer>subset=new HashSet<Integer>();
			Stack<Integer> subgraph=new Stack<Integer>();
			subgraph.push((Integer)node.getKey());
			//System.out.println(subgraph.peek());
			while(!subgraph.isEmpty())
			{
				Integer ele=subgraph.pop();
				subset.add(ele);
				Set<Integer>par=parent.get(ele);
				//System.out.println("par"+par);
				if(par!=null)
					parit=par.iterator();
				while(par !=null && parit.hasNext())
				{
					Integer it=(Integer) parit.next();
					//if(it!=null && it.intValue()>0){
					//System.out.println("it"+it);
					subgraph.push(it);}
				//if(!subgraph.isEmpty())
				//System.out.println(subset);

			}

			//System.out.println(subset);
			int flag=0;
			for(int i=0;i<subGraphs.size();i++)
			{
				if(subGraphs.get(i).equals(subset))
					flag=1;


			}
			if(flag==0)
				subGraphs.add(subset);
		}


		System.out.println(subGraphs);
		System.out.println(subGraphs.size());
		//System.out.println(subGraphs.get(49));
		try {
			pr1=new PrintWriter(new FileWriter(new File("F:/Algorithms/graph.txt")));
		} catch (IOException e) {

			e.printStackTrace();
		}
		//generateCombinations1(subGraphs.size()-1,0,pr1);
		generateUnion(subGraphs,pr1,0,subGraphs.size()-1,subGraphs.size());


	}



	static void readMiniFile()
	{
		DAG g = new DAG();
		PrintWriter pr ;

		try
		{

			Scanner in = new Scanner(new File("F:/Algorithms/mini-mfo.txt"));
			pr = new PrintWriter(new FileWriter(new File("F:/Algorithms/foo2bpo.csv")));
			in.nextLine();
			while (in.hasNextLine()) {
				final String line = in.nextLine();
				final String[] parts = line.split(" ");

				g.init(Integer.parseInt(line.substring(14)));
				g.init(Integer.parseInt(line.substring(3,10)));
				g.addParent(Integer.parseInt(line.substring(3,10)),Integer.parseInt(line.substring(14)));
				g.addChildren(Integer.parseInt(line.substring(14)),Integer.parseInt(line.substring(3,10)));


			}

			for(Integer key:g.parent.keySet())
			{

				//pr.print("hello");
				//pr.println("node is : "+key+" parents := "+g.parent.get(key)+" children := "+g.children.get(key));
				System.out.println("node is : "+key+" parents := "+g.parent.get(key)+" children := "+g.children.get(key));
				//break;
			}
			pr.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}

		g.addDependentCount();
		g.PrintDependentCount();
		MyComparator comparator=new MyComparator();
		List sortedDependents;
		sortedDependents=comparator.entriesSortedByValues(g.dependents);
		g.generateSubGraphs(sortedDependents);

		try {
			pr1=new PrintWriter(new FileWriter(new File("F:/Algorithms/graph.txt")));
		} catch (IOException e) {

			e.printStackTrace();
		}

		//g.generateCombinations1(g.subGraphs.size()-1,0,g.pr1);
		generateUnion(g.subGraphs,pr1,0,g.subGraphs.size()-1,g.subGraphs.size());


	}



	public static void generateUnion(ArrayList<Set<Integer>> asList,PrintWriter pr, int startIndex, int currentSize,int count) {
		int newSize=0;
		int i = 0 ;
		Set setA=null;
		Set setB=null;
		Set<Integer> union;



		if(currentSize<=startIndex)
		{
			System.out.println("Count of graphs "+count);
			System.exit(0);
			pr.close();
		}
		else
		{
			/* System.out.println("start index"+startIndex);
            System.out.println("current index"+currentSize);
			System.out.println(" Initial Size " + currentSize ) ;*/


			pr.println("asList in the function         "+ asList);
			pr.flush();
			//System.out.println(asList);


			for(i=startIndex; i<=currentSize-1;i++) {
				int k = startIndex+1;

				//if(i<=currentSize)
				setA = asList.get(i);

				while( k <= currentSize ) {
					setB = asList.get(k);
					union = new HashSet<Integer>();
					union.addAll(setA);
					union.addAll(setB);
					if(asList.contains(union)){
					}
					else{
						asList.add(union);
						count+=1;
						pr.println("union    "+union);

						pr.flush();
					}


					k++;
				}



			}




			pr.println("currentSize"+currentSize);
			for(int cnt=0;cnt<=currentSize;cnt++)
			{   

				//pr.println("removed elemnet"+asList.get(0));
				asList.remove(0);
				//pr.flush();


			}
			//pr.println( "after removal   "+asList);
			//pr.flush();
			newSize = asList.size();
			startIndex=0;
			generateUnion( asList, pr,startIndex,newSize-1,count);
		}



	}





	void generateCombinations(DAG graph)
	{
		int currInd=graph.subGraphs.size();
		currInd=currInd-1;
		int prevInd=0;

		for(int ind1=currInd;ind1>=prevInd;ind1--)
		{
			for(int ind2=ind1-1;ind2>=prevInd;ind2--)
			{
				Set<Integer>s1=graph.subGraphs.get(ind1);
				Set<Integer>s2=graph.subGraphs.get(ind2);
				if(s1.addAll(s2)==true)
					graph.subGraphs.add(s1);
			}
		}
		prevInd=currInd;
		currInd=graph.subGraphs.size()-1;
	}



	void generateCombinations1(int currInd, int prevInd,PrintWriter pr)
	{
		System.out.println("curr"+currInd+"prev"+prevInd);
		//int currInd=graph.subGraphs.size();
		//currInd=currInd-1;
		//int prevInd=0;
		if(currInd<=prevInd)
		{
			System.out.println("curr"+currInd);
			System.out.println("prev"+prevInd);
			System.out.println("All generated");
			System.exit(0);
			return;
		}

		else
		{	
			for(int ind1=currInd;ind1>=prevInd;ind1--)
			{
				for(int ind2=ind1-1;ind2>=prevInd;ind2--)
				{
					Set<Integer>s1=subGraphs.get(ind1);
					Set<Integer>s3=new HashSet<Integer>();
					s3=s1;
					Set<Integer>s4=new HashSet<Integer>();
					Set<Integer>s2=subGraphs.get(ind2);
					s4=s2;
					System.out.println("s1="+s1);
					System.out.println("s2="+s2);
					if(s3.addAll(s4)==true)
					{

						System.out.println("after concat s1="+s1);


						int flag=0;
						for(int chk=0;chk<subGraphs.size();chk++)
						{
							if(s3.equals(subGraphs.get(chk))==true)

							{   
								System.out.println("subGraphs"+subGraphs);
								System.out.println("subGraphs.get(chk)"+subGraphs.get(chk));
								System.out.println("chk"+chk);
								flag=1;
							}


						}
						System.out.println("flag"+flag);
						if(flag==0)
							subGraphs.add(s3);
					}
				}
			}
			//System.out.println(subGraphs);
			//pr.println(subGraphs);

			for(int temp=currInd;temp>=prevInd;temp--)
				subGraphs.remove(temp);

			System.out.println(subGraphs);

			/*try {
				//System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			generateCombinations1(subGraphs.size()-1,0,pr);

		}
	}


	public static void readMatrix()
	{
		DAG g = new DAG();
		int nodes=4567, cntNode=0,cntAdj=0;
		Scanner scanner=null;
		//PrintWriter pr1 ;
		try {
			//scanner = new Scanner(new File("/home/helloworld/tree_100.txt"));
			scanner = new Scanner(new File("F:/Algorithms/dag25-2.txt"));
			//pr = new PrintWriter(new FileWriter(new File("F:/Algorithms/foo2bpo.csv")));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		try
		{

			while(scanner.hasNextLine())
			{
				cntNode++;
				System.out.println("cntNode"+cntNode);
				g.init(Integer.valueOf(cntNode));




				//children.put(Integer.valueOf(cntNode),new HashSet<Integer>());
				String line;
				//Set<Integer>tempNode=new HashSet<Integer>();
				String lineSplit[]=new String[100];
				line=scanner.nextLine();
				System.out.println("line"+line);
				lineSplit=line.split(" ");
				for (int i = 0; i < lineSplit.length; i++) {
					System.out.print(lineSplit[i]);
					if(Integer.parseInt(lineSplit[i])==1){
						//tempNode.add(Integer.valueOf(i+1));
						g.init(Integer.valueOf(i+1));
						g.addParent(Integer.valueOf(i+1),Integer.valueOf(cntNode));
						g.addChildren(Integer.valueOf(cntNode),Integer.valueOf(i+1));


					}
				}
				//children.put(cntNode, tempNode);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("children"+children);
		//System.out.println((long)subGraphs(Integer.valueOf(1)));

		g.addDependentCount();
		g.PrintDependentCount();
		MyComparator comparator=new MyComparator();
		List sortedDependents;
		sortedDependents=comparator.entriesSortedByValues(g.dependents);
		g.generateSubGraphs(sortedDependents);

		try {
			pr1=new PrintWriter(new FileWriter(new File("F:/Algorithms/graphmatrix.txt")));
		} catch (IOException e) {

			e.printStackTrace();
		}

		//g.generateCombinations1(g.subGraphs.size()-1,0,g.pr1);
		generateUnion(g.subGraphs,pr1,0,g.subGraphs.size()-1,g.subGraphs.size());

	}

	

	public static void main(String args[])
	{
		DAG graph=new DAG();
		//01
		/*graph.init(Integer.valueOf(1));
		graph.addParent(Integer.valueOf(1),Integer.valueOf(-1));
		graph.addChildren(Integer.valueOf(-1),1);
		//12
		graph.init(Integer.valueOf(1));
		graph.init(Integer.valueOf(2));
		graph.addParent(Integer.valueOf(2),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1), Integer.valueOf(2));
		//13
		graph.init(Integer.valueOf(1));
		graph.init(Integer.valueOf(3));
		graph.addParent(Integer.valueOf(3),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1), Integer.valueOf(3));
		//1,14
		graph.init(Integer.valueOf(1));
		graph.init(Integer.valueOf(14));
		graph.addParent(Integer.valueOf(14),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1), Integer.valueOf(14));
		//
		graph.addParent(Integer.valueOf(14),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1), Integer.valueOf(14));
		//2,4
		graph.init(Integer.valueOf(2));
		graph.init(Integer.valueOf(4));
		graph.addParent(Integer.valueOf(4),Integer.valueOf(2));
		graph.addChildren(Integer.valueOf(2), Integer.valueOf(4));
		//2,5
		graph.init(Integer.valueOf(2));
		graph.init(Integer.valueOf(5));
		graph.addParent(Integer.valueOf(5),Integer.valueOf(2));
		graph.addChildren(Integer.valueOf(2), Integer.valueOf(5));
		//4,6
		graph.init(Integer.valueOf(4));
		graph.init(Integer.valueOf(6));
		graph.addParent(Integer.valueOf(6),Integer.valueOf(4));
		graph.addChildren(Integer.valueOf(4), Integer.valueOf(6));
		//5,6
		graph.init(Integer.valueOf(5));
		graph.init(Integer.valueOf(6));
		graph.addParent(Integer.valueOf(6),Integer.valueOf(5));
		graph.addChildren(Integer.valueOf(5), Integer.valueOf(6));
		//3,8
		graph.init(Integer.valueOf(3));
		graph.init(Integer.valueOf(8));
		graph.addParent(Integer.valueOf(8),Integer.valueOf(3));
		graph.addChildren(Integer.valueOf(3), Integer.valueOf(8));*/

		graph.init(Integer.valueOf(1));
		graph.addParent(Integer.valueOf(1),Integer.valueOf(-1));
		graph.addChildren(Integer.valueOf(-1),1);

		graph.init(Integer.valueOf(2));
		graph.addParent(Integer.valueOf(2),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1),Integer.valueOf(2));

		graph.init(Integer.valueOf(3));
		graph.addParent(Integer.valueOf(3),Integer.valueOf(1));
		graph.addChildren(Integer.valueOf(1),Integer.valueOf(3));

		graph.init(Integer.valueOf(4));
		graph.addParent(Integer.valueOf(4),Integer.valueOf(2));
		graph.addChildren(Integer.valueOf(2),Integer.valueOf(4));

		graph.init(Integer.valueOf(5));
		graph.addParent(Integer.valueOf(5),Integer.valueOf(2));
		graph.addChildren(Integer.valueOf(2),Integer.valueOf(5));


		graph.init(Integer.valueOf(5));
		graph.addParent(Integer.valueOf(5),Integer.valueOf(3));
		graph.addChildren(Integer.valueOf(3),Integer.valueOf(5));


		graph.init(Integer.valueOf(6));
		graph.addParent(Integer.valueOf(6),Integer.valueOf(3));
		graph.addChildren(Integer.valueOf(3),Integer.valueOf(6));

		graph.init(Integer.valueOf(7));
		graph.addParent(Integer.valueOf(7),Integer.valueOf(3));
		graph.addChildren(Integer.valueOf(3),Integer.valueOf(7));

		graph.init(Integer.valueOf(7));
		graph.addParent(Integer.valueOf(7),Integer.valueOf(6));
		graph.addChildren(Integer.valueOf(6),Integer.valueOf(7));








		/*graph.PrintAdjacencyList();
		graph.addDependentCount();
		graph.PrintDependentCount();
		MyComparator comparator=new MyComparator();
		List sortedDependents;
		sortedDependents=comparator.entriesSortedByValues(dependents);
		System.out.println("dependents after sorting"+dependents);
		System.out.println("sorted dependents"+sortedDependents);
		graph.generateSubGraphs(sortedDependents);*/
		//readMiniFile();
		readMatrix();






	}


}
