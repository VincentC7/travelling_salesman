package GUI;

import Algo.Path;
import Algo.TravellingSalesman;
import Structure_Donnees.City;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicMenuBarUI;

@SuppressWarnings( "deprecation" )
public class Windows extends JFrame implements Observer {

    XYChartPanelTS xchart;
    TravellingSalesman travellingSaleman;
    Windows parent;
    JButton begin,stop;
    ArrayList<JComponent> components;
    Thread running;
    JComboBox<City> cities;
    JComboBox<String> remplacement,types;

    /**
     *Configuration de la condition du critère d'arrêt (par exemple, si vous choisissez un nombre de générations maximal,
     *     laissez l'utilisateur choisir ce maximum)
     *Configuration de l'algorithme de remplacement : si vous avez choisi un remplacement partiel, laissez l'utilisateur donner
     *     la proportion d'individus de l'ancienne génération qui seront conservés
     */



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

        begin = new JButton("commencer");


        begin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                running = new Thread(() -> {
                    try {
                        jm.setEnabled(false);
                        TravellingSalesman.setPersentageMutation(Double.parseDouble(jtext1.getText()));
                        TravellingSalesman.setPopulationSize(Integer.parseInt(jtext2.getText()));
                        TravellingSalesman.setPersentageRemplacement(Double.parseDouble(jtext3.getText()));
                        travellingSaleman.runAlgo();

                    }
                    catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(Windows.this,"Une case est vide ou bien vous n'avez pas renter un nombre");
                    }

                });
                running.start();


            }
        });
        stop = new JButton("Fin");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(running.isInterrupted())) {

                    running.interrupt();
                    travellingSaleman.stop();
                    travellingSaleman = null;
                    System.out.println(travellingSaleman);
                    for(JComponent comp : components){
                        comp.setEnabled(false);
                    }
                    xchart.addData("Fitness",new double []{ 0 },1);
                    jm.setEnabled(true);
                }
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
                    //TODO
                }
            }
        });
        JLabel jlabel6 = new JLabel("Type de selection");
        types = new JComboBox<>();
        types.addItem("Normal");
        types.addItem("Tournoi");
        types.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED ){
                    String s = (String)e.getItem();
                    if(s.equals("Tournoi")){
                        remplacement.setEnabled(false);
                        remplacement.setEnabled(false);
                    }
                    else if(s.equals("Normal")){
                        remplacement.setEnabled(true);
                        jtext3.setEnabled(true);
                    }
                }
            }
        });


        firstPart.add(jlabel1);
        firstPart.add(jtext1);
        firstPart.add(jlabel2);
        firstPart.add(jtext2);
        firstPart.add(jlabel5);
        firstPart.add(jtext3);
        firstPart.add(begin);
        firstPart.add(stop);
        secondPart.add(jlabel3);
        secondPart.add(cities);

        secondPart.add(jlabel4);
        secondPart.add(remplacement);
        secondPart.add(jlabel6);
        secondPart.add(types);


        upperPanel.add(firstPart);
        upperPanel.add(secondPart);

        components.add(jtext1);
        components.add(jtext2);
        components.add(jtext3);
        components.add(begin);
        components.add(remplacement);
        components.add(types);

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
        this.setMinimumSize(new Dimension(1000,600));
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
