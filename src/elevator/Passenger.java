package elevator;

public class Passenger
{
    int floorFrom, dropOff, preDropOff,seqNo;
    boolean pickedUp = false;
    boolean droppedOff = false;
    static int counter = 0;

    public Passenger(int floorFrom, int preDropOff)
    {
        this.floorFrom = floorFrom;
        this.preDropOff = preDropOff;
        counter++;
        seqNo = counter;
    }

    public int getFloorFrom()
    {
        return floorFrom;
    }

    public int getDropOff()
    {
        return dropOff;
    }
    
    public int getPreDropOff()
    {
        return preDropOff;
    }
    
    public void convertDropOff()
    {
        dropOff = preDropOff;
    }
    
    public void setPickedUp()
    {
        pickedUp = true;
    }
    
    public void setDroppedOff()
    {
        droppedOff = true;
    }
    
    @Override
    public String toString()
    {
        return "Floor from: " + floorFrom + " / Pre dropoff: " + preDropOff + " / Actual dropoff: " + dropOff + "/ SeqNo: " + seqNo;
    }
    
    
}

