package hundun.gdxgame.textuma.core.logic;
/**
 * @author hundun
 * Created on 2021/11/05
 */

import java.util.Arrays;
import java.util.List;

public class ResourceType {
    public static final String TURN = "ENUM_RESC@DAY";
    public static final String COIN = "ENUM_RESC@COIN";
    
    public static final String HORSE_SPEED = "ENUM_RESC@HORSE_SPEED";
    public static final String HORSE_STAMINA = "ENUM_RESC@HORSE_STAMINA";
    public static final String HORSE_POWER = "ENUM_RESC@HORSE_POWER";

    //public static final String WIN_TROPHY = "ENUM_RESC@TROPHY";
    
    public static final List<String> VALUES_FOR_SHOW_ORDER = Arrays.asList(TURN, COIN, HORSE_SPEED);
    
    
}
