// Represents the drawable elevator within the innerCenterPanel.
// Holds dimension, location and other data.
package elevator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Elevator
{
    private int width, height;
    private int x, y, limitX;
    private int dif;
    private int currentFloor, upper;
    private int direction = 1;
    private ArrayList floors;
    private ArrayList<JButton> buttons;
    private Timer t, t2;
    private int delay = 1000;
    private int wait = 1;
    private boolean stop = false;
    
    //Testing callback
    private JPanel callback;
    
    Color n = new Color(102,102,102);

    public Elevator(int width, int height, int x, int y, Timer t, ArrayList a, JPanel callback)
    {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        dif = -4;
        currentFloor = 1;
        upper = 0;
        floors = new ArrayList<>();
        t2 = new Timer(delay, new T2Listener());
        this.t = t;
        buttons = a;
        this.callback = callback;
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
    
    // Method not finished yet!
    public HashMap update(HashMap m)
    {
        checkFloor();
        
        // Level 8.
        if (y+height == (int)floors.get(0)-3 && y > (int)floors.get(0)-height-7 && (int)m.get(8) > 0)
        {
            y = (int)(floors.get(0))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 8");
            setWait((int)m.get(8));
            m.put(8, 0);
            t2.start();
        }
        
        // Level 7.
        else if (y+height == (int)floors.get(1)-3 && y > (int)floors.get(1)-height-7 && (int)m.get(7) > 0)
        {
            y = (int)(floors.get(1))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 7");
            setWait((int)m.get(7));
            m.put(7, 0);
            t2.start();
        }
        
        // Level 6.
        else if (y+height == (int)floors.get(2)-3 && y > (int)floors.get(2)-height-7 && (int)m.get(6) > 0)
        {
            y = (int)(floors.get(2))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 6");
            setWait((int)m.get(6));
            m.put(6, 0);
            t2.start();
        }
        
        // Level 5.
        else if (y+height < (int)floors.get(3) && y > (int)floors.get(3)-height-5 && (int)m.get(5) > 0)
        {
            y = (int)(floors.get(3))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 5");
            setWait((int)m.get(5));
            m.put(5, 0);
            t2.start();
        }
        
        // Level 4.
        else if (y+height < (int)floors.get(4) && y > (int)floors.get(4)-height-5 && (int)m.get(4) > 0)
        {
            y = (int)(floors.get(4))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 4");
            setWait((int)m.get(4));
            m.put(4, 0);
            t2.start();
        }
        
        // Level 3.
        else if (y+height < (int)floors.get(5) && y > (int)floors.get(5)-height-5 && (int)m.get(3) > 0)
        {
            y = (int)(floors.get(5))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 3");
            setWait((int)m.get(3));
            m.put(3, 0);
            t2.start();
        }
        
        // Level 2.
        else if (y+height < (int)floors.get(6) && y > (int)floors.get(6)-height-5 && (int)m.get(2) > 0)
        {
            y = (int)(floors.get(6))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 2");
            setWait((int)m.get(2));
            m.put(2, 0);
            t2.start();
        }
        
        // Level 1.
        else if (y+height < (int)floors.get(7)-1 && y > (int)floors.get(7)-height-4 && (int)m.get(1) > 0)
        {
            y = (int)(floors.get(7))-height-3;
            stop = true;
            t.stop();
            System.out.println("Level 1");
            setWait((int)m.get(1));
            m.put(1, 0);
            t2.start();
        }
        
        
        // Updating
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
        
        return m;
    }
    
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
            //System.out.println("8");
            currentFloor = 8;
        }
        else if (y+height < (int)floors.get(1))
        {
            //System.out.println("7");
            currentFloor = 7;
        }
        else if (y+height < (int)floors.get(2))
        {
            //System.out.println("6");
            currentFloor = 6;
        }
        else if (y+height < (int)floors.get(3))
        {
            //System.out.println("5");
            currentFloor = 5;
        }
        else if (y+height < (int)floors.get(4))
        {
            //System.out.println("4");
            currentFloor = 4;
        }
        else if (y+height < (int)floors.get(5))
        {
            //System.out.println("3");
            currentFloor = 3;
        }
        else if (y+height < (int)floors.get(6))
        {
            //System.out.println("2");
            currentFloor = 2;
        }
        else if (y+height < (int)floors.get(7))
        {
            //System.out.println("1");
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
    
    class T2Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (wait > 1)
            {
                wait--;
                buttons.get(currentFloor-1).setText(currentFloor + " (" + wait + ")");
            }
            else
            {
                t2.stop();
                t.start();
                stop = false;
                buttons.get(currentFloor-1).setText(currentFloor+"");
                buttons.get(currentFloor-1).setBackground(n);
            }
        }
    }
}
