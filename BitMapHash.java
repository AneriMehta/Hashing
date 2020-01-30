import java.io.*;
import java.util.Scanner;

public class BitMapHash
{
  static int n,nor = 12;
  static int count1=0,count2=0;
  static int[][] salary=new int[nor+1][nor+1];
  static int[][] age=new int[nor+1][nor+1];
  static int[][] a = new int[][]{{63,100,41},{25,900,34},{27,700,54},{63,950,74},{25,100,26},{27,950,74},{25,700,23},{36,500,53},{48,900,94},{27,100,24},{48,400,93},{50,500,35}};
  
  public void CreateBitMap()
  {
    //BitMapHash b = new BitMapHash();
    for(int i=0;i<nor;i++)
    {
        int flag1=0,flag2=0;
        for(int j=0;j<count1;j++)
        {
            if(age[j][0] == a[i][0])
            {
                age[j][i+1]=1;
                flag1=1;
                break;
            }
        }
        if(flag1==0)
        {
            age[count1][0]=a[i][0];
            age[count1][i+1]=1;
            count1++;
        }
        for(int j=0;j<count2;j++)
        {
            if(salary[j][0]==a[i][1])
            {
                salary[j][i+1]=1;
                flag2=1;
                break;
            }
        }
        if(flag2==0)
            {
                salary[count2][0]=a[i][1];
                salary[count2][i+1]=1;
                count2++;
            }
    }
    for(int i =0;i<count1;i++)
    {
        for(int j=0;j<=nor;j++)
        {
            if(j==0)
            {
                System.out.print(age[i][j]+"-->");
            }
            else
                System.out.print(age[i][j]);
        }
        System.out.print("\n");
    }
    for(int i =0;i<count2;i++)
    {
        for(int j=0;j<=nor;j++)
        {
             if(j==0)
            {
                System.out.print(salary[i][j]+"-->");
            }
            else
                System.out.print(salary[i][j]);
        }
        System.out.print("\n");
    }
    BitMapHash b1 = new BitMapHash();
    b1.sortBitmap(age, count1, nor);
    b1.sortBitmap(salary, count1, nor);
  }
  void sortBitmap(int[][] a, int cnt, int n)
  {
      int[] temp;
      int index=0;
      for(int i=0;i<(cnt-1);i++)
      {
          int min = a[i][0];
          for(int j=i+1;j<cnt;j++)
          {
            if(min>a[j][0])
            {
                min = a[j][0];
                index = j;
            }
          }
          temp = a[index].clone();
          a[index] = a[i].clone();
          a[i] = temp.clone();
      }
   for(int i =0;i<cnt;i++)
    {
        for(int j=0;j<=n;j++)
        {
             if(j==0)
            {
                System.out.print(a[i][j]+"-->");
            }
            else
                System.out.print(a[i][j]);
        }
        System.out.print("\n");
    }   
  }
  
  public void simpleQuery(int a1, int s1)
  {
      int n1=0,n2=0,fg1=0,fg2=0;
      for(int i=0;i<count1;i++)
      {
          if(age[i][0]==a1)
          {
            n1 = i;
            fg1=1;
          }
      }
      for(int i=0;i<count2;i++)
      {
          if(salary[i][0]==s1)
          {
            n2 = i;
            fg2=1;
          }
      }
      if(fg1==1 && fg2==1)
      {
        int[] tmp = new int[nor];
        for(int i=1;i<=nor;i++)
        {
          tmp[i-1] = (age[n1][i])&(salary[n2][i]);
        }
        for(int j=0;j<nor;j++)
        {
          if(tmp[j]==1)
          {
              System.out.println("Record: "+a[j][0]+" "+a[j][1]+" "+a[j][2]+"\n");
          }
        }
      }
      else
          System.out.println("Oops! No such record Found :(");
      
  }
  
  public void complexQuery(int a1, int a2, int s1, int s2)
  {
      int fg1=0,fg2=0;
      int[] temp1 = new int[nor];
      int[] temp2 = new int[nor];
      for(int i=0;i<count1;i++)
      {
          if(age[i][0]>=a1 && age[i][0]<=a2)
          {
              for(int j=0;j<nor;j++)
              {
                  temp1[j] = (temp1[j])|(age[i][j+1]);
                  fg1=1;
              }
          }
      }
      for(int i=0;i<count1;i++)
      {
          if(salary[i][1]>=s1 && salary[i][1]<=s2)
          {
              for(int j=0;j<nor;j++)
              {
                  temp2[j] = (temp2[j])|(salary[i][j+1]);
                  fg2=1;
              }
          }
      }
      if(fg1==1 && fg2==1)
      {
        for(int i=0;i<nor;i++)
        {
          temp1[i] = (temp1[i]) & (temp2[i]);
        }
        for(int j=0;j<nor;j++)
        {
          if(temp1[j]==1)
          {
              System.out.println("Record: "+a[j][0]+" "+a[j][1]+" "+a[j][2]+"\n");
          }
        }
      }
      else
         System.out.println("Oops! No such record Found :("); 
  }

  public static void main(String args[])
  {
    BitMapHash b1 = new BitMapHash();
    
    b1.CreateBitMap();
    Scanner sc = new Scanner(System.in); 
    int flag=1;
    while(flag==1)
    {
        System.out.println("1. Simple Query\n2. Range Query\nEnter your choice");
        int c = sc.nextInt();
        int sal=0,ag=0,ag2=0,sal2=0;
        if(c==1)
        {
            System.out.println("Enter age: ");
            ag = sc.nextInt();
            System.out.println("Enter salary: ");
            sal = sc.nextInt();
            b1.simpleQuery(ag,sal);
        }
        else if(c==2)
        {
            System.out.println("Enter lower age: ");
            ag = sc.nextInt();
            System.out.println("Enter upper age: ");
            ag2 = sc.nextInt();
            System.out.println("Enter lower salary: ");
            sal = sc.nextInt();
            System.out.println("Enter upper salary: ");
            sal2 = sc.nextInt();
            b1.complexQuery(ag, ag2, sal, sal2);
            //System.out.println("Under Construction!!");
        }
        System.out.println("Do you want to fetch more records??y=1/n=0");
        flag = sc.nextInt();
    }
  }
}