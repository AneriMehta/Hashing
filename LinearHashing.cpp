#include<iostream>
#include<vector>
#include<bits/stdc++.h>
#define LOADFACTOR 0.7
#define BUCKETSIZE 2
using namespace std;
//Collision resolution technique : Chaining
//Split on Load Factor>0.7
//To insert element: key % insertVal
//To redistribute bucket: key % splitVal
//Number of Buckets, Number of Elements
//flag is the pointer to the bucket to be split when required

int M=2;
int flag=0, insertVal=M, splitVal=2*M;
float Buckets=4, Elements=0;

void initialize();
void insertEle(int n, int modVal);
void Split();
void Show();

class chainedEle
{
	public:
		int ele;
		chainedEle *next;
    public:
        void setval(int n)
        {
            ele = n;
            next = NULL;
        }
};

class index
{
	public:
		int key;
		int bucket[BUCKETSIZE];
		chainedEle *ptr;

	public:
		void setVal(int k)
		{
			key = k;
			for(int j=0;j<BUCKETSIZE;j++)
                bucket[j]=-1;
			ptr = NULL;
			//cout<<ptr<<"\n";
		}
};

vector<index> ind;

void initialize()
{
	index p1;
	for(int i=0;i<M;i++)
	{
		p1.setVal(i);
		ind.push_back(p1);
	}
	Show();
}

void insertEle(int n, int modVal)
{
	index p2;
	int k = n%modVal;
	p2 = ind.at(k);
	int f=0;
    for(int i=0;i<BUCKETSIZE;i++)
    {
        if(p2.bucket[i]==-1)
        {
            p2.bucket[i]=n;
            f=1;
            break;
        }
    }
    if(f==0)
    {
        chainedEle ch;
        ch.setval(n);
        if(p2.ptr==NULL)
        {
            p2.ptr=&ch;
        }
        else
        {
            chainedEle *c;
            c->next=p2.ptr->next;
            c->ele = p2.ptr->ele;
            while(c->next!=NULL)
                c = c->next;
            c->next = ch.next;
            c->ele = ch.ele;
        }
    }
    ind.at(k) = p2;
    Elements+=1;
    Split();
}

void Split()
{
    float chk = Elements/Buckets;
    cout<<chk<<"\n";
    if(chk>LOADFACTOR)
    {
        index p1,p2;
        p2.setVal(flag+M);
        ind.push_back(p2);
        Buckets+=BUCKETSIZE;
        p1 = ind.at(flag);
        int temp[BUCKETSIZE], temp1[10];
        for(int i=0;i<BUCKETSIZE;i++)
        {
            if(p1.bucket[i]!=(-1))
            {
                temp[i] = p1.bucket[i];
                p1.bucket[i]=(-1);
                Elements-=1;
            }
            else
            {
                temp[i]=-1;
            }
        }
        ind.at(flag) = p1;
        for(int j=0;j<BUCKETSIZE;j++)
        {
            if(temp[j]!=-1)
                insertEle(temp[j],splitVal);
        }
        p1 = ind.at(flag);
        if(p1.ptr!=NULL)
        {
            int j=0;
            chainedEle *ck;
            ck->next=p1.ptr->next;
            ck->ele = p1.ptr->ele;
            temp1[j] = ck->ele;
            Elements-=1;
            while(ck->next!=NULL)
            {
                temp1[j] = ck->ele;
                Elements-=1;
                j+=1;
                ck = ck->next;
            }
            p1.ptr=NULL;
            ind.at(flag) = p1;
            for(int k=0;k<=j;k++)
            {
                insertEle(temp1[k],splitVal);
            }
        }
        flag+=1;
        if(flag==M)
        {
            flag=0;
            M = M*2;
            splitVal = 2*M;
            insertVal = M;
        }
    }
}

void Show()
{
    int iter = (int)(Buckets/BUCKETSIZE);
    index id;
    vector<index>::iterator pt;
    for(pt=ind.begin();pt<ind.end();pt++)
    {
        id = *pt;
        cout<<"Elements at "<<id.key<<"\n";
        for(int j=0;j<BUCKETSIZE;j++)
        {
            if(id.bucket[j]!=(-1))
            {
                cout<<id.bucket[j]<<" ";
            }
            else
                break;
        }
        if(id.ptr !=NULL)
        {
            chainedEle *che;
            che=id.ptr;
            cout<<"---> "<<che->ele<<" ";
         }
        cout<<"\n";
    }
}

int main()
{
	int n,ans;
	initialize();
	while(true)
	{
		cout<<"Enter element: ";
		cin>>n;
		insertEle(n,insertVal);
		Show();
		cout<<"Do you want to enter more elements?1=yes/0=n ";
		cin>>ans;
		if(ans==0)
			break;
	}
}
