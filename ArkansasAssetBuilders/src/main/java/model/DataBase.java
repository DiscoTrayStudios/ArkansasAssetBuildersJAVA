package model;

import SQLite.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class DataBase {

    // Regex code from: https://codeahoy.com/q/11/check-if-a-string-is-numeric-in-java
    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    /**
     * Check if a String is a number.
     * Utility function for checking a String to insert as an integer into the
     * SQLite database.
     * @param str Input string
     * @return boolean determining if the input string is an integer.
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        return pattern.matcher(str).matches();
    }

    /*
     * ==================================================================================
     * CLIENT FUNCTIONS
     * ==================================================================================
     */

    /**
     * Insert a client into the Client table of the database.
     * Inserts a client if the client associated with the clientID
     * does not already exist, otherwise the client with the associated
     * clientID is updated.
     * @param clientData Properties associated with the client.
     * @param clientID ID of the client.
     */
    public static void insertClient(HashMap<String, String> clientData, String clientID) {
        // Grab client data for new or existing client in the table.
        String EFIN = clientData.get("EFIN");
        String SIDN = "";
        String firstName = clientData.get("FIRSTNAME");
        String lastName = clientData.get("LASTNAME");
        int last4SS;
        if (clientData.containsKey("SIDN")){
            SIDN = clientData.get("SIDN");
        }
        if (clientData.containsKey("LAST4")){
            last4SS = Integer.parseInt(clientData.get("LAST4"));
        }else if (clientData.containsKey("LAST4SS")){
            last4SS = Integer.parseInt(clientData.get("LAST4SS"));
        } else{
            last4SS = Integer.parseInt(clientData.get("L4SSN"));
        }
        // Not all CSVs contain the DoB of a client.
        String dob = "";
        if (clientData.containsKey("DOB") || clientData.containsKey("DATEOFBIRTH")) {
            dob = clientData.containsKey("DOB")
                    ? clientData.get("DOB")
                    : clientData.get("DATEOFBIRTH");
        }
        // Get the row where the clientID exists in the Client table (if it does exist).
        try (ResultSet query = DB.executeQuery(String.format("SELECT * FROM Client WHERE ID = '%s';", clientID))) {
            // If the client already exists, update the necessary fields.
            if (query.next()){
                // Reset pointer of the ResultSet (somewhat redundant since there should only be one or no elements
                // but a good habit nonetheless).
                query.beforeFirst();

                // Update the fields.
                if (firstName != null){
                    updateFirstName(clientID, firstName);
                }
                if (lastName != null){
                    updateLastName(clientID, lastName);
                }
                if (dob != null){
                    updateDOB(clientID, dob);
                }
                if (EFIN != null){
                    updateEFIN(clientID, EFIN);
                }
                if (SIDN != null){
                    updateSIDN(clientID, SIDN);
                }
                updateLast4SS(clientID, String.valueOf(last4SS));
            }else {
                // Create an update SQL command to insert a new row into the Client table.
                String sqlStmt = String.format("INSERT INTO Client (ID, FirstName, LastName, DoB, Last4SS, EFIN, SIDN)"
                                + "\n" + "VALUES ('%1$s', '%2$s', '%3$s', '%4$s', %5$d, '%6$s', '%7$s');", clientID,
                                firstName, lastName, dob, last4SS, EFIN, SIDN);
                try {
                    // Execute the SQL statement.
                    DB.update(sqlStmt);
                } catch (Exception e) {
                    System.out.print("Error occurred while UPDATE Operation: " + e);
                    throw e;
                }
            }
        }catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    /**
     * Update first name of a Client.
     * @param clientID String, the ID of the Client.
     * @param firstName String, name that the first name will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateFirstName(String clientID, String firstName) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET FirstName = '" + firstName + "'\n" +
                        "WHERE ID = '" + clientID + "';";
        System.out.println(updateStmt);
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    /**
     * Update last name of a Client.
     * @param clientID String, the ID of the Client.
     * @param lastName String, name that the last name will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateLastName(String clientID, String lastName) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET LastName = '" + lastName + "'\n" +
                        "WHERE ID = '" + clientID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    /**
     * Update Date of Birth of a Client.
     * @param clientID String, the ID of the Client.
     * @param dob String, date that the date of birth will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateDOB(String clientID, String dob) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET DoB = '" + dob + "'\n" +
                        "WHERE ID = '" + clientID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    /**
     * Update Last4SS of a Client.
     * @param clientID String, the ID of the Client.
     * @param last4SS String, number that the last 4 SS numbers will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateLast4SS(String clientID, String last4SS) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET Last4SS = " + last4SS + "\n" +
                        "WHERE ID = '" + clientID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    /**
     * Update EFIN of a Client.
     * @param clientID String, the ID of the Client.
     * @param efin String, EFIN that the last EFIN will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateEFIN(String clientID, String efin) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET EFIN = '" + efin + "'\n" +
                        "WHERE ID = '" + clientID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }

    /**
     * Update last name of a Client.
     * @param clientID String, the ID of the Client.
     * @param sidn String, SIDN that the SIDN will be changed to.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    private static void updateSIDN(String clientID, String sidn) throws SQLException{
        String updateStmt =
                "UPDATE Client\n" +
                        "SET SIDN = '" + sidn + "'\n" +
                        "WHERE ID = '" + clientID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
            throw e;
        }
    }



    /*
     * ==================================================================================
     * DEMOGRAPHIC FUNCTIONS
     * ==================================================================================
     */

    static String[] demoFields = new String[] {
            "TAXYEAR",
            "CITY",
            "STATE",
            "ZIP",
            "RESIDENCY",
            "PRIMARYORSECONDARY60"
    };

    static HashMap<String, String> demoFieldMap = new HashMap<>();

    /**
     * Insert a demographic into the Demographic table of the database.
     * Inserts a demographic if the demographic associated with the clientID
     * does not already exist, otherwise the demographic with the associated
     * clientID is updated.
     * @param clientData Properties associated with the demographic.
     * @param clientID ID of the demographic's client.
     */
    public static void insertDemographic(HashMap<String, String> clientData, String clientID){
        // Grab demographic data for new or existing demographic in the demographic table.
        // Not guaranteed that each field is in clientData, so must instantiate variables.
        for (String field: clientData.keySet()){
            if (Arrays.asList(demoFields).contains(field)){
                demoFieldMap.put(field, clientData.get(field));
            }
        }
        System.out.println(demoFieldMap);
        StringBuilder insertStmt = new StringBuilder("INSERT INTO Demographic (Client_ID");
        StringBuilder intoStmt = new StringBuilder("VALUES ('" + clientID + "'");
        if(!demoFieldMap.isEmpty()){
            for (String field: demoFieldMap.keySet()){
                if (!demoFieldMap.get(field).equals("")){
                    insertStmt.append(", ").append(field);
                    if (isNumeric(demoFieldMap.get(field))){
                        intoStmt.append(", ").append(demoFieldMap.get(field));
                    }else{
                        intoStmt.append(", ").append("'").append(demoFieldMap.get(field)).append("'");
                    }
                }
            }
            insertStmt.append(") \n");
            intoStmt.append((");"));
            try {
                // Execute the SQL statement.
                DB.update(insertStmt + intoStmt.toString());
            } catch (Exception e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
                throw e;
            }
        }
    }


    /**
     * Update State of a Demographic.
     * @param demographicID String, the ID of the Demographic.
     * @param ps60p String, number representing sum of 60+ year olds between the primary
     *              and secondary filers.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     */
    public static void updatePrimarySecondary60Plus(String demographicID, String ps60p) throws SQLException{
        String updateStmt =
                "UPDATE Demographic\n" +
                        "SET PrimarySecondary60Plus = '" + ps60p + "'\n" +
                        "WHERE Client_ID = '" + demographicID + "';";
        try{
            DB.update(updateStmt);
        }catch(Exception e){
            System.out.print("Error occurred while UPDATE Operation: " + e);
        }
    }


    /*
     * ==================================================================================
     * QUESTION FUNCTIONS
     * ==================================================================================
     */

    static String[] questionFields = new String[] {
            "CONSENTTODISCLOSETAXRETURN",
            "CONSENTTODISCLOSETAXPAYERD",
            "CONSENTTOUSETAXPAYERDATA",
            "CONSENTTODISCLOSETAXRETURN1",
            "CONSENTTODISCLOSETAXRETURN2",
            "CONSENTTODISCLOSETAXRETURN3",
            "CONSENTTODISCLOSETAXRETURN4",
            "QUESTIONS1",
            "QUESTIONS2",
            "QUESTIONS3",
            "QUESTIONS4",
            "QUESTIONS5",
            "QUESTIONS6",
            "QUESTIONS7",
            "QUESTIONS8",
            "QUESTIONS9",
            "QUESTIONA",
            "QUESTIONB",
            "QUESTIONC",
            "QUESTIOND",
            "QUESTIONE",
            "QUESTIONF",
            "QUESTIONG",
            "QUESTIONH",
            "QUESTIONI",
            "QUESTIONJ",
            "QUESTIONK",
            "PERSONS5ANDUNDER",
            "PERSONSAGE6TO18",
            "PERSONSAGE65PLUS"
    };

    static String[] actualQuestions = new String[] {
            "1WOULDYOUSAYYOUCANCARRY",
            "2WOULDYOUSAYYOUCANREAD",
            "3DOYOUORANYMEMBEROFYOU",
            "4AREYOUORYOURSPOUSEAVE",
            "5YOURRACE",
            "6YOURSPOUSESRACE",
            "7YOURETHNICITY",
            "8YOURSPOUSESETHNICITY",
            "9WASTHETAXPAYERPHYSICALLY",
            "AAREYOUPLANNINGTOSAVEAN",
            "BIFYOUSETASIDEMONEYFROM",
            "CWHICHOFTHEFOLLOWINGBEST",
            "DINTHEPAST12MONTHSHAVE",
            "EINATYPICALMONTHWHICHO",
            "FSUPPOSETHATYOUHAVEANEM",
            "GBECAUSEOFMYMONEYSITUATI",
            "HIAMJUSTGETTINGBYFINANC",
            "IIAMCONCERNEDTHATTHEMON",
            "JIHAVEMONEYLEFTOVERATT",
            "KMYFINANCESCONTROLMYLIFE",
            "PERSONSONTAXRETURNAGE5AN",
            "PERSONSONTAXRETURNAGE618",
            "PERSONSONTAXRETURNAGE65"
    };

    static HashMap<String, String> questionFieldMap = new HashMap<>();

    private static String formatQuestions(String unformattedString){
        if (Character.isDigit(unformattedString.charAt(0))){
            return "QUESTIONS" + unformattedString.charAt(0);
        } else {
            return switch (unformattedString) {
                case "AAREYOUPLANNINGTOSAVEAN" -> "QUESTIONA";
                case "BIFYOUSETASIDEMONEYFROM" -> "QUESTIONB";
                case "CWHICHOFTHEFOLLOWINGBEST" -> "QUESTIONC";
                case "DINTHEPAST12MONTHSHAVE" -> "QUESTIOND";
                case "EINATYPICALMONTHWHICHO" -> "QUESTIONE";
                case "FSUPPOSETHATYOUHAVEANEM" -> "QUESTIONF";
                case "GBECAUSEOFMYMONEYSITUATI" -> "QUESTIONG";
                case "HIAMJUSTGETTINGBYFINANC" -> "QUESTIONH";
                case "IIAMCONCERNEDTHATTHEMON" -> "QUESTIONI";
                case "JIHAVEMONEYLEFTOVERATT" -> "QUESTIONJ";
                case "KMYFINANCESCONTROLMYLIFE" -> "QUESTIONK";
                case "PERSONSONTAXRETURNAGE5AN" -> "PERSONS5ANDUNDER";
                case "PERSONSONTAXRETURNAGE618" -> "PERSONSAGE6TO18";
                case "PERSONSONTAXRETURNAGE65" -> "PERSONSAGE65PLUS";
                default -> null;
            };
        }
    }

    public static void insertQuestion(HashMap<String, String> clientData, String clientID){
        //For each key in the field map
        //If the key matches one of the fields that we have instantiated, put it into our field map.
        //However, if the field is one of the poorly formatted questions, reformat the question name
        //to match the new name that we created ("7YOURETHNICITY" --> "QUESTIONS7") and insert the
        //value of the question into the database.
        //If the field is a consent to disclose tax return, since there are multiple of the same names,
        //need to assign it to the four different fields reserved for it.

        for (String field: clientData.keySet()){
            if (Arrays.asList(questionFields).contains(field)){
                questionFieldMap.put(field, clientData.get(field));
            }
            //Finding exact match of String occurrence in an Array:
            //https://stackoverflow.com/questions/68115849/find-exact-match-from-array
            else if (Arrays.stream(actualQuestions)
                    .anyMatch(word -> Pattern
                            .compile("(?<![a-z-])" + Pattern.quote(word) + "(?![a-z-])",
                                     Pattern.CASE_INSENSITIVE)
                            .matcher(field)
                            .find())){
                questionFieldMap.put(formatQuestions(field), clientData.get(field).replace("'", "`"));
            } else if (field.contains("CONSENTTODISCLOSETAXRETURN")){
                if (questionFieldMap.get("CONSENTTODISCLOSETAXRETURN").equals("")){
                    questionFieldMap.put("CONSENTTODISCLOSETAXRETURN", clientData.get(field));
                }
                else if (questionFieldMap.get("CONSENTTODISCLOSETAXRETURN1").equals("")){
                    questionFieldMap.put("CONSENTTODISCLOSETAXRETURN1", clientData.get(field));
                }
                else if (questionFieldMap.get("CONSENTTODISCLOSETAXRETURN2").equals("")){
                    questionFieldMap.put("CONSENTTODISCLOSETAXRETURN2", clientData.get(field));
                }
                else if (questionFieldMap.get("CONSENTTODISCLOSETAXRETURN3").equals("")){
                    questionFieldMap.put("CONSENTTODISCLOSETAXRETURN3", clientData.get(field));
                }
                else {
                    questionFieldMap.put("CONSENTTODISCLOSETAXRETURN4", clientData.get(field));
                }
            }
        }
        StringBuilder insertStmt = new StringBuilder("INSERT INTO Question (Client_ID");
        StringBuilder intoStmt = new StringBuilder("VALUES ('" + clientID + "'");
        if(!questionFieldMap.isEmpty()){
            for (String field: questionFieldMap.keySet()){
                if (!questionFieldMap.get(field).equals("")){
                    insertStmt.append(", ").append(field);
                    if (isNumeric(questionFieldMap.get(field))){
                        intoStmt.append(", ").append(questionFieldMap.get(field));
                    }else{
                        intoStmt.append(", ").append("'").append(questionFieldMap.get(field)).append("'");
                    }
                }
            }
            insertStmt.append(") \n");
            intoStmt.append((");"));
            System.out.println(insertStmt + intoStmt.toString());
            try {
                // Execute the SQL statement.
                DB.update(insertStmt + intoStmt.toString());
            } catch (Exception e) {
                System.out.print("Error occurred while UPDATE Operation: " + e);
            }
        }
    }


    /*
     * ==================================================================================
     * RETURN DATA FUNCTIONS
     * ==================================================================================
     */

    static String[] returnDataFields = new String[] {
            "CREATEDDATETIME",
            "TAXYEAR",
            "FEDERAL",
            "EIC",
            "ACCEPTEDDATA",
            "RETURNTYPE",
            "FILINGSTATUS",
            "TOTALIRSEXEMPTIONS",
            "REFUND",
            "PAPERSTATE",
            "PAPERFEDERAL",
            "REQUESTINGDD",
            "AGI",
            "ADDCTC",
            "SAVINGSBONDS", // #SAVINGSBONDS
            "SAVINGBOND",
            "CHILDAXCREDIT",
            "EDUCATIONTAXCREDIT",
            "ELDERLYCREDIT",
            "TOTALRESPPYMENT",
            "TOTALADVPTCREPAYMENT",
            "AVERAGEADVPTCPAYMENT",
            "TOTALPTC",
            "BALANCEDUE",
            "ITIN",
            "EXEMPTION7",
            "FULLYEARCOVERAGE",
            "FORM8888",
            "SCHEDULEA",
            "SCHEDULEB",
            "SCHEDULEC",
            "SCHEDULECEZ",
            "SCHEDULED",
            "SCHEDULEE",
            "SCHEDULEF",
            "SCHEDULEH",
            "SCHEDULER",
            "SCHEDULESETP",
            "SCHEDULESESP",
            "AGENCYID",
            "STATEWITHOLDING",
            "STATETAXLIABILITY",
            "AAMOUNTTAXPAYERISPLANNINGTOSAVE"
    };

    static HashMap<String, String> returnFieldMap = new HashMap<>();

    /**
     * Insert a return into the Return table of the database.
     * Inserts a return if the return associated with the clientID
     * does not already exist, otherwise the return with the associated
     * clientID is updated.
     * @param clientData Properties associated with the return.
     * @param clientID ID of the return's client.
     */
    public static void insertReturnData(HashMap<String, String> clientData, String clientID){
        for (String field: clientData.keySet()){
            if (Arrays.asList(returnDataFields).contains(field)){
                returnFieldMap.put(field, clientData.get(field));
            }
        }
        try (ResultSet query = DB.executeQuery(String.format("SELECT * FROM ReturnData WHERE Client_ID = '%1$s'" +
                                                             "AND TAXYEAR = '%2$s' AND FEDERAL = '%3$s';",
                                                      clientID,
                                                      clientData.get("TAXYEAR"),
                                                      clientData.get("FEDERAL")))) {
            // If the client already exists, update the necessary fields.
            if (query.next()) {
                // Reset pointer of the ResultSet (somewhat redundant since there should only be one or no elements
                // but a good habit nonetheless).
                query.beforeFirst();
            }else{
                StringBuilder insertStmt = new StringBuilder("INSERT INTO ReturnData (Client_ID");
                StringBuilder intoStmt = new StringBuilder("VALUES ('" + clientID + "'");
                if (!returnFieldMap.isEmpty()) {
                    for (String field : returnFieldMap.keySet()) {
                        if (!returnFieldMap.get(field).equals("")) {
                            insertStmt.append(", ").append(field);
                            if (isNumeric(returnFieldMap.get(field))) {
                                intoStmt.append(", ").append(returnFieldMap.get(field));
                            } else {
                                intoStmt.append(", ").append("'").append(returnFieldMap.get(field)).append("'");
                            }
                        }
                    }
                    insertStmt.append(") \n");
                    intoStmt.append((");"));
                    try {
                        // Execute the SQL statement.
                        DB.update(insertStmt + intoStmt.toString());
                    } catch (Exception e) {
                        System.out.print("Error occurred while UPDATE Operation: " + e);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Unable to execute query: " + e);
        }
    }


    /*
     * ==================================================================================
     * TAX YEAR FUNCTIONS
     * ==================================================================================
     */

    public static void insertTaxYear(HashMap<String, String> clientData){
        String taxYear = "";
        if (clientData.containsKey("TAXYEAR")){
            taxYear = clientData.get("TAXYEAR");
        }
        if (!taxYear.equals("")){
            try (ResultSet query = DB.executeQuery(String.format("SELECT * FROM TaxYear WHERE TAXYEAR = '%s';", taxYear))) {
                // If the client already exists, update the necessary fields.
                if (query.next()) {
                    // Reset pointer of the ResultSet (somewhat redundant since there should only be one or no elements
                    // but a good habit nonetheless).
                    query.beforeFirst();
                } else {
                    // Create an update SQL command to insert a new row into the Client table.
                    String sqlStmt = "INSERT INTO TaxYear (TAXYEAR)\n" +
                            "VALUES (" + taxYear + ");";
                    try {
                        // Execute the SQL statement.
                        DB.update(sqlStmt);
                    } catch (Exception e) {
                        System.out.print("Error occurred while UPDATE Operation: " + e);
                    }
                }
            } catch (Exception e) {
                System.out.println("Unable to insert Tax Year: " + e);
            }
        }
    }



    /*
    ==================================================================================
    SEARCH/GET FUNCTIONS
    ==================================================================================
     */

    /**
     * Translates a result set into an ObservableList of Data Objects
     * @param rs
     * @param demographic
     * @param returnData
     * @param client
     * @return
     * @throws SQLException
     */
    private static ObservableList<DataObject> getDataObjectList(ResultSet rs, boolean demographic, boolean returnData, boolean client) throws SQLException{
        ObservableList<DataObject> dataObjectList = FXCollections.observableArrayList();
        while(rs.next()){
            DataObject dataObject = new DataObject();
            if(demographic) {
                dataObject.setId(rs.getString("Client_ID"));
                dataObject.setTaxYear(rs.getInt("TAXYEAR"));
                dataObject.setZip(rs.getString("Zip"));
                dataObject.setState(rs.getString("State"));
            }
            if(returnData){
                dataObject.setFederal(rs.getBoolean("FEDERAL"));
                dataObject.setRefund(rs.getInt("REFUND"));
                dataObject.setEic(rs.getInt("EIC"));
                dataObject.setChildTaxCredit(rs.getInt("CHILDTAXCREDIT"));
            }
            if(client){
                dataObject.setFirstName(rs.getString("FirstName"));
                dataObject.setLastName(rs.getString("LastName"));
                dataObject.setDoB(rs.getString("DoB"));
                dataObject.setL4SSN(rs.getString("Last4SS"));
            }
            //This may not be a good way to set TaxYear
            //
            if(demographic || returnData || !client){
                dataObject.setTaxYear(rs.getInt("TAXYEAR"));
            }
            dataObjectList.add(dataObject);
        }
        return dataObjectList;

    }

    /**
     * Searches for a Client by ID.
     * @param ID String, ID of the Client.
     * @return Client with corresponding ID.
     * @throws SQLException Unable to retrieve data, loss of connection, or other errors.
     * @throws ClassNotFoundException Client class unable to be found.
     */
    public static Client searchClient(String ID) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * From Client WHERE ID = " + ID;

        try{
            ResultSet rs = DB.executeQuery(selectStmt);
            return getClientFromResultSet(rs);
        }catch(Exception e){
            System.out.println("Error while searching for " + ID + " : " + e);
            throw e;
        }
    }

    private static Client getClientFromResultSet(ResultSet rs) throws SQLException{
        Client client = null;
        if(rs.next()){
            client = new Client();
            client.setId(rs.getString("ID"));
            client.setFirstName(rs.getString("FirstName"));
            client.setLastName(rs.getString("LastName"));
            client.setDoB(rs.getString("DoB"));
            client.setL4SSN(rs.getString("Last4SS"));
        }
        return client;
    }

    /**
     * runs an SQL query selecting data from the Client table with a given condition
     * @param condition the condition of the WHERE clause of the SQL statement
     * @return the results of the SQL query as an ObservableList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<DataObject> searchClients(String condition) throws SQLException, ClassNotFoundException {
        String selectStmt = "SELECT * FROM Client" + condition;
        System.out.println(selectStmt);
        try {
            ResultSet rsClients = DB.executeQuery(selectStmt);
            ObservableList<DataObject> clientList = getDataObjectList(rsClients, false, false, true);
            System.out.println(clientList);
            return clientList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchDemographics(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM Demographic " + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> demographicList = getDataObjectList(rsDemographics, true, false, false);
            return demographicList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchReturnData(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM ReturnData " + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> demographicList = getDataObjectList(rsDemographics, false, true, false);
            return demographicList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchDemographicsAndReturnData(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM Demographic INNER JOIN ReturnData ON Demographic.TaxYear = ReturnData.TaxYear AND Demographic.Client_ID = ReturnData.Client_ID" + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> demographicList = getDataObjectList(rsDemographics, true, true, false);
            return demographicList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchDemographicsAndClients(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM Demographic INNER JOIN Client ON Demographic.Client_ID = Client.ID" + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> demographicList = getDataObjectList(rsDemographics, true, false, true);
            return demographicList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchReturnDataAndClients(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM ReturnData INNER JOIN Client ON ReturnData.Client_ID = Client.ID" + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> returnDataClientList = getDataObjectList(rsDemographics, false, true, true);
            return returnDataClientList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchDemographicsAndReturnDataAndClients(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM Demographic INNER JOIN ReturnData ON Demographic.TAXYEAR = ReturnData.TAXYEAR AND Demographic.Client_ID = ReturnData.Client_ID INNER JOIN Client ON Demographic.Client_ID = Client.ID" + condition;
        System.out.println(selectStmt);
        try{
            ResultSet rsDemographics = DB.executeQuery(selectStmt);
            ObservableList<DataObject> demographicList = getDataObjectList(rsDemographics, true, true, true);
            return demographicList;
        }catch(SQLException e){
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }

    public static ObservableList<DataObject> searchTaxYears(String condition) throws SQLException, ClassNotFoundException{
        String selectStmt = "SELECT * FROM TaxYear" + condition;
        System.out.println(selectStmt);
        try {
            ResultSet rsClients = DB.executeQuery(selectStmt);
            ObservableList<DataObject> taxYearList = getDataObjectList(rsClients, false, false, false);
            return taxYearList;
        } catch (SQLException e) {
            System.out.println("SQL select operation has failed:" + e);
            throw e;
        }
    }


}
