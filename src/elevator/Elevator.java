// Represents the drawable elevator within the innerCenterPanel.
// Holds dimension, location and other data.
package elevator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.Timer;

public class Elevator
{
    private int width, height, x, y, limitX, dif, currentFloor, upper;
    private int direction = -1;
    
    // Lists from ElevatorMain
    private ArrayList floors;
    private ArrayList<JButton> buttons;
    private ArrayList<JButton> goToButtons;
   
    // Testing arrays for each floor
    private ArrayList<Passenger> in8 = new ArrayList<>(); private ArrayList<Passenger> out8 = new ArrayList<>();
    private ArrayList<Passenger> in7 = new ArrayList<>(); private ArrayList<Passenger> out7 = new ArrayList<>();
    private ArrayList<Passenger> in6 = new ArrayList<>(); private ArrayList<Passenger> out6 = new ArrayList<>();
    private ArrayList<Passenger> in5 = new ArrayList<>(); private ArrayList<Passenger> out5 = new ArrayList<>();
    private ArrayList<Passenger> in4 = new ArrayList<>(); private ArrayList<Passenger> out4 = new ArrayList<>();
    private ArrayList<Passenger> in3 = new ArrayList<>(); private ArrayList<Passenger> out3 = new ArrayList<>();
    private ArrayList<Passenger> in2 = new ArrayList<>(); private ArrayList<Passenger> out2 = new ArrayList<>();
    private ArrayList<Passenger> in1 = new ArrayList<>(); private ArrayList<Passenger> out1 = new ArrayList<>();
    
    HashMap<Integer, ArrayList> tIn = new HashMap<>();
    HashMap<Integer, ArrayList> tOut = new HashMap<>();
    
    // Timer variables.
    private Timer t, t2;
    private int delay = 1000;
    private int waitIn = 0;
    private int waitOut = 0;
    private boolean stop = false;
    private boolean pushStop = false;
    
    // Reference to ElevatorMain frame for access to components (callback).
    private ElevatorMain callback;
    
    // Button not clicked
    private Color n = new Color(102,102,102);

    public Elevator(int width, int height, int x, int y, Timer t, ArrayList buttons, ElevatorMain callback, ArrayList goTo)
    {
        // Set up dimensions of elevator.
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        // References to elements in ElevatorMain
        this.t = t;
        this.buttons = buttons;
        goToButtons = goTo;
        this.callback = callback;
        
        dif = -4;
        currentFloor = 1;
        upper = 0;
        floors = new ArrayList<>();
        
        // Timer for passenger exit delay.
        t2 = new Timer(delay, new T2Listener());
        t2.setInitialDelay(0);
        
        tIn.put(1,in1);tIn.put(2,in2);tIn.put(3,in3);tIn.put(4,in4);tIn.put(5,in5);tIn.put(6,in6);tIn.put(7,in7);tIn.put(8,in8);
        tOut.put(1,out1);tOut.put(2,out2);tOut.put(3,out3);tOut.put(4,out4);tOut.put(5,out5);tOut.put(6,out6);tOut.put(7,out7);tOut.put(8,out8);
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getX()
    {
        return x;
    }

    public boolean isStopped()
    {
        return stop;
    }
    
    public void setLimitX(int limitX)
    {
        this.limitX = limitX;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
    
    public int getDirection()
    {
        return this.direction;
    }
    
    public void stopT2()
    {
        t2.stop();
        waitIn = 0;
        waitOut = 0;
        
    }
    
    // Sets the upper bound of the simulation panel.
    // For example, if level 4 is set as the upper limit, then the elevator will
    // not move past the pixels allocated to level 4.
    public void setUpper(int height, int i)
    {
        switch (i){
            case 2:
                upper = (height/8)*6;
                break;
            case 3:
                upper = (height/8)*5;
                break;
            case 4:
                upper = (height/8)*4;
                break;
            case 5:
                upper = (height/8)*3;
                break;
            case 6:
                upper = (height/8)*2;
                break;
            case 7:
                upper = (height/8);
                break;
            case 8:
                upper = 0;
                break;
            default: System.out.println("Upper error");
        }
    }
    
    // Updates the position of the moving elevator.
    // Write some info here *****
    public HashMap update(HashMap pickupMap, HashMap dropOffMap)
    {
        checkFloor();
        
        for (Passenger p : ElevatorMain.passengers)
        {
            if (p.getFloorFrom() == 8 && !p.added)
            {
                in8.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 7 && !p.added)
            {
                in7.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 6 && !p.added)
            {
                in6.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 5 && !p.added)
            {
                in5.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 4 && !p.added)
            {
                in4.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 3 && !p.added)
            {
                in3.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 2 && !p.added)
            {
                in2.add(p);
                p.setAdded(true);
            }
            else if (p.getFloorFrom() == 1 && !p.added)
            {
                in1.add(p);
                p.setAdded(true);
            }
        }
        
        // Level 8.
        if (y+height < (int)floors.get(0) && y > (int)floors.get(0)-height-5)
        {
            if (out8.size() > 0)
            {
                y = (int)(floors.get(0))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out8.size());
                out8.clear();
            }
            
            if (in8.size() > 0)
            {
                y = (int)(floors.get(0))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in8.size());
                
                if (!in8.isEmpty())
                {
                    for (Passenger p: in8)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in8.clear();
                
            }
            
            t2.start();
            
            if (out8.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(8, 0);
            dropOffMap.put(8,0);
        }
        
        // Level 7.
        else if (y+height < (int)floors.get(1) && y > (int)floors.get(1)-height-5)
        {
            if (out7.size() > 0)
            {
                y = (int)(floors.get(1))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out7.size());
                out7.clear();
            }
            
            if (in7.size() > 0)
            {
                y = (int)(floors.get(1))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in7.size());
                
                if (!in7.isEmpty())
                {
                    for (Passenger p: in7)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in7.clear();
            }
            
            t2.start();
            
            if (out7.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(7, 0);
            dropOffMap.put(7,0);
        }
        
        // Level 6. - seems to be working.
        else if (y+height < (int)floors.get(2) && y > (int)floors.get(2)-height-5)
        {
            if (out6.size() > 0)
            {
                y = (int)(floors.get(2))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out6.size());
                out6.clear();
            }
            
            if (in6.size() > 0)
            {
                y = (int)(floors.get(2))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in6.size());
                
                if (!in6.isEmpty())
                {
                    for (Passenger p: in6)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in6.clear();
            }
            
            t2.start();
            
            if (out6.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(6, 0);
            dropOffMap.put(6,0);
        }
        
//        // Level 5.
        else if (y+height < (int)floors.get(3) && y > (int)floors.get(3)-height-5)
        {
            if (out5.size()> 0)
            {
                y = (int)(floors.get(3))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out5.size());
                out5.clear();
                
            }
            
            if (in5.size() > 0)
            {
                y = (int)(floors.get(3))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in5.size());
                
                if (!in5.isEmpty())
                {
                    for (Passenger p: in5)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in5.clear();
            }
            
            t2.start();
            
            if (out5.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(5, 0);
            dropOffMap.put(5,0);
        }
        
//        // Level 4.
        else if (y+height < (int)floors.get(4) && y > (int)floors.get(4)-height-5)
        {
            if (out4.size()> 0)
            {
                y = (int)(floors.get(4))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out4.size());
                out4.clear();
                
            }
            
            if (in4.size() > 0)
            {
                y = (int)(floors.get(4))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in4.size());
                
                if (!in4.isEmpty())
                {
                    for (Passenger p: in4)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in4.clear();
            }
            
            t2.start();
            
            if (in4.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(4, 0);
            dropOffMap.put(4, 0);
        }
        
        // Level 3.
        else if (y+height < (int)floors.get(5) && y > (int)floors.get(5)-height-5)
        {
            if (out3.size()> 0)
            {
                y = (int)(floors.get(5))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out3.size());
                out3.clear();
                
            }
            
            if (in3.size() > 0)
            {
                y = (int)(floors.get(5))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in3.size());
                
                if (!in3.isEmpty())
                {
                    for (Passenger p: in3)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in3.clear();
            }
            
            t2.start();
            
            if (out3.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(3, 0);
            dropOffMap.put(3,0);
        }
        
//        // Level 2.
        else if (y+height < (int)floors.get(6) && y > (int)floors.get(6)-height-5)
        {
            if (out2.size()> 0)
            {
                y = (int)(floors.get(6))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out2.size());
                out2.clear();
                
            }
            
            if (in2.size() > 0)
            {
                y = (int)(floors.get(6))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in2.size());
                
                if (!in2.isEmpty())
                {
                    for (Passenger p: in2)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in2.clear();
            }
            
            t2.start();
            
            if (out2.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(2, 0);
            dropOffMap.put(2,0);
        }
        
//        // Level 1.
        else if (y+height < (int)floors.get(7) && y > (int)floors.get(7)-height-5)
        {
            if (out1.size()> 0)
            {
                y = (int)(floors.get(7))-height-3;
                stop = true;
                t.stop();
                
                setWaitOut(out1.size());
                out1.clear();
                
            }
            
            if (in1.size() > 0)
            {
                y = (int)(floors.get(7))-height-3;
                stop = true;
                t.stop();
                setWaitIn(in1.size());
                
                if (!in1.isEmpty())
                {
                    for (Passenger p: in1)
                    {
                        tOut.get(p.getPreDropOff()).add(p);
                    }
                }
                in1.clear();
            }
            
            t2.start();
            
            if (out1.size() > 1)
                ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
            else
                ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(1, 0);
            dropOffMap.put(1,0);
        }
        
        // Updating
        // Increment/decrement y coordinate respectively.
        if (stop != true)
        {
            if (y < upper+4)
            {
                dif = 4;
                direction = 1;
            }
            else if (y > (limitX - height - 3))
            {
                dif = -4;
                direction = -1;
            }
            y = y + dif;
        }
        return pickupMap;
    }
    
    public boolean stopped()
    {
        return stop;
    }
    
    // Stops timer from restarting if stop button is pushed during passenger pickup.
    public boolean pushStop()
    {
        pushStop = true;
        return pushStop;
    }
    
    // Resets stop button push boolean.
    public boolean pushStart()
    {
        pushStop = false;
        return pushStop;
    }
    
    public void stop()
    {
        stop = false;
    }
    
    // Return the number of passengers still to be dropped off at the given floor.
    public int anyToDropOffAt(int floor)
    {
        int num = 0;
        for (Passenger p: ElevatorMain.passengers)
        {
            if (p.dropOff == floor && p.droppedOff != true)
                num++;
        }
        return num;
    }
    
    // Return the number of passengers still to be picked up at the given floor.
    public int anyToPickUpAt(int floor)
    {
        int num = 0;
        for (Passenger p: ElevatorMain.passengers)
        {
            if (p.floorFrom == floor && p.pickedUp != true)
                num++;
        }
        return num;
    }
    
    // Convert passengers to have an actual drop off floor ONCE THEY HAVE BEEN 
    // PICKED UP.
    public void convertPassengers(int floor)
    {
        for (Passenger p: ElevatorMain.passengers)
        {
            if (p.floorFrom == floor)
                p.convertDropOff();
        }
    }
    
    // Clears all the lists
    public void clearAllLists()
    {
        for(int i = 1; i<9; i++)
        {
            tIn.get(i).clear();
            tOut.get(i).clear();
        }
    }
    
    public HashMap getTOut()
    {
        return tOut;
    }
    
    public HashMap getTIn()
    {
        return tIn;
    }
    
    // Sets up individual floor boundaries relative to the current size of the panel.
    // Adds each floor to the floors list.
    // Maintains a variable for the floor the elevator is currently passing/on.
    public int checkFloor()
    {
        floors.clear();
        int step = 0;
        for (int i = 0; i < 8; i++)
        {
            step = step + (limitX/8);
            floors.add(step);
        }
        
        if (y+height < (int)floors.get(0))
        {
            currentFloor = 8;
        }
        else if (y+height < (int)floors.get(1))
        {
            currentFloor = 7;
        }
        else if (y+height < (int)floors.get(2))
        {
            currentFloor = 6;
        }
        else if (y+height < (int)floors.get(3))
        {
            currentFloor = 5;
        }
        else if (y+height < (int)floors.get(4))
        {
            currentFloor = 4;
        }
        else if (y+height < (int)floors.get(5))
        {
            currentFloor = 3;
        }
        else if (y+height < (int)floors.get(6))
        {
            currentFloor = 2;
        }
        else if (y+height < (int)floors.get(7))
        {
            currentFloor = 1;
        }
        return currentFloor;
    }
    
    public int getCurrentFloor()
    {
        return currentFloor;
    }
    
    public void setWait(int i, int j)
    {
        waitIn = i;
        waitOut = j;
    }
    
    public void setWaitIn(int i)
    {
        waitIn = i;
    }
    
    public void setWaitOut(int i)
    {
        waitOut = i;
    }
    
    // Timer for picking up passengers.
    // Events fire proportionately to the set wait time.
    class T2Listener implements ActionListener
    {
        boolean readyToStop = false;
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!pushStop)
            {
                if (waitOut > 0 || waitIn > 0)
                {
                    if (waitOut > 0)
                    {
                        System.out.println("Out: " +waitOut);
                        
                        ElevatorMain.statusLabel.setText("Dropping off ["+ waitOut +"] passengers on floor ["+ currentFloor +"]");
                        buttons.get(currentFloor-1).setText(currentFloor +" (in: " + waitIn + ", out: "+waitOut+")");
                        goToButtons.get(currentFloor-1).setText(""+currentFloor+" ("+waitOut+")");
                        waitOut--;
                        
                    }
                    else if (waitIn > 0)
                    {
                        System.out.println("In: "+waitIn);
                        
                        ElevatorMain.statusLabel.setText("Picking up ["+ waitIn +"] passengers on floor ["+ currentFloor +"]");
                        buttons.get(currentFloor-1).setText(currentFloor +" (in: " + waitIn + ", out: "+waitOut+")");
                        waitIn--;
                    }
                }
                else if (waitOut == 0 || waitIn == 0)
                {
                    buttons.get(currentFloor-1).setText(currentFloor +"");
                    buttons.get(currentFloor-1).setBackground(n);
                    
                    readyToStop = true;
                    
                    for (int i = 0; i<8; i++)
                    {
                        if (tIn.get(i+1).size()>0 || tOut.get(i+1).size()>0)
                            buttons.get(i).setText(i+1 + " (in: " + tIn.get(i+1).size() + ", out: "+ tOut.get(i+1).size() +")");
                    }
                    
                }

                if (readyToStop)
                {
                    t2.stop();
                    if (!pushStop)
                        t.start();
                    stop = false;
                    readyToStop = false;
                    
                    goToButtons.get(currentFloor-1).setText(""+currentFloor);
                    if (!pushStop)
                        t.start();
                    waitIn = 0;
                    waitOut = 0;
                }
            }
        }
    }
}