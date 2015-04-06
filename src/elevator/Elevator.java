// Represents the drawable elevator within the innerCenterPanel.
// Holds dimension, location and other data.
package elevator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Elevator
{
    private int width, height, x, y, limitX, dif, currentFloor, upper;
    private int direction = -1;
    
    // Lists from ElevatorMain
    private ArrayList floors;
    private ArrayList<JButton> buttons;
    
    // Timer variables.
    private Timer t, t2;
    private int delay = 1000;
    private int wait = 1;
    private boolean stop = false;
    private boolean pushStop = false;
    
    //Testing callback
    private ElevatorMain callback;
    
    // Button not clicked
    Color n = new Color(102,102,102);

    public Elevator(int width, int height, int x, int y, Timer t, ArrayList buttons, ElevatorMain callback)
    {
        // Set up dimensions of elevator.
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        
        // References to elements in ElevatorMain
        this.t = t;
        this.buttons = buttons;
        this.callback = callback;
        
        dif = -4;
        currentFloor = 1;
        upper = 0;
        floors = new ArrayList<>();
        
        // Timer for passenger exit delay.
        t2 = new Timer(delay, new T2Listener());
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
    // Checks the position of the elevator against the boundaries of each floor
    // and if there is anyone to pick up on that floor. If the list containing
    // pickups is not empty on a floor, the main timer stops and another timer is
    // started, which counts down proportionately to the number in the list.
    // FLOORS arraylist starts at 0 for floor 8, and 7 for floor 1. (Take the required
    // floor from 8 to get the index)...
    // Eg. Floor 6 is at floors.get(2) .. Floor 3 is at floors.get(5) and so on...
    // PICKUPMAP hashmap elements are identified by the int of the required floor.
    // Eg. pickupMap.get(8) for floor 8 ... pickupMap.get(3) for floor 3 and so on...
    public HashMap update(HashMap pickupMap)
    {
        checkFloor();
        
        // Level 8.
        if (y+height < (int)floors.get(0) && y > (int)floors.get(0)-height-5 && (int)pickupMap.get(8) > 0)
        {
            y = (int)(floors.get(0))-height-3; // Snap the elevator to the right pixels.
            stop = true; 
            t.stop(); // Stop main animation timer.
            setWait((int)pickupMap.get(8)); // Set the wait time (num passengers) for the pickup timer.
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(8, 0); // Reset pickup to 0.
            t2.start(); // Start the pickup timer.
        }
        
        // Level 7.
        else if (y+height < (int)floors.get(1) && y > (int)floors.get(1)-height-5 && (int)pickupMap.get(7) > 0)
        {
            y = (int)(floors.get(1))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(7));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(7, 0);
            t2.start();
        }
        
        // Level 6.
        else if (y+height < (int)floors.get(2) && y > (int)floors.get(2)-height-5 && (int)pickupMap.get(6) > 0)
        {
            y = (int)(floors.get(2))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(6));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(6, 0);
            t2.start();
        }
        
        // Level 5.
        else if (y+height < (int)floors.get(3) && y > (int)floors.get(3)-height-5 && (int)pickupMap.get(5) > 0)
        {
            y = (int)(floors.get(3))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(5));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(5, 0);
            t2.start();
        }
        
        // Level 4.
        else if (y+height < (int)floors.get(4) && y > (int)floors.get(4)-height-5 && (int)pickupMap.get(4) > 0)
        {
            y = (int)(floors.get(4))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(4));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(4, 0);
            t2.start();
        }
        
        // Level 3.
        else if (y+height < (int)floors.get(5) && y > (int)floors.get(5)-height-5 && (int)pickupMap.get(3) > 0)
        {
            y = (int)(floors.get(5))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(3));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(3, 0);
            t2.start();
        }
        
        // Level 2.
        else if (y+height < (int)floors.get(6) && y > (int)floors.get(6)-height-5 && (int)pickupMap.get(2) > 0)
        {
            y = (int)(floors.get(6))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(2));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(2, 0);
            t2.start();
        }
        
        // Level 1.
        else if (y+height < (int)floors.get(7) && y > (int)floors.get(7)-height-5 && (int)pickupMap.get(1) > 0)
        {
            y = (int)(floors.get(7))-height-3;
            stop = true;
            t.stop();
            setWait((int)pickupMap.get(1));
            ElevatorMain.statusLabel.setText("Picking up ["+ wait +"] passengers on floor ["+ currentFloor +"]");
            pickupMap.put(1, 0);
            t2.start();
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
    
    public void setWait(int i)
    {
        wait = i;
    }
    
    // Timer for picking up passengers.
    // Events fire proportionately to the set wait time.
    class T2Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (!pushStop)
            {
                callback.updatePickupLabel(currentFloor,wait);
                if (wait > 1)
                {
                    wait--;
                    buttons.get(currentFloor-1).setText(currentFloor + " (" + wait + ")");
                    callback.updatePickupLabel(currentFloor,wait);
                }
                else
                {
                    t2.stop();
                    if (!pushStop)
                        t.start();
                    stop = false;
                    buttons.get(currentFloor-1).setText(currentFloor+"");
                    buttons.get(currentFloor-1).setBackground(n);
                }
            }
        }
    }
}
