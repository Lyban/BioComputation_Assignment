/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneticAlgorithm;

import GeneticAlgorithm.Individuals.BinIndividuals;
import GeneticAlgorithm.Individuals.FPIndividuals;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Nabeel
 */
public class Population {

    public class BinPopulation {

        int dataset[][];
        BinIndividuals[] population;
        BinIndividuals[] mateingPool;
        double mutationRate;
        int PopulationSize;
        int chromosomeLength;
        int chromosomeSize;

        int bestFitness;
        double averageFitness;
        int worstFitness;
        
        public int getBestFitness() {
            return bestFitness;
        }

        public double getAverageFitness() {
            return averageFitness;
        }

        public int getWorstFitness() {
            return worstFitness;
        }
        
        public BinPopulation(double mutation, int PopSize, int choromLen, int choromSize) {
            this.mutationRate = mutation;
            this.PopulationSize = PopSize;
            this.chromosomeLength = choromLen;
            this.chromosomeSize = choromSize;

            population = new BinIndividuals[PopulationSize];
            mateingPool = new BinIndividuals[PopulationSize];

            for (int i = 0; i < population.length; i++) {
                population[i] = new Individuals().new BinIndividuals(chromosomeSize, chromosomeLength);
                population[i].createChromosome();
            }
        }

        public void getDatasetData(String file) {
            ArrayList<Integer[]> tempList = new ArrayList();
            Integer[] line;
            Scanner scan = new Scanner(Population.class.getResourceAsStream(file));
            while (scan.hasNextLine()) {
                String data = scan.nextLine();
                data = data.replace(" ", "");
                line = new Integer[data.length()];
                for (int i = 0; i < data.length(); i++) {
                    line[i] = Integer.parseInt(data.charAt(i) + "");
                }
                tempList.add(line);
            }
            dataset = new int[tempList.size()][tempList.get(0).length];
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < tempList.get(i).length; j++) {
                    dataset[i][j] = tempList.get(i)[j];
                }
            }

        }
        
        public BinIndividuals tournmentSelection() {
            BinIndividuals parent1 = new Individuals().new BinIndividuals(population[new Random().nextInt(PopulationSize)]);
            BinIndividuals parent2 = new Individuals().new BinIndividuals(population[new Random().nextInt(PopulationSize)]);

            if (parent1.getFitness() > parent2.getFitness()) {
                return parent1;
            } else {
                return parent2;
            }
        }
        
        public BinIndividuals[] singlePointCrossover(BinIndividuals parent1, BinIndividuals parent2) {
            BinIndividuals[] chidren = new BinIndividuals[2];
            chidren[0] = new Individuals().new BinIndividuals(parent1);
            chidren[1] = new Individuals().new BinIndividuals(parent2);

            int crossoverPoint = new Random().nextInt(chromosomeLength * chromosomeSize);
            int pointer = 0;
            for (int i = 0; i < parent1.getChromosome().length; i++) {
                for (int j = 0; j < parent1.getChromosome()[i].length; j++) {
                    if (pointer < crossoverPoint) {
                        int temp = chidren[0].getChromosome()[i][j];
                        chidren[0].getChromosome()[i][j] = chidren[1].getChromosome()[i][j];
                        chidren[1].getChromosome()[i][j] = temp;
                    } else {
                        break;
                    }
                    pointer++;
                }
                if (pointer >= crossoverPoint) {
                    break;
                }
            }
            return chidren;
        }
        
        public void newGeneration() {
            for (int i = 0; i < population.length; i++) {
                population[i] = new Individuals().new BinIndividuals(mateingPool[i]);
            }
        }
        
        public void mutation() {
            for (BinIndividuals mateingPool1 : mateingPool) {
                mateingPool1.mutation(mutationRate);
            }
        }
        
        public void crossover() {
            for (int i = 0; i < mateingPool.length / 2; i++) {
                int offset = i * 2;
                BinIndividuals[] children = singlePointCrossover(mateingPool[offset], mateingPool[offset + 1]);
                mateingPool[i] = children[0];
                mateingPool[i + 1] = children[1];
            }
        }
        
        public void selection() {
            for (int i = 0; i < mateingPool.length; i++) {
                mateingPool[i] = tournmentSelection();
            }
        }
        
        public void fitnessFunction() {
            bestFitness = 0;
            worstFitness = population[0].getFitness();
            averageFitness = 0;

            for (BinIndividuals population1 : population) {
                population1.fitnessFunction(dataset);
                if (bestFitness < population1.getFitness()) {
                    bestFitness = population1.getFitness();
                }
                if (worstFitness > population1.getFitness()) {
                    worstFitness = population1.getFitness();
                }
                averageFitness += population1.getFitness();
            }
            averageFitness /= population.length;
        }
    }

    public class FPPopulation {
        double dataset[][];
        private double [][] normalizedTrainSet;
        private double [][] normalizedTesting;
        FPIndividuals[] population;
        FPIndividuals[] mateingPool;
        double mutationRate;
        int PopulationSize;
        int chromosomeLength;
        int chromosomeSize;

        int bestFitness;
        double averageFitness;
        int worstFitness;
        private int totalFitness;
        
        public int getBestFitness() {
            return bestFitness;
        }

        public double getAverageFitness() {
            return averageFitness;
        }

        public int getWorstFitness() {
            return worstFitness;
        }
        
        public FPPopulation(double mutation, int PopSize, int choromLen, int choromSize) {
            
            this.mutationRate = mutation;
            this.PopulationSize = PopSize;
            this.chromosomeLength = choromLen;
            this.chromosomeSize = choromSize;
            
            population = new FPIndividuals[PopulationSize];
            mateingPool = new FPIndividuals[PopulationSize];

            for (int i = 0; i < population.length; i++) {
                population[i] = new Individuals().new FPIndividuals(chromosomeSize, chromosomeLength);
                population[i].createChromosome();
            }

        }
        
        public void getDatasetData(String file) {
            Scanner scn = new Scanner(Population.class.getResourceAsStream(file));
            double[][] dataSet;
            ArrayList<Double[]> tempList = new ArrayList();
            Double[] arr;

            while (scn.hasNextLine()) {
                String data = scn.nextLine();
                String[] split = data.split(" ");
                arr = new Double[split.length];

                for (int i = 0; i < split.length; i++) {
                    arr[i] = Double.parseDouble(split[i]);
                }
                tempList.add(arr);
            }
            dataSet = new double[tempList.size()][tempList.get(0).length];

            for (int i = 0; i < dataSet.length; i++) {
                for (int j = 0; j < dataSet[i].length; j++) {
                    dataSet[i][j] = tempList.get(i)[j];
                }
            }
            //copy trinmed verson to normalised dataset
            double[][] normalizedDataset;
            DecimalFormat df = new DecimalFormat("#.00");
            normalizedDataset = new double[dataSet.length][dataSet[0].length];

            for (int i = 0; i < dataSet.length; i++) {
                for (int j = 0; j < dataSet[i].length; j++) {
                    normalizedDataset[i][j] = Double.parseDouble(df.format(dataSet[i][j]));
                }
            }
            //split into traing and testing sets
            int var = 0;
            normalizedTrainSet = new double[(int) (dataSet.length * 0.50)][dataSet[0].length];
            normalizedTesting = new double[(int) (dataSet.length * 0.50)][dataSet[0].length];
            for (int i = 0; i < normalizedDataset.length; i++) {
                if (i <= normalizedTrainSet.length - 1) {
                    normalizedTrainSet[i] = normalizedDataset[i];
                } else {
                    normalizedTesting[var] = normalizedDataset[i];
                    var++;
                }
            }
        }
        
        private FPIndividuals rouletteWheelSelection() {
            int randomNumber = new Random().nextInt(totalFitness + 1);
            int sumOfFitness = 0;
            int savedIndex;
            for (savedIndex = 0; savedIndex < population.length; savedIndex++) {
                if (sumOfFitness > randomNumber) {
                    break;
                }
                sumOfFitness += population[savedIndex].getFitness();
            }
            return population[savedIndex - 1];
        }
        
        private FPIndividuals[] singlePointCrossover(FPIndividuals parent, FPIndividuals parent2) {
            FPIndividuals[] children = new FPIndividuals[2];
            children[0] =  new Individuals().new FPIndividuals(parent);
            children[1] =  new Individuals().new FPIndividuals(parent2);
            Random rand = new Random();
            int crossoverPoint = rand.nextInt(chromosomeLength*chromosomeSize);
            int pointer  =0;
            for(int i = 0; i < parent.getChromosome().length; i++) {
                for(int j = 0; j < parent.getChromosome()[i].length; j++) {
                    if(pointer < crossoverPoint) {
                        double temp = children[0].getChromosome()[i][j];
                        children[0].getChromosome()[i][j] = children[1].getChromosome()[i][j];
                        children[1].getChromosome()[i][j] = temp;
                    }
                    else break;
                    pointer++;
                }
                if(pointer >= crossoverPoint)
                    break;
            }
            return children;
        }
        
        public void newGeneration() {
            for(int i = 0; i < population.length; i++) {
                population[i] = new Individuals().new FPIndividuals(mateingPool[i]);
            }
        }
        
        public void mutation() {
            for (FPIndividuals matingpool1 : mateingPool) {
                matingpool1.mutation(mutationRate);
            }
        }
        
        public void crossover() {
            for (int i = 0; i < mateingPool.length / 2; i++) {
                int offset = i * 2;
                FPIndividuals[] children = singlePointCrossover(mateingPool[offset], mateingPool[offset + 1]);
                mateingPool[i] = children[0];
                mateingPool[i + 1] = children[1];
            }
        }
        
        public void selection() {
            for (int i = 0; i < mateingPool.length; i++) {
                mateingPool[i] = rouletteWheelSelection();
            }
        }
        
        public void fitnessFunction() {
            totalFitness = 0;
            bestFitness = 0;
            worstFitness = population[0].getFitness();
            averageFitness = 0;
            for (FPIndividuals population1 : population) {
                population1.fitnessFunction(normalizedTrainSet);
                if (bestFitness < population1.getFitness()) {
                    bestFitness = population1.getFitness();
                }
                if (worstFitness > population1.getFitness()) {
                    worstFitness = population1.getFitness();
                }
                averageFitness += population1.getFitness();
                totalFitness += population1.getFitness();
            }
            averageFitness = averageFitness / population.length;
        }
    }

}
