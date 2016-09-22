/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrixsolver;

import java.util.Scanner;

/**
 *
 * @author jihua5758
 */
public class MatrixSolver {
    public static double calculateDeterminant(double[][]a){
        double r=0;
        if(a.length==1){
            return a[0][0];
        }else{
            for(int i=0;i<a.length;i++){
                r+=Math.pow(-1,i)*calculateDeterminant(complementaryMinor(0,i,a))*a[0][i];
            }
        }
        return r;
    }
    public static double[][] complementaryMinor(int i,int j,double[][]a) {
        double[][] b=new double[a.length-1][a.length-1];
        int n1,m1;
        for (int m2=0;m2<a.length;m2++){
            for (int n2=0;n2<a.length;n2++){
                if(n2>i){
                    n1=n2-1;
                }else{
                    n1=n2;
                }
                if(m2>j){
                    m1=m2-1;
                }else{
                    m1=m2;
                }
                if((n2!=i)&&(m2!=j)){
                    b[n1][m1]=a[n2][m2];
                }
            }
        }
        return b;
    }
    public static double[][] rowOperations(int i,int j,double n,double[][]a){
        double[][]b=a;
        for (int m=0;m<a.length;m++){
            b[m][i]+=a[m][j]*n;
        }
        return b;
    }
    public static double[][] rowInterchange(int i,int j,double[][]a){
        double[][]b=a;
        for (int m=0;i<a.length;i++){
            b[i][m]=a[j][m];
            b[j][m]=a[i][m];
        }
        return b;
    }
    public static double[][] standardEchelon(double[][]a){
        double[][]b=a;
        int c=1;
        for(int i=0;i<Math.min(a[0].length,a.length);i++){
            while(b[i][i]==0){
                b=rowInterchange(i,i+c,b);
                c++;
                if(c>=a.length-i){
                    return null;
                }
            }
            b=rowOperations(i,i,1/a[i][i]-1,b);
            for(int j=0;j<a[0].length;j++){
                if(j!=i){
                    b=rowOperations(j,i,-1*b[i][j],b);
                }
            }
        }
        return b;
    }
    public static double[] guassJordanElimination(double[][]a){
        if(a==null){
            return null;
        }
        double[]r=new double[a.length-1];
        double[][]b=standardEchelon(a);
        for(int i=a.length-1;i<a[0].length;i++){
            if(a[a.length-1][i]!=0){
                return null;
            }
        }
        for(int i=0;i<a.length-1;i++){
            r[i]=a[a.length-1][i];
        }
        return r;
    }
    public static int rank(double[][]a){
        double[][] b=standardEchelon(a);
        int i=0;
        int j=0;
        int r=0;
        while(b[i][j]!=0){
            r++;
            if(i+1==b.length||j+1==b[0].length){
                break;
            }
            if(i+1<b.length){
                i++;
            }
            if(j+1<b[0].length){
                j++;
            }
        }
        return r;
    }
    public static String fraction(double n){
        double s=n*10000;
        n=(double)Math.round(s)/10000;
        boolean t;
        if(n-Math.floor(n)==0){
            return String.valueOf((int)n);
        }else{
            int p=2;
            while(((double)Math.round(p*n*1000)/1000-Math.floor(p*n))!=0){
                p++;
            }
            int q=(int)(p*n);
            return String.valueOf(q)+"/"+String.valueOf(p);
        }
    }
    public static void main(String[] args) {
        int l,h,c;
        Scanner sc=new Scanner(System.in);
        System.out.print("input length of matrix:");
        l=sc.nextInt();
        System.out.print("input height of matrix:");
        h=sc.nextInt();
        double[][]a=new double[l][h],in=new double[l*2][h];
        System.out.println("input matrix:");
        for (int i=0;i<h;i++){
            for (int j=0;j<l;j++){
                a[j][i]=sc.nextDouble();
            }
        }
        System.out.println("1.determinant\n2.solve\n3.rank\n4.standard echelon\n5.inverse");
        c=sc.nextInt();
        switch(c){
            case 1:
                if(l==h){
                    System.out.print("the determinant is:");
                    System.out.println(fraction(calculateDeterminant(a)));
                }else{
                    System.out.println("square matrix required");
                }
                break;
            case 2:
                if(l<=h+1){
                double[]r=guassJordanElimination(a);
                    if(r==null){
                        System.out.println("the system is inconsistent");
                    }else{
                        System.out.println("the solution to the system is:");
                        for(int i=0;i<r.length;i++){
                            System.out.println("x"+(i+1)+"="+fraction(r[i]));
                        }
                    }
                }else{
                    System.out.println("number of equations shoule be more than variables");
                }
                break;
            case 3:
                System.out.println("the rank for the matrix is:"+rank(a));
                break;
            case 4:
                standardEchelon(a);
                for (int i=0;i<h;i++){
                    for (int j=0;j<l;j++){
                        System.out.print(fraction(a[j][i])+" ");
                    }
                    System.out.println();
                }
                break;
            case 5:
                for (int i=0;i<h;i++){
                    for (int j=0;j<l;j++){
                        in[j][i]=a[j][i];
                    }
                }
                for (int i=0;i<h;i++){
                    in[l+i][i]=1;
                }
                standardEchelon(in);
                for (int i=0;i<h;i++){
                    for (int j=0;j<l;j++){
                        System.out.print(fraction(in[l+j][i])+" ");
                    }
                    System.out.println();
                }
            default:
                break;
        }
    }
}
