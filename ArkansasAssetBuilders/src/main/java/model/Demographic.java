package model;
import javafx.beans.property.*;
public class Demographic {
	private StringProperty client_id;
	private DoubleProperty tax_year;
	private StringProperty city;
	private StringProperty state;
	private StringProperty zip;
	private DoubleProperty primary_secondary_60_plus;
	private StringProperty residency;
	public Demographic(){
		this.client_id = new SimpleStringProperty();
		this.tax_year = new SimpleDoubleProperty();
		this.city = new SimpleStringProperty();
		this.state = new SimpleStringProperty();
		this.zip = new SimpleStringProperty();
		this.state = new SimpleStringProperty();
		this.primary_secondary_60_plus = new SimpleDoubleProperty();
		this.residency = new SimpleStringProperty();
	}
	public String getClient(){ return client_id.get();}

	public void setClient(String client_id){this.client_idset(client_id);}

	public StringProperty ClientProperty(){return c;}


	public String getClientId(){ return client_id.get();}

	public void setClientId(String client_id){this.client_idset(client_id);}

	public StringProperty ClientIdProperty(){return c;}


	public double getTax(){ return tax_year.get();}

	public void setTax(double tax_year){this.tax_yearset(tax_year);}

	public DoubleProperty TaxProperty(){return c;}


	public double getTaxYear(){ return tax_year.get();}

	public void setTaxYear(double tax_year){this.tax_yearset(tax_year);}

	public DoubleProperty TaxYearProperty(){return c;}


	public String getCity(){ return city.get();}

	public void setCity(String city){this.cityset(city);}

	public StringProperty CityProperty(){return c;}


	public String getState(){ return state.get();}

	public void setState(String state){this.stateset(state);}

	public StringProperty StateProperty(){return c;}


	public String getZip(){ return zip.get();}

	public void setZip(String zip){this.zipset(zip);}

	public StringProperty ZipProperty(){return c;}


	public String getState(){ return state.get();}

	public void setState(String state){this.stateset(state);}

	public StringProperty StateProperty(){return c;}


	public double getPrimary(){ return primary_secondary_60_plus.get();}

	public void setPrimary(double primary_secondary_60_plus){this.primary_secondary_60_plusset(primary_secondary_60_plus);}

	public DoubleProperty PrimaryProperty(){return c;}


	public double getPrimarySecondary(){ return primary_secondary_60_plus.get();}

	public void setPrimarySecondary(double primary_secondary_60_plus){this.primary_secondary_60_plusset(primary_secondary_60_plus);}

	public DoubleProperty PrimarySecondaryProperty(){return c;}


	public double getPrimarySecondary60(){ return primary_secondary_60_plus.get();}

	public void setPrimarySecondary60(double primary_secondary_60_plus){this.primary_secondary_60_plusset(primary_secondary_60_plus);}

	public DoubleProperty PrimarySecondary60Property(){return c;}


	public double getPrimarySecondary60Plus(){ return primary_secondary_60_plus.get();}

	public void setPrimarySecondary60Plus(double primary_secondary_60_plus){this.primary_secondary_60_plusset(primary_secondary_60_plus);}

	public DoubleProperty PrimarySecondary60PlusProperty(){return c;}


	public String getResidency(){ return residency.get();}

	public void setResidency(String residency){this.residencyset(residency);}

	public StringProperty ResidencyProperty(){return c;}


}
