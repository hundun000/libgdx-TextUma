package hundun.gdxgame.textuma.share.framework.util.text;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author hundun
 * Created on 2021/11/26
 */
public class TextFormatTool {
    private final NavigableMap<Long, String> suffixes = new TreeMap<>();
    public TextFormatTool() {
      suffixes.put(1_000L, "K");
      suffixes.put(1_000_000L, "M");
      suffixes.put(1_000_000_000L, "G");
      suffixes.put(1_000_000_000_000L, "T");
      suffixes.put(1_000_000_000_000_000L, "P");
      suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public String format(long value) {
      //Long.MIN_VALUE == -Long.MIN_VALUE so we need an adjustment here
      if (value == Long.MIN_VALUE) return format(Long.MIN_VALUE + 1);
      if (value < 0) return "-" + format(-value);
      if (value < 1000) return Long.toString(value); //deal with easy case

      Entry<Long, String> e = suffixes.floorEntry(value);
      Long divideBy = e.getKey();
      String suffix = e.getValue();

      int decimalBase = 1000;
      double decimalBaseDouble = 1000d;

      long truncated = value / (divideBy / decimalBase); //the number part of the output times 10
      boolean hasDecimal = true;//truncated < 100 && (truncated / 10d) != (truncated / 10);
      return hasDecimal ? ((truncated / decimalBaseDouble) + suffix) : ((truncated / decimalBase) + suffix);
    }
}
