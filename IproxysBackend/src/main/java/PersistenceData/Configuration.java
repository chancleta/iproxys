/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PersistenceData;

import javax.persistence.*;
import java.io.Serializable;

;

/**
 * @author root
 */
@Entity
@Table(name = "Configuration")
@NamedQueries({
        @NamedQuery(name = Configuration.findByConfigurationTypeNamedQuery, query = "select u from Configuration u where u.type = :type"),
})
public class Configuration extends PersistenceProvider implements Serializable {

    public static final String findByConfigurationTypeNamedQuery = "findByConfigurationType";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, unique = true)
    private ConfigurationType type;

    @Column(nullable = false)
    private String data;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ConfigurationType getType() {
        return this.type;
    }

    public void setType(ConfigurationType type) {
        this.type = type;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Configuration findByConfigurationType(ConfigurationType configType) {
        Query findConfigurationType = entityManager.createNamedQuery(findByConfigurationTypeNamedQuery, this.getClass());
        findConfigurationType.setParameter("type", configType);

        try{
            return (Configuration) findConfigurationType.getSingleResult();

        }catch (NoResultException ex){
            return null;
        }

    }
}

