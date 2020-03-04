import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Institution implements Comparable<Institution> {
    private int ope_id;
    private String inst_name = "";
    private String inst_city = "";
    private String inst_state = "";
    private String type = "";
    private double totalGrant;
    private int numStudents;


    public Institution(int ope_id, String inst_name,String inst_city,String inst_state, String type,
                       double totalGrant,int numStudents) {
        this.ope_id = ope_id;
        this.inst_name = inst_name;
        this.inst_city = inst_city;
        this.inst_state = inst_state;
        this.type = type;
        this.totalGrant = totalGrant;
        this.numStudents = numStudents;
    }

    public Institution(String ope_id, String inst_name,String inst_city,String inst_state, String type,
                       String totalGrant,String numStudents) {
        this.ope_id = Integer.parseInt(ope_id);
        this.inst_name = inst_name;
        this.inst_city = inst_city;
        this.inst_state = inst_state;
        this.type = type;
        this.totalGrant = (Double.parseDouble(totalGrant));
        this.numStudents = Integer.parseInt(numStudents);
    }


    public int getOpe_id() {
        return ope_id;
    }

    public void setOpe_id(String ope_id) {
        this.ope_id = Integer.parseInt(ope_id);
    }

    public void setOpe_id(int ope_id) {
        this.ope_id = ope_id;
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public String getInst_city() {
        return inst_city;
    }

    public void setInst_city(String inst_city) {
        this.inst_city = inst_city;
    }

    public String getInst_state() {
        return inst_state;
    }

    public void setInst_state(String inst_state) {
        this.inst_state = inst_state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalGrant() {
        return totalGrant;
    }

    public void setTotalGrant(String totalGrant) {
        this.totalGrant = Double.parseDouble(totalGrant);
    }
    public void setTotalGrant(double totalGrant) {
        this.totalGrant = totalGrant;
    }

    public int getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(String numStudents) {
        this.numStudents = Integer.parseInt(numStudents);
    }
    public void setNumStudents(int numStudents) {
        this.numStudents = numStudents;
    }

    public int getAverage() {
        return (int)Math.round(totalGrant/numStudents);
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.0");

        return ("\n    {" +
                "\n      \"ope_id\": \"" + getOpe_id() + "\"" +
                ",\n      \"name\": \"" + getInst_name() + "\"" +
                ",\n      \"city\": \"" + getInst_city() + "\""  +
                ",\n      \"state\": \"" + getInst_state() + "\"" +
                ",\n      \"type\": \"" + getType() + "\"" +
                ",\n      \"totalGrant\": " + (int)getTotalGrant() +
                ",\n      \"numStudents\": " + getNumStudents() +
                ",\n      \"averageAmount\": " + formatter.format(getAverage()) +
                "\n    "+'}');
    }


//    @Override
//    public String toString() {
//        return "Institution{" +
//                "ope_id=" + ope_id +
//                ", inst_name='" + inst_name + '\'' +
//                ", inst_city='" + inst_city + '\'' +
//                ", inst_state='" + inst_state + '\'' +
//                ", type='" + type + '\'' +
//                ", totalGrant=" + totalGrant +
//                ", numStudents=" + numStudents +
//                '}';
//    }

    @Override
    public int compareTo(Institution institution) {
        return this.getInst_name().compareTo(institution.getInst_name());
    }
}
