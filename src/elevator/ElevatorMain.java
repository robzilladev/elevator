package elevator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.event.ChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;

public class ElevatorMain extends javax.swing.JFrame
{
    int delay, floorsAvail;
    String floorButtonTooltip, floorComboTooltip, colourTooltip;
    boolean goToClicked = false;
    Timer timer1;
    Elevator elevator; // Main elevator object.
    Timer hoverTimer;
    
    GoToBottomPanel goToBottomPanel = new GoToBottomPanel();
    
    HashMap<Integer, Integer> pickUp; // Maps number of button clicks to the appropriate floors.
    HashMap<Integer, Integer> dropOff; // Maps number of people to be dropped off at each floor.
    HashMap<Integer, Integer> speed;
    ArrayList<JButton> floors; // List of buttons for easy access later.
    ArrayList<JButton> goToFloors;
    static ArrayList<Passenger> passengers;
    
    ImageIcon logo = new javax.swing.ImageIcon(getClass().getResource("/elevator/logo.png"));
    
    ImageIcon d1 = new javax.swing.ImageIcon(getClass().getResource("/1.png"));
    ImageIcon d2 = new javax.swing.ImageIcon(getClass().getResource("/2.png"));
    ImageIcon d3 = new javax.swing.ImageIcon(getClass().getResource("/3.png"));
    ImageIcon d4 = new javax.swing.ImageIcon(getClass().getResource("/4.png"));
    ImageIcon d5 = new javax.swing.ImageIcon(getClass().getResource("/5.png"));
    ImageIcon d6 = new javax.swing.ImageIcon(getClass().getResource("/4.png"));
    ImageIcon d7 = new javax.swing.ImageIcon(getClass().getResource("/3.png"));
    ImageIcon d8 = new javax.swing.ImageIcon(getClass().getResource("/2.png"));
    ImageIcon d9 = new javax.swing.ImageIcon(getClass().getResource("/1.png"));
    
    // Listeners
    ButtonListenerBottom blb;
    ButtonListenerFloors blf;
    ComboListener cl;
    GoToButtonListener gtl;
    HoverListener hl;
    
    // Colours for floor buttons (clicked/non-clicked)
    Color c = new Color(61,101,128);
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
    
    boolean first = true;
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
        floorComboTooltip = "<html>Choose the number of floors you wish to be active.</html>";
        colourTooltip = "<html>Choose from six colour themes.</html>";
        
        blb  = new ButtonListenerBottom();
        blf = new ButtonListenerFloors();
        cl = new ComboListener();
        gtl = new GoToButtonListener();
        hl = new HoverListener();
                
        startButton.addActionListener(blb);
        stopButton.addActionListener(blb);
        colourButton.addActionListener(blb);
        quitButton.addActionListener(blb);
        
        oneButton.addActionListener(blf); //oneButton.addMouseListener(mcl);
        twoButton.addActionListener(blf); //twoButton.addMouseListener(mcl);
        threeButton.addActionListener(blf); //threeButton.addMouseListener(mcl);
        fourButton.addActionListener(blf); //fourButton.addMouseListener(mcl);
        fiveButton.addActionListener(blf); //fiveButton.addMouseListener(mcl);
        sixButton.addActionListener(blf); //sixButton.addMouseListener(mcl);
        sevenButton.addActionListener(blf); //sevenButton.addMouseListener(mcl);
        eightButton.addActionListener(blf); //eightButton.addMouseListener(mcl);
        
        floorComboBox.addActionListener(cl);
        slider.addChangeListener(new SliderListener());
        
        // Set up button/click count mapping.
        pickUp = new HashMap<>();
        for (int i = 1; i<9; i++)
        {
            pickUp.put(i, 0);
        }
        
        // Set up goto button/click count mapping.
        dropOff = new HashMap<>();
        for (int i = 1; i<9; i++)
        {
            dropOff.put(i, 0);
        }
        
        speed = new HashMap<>();
        speed.put(1, 49);speed.put(2, 42);speed.put(3, 35);speed.put(4, 28);speed.put(5, 21);
        speed.put(6, 14);speed.put(7, 7);speed.put(8, 0);
        
        // Add all buttons to list for easy access later.
        floors = new ArrayList<>();
        floors.add(oneButton); floors.add(twoButton); floors.add(threeButton); 
        floors.add(fourButton); floors.add(fiveButton); floors.add(sixButton); 
        floors.add(sevenButton); floors.add(eightButton); 
        
        for(JButton j:floors)
        {
            j.setToolTipText(floorButtonTooltip);
            j.addMouseListener(hl);
        }
        floorComboBox.setToolTipText(floorComboTooltip);
        colourButton.setToolTipText(colourTooltip);
        
        
        stopButton.setEnabled(false);
        stopButton.setBackground(dis);
        
        // Go to frame stuff
        goToFrame.pack();
        goToFrame.addFocusListener(new GoToFocusListener());
        goToCancel.addActionListener(gtl); goTo8.addActionListener(gtl);goTo7.addActionListener(gtl);
        goTo6.addActionListener(gtl);goTo5.addActionListener(gtl);goTo4.addActionListener(gtl);
        goTo3.addActionListener(gtl);goTo2.addActionListener(gtl);goTo1.addActionListener(gtl);
        
        goToFloors = new ArrayList<>();
        goToFloors.add(goTo1);goToFloors.add(goTo2);goToFloors.add(goTo3);goToFloors.add(goTo4);
        goToFloors.add(goTo5);goToFloors.add(goTo6);goToFloors.add(goTo7);goToFloors.add(goTo8);
        
        passengers = new ArrayList<>();
        
        // !!!Must be initialised LAST!!!
        elevator = new Elevator(0,0,0,0,timer1,floors,this,goToFloors);
        elevator.setWidth(60);
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

        goToFrame = new javax.swing.JFrame();
        goToTopPanel = new javax.swing.JPanel();
        goToLabel = new javax.swing.JLabel();
        javax.swing.JPanel goToBottomPanel = new GoToBottomPanel();
        goTo1 = new javax.swing.JButton();
        goTo2 = new javax.swing.JButton();
        goTo3 = new javax.swing.JButton();
        goTo4 = new javax.swing.JButton();
        goTo5 = new javax.swing.JButton();
        goTo6 = new javax.swing.JButton();
        goTo7 = new javax.swing.JButton();
        goTo8 = new javax.swing.JButton();
        goToCancel = new javax.swing.JButton();
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
        slider = new javax.swing.JSlider();
        sliderLabel = new javax.swing.JLabel();
        bottomPanel = new javax.swing.JPanel();
        bottomLeftPanel = new javax.swing.JPanel();
        floorComboBox = new javax.swing.JComboBox();
        bottomCenterPanel = new javax.swing.JPanel();
        startButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        bottomRightPanel = new javax.swing.JPanel();
        colourButton = new javax.swing.JButton();
        quitButton = new javax.swing.JButton();

        goToFrame.setMaximumSize(null);
        goToFrame.setUndecorated(true);

        goToTopPanel.setBackground(new java.awt.Color(51, 51, 51));
        goToTopPanel.setPreferredSize(new java.awt.Dimension(2, 50));
        goToTopPanel.setLayout(new java.awt.BorderLayout());

        goToLabel.setBackground(new java.awt.Color(51, 51, 51));
        goToLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        goToLabel.setForeground(new java.awt.Color(255, 255, 255));
        goToLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goToLabel.setText("Destination Floor:");
        goToTopPanel.add(goToLabel, java.awt.BorderLayout.CENTER);

        goToFrame.getContentPane().add(goToTopPanel, java.awt.BorderLayout.NORTH);

        goToBottomPanel.setBackground(new java.awt.Color(102, 102, 102));
        goToBottomPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        goToBottomPanel.setPreferredSize(new java.awt.Dimension(220, 200));
        goToBottomPanel.setLayout(new java.awt.GridLayout(3, 3, 5, 5));

        goTo1.setBackground(new java.awt.Color(51, 51, 51));
        goTo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo1.setForeground(new java.awt.Color(102, 255, 255));
        goTo1.setText("1");
        goTo1.setBorderPainted(false);
        goTo1.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo1);

        goTo2.setBackground(new java.awt.Color(51, 51, 51));
        goTo2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo2.setForeground(new java.awt.Color(102, 255, 255));
        goTo2.setText("2");
        goTo2.setBorderPainted(false);
        goTo2.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo2);

        goTo3.setBackground(new java.awt.Color(51, 51, 51));
        goTo3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo3.setForeground(new java.awt.Color(102, 255, 255));
        goTo3.setText("3");
        goTo3.setBorderPainted(false);
        goTo3.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo3);

        goTo4.setBackground(new java.awt.Color(51, 51, 51));
        goTo4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo4.setForeground(new java.awt.Color(102, 255, 255));
        goTo4.setText("4");
        goTo4.setBorderPainted(false);
        goTo4.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo4);

        goTo5.setBackground(new java.awt.Color(51, 51, 51));
        goTo5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo5.setForeground(new java.awt.Color(102, 255, 255));
        goTo5.setText("5");
        goTo5.setBorderPainted(false);
        goTo5.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo5);

        goTo6.setBackground(new java.awt.Color(51, 51, 51));
        goTo6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo6.setForeground(new java.awt.Color(102, 255, 255));
        goTo6.setText("6");
        goTo6.setBorderPainted(false);
        goTo6.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo6);

        goTo7.setBackground(new java.awt.Color(51, 51, 51));
        goTo7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo7.setForeground(new java.awt.Color(102, 255, 255));
        goTo7.setText("7");
        goTo7.setBorderPainted(false);
        goTo7.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo7);

        goTo8.setBackground(new java.awt.Color(51, 51, 51));
        goTo8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goTo8.setForeground(new java.awt.Color(102, 255, 255));
        goTo8.setText("8");
        goTo8.setBorderPainted(false);
        goTo8.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goTo8);

        goToCancel.setBackground(new java.awt.Color(51, 51, 51));
        goToCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        goToCancel.setForeground(new java.awt.Color(255, 51, 51));
        goToCancel.setText("Cancel");
        goToCancel.setBorderPainted(false);
        goToCancel.setMargin(new java.awt.Insets(1, 1, 1, 1));
        goToBottomPanel.add(goToCancel);

        goToFrame.getContentPane().add(goToBottomPanel, java.awt.BorderLayout.CENTER);

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
        statusLabel.setText("Press START to begin simulation. Press a FLOOR to request a pickup.");
        topRightPanel.add(statusLabel, java.awt.BorderLayout.CENTER);

        topPanel.add(topRightPanel, java.awt.BorderLayout.CENTER);

        mainPanel.add(topPanel, java.awt.BorderLayout.NORTH);

        centerPanel.setBackground(new java.awt.Color(51, 255, 51));
        centerPanel.setPreferredSize(new java.awt.Dimension(465, 200));
        centerPanel.setLayout(new java.awt.BorderLayout());

        innerLeftPanel.setBackground(new java.awt.Color(153, 153, 153));
        innerLeftPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        innerLeftPanel.setPreferredSize(new java.awt.Dimension(140, 200));
        innerLeftPanel.setLayout(new java.awt.GridLayout(8, 0, 0, 5));

        eightButton.setBackground(new java.awt.Color(102, 102, 102));
        eightButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        eightButton.setForeground(new java.awt.Color(255, 255, 255));
        eightButton.setText("8");
        eightButton.setBorderPainted(false);
        eightButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(eightButton);

        sevenButton.setBackground(new java.awt.Color(102, 102, 102));
        sevenButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sevenButton.setForeground(new java.awt.Color(255, 255, 255));
        sevenButton.setText("7");
        sevenButton.setBorderPainted(false);
        sevenButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(sevenButton);

        sixButton.setBackground(new java.awt.Color(102, 102, 102));
        sixButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sixButton.setForeground(new java.awt.Color(255, 255, 255));
        sixButton.setText("6");
        sixButton.setBorderPainted(false);
        sixButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(sixButton);

        fiveButton.setBackground(new java.awt.Color(102, 102, 102));
        fiveButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fiveButton.setForeground(new java.awt.Color(255, 255, 255));
        fiveButton.setText("5");
        fiveButton.setBorderPainted(false);
        fiveButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(fiveButton);

        fourButton.setBackground(new java.awt.Color(102, 102, 102));
        fourButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fourButton.setForeground(new java.awt.Color(255, 255, 255));
        fourButton.setText("4");
        fourButton.setBorderPainted(false);
        fourButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(fourButton);

        threeButton.setBackground(new java.awt.Color(102, 102, 102));
        threeButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        threeButton.setForeground(new java.awt.Color(255, 255, 255));
        threeButton.setText("3");
        threeButton.setBorderPainted(false);
        threeButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(threeButton);

        twoButton.setBackground(new java.awt.Color(102, 102, 102));
        twoButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        twoButton.setForeground(new java.awt.Color(255, 255, 255));
        twoButton.setText("2");
        twoButton.setBorderPainted(false);
        twoButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(twoButton);

        oneButton.setBackground(new java.awt.Color(102, 102, 102));
        oneButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        oneButton.setForeground(new java.awt.Color(255, 255, 255));
        oneButton.setText("1");
        oneButton.setBorderPainted(false);
        oneButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        innerLeftPanel.add(oneButton);

        centerPanel.add(innerLeftPanel, java.awt.BorderLayout.WEST);

        innerCenterPanel.setBackground(new java.awt.Color(204, 204, 204));
        innerCenterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3));

        javax.swing.GroupLayout innerCenterPanelLayout = new javax.swing.GroupLayout(innerCenterPanel);
        innerCenterPanel.setLayout(innerCenterPanelLayout);
        innerCenterPanelLayout.setHorizontalGroup(
            innerCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );
        innerCenterPanelLayout.setVerticalGroup(
            innerCenterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        centerPanel.add(innerCenterPanel, java.awt.BorderLayout.CENTER);

        innerRightPanel.setBackground(new java.awt.Color(110, 147, 250));
        innerRightPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        innerRightPanel.setPreferredSize(new java.awt.Dimension(70, 100));
        innerRightPanel.setLayout(new java.awt.BorderLayout());

        slider.setBackground(new java.awt.Color(102, 102, 102));
        slider.setForeground(new java.awt.Color(255, 255, 255));
        slider.setMajorTickSpacing(1);
        slider.setMaximum(8);
        slider.setMinimum(1);
        slider.setOrientation(javax.swing.JSlider.VERTICAL);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        slider.setValue(5);
        slider.setOpaque(false);
        innerRightPanel.add(slider, java.awt.BorderLayout.CENTER);

        sliderLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sliderLabel.setForeground(new java.awt.Color(255, 255, 255));
        sliderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sliderLabel.setText("Speed");
        innerRightPanel.add(sliderLabel, java.awt.BorderLayout.NORTH);

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
        // Set width based on window width.
        if (this.getWidth()<1100)
            elevator.setWidth(60);
        if(this.getWidth()>1100)
            elevator.setWidth(100);
    }//GEN-LAST:event_formComponentResized

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new ElevatorMain().setVisible(true);
            }
        });
    }
    
    public void updatePickupLabel(int floor, int num, String action)
    {
        statusLabel.setText(action + " ["+ num +"] passengers on floor ["+ floor +"]");
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
    private javax.swing.JButton goTo1;
    private javax.swing.JButton goTo2;
    private javax.swing.JButton goTo3;
    private javax.swing.JButton goTo4;
    private javax.swing.JButton goTo5;
    private javax.swing.JButton goTo6;
    private javax.swing.JButton goTo7;
    private javax.swing.JButton goTo8;
    private javax.swing.JButton goToCancel;
    private javax.swing.JFrame goToFrame;
    private javax.swing.JLabel goToLabel;
    private javax.swing.JPanel goToTopPanel;
    private javax.swing.JPanel innerCenterPanel;
    private javax.swing.JPanel innerLeftPanel;
    private javax.swing.JPanel innerRightPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton oneButton;
    private javax.swing.JButton quitButton;
    private javax.swing.JButton sevenButton;
    private javax.swing.JButton sixButton;
    private javax.swing.JSlider slider;
    private javax.swing.JLabel sliderLabel;
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
        
        Image img = d1.getImage();
        
        public void changeImage()
        {
            if (img == d1.getImage())
                img = d2.getImage();
            else if (img == d2.getImage())
                img = d3.getImage();
            else if (img == d3.getImage())
                img = d4.getImage();
            else if (img == d4.getImage())
                img = d5.getImage();
            else if (img == d5.getImage())
                img = d6.getImage();
            else if (img == d6.getImage())
                img = d7.getImage();
            else if (img == d7.getImage())
                img = d8.getImage();
            else
                img = d1.getImage();
        }
        
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
            elevator.setHeight((this.getHeight()/8)-10);
            
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
            g2.setColor(new Color(102,102,102));
            g2.drawRect(elevator.getX(), 
                        elevator.getY(), 
                        elevator.getWidth(), 
                        elevator.getHeight());
            
            g2.drawLine(elevator.getX()+(elevator.getWidth()/2),
                        elevator.getY()+1,
                        elevator.getX()+(elevator.getWidth()/2),
                        elevator.getY()+(elevator.getHeight())-2);
            
//            g2.drawImage(img, 10, 10, elevator.getWidth(), elevator.getHeight(), null);
//            changeImage();
            
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
        @Override
        public void actionPerformed(ActionEvent e)
        {
            elevator.update(pickUp,dropOff);
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
        Point p;
        public void popUpFrame()
        {
            goToFrame.setVisible(true);
            goToFrame.setEnabled(true);
            goToClicked = true;
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Reenable all buttons on popup.
            for (JButton b: goToFloors)
            {
                b.setEnabled(true);
                
            }
           if (e.getSource() == eightButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 8)
               {
                   // Open the destination floor pop up panel (new frame).
                   popUpFrame();
                   gtl.setSource(eightButton,8);
                   p = innerCenterPanel.getLocationOnScreen();
                   p.y = eightButton.getLocationOnScreen().y;
                   p.x = p.x +3;
                   goToFrame.setLocation(p);
                   goTo8.setEnabled(false);
               }
           }
           else if (e.getSource() == sevenButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 7)
               {
                   popUpFrame();
                   gtl.setSource(sevenButton,7);
                   p = innerCenterPanel.getLocationOnScreen();
                   p.y = sevenButton.getLocationOnScreen().y;
                   p.x = p.x +3;
                   goToFrame.setLocation(p);
                   goTo7.setEnabled(false);
               }
           }
           else if (e.getSource() == sixButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 6)
               {
                    popUpFrame();
                    gtl.setSource(sixButton,6);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = sixButton.getLocationOnScreen().y;
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo6.setEnabled(false);
               }
           }
           else if (e.getSource() == fiveButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 5)
               {
                    popUpFrame();
                    gtl.setSource(fiveButton,5);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = fiveButton.getLocationOnScreen().y;
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo5.setEnabled(false);
               }
           }
           else if (e.getSource() == fourButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 4)
               {
                    popUpFrame();
                    gtl.setSource(fourButton,4);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = fourButton.getLocationOnScreen().y-goToFrame.getHeight()+fourButton.getHeight();
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo4.setEnabled(false);
               }
           }
           else if (e.getSource() == threeButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 3)
               {
                    popUpFrame();
                    gtl.setSource(threeButton,3);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = threeButton.getLocationOnScreen().y-goToFrame.getHeight()+threeButton.getHeight();
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo3.setEnabled(false);
               }
           }
           else if (e.getSource() == twoButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 2)
               {
                    popUpFrame();
                    gtl.setSource(twoButton,2);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = twoButton.getLocationOnScreen().y-goToFrame.getHeight()+twoButton.getHeight();
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo2.setEnabled(false);
               }
           }
           else if (e.getSource() == oneButton)
           {
               if (!elevator.isStopped() || elevator.getCurrentFloor() != 1)
               {
                    popUpFrame();
                    gtl.setSource(oneButton,1);
                    p = innerCenterPanel.getLocationOnScreen();
                    p.y = oneButton.getLocationOnScreen().y-goToFrame.getHeight()+oneButton.getHeight();
                    p.x = p.x +3;
                    goToFrame.setLocation(p);
                    goTo1.setEnabled(false);
               }
           }
        }
    }
    
    // Combo Listener - subclass to listen and handle events generated by the 
    // combo box. Changes the number of active floors when an event is fired.
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
            for(JButton j: goToFloors)
            {
                j.setEnabled(true);
            }
            for (int i = floorsAvail; i<floors.size(); i++)
            {
                floors.get(i).setBackground(Color.black);
                floors.get(i).setEnabled(false);
                goToFloors.get(i).setEnabled(false);
            }
            
            //Stop timer and reset simulation.
            //Resets the elevator to the bottom floor.
            //Enables/disables the correct buttons.
            //Resets the pickup counters and button labels.
            timer1.stop();
            elevator.stopT2();
            elevator.setY((innerCenterPanel.getHeight())-elevator.getHeight()-5);
            stopButton.setBackground(dis);
            stopButton.setEnabled(false);
            
            startButton.setEnabled(true);
            startButton.setBackground(en);
            elevator.pushStop();
            elevator.stop();
            
            // Resets the pickup/dropOff counters.
            for (int i = 1; i<=pickUp.size();i++)
            {
                pickUp.put(i, 0);
            }
            for (int i = 1; i<=dropOff.size();i++)
            {
                dropOff.put(i, 0);
            }
            
            passengers.clear();
            elevator.clearAllLists();
            
            // Resets the button labels (pickup).
            int j = 1;
            for (JButton b: floors)
            {
                b.setText(""+j);
                b.setBackground(n);
                j++;
            }
            int k = 1;
            for (JButton b: goToFloors)
            {
                b.setText(""+k);
                k++;
            }
            
            statusLabel.setText("Standing by...");
            repaint();
        } 
    }
    
    // Slider Listener - listens for and reacts to events fired by the
    // JSlider on the right side of the frame. The speed of the animation
    // is determined by the location of the JSlider caret.
    class SliderListener implements ChangeListener
    {
        @Override
        public void stateChanged(ChangeEvent e)
        {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) 
            {
               delay = speed.get(source.getValue());
               timer1.setDelay(delay);
            }   
        }
    }
    
    // GoToButtonListener - Listens for and reacts to events fired by the buttons
    // on the popup GoTo frame. The source button and floor number is passed to 
    // this class so that when a GoTo button is pressed, the correct buttons
    // on the main frame can also be updated.
    class GoToButtonListener implements ActionListener
    {
        JButton source;
        int floor = 1;
        
        public void setSource(JButton source, int floor)
        {
            this.source = source;
            this.floor = floor;
        }
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            JButton j = (JButton)e.getSource();
            
            if (j == goToCancel)
            {
                goToFrame.setVisible(false);
            }
            else if (j == goTo8)
            {
                dropOff.put(8, dropOff.get(8)+1);
                j.setText("8 (" + dropOff.get(8) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,8));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo7)
            {
                dropOff.put(7, dropOff.get(7)+1);
                j.setText("7 (" + dropOff.get(7) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,7));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo6)
            {
                dropOff.put(6, dropOff.get(6)+1);
                j.setText("6 (" + dropOff.get(6) + ")");
                
                passengers.add(new Passenger(floor,6));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo5)
            {
                dropOff.put(5, dropOff.get(5)+1);
                j.setText("5 (" + dropOff.get(5) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,5));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo4)
            {
                dropOff.put(4, dropOff.get(4)+1);
                j.setText("4 (" + dropOff.get(4) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,4));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo3)
            {
                dropOff.put(3, dropOff.get(3)+1);
                j.setText("3 (" + dropOff.get(3) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,3));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo2)
            {
                dropOff.put(2, dropOff.get(2)+1);
                j.setText("2 (" + dropOff.get(2) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,2));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            else if (j == goTo1)
            {
                dropOff.put(1, dropOff.get(1)+1);
                j.setText("1 (" + dropOff.get(1) + ")");
                System.out.println("Drop off: " + dropOff);
                
                passengers.add(new Passenger(floor,1));
                
                pickUp.put(floor, pickUp.get(floor) + 1);
                source.setText(floor + " (in: " + pickUp.get(floor) + ", out: "+ elevator.tOut.get(floor).size() +")");
                source.setBackground(c);
            }
            
            goToFrame.setVisible(false);
            goToClicked = false;
            goToFrame.setEnabled(false);
            System.out.println("Pickup: " + pickUp);
            
            hoverTimer.stop();
        }
        
    }
    
    // Hover Listener - Listens for and reacts to events fired by the hover timer.
    // When the floor buttons are hovered over for 2 seconds (2000ms), the GoTo
    // frame pops up next to the button. The frame closes when the mouse leaves
    // the listener area, unless the button has been clicked.
    class HoverListener implements MouseListener
    {
        int hoverDelay = 2000;
        boolean done = false;
        Point p;
        JButton source;
        
        public HoverListener()
        {
            hoverTimer = new Timer(hoverDelay,new HoverTimerListener());
        }
        
        @Override public void mouseEntered(MouseEvent e)
        {
            source = (JButton)e.getSource();
            if (!goToClicked)
                hoverTimer.start();
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            if (!goToClicked)
            {
                goToFrame.setVisible(false);
                goToFrame.setEnabled(true);
                hoverTimer.stop();
                done = false;
            }
        }
        
        @Override public void mouseClicked(MouseEvent e){}
        @Override public void mousePressed(MouseEvent e){}
        @Override public void mouseReleased(MouseEvent e){}
        
        class HoverTimerListener implements ActionListener
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (done == false && !goToClicked)
                {
                    p = innerCenterPanel.getLocationOnScreen();
                    
                    if (source == eightButton || source == sevenButton  || source == sixButton || source == fiveButton)
                        p.y = source.getLocationOnScreen().y;
                    else
                        p.y = source.getLocationOnScreen().y-goToFrame.getHeight()+oneButton.getHeight();
                    
                    p.x = p.x +3;
                    
                    goToFrame.setLocation(p);
                    goToFrame.setEnabled(false);
                    goToFrame.setVisible(true);
                    done = true;
                }
                hoverTimer.stop();                   
            }
        }
    }
   
    // Focus Listener - Captures events fired when the window loses or gains focus.
    class GoToFocusListener implements FocusListener
    {
        @Override
        public void focusLost(FocusEvent e)
        {
            goToFrame.setEnabled(false);
            goToFrame.setVisible(false);
        }
        
        @Override public void focusGained(FocusEvent e){}
    }
    
    // Represents the bottom half (conaining number buttons) of the popup frame.
    class GoToBottomPanel extends JPanel
    {
        boolean dropOffAdded = false;
        
        public boolean dropOffAdded()
        {
            return dropOffAdded;
        }
        
        public void setDropOff(boolean b)
        {
            dropOffAdded = b;
        }
    }
}
