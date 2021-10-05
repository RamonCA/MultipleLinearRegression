package com.mithrill;

public class gradient_calculation {

    private final double [] factor1;
    private final double [] yield;
    private final double alpha;

    public gradient_calculation(double[] factor1, double[] yield, double alpha) {
        this.factor1 = factor1;
        this.yield = yield;
        this.alpha = alpha;
    }

    public void func(){

        double beta0 = 0;
        double beta1 = 0;

        double error = 0;

        for (int i = 0; i < 150000; i++) {
            error = this.error(beta0,beta1);
            beta0 = this.beta0(beta0, beta1);
            beta1 = this.beta1(beta0, beta1);
        }
        System.out.println(error + " " +beta0 + " " +beta1);
    }

    private double error (double b0, double b1){

        double aux = 0;

        for (int i = 0; i < this.factor1.length; i++) {
            aux += ((this.yield[i]-(b0 + b1*this.factor1[i])));
        }
        return (1.0/9) * aux * aux;
    }

    private double beta0 (double cB0, double cB1){
        return cB0 - (this.gB0(cB0, cB1) * this.alpha);
    }

    private double beta1 (double cB0, double cB1){
        return cB1 - (this.gB1(cB0, cB1) * this.alpha);
    }

    private double gB0(double beta0, double beta1){
        double aux = 0;
        for (int i = 0; i < this.factor1.length; i++) {
            aux += (this.yield[i]-(beta0 + beta1 * this.factor1[i]));
        }

         return (-2.0/this.factor1.length) * aux;
    }

    private double gB1(double beta0, double beta1){
        double aux = 0;
        for (int i = 0; i < this.factor1.length; i++) {
            aux += this.factor1[i]*(this.yield[i]-(beta0 + beta1 * this.factor1[i]));
        }
        return (-2.0/this.factor1.length) * aux;
    }
}
