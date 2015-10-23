package pers.itemsetmining.util;

import pers.itemsetmining.Candidate;
import pers.itemsetmining.Transaction;

public class HashTree 
{
	private HashTreeNode root;
	private int degree;
	private int splitThreashold;

	public HashTree(int degree, int splitThreshold)
	{
		this.degree = degree;
		this.splitThreashold = splitThreshold;
		root = new HashTreeNode(0, degree);//�������ڵ�
	}
	
	public void add(Candidate candi)
	{
		HashTreeNode node = root;
		int curLevel = 0;//��ǰ����,0����root
		while(true)//���Ҳ���λ��
		{
			int h = candi.getItem(curLevel).hashCode() % degree;
			if(node.branches[h] == null)//��δ������ֱ�����
			{
				node.branches[h] = new HashTreeNode(curLevel+1, degree);
				node.branches[h].candidateSets.add(candi);
				node.isLeaf = false;
				break;
			}
			else
			{
				if(node.branches[h].isLeaf)//������ڵ�Ϊ���ڵ�
				{
					//����
					node.branches[h].candidateSets.add(candi);
					//��ѡ�ڵ�����������з���
					if(node.branches[h].candidateSets.size() > splitThreashold && curLevel+1<candi.getSize())
					{
						node.branches[h].isLeaf = false;
						for(Candidate c : node.branches[h].candidateSets)
						{
							int hash = c.getItem(curLevel+1).hashCode() % degree;
							if(node.branches[h].branches[hash] == null)
								node.branches[h].branches[hash] = new HashTreeNode(curLevel+2, degree);
							node.branches[h].branches[hash].candidateSets.add(c);
						}
						node.branches[h].candidateSets.clear();
					}
					
					break;
				}
				else//Ѱ����һ���ڵ�
				{
					curLevel++;
					node = node.branches[h];
				}
			}
		}
	}

	//��������ʼ�ڵ��countֵ��Ϊ0
	public void count(Transaction trans, int k)
	{
		Candidate cand = trans.cand;

		count(new Candidate(), cand, k, root);
	}
	
	//�����ĵݹ麯������ʼ�ڵ��countֵ��Ϊ0
	private void count(Candidate prefixCand, Candidate searchCand, int k, HashTreeNode rootnode)
	{
		//System.out.println(prefixCand.toString() + " | " + searchCand.toString());
		
		HashTreeNode node = rootnode;
		if(node == null)
			return;
		//�ж��Ƿ�Ϊ�Ӽ�
		if(node.isLeaf)
		{
			for(Candidate c : node.candidateSets)
			{
				boolean isContain = true;
				//ǰ׺��ͬ����׺����
				if(!prefixCand.equals(c.sub(0, prefixCand.getSize())))
				{
					isContain = false;
				}
				else
				{
					Candidate remainCand = c.sub(prefixCand.getSize(), c.getSize());
					for(int j=0; j<remainCand.getSize(); j++)
					{
						if(!searchCand.contains(remainCand.getItem(j)))
						{
							isContain = false;
							break;
						}
					}
				}
				if(isContain)
				{	
					c.count ++;
				}
			}
		}
		else
		{
			Candidate newSearchCand = searchCand.clone();
			for(int i=0; i<searchCand.getSize(); i++)
			{
				Candidate newPrefixCand = prefixCand.clone();
				newPrefixCand.addItem(newSearchCand.removeItem(0));//ÿ��SEARCH����һ����prefix����һ��
					
				int h = searchCand.getItem(i).hashCode() % degree;
				count(newPrefixCand, newSearchCand, k, node.branches[h]);
				
			}
		}
	}
	
	public void output()
	{
		outputTree(root, 0);
	}
	
	private void outputTree(HashTreeNode root, int i)
	{
		if(root == null)
			return;
		if(root.isLeaf)
		{
			System.out.print(root.getLevel() + "," + i + ": ");
			for(Candidate ci : root.candidateSets)
			{
				System.out.print(ci.toString() + " count:" + ci.count + "   ");
			}
			System.out.println();
		}
		for(i=0; i<root.branches.length; i++)
		{
			outputTree(root.branches[i], i);
		}
	}

	public int getSplitThreashold() {
		return splitThreashold;
	}
}
