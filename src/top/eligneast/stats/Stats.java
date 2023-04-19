package top.eligneast.stats;

import java.util.ArrayList;
import java.util.List;

public class Stats {
    private final List<Double> data;

    private int size;

    private double sum;
    private double mean;
    private double pVariance; // Population variance
    private double sVariance; // Sample variance
    private double sStdDev; // Sample standard deviation
    private double pStdDev; // Population standard deviation
    private double sStdErr;
    private double pStdErr;
    private double absDev;
    private double absErr;


    public Stats(ArrayList<Double> data) {
        this.data = data;
        this.size = data.size();
        this.calculate();
    }

    public void add(double datum) {
        this.data.add(datum);
        this.size = this.data.size();
        this.calculate();
    }

    private void calculate() {
        double sum = 0.0;
        for (Double datum : this.data) {
            sum += datum;
        }
        this.sum = sum;

        this.mean = this.sum / this.size;

        double squaredTotalDev = 0.0;
        double absTotalDev = 0.0;
        for (Double datum : this.data) {
            squaredTotalDev += Math.pow(datum - this.mean, 2);
            absTotalDev += Math.abs(datum - this.mean);
        }
        this.pVariance = squaredTotalDev / this.size;
        this.sVariance = squaredTotalDev / (this.size - 1);

        this.pStdDev = Math.sqrt(this.pVariance);
        this.sStdDev = Math.sqrt(this.sVariance);

        this.pStdErr = this.pStdDev / Math.sqrt(this.size);
        this.sStdErr = this.sStdDev / Math.sqrt(this.size);

        this.absDev = absTotalDev / this.size;
        this.absErr = this.absDev / Math.sqrt(this.size);
    }

    public double getSum() {
        return this.sum;
    }

    public double getMean() {
        return this.mean;
    }

    public double getpVariance() {
        return this.pVariance;
    }

    public double getsVariance() {
        return this.sVariance;
    }

    public double getsStdDev() {
        return this.sStdDev;
    }

    public double getpStdDev() {
        return this.pStdDev;
    }

    public double getsStdErr() {
        return this.sStdErr;
    }

    public double getpStdErr() {
        return this.pStdErr;
    }

    public double getAbsDev() {
        return this.absDev;
    }

    public double getAbsErr() {
        return this.absErr;
    }

    public void print() {
        System.out.printf("Sum: %.5f%n", this.sum);
        System.out.printf("Average: %.5f%n", this.mean);
        System.out.printf("Population variance: %.5f%n", this.pVariance);
        System.out.printf("Sample variance: %.5f%n", this.sVariance);
        System.out.printf("Population standard deviation: %.5f%n", this.pStdDev);
        System.out.printf("Sample standard deviation: %.5f%n", this.sStdDev);
        System.out.printf("Population error: %.5f%n", this.pStdErr);
        System.out.printf("Sample error: %.5f%n", this.sStdErr);
        System.out.printf("Absolute deviation: %.5f%n", this.absDev);
        System.out.printf("Absolute error: %.5f%n", this.absErr);
    }

    public Double[] statsArray() {
        return new Double[]{this.sum, this.mean, this.pVariance, this.sVariance, this.pStdDev,
                            this.sStdDev, this.pStdErr, this.sStdErr, this.absDev, this.absErr};
    }
}
