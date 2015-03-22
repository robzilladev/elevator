// Represents the drawable elevator within the innerCenterPanel.
// Holds dimension, location and other data.
package elevator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.swing.Timer;

public class Elevator
{
    private int width, height;
    private int x, y, limitX;
    private int dif;
    private int currentFloor, upper;
    private String direction;
    private ArrayList floors;
    private Timer t, t2;
    private int delay = 1000;
    private int wait = 2;

    public Elevator(int width, int height, int x, int y, Timer t)
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
    public void update(HashMap m) throws InterruptedException
    {
        checkFloor();
        
        if (y+height == (int)floors.get(0)-3 && y > (int)floors.get(0)-height-7 && (int)m.get(8) > 0)
        {
            t.stop();
            System.out.println("Level 8");
            t.start();
        }
        else if (y+height == (int)floors.get(1)-3 && y > (int)floors.get(1)-height-7 && (int)m.get(7) > 0)
        {
            t.stop();
            System.out.println("Level 7");
            t.start();
        }
        else if (y+height == (int)floors.get(2)-3 && y > (int)floors.get(2)-height-7 && (int)m.get(6) > 0)
        {
            t.stop();
            System.out.println("Level 6");
            t.start();
        }
        else if (y+height == (int)floors.get(3)-3 && y > (int)floors.get(3)-height-7 && (int)m.get(5) > 0)
        {
            t.stop();
            System.out.println("Level 5");
            t2.start();
        }
        else if (y+height == (int)floors.get(4)-3 && y > (int)floors.get(3)-height-7 && (int)m.get(4) > 0)
        {
            t.stop();
            System.out.println((int)m.get(4));
            t2.start();
        }
        else if (y+height == (int)floors.get(5)-3 && y > (int)floors.get(5)-height-7 && (int)m.get(3) > 0)
        {
            t.stop();
            System.out.println((int)m.get(3));
            t.start();
        }
        else if (y+height == (int)floors.get(6)-3 && y > (int)floors.get(6)-height-7 && (int)m.get(2) > 0)
        {
            t.stop();
            System.out.println("Level 2");
            t.start();
        }
        else if (y+height < (int)floors.get(7)-1 && y > (int)floors.get(7)-height-4 && (int)m.get(1) > 0)
        {
            t.stop();
            System.out.println("Level 1");
            t.start();
        }
        
        if (y < upper+4)
            dif = 4;
        else if (y > (limitX - height - 3))
            dif = -4;
        y = y + dif;
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
    
    class T2Listener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (wait > 0)
                wait--;
            else
            {
                t2.stop();
                t.start();
                wait = 2;
            }
        }
        
    }
}
