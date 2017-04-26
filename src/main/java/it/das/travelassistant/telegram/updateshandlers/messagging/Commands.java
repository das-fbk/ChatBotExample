package it.das.travelassistant.telegram.updateshandlers.messagging;

import java.nio.charset.Charset;

/**
 * Created by gekoramy
 */
public class Commands {

    // region commandsu


	
    public static final String HELPCOMMAND = "/help";
    public static final String STARTCOMMAND = "/start start";
    public static final String LANGUAGECOMMAND = "/language";
    public static final String BACKCOMMAND = "BACK";
    public static final String LOCATION = "PARTI DALLA POSIZIONE CORRENTE";
    public static final String MANUAL = "SCRIVI PARTENZA e DESTINAZIONE";

    
    // filter commands
    public static final String PRICE = "\u2193"+"\ud83d\udcb5";
    public static final String TIME = "\u2193"+"\u23f3";
    public static final String CHANGES = "\u2193"+"\u0058";
    public static final String DISTANCE = "\u2193"+"\u33ce";

    // endregion commands

    // region languages

    public static final String ITALIANO = "ITALIANO";
    public static final String ENGLISH = "ENGLISH";
    public static final String ESPANOL = "ESPANOL";

    // endregion languages

    // region cbq

    public static final String CURRENT = "·";
    public static final String INDEX = "INDEX";
    public static final String RETURN = "RITORNO";
    public static final String ANDATA = "ANDATA";
    public static final String NOW = "NOW";

    // endregion cbq

    // region main menu
    public static final String ROME2RIO = "ROME2RIO";
    public static final String CALCOLA = "CALCOLA";
    /* vecchio */
    public static final String TAXICOMMAND = "TAXI";
    public static final String AUTOBUSCOMMAND = "AUTOBUS";
    public static final String TRAINSCOMMAND = "TRAINS";
    public static final String PARKINGSCOMMAND = "PARKINGS";
    public static final String BIKESHARINGSCOMMAND = "BIKE SHARINGS";
    

    // endregion main menu

}
