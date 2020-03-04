import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class GrantRecipients {
    private Set<String> states = new HashSet<>();
    private List<Institution> institutions = new ArrayList<>();
    private Map<String, List<Institution>> pellGrant = new TreeMap<>();
    private Connection c;
    private final String URL = "jdbc:sqlite:pellgrant.db";

    public GrantRecipients(String linkTSV) throws Exception
    {
        URL url = new URL(linkTSV);
        InputStream is = url.openStream();
        String str = IOUtils.toString(is, StandardCharsets.UTF_8);
        str = str.replace("\"","");
        str = str.replace(",","");
        str = str.replace("$","");

        Scanner scan = new Scanner(str);

        //skip first 5 lines
        for(int i = 0; i < 5; i++) {
            scan.nextLine();
        }

        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] data = line.split("\t");
            String ope_id = data[0];
            String inst_name = data[1];
            String inst_city = data[2];
            String inst_state = data[3];
            String type = data[4];
            String totalGrant = data[5];
            String numStudents = data[6];

            if (!ope_id.equals("")) {
                Institution inst = new Institution(ope_id, inst_name, inst_city, inst_state, type, totalGrant, numStudents);
                institutions.add(inst);
                states.add(inst_state); //takes the state name --> adds unique state to states
            }
        }

    }


    public void parseToJSON() throws Exception{
        File file = new File("pell.json");
        FileUtils.writeStringToFile(file,"{", "UTF-8", true);
        int j=0;
        for(Map.Entry<String,List<Institution>> entry : getPellGrant().entrySet()) {
            FileUtils.writeStringToFile(file, entry.getKey() + "", "UTF-8", true);
            int i = 0;
            for(Institution in : entry.getValue()) {
                if(i != 0){
                    FileUtils.writeStringToFile(file,",", "UTF-8", true);
                }
                FileUtils.writeStringToFile(file, in + "", "UTF-8", true);
                i++;
            }
            j++;
            if(j != getPellGrant().size()){
                //add ], after the end of an state
                FileUtils.writeStringToFile(file,"\n  ],", "UTF-8", true);
            } else {
                //don't add a comma after ] if it is the last state
                FileUtils.writeStringToFile(file,"\n  ]", "UTF-8", true);
            }
        }
        FileUtils.writeStringToFile(file,"\n}", "UTF-8", true);
    }


    public void addToDatabaseFromInstitutions() {
        try {
            c = DriverManager.getConnection(URL);
            Statement s = c.createStatement();
            for (Institution inst : getInstitutions()) {
                String sql = "insert into institutions values " +
                        "(" + inst.getOpe_id() + ", " +
                        "\"" + inst.getInst_name() + "\"" + "," +
                        "\"" + inst.getInst_city() + "\"" + "," +
                        "\"" + inst.getInst_state() + "\"" + "," +
                        "\"" + inst.getType() + "\"" + "," +
                        inst.getTotalGrant() + "," + inst.getNumStudents() + ")";
                s.executeUpdate(sql);
                s.close();
            }
            System.out.println("-----Added stuff from institutions array list to Database-----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToDatabaseFromJSON(String jsonFile) throws Exception {
        File file = new File(jsonFile);
        FileReader read = new FileReader(file.getAbsoluteFile());
        JSONParser parser = new JSONParser();

        try {
            c = DriverManager.getConnection(URL);
            Statement s = c.createStatement();

            Object pellgrant = parser.parse(read);
            JSONObject jsonPellGrant = (JSONObject) pellgrant;
            for (Object obj : jsonPellGrant.keySet()) {
                JSONArray indexOfState = (JSONArray) jsonPellGrant.get(obj.toString());
//                System.out.println(obj.toString() + ":");
                for (Object institution: indexOfState) {
                    JSONObject theInstitution = (JSONObject) institution;
                    int ope_id = Integer.parseInt(theInstitution.get("ope_id").toString());
                    String inst_name = theInstitution.get("name").toString();
                    String inst_city = theInstitution.get("city").toString();
                    String inst_state = theInstitution.get("state").toString();
                    String type = theInstitution.get("type").toString();
                    double totalGrant = Double.parseDouble(theInstitution.get("totalGrant").toString());
                    int numStudents = Integer.parseInt(theInstitution.get("numStudents").toString());

                    String sql = "insert into institutions values " +
                            "(" + ope_id + ", " +
                            "\"" + inst_name + "\"" + "," +
                            "\"" + inst_city + "\"" + "," +
                            "\"" + inst_state + "\"" + "," +
                            "\"" + type + "\"" + "," +
                            totalGrant + "," + numStudents + ")";
                    s.executeUpdate(sql);
                    s.close();

                }

            }
            System.out.println("-----Added stuff from json to Database-----");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Institution> getTopTenHighestAverageAward(){
        System.out.println("----Top Ten Highest Average Award----");
//        Comparator<Institution> rankOrder = (Institution o1, Institution o2) -> o1.getAverage() - o2.getAverage();
        return getTopTenInstitutionsReverseSorted(Comparator.comparingInt(Institution::getAverage));
    }


    public ArrayList<Institution> getTopTenLowestAverageAward(){
        System.out.println("\n----Top Ten Lowest Average Award----");
        return getTopTenInstitutionsSorted(Comparator.comparingInt(Institution::getAverage));
    }

    public ArrayList<Institution> getTopTenMostNumStu(){
        System.out.println("\n----Top Ten Most Number Of Students----");
        return getTopTenInstitutionsReverseSorted(Comparator.comparingInt(Institution::getNumStudents));
    }

    private ArrayList<Institution> getTopTenInstitutionsReverseSorted(Comparator<Institution> institutionComparator) {
        getInstitutions().sort(institutionComparator);
        Collections.reverse(getInstitutions());
        return getTopTenInstitutions(institutionComparator);
    }
    private ArrayList<Institution> getTopTenInstitutionsSorted(Comparator<Institution> institutionComparator) {
        getInstitutions().sort(institutionComparator);
        return getTopTenInstitutions(institutionComparator);
    }

    private ArrayList<Institution> getTopTenInstitutions(Comparator<Institution> institutionComparator) {
        ArrayList<Institution> topTenInstitutions = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            topTenInstitutions.add(getInstitutions().get(i));
        }
        return topTenInstitutions;
    }

    public List<Institution> getInstitutions() {
        return this.institutions;
    }

    public Set<String> getStates() {
        return this.states;
    }

    public Map<String, List<Institution>> getPellGrant() {
        for (String s : getStates()) {
            List<Institution> institutionsByState = new ArrayList<>();
            for (Institution institution : getInstitutions()) {
                if (institution.getInst_state().equals(s)) {
                    institutionsByState.add(institution);
                }
            }
            //sort all the Institutions in a state alphabetically
            Collections.sort(institutionsByState);
            pellGrant.put("\n  \""+s+"\": [", institutionsByState);

        }
        return pellGrant;
    }


    public static void main(String[] arg) throws Exception {
        String link = "https://gist.githubusercontent.com/tacksoo/b32d630c2e5c7dcd8ebeb2fc67e9c7ae/raw/72b6bae956dfb3eeb66fa277f63ce8acb784fd01/pell.tsv";
        GrantRecipients r = new GrantRecipients(link);
//        r.parseToJSON();
        r.addToDatabaseFromInstitutions();
//        r.addToDatabaseFromJSON("pell.json");


        //Adding the top ten institutions to the text file
//        PrintStream stream = new PrintStream("pell-answers.txt");
//        System.setOut(stream);
//        System.out.println(r.getTopTenHighestAverageAward());
//        System.out.println(r.getTopTenLowestAverageAward());
//        System.out.println(r.getTopTenMostNumStu());

    }

}
