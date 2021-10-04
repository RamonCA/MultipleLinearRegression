package com.mithrill;

import java.util.Arrays;
import org.apache.commons.math3.linear.MatrixUtils;

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
        //System.out.println(Arrays.deepToString(beta()));
        System.out.println(Arrays.deepToString(inverse_XTX()));
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

    private double[][] inverse_XTX(){

        double [][]mat_XTX = this.mat_Multiplication(matrix_X(),matrix_XT());
        double [][] mat_Ident = new double[mat_XTX.length][mat_XTX[0].length];
        double[][] inv_XTX = new double[mat_XTX.length][2*mat_XTX[0].length];

        for (int i = 0; i < mat_Ident.length; i++) {
            for (int j = 0; j < mat_Ident[0].length; j++) {
                if (i == j) mat_Ident[i][j] = 1;
                else mat_Ident[i][j] = 0;
            }
        }

        for (int i = 0; i < inv_XTX.length; i++) {
            for (int j = 0; j < inv_XTX[0].length; j++) {
                if (j < mat_Ident[0].length) inv_XTX[i][j] = mat_XTX[i][j];
                else inv_XTX[i][j] = mat_Ident[i][j - mat_Ident.length];
            }
        }

        double [] t1 = {2,1,3,1,0,0};
        double [] t2 = {0,0,-4,1,2,-5};
        System.out.println(Arrays.toString(row_Matrix(t1, t2, 4, 3)));
        return inv_XTX;
        //return MatrixUtils.inverse(MatrixUtils.createRealMatrix(this.mat_Multiplication(matrix_X(),matrix_XT()))).getData();
    }

    private double [] row_Matrix(double [] f1, double[] f2, double xf1, double xf2){
        double [] ans = new double[f1.length];
        double [] ff1 = row_mult(f1,xf1);
        double [] ff2 = row_mult(f2,xf2);
        for (int i = 0; i < f1.length; i++) ans[i] = ff1[i] + ff2[i];

        return ans;
    }

    private double [] row_mult(double [] f1, double factor){
        for (int i = 0; i < f1.length; i++) {
            f1[i] = factor * f1[i];
        }
        return f1;
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

