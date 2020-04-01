/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneticAlgorithm;

import GeneticAlgorithm.Population.BinPopulation;
import GeneticAlgorithm.Population.FPPopulation;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.text.NumberFormatter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Nabeel
 * BioComp Assignment
 */
public class Main {

    public static void main(String[] args) {
        ControlPannel cpannel = new Main().new ControlPannel();
        cpannel.dashboardGUI();
    }

    public class ControlPannel {

        JFrame frame = new JFrame("Control Pannel");
        JPanel optionsPanel = new JPanel();
        JPanel chartPanel = new JPanel();
        JRadioButton datasetRadioBtn1 = new JRadioButton("data1.txt");
        JRadioButton datasetRadioBtn2 = new JRadioButton("data2.txt");
        JRadioButton datasetRadioBtn3 = new JRadioButton("data3.txt");
        ButtonGroup datasetsBtn = new ButtonGroup();
        NumberFormatter nf = new NumberFormatter();
        JFormattedTextField mutationRateTF = new JFormattedTextField(nf);
        JFormattedTextField populationSizeTF = new JFormattedTextField(nf);
        JFormattedTextField chromosomeLengthTF = new JFormattedTextField(nf);
        JFormattedTextField chromosomeSizeTF = new JFormattedTextField(nf);
        JFormattedTextField generatuinSizeTF = new JFormattedTextField(nf);
        JButton runBtn = new JButton("Run");
        JProgressBar progress = new JProgressBar();
        XYSeries best = new XYSeries("Best Fitness");
        XYSeries worst = new XYSeries("Worst Fitness");
        XYSeries avrg = new XYSeries("Average Fitness");
        JFreeChart lineGraph;

        public void dashboardGUI() {
            frameSetup();
            radioBtnSetup();
            inputFeildSetup();
            progressBarSetup();
            runBtnSetup();
            chartSetup("Graph");
            functions();
            frame.pack();
        }
        
        private void functions() {
            if (datasetRadioBtn1.isSelected()) {
                frame.repaint();
                optionsPanel.repaint();

                chromosomeLengthTF.setEditable(false);
                chromosomeLengthTF.setValue(6);
                chromosomeSizeTF.setValue(16);
                mutationRateTF.setValue(0.02);
                populationSizeTF.setValue(1000);
                generatuinSizeTF.setValue(1000);
            }
            datasetRadioBtn1.addActionListener((ActionEvent e) -> {
                frame.repaint();
                optionsPanel.repaint();
                chromosomeLengthTF.setEditable(false);
                chromosomeLengthTF.setValue(6);
                chromosomeSizeTF.setValue(16);
                generatuinSizeTF.setValue(1000);
            });

            datasetRadioBtn2.addActionListener((ActionEvent e) -> {
                chromosomeLengthTF.setEditable(false);
                chromosomeLengthTF.setValue(7);
                chromosomeSizeTF.setValue(32);
                generatuinSizeTF.setValue(1000);
            });

            datasetRadioBtn3.addActionListener((ActionEvent e) -> {
                chromosomeLengthTF.setEditable(false);
                chromosomeLengthTF.setValue(7);
                chromosomeSizeTF.setValue(64);
                generatuinSizeTF.setValue(200);
            });
            
            runBtn.addActionListener((ActionEvent e) -> {
                 double mRate = Double.parseDouble(mutationRateTF.getValue()+"");
                 int pop = Integer.parseInt(populationSizeTF.getValue()+"");
                 int chroLen = Integer.parseInt(chromosomeLengthTF.getValue()+"");
                 int chroSize = Integer.parseInt(chromosomeSizeTF.getValue()+"");
                 String dataSet;
                 if(datasetRadioBtn3.isSelected()) {
                    dataSet = datasetRadioBtn3.getText();
                    lineGraph.setTitle("Pop size: " + populationSizeTF.getValue() + ", Mutation: " + mutationRateTF.getValue() + ", Chromosome Size: " + chromosomeSizeTF.getValue() + ", Total Generation: " + generatuinSizeTF.getValue() + ", " +dataSet);
                    progress.setMaximum(Integer.parseInt(generatuinSizeTF.getValue()+""));
                    algrithmFP(mRate,pop,chroLen,chroSize,dataSet );
                 }
                 else if(datasetRadioBtn2.isSelected()) {
                    dataSet = datasetRadioBtn2.getText();
                    lineGraph.setTitle("Pop size: " + populationSizeTF.getValue() + ", Mutation: " + mutationRateTF.getValue() + ", Chromosome Size: " + chromosomeSizeTF.getValue() + ", Total Generation: " + generatuinSizeTF.getValue() + ", " +dataSet);
                    progress.setMaximum(Integer.parseInt(generatuinSizeTF.getValue()+""));
                    algrithmBin(mRate,pop,chroLen,chroSize,dataSet );
                 }
                 else {
                    dataSet = datasetRadioBtn1.getText();
                    lineGraph.setTitle("Pop size: " + populationSizeTF.getValue() + ", Mutation: " + mutationRateTF.getValue() + ", Chromosome Size: " + chromosomeSizeTF.getValue() + ", Total Generation: " + generatuinSizeTF.getValue() + ", " +dataSet);
                    progress.setMaximum(Integer.parseInt(generatuinSizeTF.getValue()+""));
                    algrithmBin(mRate,pop,chroLen,chroSize,dataSet );
                 }
            });
        }
        
        private void frameSetup() {
            frame.setSize(800, 600);
            frame.setResizable(false);
            frame.setLayout(new BorderLayout());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            try { Thread.sleep(50); } catch (InterruptedException ex) {Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);}
            
            optionsPanel.setBackground(Color.WHITE);
            optionsPanel.setLayout(new GridBagLayout());
            frame.getContentPane().add(optionsPanel, BorderLayout.LINE_END);
            
            frame.add(chartPanel, BorderLayout.WEST);
            chartPanel.setLayout(new BorderLayout());
            
            
        }
        
        private void radioBtnSetup() {
            GridBagConstraints gbc = new GridBagConstraints();
            
            datasetsBtn.add(datasetRadioBtn1);
            datasetsBtn.add(datasetRadioBtn2);
            datasetsBtn.add(datasetRadioBtn3);

            datasetsBtn.setSelected(datasetRadioBtn1.getModel(), true);
            datasetRadioBtn1.setBackground(Color.WHITE);
            datasetRadioBtn2.setBackground(Color.WHITE);
            datasetRadioBtn3.setBackground(Color.WHITE);
            
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 15, 10);
            optionsPanel.add(datasetRadioBtn1, gbc);
            gbc.gridx = 1;
            gbc.gridy = 0;
            optionsPanel.add(datasetRadioBtn2, gbc);
            gbc.gridx = 2;
            gbc.gridy = 0;
            optionsPanel.add(datasetRadioBtn3, gbc);
        }
        
        private void inputFeildSetup() {
            GridBagConstraints gbc = new GridBagConstraints();
            // dashbord text feilds 
            JLabel mutationLabel = new JLabel("Mutation Rate: ");
            JLabel populationLabel = new JLabel("Population Size: ");
            JLabel chromosomeLenLabel = new JLabel("Chromosome Length: ");
            JLabel chromosomeSizeLabel = new JLabel("Chromosome Size: ");
            JLabel genLabel = new JLabel("Number of Generation: ");

            nf.setOverwriteMode(true);
            
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            
            gbc.gridx = 0;
            gbc.gridy = 2;
            optionsPanel.add(mutationLabel, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 4;
            optionsPanel.add(populationLabel,gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 6;
            optionsPanel.add(chromosomeLenLabel,gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 8;
            optionsPanel.add(chromosomeSizeLabel,gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 10;
            optionsPanel.add(genLabel,gbc);

            gbc.gridx = 2;
            gbc.gridy = 2;
            optionsPanel.add(mutationRateTF, gbc);
            
            gbc.gridx = 2;
            gbc.gridy = 4;
            optionsPanel.add(populationSizeTF,gbc);
            
            gbc.gridx = 2;
            gbc.gridy = 6;
            optionsPanel.add(chromosomeLengthTF, gbc);
            
            gbc.gridx = 2;
            gbc.gridy = 8;
            optionsPanel.add(chromosomeSizeTF, gbc);
            
            gbc.gridx = 2;
            gbc.gridy = 10;
            optionsPanel.add(generatuinSizeTF, gbc);
        }
        
        private void progressBarSetup() {
            GridBagConstraints gbc = new GridBagConstraints();
            JLabel progressLable = new JLabel("Genetic Algorithm Progress: ");
            
            gbc.insets = new Insets(10, 10, 25, 10);
            gbc.gridx = 0;
            gbc.gridy = 14;
            gbc.gridwidth = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            progress.setMinimum(0);
            
            optionsPanel.add(progress, gbc);
            
            gbc.gridx = 0;
            gbc.gridy = 12;
            optionsPanel.add(progressLable,gbc);
        }
        
        private void runBtnSetup() {
            GridBagConstraints gbc = new GridBagConstraints();
            
            gbc.insets = new Insets(10, 10, 5, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridy = 16;
            gbc.gridx = 2;
            optionsPanel.add(runBtn, gbc);
            optionsPanel.repaint();
        }
        
        private void chartSetup(String title){
            
            XYSeriesCollection dataCollection = new XYSeriesCollection();
            dataCollection.addSeries(best);
            dataCollection.addSeries(avrg);
            dataCollection.addSeries(worst);
            
            lineGraph = ChartFactory.createXYLineChart(title, "No. Of Generations", "Fitness Values", dataCollection);
            
            chartPanel.add(new ChartPanel (lineGraph), BorderLayout.CENTER);
            
        }
        
        public void drawLine(int currentgenerations, int bestfitness, double avgfitness, int worstfitness) {
            best.add(currentgenerations, bestfitness);
            avrg.add(currentgenerations, avgfitness);
            worst.add(currentgenerations, worstfitness);
        }
        
        public void algrithmBin(double mutation, int Population, int chromosomeLen, int chromosomeSize, String file) {
            runBtn.setEnabled(false);
            Thread t = new Thread(){
                @Override
                public void run(){
                    BinPopulation x = new Population().new BinPopulation(mutation, Population, chromosomeLen, chromosomeSize);

                    x.getDatasetData(file);
                    int current = 0;
                    while (current <= Integer.parseInt(generatuinSizeTF.getValue()+"")) {
                        progress.setValue(current);
                        drawLine(current, x.getBestFitness(),x.getAverageFitness(),x.getWorstFitness());
                        x.fitnessFunction();
                        x.selection();
                        x.crossover();
                        x.mutation();
                        x.newGeneration();
                        current++;
                        System.out.println(current);
                    }
                    runBtn.setEnabled(true);
                }
            };
            t.start();
        }
        
        public void algrithmFP(double mutation, int Population, int chromosomeLen, int chromosomeSize, String file) {
            runBtn.setEnabled(false);
            Thread t = new Thread(){
                @Override
                public void run(){
                    FPPopulation x = new Population().new FPPopulation(mutation, Population, chromosomeLen, chromosomeSize);
                    
                    x.getDatasetData(file);
                    int current = 0;
                    while (current <= Integer.parseInt(generatuinSizeTF.getValue()+"")) {
                        progress.setValue(current);
                        drawLine(current, x.getBestFitness(),x.getAverageFitness(),x.getWorstFitness());
                        x.fitnessFunction();
                        x.selection();
                        x.crossover();
                        x.mutation();
                        x.newGeneration();
                        current++;
                        System.out.println(current);
                    }
                    runBtn.setEnabled(true);
                }
            };
            t.start();
        }
    }
}
