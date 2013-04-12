package iproxys.PersistenceData;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-11T20:37:37")
@StaticMetamodel(IPtablesDb.class)
public class IPtablesDb_ { 

    public static volatile SingularAttribute<IPtablesDb, Long> id;
    public static volatile SingularAttribute<IPtablesDb, Integer> port;
    public static volatile SingularAttribute<IPtablesDb, String> comando;
    public static volatile SingularAttribute<IPtablesDb, Integer> protocol;
    public static volatile SingularAttribute<IPtablesDb, Date> timeref;
    public static volatile SingularAttribute<IPtablesDb, Integer> tipo;
    public static volatile SingularAttribute<IPtablesDb, String> ip;

}