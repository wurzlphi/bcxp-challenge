package de.exxcellent.challenge.football;

import de.exxcellent.challenge.input.csv.CsvColumn;


/**
 * Data class holding football data.
 */
public class FootballData {

    @CsvColumn(columnName = "Team")
    public String team;
    @CsvColumn(columnName = "Games")
    public int games;
    @CsvColumn(columnName = "Wins")
    public int wins;
    @CsvColumn(columnName = "Losses")
    public int losses;
    @CsvColumn(columnName = "Draws")
    public int draws;
    @CsvColumn(columnName = "Goals")
    public int goals;
    @CsvColumn(columnName = "Goals Allowed")
    public int goalsAllowed;
    @CsvColumn(columnName = "Points")
    public int points;

}
