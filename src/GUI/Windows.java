package GUI;

import Algo.KBestSelection;
import Algo.Path;
import Algo.TournamentSelection;
import Algo.TravellingSalesman;
import Structure_Donnees.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings( "deprecation" )
public class Windows extends JFrame implements Observer {

    XYChartPanelTS xchart;
    TravellingSalesman travellingSaleman;
    Windows parent;
    JButton begin;
    ArrayList<JComponent> components;
    Thread running;
    JComboBox<City> cities;
    JComboBox<String> remplacement,types;
    

    public Windows(/*Observable o*/){
        //o.addObserver(this);
        ItemListener il =new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED ){
                    travellingSaleman.setCity_start((City)e.getItem());
                }
            }
        };
        components = new ArrayList<>();
        parent = this;
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(new GridBagLayout());

        JMenuBar jmb = new JMenuBar();

        JMenu jm = new JMenu("Fichier");
        JMenuItem jmi = new JMenuItem("Ouvrir");

        jmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choix = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Json","json");
                choix.setFileFilter(filter);

                int returnVal = choix.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    travellingSaleman = new TravellingSalesman(choix.getSelectedFile().getAbsolutePath());

                    travellingSaleman.addObserver(Windows.this);
                    for(JComponent comp : components){
                        comp.setEnabled(true);
                    }
                    cities.addItem(travellingSaleman.getCityStart());
                    for(City city : travellingSaleman.getCities()){
                        cities.addItem(city);
                    }
                    cities.setSelectedItem(travellingSaleman.getCityStart());
                    cities.addItemListener(il);
                }

            }
        });
        JMenuItem jmi2 = new JMenuItem("Créer");

        jmi2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travellingSaleman = new TravellingSalesman();

                travellingSaleman.addObserver(Windows.this);
                for(JComponent comp : components){
                    comp.setEnabled(true);
                }
                cities.addItem(travellingSaleman.getCityStart());
                for(City city : travellingSaleman.getCities()){
                    cities.addItem(city);
                }
                cities.setSelectedItem(travellingSaleman.getCityStart());
                cities.addItemListener(il);
            }
        });

        jm.add(jmi);
        jm.add(jmi2);
        jmb.add(jm);
        this.setJMenuBar(jmb);
        JPanel upperPanel = new JPanel();
        GridLayout gl = new GridLayout(0,1);
        upperPanel.setLayout(gl);

        JPanel firstPart = new JPanel();
        JPanel secondPart = new JPanel();
        FlowLayout fl1 = new FlowLayout();
        firstPart.setLayout(fl1);
        FlowLayout fl2 = new FlowLayout();
        secondPart.setLayout(fl2);


        JLabel jlabel1 = new JLabel("Probabilité mutation");
        JTextField jtext1 = new JTextField(4);
        jtext1.addFocusListener(new FocusListenerMutator(2));

        JLabel jlabel2 = new JLabel("Taille de la population");
        JTextField jtext2 = new JTextField(5);

        JLabel jlabel5 = new JLabel("Proportion conservée");
        JTextField jtext3 = new JTextField(4);
        jtext1.addFocusListener(new FocusListenerMutator(2));

        JLabel jlabel7 = new JLabel("Nombre de generation max");
        JTextField jtext7 = new JTextField(5);

        JLabel jlabel8 = new JLabel("Max Generation meme fitness");
        JTextField jtext8 = new JTextField(5);

        begin = new JButton("commencer");


        begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = new Thread(() -> {
                    try {
                        TravellingSalesman.setPersentageMutation(Double.parseDouble(jtext1.getText()));
                        TravellingSalesman.setPopulationSize(Integer.parseInt(jtext2.getText()));
                        if (jtext3.isEnabled()) TravellingSalesman.setPersentageRemplacement(Double.parseDouble(jtext3.getText()));
                        travellingSaleman.setReplacementTotal(remplacement.getSelectedItem().equals("Total"));
                        TravellingSalesman.setMaxRepetitionSameFitness(Integer.parseInt(jtext8.getText()));
                        TravellingSalesman.setMaxGeneration(Integer.parseInt(jtext7.getText()));
                        xchart.addData("Fitness",new double []{ 0 },1);
                        jm.setEnabled(false);
                        travellingSaleman.runAlgo();
                        travellingSaleman = null;
                        cities = new JComboBox<>();
                        for(JComponent comp : components){
                            comp.setEnabled(false);
                        }
                        jm.setEnabled(true);


                    }
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(Windows.this,"Une case est vide ou bien vous n'avez pas renter un nombre");
                    }

                });
                running.start();


            }
        });

        JLabel jlabel3 = new JLabel("Ville départ");
        cities = new JComboBox<>();
        JLabel jlabel4 = new JLabel("Type Remplacement");
        remplacement = new JComboBox<>();
        remplacement.addItem("Partiel");
        remplacement.addItem("Total");
        remplacement.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED ){
                    String s = (String)e.getItem();
                    if(s.equals("Partiel")){
                        jtext3.setEnabled(true);
                    }
                    else{
                        jtext3.setEnabled(false);
                    }
                }
            }
        });
        JLabel jlabel6 = new JLabel("Type de selection");
        types = new JComboBox<>();
        types.addItem("k meilleur");
        types.addItem("Tournoi");
        types.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED ){
                String s = (String)e.getItem();
                if(s.equals("Tournoi")){
                    travellingSaleman.setSelector(new TournamentSelection());
                }
                else if(s.equals("k meilleur")){
                    travellingSaleman.setSelector(new KBestSelection());
                }
            }
        });

        JPanel thirdPart = new JPanel();
        FlowLayout fl3 = new FlowLayout();
        thirdPart.setLayout(fl3);



        firstPart.add(jlabel1);
        firstPart.add(jtext1);
        firstPart.add(jlabel2);
        firstPart.add(jtext2);
        firstPart.add(jlabel5);
        firstPart.add(jtext3);
        firstPart.add(begin);
        secondPart.add(jlabel3);
        secondPart.add(cities);

        secondPart.add(jlabel4);
        secondPart.add(remplacement);
        secondPart.add(jlabel6);
        secondPart.add(types);

        thirdPart.add(jlabel7);
        thirdPart.add(jtext7);
        thirdPart.add(jlabel8);
        thirdPart.add(jtext8);


        upperPanel.add(firstPart);
        upperPanel.add(secondPart);
        upperPanel.add(thirdPart);

        components.add(jtext1);
        components.add(jtext2);
        components.add(jtext3);
        components.add(begin);
        components.add(remplacement);
        components.add(types);
        components.add(jtext7);
        components.add(jtext8);
        components.add(cities);

        c.weightx=1;
        c.weighty=0.5;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight =1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy=1;
        this.add(upperPanel,c);
        JPanel lowerPanel = new JPanel();
        c.gridheight =2;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy=2;
        c.weighty=3;
        this.xchart = new XYChartPanelTS("X","Y","Fitness",1000,400);
        lowerPanel.add(xchart);
        this.add(lowerPanel,c);
        for(JComponent comp : components){
            comp.setEnabled(false);
        }
        this.setTitle("Travelling Saleman");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(1000,700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void update(Observable o, Object arg) {
        TravellingSalesman ts = (TravellingSalesman)o;
        List<Integer> array = ts.getFitness().stream().map(Path::getDistance).collect(Collectors.toList());
        double[] doubleArray = array.stream().mapToDouble(d -> d.doubleValue()).toArray();
        xchart.addData("Fitness",doubleArray, ts.getCurrent_generation());
    }


}
