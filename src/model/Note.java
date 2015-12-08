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
public class Note {
    
    private String label;
    private double freq;
    
    public Note(String label, double freq){
        this.label = label;
        this.freq = freq;
    }

    public String getLabel() {
        return label;
    }

    public double getFreq() {
        return freq;
    }
}
