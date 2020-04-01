/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneticAlgorithm;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author Nabeel
 */
public class Individuals {

    public class BinIndividuals {

        private int[][] chromosome;
        private int fitness;

        public BinIndividuals(int size, int length) {
            chromosome = new int[size][length];
        }

        public BinIndividuals(BinIndividuals copy) {
            int[][] tempGenes = copy.getChromosome();
            chromosome = new int[tempGenes.length][tempGenes[0].length];
            for (int i = 0; i < tempGenes.length; i++) {
                for (int j = 0; j < tempGenes[i].length; j++) {
                    chromosome[i][j] = tempGenes[i][j];
                }
            }

            fitness = copy.getFitness();
        }

        public void fitnessFunction(int[][] dataset) {
            fitness = 0;
            boolean conditionsCorrect = true;
            for (int[] dataset1 : dataset) {
                for (int[] chromosome1 : chromosome) {
                    conditionsCorrect = true;
                    //check all the conditions
                    for (int k = 0; k < chromosome1.length - 1; k++) {
                        if (chromosome1[k] == dataset1[k] || chromosome1[k] == 2) {
                            conditionsCorrect = true;
                        } else {
                            conditionsCorrect = false;
                            break;
                        }
                    }
                    if (conditionsCorrect) {
                        if (chromosome1[chromosome1.length - 1] == dataset1[dataset1.length - 1]) {
                            fitness++;
                        }
                        break;
                    }
                }
            }

        }

        public void createChromosome() {
            for (int[] chromosome1 : chromosome) {
                for (int j = 0; j < chromosome1.length - 1; j++) {
                    chromosome1[j] = new Random().nextInt(3); //2 is wild card
                }
                chromosome1[chromosome1.length - 1] = new Random().nextInt(2);
            }
        }

        public void mutation(double mutationRate) {
            for (int[] chromosome1 : chromosome) {
                for (int j = 0; j < chromosome1.length - 1; j++) {
                    if (mutationRate > Math.random()) {
                        chromosome1[j] = new Random().nextInt(3);
                    }
                }
                if (mutationRate > Math.random()) {
                    chromosome1[chromosome1.length - 1] = new Random().nextInt(2);
                }
            }
        }
        
        public int getFitness() {

            return fitness;
        }

        public int[][] getChromosome() {
            return chromosome;
        }
    }

    public class FPIndividuals {
        private double[][] chromosome;
        private int fitness;
        
        public FPIndividuals(int size, int length) {
            chromosome = new double[size][length];
        }
        
        public FPIndividuals(FPIndividuals nextGen) {
        double temp[][] = nextGen.getChromosome();
            chromosome = new double [temp.length][temp[0].length];
            for(int i = 0; i < temp.length; i++) {
                System.arraycopy(temp[i], 0, chromosome[i], 0, temp[i].length);
            }
            fitness = nextGen.getFitness();
        }
        
        public void fitnessFunction(double[][] dataset) {
            fitness = 0;
            boolean match = false;
            for (double[] dataset1 : dataset) 
            {
                for (double[] rule : chromosome) 
                {
                    for (int k = 0; k < dataset1.length - 1; k++) 
                    {
                        if (((dataset1[k] >= 0.5) && (rule[k] >= 0.5)) ||
                            ((dataset1[k] <= 0.5) && (rule[k] <= 0.5)) ||
                             (dataset1[k] ==  rule[k])         ||
                             (rule[k]     == 0.5)) {

                            match = true;
                        } 
                        else 
                        {
                            match = false;
                            break;
                        }
                    }
                    if (match == true) 
                    {
                        if (dataset1[dataset1.length - 1] == rule[rule.length - 1]) 
                        {
                            fitness++;
                        }
                        break;
                    }
                }
            }
        }
        
        public void createChromosome() {
            DecimalFormat df = new DecimalFormat("#.##");
            for (double[] rule : chromosome) {
                for (int j = 0; j < rule.length - 1; j++) {
                    rule[j] = (new Random().nextInt(3) == 2) ? 0.50 : Double.parseDouble(df.format(Math.random())); // 0.05 is the wildcard
                }
                rule[rule.length - 1] = new Random().nextInt(2); 
            }
        }
        
        public void mutation(double rate) {
            for (double[] rule : chromosome) {
                for (int j = 0; j < rule.length - 1; j++) {
                    if (rate <= Math.random()) {
                        rule[j] = (new Random().nextInt(3) == 2) ? 0.50 : Double.parseDouble(new DecimalFormat("#.##").format(Math.random())); // 0.05 is the wildcard
                    }
                }
                if (rate <= Math.random()) {
                    rule[rule.length - 1] = new Random().nextInt(2);
                }
            }
        }
        
        public int getFitness() {
            return fitness;
        }

        public double[][] getChromosome() {
            return chromosome;
        }
    }

}
