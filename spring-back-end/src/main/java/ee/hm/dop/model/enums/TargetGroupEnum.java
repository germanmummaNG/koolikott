package ee.hm.dop.model.enums;

import ee.hm.dop.model.TargetGroup;
import org.apache.commons.lang3.Range;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TargetGroupEnum {
    ZERO_FIVE(0, 5),
    SIX_SEVEN(6, 7),
    GRADE1(7, 8),
    GRADE2(8, 9),
    GRADE3(9, 10),
    GRADE4(10, 11),
    GRADE5(11, 12),
    GRADE6(12, 13),
    GRADE7(13, 14),
    GRADE8(14, 15),
    GRADE9(15, 16),
    GYMNASIUM(16, 19);

    private final Range<Integer> range;

    TargetGroupEnum(int from, int to) {
        this.range = Range.between(from, to);
    }

    public Range<Integer> getRange() {
        return range;
    }

    public static boolean containsTargetGroup(List<TargetGroup> targetGroups, TargetGroupEnum groupEnum) {
        return targetGroups.stream().anyMatch(t -> t.getName().equals(groupEnum.name()));
    }

    public static List<String> getTargetGroupEnumNames(int from, int to) {
        return getTargetGroupEnumsByAge(from, to).stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static List<TargetGroupEnum> getTargetGroupEnumsByAge(int from, int to) {
        Range<Integer> range = Range.between(from, to);

        return Arrays.stream(TargetGroupEnum.values())
                .filter(targetGroupEnum -> targetGroupEnum.getRange().isOverlappedBy(range))
                .collect(Collectors.toList());
    }
}
