package iproxys.PersistenceData;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-11T20:37:37")
@StaticMetamodel(UserTable.class)
public class UserTable_ { 

    public static volatile SingularAttribute<UserTable, Long> id;
    public static volatile SingularAttribute<UserTable, String> nombre;
    public static volatile SingularAttribute<UserTable, String> apellido;
    public static volatile SingularAttribute<UserTable, String> username;
    public static volatile SingularAttribute<UserTable, String> password;
    public static volatile SingularAttribute<UserTable, String> correo;

}