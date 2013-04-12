package iproxys.PersistenceData;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-11T20:37:37")
@StaticMetamodel(UnBlockableIP.class)
public class UnBlockableIP_ { 

    public static volatile SingularAttribute<UnBlockableIP, Long> id;
    public static volatile SingularAttribute<UnBlockableIP, Date> blockDate;
    public static volatile SingularAttribute<UnBlockableIP, String> ip;

}