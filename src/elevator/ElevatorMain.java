package elevator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class ElevatorMain extends javax.swing.JFrame
{
    int delay, floorsAvail;
    String floorButtonTooltip, floorComboTooltip, colourTooltip;
    Timer timer1;
    Elevator elevator; // Main elevator object.
    
    HashMap<Integer, Integer> pickUp; // Maps number of button clicks to the appropriate floors.
    ArrayList<JButton> floors; // List of buttons for easy access later.
    
    ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/elevator/logo.png"));
    
    // Listeners
    ButtonListenerBottom blb;
    ButtonListenerFloors blf;
    MouseClickListener mcl;
    ComboListener cl;
    
    // Colours for floor buttons (clicked/non-clicked)
    Color c = new Color(153,204,255);
    Color n = new Color(102,102,102);
    
    // Colours for theme.
    int theme = 1;
    Color theme1 = new Color(110,147,250); //light blue
    Color theme2 = new Color(61,88,145); //dark blue
    Color theme3 = new Color(99,65,95); //dark purple
    Color theme4 = new Color(82,83,84); //dark grey
    
    // Colors to represent enabled/disabled (buttons).
    Color dis = new Color(102,102,102);
    Color en = new Color(138,138,138);
    /**
     * Creates new form ElevatorMain
     */
    public ElevatorMain()
    {
        initComponents();
        this.setLocationRelativeTo(null);
        
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Welcome to the Interactive Computer Systems Elevator Simulator.");
        System.out.println("By Rob Bloomfield, 2015.");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("\nFunctionality:\n");
        System.out.println("- Press START to start the elevator moving up and down.");
        System.out.println("\t- While START is engaged, the elevator will move up and down indefintitely.");
        System.out.println("- Press STOP to stop it.");
        System.out.println("- Press any of the numbered buttons on the left hand side to add a pickup request to that floor.");
        System.out.println("- Right click on a button that has pickup requests in the queue to remove ONE request.");
        System.out.println("- Select the number of floors you wish to be active using the combo box.");
        System.out.println("---------------------------------------------------------------------------");
        
        delay = 20;
        timer1 = new Timer(delay, new TimerListener());
        
        floorsAvail = 8;
        floorButtonTooltip = "<html>Left click to add pickup request.<br> Right click to cancel a request.</html>";
        floorComboTooltip = "<html>Choose the number of floors you wish to be active.</html>";
        colourTooltip = "<html>Choose from six colour themes.</html>";
        
        blb  = new ButtonListenerBottom();
        blf = new ButtonListenerFloors();
        mcl = new MouseClickListener();
        cl = new ComboListener();
                
        startButton.addActionListener(blb);
        stopButton.addActionListener(blb);
        colourButton.addActionListener(blb);
        quitButton.addActionListener(blb);
        
        oneButton.addActionListener(blf); oneButton.addMouseListener(mcl);
        twoButton.addActionListener(blf); twoButton.addMouseListener(mcl);
        threeButton.addActionListener(blf); threeButton.addMouseListener(mcl);
        fourButton.addActionListener(blf); fourButton.addMouseListener(mcl);
        fiveButton.addActionListener(blf); fiveButton.addMouseListener(mcl);
        sixButton.addActionListener(blf); sixButton.addMouseListener(mcl);
        sevenButton.addActionListener(blf); sevenButton.addMouseListener(mcl);
        eightButton.addActionListener(blf); eightButton.addMouseListener(mcl);
        
        floorComboBox.addActionListener(cl);
        
        // Set up button/click count mapping.
        pickUp = new HashMap<Integer, Integer>();
        for (int i = 1; i<9; i++)
        {
            pickUp.put(i, 0);
        }
        
        // Add all buttons to list for easy access later.
        floors = new ArrayList<JButton>();
        floors.add(oneButton); floors.add(twoButton); floors.add(threeButton); 
        floors.add(fourButton); floors.add(fiveButton); floors.add(sixButton); 
        floors.add(sevenButton); floors.add(eightButton); 
        
        for(JButton j:floors)
        {
            j.setToolTipText(floorButtonTooltip);
        }
        floorComboBox.setToolTipText(floorComboTooltip);
        colourButton.setToolTipText(colourTooltip);
        stopButton.setEnabled(false);
        elevator = new Elevator(0,0,0,0,timer1,floors,this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        mainPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        topLeftPanel = new logoPanel();
        floorHead = new javax.swing.JLabel();
        floorLabel = new javax.swing.JLabel();
        topRightPanel = new javax.swing.JPanel();
        upperTopRightPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();
        centerPanel = new javax.swing.JPanel();
        innerLeftPanel = new javax.swing.JPanel();
        eightButton = new javax.swing.JButton();
        sevenButton = new javax.swing.JButton();
        sixButton = new javax.swing.JButton();
        fiveButton = new javax.swing.JButton();
        fourButton = new javax.swing.JButton();
        threeButton = new javax.swing.JButton();
        twoButton = new javax.swing.JButton();
        oneButton = new javax.swing.JButton();
        innerCenterPanel = new innerCenterPanel();
        innerRightPanel = new javax.swing.JPanel();
        pad8 = new javax.swing.JPanel();
        pad7 = new javax.swing.JPanel();
        pad6 = new javax.swing.JPanel();
        pad5 = new javax.swing.JPanel();
        pad4 = new javax.swing.JPanel();
        pad3 = new javax.swing.JPanel();
        pad2 = new javax.swing.JPanel();
        pad1 = new javax.swing.JPanel();
        bottomPanel = new javax.swing.JPanel();
        bottomLeftPanel = new javax.swing.JPanel();
        floorComboBox = new javax.swing.JComboBox();
        bottomCenterPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        bottomRightPanel = new javax.swing.JPanel();
        colourButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentResized(java.awt.event.ComponentEvent evt)
            {
                formComponentResized(evt);
            }
        });

        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        mainPanel.setLayout(new java.awt.BorderLayout());

        topPanel.setBackground(new java.awt.Color(153, 204, 255));
        topPanel.setLayout(new java.awt.BorderLayout());

        topLeftPanel.setBackground(new java.awt.Color(153, 153, 153));
        topLeftPanel.setBorder(javax.swing.BorderFactory.createMatteBorder(5, 5, 5, 5, new java.awt.Color(153, 153, 153)));
        topLeftPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        topLeftPanel.setLayout(new java.awt.GridLayout(2, 0));

        floorHead.setBackground(new java.awt.Color(167, 209, 255));
        floorHead.setFont(new java.awt.Font("Gill Sans MT Condensed", 0, 36)); // NOI18N
        floorHead.setForeground(new java.awt.Color(102, 102, 102));
        floorHead.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorHead.setText("Floor");
        floorHead.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 5, new java.awt.Color(107, 161, 173)));
        floorHead.setOpaque(true);
        topLeftPanel.add(floorHead);

        floorLabel.setBackground(new java.awt.Color(167, 209, 255));
        floorLabel.setFont(new java.awt.Font("Mistral", 0, 42)); // NOI18N
        floorLabel.setForeground(new java.awt.Color(102, 102, 102));
        floorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        floorLabel.setText("1");
        floorLabel.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 5, new java.awt.Color(107, 161, 173)));
        floorLabel.setOpaque(true);
        topLeftPanel.add(floorLabel);

        topPanel.add(topLeftPanel, java.awt.BorderLayout.WEST);

        topRightPanel.setBackground(new java.awt.Color(110, 147, 250));
        topRightPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topRightPanel.setLayout(new java.awt.BorderLayout(0, 2));

        upperTopRightPanel.setBackground(new java.awt.Color(167, 209, 255));
        upperTopRightPanel.setPreferredSize(new java.awt.Dimension(50, 70));
        upperTopRightPanel.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("Gill Sans MT Condensed", 1, 56)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Elevator Simulator");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 5, 5, new java.awt.Color(107, 161, 173)));
        upperTopRightPanel.add(jLabel1);

        topRightPanel.add(upperTopRightPanel, java.awt.BorderLayout.NORTH);

        statusLabel.setForeground(new java.awt.Color(255, 255, 255));
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("Press START to being simulation. Press a FLOOR to request a pickup.");
        topRightPanel.add(statusLabel, java.awt.BorderLayout.CENTER);

        topPanel.add(topRightPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        centerPanel.setBackground(new java.awt.Color(51, 255, 51));
        centerPanel.setPreferredSize(new java.awt.Dimension(465, 200));
        centerPanel.setLayout(new java.awt.BorderLayout());

        innerLeftPanel.setBackground(new java.awt.Color(153, 153, 153));
        innerLeftPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        innerLeftPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        innerLeftPanel.setLayout(new java.awt.GridLayout(8, 0, 0, 5));

        eightButton.setBackground(new java.awt.Color(102, 102, 102));
        eightButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        eightButton.setForeground(new java.awt.Color(255, 255, 255));
        eightButton.setText("8");
        eightButton.setBorderPainted(false);
        innerLeftPanel.add(eightButton);

        sevenButton.setBackground(new java.awt.Color(102, 102, 102));
        sevenButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sevenButton.setForeground(new java.awt.Color(255, 255, 255));
        sevenButton.setText("7");
        sevenButton.setBorderPainted(false);
        innerLeftPanel.add(sevenButton);

        sixButton.setBackground(new java.awt.Color(102, 102, 102));
        sixButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sixButton.setForeground(new java.awt.Color(255, 255, 255));
        sixButton.setText("6");
        sixButton.setBorderPainted(false);
        innerLeftPanel.add(sixButton);

        fiveButton.setBackground(new java.awt.Color(102, 102, 102));
        fiveButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fiveButton.setForeground(new java.awt.Color(255, 255, 255));
        fiveButton.setText("5");
        fiveButton.setBorderPainted(false);
        innerLeftPanel.add(fiveButton);

        fourButton.setBackground(new java.awt.Color(102, 102, 102));
        fourButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        fourButton.setForeground(new java.awt.Color(255, 255, 255));
        fourButton.setText("4");
        fourButton.setBorderPainted(false);
        innerLeftPanel.add(fourButton);

        threeButton.setBackground(new java.awt.Color(102, 102, 102));
        threeButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        threeButton.setForeground(new java.awt.Color(255, 255, 255));
        threeButton.setText("3");
        threeButton.setBorderPainted(false);
        innerLeftPanel.add(threeButton);

        twoButton.setBackground(new java.awt.Color(102, 102, 102));
        twoButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        twoButton.setForeground(new java.awt.Color(255, 255, 255));
        twoButton.setText("2");
        twoButton.setBorderPainted(false);
        innerLeftPanel.add(twoButton);

        oneButton.setBackground(new java.awt.Color(102, 102, 102));
        oneButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        oneButton.setForeground(new java.awt.Color(255, 255, 255));
        oneButton.setText("1");
        oneButton.setBorderPainted(false);
        innerLeftPanel.add(oneButton);

        centerPanel.add(innerLeftPanel, java.awt.BorderLayout.WEST);

        innerCenterPanel.setBackground(new java.awt.Color(204, 204, 204));
        innerCenterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout innerCenterPanelLayout = new javax.swing.GroupLayout(innerCenterPanel);
        innerCenterPanel.setLayout(innerCenterPanelLayout);
        innerCenterPanelLayout.setHorizontalGroup(
            innerCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );
        innerCenterPanelLayout.setVerticalGroup(
            innerCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        centerPanel.add(innerCenterPanel, java.awt.BorderLayout.CENTER);

        innerRightPanel.setBackground(new java.awt.Color(110, 147, 250));
        innerRightPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        innerRightPanel.setPreferredSize(new java.awt.Dimension(55, 100));
        innerRightPanel.setLayout(new java.awt.GridLayout(8, 0));

        pad8.setBackground(new java.awt.Color(224, 224, 224));

        javax.swing.GroupLayout pad8Layout = new javax.swing.GroupLayout(pad8);
        pad8.setLayout(pad8Layout);
        pad8Layout.setHorizontalGroup(
            pad8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad8Layout.setVerticalGroup(
            pad8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad8);

        pad7.setBackground(new java.awt.Color(215, 214, 212));

        javax.swing.GroupLayout pad7Layout = new javax.swing.GroupLayout(pad7);
        pad7.setLayout(pad7Layout);
        pad7Layout.setHorizontalGroup(
            pad7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad7Layout.setVerticalGroup(
            pad7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad7);

        pad6.setBackground(new java.awt.Color(199, 199, 199));

        javax.swing.GroupLayout pad6Layout = new javax.swing.GroupLayout(pad6);
        pad6.setLayout(pad6Layout);
        pad6Layout.setHorizontalGroup(
            pad6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad6Layout.setVerticalGroup(
            pad6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad6);

        pad5.setBackground(new java.awt.Color(191, 191, 191));

        javax.swing.GroupLayout pad5Layout = new javax.swing.GroupLayout(pad5);
        pad5.setLayout(pad5Layout);
        pad5Layout.setHorizontalGroup(
            pad5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad5Layout.setVerticalGroup(
            pad5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad5);

        pad4.setBackground(new java.awt.Color(160, 160, 160));

        javax.swing.GroupLayout pad4Layout = new javax.swing.GroupLayout(pad4);
        pad4.setLayout(pad4Layout);
        pad4Layout.setHorizontalGroup(
            pad4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad4Layout.setVerticalGroup(
            pad4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad4);

        pad3.setBackground(new java.awt.Color(140, 140, 140));

        javax.swing.GroupLayout pad3Layout = new javax.swing.GroupLayout(pad3);
        pad3.setLayout(pad3Layout);
        pad3Layout.setHorizontalGroup(
            pad3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad3Layout.setVerticalGroup(
            pad3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad3);

        pad2.setBackground(new java.awt.Color(112, 112, 112));

        javax.swing.GroupLayout pad2Layout = new javax.swing.GroupLayout(pad2);
        pad2.setLayout(pad2Layout);
        pad2Layout.setHorizontalGroup(
            pad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad2Layout.setVerticalGroup(
            pad2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad2);

        pad1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout pad1Layout = new javax.swing.GroupLayout(pad1);
        pad1.setLayout(pad1Layout);
        pad1Layout.setHorizontalGroup(
            pad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );
        pad1Layout.setVerticalGroup(
            pad1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        innerRightPanel.add(pad1);

        centerPanel.add(innerRightPanel, java.awt.BorderLayout.EAST);

        mainPanel.add(centerPanel, java.awt.BorderLayout.CENTER);

        bottomPanel.setBackground(new java.awt.Color(0, 153, 153));
        bottomPanel.setPreferredSize(new java.awt.Dimension(200, 50));
        bottomPanel.setLayout(new java.awt.BorderLayout());

        bottomLeftPanel.setBackground(new java.awt.Color(153, 153, 153));
        bottomLeftPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomLeftPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        bottomLeftPanel.setLayout(new java.awt.GridLayout(1, 1));

        floorComboBox.setBackground(new java.awt.Color(242, 242, 242));
        floorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "8 Floors", "7 Floors", "6 Floors", "5 Floors", "4 Floors", "3 Floors", "2 Floors" }));
        floorComboBox.setPreferredSize(new java.awt.Dimension(20, 20));
        bottomLeftPanel.add(floorComboBox);

        bottomPanel.add(bottomLeftPanel, java.awt.BorderLayout.WEST);

        bottomCenterPanel.setBackground(new java.awt.Color(123, 119, 123));
        bottomCenterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomCenterPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        bottomCenterPanel.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        startButton.setBackground(new java.awt.Color(138, 138, 138));
        startButton.setForeground(new java.awt.Color(255, 255, 255));
        startButton.setText("Start");
        startButton.setBorderPainted(false);
        bottomCenterPanel.add(startButton);

        stopButton.setBackground(new java.awt.Color(138, 138, 138));
        stopButton.setForeground(new java.awt.Color(255, 255, 255));
        stopButton.setText("Stop");
        stopButton.setBorderPainted(false);
        bottomCenterPanel.add(stopButton);

        bottomPanel.add(bottomCenterPanel, java.awt.BorderLayout.CENTER);

        bottomRightPanel.setBackground(new java.awt.Color(102, 102, 102));
        bottomRightPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomRightPanel.setPreferredSize(new java.awt.Dimension(200, 50));
        bottomRightPanel.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        colourButton.setBackground(new java.awt.Color(0, 51, 102));
        colourButton.setForeground(new java.awt.Color(255, 255, 255));
        colourButton.setText("Colour");
        colourButton.setBorderPainted(false);
        bottomRightPanel.add(colourButton);

        quitButton.setBackground(new java.awt.Color(204, 0, 51));
        quitButton.setForeground(new java.awt.Color(255, 255, 255));
        quitButton.setText("Quit");
        quitButton.setBorderPainted(false);
        bottomRightPanel.add(quitButton);

        bottomPanel.add(bottomRightPanel, java.awt.BorderLayout.EAST);

        mainPanel.add(bottomPanel, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_formComponentResized
    {//GEN-HEADEREND:event_formComponentResized
        // TODO: reposition elevator rectangle when window is resized and timer is not running.
    }//GEN-LAST:event_formComponentResized

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ElevatorMain().setVisible(true);
            }
        });
    }
    
    public void updatePickupLabel(int floor, int num)
    {
        statusLabel.setText("Picking up ["+ num +"] passengers on floor ["+ floor +"]");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomCenterPanel;
    private javax.swing.JPanel bottomLeftPanel;
    private javax.swing.JPanel bottomPanel;
    private javax.swing.JPanel bottomRightPanel;
    private javax.swing.JPanel centerPanel;
    private javax.swing.JButton colourButton;
    private javax.swing.JButton eightButton;
    private javax.swing.JButton fiveButton;
    private javax.swing.JComboBox floorComboBox;
    private javax.swing.JLabel floorHead;
    static javax.swing.JLabel floorLabel;
    private javax.swing.JButton fourButton;
    private javax.swing.JPanel innerCenterPanel;
    private javax.swing.JPanel innerLeftPanel;
    private javax.swing.JPanel innerRightPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton oneButton;
    private javax.swing.JPanel pad1;
    private javax.swing.JPanel pad2;
    private javax.swing.JPanel pad3;
    private javax.swing.JPanel pad4;
    private javax.swing.JPanel pad5;
    private javax.swing.JPanel pad6;
    private javax.swing.JPanel pad7;
    private javax.swing.JPanel pad8;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton sevenButton;
    private javax.swing.JButton sixButton;
    private javax.swing.JButton startButton;
    public static javax.swing.JLabel statusLabel;
    private javax.swing.JButton stopButton;
    private javax.swing.JButton threeButton;
    private javax.swing.JPanel topLeftPanel;
    private javax.swing.JPanel topPanel;
    private javax.swing.JPanel topRightPanel;
    private javax.swing.JButton twoButton;
    private javax.swing.JPanel upperTopRightPanel;
    // End of variables declaration//GEN-END:variables

    // Custom inner class to represent the middle panel.
    // Elevator animation will occur in the overriden paint function.
    class innerCenterPanel extends JPanel
    {
        int interval = 0;
        boolean first = true;
        
        // PaintComponent is called every time the timer generates an event.
        // Measurements should be re-calculated here so that the positioning of the
        // painted panel is always correct.
        // However, keep processing to a minimum here to avoid lag.
        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);  
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            interval = this.getHeight()/8;
            g2.setColor(Color.white);
            
            // Draw all the horizontal (floors) lines.
            for (int i = 0; i<8; i++)
            {
                g2.drawLine(0,interval,this.getWidth(),interval);
                interval += this.getHeight()/8;
            }
            
            // Blank out the inactive floors areas.
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(0,
                    0,
                    this.getWidth(),
                    this.getHeight()-(floorsAvail*(this.getHeight()/8))-4);
           
            // Set up elevator.
            elevator.setLimitX((this.getHeight()));
            elevator.setHeight((this.getHeight()/8)-7);
            elevator.setWidth(60);
            elevator.setX((this.getWidth()/2)-(elevator.getWidth()/2));
            if (first == true)
            {
                elevator.setY((this.getHeight())-elevator.getHeight()-5);
                first = false;
            }
            
            // Draw elevator.
            g2.setColor(Color.GRAY);
            g2.fillRect(elevator.getX(), 
                        elevator.getY(), 
                        elevator.getWidth(), 
                        elevator.getHeight());
            
            g2.setColor(Color.white);
            g2.drawLine(elevator.getX()+(elevator.getWidth()/2),
                        elevator.getY()+1,
                        elevator.getX()+(elevator.getWidth()/2),
                        elevator.getY()+(elevator.getHeight())-2);
            
            // Set the upper limit for animation.
            elevator.setUpper(this.getHeight(),floorsAvail);
            
        }
    }
    
    // Custom inner class to represent the top left panel.
    // Image is drawn on JPanel (Logo).
    class logoPanel extends JPanel
    {
//        @Override
//        protected void paintComponent(Graphics g) 
//        {
//            super.paintComponent(g);
//            g.drawImage(logo.getImage(), 0, 0, super.getWidth(), super.getHeight(), null);
//        }
    }
    
    // All of the listeners:
    // Timer listener for elevator animation.
    class TimerListener implements ActionListener
    {
        int previous = 1;
        @Override
        public void actionPerformed(ActionEvent e)
        {
            elevator.update(pickUp);
            repaint();
            
            if (elevator.getDirection() > 0 && !elevator.stopped())
                statusLabel.setText("Going down...");
            else if (elevator.getDirection() < 0 && !elevator.stopped())
                statusLabel.setText("Going up...");
            
            floorLabel.setText(""+elevator.getCurrentFloor());
        }
    }
    
    // Button Listener for bottom buttons.
    class ButtonListenerBottom implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == startButton)
            {
                timer1.start();
                startButton.setEnabled(false);
                startButton.setBackground(dis);
                stopButton.setEnabled(true);
                stopButton.setBackground(en);
                elevator.pushStart();
            }
            else if (e.getSource() == stopButton)
            {
                timer1.stop();
                stopButton.setEnabled(false);
                stopButton.setBackground(dis);
                startButton.setEnabled(true);
                startButton.setBackground(en);
                elevator.pushStop();
                statusLabel.setText("Stopped...");
            }
            else if (e.getSource() == colourButton)
            {
                switch(theme)
                {
                    case 1:
                        innerRightPanel.setBackground(theme2);
                        topRightPanel.setBackground(theme2);
                        theme++;
                        break;
                    case 2:
                        innerRightPanel.setBackground(theme3);
                        topRightPanel.setBackground(theme3);
                        theme++;
                        break;
                    case 3:
                        innerRightPanel.setBackground(theme4);
                        topRightPanel.setBackground(theme4);
                        theme++;
                        break;
                    case 4:
                        innerRightPanel.setBackground(theme1);
                        topRightPanel.setBackground(theme1);
                        theme = 1;
                        break;
                    
                }
                
            }
            else
            {
                System.out.println("Exiting with exit code (0).");
                System.exit(0); // Close program.
            }
        }
        
    }
    
    // Button Listener for floor buttons.
    class ButtonListenerFloors implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
           if (e.getSource() == eightButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 8)
               {
                   pickUp.put(8, pickUp.get(8) + 1);
                   eightButton.setBackground(c);
                   eightButton.setText("8 (" + pickUp.get(8) + ")");
               }
           }
           else if (e.getSource() == sevenButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 7)
               {
                   pickUp.put(7, pickUp.get(7) + 1);
                   sevenButton.setText("7 (" + pickUp.get(7) + ")");
                   sevenButton.setBackground(c);
               }
           }
           else if (e.getSource() == sixButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 6)
               {
                    pickUp.put(6, pickUp.get(6) + 1);
                    sixButton.setText("6 (" + pickUp.get(6) + ")");
                    sixButton.setBackground(c);
               }
           }
           else if (e.getSource() == fiveButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 5)
               {
                    pickUp.put(5, pickUp.get(5) + 1);
                    fiveButton.setText("5 (" + pickUp.get(5) + ")");
                    fiveButton.setBackground(c);
               }
           }
           else if (e.getSource() == fourButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 4)
               {
                    pickUp.put(4, pickUp.get(4) + 1);
                    fourButton.setText("4 (" + pickUp.get(4) + ")");
                    fourButton.setBackground(c);
               }
           }
           else if (e.getSource() == threeButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 3)
               {
                    pickUp.put(3, pickUp.get(3) + 1);
                    threeButton.setText("3 (" + pickUp.get(3) + ")");
                    threeButton.setBackground(c);
               }
           }
           else if (e.getSource() == twoButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 2)
               {
                    pickUp.put(2, pickUp.get(2) + 1);
                    twoButton.setText("2 (" + pickUp.get(2) + ")");
                    twoButton.setBackground(c);
               }
           }
           else if (e.getSource() == oneButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 1)
               {
                    pickUp.put(1, pickUp.get(1) + 1);
                    oneButton.setText("1 (" + pickUp.get(1) + ")");
                    oneButton.setBackground(c);
               }
               
           }
           System.out.println(pickUp);
        }
    }
    
    // Mouse Listener for right-click.
    // If a button is right clicked and that button has a pickup value greater than 0,
    // that number is decreased by 1, until 0.
    class MouseClickListener implements MouseListener
    {
        JButton temp;
        @Override
        public void mouseClicked(MouseEvent e)
        {
            temp = (JButton)e.getSource();
            if (SwingUtilities.isRightMouseButton(e) && temp.isEnabled())
            {
               if (e.getSource() == eightButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 8)
                   {
                        if (pickUp.get(8) > 1)
                        {
                             pickUp.put(8, pickUp.get(8) - 1);
                             eightButton.setText("8 (" + pickUp.get(8) + ")");
                        }
                        else
                        {
                             pickUp.put(8, 0);
                             eightButton.setBackground(n);
                             eightButton.setText("8");
                        }
                    }
               }
               else if (e.getSource() == sevenButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 7)
                   {
                        if (pickUp.get(7) > 1)
                        {
                             pickUp.put(7, pickUp.get(7) - 1);
                             sevenButton.setText("7 (" + pickUp.get(7) + ")");
                        }
                        else
                        {
                             pickUp.put(7, 0);
                             sevenButton.setBackground(n);
                             sevenButton.setText("7");
                        }
                   }
               }
               else if (e.getSource() == sixButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 6)
                   {
                        if (pickUp.get(6) > 1)
                        {
                             pickUp.put(6, pickUp.get(6) - 1);
                             sixButton.setText("6 (" + pickUp.get(6) + ")");
                        }
                        else
                        {
                             pickUp.put(6, 0);
                             sixButton.setBackground(n);
                             sixButton.setText("6");
                        }
                   }
               }
               else if (e.getSource() == fiveButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 5)
                   {
                        if (pickUp.get(5) > 1)
                        {
                             pickUp.put(5, pickUp.get(5) - 1);
                             fiveButton.setText("5 (" + pickUp.get(5) + ")");
                        }
                        else
                        {
                             pickUp.put(5, 0);
                             fiveButton.setBackground(n);
                             fiveButton.setText("5");
                        }
                   }
               }
               else if (e.getSource() == fourButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 4)
                   {
                        if (pickUp.get(4) > 1)
                        {
                             pickUp.put(4, pickUp.get(4) - 1);
                             fourButton.setText("4 (" + pickUp.get(4) + ")");
                        }
                        else
                        {
                             pickUp.put(4, 0);
                             fourButton.setBackground(n);
                             fourButton.setText("4");
                        }
                   }
               }
               else if (e.getSource() == threeButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 3)
                   {
                        if (pickUp.get(3) > 1)
                        {
                             pickUp.put(3, pickUp.get(3) - 1);
                             threeButton.setText("3 (" + pickUp.get(3) + ")");
                        }
                        else
                        {
                             pickUp.put(3, 0);
                             threeButton.setBackground(n);
                             threeButton.setText("3");
                        }
                   }
               }
               else if (e.getSource() == twoButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 2)
                   {
                        if (pickUp.get(2) > 1)
                        {
                             pickUp.put(2, pickUp.get(2) - 1);
                             twoButton.setText("2 (" + pickUp.get(2) + ")");
                        }
                        else
                        {
                             pickUp.put(2, 0);
                             twoButton.setBackground(n);
                             twoButton.setText("2");
                        }
                   }
               }
               else if (e.getSource() == oneButton)
               {
                   if (!elevator.isStopped() || elevator.getCurrentFloor() != 1)
                   {
                        if (pickUp.get(1) > 1)
                        {
                             pickUp.put(1, pickUp.get(1) - 1);
                             oneButton.setText("1 (" + pickUp.get(1) + ")");
                        }
                        else
                        {
                             pickUp.put(1, 0);
                             oneButton.setBackground(n);
                             oneButton.setText("1");
                        }
                   }
               }
               System.out.println(pickUp);
            }
            
        }

        // Implement abstract methods.
        @Override public void mousePressed(MouseEvent e){}
        @Override public void mouseReleased(MouseEvent e){}
        @Override public void mouseEntered(MouseEvent e){}
        @Override public void mouseExited(MouseEvent e){}
            
    }
    
    class ComboListener implements ActionListener
    {
        JComboBox cb;
        String selection;
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            cb = (JComboBox)e.getSource();
            selection = (String)cb.getSelectedItem();
            
            switch (selection)
            {
                case "8 Floors": 
                    floorsAvail=8;
                    break;
                case "7 Floors":
                    floorsAvail=7;
                    break;
                case "6 Floors":
                    floorsAvail=6;
                    break;
                case "5 Floors":
                    floorsAvail=5;
                    break;
                case "4 Floors":
                    floorsAvail=4;
                    break;
                case "3 Floors":
                    floorsAvail=3;
                    break;
                case "2 Floors":
                    floorsAvail=2;
                    break;
                default:  
                    floorsAvail = 8;
            }
            
            for(JButton j: floors)
            {
                j.setEnabled(true);
            }
            for (int i = floorsAvail; i<floors.size(); i++)
            {
                floors.get(i).setBackground(Color.black);
                floors.get(i).setEnabled(false);
                
            }
            
            //Stop timer and reset simulation.
            //Resets the elevator to the bottom floor.
            //Enables/disables the correct buttons.
            //Resets the pickup counters and button labels.
            timer1.stop();
            elevator.stopT2();
            elevator.setY((innerCenterPanel.getHeight())-elevator.getHeight()-5);
            stopButton.setEnabled(false);
            stopButton.setBackground(dis);
            startButton.setEnabled(true);
            startButton.setBackground(en);
            elevator.pushStop();
            elevator.stop();
            
            // Resets the pickup counters.
            for (int i = 1; i<=pickUp.size();i++)
            {
                pickUp.put(i, 0);
            }
            
            // Resets the button labels (pickup).
            int j = 1;
            for (JButton b: floors)
            {
                b.setText(""+j);
                b.setBackground(n);
                j++;
            }
            
            statusLabel.setText("Standing by...");
            repaint();
        } 
    }
}
