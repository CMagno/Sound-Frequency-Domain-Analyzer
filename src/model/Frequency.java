/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author carlosmagno
 */
public class Frequency implements Comparable{
    private double ampl;
    private double freq;

    public Frequency(double ampl, double freq) {
        this.ampl = ampl;
        this.freq = freq;
    }

    public double getAmpl() {
        return ampl;
    }

    public double getFreq() {
        return freq;
    }
    
    public static Frequency[] gerenateFrequencies(double[] ampls, double[] freqs){
        Frequency[] frequencies = new Frequency[ampls.length];
        
        for(int i = 0; i < frequencies.length; ++i){
            frequencies[i] = new Frequency(ampls[i], freqs[i]);
        }
        
        return frequencies;
    }

    @Override
    public int compareTo(Object o) {
        double diff = this.ampl - ((Frequency)o).ampl;
        if(diff < 0)
            return -1;
        if(diff > 0)
            return 1;
        
        return 0;
    }
}
