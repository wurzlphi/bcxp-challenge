package de.exxcellent.challenge.weather;

import de.exxcellent.challenge.input.csv.CsvColumn;


/**
 * Data class holding all available weather data.
 */
public class WeatherData {

    @CsvColumn(columnName = "Day")
    public int day;
    @CsvColumn(columnName = "MxT")
    public int maxTemperature;
    @CsvColumn(columnName = "MnT")
    public int minTemperature;
    @CsvColumn(columnName = "AvT")
    public int averageTemperature;
    @CsvColumn(columnName = "AvDP")
    public float AvDP;
    @CsvColumn(columnName = "1HrP TPcpn")
    public int _1HrP_TPcpn;
    @CsvColumn(columnName = "PDir")
    public int PDir;
    @CsvColumn(columnName = "AvSp")
    public float AvSp;
    @CsvColumn(columnName = "Dir")
    public int Dir;
    @CsvColumn(columnName = "MxS")
    public int MxS;
    @CsvColumn(columnName = "SkyC")
    public float SkyC;
    @CsvColumn(columnName = "MxR")
    public int MxR;
    @CsvColumn(columnName = "Mn")
    public int Mn;
    @CsvColumn(columnName = "R AvSLP")
    public float R_AvSLP;

}
