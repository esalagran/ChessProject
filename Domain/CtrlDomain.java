package Domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class ctrlDomain {

    private ctrlPersistance CP;

    private ctrlDomain() {
        CP = ctrlPersistance.getInstance();

    }

}
