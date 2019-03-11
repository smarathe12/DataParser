import java.util.ArrayList;

public class DataManager {
    public ArrayList<States> getStates() {
        return states;
    }

    public void setStates(ArrayList<States> states) {
        this.states = states;
    }

    public DataManager(ArrayList<States> states) {
        this.states = states;
    }

    private ArrayList <States> states;
}
