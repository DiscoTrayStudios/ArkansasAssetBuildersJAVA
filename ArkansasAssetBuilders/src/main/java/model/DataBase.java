package model;

import SQLite.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {

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
        String selectStmt = "SELECT * FROM Demographic INNER JOIN ReturnData ON Demographic.TaxYear = ReturnData.TaxYear AND Demographic.Client_ID = ReturnData.Client_ID INNER JOIN Client ON Demographic.Client_ID = Client.ID" + condition;
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
                dataObject.setClient_ID(rs.getString("Client_ID"));
                dataObject.setTaxYear(rs.getInt("TaxYear"));
                dataObject.setAddress(rs.getString("Address"));
                dataObject.setCounty(rs.getString("County"));
                dataObject.setZip(rs.getInt("Zip"));
                dataObject.setState(rs.getString("State"));
            }
            if(returnData){
                dataObject.setFederalReturn(rs.getInt("FederalReturn"));
                dataObject.setTotalRefund(rs.getInt("TotalRefund"));
                dataObject.setEITC(rs.getInt("EITC"));
                dataObject.setCTC(rs.getInt("CTC"));
                dataObject.setDependents(rs.getInt("Dependents"));
                dataObject.setSurveyScore(rs.getInt("SurveyScore"));
            }
            if(client){
                dataObject.setFirstName(rs.getString("FirstName"));
                dataObject.setLastName(rs.getString("LastName"));
                dataObject.setDoB(rs.getString("DoB"));
                dataObject.setLast4SS(rs.getInt("Last4SS"));
            }
            //This may not be a good way to set TaxYear
            //
            if(demographic || returnData || !client){
                dataObject.setTaxYear(rs.getInt("TaxYear"));
            }
            dataObjectList.add(dataObject);
        }
        return dataObjectList;

    }

}
