package GUI;

import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicMenuBarUI;

@SuppressWarnings( "deprecation" )
public class Windows extends JFrame implements Observer {

    XYChartPanelTS xchart;
    /**
     *laissé à l'utilisateur du fichier des villes / distances grâce à, par exemple, une boîte de dialogue
     *laissé à l'utilisateur de la ville de départ (et/ou de retour) dans l'interface graphique depuis les
     *     villes du fichier
     *Configuration de la probabilité de mutation
     *Configuration de la taille de la population
     *Configuration de la condition du critère d'arrêt (par exemple, si vous choisissez un nombre de générations maximal,
     *     laissez l'utilisateur choisir ce maximum)
     *Configuration de l'algorithme de remplacement : si vous avez choisi un remplacement partiel, laissez l'utilisateur donner
     *     la proportion d'individus de l'ancienne génération qui seront conservés
     *Mise en place du multithreading : à vous de proposer une (ou plusieurs) stratégie(s) de multithreading dans le
     *     cadre de ce problème. N'hésitez pas à en discuter avec vos enseignants.
     */



    public Windows(/*Observable o*/){
        //o.addObserver(this);
        GridBagConstraints c = new GridBagConstraints();

        this.setLayout(new GridBagLayout());

        JMenuBar jmb = new JMenuBar();
        jmb.setUI(new BasicMenuBarUI(){
            @Override
            public void paint(Graphics g, JComponent c) {
                g.setColor(Color.GRAY);
                g.fillRect(0,0,c.getWidth(),c.getHeight());
                c.repaint();
            }
        });

        JMenu jm = new JMenu("Fichier");
        jm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jf = new JFileChooser();
            }
        });
        jmb.add(jm);
        this.setJMenuBar(jmb);
        JPanel upperPanel = new JPanel();
        FlowLayout fl = new FlowLayout();
        upperPanel.setLayout(fl);
        JLabel jlabel1 = new JLabel("Probabilité mutation");
        JTextField jtext1 = new JTextField(4);
        jtext1.addFocusListener(new FocusListenerMutator(2));

        JLabel jlabel2 = new JLabel("Taille de la population");
        JTextField jtext2 = new JTextField(5);
        JButton jb = new JButton("test");
        jb.addActionListener(new ActionListener() {
            int compteur = 1;
            ArrayList<Double> arraytemp = new ArrayList<>();
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    while(true) {
                        arraytemp.add(Math.random() * 100);
                        double[] temp = arraytemp.stream().mapToDouble(d -> d).toArray();
                        xchart.addData("Fitness", temp, compteur);
                        compteur++;
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        upperPanel.add(jlabel1);
        upperPanel.add(jtext1);
        upperPanel.add(jlabel2);
        upperPanel.add(jtext2);
        upperPanel.add(jb);
        c.weightx=1;
        c.weighty=1;
        c.fill = GridBagConstraints.BOTH;
        c.gridheight =1;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy=1;

        upperPanel.setBackground(Color.blue);
        this.add(upperPanel,c);
        JPanel lowerPanel = new JPanel();
        c.gridheight =2;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy=2;
        c.weighty=3;
        lowerPanel.setBackground(Color.BLACK);
        //this.chart = new XYChartBuilder().width(600).height(400).xAxisTitle("X").yAxisTitle("Y").build();
        //chart.addSeries("Fitness",new double [] { 0 },new double [] { 0 });
        //this.xchart = new XYChartPanelTS(chart);
        this.xchart = new XYChartPanelTS("X","Y","Fitness",800,400);
        lowerPanel.add(xchart);
        this.add(lowerPanel,c);
        this.setTitle("Travelling Saleman");
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(800,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void update(Observable o, Object arg) {
        this.repaint();
    }


}
