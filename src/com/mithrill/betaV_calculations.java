package com.mithrill;

import java.util.Arrays;

public class betaV_calculations {

    private final double [] factor1;
    private final double [] factor2;
    private final double [] yield;

    public betaV_calculations(double[] factor1, double[] factor2, double[] yield) {
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.yield = yield;
    }

    public void func (){

        System.out.println(Arrays.deepToString(inverse_XTX()));
        System.out.println(Arrays.deepToString(beta()));
    }

    private double[][] matrix_X(){
        int size = Math.min(this.factor1.length, this.factor2.length);
        double[][] mat_X = new double[size][3];
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < 3; j++){
                if (j == 0) mat_X[i][j] = 1;
                else if (j == 1) mat_X[i][j] = this.factor1[i];
                else mat_X[i][j] = this.factor2[i];

            }
        }
        return mat_X;
    }

    private double [][] matrix_XT (){
        double [][] matrix_X = this.matrix_X();
        double [][] mat_XT = new double[matrix_X[0].length][matrix_X.length];

        for (int i = 0; i < matrix_X.length; i++) {
            for (int j = 0; j < matrix_X[0].length; j++){
                mat_XT[j][i] = matrix_X[i][j];

            }
        }
        return mat_XT;
    }

    private double [][] mat_Multiplication(double [][] mat_A, double[][] mat_B){
        double [][] mat_XTmultX = new double[mat_B.length][mat_A[0].length];

        for (int i = 0; i < mat_B.length; i++) {
            for (int j = 0; j < mat_A[0].length; j++) {
                double x = 0;
                for (int k = 0; k < mat_B[0].length; k++) {
                    x += mat_B[i][k] * mat_A[k][j];
                }
                mat_XTmultX[i][j] = x;
            }
        }
        return mat_XTmultX;
    }

    private double[][] mat_ident_creation(int mat_size){

        double[][] mat_ident = new double[mat_size][mat_size];
        for (int i = 0; i < mat_size; i++) {
            for (int j = 0; j < mat_size; j++) {
                if (i == j) mat_ident[i][j] = 1;
                else mat_ident[i][j]=0;
            }
        }
        return mat_ident;
    }

    private double [][] inverse_XTX(){

        double [][] matrix_XT = this.mat_Multiplication(matrix_X(),matrix_XT());
        double[][] ans = mat_ident_creation(matrix_XT.length);
        double xF1;
        double aux;
        for (int i = 0; i <matrix_XT.length ; i++) {
            xF1 = matrix_XT[i][i];

            for (int j = 0; j < matrix_XT.length; j++) {
                matrix_XT[i][j] = matrix_XT[i][j] / xF1;
                ans[i][j] = ans[i][j] / xF1;
            }

            for (int j = 0; j < matrix_XT.length ; j++) {
                if (i != j){
                    aux = matrix_XT[j][i];
                    for (int k = 0; k < matrix_XT.length; k++) {
                        matrix_XT[j][k] = matrix_XT[j][k] -aux*matrix_XT[i][k];
                        ans[j][k] = ans[j][k] -aux*ans[i][k];
                    }
                }
            }
        }
        return ans;
    }

    private double [][] XT_mult_Y(){
        double [][] y = new double[this.yield.length][1];
        int j = 0;
        for (double val: yield) {
            y[j++][0] = val;
        }
        return mat_Multiplication(y, matrix_XT());
    }

    private double [][] beta(){
        return mat_Multiplication(XT_mult_Y(), inverse_XTX());
    }
}

