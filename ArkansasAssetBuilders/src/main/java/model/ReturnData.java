package model;

import javafx.beans.property.*;

/**
 * ReturnData class
 * <br/>
 * <br/>
 * Tax return data from inputted tax information.
 * Tax return information is stored in the local database.
 */
public class ReturnData {
    private IntegerProperty FederalReturn;
    private IntegerProperty TotalRefund;
    private IntegerProperty EITC;
    private IntegerProperty CTC;
    private IntegerProperty Dependents;
    private IntegerProperty SurveyScore;
    private Client client;
    private TaxYear taxYear;

    /**
     * Constructor for the ReturnData class.
     */
    public ReturnData(){
        this.FederalReturn = new SimpleIntegerProperty();
        this.TotalRefund = new SimpleIntegerProperty();
        this.EITC = new SimpleIntegerProperty();
        this.CTC = new SimpleIntegerProperty();
        this.Dependents = new SimpleIntegerProperty();
        this.SurveyScore = new SimpleIntegerProperty();
        this.client = new Client();
        this.taxYear = new TaxYear();
    }

    /**
     * Method for getting the ID of the ReturnData object.
     * @return int, ID of the ReturnData object.
     */
    public String getClient_ID(){
        return client.getID();
    }

    /**
     * Method for setting the ID of the ReturnData object.
     * @param Client_ID int, ID that the old ID will be set to.
     */
    public void setClient_ID(String Client_ID){
        client.setID(Client_ID);
    }

    /**
     * Method for retrieving ReturnData ID from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the ID of the ReturnData.
     */
    public StringProperty Client_IDProperty(){
        return client.Client_IDProperty();
    }

    /**
     * Method for getting the tax year ID of the ReturnData object.
     * @return int, tax year ID of the ReturnData object.
     */
    public int getTaxYear(){
        return taxYear.getTaxYear();
    }

    /**
     * Method for setting the tax year ID of the ReturnData object.
     * @param TaxYear int, ID that will replace the old tax year ID.
     */
    public void setTaxYear(int TaxYear){
        taxYear.setTaxYear(TaxYear);
    }

    /**
     * Method for retrieving ReturnData tax year ID from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the tax year ID of the ReturnData.
     */
    public IntegerProperty taxYearProperty(){
        return taxYear.taxYearProperty();
    }

    /**
     * Method for getting the federal return from the ReturnData object.
     * @return int, federal return of the ReturnData object.
     */
    public int getFederalReturn(){
        return FederalReturn.get();
    }

    /**
     * Method for setting the federal return of the ReturnData object.
     * @param FederalReturn int, federal return that will replace the old federal
     *                      return.
     */
    public void setFederalReturn(int FederalReturn){
        this.FederalReturn.set(FederalReturn);
    }

    /**
     * Method for retrieving ReturnData federal return from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the federal return of the ReturnData.
     */
    public IntegerProperty federalReturnProperty(){
        return FederalReturn;
    }

    /**
     * Method for getting the total refund of a ReturnData object.
     * @return int, total refund of the ReturnData object.
     */
    public int getTotalRefund(){
        return TotalRefund.get();
    }

    /**
     * Method for setting the total refund of a ReturnData object.
     * @param TotalRefund int, total refund that will replace the old total
     *                    refund.
     */
    public void setTotalRefund(int TotalRefund){
        this.TotalRefund.set(TotalRefund);
    }

    /**
     * Method for retrieving ReturnData total refund from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the total refund of the ReturnData.
     */
    public IntegerProperty totalRefundProperty(){
        return TotalRefund;
    }

    /**
     * Method for getting the EITC of the ReturnData object.
     * @return int, the EITC of the ReturnData object.
     */
    public int getEITC(){
        return EITC.get();
    }

    /**
     * Method for setting the EITC of the ReturnData object.
     * @param EITC int, the EITC that will replace the old EITC.
     */
    public void setEITC(int EITC){
        this.EITC.set(EITC);
    }

    /**
     * Method for retrieving ReturnData EITC from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the EITC of the ReturnData.
     */
    public IntegerProperty EITCProperty(){
        return EITC;
    }

    /**
     * Method for getting the CTC of the ReturnData object.
     * @return int, CTC of the ReturnData object.
     */
    public int getCTC(){
        return CTC.get();
    }

    /**
     * Method for setting the CTC of the ReturnData object.
     * @param CTC int, the CTC that will replace the old CTC.
     */
    public void setCTC(int CTC){
        this.CTC.set(CTC);
    }

    /**
     * Method for retrieving ReturnData CTC from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the CTC of the ReturnData.
     */
    public IntegerProperty CTCProperty(){
        return CTC;
    }

    /**
     * Method for getting the dependents of the ReturnData object.
     * @return int, dependents of the ReturnData object.
     */
    public int getDependents(){
        return Dependents.get();
    }

    /**
     * Method for setting the dependents of the ReturnData object.
     * @param Dependents int, the dependents that the old dependents
     *                   will be set to.
     */
    public void setDependents(int Dependents){
        this.Dependents.set(Dependents);
    }

    /**
     * Method for retrieving ReturnData dependents from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the dependents of the ReturnData.
     */
    public IntegerProperty dependentsProperty(){
        return Dependents;
    }

    /**
     * Method for getting the survey score of a ReturnData object.
     * @return int, survey score of the ReturnData object.
     */
    public int getSurveyScore(){
        return SurveyScore.get();
    }

    /**
     * Method for setting the survey score of the ReturnData object.
     * @param SurveyScore int, the survey score that will replace the
     *                    old survey score.
     */
    public void setSurveyScore(int SurveyScore){
        this.SurveyScore.set(SurveyScore);
    }

    /**
     * Method for retrieving ReturnData survey score from the database in an
     * IntegerProperty format.<br/>
     * @return IntegerProperty, the survey score of the ReturnData.
     */
    public IntegerProperty surveyScoreProperty(){
        return SurveyScore;
    }

    public String getFirstName(){return client.getFirstName();}

    public void setFirstName(String FirstName){client.setFirstName(FirstName);}

    public StringProperty firstNameProperty(){return client.firstNameProperty();}

    public String getLastName(){return client.getLastName();}

    public void setLastName(String LastName){client.setLastName(LastName);}

    public StringProperty lastNameProperty(){return client.lastNameProperty();}

    public String getDoB(){return client.getDoB();}

    public void setDoB(String DoB){client.setDoB(DoB);}

    public StringProperty doBProperty(){return client.doBProperty();}

    public int getLast4SS(){return client.getLast4SS();}

    public void setLast4SS(int Last4SS){client.setLast4SS(Last4SS);}

    public IntegerProperty last4SSProperty(){return client.last4SSProperty();}


}
