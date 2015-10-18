package pers.itemsetmining.util;

import java.util.ArrayList;
import java.util.List;

import pers.itemsetmining.CandidateItems;

public class HashTreeNode 
{
	public HashTreeNode[] branches;//�ӽڵ�
	public List<CandidateItems> candidateSets;//��ѡ���
	public boolean isLeaf;
	
	private int level;//���ڲ�,���ڵ�Ϊ0����������ѡ��
	private int degree;//ɢ���̶�
	
	public HashTreeNode(int level, int degree)
	{
		this.isLeaf = true;
		this.level = level;
		this.degree = degree;
		branches = new HashTreeNode[degree];
		candidateSets = new ArrayList<CandidateItems>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
}
