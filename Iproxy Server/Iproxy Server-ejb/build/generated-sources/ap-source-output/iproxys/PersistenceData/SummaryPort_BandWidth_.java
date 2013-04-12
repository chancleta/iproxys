package iproxys.PersistenceData;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-11T20:37:37")
@StaticMetamodel(SummaryPort_BandWidth.class)
public class SummaryPort_BandWidth_ { 

    public static volatile SingularAttribute<SummaryPort_BandWidth, Long> id;
    public static volatile SingularAttribute<SummaryPort_BandWidth, Integer> port;
    public static volatile SingularAttribute<SummaryPort_BandWidth, Integer> protocol;
    public static volatile SingularAttribute<SummaryPort_BandWidth, Date> timeref;
    public static volatile SingularAttribute<SummaryPort_BandWidth, Double> bdusage;

}