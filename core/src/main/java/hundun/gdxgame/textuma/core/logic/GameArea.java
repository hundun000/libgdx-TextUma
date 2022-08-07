package hundun.gdxgame.textuma.core.logic;

import java.util.Arrays;
import java.util.List;


public class GameArea {
    public static final String AREA_RACE = "ENUM_AREA@AREA_RACE";
    public static final String AREA_TRAIN = "ENUM_AREA@AREA_TRAIN";
    public static final String AREA_RECORD = "ENUM_AREA@AREA_RECORD";
    
    public static final List<String> values = Arrays.asList(
            AREA_RACE, 
            AREA_TRAIN
//            AREA_RECORD
            );
}
