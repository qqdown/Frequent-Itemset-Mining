package pers.itemsetmining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pers.itemsetming.algorithms.Apriori;
import pers.itemsetmining.util.HashTree;

public class Main {
	
	private static String dataPath = "F:\\OtherProject\\DataMining\\Assignment2\\assignment2-data.txt";
	private static double minsup = 0.144;
	
	public static void main(String[] args) throws IOException {
		//test();
		if(args.length == 1)
			dataPath = args[0];
		File dataFile = new File(dataPath); 
		if(!dataFile.exists())
		{
			System.out.println(dataPath + "不存在！");
		}
		
		String str;
		BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)));  
		str = br.readLine();//读取第一行标题
		
		
		String[] titles = str.split(" ");
		Item[] titleItemSet = new Item[titles.length];
		for(int i=0; i<titles.length; i++)
		{
			titleItemSet[i] = new Item(titles[i]);
		}

		List<Transaction> transactions = new ArrayList<Transaction>();
        while ((str = br.readLine()) != null) {  
        	String[] contents = str.split(" ");
        	Transaction trans = new Transaction();

        	for(int i=0; i<contents.length; i++)
        	{
        		if(contents[i].equals("1"))
        		{	
        			trans.addItem(new Item(titles[i])); 
        		}
        		
        	}

        	transactions.add(trans);
        }  
        br.close();
        System.out.println("finish init transactions\n"
        		+ "transactions size:" + transactions.size());

        Apriori apriori = new Apriori(titleItemSet, transactions, minsup);
        List<Candidate> resultSet = apriori.run();
        
        String outputPath = "output/result.txt";
        File outFile = new File(outputPath);
        if(!outFile.getParentFile().exists())
        	outFile.getParentFile().mkdirs();
        if(!outFile.exists())
        	outFile.createNewFile();
        FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
        for(Candidate c : resultSet)
        {
        	System.out.println(c.toString() + " " +  (c.count*1.0/transactions.size()));
        	bw.write(c.toString() + " " +  (c.count*1.0/transactions.size()));
        	bw.newLine();
        }
        bw.close();
        fw.close();
        System.out.println("输出保存至：" + outFile.getAbsolutePath());
	}
	
	public static void test()
	{
		int[] is = new int[]{1,2,3,5,6};
		Transaction trans = new Transaction();
		for(int i : is)
		{
			trans.addItem(new Item(i + ""));
		}
		
		int[] candis = new int[]{
				1,2,4,
				1,2,5,
				1,4,5,
				1,3,6,
				2,3,4,
				5,6,7,
				3,4,5,
				3,5,6,
				3,5,7,
				6,8,9,
				3,6,7,
				3,6,8,
				4,5,7,
				4,5,8
		};
		
		HashTree ht = new HashTree(3, 3);
		List<Candidate> cands = new ArrayList<Candidate>();
		for(int i=0; i<candis.length; i+=3)
		{
			Candidate c = new Candidate();
			c.addItem(new Item(candis[i]+""));
			c.addItem(new Item(candis[i+1]+""));
			c.addItem(new Item(candis[i+2]+""));
			cands.add(c);
			ht.add(c);
		}
		ht.count(trans, 3);
		ht.output();
	}

}
