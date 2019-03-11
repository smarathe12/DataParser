import java.util.ArrayList;

public class States {
    private String name;
    private ArrayList <County> counties;

    public States(String name, ArrayList<County> counties) {
        this.name = name;
        counties = new ArrayList<>();
    }

    public void addCounty(County c){
        this.counties.add(c);
    }

    public void removeCounty(County c){
        this.counties.remove(c);
    }

    public County removeCounty(int index){
        return this.counties.remove(index);
    }

    public ArrayList<County> getCounties() {
        return counties;
    }

    public void setCounties(ArrayList<County> counties) {
        this.counties = counties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
