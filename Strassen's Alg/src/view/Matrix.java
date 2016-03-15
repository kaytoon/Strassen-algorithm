package view;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Matrix {
	public static int coun = 0;
	static int counter  = 0;
	public static void main(String[] args) {
		Scanner scan =  new Scanner(System.in);
		System.out.println("Please enter the size of your matrices and make sure that it is in power of 2's.");
		System.out.println("both matrix will be using the same size to make it easy for you");
		System.out.println("for example, if you in put '4' you will get 2 4x4 matrices");
		System.out.println("and everything will be printed on the console.");
		int m = scan.nextInt();
		int[][] meticesOne= new int[m][m];
		int[][] meticesTwo = new int[m][m];
		Random ran = new Random();
		Random myRand = new Random();
		for(int i=0; i <m;i++){
			for(int j = 0; j < m;j++){

				//int myRand = ran.nextInt(a);
				//int myRand2 = 
				meticesOne[i][j]=ran.nextInt(10);

				meticesTwo[i][j]=myRand.nextInt(10);
			}
		}
		matrices(meticesOne,meticesTwo);
		//stressings(meticesOne,meticesTwo);
		int[][] newArray = stressings(meticesOne,meticesTwo);
		System.out.println();
		
		for(int i=0; i <newArray.length;i++){
			for(int j = 0; j < newArray.length ;j++){
				System.out.print(newArray[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println("The naive multplication took "+counter+" multplications to complete");
		System.out.println("The stressings multplication toook "+coun+" multplications to complete");

	}

	private static int[][] stressings(int[][] meticesOne, int[][] meticesTwo) {
		
		int n = meticesOne.length;
		
		/**Empty matrix that will hold the result.**/
        int[][] finalMatric = new int[n][n];
        /** This is the base case **/
        if (n == 1){
        	coun++;
            finalMatric[0][0] = meticesOne[0][0] * meticesTwo[0][0];   
        }
        else
        {	  
        	/**Empty matrices that will hold sub sections of each matrix.**/
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];
            /** Dividing matrix A into 4 halves.**/
            divid(meticesOne, A11, 0 , 0);
            divid(meticesOne, A12, 0 , n/2);
            divid(meticesOne, A21, n/2, 0);
            divid(meticesOne, A22, n/2, n/2);
            
            /** Dividing matrix B into 4 halves **/
            divid(meticesTwo, B11, 0 , 0);
            divid(meticesTwo, B12, 0 , n/2);
            divid(meticesTwo, B21, n/2, 0);
            divid(meticesTwo, B22, n/2, n/2);
            
            /** This is where i create the 7 M following stressing multiplication algorithm.**/
            int [][] m1 = stressings(add(A11, A22), add(B11, B22));
            int [][] m2 = stressings(add(A21, A22), B11);
            int [][] m3 = stressings(A11, subtrack(B12, B22));
            int [][] m4 = stressings(A22, subtrack(B21, B11));
            int [][] m5 = stressings(add(A11, A12), B22);
            int [][] m6 = stressings(subtrack(A21, A11), add(B11, B12));
            int [][] m7 = stressings(subtrack(A12, A22), add(B21, B22));
            /**The 4 section C's that will make up the final matrix. **/
            int [][] C11 = add(subtrack(add(m1, m4), m5), m7);
            int [][] C12 = add(m3, m5);
            int [][] C21 = add(m2, m4);
            int [][] C22 = add(subtrack(add(m1, m3), m2), m6);
            /** combine the C's to create the final matrix.**/
            createFinalMatrices(C11, finalMatric, 0 , 0);
            createFinalMatrices(C12, finalMatric, 0 , n/2);
            createFinalMatrices(C21, finalMatric, n/2, 0);
            createFinalMatrices(C22, finalMatric, n/2, n/2);
            
        }
		return finalMatric;
	}
	/**This method combines  C's to create the final matrix**/
	 public static void createFinalMatrices(int[][] C, int[][] P, int iB, int jB) 
	    {
	        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
	            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
	                P[i2][j2] = C[i1][j1];
	    }
	 /**Takes two matrices and subtracts them
	  * @return newMat- a new matrix.**/
	private static int[][] subtrack(int[][] a21, int[][] a11) {
		int size = a11.length;
		int[][] newMat= new int[size][size];
		for(int i = 0; i<size;i++){
			for(int j = 0; j< size;j++){
				newMat[i][j] = a21[i][j]-a11[i][j];
			}	
		}
		return newMat;
	}
	/** Dividing matrix into 4 halves **/
	public static void divid(int[][] P, int[][] C, int iB, int jB) 
    {
        for(int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++)
            for(int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++)
                C[i1][j1] = P[i2][j2];
    }
	/**This method adds two matrices**/
	private static int[][] add(int[][] a11, int[][] a22) {
		int size = a11.length;
		int[][] newMat= new int[size][size];
		for(int i = 0; i<size;i++){
			for(int j = 0; j< size;j++){
				newMat[i][j] = a11[i][j]+a22[i][j];
			}
		}
		return newMat;
	}
	/** This methods does the naive multiplication**/
	private static void matrices(int[][] meticesOne, int[][] meticesTwo) {
		int size = meticesOne.length;
		int[][] newMatrices = new int[size][size];
		int answer =0;
		for(int i=0; i <size;i++){
			for(int j = 0; j < size;j++){
				for(int k = 0; k < size;k++){
					answer =answer+ meticesOne[i][k]*meticesTwo[k][j];
					counter++;	
				}
				newMatrices[i][j] = answer;
				answer = 0;
			}
		}
		
		System.out.println();
		System.out.println("This is the input of the first Matrices");
		for(int i=0; i <size;i++){
			for(int j = 0; j < size ;j++){
				System.out.print(meticesOne[i][j]+"   ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("This is the input of the second Matrices");
		for(int i=0; i <size;i++){
			for(int j = 0; j < size ;j++){
				System.out.print(meticesTwo[i][j]+"   ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		for(int i=0; i <size;i++){
			for(int j = 0; j < size ;j++){
				System.out.print(newMatrices[i][j]+" ");
			}
			System.out.println();
		}
	}
}




