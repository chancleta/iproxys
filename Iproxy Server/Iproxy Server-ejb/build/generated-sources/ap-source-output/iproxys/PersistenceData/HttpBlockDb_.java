package iproxys.PersistenceData;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-11T20:37:37")
@StaticMetamodel(HttpBlockDb.class)
public class HttpBlockDb_ { 

    public static volatile SingularAttribute<HttpBlockDb, Long> id;
    public static volatile SingularAttribute<HttpBlockDb, Date> timeref;
    public static volatile SingularAttribute<HttpBlockDb, String> domain;
    public static volatile SingularAttribute<HttpBlockDb, String> ip;

}