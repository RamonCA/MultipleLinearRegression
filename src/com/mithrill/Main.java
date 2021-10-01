package com.mithrill;

public class Main {

    public static void main(String[] args) {
		double [] factor_1 = {41.90,43.40,43.90,44.50,47.30,47.50,47.90,50.20,52.80,53.20,56.70,57.00,63.50,65.30,71.10,77.00,77.80};
		double [] factor_2 = {29.10,29.30,29.50,29.70,29.90,30.30,30.50,30.70,30.80,30.90,31.50,31.70,31.90,32.00,32.10,32.50,32.90};
		double [] yield = {15,15,15,15};

		betaV_claculation test = new betaV_claculation(factor_1, factor_2, yield);

		test.func();
    }
}
