package danielkaiser.gss.challenge.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class GenerateShortUuid {

    public String generate(int length) {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, length);
    }

}
