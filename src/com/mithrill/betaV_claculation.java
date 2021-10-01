package com.mithrill;

import java.util.Arrays;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class betaV_claculation {

    private final double [] factor1;
    private final double [] factor2;
    private final double [] yield;

    public betaV_claculation(double[] factor1, double[] factor2, double[] yield) {
        this.factor1 = factor1;
        this.factor2 = factor2;
        this.yield = yield;
    }

    public void func (){
        System.out.println(Arrays.deepToString(this.matrix_X()));
        System.out.println(Arrays.deepToString(this.matrix_XT()));
        System.out.println(Arrays.deepToString(this.matrix_XTmultX()));
        System.out.println(Arrays.deepToString(this.inverse_XTX()));
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

    private double [][] matrix_XTmultX(){
        double [][] matrix_X = this.matrix_X();
        double [][] matrix_XT = this.matrix_XT();
        double [][] mat_XTmultX = new double[matrix_XT.length][matrix_X[0].length];

        for (int i = 0; i < matrix_XT.length; i++) {
            for (int j = 0; j < matrix_X[0].length; j++) {
                double x = 0;
                for (int k = 0; k < matrix_XT[0].length; k++) {
                    x += matrix_XT[i][k] * matrix_X[k][j];
                }
                mat_XTmultX[i][j] = x;

                if (j == matrix_X[0].length - 1){
                    System.out.print(x + "\n");
                }else{
                    System.out.print(x + "\t");
                }
            }
        }
        return mat_XTmultX;
    }

    private double[][] inverse_XTX(){
        RealMatrix inv_XTX = MatrixUtils.inverse(MatrixUtils.createRealMatrix(this.matrix_XTmultX()));
        return inv_XTX.getData();
    }
}

